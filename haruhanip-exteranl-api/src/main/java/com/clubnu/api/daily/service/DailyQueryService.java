package com.clubnu.api.daily.service;

import com.clubnu.api.daily.dto.DailyResponse;
import com.clubnu.domains.category.domain.Category;
import com.clubnu.domains.category.repository.CategoryRepository;
import com.clubnu.domains.daily.repository.DailyRepository;
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
}
