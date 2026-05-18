package com.salesianos.dam;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private String especialidad;
    private String nombre;
    @Builder.Default
    private Integer duracionCitaMinutos = 30;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "medico")
    private List<Cita> citas = new ArrayList<>();
    
}
