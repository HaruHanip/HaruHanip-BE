package com.haruhanip.api.category.api;

import com.haruhanip.api.category.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Category Query API", description = "카테고리 조회 API")
public interface CategoryQueryApi {

    @Operation(summary = "단일 카테고리 조회", description = "카테고리 ID로 단일 카테고리를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "Category Response Example", value = """
                {
                    "name": "Example Category"
                }
                """)
        })),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "400", description = "카테고리를 찾을 수 없음")
    })
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable @Valid Long categoryId);

    @Operation(summary = "모든 카테고리 조회", description = "모든 카테고리를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "Category List Response Example", value = """
                [
                    {
                        "name": "Category 1"
                    },
                    {
                        "name": "Category 2"
                    }
                ]
                """)
        })),
        @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<List<CategoryResponse>> getAllCategories();
}
