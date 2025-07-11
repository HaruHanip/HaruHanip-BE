package com.haruhanip.api.challenge.api;

import com.haruhanip.api.challenge.dto.ChallengeCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Challenge Command API", description = "챌린지 명령 API")
public interface ChallengeCommandApi {

    @Operation(summary = "챌린지 생성", description = "새로운 챌린지를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
    })
    public ResponseEntity<Void> createChallenge(@PathVariable Long challengeTypeId,
                                                @RequestBody @Valid ChallengeCreateRequest request);
    }
