package com.haruhanip.api.category.api;

import com.haruhanip.api.category.dto.CategoryCreateRequest;
import com.haruhanip.api.category.dto.CategoryEditRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "Category Command API", description = "카테고리 명령 API")
public interface CategoryCommandApi {
    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
    })
    public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryCreateRequest request);

    @Operation(summary = "카테고리 수정", description = "기존 카테고리를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "400", description = "카테고리 없음")
    })
    public ResponseEntity<Void> updateCategory(@RequestBody @Valid CategoryEditRequest request, Long categoryId);

    @Operation(summary = "카테고리 삭제", description = "기존 카테고리를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "400", description = "카테고리 없음")
    })
    public ResponseEntity<Void> deleteCategory(Long categoryId);
}
