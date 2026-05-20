package com.salesianos.dam.exception;

public class CitaSolapadaException extends RuntimeException {
    
    public CitaSolapadaException() {
        super("La cita se solapa con otra existente para el mismo médico.");
    }
}

