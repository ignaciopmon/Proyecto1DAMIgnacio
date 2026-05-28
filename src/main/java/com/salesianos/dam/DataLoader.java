package com.salesianos.dam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
            
            String[][] patientData = {
                {"Alejandro Ruiz", "alejandro.ruiz@email.com", "601234567"},
                {"Beatriz Gómez", "beatriz.gomez@email.com", "612345678"},
                {"Carlos Ortega", "carlos.ortega@email.com", "623456789"},
                {"Diana Castro", "diana.castro@email.com", "634567890"},
                {"Eduardo Marín", "eduardo.marin@email.com", "645678901"},
                {"Fernanda Gil", "fernanda.gil@email.com", "656789012"},
                {"Gabriel Navarro", "gabriel.navarro@email.com", "667890123"},
                {"Helena Vidal", "helena.vidal@email.com", "678901234"},
                {"Ignacio Pardo", "ignacio.pardo@email.com", "689012345"},
                {"Julia Montes", "julia.montes@email.com", "690123456"},
                {"Kevin Ramos", "kevin.ramos@email.com", "602345678"},
                {"Laura Soler", "laura.soler@email.com", "613456789"},
                {"Manuel Vega", "manuel.vega@email.com", "624567890"},
                {"Natalia Rivas", "natalia.rivas@email.com", "635678901"},
                {"Oscar Ferrer", "oscar.ferrer@email.com", "646789012"},
                {"Patricia Aguilar", "patricia.aguilar@email.com", "657890123"},
                {"Quirino Flores", "quirino.flores@email.com", "668901234"},
                {"Rosa Crespo", "rosa.crespo@email.com", "679012345"},
                {"Sergio Benítez", "sergio.benitez@email.com", "680123456"},
                {"Teresa Hidalgo", "teresa.hidalgo@email.com", "691234567"},
                {"Ulrich Sanz", "ulrich.sanz@email.com", "603456789"},
                {"Valeria Romero", "valeria.romero@email.com", "614567890"},
                {"Walter Medina", "walter.medina@email.com", "625678901"},
                {"Ximena Cruz", "ximena.cruz@email.com", "636789012"},
                {"Yago Pascual", "yago.pascual@email.com", "647890123"},
                {"Zoe Blanco", "zoe.blanco@email.com", "658901234"},
                {"Andrés Garrido", "andres.garrido@email.com", "669012345"},
                {"Blanca Fuentes", "blanca.fuentes@email.com", "670123456"},
                {"César Torres", "cesar.torres@email.com", "681234567"},
                {"Dolores Muñoz", "dolores.munoz@email.com", "692345678"},
                {"Emilio Calvo", "emilio.calvo@email.com", "604567890"},
                {"Francisca Cabrera", "francisca.cabrera@email.com", "615678901"},
                {"Guillermo Pastor", "guillermo.pastor@email.com", "626789012"},
                {"Irene Santana", "irene.santana@email.com", "637890123"},
                {"Jaime Roldán", "jaime.roldan@email.com", "648901234"},
                {"Karen Soto", "karen.soto@email.com", "659012345"},
                {"Luis Herrero", "luis.herrero@email.com", "670123456"},
                {"Marta Lara", "marta.lara@email.com", "681234567"},
                {"Nicolás Díaz", "nicolas.diaz@email.com", "692345678"},
                {"Olivia López", "olivia.lopez@email.com", "605678901"},
                {"Pablo Pérez", "pablo.perez@email.com", "616789012"},
                {"Raquel Gómez", "raquel.gomez@email.com", "627890123"},
                {"Santiago Ruiz", "santiago.ruiz@email.com", "638901234"},
                {"Tatiana Castro", "tatiana.castro@email.com", "649012345"},
                {"Úrsula Marín", "ursula.marin@email.com", "650123456"},
                {"Vicente Gil", "vicente.gil@email.com", "661234567"},
                {"Wendy Navarro", "wendy.navarro@email.com", "672345678"},
                {"Xavier Vidal", "xavier.vidal@email.com", "683456789"},
                {"Yolanda Pardo", "yolanda.pardo@email.com", "694567890"},
                {"Zacarías Montes", "zacarias.montes@email.com", "606789012"},
                {"Adrián Ramos", "adrian.ramos@email.com", "617890123"},
                {"Berta Soler", "berta.soler@email.com", "628901234"},
                {"Claudio Vega", "claudio.vega@email.com", "639012345"},
                {"Daniela Rivas", "daniela.rivas@email.com", "640123456"},
                {"Enrique Ferrer", "enrique.ferrer@email.com", "651234567"},
                {"Fátima Aguilar", "fatima.aguilar@email.com", "662345678"},
                {"Gonzalo Flores", "gonzalo.flores@email.com", "673456789"},
                {"Hilda Crespo", "hilda.crespo@email.com", "684567890"},
                {"Iván Benítez", "ivan.benitez@email.com", "695678901"},
                {"Josefa Hidalgo", "josefa.hidalgo@email.com", "607890123"},
                {"Juan Manuel Sanz", "juanmanuel.sanz@email.com", "618901234"},
                {"Leonor Romero", "leonor.romero@email.com", "629012345"},
                {"Marcos Medina", "marcos.medina@email.com", "640123456"},
                {"Nuria Cruz", "nuria.cruz@email.com", "651234567"},
                {"Orlando Pascual", "orlando.pascual@email.com", "662345678"},
                {"Pilar Blanco", "pilar.blanco@email.com", "673456789"},
                {"Ramón Garrido", "ramon.garrido@email.com", "684567890"},
                {"Silvia Fuentes", "silvia.fuentes@email.com", "695678901"},
                {"Tomás Torres", "tomas.torres@email.com", "608901234"},
                {"Úrsula Muñoz", "ursula.munoz@email.com", "619012345"},
                {"Valentín Calvo", "valentin.calvo@email.com", "620123456"},
                {"Virginia Cabrera", "virginia.cabrera@email.com", "631234567"},
                {"Wilfredo Pastor", "wilfredo.pastor@email.com", "642345678"},
                {"Yasmina Santana", "yasmina.santana@email.com", "653456789"},
                {"Zaira Roldán", "zaira.roldan@email.com", "664567890"},
                {"Alfredo Soto", "alfredo.soto@email.com", "675678901"},
                {"Bárbara Herrero", "barbara.herrero@email.com", "686789012"},
                {"Christian Lara", "christian.lara@email.com", "697890123"},
                {"Delia Díaz", "delia.diaz@email.com", "609012345"},
                {"Esteban López", "esteban.lopez@email.com", "619123456"}
            };

            List<Paciente> pacientes = new ArrayList<>();
            for (String[] data : patientData) {
                Paciente p = Paciente.builder()
                    .nombre(data[0])
                    .email(data[1])
                    .telefono(data[2])
                    .build();
                pacienteService.save(p);
                pacientes.add(p);
            }

            Medico m1 = Medico.builder().nombre("Dr. Carlos Gutiérrez").especialidad("Cardiología").usuario("medico").duracionCitaMinutos(30).precioPorMinuto(1.50).build();
            Medico m2 = Medico.builder().nombre("Dra. Ana Martínez").especialidad("Pediatría").duracionCitaMinutos(20).precioPorMinuto(2.00).build();
            Medico m3 = Medico.builder().nombre("Dr. Luis Fernández").especialidad("Dermatología").duracionCitaMinutos(15).precioPorMinuto(2.50).build();
            Medico m4 = Medico.builder().nombre("Dra. Sofía Vega").especialidad("Ginecología").duracionCitaMinutos(30).precioPorMinuto(2.00).build();
            Medico m5 = Medico.builder().nombre("Dr. Miguel Herrero").especialidad("Traumatología").duracionCitaMinutos(45).precioPorMinuto(1.80).build();
            Medico m6 = Medico.builder().nombre("Dra. Elena Ramos").especialidad("Oftalmología").duracionCitaMinutos(20).precioPorMinuto(2.20).build();

            medicoService.save(m1);
            medicoService.save(m2);
            medicoService.save(m3);
            medicoService.save(m4);
            medicoService.save(m5);
            medicoService.save(m6);

            LocalDate today = LocalDate.now();

            String[] observacionesCardio = {
                "Revisión rutinaria de cardiología",
                "Seguimiento de tensión arterial",
                "Electrocardiograma de control",
                "Consulta por palpitaciones leves",
                "Chequeo post-esfuerzo",
                "Estudio pre-operatorio",
                "Control de soplos",
                "Revisión de marcapasos"
            };

            String[] observacionesPedia = {
                "Vacunación sistemática y peso",
                "Control de crecimiento mensual",
                "Consulta por fiebre y tos",
                "Chequeo pediátrico anual",
                "Revisión del primer año",
                "Dolor abdominal leve",
                "Consulta de lactancia",
                "Seguimiento de alergia"
            };

            String[] observacionesDerma = {
                "Revisión general de lunares",
                "Consulta por brote de acné",
                "Tratamiento de eccema seco",
                "Eliminación de queratosis",
                "Valoración de mancha cutánea",
                "Tratamiento de psoriasis leve",
                "Control de dermatitis atópica",
                "Biopsia de control"
            };

            String[] observacionesGine = {
                "Ecografía ginecológica trimestral",
                "Consulta anual de control",
                "Planificación familiar y anticonceptivos",
                "Control post-parto rutinario",
                "Consulta por desarreglo hormonal",
                "Ecografía pélvica",
                "Revisión citológica",
                "Consulta por molestias"
            };

            String[] observacionesTrauma = {
                "Esguince de tobillo grado I",
                "Dolor lumbar por sobrecarga",
                "Revisión de fractura consolidada",
                "Infiltración intraarticular rodilla",
                "Estudio de pisada y dolor talón",
                "Dolor cervical agudo",
                "Control de prótesis de cadera",
                "Sesión de valoración física"
            };

            String[] observacionesOftal = {
                "Control de agudeza visual y graduación",
                "Medición de presión intraocular",
                "Estudio de fondo de ojo diabético",
                "Conjuntivitis alérgica estacional",
                "Revisión de sequedad ocular",
                "Seguimiento de cataratas leves",
                "Control de miopía magna",
                "Revisión post-quirúrgica"
            };

            Random random = new Random();
            List<Medico> medicos = List.of(m1, m2, m3, m4, m5, m6);
            String[][] observacionesPorMedico = {
                observacionesCardio,
                observacionesPedia,
                observacionesDerma,
                observacionesGine,
                observacionesTrauma,
                observacionesOftal
            };

            for (int d = -7; d <= 15; d++) {
                LocalDate dia = today.plusDays(d);
                boolean esPasado = dia.isBefore(today);

                for (int mIdx = 0; mIdx < medicos.size(); mIdx++) {
                    Medico medico = medicos.get(mIdx);
                    String[] observaciones = observacionesPorMedico[mIdx];

                    // Obtener huecos disponibles estándar (redondos) para este médico en este día
                    List<LocalTime> horasDisponibles = citaService.getHorasDisponibles(medico, dia);
                    if (horasDisponibles.isEmpty()) {
                        continue;
                    }

                    // Menos densidad de citas en general:
                    // Pasado: 25% de probabilidad de tener 1 cita, sino 0.
                    // Futuro/Presente: entre 0 y 2 citas al día.
                    int numCitasMax = esPasado ? (random.nextDouble() < 0.25 ? 1 : 0) : random.nextInt(3);
                    int numCitas = Math.min(numCitasMax, horasDisponibles.size());
                    if (numCitas == 0) {
                        continue;
                    }

                    // Mezclar los huecos disponibles
                    Collections.shuffle(horasDisponibles, random);
                    List<LocalTime> horasSeleccionadas = horasDisponibles.subList(0, numCitas);

                    for (LocalTime hora : horasSeleccionadas) {
                        // Buscar un paciente aleatorio que no tenga cita con este médico en este día
                        Paciente pacienteElegido = null;
                        List<Paciente> pacientesCandidatos = new ArrayList<>(pacientes);
                        Collections.shuffle(pacientesCandidatos, random);

                        for (Paciente p : pacientesCandidatos) {
                            if (!citaService.tieneCitaMismoDia(p.getId(), medico.getId(), dia, null)) {
                                pacienteElegido = p;
                                break;
                            }
                        }

                        if (pacienteElegido == null) {
                            continue;
                        }

                        // Seleccionar observación aleatoria
                        String obs = observaciones[random.nextInt(observaciones.length)];

                        // Estado de la cita
                        EstadosCita estado = esPasado 
                            ? ((random.nextDouble() < 0.15) ? EstadosCita.NO_PRESENTADO : EstadosCita.REALIZADA) 
                            : EstadosCita.PENDIENTE;

                        // Crear y guardar la cita
                        crearCita(pacienteElegido, medico, dia.atTime(hora), estado, obs);
                    }
                }
            }
        }
    }

    private void crearCita(Paciente paciente, Medico medico, LocalDateTime fecha, EstadosCita estado, String observaciones) {
        Cita c = Cita.builder()
            .paciente(paciente)
            .medico(medico)
            .fecha(fecha)
            .duracionMinutos(citaService.getDuracionCita(medico))
            .precio(citaService.calcularPrecio(medico))
            .estado(estado)
            .observaciones(observaciones)
            .build();
        citaService.save(c);
    }
}
