package com.haruhanip.api.daily.service;

import com.haruhanip.api.daily.dto.DailyIdResponse;
import com.haruhanip.api.daily.dto.DailyResponse;
import com.haruhanip.domains.category.domain.Category;
import com.haruhanip.domains.category.repository.CategoryRepository;
import com.haruhanip.domains.daily.repository.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyQueryService {

    private final DailyRepository dailyRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public DailyResponse getDaily(Long dailyId) {
        return dailyRepository.findById(dailyId)
                .map(DailyResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Daily not found"));
    }

    @Transactional(readOnly = true)
    public DailyResponse getDailyByDate(Long categoryId, LocalDate date) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        return dailyRepository.findByCategoryAndDailyDate(category, date)
                .map(DailyResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Daily not found for the given date"));
    }

    @Transactional(readOnly = true)
    public DailyIdResponse getTodayDailyId(LocalDate date, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        return dailyRepository.findByCategoryAndDailyDate(category, date)
                .map(daily -> DailyIdResponse.from(daily.getDailyId()))
                .orElseThrow(() -> new IllegalArgumentException("No daily found for the given date"));
    }
}
