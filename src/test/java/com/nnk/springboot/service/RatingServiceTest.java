package com.nnk.springboot.service;

import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private final RatingMapper ratingMapper = new RatingMapper();
    private RatingService ratingService;

    @BeforeEach
    public void setUp() {
        ratingService = new RatingService(ratingRepository, ratingMapper);
    }

    private RatingEntity sample() {
        RatingEntity entity = new RatingEntity();
        entity.setId(1);
        entity.setMoodysRating("moodysRating_v");
        return entity;
    }

    @Test
    public void findAllReturnsModels() {
        when(ratingRepository.findAll()).thenReturn(List.of(sample()));
        List<RatingModel> result = ratingService.findAll();
        assertEquals(1, result.size());
        assertEquals("moodysRating_v", result.getFirst().getMoodysRating());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void findByIdReturnsModel() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<RatingModel> result = ratingService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("moodysRating_v", result.get().getMoodysRating());
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    public void savePersistsAndReturnsModel() {
        when(ratingRepository.save(any(RatingEntity.class))).thenReturn(sample());
        RatingModel model = RatingModel.builder().moodysRating("moodysRating_v").build();
        RatingModel saved = ratingService.save(model);
        assertEquals("moodysRating_v", saved.getMoodysRating());
        verify(ratingRepository, times(1)).save(any(RatingEntity.class));
    }

    @Test
    public void deleteByIdShouldDelegateToRepository() {
        ratingService.deleteById(1);
        verify(ratingRepository, times(1)).deleteById(1);
    }
}
