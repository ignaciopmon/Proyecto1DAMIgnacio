package com.salesianos.dam.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.salesianos.dam.Medico;
import com.salesianos.dam.enums.EstadosCita;
import com.salesianos.dam.exception.MedicoConCitasActivasException;
import com.salesianos.dam.repository.MedicoRepository;
import java.util.List;

@Service
public class MedicoService extends BaseServiceImpl<Medico, Long, MedicoRepository> {

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(medico -> {
            // miramos en la lista de citas del médico si hay alguna que esté todavía en estado pendiente
            boolean tieneCitasActivas = medico.getCitas().stream()
                .anyMatch(cita -> cita.getEstado() == EstadosCita.PENDIENTE);
            // si tiene citas pendientes paramos el borrado y lanzamos un error
            if (tieneCitasActivas) {
                throw new MedicoConCitasActivasException();
            }
        });
        // Si no tiene ninguna cita activa borramos bien
        super.deleteById(id);
    }

    public Medico findByUsuario(String usuario) {
        if (usuario == null) return null;
        return repository.findByUsuario(usuario).orElse(null);
    }

    public List<Object[]> findMedicosMasActivos() {
        return repository.findMedicosMasActivos();
    }

}

