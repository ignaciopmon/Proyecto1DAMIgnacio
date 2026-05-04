package com.salesianos.dam;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paciente {

    @Id
    private String idPaciente;
    private String nombre;
    private String email;
    private String telefono;


}