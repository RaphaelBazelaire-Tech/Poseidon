package com.nnk.springboot.service;

import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation de {@link UserDetailsService} chargeant les utilisateurs
 * depuis la base via le {@link UserRepository}.
 * <p>
 * C'est la « méthode d'authentification » côté back-end : Spring Security l'utilise
 * pour récupérer l'utilisateur lors de la connexion. Le rôle stocké en base
 * (ex. {@code ADMIN}) est préfixé par {@code ROLE_} pour produire l'autorité Spring.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * @param userRepository repository d'accès aux utilisateurs, injecté par Spring
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Charge un utilisateur par son nom d'utilisateur pour l'authentification.
     *
     * @param username le nom d'utilisateur fourni lors de la connexion
     * @return les {@link UserDetails} (identifiants + autorité) de l'utilisateur
     * @throws UsernameNotFoundException si aucun utilisateur ne porte ce nom
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                .build();
    }
}
