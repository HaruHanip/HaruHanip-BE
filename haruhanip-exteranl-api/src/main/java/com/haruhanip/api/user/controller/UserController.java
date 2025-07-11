package com.haruhanip.api.user.controller;

import com.haruhanip.api.user.api.UserApi;
import com.haruhanip.api.user.dto.RegistUserRequest;
import com.haruhanip.api.user.dto.UserProfileResponse;
import com.haruhanip.api.user.dto.VerifyEmailRequest;
import com.haruhanip.api.user.service.MailService;
import com.haruhanip.api.user.service.UserService;
import com.haruhanip.security.CustomUserDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserApi {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserProfileResponse userProfile = userService.getUserProfile(userDetails.getId());
        return ResponseEntity.ok(userProfile);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid RegistUserRequest registerRequest) {
        userService.registUser(userDetails.getId(), registerRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/email/send")
    public ResponseEntity<Void> sendMail(@RequestParam @Valid @NotNull String email) {
        mailService.sendMail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/verify")
    public ResponseEntity<Void> verifyMail(@RequestBody @Valid VerifyEmailRequest request) {
        mailService.verifyMail(request.email(), request.authCode());
        return ResponseEntity.ok().build();
    }
}
