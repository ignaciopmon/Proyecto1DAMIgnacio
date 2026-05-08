package com.salesianos.dam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.dam.service.ClinicService;

@Controller
public class MainController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping({ "/", "/inicio" })
    public String inicio(Model model) {
        model.addAttribute("numeroPacientes", clinicService.getPacientes().size());
        model.addAttribute("numeroMedicos", clinicService.getMedicos().size());
        model.addAttribute("numeroCitas", clinicService.getCitas().size());
        return "index";
    }

}
