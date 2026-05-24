package com.salesianos.dam;

import java.time.LocalDateTime;

import com.salesianos.dam.enums.EstadosCita;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private String codigo;

    @NotNull(message = "La fecha de la cita es obligatoria.")
    private LocalDateTime fecha;

    @Min(value = 5, message = "La duración mínima de la cita es de 5 minutos.")
    private int duracionMinutos;

    @Min(value = 0, message = "El precio de la cita no puede ser negativo.")
    private double precio;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado de la cita es obligatorio.")
    private EstadosCita estado;
    
    private String observaciones;

    @ManyToOne
    @NotNull(message = "Debe asignar un paciente a la cita.")
    private Paciente paciente;

    @ManyToOne
    @NotNull(message = "Debe asignar un médico a la cita.")
    private Medico medico;

    @jakarta.persistence.PrePersist
    public void generarCodigo() {
        if (this.codigo == null) {
            this.codigo = "CITA-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

}
