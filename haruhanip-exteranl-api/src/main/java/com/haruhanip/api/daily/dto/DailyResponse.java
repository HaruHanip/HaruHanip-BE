package com.haruhanip.api.daily.dto;

import com.haruhanip.domains.daily.domain.Daily;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyResponse {

    private final Long dailyId;

    private final LocalDate dailyDate;

    private final Long categoryId;

    private final String categoryName;

    private final List<DailyProblemResponse> problems;

    @Builder
    public DailyResponse(Long dailyId, LocalDate dailyDate, Long categoryId, String categoryName,
                         List<DailyProblemResponse> problems) {
        this.dailyId = dailyId;
        this.dailyDate = dailyDate;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.problems = problems;
    }

    public static DailyResponse from(Daily daily) {
        return new DailyResponse(
                daily.getDailyId(),
                daily.getDailyDate(),
                daily.getCategory().getCategoryId(),
                daily.getCategory().getName(),
                daily.getDailyProblems().stream()
                        .sorted((a, b) -> Integer.compare(a.getSequence(), b.getSequence()))
                        .map(DailyProblemResponse::from)
                        .collect(Collectors.toList())
        );
    }

}
