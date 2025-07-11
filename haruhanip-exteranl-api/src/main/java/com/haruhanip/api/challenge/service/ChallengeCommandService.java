package com.haruhanip.api.challenge.service;

import com.haruhanip.api.challenge.dto.ChallengeCreateRequest;
import com.haruhanip.domains.challenge.domain.Challenge;
import com.haruhanip.domains.challenge.domain.ChallengeType;
import com.haruhanip.domains.challenge.repository.ChallengeRepository;
import com.haruhanip.domains.challenge.repository.ChallengeTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeCommandService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeTypeRepository challengeTypeRepository;

    @Transactional
    public void createChallenge(Long challengeTypeId , ChallengeCreateRequest request){
        ChallengeType type = challengeTypeRepository.findById(challengeTypeId)
                        .orElseThrow(()-> new IllegalArgumentException("Challenge type not found"));

        challengeRepository.save(Challenge.builder()
                        .name(request.name())
                        .challengeType(type)
                        .description(request.description())
                        .durationDays(request.duration())
                        .targetAll(request.targetAll())
                .build());
    }
}
