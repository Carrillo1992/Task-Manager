package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.user.RegisterUserDTO;
import com.dcarrillo.taskmanager.dto.user.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO registerUser(RegisterUserDTO registerUserDto);

    UserDTO findByUsername(String username);

    List<UserDTO> findAll();

    void deleteById(Long id);

}
