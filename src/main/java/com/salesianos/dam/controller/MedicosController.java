package com.salesianos.dam.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;
import com.salesianos.dam.Medico;
import com.salesianos.dam.service.MedicoService;
import jakarta.validation.Valid;

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
    public String procesarFormulario(@Valid @ModelAttribute("medico") Medico medico) {
        medicoService.save(medico);
        return "redirect:/medicos";
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

    @GetMapping("/medicos/perfil")
    public String mostrarPerfil(Model model, java.security.Principal principal) {
        Medico medico = null;
        if (principal != null) {
            medico = medicoService.findByUsuario(principal.getName());
        }
        if (medico == null) {
            Optional<Medico> firstMedico = medicoService.findAll().stream().findFirst();
            if (firstMedico.isPresent()) {
                medico = firstMedico.get();
            }
        }
        if (medico != null) {
            model.addAttribute("medico", medico);
            return "medicos/perfil";
        }
        return "redirect:/";
    }

    @PostMapping("/medicos/perfil/submit")
    public String guardarPerfil(@Valid @ModelAttribute("medico") Medico medico, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "medicos/perfil";
        }
        medicoService.save(medico);
        return "redirect:/medicos/perfil?saved=true";
    }

}
