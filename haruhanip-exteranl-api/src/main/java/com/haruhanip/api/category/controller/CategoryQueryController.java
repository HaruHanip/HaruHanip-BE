package com.haruhanip.api.category.controller;

import com.haruhanip.api.category.api.CategoryQueryApi;
import com.haruhanip.api.category.dto.CategoryResponse;
import com.haruhanip.api.category.service.CategoryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryQueryController implements CategoryQueryApi {

    private final CategoryQueryService categoryQueryService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable @Valid Long categoryId) {
        CategoryResponse response = categoryQueryService.getCategory(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> responses = categoryQueryService.getAllCategories();
        return ResponseEntity.ok(responses);
    }
}
