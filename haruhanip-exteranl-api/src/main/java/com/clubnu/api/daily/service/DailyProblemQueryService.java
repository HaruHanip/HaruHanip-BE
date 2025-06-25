package com.clubnu.api.daily.service;

import com.clubnu.api.daily.dto.DailyProblemResponse;
import com.clubnu.domains.category.domain.Category;
import com.clubnu.domains.category.repository.CategoryRepository;
import com.clubnu.domains.daily.domain.Daily;
import com.clubnu.domains.daily.repository.DailyProblemRepository;
import com.clubnu.domains.daily.repository.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyProblemQueryService {
    private final DailyRepository dailyRepository;

    private final DailyProblemRepository dailyProblemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public DailyProblemResponse getDailyProblem(Long dailyId, int sequence) {
        Daily daily = dailyRepository.findById(dailyId)
                .orElseThrow(() -> new IllegalArgumentException("Daily not found"));

        return dailyProblemRepository.findByDailyAndSequence(daily, sequence)
                .map(DailyProblemResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Daily problem not found"));
    }

    @Transactional(readOnly = true)
    public DailyProblemResponse getDailyProblemByDate(Long categoryId, LocalDate date, int sequence) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Daily daily = dailyRepository.findByCategoryAndDailyDate(category, date)
                .orElseThrow(() -> new IllegalArgumentException("Daily not found for the given date"));

        return dailyProblemRepository.findByDailyAndSequence(daily, sequence)
                .map(DailyProblemResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Daily problem not found"));
    }
}
