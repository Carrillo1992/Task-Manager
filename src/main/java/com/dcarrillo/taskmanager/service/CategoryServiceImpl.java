package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.category.CategoryDTO;
import com.dcarrillo.taskmanager.dto.category.CreateCategoryDTO;
import com.dcarrillo.taskmanager.dto.category.UpdateCategoryDTO;
import com.dcarrillo.taskmanager.entity.Category;
import com.dcarrillo.taskmanager.repository.CategoryRepository;
import com.dcarrillo.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
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

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> findAllByUserId(Long userId) {
        return categoryRepository.findAllByUser(userRepository.
                findById(userId)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado")))
                .stream()
                .map(category -> getCategoryDTO(userId , category))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDTO findById(Long id, Long userId) {
        Category category = categoryRepository.findByIdAndUser_Id(id, userId)
                .orElseThrow(()-> new RuntimeException("Categoria no encontrada"));
        return getCategoryDTO(userId,category);
    }

    @Transactional
    @Override
    public CategoryDTO updateCategory(Long id, Long userId, UpdateCategoryDTO updateCategoryDTO) {
        Category category = categoryRepository.findByIdAndUser_Id(id , userId)
                .orElseThrow(()->new RuntimeException("Categoria no encontrada"));
        category.setName(updateCategoryDTO.getName());
        categoryRepository.save(category);
        return getCategoryDTO(userId,category);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id, Long userId) {

        Category category = categoryRepository.findByIdAndUser_Id(id , userId)
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
