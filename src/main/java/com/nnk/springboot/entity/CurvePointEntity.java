package com.nnk.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Entité JPA représentant un point de courbe, persistée dans la table {@code curvepoint}.
 * <p>
 * Cette classe appartient à la couche de persistance : elle n'est pas exposée
 * directement aux vues. La conversion vers le {@link com.nnk.springboot.model.CurvePointModel}
 * utilisé par Thymeleaf est assurée par le {@link com.nnk.springboot.mapper.CurvePointMapper}.
 * Les accesseurs (getters/setters) sont générés par Lombok.
 */
@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@NoArgsConstructor
public class CurvePointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer curveId;
    private Timestamp asOfDate;
    private Double term;
    private Double value;
    private Timestamp creationDate;
}
