package com.salesianos.dam.service;

import org.springframework.stereotype.Service;

import com.salesianos.dam.Paciente;
import com.salesianos.dam.exception.EmailInvalidoException;
import com.salesianos.dam.exception.PacienteSinNombreException;
import com.salesianos.dam.exception.TelefonoInvalidoException;
import com.salesianos.dam.repository.PacienteRepository;

@Service
public class PacienteService extends BaseServiceImpl<Paciente, Long, PacienteRepository> {

    @Override
    public Paciente save(Paciente paciente) {
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            throw new PacienteSinNombreException();
        }
        
        if (paciente.getEmail() == null || !paciente.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new EmailInvalidoException();
        }

        if (paciente.getTelefono() == null || !paciente.getTelefono().matches("^[0-9]{9}$")) {
            throw new TelefonoInvalidoException();
        }
        
        return super.save(paciente);
    }
}

