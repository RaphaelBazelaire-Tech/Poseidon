package com.nnk.springboot.api;

import com.nnk.springboot.model.TradeModel;
import com.nnk.springboot.service.TradeService;
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

@WebMvcTest(TradeRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TradeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeService tradeService;

    private TradeModel sample(Integer id) {
        return TradeModel.builder()
                .tradeId(id)
                .account("Account test")
                .type("Account type")
                .buyQuantity(10.0)
                .build();
    }

    @Test
    public void getAllReturns200AndList() throws Exception {
        when(tradeService.findAll()).thenReturn(List.of(sample(1)));

        mockMvc.perform(get("/api/trade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tradeId").value(1));
    }

    @Test
    public void getByIdWhenFoundReturns200() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(get("/api/trade/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tradeId").value(1))
                .andExpect(jsonPath("$.account").value("Account test"));
    }

    @Test
    public void getByIdWhenNotFoundReturns404() throws Exception {
        when(tradeService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/trade/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createReturns201AndLocation() throws Exception {
        when(tradeService.save(any(TradeModel.class))).thenReturn(sample(1));

        mockMvc.perform(post("/api/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"Account test\",\"type\":\"Account type\",\"buyQuantity\":10.0}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/trade/1")))
                .andExpect(jsonPath("$.tradeId").value(1));
    }

    @Test
    public void createWhenInvalidReturns400() throws Exception {
        mockMvc.perform(post("/api/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWhenFoundReturns200() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.of(sample(1)));
        when(tradeService.save(any(TradeModel.class))).thenReturn(sample(1));

        mockMvc.perform(put("/api/trade/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"Account test\",\"type\":\"Account type\",\"buyQuantity\":10.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tradeId").value(1));
    }

    @Test
    public void updateWhenNotFoundReturns404() throws Exception {
        when(tradeService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/trade/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"Account test\",\"type\":\"Account type\",\"buyQuantity\":10.0}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWhenFoundReturns204() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(delete("/api/trade/1"))
                .andExpect(status().isNoContent());

        verify(tradeService).deleteById(1);
    }

    @Test
    public void deleteWhenNotFoundReturns404() throws Exception {
        when(tradeService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/trade/99"))
                .andExpect(status().isNotFound());
    }
}
