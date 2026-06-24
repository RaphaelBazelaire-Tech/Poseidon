package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration de Spring Security pour l'application.
 * <p>
 * Deux chaînes de filtres cohabitent :
 * <ul>
 *   <li>une chaîne <b>API REST</b> ({@code /api/**}) : sans état, authentification
 *       HTTP Basic, protection CSRF désactivée, renvoyant un {@code 401} en cas
 *       d'absence d'authentification ;</li>
 *   <li>une chaîne <b>web</b> (tout le reste) : authentification par formulaire
 *       (session), déconnexion, page d'erreur 403, zone {@code /user/**} réservée
 *       au rôle ADMIN.</li>
 * </ul>
 * Les deux partagent le même {@link BCryptPasswordEncoder} et le même
 * {@code UserDetailsService} (câblés automatiquement par Spring Security).
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
     * Chaîne de sécurité dédiée à l'API REST ({@code /api/**}).
     * <p>
     * Sans état (pas de session HTTP), authentification HTTP Basic, CSRF désactivé
     * (inutile pour une API consommée hors navigateur). Toute requête doit être
     * authentifiée ; une requête anonyme reçoit un {@code 401 Unauthorized} avec
     * l'en-tête {@code WWW-Authenticate: Basic}.
     *
     * @param http le constructeur de configuration HTTP fourni par Spring Security
     * @return la {@link SecurityFilterChain} de l'API
     * @throws Exception si la construction de la configuration échoue
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {

        http.securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Chaîne de sécurité du site web (toutes les routes hors {@code /api/**}).
     * <p>
     * Authentification par formulaire (session) : règles d'autorisation, page de
     * connexion personnalisée, déconnexion et gestion des accès refusés.
     *
     * @param http le constructeur de configuration HTTP fourni par Spring Security
     * @return la {@link SecurityFilterChain} du site web
     * @throws Exception si la construction de la configuration échoue
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/app/login")
                        .permitAll()
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())

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
