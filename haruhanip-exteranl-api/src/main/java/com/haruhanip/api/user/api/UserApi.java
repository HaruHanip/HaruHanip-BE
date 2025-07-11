package com.haruhanip.api.user.api;

import com.haruhanip.api.user.dto.RegistUserRequest;
import com.haruhanip.api.user.dto.UserProfileResponse;
import com.haruhanip.api.user.dto.VerifyEmailRequest;
import com.haruhanip.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "User API", description = "유저 API")
public interface UserApi {

    @Operation(summary = "사용자 프로필 조회", description = "인증된 사용자의 프로필 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 프로필 조회 성공", content = @Content(
                    mediaType = "application/json", examples = {
                    @ExampleObject(
                            name = "User Profile Example",
                            value = """
                                    {
                                      "userId": 1,
                                      "nickname": "신승용",
                                      "email": "sso9594@example.com",
                                      "profileImage": "https://example.com/profiles/sso9594.png",
                                      "role": "USER",
                                      "registStatus": "ACTIVE",
                                      "birthday": "1993-08-21",
                                      "createdAt": "2025-07-11T15:00:00"
                                    }
                                    """
                    )
            })),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "사용자 등록", description = "인증된 사용자가 새로운 사용자를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<Void> registerUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid RegistUserRequest registerRequest);

    @Operation(summary = "이메일 전송", description = "사용자에게 인증 이메일을 전송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 전송 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<Void> sendMail(@RequestParam @Valid @NotNull String email);

    @Operation(summary = "이메일 인증", description = "사용자가 이메일 인증을 완료합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 인증 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<Void> verifyMail(@RequestBody @Valid VerifyEmailRequest request);
}
