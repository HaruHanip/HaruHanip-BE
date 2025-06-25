package com.clubnu.api.category.service;

import com.clubnu.api.category.dto.CategoryResponse;
import com.clubnu.domains.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .toList();
    }
}
