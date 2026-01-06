package com.shoptrust.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de Seguridad - Spring Security
 * Define las reglas de autenticación y autorización
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configura el encoder de contraseñas (BCrypt)
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el proveedor de autenticación
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura el manager de autenticación
     * @param config configuración de autenticación
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura la cadena de filtros de seguridad
     * Define qué rutas son públicas y cuáles requieren autenticación
     * @param http HttpSecurity
     * @return SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitado para pruebas (habilitar en producción)
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas (sin autenticación)
                .requestMatchers(
                    "/login",
                    "/registro",
                    "/css/**",
                    "/javascript/**",
                    "/images/**",
                    "/error"
                ).permitAll()
                // Rutas que requieren rol ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Todas las demás rutas requieren autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")                    // Página de login personalizada
                .loginProcessingUrl("/autenticar")      // URL que procesa el login
                .defaultSuccessUrl("/inicio", true)     // Redirección después de login exitoso
                .failureUrl("/login?error=true")        // Redirección si falla el login
                .usernameParameter("nombreUsuario")     // Nombre del campo usuario
                .passwordParameter("contrasena")        // Nombre del campo contraseña
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/cerrar-sesion")            // URL para cerrar sesión
                .logoutSuccessUrl("/login?logout=true") // Redirección después de logout
                .invalidateHttpSession(true)            // Invalida la sesión
                .deleteCookies("JSESSIONID")            // Elimina cookies
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/acceso-denegado")   // Página de acceso denegado
            );

        return http.build();
    }
}
