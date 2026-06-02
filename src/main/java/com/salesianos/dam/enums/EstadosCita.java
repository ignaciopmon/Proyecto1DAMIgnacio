package com.salesianos.dam.enums;

public enum EstadosCita {

    PENDIENTE("Pendiente"),
    REALIZADA("Realizada"),
    NO_PRESENTADO("No presentado");

    private final String displayName;

    EstadosCita(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
