package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.model.CurvePointModel;
import org.springframework.stereotype.Component;

/**
 * Convertisseur entre l'entité {@link CurvePointEntity} (persistance) et le
 * {@link CurvePointModel} (présentation), pour les points de courbe.
 */
@Component
public class CurvePointMapper {

    /**
     * Convertit une entité en Model.
     *
     * @param entity l'entité à convertir (peut être {@code null})
     * @return le Model correspondant, ou {@code null} si {@code entity} est {@code null}
     */
    public CurvePointModel toModel(CurvePointEntity entity) {
        if (entity == null) {
            return null;
        }

        return CurvePointModel.builder()
                .id(entity.getId())
                .curveId(entity.getCurveId())
                .asOfDate(entity.getAsOfDate())
                .term(entity.getTerm())
                .value(entity.getValue())
                .creationDate(entity.getCreationDate())
                .build();
    }

    /**
     * Convertit un Model en entité.
     *
     * @param model le Model à convertir (peut être {@code null})
     * @return l'entité correspondante, ou {@code null} si {@code model} est {@code null}
     */
    public CurvePointEntity toEntity(CurvePointModel model) {
        if (model == null) {
            return null;
        }

        CurvePointEntity entity = new CurvePointEntity();
        entity.setId(model.getId());
        entity.setCurveId(model.getCurveId());
        entity.setAsOfDate(model.getAsOfDate());
        entity.setTerm(model.getTerm());
        entity.setValue(model.getValue());
        entity.setCreationDate(model.getCreationDate());
        return entity;
    }
}
