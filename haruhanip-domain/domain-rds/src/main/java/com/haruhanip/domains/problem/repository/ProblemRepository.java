package com.haruhanip.domains.problem.repository;

import com.haruhanip.domains.category.domain.Category;
import com.haruhanip.domains.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByCategory(Category category);
    List<Problem> findAllByCategoryAndCreatedAtBetween(
            Category category,
            LocalDateTime start,
            LocalDateTime end);
}
