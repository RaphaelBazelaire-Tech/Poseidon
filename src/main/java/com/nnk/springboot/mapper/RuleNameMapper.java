package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.model.RuleNameModel;
import org.springframework.stereotype.Component;

@Component
public class RuleNameMapper {

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
