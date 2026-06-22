package com.nnk.springboot.controller;

import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RuleNameService ruleNameService;

    private RuleNameModel sample() {
        return RuleNameModel.builder()
                .id(1)
                .name("Rule Name")
                .build();
    }

    @Test
    @WithMockUser
    public void homeShouldReturnListView() throws Exception {
        when(ruleNameService.findAll()).thenReturn(List.of(sample()));

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"));

        verify(ruleNameService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    public void addFormShouldReturnAddView() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser
    public void validateWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .param("name", "Rule Name")
                .param("description", "Description")
                .param("json", "{}")
                .param("template", "Template")
                .param("sqlStr", "SELECT 1")
                .param("sqlPart", "WHERE 1=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).save(any(RuleNameModel.class));
    }

    @Test
    @WithMockUser
    public void showUpdateFormShouldReturnUpdateView() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(Optional.of(sample()));
        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @WithMockUser
    public void updateWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                .with(csrf())
                .param("name", "Updated")
                .param("description", "Updated description")
                .param("json", "{}")
                .param("template", "Template")
                .param("sqlStr", "SELECT 2")
                .param("sqlPart", "WHERE 2=2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).save(any(RuleNameModel.class));
    }

    @Test
    @WithMockUser
    public void deleteShouldDeleteAndRedirect() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(Optional.of(sample()));

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).deleteById(anyInt());
    }
}
