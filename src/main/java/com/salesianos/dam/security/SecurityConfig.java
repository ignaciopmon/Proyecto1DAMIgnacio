package com.salesianos.dam.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/", "/index", "/home", "/inicio", "/login", "/error", "/favicon.svg", "/css/**", "/js/**", "/img/**").permitAll()
                // tanto el médico como el admin pueden ver y editar el perfil de médico
                .requestMatchers("/medicos/perfil", "/medicos/perfil/submit").hasAnyRole(UserRole.ADMIN.name(), UserRole.MEDICO.name())
                .requestMatchers(
                    "/medicos/editar/**", "/medicos/eliminar/**",
                    "/pacientes/editar/**", "/pacientes/eliminar/**",
                    "/citas/editar/**", "/citas/eliminar/**",
                    "/estadisticas"
                ).hasRole(UserRole.ADMIN.name())
                // ver el listado de médicos y sus detalles solo lo puede hacer el admin
                .requestMatchers("/medicos", "/medicos/**").hasRole(UserRole.ADMIN.name())
                .requestMatchers("/pacientes", "/pacientes/nuevo", "/pacientes/nuevo/submit").hasAnyRole(UserRole.ADMIN.name(), UserRole.MEDICO.name())
                // gestionar las citas lo pueden hacer tanto el admin como el médico
                .requestMatchers("/citas", "/citas/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.MEDICO.name())
                .anyRequest().authenticated()
            )
    
            .formLogin(form -> form
                // página personalizada
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout(logout -> logout
                // tras cerrar sesión volvemos a la portada
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // creamos el usuario "medico" con contraseña "medico" y su rol 
        UserDetails medico = User.builder()
            .username("medico")
            .password(passwordEncoder.encode("medico"))
            .roles(UserRole.MEDICO.name())
            .build();
            
        // creamos el usuario "admin" con contraseña "admin"
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .roles(UserRole.ADMIN.name())
            .build();

        return new InMemoryUserDetailsManager(medico, admin);
    }
}
