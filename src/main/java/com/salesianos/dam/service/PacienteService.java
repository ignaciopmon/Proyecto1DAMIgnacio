package com.salesianos.dam.service;

import org.springframework.stereotype.Service;

import com.salesianos.dam.Paciente;
import com.salesianos.dam.enums.EstadosCita;
import com.salesianos.dam.exception.PacienteConCitasActivasException;
import com.salesianos.dam.repository.PacienteRepository;
import java.util.List;

@Service
public class PacienteService extends BaseServiceImpl<Paciente, Long, PacienteRepository> {

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(paciente -> {
            // buscamos entre todas sus citas para ver si alguna sigue como pendiente
            boolean tieneCitasActivas = paciente.getCitas().stream()
                .anyMatch(cita -> cita.getEstado() == EstadosCita.PENDIENTE);

            if (tieneCitasActivas) {
                throw new PacienteConCitasActivasException();
            }
        });

        super.deleteById(id);
    }

    public List<Object[]> findPacientesMasFrecuentes() {
        return repository.findPacientesMasFrecuentes();
    }

}


