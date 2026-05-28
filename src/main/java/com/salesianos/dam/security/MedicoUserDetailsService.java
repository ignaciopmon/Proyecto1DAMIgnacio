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

// Esta clase sirve para que Spring Security busque los usuarios en nuestra base de datos
// en lugar de usar usuarios fijos en memoria.
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
                .password(passwordEncoder.encode("admin")) // Ciframos su contraseña sobre la marcha
                .roles(UserRole.ADMIN.name())
                .build();
        }

        // Si no es el admin, buscamos el médico en la base de datos por su nombre de usuario
        Medico medico = medicoRepository.findByUsuario(username)
            .orElseThrow(() -> new UsernameNotFoundException("No existe ningún médico con el usuario: " + username));

        // Devolvemos el usuario con su contraseña cifrada de la base de datos y su rol correspondiente
        return User.builder()
            .username(medico.getUsuario())
            .password(medico.getPassword()) // La contraseña que ya está guardada cifrada en la base de datos
            .roles(UserRole.MEDICO.name())
            .build();
    }
}
