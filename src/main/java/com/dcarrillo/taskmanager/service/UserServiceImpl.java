package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.user.RegisterUserDTO;
import com.dcarrillo.taskmanager.dto.user.UserDTO;
import com.dcarrillo.taskmanager.entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public UserDTO registerUser(RegisterUserDTO registerUserDto) {
        return null;
    }

    @Override
    public UserDTO findByUsername(String username) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }
}
