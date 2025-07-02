package com.haruhanip.api.daily.api;

import com.haruhanip.api.daily.dto.DailyCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Daily Command API", description = "데일리 명령 API")
public interface DailyCommandApi {

    @Operation(summary = "데일리 생성", description = "새로운 데일리를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데일리 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "400", description = "카테고리 없음")
    })
    public ResponseEntity<Void> createDaily(@RequestBody @Valid DailyCreateRequest request);

    @Operation(summary = "데일리 삭제", description = "기존 데일리를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데일리 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "400", description = "데일리 없음")
    })
    public ResponseEntity<Void> deleteDaily(Long dailyId);
}
