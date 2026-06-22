package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.model.CurvePointModel;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CurvePointMapperTest {

    private final CurvePointMapper mapper = new CurvePointMapper();

    @Test
    public void toModelMapsFields() {
        CurvePointEntity entity = new CurvePointEntity();
        entity.setId(7);
        entity.setCurveId(7);
        entity.setTerm(12.5);
        entity.setValue(12.5);
        entity.setAsOfDate(Timestamp.valueOf("2024-01-02 03:04:05"));

        CurvePointModel model = mapper.toModel(entity);

        assertEquals(Integer.valueOf(7), model.getId());
        assertEquals(Integer.valueOf(7), model.getCurveId());
        assertEquals(12.5, model.getTerm());
        assertEquals(12.5, model.getValue());
        assertEquals(Timestamp.valueOf("2024-01-02 03:04:05"), model.getAsOfDate());
    }

    @Test
    public void toEntityMapsFields() {
        CurvePointModel model = CurvePointModel.builder()
                .id(7)
                .curveId(7)
                .term(12.5)
                .value(12.5)
                .asOfDate(Timestamp.valueOf("2024-01-02 03:04:05"))
                .build();

        CurvePointEntity entity = mapper.toEntity(model);

        assertEquals(Integer.valueOf(7), entity.getId());
        assertEquals(Integer.valueOf(7), entity.getCurveId());
        assertEquals(12.5, entity.getTerm());
        assertEquals(12.5, entity.getValue());
        assertEquals(Timestamp.valueOf("2024-01-02 03:04:05"), entity.getAsOfDate());
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
