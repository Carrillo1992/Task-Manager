package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.category.CategoryDTO;
import com.dcarrillo.taskmanager.dto.category.CreateCategoryDTO;
import com.dcarrillo.taskmanager.dto.category.UpdateCategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CreateCategoryDTO createCategoryDTO, Long userId);

    List<CategoryDTO> findAllByUserId(Long userId);

    CategoryDTO findById(Long id, Long userId);

    CategoryDTO updateCategory(Long id,  Long userId, UpdateCategoryDTO updateCategoryDTO);

    void deleteCategory(Long id ,  Long userId);




}
