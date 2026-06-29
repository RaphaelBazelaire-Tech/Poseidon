package com.nnk.springboot.api;

import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.service.BidListService;
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

@WebMvcTest(BidListRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class BidListRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BidListService bidListService;

    private BidListModel sample(Integer id) {
        return BidListModel.builder()
                .bidListId(id)
                .account("Account test")
                .type("Account type")
                .bidQuantity(15.0)
                .build();
    }

    @Test
    public void getAllReturns200AndList() throws Exception {
        when(bidListService.findAll()).thenReturn(List.of(sample(1)));

        mockMvc.perform(get("/api/bidlist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bidListId").value(1));
    }

    @Test
    public void getByIdWhenFoundReturns200() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(get("/api/bidlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bidListId").value(1))
                .andExpect(jsonPath("$.account").value("Account test"));
    }

    @Test
    public void getByIdWhenNotFoundReturns404() throws Exception {
        when(bidListService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bidlist/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createReturns201AndLocation() throws Exception {
        when(bidListService.save(any(BidListModel.class))).thenReturn(sample(1));

        mockMvc.perform(post("/api/bidlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"Account test\",\"type\":\"Account type\",\"bidQuantity\":15.0}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/bidlist/1")))
                .andExpect(jsonPath("$.bidListId").value(1));
    }

    @Test
    public void createWhenInvalidReturns400() throws Exception {
        mockMvc.perform(post("/api/bidlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWhenFoundReturns200() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.of(sample(1)));
        when(bidListService.save(any(BidListModel.class))).thenReturn(sample(1));

        mockMvc.perform(put("/api/bidlist/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"Account test\",\"type\":\"Account type\",\"bidQuantity\":15.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bidListId").value(1));
    }

    @Test
    public void updateWhenNotFoundReturns404() throws Exception {
        when(bidListService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/bidlist/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"Account test\",\"type\":\"Account type\",\"bidQuantity\":15.0}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWhenFoundReturns204() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(delete("/api/bidlist/1"))
                .andExpect(status().isNoContent());

        verify(bidListService).deleteById(1);
    }

    @Test
    public void deleteWhenNotFoundReturns404() throws Exception {
        when(bidListService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/bidlist/99"))
                .andExpect(status().isNotFound());
    }
}
