package com.salesianos.dam;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class Medico {

    @Id @GeneratedValue
    private Long id;

    @NotBlank(message = "La especialidad es obligatoria.")
    private String especialidad;

    @NotBlank(message = "El nombre del médico es obligatorio.")
    private String nombre;

    @Builder.Default
    @NotNull(message = "La duración de la cita es obligatoria.")
    @Min(value = 5, message = "La duración de la cita debe ser de al menos 5 minutos.")
    private Integer duracionCitaMinutos = 30;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "medico")
    private List<Cita> citas = new ArrayList<>();
    
}
