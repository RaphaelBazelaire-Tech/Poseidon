package com.nnk.springboot.service;

import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private UserEntity sample() {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("$2a$10$encodedHash");
        user.setFullname("Administrator");
        user.setRole("ADMIN");
        return user;
    }

    @Test
    public void loadUserByUsernameWhenUserExistsReturnsUserDetailsWithRole() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(sample()));

        UserDetails details = customUserDetailsService.loadUserByUsername("admin");

        assertEquals("admin", details.getUsername());
        assertEquals("$2a$10$encodedHash", details.getPassword());
        assertTrue(details.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    public void loadUserByUsernameWhenUserMissingThrowsUsernameNotFound() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("ghost"));
    }
}
