package com.haruhanip.api.daily.dto;

import java.time.LocalDate;

public record DailyCreateRequest (
        LocalDate dailyDate,
        Long categoryId
){
}
