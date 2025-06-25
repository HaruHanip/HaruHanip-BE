package com.clubnu.api.category.controller;

import com.clubnu.api.category.api.CategoryCommandApi;
import com.clubnu.api.category.dto.CategoryCreateRequest;
import com.clubnu.api.category.dto.CategoryEditRequest;
import com.clubnu.api.category.service.CategoryCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryCommandController implements CategoryCommandApi {

    private final CategoryCommandService categoryCommandService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
        categoryCommandService.createCategory(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(@RequestBody @Valid CategoryEditRequest request, Long categoryId) {
        categoryCommandService.updateCategory(categoryId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryCommandService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
