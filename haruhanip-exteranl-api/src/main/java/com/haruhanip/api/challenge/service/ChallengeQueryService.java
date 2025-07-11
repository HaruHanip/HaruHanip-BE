package com.haruhanip.api.challenge.service;

import com.haruhanip.api.challenge.dto.ChallengeResponse;
import com.haruhanip.domains.challenge.domain.Challenge;
import com.haruhanip.domains.challenge.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeQueryService {

    private final ChallengeRepository challengeRepository;


    @Transactional(readOnly = true)
    public ChallengeResponse getChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

        return ChallengeResponse.from(challenge);
    }

    @Transactional(readOnly = true)
    public List<ChallengeResponse> getAllChallenges() {
        List<Challenge> challenges = challengeRepository.findAll();
        return challenges.stream()
                .map(ChallengeResponse::from)
                .toList();
    }
}
