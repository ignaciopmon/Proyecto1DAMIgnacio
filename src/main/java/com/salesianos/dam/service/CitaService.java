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

    private static final LocalTime INICIO_CONSULTA = LocalTime.of(9, 0);
    private static final LocalTime FIN_CONSULTA = LocalTime.of(18, 0);

    @Override
    public Cita save(Cita cita) {
        if (cita.getMedico() != null && cita.getFecha() != null) {
            LocalDate fecha = cita.getFecha().toLocalDate();
            LocalTime hora = cita.getFecha().toLocalTime();
            
            if (!estaLibre(cita.getMedico(), fecha, hora, cita.getId())) {
                throw new CitaSolapadaException();
            }

            if (cita.getPaciente() != null) {
                if (tieneCitaMismoDia(cita.getPaciente().getId(), cita.getMedico().getId(), fecha, cita.getId())) {
                    throw new CitaDuplicadaException();
                }
            }
        }
        return super.save(cita);
    }

    public boolean tieneCitaMismoDia(Long pacienteId, Long medicoId, LocalDate fecha, Long idExcluir) {
        for (Cita cita : getCitasDelDia(medicoId, fecha)) {
            if (Objects.equals(cita.getId(), idExcluir)) {
                continue;
            }
            if (cita.getPaciente() != null && Objects.equals(cita.getPaciente().getId(), pacienteId)) {
                return true;
            }
        }
        return false;
    }

    public List<Cita> getCitasDelDia(Long medicoId, LocalDate fecha) {
        return repository.findByMedicoIdAndFechaBetween(
            medicoId, 
            fecha.atStartOfDay(), 
            fecha.atTime(23, 59, 59)
        );
    }

    public List<LocalTime> getHorasDisponibles(Medico medico, LocalDate fecha) {
        List<LocalTime> horasLibres = new ArrayList<>();
        int duracion = getDuracionCita(medico);
        LocalTime hora = INICIO_CONSULTA;

        while (!hora.plusMinutes(duracion).isAfter(FIN_CONSULTA)) {
            if (estaLibre(medico, fecha, hora)) {
                horasLibres.add(hora);
            }
            hora = hora.plusMinutes(duracion);
        }
        return horasLibres;
    }

    public boolean estaLibre(Medico medico, LocalDate fecha, LocalTime hora) {
        return estaLibre(medico, fecha, hora, null);
    }

    public boolean estaLibre(Medico medico, LocalDate fecha, LocalTime hora, Long idExcluir) {
        int duracion = getDuracionCita(medico);
        LocalDateTime inicioCita = LocalDateTime.of(fecha, hora);
        LocalDateTime finCita = inicioCita.plusMinutes(duracion);

        for (Cita citaExistente : getCitasDelDia(medico.getId(), fecha)) {
            if (Objects.equals(citaExistente.getId(), idExcluir)) {
                continue;
            }
            
            LocalDateTime inicioExistente = citaExistente.getFecha();
            LocalDateTime finExistente = inicioExistente.plusMinutes(getDuracionCita(medico));

            if (inicioCita.isBefore(finExistente) && finCita.isAfter(inicioExistente)) {
                return false;
            }
        }
        return true;
    }

    public int getDuracionCita(Medico medico) {
        return (medico.getDuracionCitaMinutos() != null && medico.getDuracionCitaMinutos() > 0) 
            ? medico.getDuracionCitaMinutos() 
            : 30;
    }

    public double calcularPrecio(Medico medico) {
        double precioPorMinuto = medico.getPrecioPorMinuto() != null ? medico.getPrecioPorMinuto() : 1.50;
        return precioPorMinuto * getDuracionCita(medico);
    }

    public List<Cita> findByMedicoId(Long medicoId) {
        return medicoId != null ? repository.findByMedicoId(medicoId) : List.of();
    }
}
