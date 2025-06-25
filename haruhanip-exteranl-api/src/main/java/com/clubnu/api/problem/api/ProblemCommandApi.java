package com.clubnu.api.problem.api;

import com.clubnu.api.problem.dto.ProblemCreateRequest;
import com.clubnu.api.problem.dto.ProblemOptionCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Problem Command API", description = "문제 명령 API")
public interface ProblemCommandApi {

    @Operation(summary = "문제 생성", description = "새로운 문제를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "문제 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
    })
    public ResponseEntity<Void> createProblem(@RequestBody @Valid ProblemCreateRequest request);


        @Operation(summary = "문제 삭제", description = "기존 문제를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "문제 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "400", description = "문제 없음")
    })
    public ResponseEntity<Void> deleteProblem(Long problemId);
}
