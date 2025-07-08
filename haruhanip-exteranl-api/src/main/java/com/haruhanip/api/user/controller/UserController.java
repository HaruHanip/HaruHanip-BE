package com.haruhanip.api.user.controller;

import com.haruhanip.api.user.dto.UserProfileResponse;
import com.haruhanip.api.user.service.UserService;
import com.haruhanip.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserProfileResponse userProfile = userService.getUserProfile(userDetails.getId());
        return ResponseEntity.ok(userProfile);
    }
}
