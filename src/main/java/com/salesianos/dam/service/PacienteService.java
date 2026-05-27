package com.salesianos.dam.service;

import org.springframework.stereotype.Service;

import com.salesianos.dam.Paciente;
import com.salesianos.dam.enums.EstadosCita;
import com.salesianos.dam.exception.PacienteConCitasActivasException;
import com.salesianos.dam.repository.PacienteRepository;

@Service
public class PacienteService extends BaseServiceImpl<Paciente, Long, PacienteRepository> {

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(paciente -> {
            // buscamos entre todas sus citas para ver si alguna sigue como pendiente
            boolean tieneCitasActivas = paciente.getCitas().stream()
                .anyMatch(cita -> cita.getEstado() == EstadosCita.PENDIENTE);
            // Si tiene citas que aún no han pasado no dejamos borrarlo
            if (tieneCitasActivas) {
                throw new PacienteConCitasActivasException();
            }
        });
        // Si el paciente está libre de citas pendientes lo borramos
        super.deleteById(id);
    }
}


