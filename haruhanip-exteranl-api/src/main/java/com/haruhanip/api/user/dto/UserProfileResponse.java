package com.haruhanip.api.user.dto;

import com.haruhanip.domains.user.domain.RegistStatus;
import com.haruhanip.domains.user.domain.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserProfileResponse {
    private Long userId;
    private String nickname;
    private String email;
    private String profileImage;
    private UserRole role;
    private RegistStatus registStatus;
    private LocalDate birthday;
    private LocalDateTime createdAt;

    @Builder
    public UserProfileResponse(Long userId, String nickname, String email, String profileImage,
                               UserRole role, RegistStatus registStatus, LocalDate birthday,
                               LocalDateTime createdAt) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
        this.registStatus = registStatus;
        this.birthday = birthday;
        this.createdAt = createdAt;
    }
}
