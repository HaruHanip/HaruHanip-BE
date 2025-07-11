package com.haruhanip.api.user.controller;

import com.haruhanip.api.user.api.UserChallengeApi;
import com.haruhanip.api.user.dto.CreateUserChallengeProgressRequest;
import com.haruhanip.api.user.dto.UserChallengeProgressResponse;
import com.haruhanip.api.user.service.UserChallengeProgressService;
import com.haruhanip.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/challenge")
@Validated
public class UserChallengeController implements UserChallengeApi {

    private final UserChallengeProgressService userChallengeProgressService;

    @PostMapping
    public ResponseEntity<Void> createUserChallengeProgress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid CreateUserChallengeProgressRequest request
    ) {
        userChallengeProgressService.createUserChallengeProgress(customUserDetails.getId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<UserChallengeProgressResponse> getUserChallengeProgress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long challengeId
    ) {
        UserChallengeProgressResponse response = userChallengeProgressService.getUserChallengeProgress(
                customUserDetails.getId(), challengeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserChallengeProgressResponse>> getAllUserChallengeProgress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        List<UserChallengeProgressResponse> responses = userChallengeProgressService.getAllUserChallengeProgress(
                customUserDetails.getId());
        return ResponseEntity.ok(responses);
    }
}
