package com.salesianos.dam.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianos.dam.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMedicoIdAndFechaBetween(Long medicoId, LocalDateTime inicioDia, LocalDateTime finDia);
}
