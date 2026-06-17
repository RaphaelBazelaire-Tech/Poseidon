package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.model.CurvePointModel;
import org.springframework.stereotype.Component;

@Component
public class CurvePointMapper {

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
