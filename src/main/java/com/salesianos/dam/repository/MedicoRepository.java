package com.salesianos.dam.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salesianos.dam.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    java.util.Optional<Medico> findByUsuario(String usuario);

    @Query("SELECT m, COUNT(c) FROM Medico m LEFT JOIN m.citas c GROUP BY m ORDER BY COUNT(c) DESC")
    List<Object[]> findMedicosMasActivos();

}
