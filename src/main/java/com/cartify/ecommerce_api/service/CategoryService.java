package com.cartify.ecommerce_api.service;

import com.cartify.ecommerce_api.dto.CategoryRequestDTO;
import com.cartify.ecommerce_api.dto.CategoryResponseDTO;
import com.cartify.ecommerce_api.entity.Category;
import com.cartify.ecommerce_api.exception.CategoryNotFoundException;
import com.cartify.ecommerce_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());

        Category saved = categoryRepository.save(category);
        return toResponseDTO(saved);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        // findAll() + stream + map + toList()
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        // findById + orElseThrow
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        return toResponseDTO(category);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        // find existing, mutate it directly (not new Category()), save, convert
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        category.setName(dto.getName());
        Category updated = categoryRepository.save(category);
        return toResponseDTO(updated);
    }

    public void deleteCategory(Long id) {
        // existsById check, throw if not found, else deleteById
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponseDTO toResponseDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName());
    }
}
