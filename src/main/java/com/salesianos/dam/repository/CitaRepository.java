package com.salesianos.dam.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salesianos.dam.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMedicoIdAndFechaBetween(Long medicoId, LocalDateTime inicioDia, LocalDateTime finDia);

    List<Cita> findByMedicoId(Long medicoId);

    @Query("SELECT CAST(c.fecha AS date) as dia, COUNT(c) FROM Cita c WHERE CAST(c.fecha AS date) >= CURRENT_DATE AND c.estado = com.salesianos.dam.enums.EstadosCita.PENDIENTE GROUP BY CAST(c.fecha AS date) ORDER BY COUNT(c) DESC LIMIT 7")
    List<Object[]> countCitasPorDia();
}
