package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.model.RatingModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RatingMapperTest {

    private final RatingMapper mapper = new RatingMapper();

    @Test
    public void toModelMapsFields() {
        RatingEntity entity = new RatingEntity();
        entity.setId(7);
        entity.setMoodysRating("moodysRating_v");
        entity.setSandPRating("sandPRating_v");
        entity.setFitchRating("fitchRating_v");
        entity.setOrderNumber(7);

        RatingModel model = mapper.toModel(entity);

        assertEquals(Integer.valueOf(7), model.getId());
        assertEquals("moodysRating_v", model.getMoodysRating());
        assertEquals("sandPRating_v", model.getSandPRating());
        assertEquals("fitchRating_v", model.getFitchRating());
        assertEquals(Integer.valueOf(7), model.getOrderNumber());
    }

    @Test
    public void toEntityMapsFields() {
        RatingModel model = RatingModel.builder()
                .id(7)
                .moodysRating("moodysRating_v")
                .sandPRating("sandPRating_v")
                .fitchRating("fitchRating_v")
                .orderNumber(7)
                .build();

        RatingEntity entity = mapper.toEntity(model);

        assertEquals(Integer.valueOf(7), entity.getId());
        assertEquals("moodysRating_v", entity.getMoodysRating());
        assertEquals("sandPRating_v", entity.getSandPRating());
        assertEquals("fitchRating_v", entity.getFitchRating());
        assertEquals(Integer.valueOf(7), entity.getOrderNumber());
    }

    @Test
    public void toModelNullReturnsNull() {
        assertNull(mapper.toModel(null));
    }

    @Test
    public void toEntityNullReturnsNull() {
        assertNull(mapper.toEntity(null));
    }
}
