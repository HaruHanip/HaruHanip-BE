package com.haruhanip.domains.challenge.repository;

import com.haruhanip.domains.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
