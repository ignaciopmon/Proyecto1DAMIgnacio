package com.salesianos.dam.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.salesianos.dam.Cita;
import com.salesianos.dam.Medico;
import com.salesianos.dam.repository.CitaRepository;

@Service
public class CitaService extends BaseServiceImpl<Cita, Long, CitaRepository> {

    private static final LocalTime HORA_INICIO_CONSULTA = LocalTime.of(9, 0);
    private static final LocalTime HORA_FIN_CONSULTA = LocalTime.of(14, 0);

    public List<Cita> findCitasDelDia(Long medicoId, LocalDate fecha) {
        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = fecha.atTime(23, 59, 59);

        return repository.findByMedicoIdAndFechaBetween(medicoId, inicioDia, finDia);
    }

    public List<LocalTime> obtenerHorasDisponibles(Medico medico, LocalDate fecha, Long citaEditadaId) {
        List<LocalTime> horasDisponibles = new ArrayList<>();
        int duracion = getDuracionCita(medico);
        LocalTime hora = HORA_INICIO_CONSULTA;

        while (!hora.plusMinutes(duracion).isAfter(HORA_FIN_CONSULTA)) {
            if (horaDisponible(medico, fecha, hora, citaEditadaId)) {
                horasDisponibles.add(hora);
            }

            hora = hora.plusMinutes(duracion);
        }

        return horasDisponibles;
    }

    public boolean horaDisponible(Medico medico, LocalDate fecha, LocalTime horaNueva, Long citaEditadaId) {
        int duracionNueva = getDuracionCita(medico);
        LocalDateTime inicioNueva = LocalDateTime.of(fecha, horaNueva);
        LocalDateTime finNueva = inicioNueva.plusMinutes(duracionNueva);

        for (Cita cita : findCitasDelDia(medico.getId(), fecha)) {
            if (citaEditadaId != null && cita.getId().equals(citaEditadaId)) {
                continue;
            }

            int duracionExistente = cita.getDuracionMinutos();
            if (duracionExistente <= 0) {
                duracionExistente = duracionNueva;
            }

            LocalDateTime inicioExistente = cita.getFecha();
            LocalDateTime finExistente = inicioExistente.plusMinutes(duracionExistente);

            if (inicioNueva.isBefore(finExistente) && finNueva.isAfter(inicioExistente)) {
                return false;
            }
        }

        return true;
    }

    public int getDuracionCita(Medico medico) {
        if (medico.getDuracionCitaMinutos() == null || medico.getDuracionCitaMinutos() <= 0) {
            return 30;
        }

        return medico.getDuracionCitaMinutos();
    }
}
