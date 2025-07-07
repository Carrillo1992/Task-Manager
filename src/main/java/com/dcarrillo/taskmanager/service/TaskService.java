package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.task.CreateTaskDTO;
import com.dcarrillo.taskmanager.dto.task.TaskDTO;
import com.dcarrillo.taskmanager.entity.Status;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(CreateTaskDTO createTaskDTO, Long userId);

    TaskDTO findById(Long id);

    List<TaskDTO> findAllByUsername(String username);

    TaskDTO updateTask(Long id, Long userId,  CreateTaskDTO createTaskDTO);

    TaskDTO updateStatus(Long id ,Long userId,  Status status);

    void deleteTask(Long id, Long userId);
}
