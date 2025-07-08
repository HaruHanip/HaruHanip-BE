package com.haruhanip.api.user.service;

import com.haruhanip.api.user.dto.UserDailyResultResponse;
import com.haruhanip.api.user.dto.UserProblemSolveRequest;
import com.haruhanip.domains.daily.domain.DailyProblem;
import com.haruhanip.domains.daily.repository.DailyProblemRepository;
import com.haruhanip.domains.problem.domain.Problem;
import com.haruhanip.domains.problem.repository.ProblemRepository;
import com.haruhanip.domains.user.domain.User;
import com.haruhanip.domains.user.domain.UserProblemHistory;
import com.haruhanip.domains.user.repository.UserProblemHistoryRepository;
import com.haruhanip.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProblemService {

    private final UserRepository userRepository;
    private final UserProblemHistoryRepository userProblemHistoryRepository;
    private final DailyProblemRepository dailyProblemRepository;

    @Transactional
    public void solveProblem(Long userId, UserProblemSolveRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        DailyProblem dailyProblem = dailyProblemRepository.findById(request.problemId())
                .orElseThrow(() -> new IllegalArgumentException("Problem not found: " + request.problemId()));

        UserProblemHistory userProblemHistory = UserProblemHistory.builder()
                .user(user)
                .dailyProblem(dailyProblem)
                .selectedOptionIndex(request.selectedOptionIndex())
                .isCorrect(request.isCorrect())
                .build();

        userProblemHistoryRepository.save(userProblemHistory);
    }

    @Transactional(readOnly = true)
    public UserDailyResultResponse getUserDailyResult(Long userId, Long dailyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        List<UserProblemHistory> histories = userProblemHistoryRepository
                .findByUserUserIdAndDailyProblemDailyDailyId(userId, dailyId);

        int score = histories.stream()
                .mapToInt(history -> history.getIsCorrect() ? 1 : 0)
                .sum();

        return UserDailyResultResponse.builder()
                .userId(user.getUserId())
                .dailyId(dailyId)
                .score(score)
                .build();
    }
}
