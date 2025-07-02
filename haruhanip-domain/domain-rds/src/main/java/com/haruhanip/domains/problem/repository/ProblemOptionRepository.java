package com.haruhanip.domains.problem.repository;

import com.haruhanip.domains.problem.domain.ProblemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemOptionRepository extends JpaRepository<ProblemOption, Long> {
}
