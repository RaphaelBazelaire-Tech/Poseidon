package com.nnk.springboot.service;

import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private final UserMapper userMapper = new UserMapper();
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, userMapper);
    }

    private UserEntity sample() {
        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setUsername("username_v");
        return entity;
    }

    @Test
    public void findAllReturnsModels() {
        when(userRepository.findAll()).thenReturn(List.of(sample()));
        List<UserModel> result = userService.findAll();
        assertEquals(1, result.size());
        assertEquals("username_v", result.getFirst().getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void findByIdReturnsModel() {
        when(userRepository.findById(1)).thenReturn(Optional.of(sample()));
        Optional<UserModel> result = userService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("username_v", result.get().getUsername());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void savePersistsAndReturnsModel() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(sample());
        UserModel model = UserModel.builder().username("username_v").build();
        UserModel saved = userService.save(model);
        assertEquals("username_v", saved.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void deleteByIdDelegates() {
        userService.deleteById(1);
        verify(userRepository, times(1)).deleteById(1);
    }
}
