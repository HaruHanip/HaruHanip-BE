package com.clubnu.api.category.dto;

import com.clubnu.domains.category.domain.Category;
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
