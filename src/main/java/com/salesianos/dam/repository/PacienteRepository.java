package com.salesianos.dam.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salesianos.dam.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT p, COUNT(c) FROM Paciente p JOIN p.citas c GROUP BY p ORDER BY COUNT(c) DESC LIMIT 5")
    List<Object[]> findPacientesMasFrecuentes();

}
