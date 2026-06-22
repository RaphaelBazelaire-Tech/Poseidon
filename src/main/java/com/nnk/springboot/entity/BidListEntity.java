package com.nnk.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Entité JPA représentant une offre (bid), persistée dans la table {@code bidList}.
 * <p>
 * Cette classe appartient à la couche de persistance : elle n'est pas exposée
 * directement aux vues. La conversion vers le {@link com.nnk.springboot.model.BidListModel}
 * utilisé par Thymeleaf est assurée par le {@link com.nnk.springboot.mapper.BidListMapper}.
 * Les accesseurs (getters/setters) sont générés par Lombok.
 */
@Entity
@Table(name = "bidList")
@Getter
@Setter
@NoArgsConstructor
public class BidListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bidListId;

    private String account;
    private String type;
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
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
