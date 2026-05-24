package com.salesianos.dam.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.salesianos.dam.Medico;
import com.salesianos.dam.exception.EspecialidadInvalidaException;
import com.salesianos.dam.exception.MedicoSinNombreException;
import com.salesianos.dam.service.MedicoService;

@Controller
public class MedicosController {
    @Autowired
    private MedicoService medicoService;

    @GetMapping("/medicos")
    public String medicos(Model model) {
        model.addAttribute("medicos", medicoService.findAll());
        return "medicos/list";
    }

    @GetMapping("/medicos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("medico", new Medico());
        return "medicos/formulario";
    }

    @PostMapping("/medicos/nuevo/submit")
    public String procesarFormulario(@ModelAttribute("medico") Medico medico, Model model) {
        try {
            medicoService.save(medico);
            return "redirect:/medicos";
        } catch (MedicoSinNombreException | EspecialidadInvalidaException | IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "medicos/formulario";
        }
    }

    @GetMapping("/medicos/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Optional<Medico> medico = medicoService.findById(id);
        if (medico.isPresent()) {
            model.addAttribute("medico", medico.get());
            return "medicos/formulario";
        }
        return "redirect:/medicos";
    }

    @PostMapping("/medicos/eliminar/{id}")
    public String eliminarMedico(@PathVariable("id") Long id) {
        medicoService.deleteById(id);
        return "redirect:/medicos";
    }
}
