package com.salesianos.dam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class PacienteSinNombreException extends RuntimeException {
    
    public PacienteSinNombreException() {
        super("El nombre del paciente es obligatorio y no puede estar vacío.");
    }
}
