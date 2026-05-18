package com.salesianos.dam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianos.dam.Cita;
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
        return "citas";
    }

    @GetMapping("/citas/nueva")
    public String showNewCitaForm(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("medicos", medicoService.findAll());
        model.addAttribute("pacientes", pacienteService.findAll());
        return "formulario-cita";
    }

    @PostMapping("/citas/guardar")
    public String saveCita(@ModelAttribute("cita") Cita cita,
                           @RequestParam("medicoId") Long medicoId,
                           @RequestParam(value = "pacienteId", required = false) Long pacienteId) {
        cita.setMedico(medicoService.findById(medicoId).orElse(null));
        if (pacienteId != null) {
            cita.setPaciente(pacienteService.findById(pacienteId).orElse(null));
        }
        citaService.save(cita);
        return "redirect:/citas";
    }

    @GetMapping("/citas/editar/{id}")
    public String showEditCitaForm(@PathVariable Long id, Model model) {
        Cita cita = citaService.findById(id).orElse(null);
        if (cita == null) {
            return "redirect:/citas";
        }
        model.addAttribute("cita", cita);
        model.addAttribute("medicos", medicoService.findAll());
        model.addAttribute("pacientes", pacienteService.findAll());
        return "formulario-cita";
    }

    @GetMapping("/citas/eliminar/{id}")
    public String deleteCita(@PathVariable Long id) {
        citaService.deleteById(id);
        return "redirect:/citas";
    }
}