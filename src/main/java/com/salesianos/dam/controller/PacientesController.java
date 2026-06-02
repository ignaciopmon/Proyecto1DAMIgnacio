package com.salesianos.dam.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.salesianos.dam.Paciente;
import com.salesianos.dam.service.PacienteService;
import jakarta.validation.Valid;

@Controller
public class PacientesController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/pacientes")
    public String pacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.findAll());
        return "pacientes/list";
    }

    @GetMapping("/pacientes/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/formulario";
    }

    @PostMapping("/pacientes/nuevo/submit")
    public String procesarFormulario(@Valid @ModelAttribute("paciente") Paciente paciente) {
        pacienteService.save(paciente);
        return "redirect:/pacientes";
    }

    @GetMapping("/pacientes/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isPresent()) {
            model.addAttribute("paciente", paciente.get());
            return "pacientes/formulario";
        }
        return "redirect:/pacientes";
    }

    @GetMapping("/pacientes/eliminar/{id}")
    public String eliminarPaciente(@PathVariable("id") Long id) {
        pacienteService.deleteById(id);
        return "redirect:/pacientes";
    }
}