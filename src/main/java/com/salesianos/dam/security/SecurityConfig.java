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
                .requestMatchers("/", "/index", "/home", "/inicio", "/login", "/error", "/css/**", "/js/**", "/img/**").permitAll()
                // Solo ADMIN: panel admin, editar y eliminar
                .requestMatchers("/admin", "/admin/**").hasRole(UserRole.ADMIN.name())
                .requestMatchers("/**/editar/**", "/**/eliminar/**").hasRole(UserRole.ADMIN.name())
                // ADMIN y MEDICO: ver listados y crear
                .requestMatchers("/medicos", "/medicos/nuevo", "/medicos/nuevo/submit").hasAnyRole(UserRole.ADMIN.name(), UserRole.MEDICO.name())
                .requestMatchers("/pacientes", "/pacientes/nuevo", "/pacientes/nuevo/submit").hasAnyRole(UserRole.ADMIN.name(), UserRole.MEDICO.name())
                .requestMatchers("/citas", "/citas/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.MEDICO.name())
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails medico = User.builder()
            .username("medico")
            .password(passwordEncoder.encode("medico"))
            .roles(UserRole.MEDICO.name())
            .build();
            
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .roles(UserRole.ADMIN.name())
            .build();

        return new InMemoryUserDetailsManager(medico, admin);
    }
}
