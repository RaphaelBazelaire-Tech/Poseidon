package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.BidListEntity;
import com.nnk.springboot.model.BidListModel;
import org.springframework.stereotype.Component;

/**
 * Convertisseur entre l'entité {@link BidListEntity} (persistance) et le
 * {@link BidListModel} (présentation), pour les offres (bids).
 */
@Component
public class BidListMapper {

    /**
     * Convertit une entité en Model.
     *
     * @param entity l'entité à convertir (peut être {@code null})
     * @return le Model correspondant, ou {@code null} si {@code entity} est {@code null}
     */
    public BidListModel toModel(BidListEntity entity) {
        if (entity == null) {
            return null;
        }

        return BidListModel.builder()
                .bidListId(entity.getBidListId())
                .account(entity.getAccount())
                .type(entity.getType())
                .bidQuantity(entity.getBidQuantity())
                .askQuantity(entity.getAskQuantity())
                .bid(entity.getBid())
                .ask(entity.getAsk())
                .benchmark(entity.getBenchmark())
                .bidListDate(entity.getBidListDate())
                .commentary(entity.getCommentary())
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
    public BidListEntity toEntity(BidListModel model) {
        if (model == null) {
            return null;
        }

        BidListEntity entity = new BidListEntity();
        entity.setBidListId(model.getBidListId());
        entity.setAccount(model.getAccount());
        entity.setType(model.getType());
        entity.setBidQuantity(model.getBidQuantity());
        entity.setAskQuantity(model.getAskQuantity());
        entity.setBid(model.getBid());
        entity.setAsk(model.getAsk());
        entity.setBenchmark(model.getBenchmark());
        entity.setBidListDate(model.getBidListDate());
        entity.setCommentary(model.getCommentary());
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
