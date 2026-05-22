package com.salesianos.dam.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.salesianos.dam.Medico;

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
    public String procesarFormulario(@ModelAttribute("medico") Medico medico) {
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

    @GetMapping("/medico/citas")
    public String verMisCitas(Principal principal, Model model) {
        String username = principal.getName();
        Medico medico = medicoService.findByUsername(username).orElse(null);
        if (medico == null) {
            return "redirect:/";
        }
        model.addAttribute("medico", medico);
        model.addAttribute("citas", medico.getCitas());
        return "medicos/mis-citas";
    }

    @GetMapping("/medico/perfil")
    public String verMiPerfil(Principal principal, Model model) {
        String username = principal.getName();
        Medico medico = medicoService.findByUsername(username).orElse(null);
        if (medico == null) {
            return "redirect:/";
        }
        model.addAttribute("medico", medico);
        return "medicos/mi-perfil";
    }

    @PostMapping("/medico/perfil/guardar")
    public String guardarMiPerfil(@ModelAttribute("medico") Medico medicoForm, Principal principal) {
        String username = principal.getName();
        Medico medicoDb = medicoService.findByUsername(username).orElse(null);
        if (medicoDb != null) {
            medicoDb.setNombre(medicoForm.getNombre());
            medicoDb.setEspecialidad(medicoForm.getEspecialidad());
            medicoDb.setDuracionCitaMinutos(medicoForm.getDuracionCitaMinutos());
            medicoService.save(medicoDb);
        }
        return "redirect:/medico/citas";
    }
}
