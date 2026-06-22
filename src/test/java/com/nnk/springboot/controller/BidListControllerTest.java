package com.nnk.springboot.controller;

import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.service.BidListService;
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

@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BidListService bidListService;

    private BidListModel sample() {
        return BidListModel.builder()
                .bidListId(1)
                .account("Account Test")
                .build();
    }

    @Test
    @WithMockUser
    public void homeShouldReturnListView() throws Exception {
        when(bidListService.findAll()).thenReturn(List.of(sample()));
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(bidListService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    public void addFormShouldReturnAddView() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser
    public void validateWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .param("account", "Account Test")
                .param("type", "Type Test")
                .param("bidQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).save(any(BidListModel.class));
    }

    @Test
    @WithMockUser
    public void validateWithInvalidDataShouldReturnAddView() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .param("account", "")
                .param("type", "")
                .param("bidQuantity", "not-a-number"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        verify(bidListService, times(0)).save(any(BidListModel.class));
    }

    @Test
    @WithMockUser
    public void showUpdateFormShouldReturnUpdateView() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.of(sample()));

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser
    public void updateWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/bidList/update/1")
                .with(csrf())
                .param("account", "Account Test")
                .param("type", "Type Test")
                .param("bidQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).save(any(BidListModel.class));
    }

    @Test
    @WithMockUser
    public void deleteShouldDeleteAndRedirect() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.of(sample()));

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).deleteById(anyInt());
    }
}
