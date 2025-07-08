package com.haruhanip.api.daily.service;

import com.haruhanip.api.daily.dto.DailyProblemResponse;
import com.haruhanip.api.daily.exception.exceptions.NoMoreDailyException;
import com.haruhanip.domains.category.domain.Category;
import com.haruhanip.domains.category.repository.CategoryRepository;
import com.haruhanip.domains.daily.domain.Daily;
import com.haruhanip.domains.daily.repository.DailyProblemRepository;
import com.haruhanip.domains.daily.repository.DailyRepository;
import com.haruhanip.domains.user.domain.UserProblemHistory;
import com.haruhanip.domains.user.repository.UserProblemHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyProblemQueryService {
    private final DailyRepository dailyRepository;

    private final DailyProblemRepository dailyProblemRepository;
    private final CategoryRepository categoryRepository;
    private final UserProblemHistoryRepository userProblemHistoryRepository;

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

    @Transactional(readOnly = true)
    public DailyProblemResponse getNextDailyProblem(Long dailyId, Long userId) {
        // 1) dailyId 로 Daily 가져오기
        Daily daily = dailyRepository.findById(dailyId)
                .orElseThrow(() -> new IllegalArgumentException("Daily not found"));

        // 2) 같은 dailyId 에 대한 히스토리만 조회
        List<UserProblemHistory> histories =
                userProblemHistoryRepository
                        .findByUserUserIdAndDailyProblemDailyDailyId(userId, dailyId);

        // 3) 다음 sequence 계산
        int nextSeq = histories.size() + 1;

        // 4) 해당 sequence 의 문제 반환
        return dailyProblemRepository.findByDailyAndSequence(daily, nextSeq)
                .map(DailyProblemResponse::from)
                .orElseThrow(NoMoreDailyException::new);
    }

}
