package com.salesianos.dam;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cita {

    @Id
    private String codCita;
    private LocalDateTime fecha;
    private int duracionMinutos;
    private double precio;
    private String estado;
    private String observaciones;
    private String idPaciente;
    private String idMedico;

}
