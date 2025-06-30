package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.category.CategoryDTO;
import com.dcarrillo.taskmanager.dto.category.CreateCategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CreateCategoryDTO createCategoryDTO, Long userId);

    List<CategoryDTO> findAllByUserId(Long userId);

    CategoryDTO findByName(String categoryName, Long userId);

    void deleteCategory(String categoryName ,  Long userId);




}
