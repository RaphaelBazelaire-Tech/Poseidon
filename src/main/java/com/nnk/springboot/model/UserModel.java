package com.nnk.springboot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Model (POJO de présentation) représentant un utilisateur.
 * <p>
 * C'est l'objet manipulé par les contrôleurs et lié aux formulaires Thymeleaf,
 * en lieu et place de l'entité {@link com.nnk.springboot.entity.UserEntity}.
 * Les champs {@code username}, {@code password}, {@code fullname}, {@code role} sont obligatoires.
 * Le patron Builder ({@code @Builder}) est utilisé par le
 * {@link com.nnk.springboot.mapper.UserMapper} pour construire les instances.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    private Integer id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Fullname is mandatory")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    private String role;
}
