package com.haruhanip.api.user.dto;

import java.time.LocalDate;

public record UserProfileEditRequest(
        String nickname,
        String email,
        String profileImageUrl,
        LocalDate birthday
) {
}
