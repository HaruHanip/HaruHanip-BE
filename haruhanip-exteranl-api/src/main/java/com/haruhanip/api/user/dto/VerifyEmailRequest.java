package com.haruhanip.api.user.dto;

public record VerifyEmailRequest(
        String email,
        String authCode
) {
}
