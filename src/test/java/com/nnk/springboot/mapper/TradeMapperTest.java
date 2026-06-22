package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.model.TradeModel;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TradeMapperTest {

    private final TradeMapper mapper = new TradeMapper();

    @Test
    public void toModelMapsFields() {
        TradeEntity entity = new TradeEntity();
        entity.setTradeId(7);
        entity.setAccount("account_v");
        entity.setType("type_v");
        entity.setBuyQuantity(12.5);
        entity.setTradeDate(Timestamp.valueOf("2024-01-02 03:04:05"));

        TradeModel model = mapper.toModel(entity);

        assertEquals(Integer.valueOf(7), model.getTradeId());
        assertEquals("account_v", model.getAccount());
        assertEquals("type_v", model.getType());
        assertEquals(12.5, model.getBuyQuantity());
        assertEquals(Timestamp.valueOf("2024-01-02 03:04:05"), model.getTradeDate());
    }

    @Test
    public void toEntityMapsFields() {
        TradeModel model = TradeModel.builder()
                .tradeId(7)
                .account("account_v")
                .type("type_v")
                .buyQuantity(12.5)
                .tradeDate(Timestamp.valueOf("2024-01-02 03:04:05"))
                .build();

        TradeEntity entity = mapper.toEntity(model);

        assertEquals(Integer.valueOf(7), entity.getTradeId());
        assertEquals("account_v", entity.getAccount());
        assertEquals("type_v", entity.getType());
        assertEquals(12.5, entity.getBuyQuantity());
        assertEquals(Timestamp.valueOf("2024-01-02 03:04:05"), entity.getTradeDate());
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
