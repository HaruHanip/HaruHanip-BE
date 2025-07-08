package com.haruhanip.domains.user.repository;

import com.haruhanip.domains.user.domain.UserProblemHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserProblemHistoryRepository extends JpaRepository<UserProblemHistory, Long> {
    List<UserProblemHistory> findByUserUserIdAndDailyProblemDailyDailyId(
            Long userId,
            Long dailyId
    );
}
