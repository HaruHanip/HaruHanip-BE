package com.haruhanip.api.daily.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DailyIdResponse {
    private Long dailyId;

    @Builder
    public DailyIdResponse(Long dailyId) {
        this.dailyId = dailyId;
    }

    public static DailyIdResponse from(Long dailyId) {
        return DailyIdResponse.builder()
                .dailyId(dailyId)
                .build();
    }
}
