package com.salesianos.dam.service;

import org.springframework.stereotype.Service;
import com.salesianos.dam.Cita;
import com.salesianos.dam.repository.CitaRepository;

@Service
public class CitaServiceImpl extends BaseServiceImpl<Cita, Long, CitaRepository> implements CitaService {
}
