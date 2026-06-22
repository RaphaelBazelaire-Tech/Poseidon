package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.model.RuleNameModel;
import org.springframework.stereotype.Component;

/**
 * Convertisseur entre l'entité {@link RuleNameEntity} (persistance) et le
 * {@link RuleNameModel} (présentation), pour les règles métier.
 */
@Component
public class RuleNameMapper {

    /**
     * Convertit une entité en Model.
     *
     * @param entity l'entité à convertir (peut être {@code null})
     * @return le Model correspondant, ou {@code null} si {@code entity} est {@code null}
     */
    public RuleNameModel toModel(RuleNameEntity entity) {
        if (entity == null) {
            return null;
        }

        return RuleNameModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .json(entity.getJson())
                .template(entity.getTemplate())
                .sqlStr(entity.getSqlStr())
                .sqlPart(entity.getSqlPart())
                .build();
    }

    /**
     * Convertit un Model en entité.
     *
     * @param model le Model à convertir (peut être {@code null})
     * @return l'entité correspondante, ou {@code null} si {@code model} est {@code null}
     */
    public RuleNameEntity toEntity(RuleNameModel model) {
        if (model == null) {
            return null;
        }

        RuleNameEntity entity = new RuleNameEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setJson(model.getJson());
        entity.setTemplate(model.getTemplate());
        entity.setSqlStr(model.getSqlStr());
        entity.setSqlPart(model.getSqlPart());
        return entity;
    }
}
