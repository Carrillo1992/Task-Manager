package com.dcarrillo.taskmanager.repository;

import com.dcarrillo.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
