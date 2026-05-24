package com.salesianos.dam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MedicoSinNombreException extends RuntimeException {
    
    public MedicoSinNombreException() {
        super("El nombre del médico es obligatorio y no puede estar vacío.");
    }
}
