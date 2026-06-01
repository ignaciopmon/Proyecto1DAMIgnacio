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
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/", "/index", "/home", "/inicio", "/login", "/error", "/favicon.svg", "/css/**", "/js/**", "/img/**", "/h2-console/**").permitAll()
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

            // configuración de logout personalizada para que se haga con GET a /logout y redirija a /login?logout
            .logout(logout -> logout
                .logoutRequestMatcher(PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            
            // Permitir la consola H2
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}
