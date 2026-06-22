package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.model.UserModel;
import org.springframework.stereotype.Component;

/**
 * Convertisseur entre l'entité {@link UserEntity} (persistance) et le
 * {@link UserModel} (présentation), pour les utilisateurs.
 */
@Component
public class UserMapper {

    /**
     * Convertit une entité en Model.
     *
     * @param entity l'entité à convertir (peut être {@code null})
     * @return le Model correspondant, ou {@code null} si {@code entity} est {@code null}
     */
    public UserModel toModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserModel.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .fullname(entity.getFullname())
                .role(entity.getRole())
                .build();
    }

    /**
     * Convertit un Model en entité.
     *
     * @param model le Model à convertir (peut être {@code null})
     * @return l'entité correspondante, ou {@code null} si {@code model} est {@code null}
     */
    public UserEntity toEntity(UserModel model) {
        if (model == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        entity.setFullname(model.getFullname());
        entity.setRole(model.getRole());
        return entity;
    }
}
