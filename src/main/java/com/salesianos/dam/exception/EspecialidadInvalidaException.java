package com.salesianos.dam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EspecialidadInvalidaException extends RuntimeException {
    
    public EspecialidadInvalidaException() {
        super("La especialidad del médico es obligatoria y debe ser válida.");
    }
}
