package com.dcarrillo.taskmanager.controller;

import com.dcarrillo.taskmanager.dto.category.CategoryDTO;
import com.dcarrillo.taskmanager.dto.category.CreateCategoryDTO;
import com.dcarrillo.taskmanager.dto.category.UpdateCategoryDTO;
import com.dcarrillo.taskmanager.security.CustomUserDetails;
import com.dcarrillo.taskmanager.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService categoryService) {
        this.service = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryDTO createCategoryDTO, Authentication authentication ){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();
        CategoryDTO categoryDTO = service.createCategory(createCategoryDTO , userId);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @GetMapping
    public ResponseEntity<?> findAll(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();
        List<CategoryDTO> categoryDTOList = service.findAllByUserId(userId);

        return ResponseEntity.ok().body(categoryDTOList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestParam Long id, @RequestBody UpdateCategoryDTO updateCategoryDTO, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();
        CategoryDTO categoryDTO = service.updateCategory(id, userId, updateCategoryDTO);

        return ResponseEntity.ok().body(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@Valid @RequestParam Long id, Authentication authentication){        CategoryDTO categoryDTO = null;
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();
        service.deleteCategory(id, userId);

        return ResponseEntity.ok().build();
    }


}
