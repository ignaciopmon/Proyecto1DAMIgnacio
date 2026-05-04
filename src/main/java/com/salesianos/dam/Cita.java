package com.salesianos.dam;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cita {

    @Id
    private String codCita;
    private LocalDateTime fecha;
    private Duration duracion;
    private double precio;

}
