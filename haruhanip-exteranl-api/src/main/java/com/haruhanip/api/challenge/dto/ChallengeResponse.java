package com.haruhanip.api.challenge.dto;

import com.haruhanip.domains.challenge.domain.Challenge;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeResponse {
    private Long challengeId;
    private String name;
    private ChallengeTypeResponse challengeTypeResponse;
    private String description;
    private boolean targetAll;

    @Builder
    public ChallengeResponse(Long challengeId, String name, ChallengeTypeResponse challengeTypeResponse, String description, boolean targetAll) {
        this.challengeId = challengeId;
        this.name = name;
        this.challengeTypeResponse = challengeTypeResponse;
        this.description = description;
        this.targetAll = targetAll;
    }

    public static ChallengeResponse from(Challenge challenge) {
        return ChallengeResponse.builder()
                .challengeId(challenge.getChallengeId())
                .name(challenge.getName())
                .challengeTypeResponse(ChallengeTypeResponse.from(challenge.getChallengeType()))
                .description(challenge.getDescription())
                .targetAll(challenge.isTargetAll())
                .build();
    }
}
