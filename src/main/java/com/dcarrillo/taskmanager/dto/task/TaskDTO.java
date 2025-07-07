package com.dcarrillo.taskmanager.dto.task;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String username;
    private String categoryName;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
}
