package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.category.CategoryDTO;
import com.dcarrillo.taskmanager.dto.category.CreateCategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CreateCategoryDTO createCategoryDTO);

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO updateCategory(Long id, CreateCategoryDTO createCategoryDTO);

    void deleteCategory(Long id);




}
