package com.salesianos.dam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.dam.service.ClinicService;

@Controller
public class MedicosController {
    @Autowired
    private ClinicService clinicService;

    @GetMapping("/medicos")
    public String medicos(Model model) {
        model.addAttribute("medicos", clinicService.getMedicos());
        return "medicos";
    }
}
