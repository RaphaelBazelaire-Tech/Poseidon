package com.nnk.springboot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;

/**
 * Model (POJO de présentation) représentant une transaction (trade).
 * <p>
 * C'est l'objet manipulé par les contrôleurs et lié aux formulaires Thymeleaf,
 * en lieu et place de l'entité {@link com.nnk.springboot.entity.TradeEntity}.
 * Les champs {@code account}, {@code type} sont obligatoires.
 * Le patron Builder ({@code @Builder}) est utilisé par le
 * {@link com.nnk.springboot.mapper.TradeMapper} pour construire les instances.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeModel {

    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
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
