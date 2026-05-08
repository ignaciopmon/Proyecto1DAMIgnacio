package com.salesianos.dam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.salesianos.dam.service.ClinicService;

@Controller
public class CitasController {

    @Autowired
    private ClinicService clinicService;
	
    @GetMapping("/citas")
    public String citas(Model model) {
        model.addAttribute("citas", clinicService.getCitas());
        return "citas";
    }
}