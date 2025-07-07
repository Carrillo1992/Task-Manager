package com.dcarrillo.taskmanager.dto.task;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CreateTaskDTO {
    private String title;
    private String description;
    private String categoryName;
    private LocalDateTime expirationDate;
}
