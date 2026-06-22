package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.model.UserModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    public void toModelMapsFields() {
        UserEntity entity = new UserEntity();
        entity.setId(7);
        entity.setUsername("username_v");
        entity.setPassword("password_v");
        entity.setFullname("fullname_v");
        entity.setRole("role_v");

        UserModel model = mapper.toModel(entity);

        assertEquals(Integer.valueOf(7), model.getId());
        assertEquals("username_v", model.getUsername());
        assertEquals("password_v", model.getPassword());
        assertEquals("fullname_v", model.getFullname());
        assertEquals("role_v", model.getRole());
    }

    @Test
    public void toEntityMapsFields() {
        UserModel model = UserModel.builder()
                .id(7)
                .username("username_v")
                .password("password_v")
                .fullname("fullname_v")
                .role("role_v")
                .build();

        UserEntity entity = mapper.toEntity(model);

        assertEquals(Integer.valueOf(7), entity.getId());
        assertEquals("username_v", entity.getUsername());
        assertEquals("password_v", entity.getPassword());
        assertEquals("fullname_v", entity.getFullname());
        assertEquals("role_v", entity.getRole());
    }

    @Test
    public void toModelNullReturnsNull() {
        assertNull(mapper.toModel(null));
    }

    @Test
    public void toEntityNullReturnsNull() {
        assertNull(mapper.toEntity(null));
    }
}
