package com.dcarrillo.taskmanager.controller;

import com.dcarrillo.taskmanager.dto.jwt.JwtResponseDTO;
import com.dcarrillo.taskmanager.dto.user.LoginUserDTO;
import com.dcarrillo.taskmanager.dto.user.RegisterUserDTO;
import com.dcarrillo.taskmanager.dto.user.UserDTO;
import com.dcarrillo.taskmanager.entity.Role;
import com.dcarrillo.taskmanager.entity.User;
import com.dcarrillo.taskmanager.security.JwtUtils;
import com.dcarrillo.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    UserService userService;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO){
        try {
            userService.registerUser(registerUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario Registrado exitosamente");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserDTO user = userService.findByUsername(userDetails.getUsername());


        String jwt = jwtUtils.generateToken(userDetails, user.getId());
        List<String> roles = user.getRoles();
        Long userId = user.getId();

        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(jwt, userId, userDetails.getUsername(), roles);

        return ResponseEntity.ok(jwtResponseDTO);
    }

}
