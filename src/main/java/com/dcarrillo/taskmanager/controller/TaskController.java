package com.dcarrillo.taskmanager.controller;

import com.dcarrillo.taskmanager.dto.task.CreateTaskDTO;
import com.dcarrillo.taskmanager.dto.task.TaskDTO;
import com.dcarrillo.taskmanager.dto.task.UpdateTaskDTO;
import com.dcarrillo.taskmanager.security.CustomUserDetails;
import com.dcarrillo.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<?> registerTask (@Valid @RequestBody CreateTaskDTO createTaskDTO, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        TaskDTO taskDTO = service.createTask(createTaskDTO, userId);
        return ResponseEntity.ok().body(taskDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskDTO updateTaskDTO, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        TaskDTO taskDTO = service.updateTask(id, userId, updateTaskDTO);
        return ResponseEntity.ok().body(taskDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        TaskDTO taskDTO = service.findById(id, userId);
        return ResponseEntity.ok().body(taskDTO);
    }

    @GetMapping
    public ResponseEntity<?> findAllByUserId(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        List<TaskDTO> tasks = service.findAllByUserId(userId);
        return ResponseEntity.ok().body(tasks);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        service.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }
}
