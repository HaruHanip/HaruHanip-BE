package com.haruhanip.api.user.api;

import com.haruhanip.api.user.dto.CreateUserChallengeProgressRequest;
import com.haruhanip.api.user.dto.UserChallengeProgressResponse;
import com.haruhanip.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "User Challenge API", description = "사용자 챌린지 API")
public interface UserChallengeApi {

    @Operation(summary = "사용자 챌린지 생성", description = "사용자가 챌린지에 참여합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 참여 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "챌린지 없음")
    })
    @PostMapping
    public ResponseEntity<Void> createUserChallengeProgress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid CreateUserChallengeProgressRequest request
    );

    @Operation(summary = "사용자 챌린지 진행 상태 조회", description = "사용자가 특정 챌린지의 진행 상태를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 진행 상태 조회 성공", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(
                            name  = "User Challenge Progress Example",
                            value = """
                            {
                              "userChallengeProgressId": 1,
                              "user": {
                                "userId": 123,
                                "email": "example@example.com",
                                "nickname": "exampleUser",
                                "profileImage": "https://example.com/profile.jpg",
                                "userRole": "USER",
                                "registStatus": "REGISTERED",
                                "birthday": "1990-01-01"
                              },
                              "challenge": {
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
                              },
                              "startDate": "2025-01-01",
                              "endDate": "2025-01-07",
                              "currentStreak": 5,
                              "completed": true
                            }
                            """
                    )

            })),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "챌린지 없음")
    })
    public ResponseEntity<UserChallengeProgressResponse> getUserChallengeProgress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long challengeId
    );

    @Operation(summary = "사용자의 모든 챌린지 진행 상태 조회", description = "사용자가 참여한 모든 챌린지의 진행 상태를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모든 챌린지 진행 상태 조회 성공", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "All User Challenge Progress Example", value = """
                            [
                              {
                                "challengeId": 1,
                                "userId": 123,
                                "progress": 50,
                                "status": "IN_PROGRESS"
                              },
                              {
                                "challengeId": 2,
                                "userId": 123,
                                "progress": 100,
                                "status": "COMPLETED"
                              }
                            ]
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<List<UserChallengeProgressResponse>> getAllUserChallengeProgress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
