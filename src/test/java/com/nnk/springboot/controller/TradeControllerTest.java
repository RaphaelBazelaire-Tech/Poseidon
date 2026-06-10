package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeService tradeService;

    private Trade sample() {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account test");
        trade.setType("Type test");
        trade.setBuyQuantity(10d);
        return trade;
    }

    /**
     * IMPORTANT : Ne fonctionnera qu'une fois le SecurityConfig activé.
     */
    @Test
    @WithMockUser
    public void homeShouldReturnListView() throws Exception {
        when(tradeService.findAll()).thenReturn(List.of(sample()));

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));

        verify(tradeService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    public void addFormShouldReturnAddView() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser
    public void validateWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .param("account", "Account test")
                .param("type", "Type test")
                .param("buyQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).save(any(Trade.class));
    }

    @Test
    @WithMockUser
    public void validateWithInvalidDataShouldReturnAddView() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .param("account", "")
                .param("type", "")
                .param("buyQuantity", "not-a-number"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

        verify(tradeService, times(0)).save(any(Trade.class));
    }

    /**
     * IMPORTANT : Ne fonctionnera qu'une fois le SecurityConfig activé.
     */
    @Test
    @WithMockUser
    public void showUpdateFormShouldReturnUpdateView() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.of(sample()));

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser
    public void updateWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                .with(csrf())
                .param("account", "Account test")
                .param("type", "Type test")
                .param("buyQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).save(any(Trade.class));
    }

    @Test
    @WithMockUser
    public void deleteShouldDeleteAndRedirect() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.of(sample()));

        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).deleteById(anyInt());
    }
}
