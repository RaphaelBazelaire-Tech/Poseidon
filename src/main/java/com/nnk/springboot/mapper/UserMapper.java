package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

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
