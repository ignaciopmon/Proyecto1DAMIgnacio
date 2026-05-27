package com.salesianos.dam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MedicoConCitasActivasException extends RuntimeException {
    
    public MedicoConCitasActivasException() {
        super("No se puede eliminar el médico porque tiene citas activas programadas.");
    }
}
