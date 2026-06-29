package com.nnk.springboot.api;

import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private BCryptPasswordEncoder passwordEncoder;

    private UserModel sample(Integer id) {
        return UserModel.builder()
                .id(id)
                .username("jdoe")
                .password("hashed-secret")
                .fullname("John Doe")
                .role("USER")
                .build();
    }

    private static final String VALID_JSON =
            "{\"username\":\"jdoe\",\"password\":\"Secret123!\",\"fullname\":\"John Doe\",\"role\":\"USER\"}";

    @Test
    public void getAllReturns200AndHidesPassword() throws Exception {
        when(userService.findAll()).thenReturn(List.of(sample(1)));

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("jdoe"))
                .andExpect(jsonPath("$[0].password").value(nullValue()));
    }

    @Test
    public void getByIdWhenFoundReturns200AndHidesPassword() throws Exception {
        when(userService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jdoe"))
                .andExpect(jsonPath("$.password").value(nullValue()));
    }

    @Test
    public void getByIdWhenNotFoundReturns404() throws Exception {
        when(userService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/user/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createEncodesPasswordAndReturns201() throws Exception {
        when(passwordEncoder.encode(any())).thenReturn("ENCODED");
        when(userService.save(any(UserModel.class))).thenReturn(sample(1));

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/user/1")))
                .andExpect(jsonPath("$.username").value("jdoe"))
                .andExpect(jsonPath("$.password").value(nullValue()));

        verify(passwordEncoder).encode("Secret123!");
    }

    @Test
    public void createWhenInvalidReturns400() throws Exception {
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWhenFoundEncodesPasswordAndReturns200() throws Exception {
        when(userService.findById(1)).thenReturn(Optional.of(sample(1)));
        when(passwordEncoder.encode(any())).thenReturn("ENCODED");
        when(userService.save(any(UserModel.class))).thenReturn(sample(1));

        mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value(nullValue()));

        verify(passwordEncoder).encode("Secret123!");
    }

    @Test
    public void updateWhenNotFoundReturns404() throws Exception {
        when(userService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/user/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWhenFoundReturns204() throws Exception {
        when(userService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNoContent());

        verify(userService).deleteById(1);
    }

    @Test
    public void deleteWhenNotFoundReturns404() throws Exception {
        when(userService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/user/99"))
                .andExpect(status().isNotFound());
    }
}
