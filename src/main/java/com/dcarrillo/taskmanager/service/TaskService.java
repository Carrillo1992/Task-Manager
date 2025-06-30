package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.task.CreateTaskDTO;
import com.dcarrillo.taskmanager.dto.task.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(CreateTaskDTO createTaskDTO);

    TaskDTO findById(Long id);

    List<TaskDTO> findAllById(Long id);

    TaskDTO updateTask(Long id, CreateTaskDTO createTaskDTO);

    void deleteTask(Long id);
}
