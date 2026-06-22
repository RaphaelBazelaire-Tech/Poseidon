package com.nnk.springboot.model;

import lombok.*;

import java.sql.Timestamp;

/**
 * Model (POJO de présentation) représentant un point de courbe.
 * <p>
 * C'est l'objet manipulé par les contrôleurs et lié aux formulaires Thymeleaf,
 * en lieu et place de l'entité {@link com.nnk.springboot.entity.CurvePointEntity}.
 * Aucune contrainte de validation n'est appliquée.
 * Le patron Builder ({@code @Builder}) est utilisé par le
 * {@link com.nnk.springboot.mapper.CurvePointMapper} pour construire les instances.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurvePointModel {

    private Integer id;
    private Integer curveId;
    private Timestamp asOfDate;
    private Double term;
    private Double value;
    private Timestamp creationDate;
}
