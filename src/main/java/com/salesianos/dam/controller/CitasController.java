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
import com.salesianos.dam.service.CitaService;
import com.salesianos.dam.service.MedicoService;
import com.salesianos.dam.service.PacienteService;

@Controller
public class CitasController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/citas")
    public String citas(Model model) {
        model.addAttribute("citas", citaService.findAll());
        model.addAttribute("estados", EstadosCita.values());
        return "citas";
    }

    @GetMapping("/citas/nueva")
    public String showNewCitaForm(Model model,
                                  @RequestParam(value = "medicoId", required = false) Long medicoId,
                                  @RequestParam(value = "fechaDia", required = false) LocalDate fechaDia) {
        return cargarFormularioCita(model, new Cita(), medicoId, fechaDia, null, null);
    }

    @PostMapping("/citas/guardar")
    public String saveCita(@ModelAttribute("cita") Cita cita,
                           Model model,
                           @RequestParam("medicoId") Long medicoId,
                           @RequestParam("fechaDia") LocalDate fechaDia,
                           @RequestParam("hora") LocalTime hora,
                           @RequestParam(value = "pacienteId", required = false) Long pacienteId) {
        Medico medico = medicoService.findById(medicoId).orElse(null);

        if (medico == null) {
            return cargarFormularioCita(model, cita, medicoId, fechaDia, hora, "Selecciona un medico valido");
        }

        if (!citaService.horaDisponible(medico, fechaDia, hora, cita.getId())) {
            return cargarFormularioCita(model, cita, medicoId, fechaDia, hora, "Esa hora ya esta ocupada");
        }

        cita.setMedico(medico);
        cita.setFecha(LocalDateTime.of(fechaDia, hora));
        cita.setDuracionMinutos(citaService.getDuracionCita(medico));

        if (pacienteId != null) {
            cita.setPaciente(pacienteService.findById(pacienteId).orElse(null));
        }
        citaService.save(cita);
        return "redirect:/citas";
    }

    @GetMapping("/citas/editar/{id}")
    public String showEditCitaForm(@PathVariable Long id,
                                   Model model,
                                   @RequestParam(value = "medicoId", required = false) Long medicoId,
                                   @RequestParam(value = "fechaDia", required = false) LocalDate fechaDia) {
        Cita cita = citaService.findById(id).orElse(null);
        if (cita == null) {
            return "redirect:/citas";
        }

        if (medicoId == null && cita.getMedico() != null) {
            medicoId = cita.getMedico().getId();
        }

        if (fechaDia == null && cita.getFecha() != null) {
            fechaDia = cita.getFecha().toLocalDate();
        }

        LocalTime hora = cita.getFecha() != null ? cita.getFecha().toLocalTime() : null;
        return cargarFormularioCita(model, cita, medicoId, fechaDia, hora, null);
    }

    @GetMapping("/citas/eliminar/{id}")
    public String deleteCita(@PathVariable Long id) {
        citaService.deleteById(id);
        return "redirect:/citas";
    }

    @PostMapping("/citas/{id}/estado")
    public String updateEstado(@PathVariable Long id,
                               @RequestParam("estado") EstadosCita estado) {
        citaService.findById(id).ifPresent(cita -> {
            cita.setEstado(estado);
            citaService.save(cita);
        });
        return "redirect:/citas";
    }

    private String cargarFormularioCita(Model model, Cita cita, Long medicoId, LocalDate fechaDia, LocalTime hora, String error) {
        model.addAttribute("cita", cita);
        model.addAttribute("medicos", medicoService.findAll());
        model.addAttribute("pacientes", pacienteService.findAll());
        model.addAttribute("estados", EstadosCita.values());
        model.addAttribute("medicoSeleccionadoId", medicoId);
        model.addAttribute("fechaDiaSeleccionada", fechaDia);
        model.addAttribute("horaSeleccionada", hora);
        model.addAttribute("error", error);

        if (medicoId != null && fechaDia != null) {
            medicoService.findById(medicoId).ifPresent(medico ->
                    model.addAttribute("horasDisponibles",
                            citaService.obtenerHorasDisponibles(medico, fechaDia, cita.getId())));
        }

        return "formulario-cita";
    }
}
