package com.nnk.springboot.config;

import com.nnk.springboot.controller.HomeController;
import com.nnk.springboot.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private UserDetails buildUser(String rawPassword) {
        return User.withUsername("user")
                .password(passwordEncoder.encode(rawPassword))
                .authorities("ROLE_USER")
                .build();
    }

    @Test
    public void homeIsPubliclyAccessible() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void protectedUrlWhenAnonymousRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/login"));
    }

    @Test
    public void loginWithInvalidCredentialsFailsAndRedirectsToError() throws Exception {
        when(customUserDetailsService.loadUserByUsername("user")).thenReturn(buildUser("password123"));

        mockMvc.perform(formLogin("/app/login").user("user").password("wrong-password"))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/app/login?error"));
    }
}
