package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.model.RatingModel;
import org.springframework.stereotype.Component;

/**
 * Convertisseur entre l'entité {@link RatingEntity} (persistance) et le
 * {@link RatingModel} (présentation), pour les notations financières.
 */
@Component
public class RatingMapper {

    /**
     * Convertit une entité en Model.
     *
     * @param entity l'entité à convertir (peut être {@code null})
     * @return le Model correspondant, ou {@code null} si {@code entity} est {@code null}
     */
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

    /**
     * Convertit un Model en entité.
     *
     * @param model le Model à convertir (peut être {@code null})
     * @return l'entité correspondante, ou {@code null} si {@code model} est {@code null}
     */
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
