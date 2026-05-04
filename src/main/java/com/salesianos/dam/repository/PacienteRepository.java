package com.salesianos.dam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianos.dam.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, String> {

}
