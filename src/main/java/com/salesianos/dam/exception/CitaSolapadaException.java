package com.salesianos.dam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class CitaSolapadaException extends RuntimeException {
    
    public CitaSolapadaException() {
        super("La cita se solapa con otra existente para el mismo médico.");
    }
}

