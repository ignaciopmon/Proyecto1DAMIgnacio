package com.salesianos.dam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.salesianos.dam.service.ClinicService;

@Controller
public class PacientesController {

    @Autowired
    private ClinicService clinicService;

    
    @GetMapping("/pacientes")
    public String pacientes(Model model) {
        model.addAttribute("pacientes", clinicService.getPacientes());
        return "pacientes";
    }
    
}