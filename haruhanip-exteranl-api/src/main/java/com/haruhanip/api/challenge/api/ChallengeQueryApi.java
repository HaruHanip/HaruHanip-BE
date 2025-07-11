package com.haruhanip.api.challenge.api;

import com.haruhanip.api.challenge.dto.ChallengeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Challenge Query API", description = "챌린지 조회 API")
public interface ChallengeQueryApi {

    @Operation(summary = "챌린지 전체 조회", description = "모든 챌린지를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "Challenge List Response Example", value = """
                        [
                          {
                            "challengeId": 1,
                            "name": "7일 매일 챌린지",
                            "challengeType": {
                                "challengeTypeId": 1,
                                "code": "SEVEN_DAY",
                                "name": "7일 매일 챌린지",
                            },
                            "description": "7일 동안 매일 문제를 푸는 챌린지입니다.",
                            "durationDays": 7,
                            "targetAll": true
                        ]
                """)
        })),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "챌린지 없음")
    })
    public ResponseEntity<List<ChallengeResponse>> getAllChallenges();

    @Operation(summary = "챌린지 조회", description = "특정 챌린지를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "Challenge Response Example", value = """
                        {
                          "challengeId": 1,
                          "name": "7일 매일 챌린지",
                          "challengeType": {
                              "challengeTypeId": 1,
                              "code": "SEVEN_DAY",
                              "name": "7일 매일 챌린지"
                          },
                          "description": "7일 동안 매일 문제를 푸는 챌린지입니다.",
                          "durationDays": 7,
                          "targetAll": true
                        }
                """)
        })),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "챌린지 없음")
    })
    public ResponseEntity<ChallengeResponse> getChallenge(Long challengeId);
}
