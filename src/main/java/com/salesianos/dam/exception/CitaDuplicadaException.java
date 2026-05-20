package com.salesianos.dam.exception;

public class CitaDuplicadaException extends RuntimeException {
    
    public CitaDuplicadaException() {
        super("Un paciente no puede tener más de una cita con el mismo médico el mismo día.");
    }
}
