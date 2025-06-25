package com.clubnu.domains.daily.repository;

import com.clubnu.domains.daily.domain.Daily;
import com.clubnu.domains.daily.domain.DailyProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyProblemRepository extends JpaRepository<DailyProblem, Long> {
    Optional<DailyProblem> findByDailyAndSequence(Daily daily, int sequence);
}
