package com.haruhanip.api.friend.dto;

import com.haruhanip.domains.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendResponse {
    private Long userId;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public FriendResponse(Long userId, String nickname, String profileImageUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static FriendResponse from(User user) {
        return FriendResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getUsername())
                .profileImageUrl(user.getProfileImage())
                .build();
    }
}
