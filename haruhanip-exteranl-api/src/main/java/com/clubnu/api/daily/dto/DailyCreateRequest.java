package com.clubnu.api.daily.dto;

import com.clubnu.domains.category.domain.Category;
import com.clubnu.domains.daily.domain.DailyProblem;

import java.time.LocalDate;
import java.util.List;

public record DailyCreateRequest (
        LocalDate dailyDate,
        Long categoryId
){
}
