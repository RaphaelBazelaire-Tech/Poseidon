package com.nnk.springboot.service;

import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserModel> findAll() {
        return userRepository.findAll().stream().map(userMapper::toModel).toList();
    }

    public Optional<UserModel> findById(Integer id) {
        return userRepository.findById(id).map(userMapper::toModel);
    }

    public UserModel save(UserModel model) {
        return userMapper.toModel(userRepository.save(userMapper.toEntity(model)));
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
