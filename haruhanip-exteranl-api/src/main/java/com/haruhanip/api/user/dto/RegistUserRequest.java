package com.haruhanip.api.user.dto;

import java.time.LocalDate;

public record RegistUserRequest(
        String username,
        String email,
        String profileImageUrl,
        LocalDate birthDate
) {
}
