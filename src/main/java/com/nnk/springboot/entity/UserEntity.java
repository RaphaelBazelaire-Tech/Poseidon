package com.nnk.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité JPA représentant un utilisateur, persistée dans la table {@code users}.
 * <p>
 * Cette classe appartient à la couche de persistance : elle n'est pas exposée
 * directement aux vues. La conversion vers le {@link com.nnk.springboot.model.UserModel}
 * utilisé par Thymeleaf est assurée par le {@link com.nnk.springboot.mapper.UserMapper}.
 * Les accesseurs (getters/setters) sont générés par Lombok.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;
    private String password;
    private String fullname;
    private String role;
}

