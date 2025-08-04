package com.dcarrillo.taskmanager.repository;

import com.dcarrillo.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsUserByUsername(String username);

    boolean existsUserById(Long id);

    Optional<User> findByUsername(String username);
}
