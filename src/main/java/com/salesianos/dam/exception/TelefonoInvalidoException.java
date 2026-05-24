package com.salesianos.dam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TelefonoInvalidoException extends RuntimeException {
    
    public TelefonoInvalidoException() {
        super("El teléfono no es válido. Debe contener exactamente 9 números.");
    }
}
