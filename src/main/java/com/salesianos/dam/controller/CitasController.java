package com.salesianos.dam.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianos.dam.Cita;
import com.salesianos.dam.Medico;
import com.salesianos.dam.enums.EstadosCita;
import com.salesianos.dam.exception.CitaDuplicadaException;
import com.salesianos.dam.exception.CitaSolapadaException;
import com.salesianos.dam.service.CitaService;
import com.salesianos.dam.service.MedicoService;
import com.salesianos.dam.service.PacienteService;
import jakarta.validation.Valid;

@Controller
public class CitasController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;


    // método saber qué médico ha iniciado sesión en la web y si no hay nadie logueado devuelve null
    private Medico getMedicoLogueado(java.security.Principal principal) {
        if (principal == null) return null;
        return medicoService.findByUsuario(principal.getName());
    }

    // Muestra la pantalla con el listado general de citas de la clínica
    // si ha entrado un médico solo le mostramos sus propias citas pero si entra un admin le mostramos todas las citas
    @GetMapping("/citas")
    public String citas(Model model, java.security.Principal principal) {
        java.util.List<Cita> citasList;
        Medico medicoLogueado = getMedicoLogueado(principal);
        
        if (medicoLogueado != null) {
            citasList = citaService.findByMedicoId(medicoLogueado.getId());
        } else {
            citasList = citaService.findAll();
        }
        
        model.addAttribute("citas", citasList);
        model.addAttribute("estados", EstadosCita.values());
        return "citas/list";
    }

    // Muestra un formulario donde eliges médico y día y si eres un médico en activo solo puedes agendar citas contigo mismo
    @GetMapping("/citas/nueva")
    public String showPaso1(Model model, java.security.Principal principal) {
        Medico medicoLogueado = getMedicoLogueado(principal);
        if (medicoLogueado != null) {
            model.addAttribute("medicos", java.util.List.of(medicoLogueado));
            model.addAttribute("esMedico", true);
        } else {
            model.addAttribute("medicos", medicoService.findAll());
            model.addAttribute("esMedico", false);
        }
        return "citas/formulario-paso1";
    }

    //Carga los detalles específicos una vez elegidos el médico y el día. Aquí se calculan las horas en las que ese médico está libre hoy
    @GetMapping("/citas/paso2")
    public String showPaso2(Model model,
                            @RequestParam Long medicoId,
                            @RequestParam LocalDate fechaDia,
                            java.security.Principal principal) {
        Medico medicoLogueado = getMedicoLogueado(principal);
        // si el usuario es un médico e intenta meter URL de otro médico le mandamos a error
        if (medicoLogueado != null && !medicoLogueado.getId().equals(medicoId)) {
            return "redirect:/error";
        }
        return cargarPaso2(model, new Cita(), medicoId, fechaDia, null, null);
    }

    // procesa el envío del formulario del paso 2 para guardar la cita en el sistema y 
    // pone el precio y la duración estimada basándose en los datos del médico
    @PostMapping("/citas/guardar")
    public String saveCita(@ModelAttribute("cita") Cita cita,
                           Model model,
                           @RequestParam("medicoId") Long medicoId,
                           @RequestParam("fechaDia") LocalDate fechaDia,
                           @RequestParam("hora") LocalTime hora,
                           @RequestParam(value = "pacienteId", required = false) Long pacienteId,
                           java.security.Principal principal) {

        Medico medicoLogueado = getMedicoLogueado(principal);
        if (medicoLogueado != null && !medicoLogueado.getId().equals(medicoId)) {
            return "redirect:/error";
        }

        Medico medico = medicoService.findById(medicoId).orElse(null);

        if (medico == null) {
            return cargarPaso2(model, cita, medicoId, fechaDia, hora, "Selecciona un médico válido.");
        }

        // rellenamos automáticamente los datos de la cita calculados en el servicio
        cita.setMedico(medico);
        cita.setFecha(LocalDateTime.of(fechaDia, hora));
        cita.setDuracionMinutos(citaService.getDuracionCita(medico));
        cita.setPrecio(citaService.calcularPrecio(medico));

        if (pacienteId != null) {
            cita.setPaciente(pacienteService.findById(pacienteId).orElse(null));
        }

        // intentamos guardar la cita y si salta un error de solapamiento o cita duplicada
        //  recargamos la pantalla mostrando el error para que el usuario elija otra hora
        try {
            citaService.save(cita);
        } catch (CitaSolapadaException | CitaDuplicadaException e) {
            return cargarPaso2(model, cita, medicoId, fechaDia, hora, e.getMessage());
        }
        return "redirect:/citas";
    }


    // muestra la pantalla para editar una cita existente volviendo a cargar el paso 2
    @GetMapping("/citas/editar/{id}")
    public String showEditCitaForm(@PathVariable Long id, Model model) {
        Cita cita = citaService.findById(id).orElse(null);
        if (cita == null) {
            return "redirect:/citas";
        }

        Long medicoId = cita.getMedico() != null ? cita.getMedico().getId() : null;
        LocalDate fechaDia = cita.getFecha() != null ? cita.getFecha().toLocalDate() : null;
        LocalTime hora = cita.getFecha() != null ? cita.getFecha().toLocalTime() : null;

        return cargarPaso2(model, cita, medicoId, fechaDia, hora, null);
    }


    @GetMapping("/citas/eliminar/{id}")
    public String deleteCita(@PathVariable Long id) {
        citaService.deleteById(id);
        return "redirect:/citas";
    }


    // permite actualizar el estado de una cita desde la propia lista pero uun médico solo puede alterar el estado de sus propias citas
    @PostMapping("/citas/{id}/estado")
    public String updateEstado(@PathVariable Long id,
                               @RequestParam("estado") EstadosCita estado,
                               java.security.Principal principal) {
        citaService.findById(id).ifPresent(cita -> {
            Medico medicoLogueado = getMedicoLogueado(principal);
            if (medicoLogueado == null || (cita.getMedico() != null && cita.getMedico().getId().equals(medicoLogueado.getId()))) {
                cita.setEstado(estado);
                citaService.save(cita);
            }
        });
        return "redirect:/citas";
    }


    // método para rellenar todos los datos necesarios en la pantalla del paso 2
    // carga los pacientes disponibles, los posibles estados de la cita y la lista de horas libres calculada
    private String cargarPaso2(Model model, Cita cita, Long medicoId, LocalDate fechaDia,
                                LocalTime horaSeleccionada, String error) {
        model.addAttribute("cita", cita);
        model.addAttribute("medicoId", medicoId);
        model.addAttribute("fechaDia", fechaDia);
        model.addAttribute("estados", EstadosCita.values());
        model.addAttribute("pacientes", pacienteService.findAll());
        model.addAttribute("horaSeleccionada", horaSeleccionada);
        model.addAttribute("error", error);

        if (medicoId != null && fechaDia != null) {
            medicoService.findById(medicoId).ifPresent(medico -> {
                model.addAttribute("medico", medico);
                // obtenemos las horas disponibles para ese médico y día y como al editar la hora seleccionada actual no va a aparecer pues la añadimos        
                java.util.List<LocalTime> horas = citaService.getHorasDisponibles(medico, fechaDia);
                if (horaSeleccionada != null && !horas.contains(horaSeleccionada)) {
                    horas.add(horaSeleccionada);
                    java.util.Collections.sort(horas);
                }
                model.addAttribute("horasDisponibles", horas);
            });
        }

        return "citas/formulario-paso2";
    }
}
