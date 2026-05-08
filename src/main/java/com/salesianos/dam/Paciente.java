package com.salesianos.dam;

import java.util.ArrayList;
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
public class Paciente {

    @Id @GeneratedValue
    private String idPaciente;
    private String nombre;
    private String email;
    private String telefono;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "paciente")
    private List<Cita> citas = new ArrayList<>();

}
