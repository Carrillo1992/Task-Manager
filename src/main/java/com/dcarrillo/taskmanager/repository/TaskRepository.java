package com.dcarrillo.taskmanager.repository;

import com.dcarrillo.taskmanager.entity.Task;
import com.dcarrillo.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser(User user);

    Optional<Task> findByIdAndUser_Id(Long id, Long userId);
}
