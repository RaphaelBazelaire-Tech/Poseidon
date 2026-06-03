package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
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

    @Test
    public void findAllShouldReturnAllRatings() {
        when(ratingRepository.findAll()).thenReturn(List.of(sampleRating()));

        List<Rating> result = ratingService.findAll();

        assertEquals(1, result.size());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void findByIdShouldReturnRating() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(sampleRating()));

        Optional<Rating> result = ratingService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Aaa", result.get().getMoodysRating());
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    public void saveShouldDelegateToRepository() {
        Rating rating = sampleRating();
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.save(rating);

        assertEquals(rating, result);
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    public void deleteByIdShouldDelegateToRepository() {
        ratingService.deleteById(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }
}
