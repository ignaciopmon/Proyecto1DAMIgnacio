package com.salesianos.dam.exception;

public class PacienteSinNombreException extends RuntimeException {
    
    public PacienteSinNombreException() {
        super("El nombre del paciente es obligatorio y no puede estar vacío.");
    }
}
