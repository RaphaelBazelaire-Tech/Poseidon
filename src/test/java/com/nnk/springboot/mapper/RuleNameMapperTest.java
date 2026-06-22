package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.model.RuleNameModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RuleNameMapperTest {

    private final RuleNameMapper mapper = new RuleNameMapper();

    @Test
    public void toModelMapsFields() {
        RuleNameEntity entity = new RuleNameEntity();
        entity.setId(7);
        entity.setName("name_v");
        entity.setDescription("description_v");
        entity.setJson("json_v");
        entity.setSqlStr("sqlStr_v");

        RuleNameModel model = mapper.toModel(entity);

        assertEquals(Integer.valueOf(7), model.getId());
        assertEquals("name_v", model.getName());
        assertEquals("description_v", model.getDescription());
        assertEquals("json_v", model.getJson());
        assertEquals("sqlStr_v", model.getSqlStr());
    }

    @Test
    public void toEntityMapsFields() {
        RuleNameModel model = RuleNameModel.builder()
                .id(7)
                .name("name_v")
                .description("description_v")
                .json("json_v")
                .sqlStr("sqlStr_v")
                .build();

        RuleNameEntity entity = mapper.toEntity(model);

        assertEquals(Integer.valueOf(7), entity.getId());
        assertEquals("name_v", entity.getName());
        assertEquals("description_v", entity.getDescription());
        assertEquals("json_v", entity.getJson());
        assertEquals("sqlStr_v", entity.getSqlStr());
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

