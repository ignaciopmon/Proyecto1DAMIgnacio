package com.salesianos.dam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.salesianos.dam.enums.EstadosCita;
import com.salesianos.dam.service.CitaService;
import com.salesianos.dam.service.MedicoService;
import com.salesianos.dam.service.PacienteService;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private CitaService citaService;

    @Override
    public void run(String... args) throws Exception {
        if (pacienteService.findAll().isEmpty() && medicoService.findAll().isEmpty()) {
            
            Paciente p1 = Paciente.builder()
                .nombre("María López")
                .email("maria.lopez@email.com")
                .telefono("612345678")
                .build();
            Paciente p2 = Paciente.builder()
                .nombre("Juan Pérez")
                .email("juan.perez@email.com")
                .telefono("698765432")
                .build();
            Paciente p3 = Paciente.builder()
                .nombre("Elena Gómez")
                .email("elena.gomez@email.com")
                .telefono("654321098")
                .build();

            pacienteService.save(p1);
            pacienteService.save(p2);
            pacienteService.save(p3);

            Medico m1 = Medico.builder()
                .nombre("Dr. Carlos Gutiérrez")
                .especialidad("Cardiología")
                .usuario("medico")
                .duracionCitaMinutos(30)
                .precioPorMinuto(1.50)
                .build();
            Medico m2 = Medico.builder()
                .nombre("Dra. Ana Martínez")
                .especialidad("Pediatría")
                .duracionCitaMinutos(20)
                .precioPorMinuto(2.00)
                .build();
            Medico m3 = Medico.builder()
                .nombre("Dr. Luis Fernández")
                .especialidad("Dermatología")
                .duracionCitaMinutos(15)
                .precioPorMinuto(2.50)
                .build();

            medicoService.save(m1);
            medicoService.save(m2);
            medicoService.save(m3);

            LocalDate futuro = LocalDate.now().plusDays(2);

            Cita c1 = Cita.builder()
                .paciente(p1)
                .medico(m1)
                .fecha(futuro.atTime(10, 0))
                .duracionMinutos(m1.getDuracionCitaMinutos())
                .precio(citaService.calcularPrecio(m1))
                .estado(EstadosCita.PENDIENTE)
                .observaciones("Revisión rutinaria de cardiología")
                .build();

            Cita c2 = Cita.builder()
                .paciente(p2)
                .medico(m2)
                .fecha(futuro.atTime(11, 0))
                .duracionMinutos(m2.getDuracionCitaMinutos())
                .precio(citaService.calcularPrecio(m2))
                .estado(EstadosCita.PENDIENTE)
                .observaciones("Vacuna de los 2 años")
                .build();

            Cita c3 = Cita.builder()
                .paciente(p3)
                .medico(m3)
                .fecha(futuro.atTime(12, 0))
                .duracionMinutos(m3.getDuracionCitaMinutos())
                .precio(citaService.calcularPrecio(m3))
                .estado(EstadosCita.PENDIENTE)
                .observaciones("Consulta por manchas en la piel")
                .build();

            citaService.save(c1);
            citaService.save(c2);
            citaService.save(c3);
        }
    }
}
