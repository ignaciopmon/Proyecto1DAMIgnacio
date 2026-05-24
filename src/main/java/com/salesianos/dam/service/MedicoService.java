package com.salesianos.dam.service;

import org.springframework.stereotype.Service;

import com.salesianos.dam.Medico;
import com.salesianos.dam.exception.EspecialidadInvalidaException;
import com.salesianos.dam.exception.MedicoSinNombreException;
import com.salesianos.dam.repository.MedicoRepository;

@Service
public class MedicoService extends BaseServiceImpl<Medico, Long, MedicoRepository> {

    @Override
    public Medico save(Medico medico) {
        if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
            throw new MedicoSinNombreException();
        }
        
        if (medico.getEspecialidad() == null || medico.getEspecialidad().trim().isEmpty()) {
            throw new EspecialidadInvalidaException();
        }
        
        if (medico.getDuracionCitaMinutos() == null || medico.getDuracionCitaMinutos() < 5) {
            throw new IllegalArgumentException("La duración de la cita debe ser de al menos 5 minutos.");
        }
        
        return super.save(medico);
    }
}
