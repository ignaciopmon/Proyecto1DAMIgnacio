package com.salesianos.dam.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.dam.service.CitaService;
import com.salesianos.dam.service.MedicoService;
import com.salesianos.dam.service.PacienteService;

@Controller
public class EstadisticasController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/estadisticas")
    public String mostrarEstadisticas(Model model) {
        List<Object[]> medicosActivos = medicoService.findMedicosMasActivos();
        List<Object[]> pacientesFrecuentes = pacienteService.findPacientesMasFrecuentes();
        List<Object[]> citasPorDia = citaService.countCitasPorDia();

        long totalCitas = citaService.findAll().size();
        long totalPacientes = pacienteService.findAll().size();
        long totalMedicos = medicoService.findAll().size();
        double totalRevenue = citaService.calculateTotalRevenue();

        model.addAttribute("medicosActivos", medicosActivos);
        model.addAttribute("pacientesFrecuentes", pacientesFrecuentes);
        model.addAttribute("citasPorDia", citasPorDia);
        model.addAttribute("totalCitas", totalCitas);
        model.addAttribute("totalPacientes", totalPacientes);
        model.addAttribute("totalMedicos", totalMedicos);
        model.addAttribute("totalRevenue", totalRevenue);

        return "estadisticas";
    }
}
