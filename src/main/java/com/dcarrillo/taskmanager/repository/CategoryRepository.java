package com.dcarrillo.taskmanager.repository;

import com.dcarrillo.taskmanager.entity.Category;
import com.dcarrillo.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByUser(User user);

    Optional<Category> findByNameAndUser_Id(String name, Long userId);

    boolean existsByNameAndUser_Id(String name, Long userId);
}
