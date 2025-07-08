package com.haruhanip.api.user.api;

import com.haruhanip.api.user.dto.UserDailyResultResponse;
import com.haruhanip.api.user.dto.UserProblemSolveRequest;
import com.haruhanip.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User Problem API", description = "유저 문제 풀이 API")
public interface UserProblemApi {

    @Operation(summary = "유저 문제 풀이 기록", description = "유저가 문제를 풀었을 때 기록을 남깁니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "문제 풀이 기록 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "400", description = "문제 또는 유저를 찾을 수 없음")
    })
    public ResponseEntity<Void> solveProblem(
            @AuthenticationPrincipal CustomUserDetails userDetails
            , @RequestBody @Valid UserProblemSolveRequest request
    );

    @Operation(summary = "유저 일일 문제 풀이 결과 조회", description = "유저의 일일 문제 풀이 결과를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "일일 문제 풀이 결과 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "유저 또는 일일 문제를 찾을 수 없음")
    })
    public ResponseEntity<UserDailyResultResponse> getUserDailyResult(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long dailyId
    );
}
