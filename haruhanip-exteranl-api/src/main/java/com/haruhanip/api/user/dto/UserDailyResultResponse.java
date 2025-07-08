package com.haruhanip.api.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDailyResultResponse {
    private Long userId;
    private Long dailyId;
    private int score;

    @Builder
    public UserDailyResultResponse(Long userId, Long dailyId, int score) {
        this.userId = userId;
        this.dailyId = dailyId;
        this.score = score;
    }
}
