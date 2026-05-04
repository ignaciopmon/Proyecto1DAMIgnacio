package com.salesianos.dam;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medico {

    @Id
    private String idMedico;
    private String especialidad;
    private String nombre;

}
