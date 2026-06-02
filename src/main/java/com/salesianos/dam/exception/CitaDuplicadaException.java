package com.salesianos.dam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class CitaDuplicadaException extends RuntimeException {
    
    public CitaDuplicadaException() {
        super("Un paciente no puede tener más de una cita con el mismo médico el mismo día.");
    }
}
