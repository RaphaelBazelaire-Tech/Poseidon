package com.nnk.springboot.api;

import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.service.RatingService;
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

@WebMvcTest(RatingRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class RatingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    private RatingModel sample(Integer id) {
        return RatingModel.builder()
                .id(id)
                .moodysRating("Aaa")
                .orderNumber(1)
                .build();
    }

    @Test
    public void getAllReturns200AndList() throws Exception {
        when(ratingService.findAll()).thenReturn(List.of(sample(1)));

        mockMvc.perform(get("/api/rating"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void getByIdWhenFoundReturns200() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(get("/api/rating/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.moodysRating").value("Aaa"));
    }

    @Test
    public void getByIdWhenNotFoundReturns404() throws Exception {
        when(ratingService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/rating/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createReturns201AndLocation() throws Exception {
        when(ratingService.save(any(RatingModel.class))).thenReturn(sample(1));

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"moodysRating\":\"Aaa\",\"orderNumber\":1}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/rating/1")))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void updateWhenFoundReturns200() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.of(sample(1)));
        when(ratingService.save(any(RatingModel.class))).thenReturn(sample(1));

        mockMvc.perform(put("/api/rating/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"moodysRating\":\"Aaa\",\"orderNumber\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void updateWhenNotFoundReturns404() throws Exception {
        when(ratingService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/rating/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"moodysRating\":\"Aaa\",\"orderNumber\":1}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWhenFoundReturns204() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.of(sample(1)));

        mockMvc.perform(delete("/api/rating/1"))
                .andExpect(status().isNoContent());

        verify(ratingService).deleteById(1);
    }

    @Test
    public void deleteWhenNotFoundReturns404() throws Exception {
        when(ratingService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/rating/99"))
                .andExpect(status().isNotFound());
    }
}
