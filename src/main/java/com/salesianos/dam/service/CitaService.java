package com.salesianos.dam.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.salesianos.dam.Cita;
import com.salesianos.dam.Medico;
import com.salesianos.dam.exception.CitaDuplicadaException;
import com.salesianos.dam.exception.CitaSolapadaException;
import com.salesianos.dam.repository.CitaRepository;


@Service
public class CitaService extends BaseServiceImpl<Cita, Long, CitaRepository> {

    // decidimos el horario de la clínica, ahora abrimos a las 9:00 y cerramos a las 18:00
    private static final LocalTime INICIO_CONSULTA = LocalTime.of(9, 0);
    private static final LocalTime FIN_CONSULTA = LocalTime.of(18, 0);

    // Esto es antes de guardar en la base de datos y este método se activa al guardar o editar una cita
    @Override
    public Cita save(Cita cita) {
        if (cita.getMedico() != null && cita.getFecha() != null) {
            LocalDate fecha = cita.getFecha().toLocalDate();
            LocalTime hora = cita.getFecha().toLocalTime();
            
            //comprobamos si el médico tiene libre ese hueco horario
            if (!estaLibre(cita.getMedico(), fecha, hora, cita.getId())) {
                throw new CitaSolapadaException();
            }

            // Si hay un paciente asignado comprobamos que no tenga ya otra cita con este mismo médico para ese día
            if (cita.getPaciente() != null) {
                if (tieneCitaMismoDia(cita.getPaciente().getId(), cita.getMedico().getId(), fecha, cita.getId())) {
                    throw new CitaDuplicadaException();
                }
            }
        }
        return super.save(cita);
    }

    // Este método comprueba si un paciente ya tiene una cita reservada con el mismo médico el mismo día. Y sirve para evitar que alguien reserve varias citas seguidas por error.
    public boolean tieneCitaMismoDia(Long pacienteId, Long medicoId, LocalDate fecha, Long idExcluir) {
        for (Cita cita : getCitasDelDia(medicoId, fecha)) {
            // si estamos editando una cita que ya está agendada pues ignoramos su propio ID para que no se cuente a sí misma como duplicada
            if (Objects.equals(cita.getId(), idExcluir)) {
                continue;
            }
            if (cita.getPaciente() != null && Objects.equals(cita.getPaciente().getId(), pacienteId)) {
                return true;
            }
        }
        return false;
    }

    // Buscamos en la base de datos todas las citas que tiene un médico concreto en un día concreto
    public List<Cita> getCitasDelDia(Long medicoId, LocalDate fecha) {
        return repository.findByMedicoIdAndFechaBetween(
            medicoId, 
            fecha.atStartOfDay(), 
            fecha.atTime(23, 59, 59)
        );
    }

    // generamos la lista de todas las horas disponibles para un médico en un día concreto
    public List<LocalTime> getHorasDisponibles(Medico medico, LocalDate fecha) {
        List<LocalTime> horasLibres = new ArrayList<>();
        int duracion = getDuracionCita(medico);
        LocalTime hora = INICIO_CONSULTA;

        // Ahora vamos sumando la duración de la cita a la hora inicial hasta llegar al final
        while (!hora.plusMinutes(duracion).isAfter(FIN_CONSULTA)) {
            if (estaLibre(medico, fecha, hora, null)) {
                horasLibres.add(hora);
            }
            hora = hora.plusMinutes(duracion);
        }
        return horasLibres;
    }

    // Comprueba si el médico está libre en una hora concreta y compara el intervalo de la nueva cita con el de las citas que ya tiene agendadas ese día
    public boolean estaLibre(Medico medico, LocalDate fecha, LocalTime hora, Long idExcluir) {
        int duracion = getDuracionCita(medico);
        LocalDateTime inicioCita = LocalDateTime.of(fecha, hora);
        LocalDateTime finCita = inicioCita.plusMinutes(duracion);

        for (Cita citaExistente : getCitasDelDia(medico.getId(), fecha)) {
            // Si estamos editando una cita existente ignoramos a si misma para no solaparnos con nosotros mismos
            if (Objects.equals(citaExistente.getId(), idExcluir)) {
                continue;
            }
            
            LocalDateTime inicioExistente = citaExistente.getFecha();
            LocalDateTime finExistente = inicioExistente.plusMinutes(getDuracionCita(medico));

            // Si el inicio de nuestra cita es antes del fin de la otra y el fin de nustra cita es después del inicio de la otra pues chocan
            if (inicioCita.isBefore(finExistente) && finCita.isAfter(inicioExistente)) {
                return false;
            }
        }
        return true;
    }

    // Obtiene la duración de la cita de un médico y si no tiene una duración configurada usamos 30 minutos
    public int getDuracionCita(Medico medico) {
        return (medico.getDuracionCitaMinutos() != null && medico.getDuracionCitaMinutos() > 0) 
            ? medico.getDuracionCitaMinutos() 
            : 30;
    }

    // Calcula el precio de la cita multiplicando el precio por minuto por los minutos que dura y si el médico no tiene precio configurado usamos 1.50 por defecto
    public double calcularPrecio(Medico medico) {
        double precioPorMinuto = medico.getPrecioPorMinuto() != null ? medico.getPrecioPorMinuto() : 1.50;
        return precioPorMinuto * getDuracionCita(medico);
    }

    // devuelve todas las citas de un médico en concreto
    public List<Cita> findByMedicoId(Long medicoId) {
        return medicoId != null ? repository.findByMedicoId(medicoId) : List.of();
    }
}
