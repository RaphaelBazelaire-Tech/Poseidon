package com.nnk.springboot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;

/**
 * Model (POJO de présentation) représentant une offre (bid).
 * <p>
 * C'est l'objet manipulé par les contrôleurs et lié aux formulaires Thymeleaf,
 * en lieu et place de l'entité {@link com.nnk.springboot.entity.BidListEntity}.
 * Les champs {@code account}, {@code type} sont obligatoires.
 * Le patron Builder ({@code @Builder}) est utilisé par le
 * {@link com.nnk.springboot.mapper.BidListMapper} pour construire les instances.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidListModel {

    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
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
