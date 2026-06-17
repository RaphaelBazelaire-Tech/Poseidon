package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.model.RatingModel;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public RatingModel toModel(RatingEntity entity) {
        if (entity == null) {
            return null;
        }

        return RatingModel.builder()
                .id(entity.getId())
                .moodysRating(entity.getMoodysRating())
                .sandPRating(entity.getSandPRating())
                .fitchRating(entity.getFitchRating())
                .orderNumber(entity.getOrderNumber())
                .build();
    }

    public RatingEntity toEntity(RatingModel model) {
        if (model == null) {
            return null;
        }

        RatingEntity entity = new RatingEntity();
        entity.setId(model.getId());
        entity.setMoodysRating(model.getMoodysRating());
        entity.setSandPRating(model.getSandPRating());
        entity.setFitchRating(model.getFitchRating());
        entity.setOrderNumber(model.getOrderNumber());
        return entity;
    }
}
