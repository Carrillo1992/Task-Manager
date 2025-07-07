package com.dcarrillo.taskmanager.repository;

import com.dcarrillo.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserById(Long id);
}
