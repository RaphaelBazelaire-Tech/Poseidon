package com.nnk.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité JPA représentant une règle métier, persistée dans la table {@code rulename}.
 * <p>
 * Cette classe appartient à la couche de persistance : elle n'est pas exposée
 * directement aux vues. La conversion vers le {@link com.nnk.springboot.model.RuleNameModel}
 * utilisé par Thymeleaf est assurée par le {@link com.nnk.springboot.mapper.RuleNameMapper}.
 * Les accesseurs (getters/setters) sont générés par Lombok.
 */
@Entity
@Table(name = "rulename")
@Getter
@Setter
@NoArgsConstructor
public class RuleNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
}
