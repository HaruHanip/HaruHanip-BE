package com.haruhanip.api.daily.service;

import com.haruhanip.api.daily.dto.DailyCreateRequest;
import com.haruhanip.domains.category.domain.Category;
import com.haruhanip.domains.category.repository.CategoryRepository;
import com.haruhanip.domains.daily.domain.Daily;
import com.haruhanip.domains.daily.repository.DailyRepository;
import com.haruhanip.domains.problem.domain.Problem;
import com.haruhanip.domains.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyCommandService {

    private final DailyRepository dailyRepository;
    private final ProblemRepository problemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createDaily(DailyCreateRequest request) {
        Category category = categoryRepository
                .findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Daily target = Daily.builder()
                .dailyDate(request.dailyDate())
                .category(category)
                .build();

        LocalDateTime start = request.dailyDate().atStartOfDay();
        LocalDateTime end   = start.plusDays(1);
        List<Problem> problems = problemRepository
                .findAllByCategoryAndCreatedAtBetween(
                        category, start, end
                );

        for (Problem problem : problems) {
            target.addProblem(problem);
        }

        dailyRepository.save(target);
    }

    @Transactional
    public void deleteDaily(Long dailyId) {
        Daily daily = dailyRepository
                .findById(dailyId)
                .orElseThrow(() -> new IllegalArgumentException("Daily not found"));
        dailyRepository.delete(daily);
    }
}
