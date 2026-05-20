package com.salesianos.dam.service;

import org.springframework.stereotype.Service;

import com.salesianos.dam.Paciente;
import com.salesianos.dam.exception.PacienteSinNombreException;
import com.salesianos.dam.repository.PacienteRepository;

@Service
public class PacienteService extends BaseServiceImpl<Paciente, Long, PacienteRepository> {

    @Override
    public Paciente save(Paciente paciente) {
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            throw new PacienteSinNombreException();
        }
        return super.save(paciente);
    }
}

