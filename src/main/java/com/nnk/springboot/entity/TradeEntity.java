package com.nnk.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Entité JPA représentant une transaction (trade), persistée dans la table {@code trade}.
 * <p>
 * Cette classe appartient à la couche de persistance : elle n'est pas exposée
 * directement aux vues. La conversion vers le {@link com.nnk.springboot.model.TradeModel}
 * utilisé par Thymeleaf est assurée par le {@link com.nnk.springboot.mapper.TradeMapper}.
 * Les accesseurs (getters/setters) sont générés par Lombok.
 */
@Entity
@Table(name = "trade")
@Getter
@Setter
@NoArgsConstructor
public class TradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;

    private String account;
    private String type;
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
