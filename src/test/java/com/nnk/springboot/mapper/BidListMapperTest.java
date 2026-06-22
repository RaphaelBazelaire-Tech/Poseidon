package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.BidListEntity;
import com.nnk.springboot.model.BidListModel;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BidListMapperTest {

    private final BidListMapper mapper = new BidListMapper();

    @Test
    public void toModelMapsFields() {
        BidListEntity entity = new BidListEntity();
        entity.setBidListId(7);
        entity.setAccount("account_v");
        entity.setType("type_v");
        entity.setBidQuantity(12.5);
        entity.setBidListDate(Timestamp.valueOf("2024-01-02 03:04:05"));

        BidListModel model = mapper.toModel(entity);

        assertEquals(Integer.valueOf(7), model.getBidListId());
        assertEquals("account_v", model.getAccount());
        assertEquals("type_v", model.getType());
        assertEquals(12.5, model.getBidQuantity());
        assertEquals(Timestamp.valueOf("2024-01-02 03:04:05"), model.getBidListDate());
    }

    @Test
    public void toEntityMapsFields() {
        BidListModel model = BidListModel.builder()
                .bidListId(7)
                .account("account_v")
                .type("type_v")
                .bidQuantity(12.5)
                .bidListDate(Timestamp.valueOf("2024-01-02 03:04:05"))
                .build();

        BidListEntity entity = mapper.toEntity(model);

        assertEquals(Integer.valueOf(7), entity.getBidListId());
        assertEquals("account_v", entity.getAccount());
        assertEquals("type_v", entity.getType());
        assertEquals(12.5, entity.getBidQuantity());
        assertEquals(Timestamp.valueOf("2024-01-02 03:04:05"), entity.getBidListDate());
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
