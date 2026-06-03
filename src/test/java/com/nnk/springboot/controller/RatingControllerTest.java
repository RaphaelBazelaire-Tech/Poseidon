package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    private Rating sampleRating() {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Aaa");
        rating.setSandPRating("AAA");
        rating.setFitchRating("AAA");
        rating.setOrderNumber(10);
        return rating;
    }

    /**
     * IMPORTANT : Ne fonctionnera qu'une fois le SecurityConfig activé.
     */
    @Test
    @WithMockUser
    public void homeShouldReturnListViewWithRatings() throws Exception {

        when(ratingService.findAll()).thenReturn(List.of(sampleRating()));

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

        verify(ratingService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    public void addRatingFormShouldReturnAddView() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser
    public void validateWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .param("moodysRating", "Aaa")
                .param("sandPRating", "AAA")
                .param("fitchRating", "AAA")
                .param("orderNumber", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(any(Rating.class));
    }

    @Test
    @WithMockUser
    public void validateWithInvalidDataShouldReturnAddViewAndNotSave() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .param("moodysRating", "Aaa")
                .param("orderNumber", "not-a-number"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        verify(ratingService, times(0)).save(any(Rating.class));
    }

    @Test
    @WithMockUser
    public void showUpdateFormShouldReturnUpdateViewWithRating() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.of(sampleRating()));

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));

        verify(ratingService, times(1)).findById(1);
    }

    @Test
    @WithMockUser
    public void updateRatingWithValidDataShouldSaveAndRedirect() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                .with(csrf())
                .param("moodysRating", "Bbb")
                .param("sandPRating", "BBB")
                .param("fitchRating", "BBB")
                .param("orderNumber", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(any(Rating.class));
    }

    @Test
    @WithMockUser
    public void deleteRatingShouldDeleteAndRedirect() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.of(sampleRating()));

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).deleteById(anyInt());
    }
}
