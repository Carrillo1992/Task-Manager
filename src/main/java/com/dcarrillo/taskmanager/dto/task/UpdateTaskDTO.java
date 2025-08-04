package com.dcarrillo.taskmanager.dto.task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskDTO {
    private String title;
    private String description;
    private String category;
    private String status;
    private LocalDateTime expirationDate;
}
