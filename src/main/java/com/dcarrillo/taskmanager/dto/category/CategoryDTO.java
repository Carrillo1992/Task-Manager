package com.dcarrillo.taskmanager.dto.category;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private Long user_id;

}
