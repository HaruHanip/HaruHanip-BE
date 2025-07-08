package com.haruhanip.api.daily.api;

import com.haruhanip.api.daily.dto.DailyProblemResponse;
import com.haruhanip.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Daily Problem Query API", description = "데일리 문제 조회 API")
public interface DailyProblemQueryApi {

    @Operation(summary = "데일리 문제 조회", description = "특정 데일리 문제를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "데일리 문제 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "데일리 문제를 찾을 수 없음")
    })
    public ResponseEntity<DailyProblemResponse> getDailyProblem(
            @PathVariable Long dailyId, @RequestParam int sequence);

    @Operation(summary = "카테고리별 데일리 문제 조회", description = "특정 카테고리의 데일리 문제를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "카테고리별 데일리 문제 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "데일리 문제를 찾을 수 없음")
    })
    public ResponseEntity<DailyProblemResponse> getDailyProblemByDate(
            @PathVariable Long categoryId, @RequestParam int sequence);

    @Operation(summary = "로그인 시 다음 데일리 문제 조회", description = "사용자의 다음 데일리 문제를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "다음 데일리 문제 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "다음 데일리 문제를 찾을 수 없음")
    })
    public ResponseEntity<DailyProblemResponse> getDailyProblemSolving(
            @PathVariable Long dailyId, @AuthenticationPrincipal CustomUserDetails userDetails);
}
