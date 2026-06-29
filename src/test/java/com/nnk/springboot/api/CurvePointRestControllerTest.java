package com.nnk.springboot.api;

import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.service.CurvePointService;
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

@WebMvcTest(CurvePointRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CurvePointRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurvePointService curvePointService;

    private CurvePointModel sample(Integer id) {
        return CurvePointModel.builder()
                .id(id)
                .curveId(10)
                .term(1.0)
                .value(2.0)
                .build();
    }

    @Test
    public void getAllReturns200AndList() throws Exception {
        when(curvePointService.findAll()).thenReturn(List.of(sample(1)));

        mockMvc.perform(get("/api/curvePoint"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void getByIdWhenFoundReturns200() throws Exception {
        when(curvePointService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(get("/api/curvePoint/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.curveId").value(10));
    }

    @Test
    public void getByIdWhenNotFoundReturns404() throws Exception {
        when(curvePointService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/curvePoint/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createReturns201AndLocation() throws Exception {
        when(curvePointService.save(any(CurvePointModel.class))).thenReturn(sample(1));

        mockMvc.perform(post("/api/curvePoint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"curveId\":10,\"term\":1.0,\"value\":2.0}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/curvePoint/1")))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void updateWhenFoundReturns200() throws Exception {
        when(curvePointService.findById(1)).thenReturn(Optional.of(sample(1)));
        when(curvePointService.save(any(CurvePointModel.class))).thenReturn(sample(1));

        mockMvc.perform(put("/api/curvePoint/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"curveId\":10,\"term\":1.0,\"value\":2.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void updateWhenNotFoundReturns404() throws Exception {
        when(curvePointService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/curvePoint/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"curveId\":10,\"term\":1.0,\"value\":2.0}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWhenFoundReturns204() throws Exception {
        when(curvePointService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(delete("/api/curvePoint/1"))
                .andExpect(status().isNoContent());

        verify(curvePointService).deleteById(1);
    }

    @Test
    public void deleteWhenNotFoundReturns404() throws Exception {
        when(curvePointService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/curvePoint/99"))
                .andExpect(status().isNotFound());
    }
}
