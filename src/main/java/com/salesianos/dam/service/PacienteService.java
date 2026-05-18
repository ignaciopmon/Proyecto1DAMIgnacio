package com.salesianos.dam.service;

import org.springframework.stereotype.Service;
import com.salesianos.dam.Paciente;
import com.salesianos.dam.repository.PacienteRepository;

@Service
public class PacienteServiceImpl extends BaseServiceImpl<Paciente, Long, PacienteRepository> implements PacienteService {
}
