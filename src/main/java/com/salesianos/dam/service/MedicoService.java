package com.salesianos.dam.service;

import org.springframework.stereotype.Service;

import com.salesianos.dam.Medico;
import com.salesianos.dam.repository.MedicoRepository;

@Service
public class MedicoService extends BaseServiceImpl<Medico, Long, MedicoRepository> {
}
