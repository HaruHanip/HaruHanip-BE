package com.haruhanip.domains.challenge.repository;

import com.haruhanip.domains.challenge.domain.ChallengeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeTypeRepository extends JpaRepository<ChallengeType, Long> {
}
