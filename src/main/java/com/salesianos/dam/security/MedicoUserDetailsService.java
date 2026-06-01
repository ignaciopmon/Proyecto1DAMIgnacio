package com.salesianos.dam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salesianos.dam.Medico;
import com.salesianos.dam.repository.MedicoRepository;

@Service
public class MedicoUserDetailsService implements UserDetailsService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // Si el que inicia sesión es el administrador de la clínica
        if ("admin".equals(username)) {
            return User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(UserRole.ADMIN.name())
                .build();
        }

        // si no es el admin buscamos el médico en la base de datos por su usuario
        Medico medico = medicoRepository.findByUsuario(username)
            .orElseThrow(() -> new UsernameNotFoundException("No existe ningún médico con el usuario: " + username));


        return User.builder()
            .username(medico.getUsuario())
            .password(medico.getPassword())
            .roles(UserRole.MEDICO.name())
            .build();
    }
}
