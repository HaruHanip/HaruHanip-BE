package com.clubnu.api.category.service;

import com.clubnu.api.category.dto.CategoryCreateRequest;
import com.clubnu.api.category.dto.CategoryEditRequest;
import com.clubnu.domains.category.domain.Category;
import com.clubnu.domains.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {

    private final CategoryRepository categoryRepository;

    public void createCategory(CategoryCreateRequest request) {
        categoryRepository.save(Category.builder()
                        .name(request.name())
                .build());
    }

    public void updateCategory(Long categoryId, CategoryEditRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Category updatedCategory = category.toBuilder()
                .name(request.name())
                .build();
        categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        categoryRepository.delete(category);
    }
}
