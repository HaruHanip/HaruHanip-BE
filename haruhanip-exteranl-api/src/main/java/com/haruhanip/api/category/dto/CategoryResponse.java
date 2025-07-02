package com.haruhanip.api.category.dto;

import com.haruhanip.domains.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponse {
    public String name;

    @Builder
    public CategoryResponse(String name) {
        this.name = name;
    }

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .name(category.getName())
                .build();
    }
}
