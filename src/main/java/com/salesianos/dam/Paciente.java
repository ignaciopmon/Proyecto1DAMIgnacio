package com.salesianos.dam;

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
public class Paciente {

    @Id
    private String idPaciente;
    private String nombre;
    private String email;
    private String telefono;


}
