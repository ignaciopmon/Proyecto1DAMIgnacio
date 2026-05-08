package com.salesianos.dam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianos.dam.Cita;
import com.salesianos.dam.Medico;
import com.salesianos.dam.Paciente;
import com.salesianos.dam.repository.CitaRepository;
import com.salesianos.dam.repository.MedicoRepository;
import com.salesianos.dam.repository.PacienteRepository;

@Service
public class ClinicService {

        @Autowired
        private PacienteRepository pacienteRepository;

        @Autowired
        private MedicoRepository medicoRepository;

        @Autowired
        private CitaRepository citaRepository;

        public List<Paciente> getPacientes() {
                return pacienteRepository.getPacientes();
        }

        public List<Medico> getMedicos() {
                return medicoRepository.getMedicos();
        }

        public List<Cita> getCitas() {
                return citaRepository.getCitas();
        }
}
