package com.haruhanip.processor;

import com.haruhanip.domains.category.domain.Category;
import com.haruhanip.domains.category.repository.CategoryRepository;
import com.haruhanip.domains.daily.domain.Daily;
import com.haruhanip.domains.daily.repository.DailyRepository;
import com.haruhanip.domains.problem.domain.Problem;
import com.haruhanip.domains.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class DailyItemProcessor implements ItemProcessor<Long, Daily> {

    private final CategoryRepository categoryRepository;
    private final ProblemRepository problemRepository;
    private final DailyRepository dailyRepository;

    @Override
    public Daily process(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        // 중복 체크
        if (dailyRepository.existsByCategoryAndDailyDate(category, today)) {
            return null;
        }

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end   = start.plusDays(1);
        List<Problem> problems = problemRepository
                .findAllByCategoryAndCreatedAtBetween(category, start, end);

        Daily daily = Daily.builder()
                .dailyDate(today)
                .category(category)
                .build();
        problems.forEach(daily::addProblem);
        return daily;
    }
}
