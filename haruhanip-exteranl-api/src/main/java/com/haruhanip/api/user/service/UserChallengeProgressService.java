package com.haruhanip.api.user.service;

import com.haruhanip.api.user.dto.CreateUserChallengeProgressRequest;
import com.haruhanip.api.user.dto.UserChallengeProgressResponse;
import com.haruhanip.domains.challenge.domain.Challenge;
import com.haruhanip.domains.challenge.repository.ChallengeRepository;
import com.haruhanip.domains.user.domain.User;
import com.haruhanip.domains.user.domain.UserChallengeProgress;
import com.haruhanip.domains.user.repository.UserChallengeProgressRepository;
import com.haruhanip.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChallengeProgressService {

    private final UserChallengeProgressRepository userChallengeProgressRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public void createUserChallengeProgress(Long userId, CreateUserChallengeProgressRequest request) {
        User target = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Challenge challenge = challengeRepository.findById(request.challengeId())
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

        userChallengeProgressRepository.save(
                UserChallengeProgress.builder()
                        .challenge(challenge)
                        .user(target)
                        .startDate(LocalDate.now())
                        .currentStreak(0)
                        .completed(false)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public UserChallengeProgressResponse getUserChallengeProgress(Long userId, Long challengeId) {
        return UserChallengeProgressResponse.from(userChallengeProgressRepository.findByUserUserIdAndChallengeChallengeId(userId, challengeId)
                .orElseThrow(() -> new IllegalArgumentException("User challenge progress not found")));
    }

    @Transactional(readOnly = true)
    public List<UserChallengeProgressResponse> getAllUserChallengeProgress(Long userId) {
        return userChallengeProgressRepository.findAllByUserUserId(userId)
                .stream()
                .map(UserChallengeProgressResponse::from)
                .toList();
    }
}
