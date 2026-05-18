package com.salesianos.dam.service;

import org.springframework.stereotype.Service;

import com.salesianos.dam.Paciente;
import com.salesianos.dam.repository.PacienteRepository;

@Service
public class PacienteService extends BaseServiceImpl<Paciente, Long, PacienteRepository> {
}
