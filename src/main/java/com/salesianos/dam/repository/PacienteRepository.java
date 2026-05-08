package com.salesianos.dam.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.salesianos.dam.Paciente;

@Repository
public class PacienteRepository {

        public List<Paciente> getPacientes() {
                return Arrays.asList(
                                new Paciente("P001", "Ana Gómez", "ana.gomez@email.com", "600111222", null),
                                new Paciente("P002", "Luis Pérez", "luis.perez@email.com", "600333444", null),
                                new Paciente("P003", "Marta Perera", "marta.sanchez@email.com", "600555666", null));
        }

}
