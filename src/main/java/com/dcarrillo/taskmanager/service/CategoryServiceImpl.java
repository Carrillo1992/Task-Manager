package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.category.CategoryDTO;
import com.dcarrillo.taskmanager.dto.category.CreateCategoryDTO;
import com.dcarrillo.taskmanager.entity.Category;
import com.dcarrillo.taskmanager.repository.CategoryRepository;
import com.dcarrillo.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CategoryDTO createCategory(CreateCategoryDTO createCategoryDTO, Long userId) {
        if(categoryRepository.existsByNameAndUser_Id(createCategoryDTO.getName(), userId)){
            throw new RuntimeException("Categoria ya existente");
        }
        Category category = new Category();
        category.setUser(userRepository.findById(userId).
                orElseThrow(()-> new RuntimeException("Usuario no encontrado")));
        category.setName(createCategoryDTO.getName());
        categoryRepository.save(category);

        return getCategoryDTO(userId, category);
    }

    @Override
    public List<CategoryDTO> findAllByUserId(Long userId) {
        return categoryRepository.findAllByUser(userRepository.
                findById(userId)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado")))
                .stream()
                .map(category -> getCategoryDTO(userId , category))
                .toList();
    }

    @Override
    public CategoryDTO findByName(String categoryName, Long userId) {
        Category category = categoryRepository.findByNameAndUser_Id(categoryName, userId)
                .orElseThrow(()-> new RuntimeException("Categoria no encontrada"));
        return getCategoryDTO(userId,category);
    }

    @Override
    public void deleteCategory(String categoryName ,  Long userId) {

        Category category = categoryRepository.findByNameAndUser_Id(categoryName, userId)
                .orElseThrow(()-> new RuntimeException("Categoria no encontrada"));
        categoryRepository.delete(category);
    }

    private static CategoryDTO getCategoryDTO(Long userId, Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setUser_id(userId);
        return categoryDTO;
    }
}
