package com.salesianos.dam.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.salesianos.dam.Medico;

@Repository
public class MedicoRepository {

        public List<Medico> getMedicos() {
                return Arrays.asList(
                                new Medico("M001", "Medicina general", "Dra. Laura Martin"),
                                new Medico("M002", "Pediatria", "Dr. Carlos Ruiz"),
                                new Medico("M003", "Traumatologia", "Dra. Elena Torres"));
                
        }

}
