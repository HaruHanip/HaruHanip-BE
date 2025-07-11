package com.haruhanip.domains.challenge.repository;

import com.haruhanip.domains.challenge.domain.ChallengeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeCategoryRepository extends JpaRepository<ChallengeCategory, Long> {
}
