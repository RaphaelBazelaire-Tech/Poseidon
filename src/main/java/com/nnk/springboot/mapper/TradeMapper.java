package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.model.TradeModel;
import org.springframework.stereotype.Component;

/**
 * Convertisseur entre l'entité {@link TradeEntity} (persistance) et le
 * {@link TradeModel} (présentation), pour les transactions (trades).
 */
@Component
public class TradeMapper {

    /**
     * Convertit une entité en Model.
     *
     * @param entity l'entité à convertir (peut être {@code null})
     * @return le Model correspondant, ou {@code null} si {@code entity} est {@code null}
     */
    public TradeModel toModel(TradeEntity entity) {
        if (entity == null) {
            return null;
        }

        return TradeModel.builder()
                .tradeId(entity.getTradeId())
                .account(entity.getAccount())
                .type(entity.getType())
                .buyQuantity(entity.getBuyQuantity())
                .sellQuantity(entity.getSellQuantity())
                .buyPrice(entity.getBuyPrice())
                .sellPrice(entity.getSellPrice())
                .benchmark(entity.getBenchmark())
                .tradeDate(entity.getTradeDate())
                .security(entity.getSecurity())
                .status(entity.getStatus())
                .trader(entity.getTrader())
                .book(entity.getBook())
                .creationName(entity.getCreationName())
                .creationDate(entity.getCreationDate())
                .revisionName(entity.getRevisionName())
                .revisionDate(entity.getRevisionDate())
                .dealName(entity.getDealName())
                .dealType(entity.getDealType())
                .sourceListId(entity.getSourceListId())
                .side(entity.getSide())
                .build();
    }

    /**
     * Convertit un Model en entité.
     *
     * @param model le Model à convertir (peut être {@code null})
     * @return l'entité correspondante, ou {@code null} si {@code model} est {@code null}
     */
    public TradeEntity toEntity(TradeModel model) {
        if (model == null) {
            return null;
        }

        TradeEntity entity = new TradeEntity();
        entity.setTradeId(model.getTradeId());
        entity.setAccount(model.getAccount());
        entity.setType(model.getType());
        entity.setBuyQuantity(model.getBuyQuantity());
        entity.setSellQuantity(model.getSellQuantity());
        entity.setBuyPrice(model.getBuyPrice());
        entity.setSellPrice(model.getSellPrice());
        entity.setBenchmark(model.getBenchmark());
        entity.setTradeDate(model.getTradeDate());
        entity.setSecurity(model.getSecurity());
        entity.setStatus(model.getStatus());
        entity.setTrader(model.getTrader());
        entity.setBook(model.getBook());
        entity.setCreationName(model.getCreationName());
        entity.setCreationDate(model.getCreationDate());
        entity.setRevisionName(model.getRevisionName());
        entity.setRevisionDate(model.getRevisionDate());
        entity.setDealName(model.getDealName());
        entity.setDealType(model.getDealType());
        entity.setSourceListId(model.getSourceListId());
        entity.setSide(model.getSide());
        return entity;
    }
}
