package com.nnk.springboot.controller;

import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.service.CurvePointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CurveController.class)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurvePointService curvePointService;

    private CurvePointModel sample() {
        return CurvePointModel.builder()
                .id(1)
                .curveId(10)
                .build();
    }

    @Test
    @WithMockUser
    public void homeShouldReturnListView() throws Exception {
        when(curvePointService.findAll()).thenReturn(List.of(sample()));

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));

        verify(curvePointService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    public void addFormShouldReturnAddView() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser
    public void validateWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .param("term", "5")
                .param("value", "15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).save(any(CurvePointModel.class));
    }

    @Test
    @WithMockUser
    public void validateWithInvalidDataShouldReturnAddView() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .param("term", "not-a-number")
                .param("value", "15"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        verify(curvePointService, times(0)).save(any(CurvePointModel.class));
    }

    @Test
    @WithMockUser
    public void showUpdateFormShouldReturnUpdateView() throws Exception {
        when(curvePointService.findById(1)).thenReturn(Optional.of(sample()));

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser
    public void updateWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                .with(csrf())
                .param("term", "6")
                .param("value", "16"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).save(any(CurvePointModel.class));
    }

    @Test
    @WithMockUser
    public void deleteShouldDeleteAndRedirect() throws Exception {
        when(curvePointService.findById(1)).thenReturn(Optional.of(sample()));

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).deleteById(anyInt());
    }
}
