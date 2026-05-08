package com.salesianos.dam;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @Id @GeneratedValue
    private String codCita;
    private LocalDateTime fecha;
    private int duracionMinutos;
    private double precio;
    private String estado;
    private String observaciones;
    
    @ManyToOne
    private Paciente paciente;
    @ManyToOne
    private Medico medico;

}
