package com.haruhanip.domains.user.repository;

import com.haruhanip.domains.user.domain.UserChallengeProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserChallengeProgressRepository extends JpaRepository<UserChallengeProgress, Long> {
    Optional<UserChallengeProgress> findByUserUserIdAndChallengeChallengeId(Long userId, Long challengeId);

    List<UserChallengeProgress> findAllByUserUserId(Long userId);
}
