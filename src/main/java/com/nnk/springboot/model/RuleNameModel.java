package com.nnk.springboot.model;

import lombok.*;

/**
 * Model (POJO de présentation) représentant une règle métier.
 * <p>
 * C'est l'objet manipulé par les contrôleurs et lié aux formulaires Thymeleaf,
 * en lieu et place de l'entité {@link com.nnk.springboot.entity.RuleNameEntity}.
 * Aucune contrainte de validation n'est appliquée.
 * Le patron Builder ({@code @Builder}) est utilisé par le
 * {@link com.nnk.springboot.mapper.RuleNameMapper} pour construire les instances.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleNameModel {

    private Integer id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
}
