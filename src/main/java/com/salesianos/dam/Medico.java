package com.salesianos.dam;

import java.util.List;

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
    private String idMedico;
    private String especialidad;
    private String nombre;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "medico")
    private List<Cita> citas = new ArrayList<>();
    
}
