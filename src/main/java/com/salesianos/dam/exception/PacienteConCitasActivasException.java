package com.salesianos.dam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PacienteConCitasActivasException extends RuntimeException {
    
    public PacienteConCitasActivasException() {
        super("No se puede eliminar el paciente porque tiene citas activas programadas.");
    }
}
