package com.salesianos.dam.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.salesianos.dam.Cita;
import com.salesianos.dam.Medico;
import com.salesianos.dam.Paciente;
import org.springframework.stereotype.Repository;

@Repository
public class CitaRepository {

        public List<Cita> getCitas() {
                Paciente p1 = new Paciente("P001", "Ana Gomez", "ana.gomez@email.com", "600111222", null);
                Paciente p2 = new Paciente("P002", "Luis Perez", "luis.perez@email.com", "600333444", null);

                Medico m1 = new Medico("M001", "Medicina general", "Dra. Laura Martin");
                Medico m2 = new Medico("M002", "Pediatria", "Dr. Carlos Ruiz");
                Medico m3 = new Medico("M003", "Traumatologia", "Dra. Elena Torres");
                Medico m4 = Medico.builder().idMedico("M004").especialidad("Dentista").nombre("Dr. José Antonio Muñoz").build();

                return Arrays.asList(
                                new Cita("C001", LocalDateTime.of(2026, 5, 6, 10, 0), 30, 35.0, "PENDIENTE",
                                                "Primera revision", p1, m1),
                                new Cita("C002", LocalDateTime.of(2026, 5, 6, 11, 0), 45, 50.0, "REALIZADA",
                                                "Dolor de rodilla", p2, m3),
                                new Cita("C003", LocalDateTime.of(2026, 5, 7, 9, 30), 30, 35.0, "DISPONIBLE",
                                                "Hueco libre en agenda", null, m2),
                				new Cita("C004", LocalDateTime.of(2026, 5, 10, 17, 15), 60, 80, "DISPONIBLE",
                								"Extracción de muelas", p2, m4));
        }

}
