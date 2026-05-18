package com.salesianos.dam;

import java.time.LocalDateTime;

import com.salesianos.dam.enums.EstadosCita;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Long id;
    private LocalDateTime fecha;
    private int duracionMinutos;
    private double precio;
    @Enumerated(EnumType.STRING)
    private EstadosCita estado;
    private String observaciones;

    @ManyToOne
    private Paciente paciente;
    @ManyToOne
    private Medico medico;

}
