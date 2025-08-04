package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.task.CreateTaskDTO;
import com.dcarrillo.taskmanager.dto.task.TaskDTO;
import com.dcarrillo.taskmanager.dto.task.UpdateTaskDTO;
import com.dcarrillo.taskmanager.entity.Status;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(CreateTaskDTO createTaskDTO, Long userId);

    TaskDTO findById(Long id, Long userId);

    List<TaskDTO> findAllByUserId(Long userId);

    TaskDTO updateTask(Long id, Long userId, UpdateTaskDTO updateTaskDTO);

    void deleteTask(Long id, Long userId);
}
