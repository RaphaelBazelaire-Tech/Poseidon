package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration de Spring Security pour l'application.
 * <p>
 * Met en place une authentification par formulaire (session-based) : page de login
 * personnalisée sur {@code /app/login}, déconnexion sur {@code /app-logout}, accès
 * libre à la page d'accueil et aux ressources statiques, zone {@code /user/**}
 * réservée au rôle ADMIN, et page d'erreur 403 sur {@code /app/error}.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Encodeur de mots de passe BCrypt, exposé en bean pour être réutilisé
     * par la chaîne d'authentification et par les contrôleurs.
     *
     * @return {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Définit la chaîne de filtres de sécurité : règles d'autorisation,
     * configuration du formulaire de connexion, de la déconnexion
     * et de la gestion des accès refusés.
     *
     * @param http le constructeur de configuration HTTP fourni par Spring Security
     * @return la {@link SecurityFilterChain} configurée
     * @throws Exception si la construction de la configuration échoue
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/app/login").permitAll()
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/app/login")
                        .loginProcessingUrl("/app/login")
                        .defaultSuccessUrl("/bidList/list", true)
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/app-logout")
                        .logoutSuccessUrl("/app/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/app/error"));

        return http.build();
    }
}
