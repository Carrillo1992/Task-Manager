package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.task.CreateTaskDTO;
import com.dcarrillo.taskmanager.dto.task.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public TaskDTO createTask(CreateTaskDTO createTaskDTO) {
        return null;
    }

    @Override
    public TaskDTO findById(Long id) {
        return null;
    }

    @Override
    public List<TaskDTO> findAllById(Long id) {
        return List.of();
    }

    @Override
    public TaskDTO updateTask(Long id, CreateTaskDTO createTaskDTO) {
        return null;
    }

    @Override
    public void deleteTask(Long id) {

    }
}
