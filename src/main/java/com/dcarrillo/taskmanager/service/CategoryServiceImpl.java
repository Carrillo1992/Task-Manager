package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.category.CategoryDTO;
import com.dcarrillo.taskmanager.dto.category.CreateCategoryDTO;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryDTO createCategory(CreateCategoryDTO createCategoryDTO) {
        return null;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return List.of();
    }

    @Override
    public CategoryDTO findById(Long id) {
        return null;
    }

    @Override
    public CategoryDTO updateCategory(Long id, CreateCategoryDTO createCategoryDTO) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }
}
