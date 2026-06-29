package com.nnk.springboot.api;

import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.endsWith;
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

@WebMvcTest(RuleNameRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RuleNameRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RuleNameService ruleNameService;

    private RuleNameModel sample(Integer id) {
        return RuleNameModel.builder()
                .id(id)
                .name("Rule A")
                .description("Desc")
                .build();
    }

    @Test
    public void getAllReturns200AndList() throws Exception {
        when(ruleNameService.findAll()).thenReturn(List.of(sample(1)));

        mockMvc.perform(get("/api/rulename"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void getByIdWhenFoundReturns200() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(get("/api/rulename/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rule A"));
    }

    @Test
    public void getByIdWhenNotFoundReturns404() throws Exception {
        when(ruleNameService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/rulename/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createReturns201AndLocation() throws Exception {
        when(ruleNameService.save(any(RuleNameModel.class))).thenReturn(sample(1));

        mockMvc.perform(post("/api/rulename")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Rule A\",\"description\":\"Desc\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/rulename/1")))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void updateWhenFoundReturns200() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(Optional.of(sample(1)));
        when(ruleNameService.save(any(RuleNameModel.class))).thenReturn(sample(1));

        mockMvc.perform(put("/api/rulename/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Rule A\",\"description\":\"Desc\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void updateWhenNotFoundReturns404() throws Exception {
        when(ruleNameService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/rulename/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Rule A\",\"description\":\"Desc\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWhenFoundReturns204() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(delete("/api/rulename/1"))
                .andExpect(status().isNoContent());

        verify(ruleNameService).deleteById(1);
    }

    @Test
    public void deleteWhenNotFoundReturns404() throws Exception {
        when(ruleNameService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/rulename/99"))
                .andExpect(status().isNotFound());
    }
}
