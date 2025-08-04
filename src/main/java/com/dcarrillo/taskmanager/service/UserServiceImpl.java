package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.user.RegisterUserDTO;
import com.dcarrillo.taskmanager.dto.user.UserDTO;
import com.dcarrillo.taskmanager.entity.Category;
import com.dcarrillo.taskmanager.entity.Role;
import com.dcarrillo.taskmanager.entity.User;
import com.dcarrillo.taskmanager.repository.CategoryRepository;
import com.dcarrillo.taskmanager.repository.RoleRepository;
import com.dcarrillo.taskmanager.repository.UserRepository;
import com.dcarrillo.taskmanager.security.CustomUserDetails;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleRepository roleRepository,
                           UserRepository userRepository, CategoryRepository categoryRepository,
                           @Lazy PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerUser(RegisterUserDTO registerUserDto) {
        if (userRepository.existsUserByEmail(registerUserDto.getEmail())){
            throw new RuntimeException("Email ya registrado");
        }
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(()->new RuntimeException("Rol no encontrado"));
        user.setPasswordHash(passwordEncoder.encode(registerUserDto.getPassword()));
        user.getRoles().add(userRole);
        userRepository.save(user);
        Category category = new Category();
        category.setName("Sin Categoria");
        category.setUser(user);
        categoryRepository.save(category);
        return getUserDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO findByUsername(String username) {
        return getUserDTO(userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Usuario no encontrado")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::getUserDTO)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)){
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }


    private UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoles(user.getRoles()
                .stream()
                .map(Role::getName)
                .toList());
        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPasswordHash(), authorities);
    }
}
