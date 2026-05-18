package com.salesianos.dam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.dam.service.CitaService;
import com.salesianos.dam.service.MedicoService;
import com.salesianos.dam.service.PacienteService;

@Controller
public class MainController {

    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private CitaService citaService;

    @GetMapping({ "/admin" })
    public String inicio(Model model) {
        model.addAttribute("numeroPacientes", pacienteService.findAll().size());
        model.addAttribute("numeroMedicos", medicoService.findAll().size());
        model.addAttribute("numeroCitas", citaService.findAll().size());
        return "admin";
    }

    @GetMapping({ "/", "/index", "/home", "/inicio" })
    public String index(Model model) {
        return "index";
    }


}
