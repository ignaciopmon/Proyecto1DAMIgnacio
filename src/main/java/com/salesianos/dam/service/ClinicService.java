package com.salesianos.dam.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianos.dam.Cita;
import com.salesianos.dam.Medico;
import com.salesianos.dam.Paciente;

@Service
public class ClinicService {

    public List<Paciente> getPacientes() {
        return Arrays.asList(
                new Paciente("P001", "Ana Gomez", "ana.gomez@email.com", "600111222"),
                new Paciente("P002", "Luis Perez", "luis.perez@email.com", "600333444"),
                new Paciente("P003", "Marta Sanchez", "marta.sanchez@email.com", "600555666"));
    }

    public List<Medico> getMedicos() {
        return Arrays.asList(
                new Medico("M001", "Medicina general", "Dra. Laura Martin"),
                new Medico("M002", "Pediatria", "Dr. Carlos Ruiz"),
                new Medico("M003", "Traumatologia", "Dra. Elena Torres"));
    }

    public List<Cita> getCitas() {
        return Arrays.asList(
                new Cita("C001", LocalDateTime.of(2026, 5, 6, 10, 0), 30, 35.0, "PENDIENTE",
                        "Primera revision", "P001", "M001"),
                new Cita("C002", LocalDateTime.of(2026, 5, 6, 11, 0), 45, 50.0, "REALIZADA",
                        "Dolor de rodilla", "P002", "M003"),
                new Cita("C003", LocalDateTime.of(2026, 5, 7, 9, 30), 30, 35.0, "DISPONIBLE",
                        "Hueco libre en agenda", "", "M002"));
    }
}
