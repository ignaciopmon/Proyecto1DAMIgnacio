package com.salesianos.dam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailInvalidoException extends RuntimeException {
    
    public EmailInvalidoException() {
        super("El email no es válido. Debe contener un formato correcto (ejemplo@dominio.com).");
    }
}
