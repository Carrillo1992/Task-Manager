package com.dcarrillo.taskmanager.repository;

import com.dcarrillo.taskmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
