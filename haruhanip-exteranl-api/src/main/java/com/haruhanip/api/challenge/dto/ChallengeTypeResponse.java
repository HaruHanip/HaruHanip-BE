package com.haruhanip.api.challenge.dto;

import com.haruhanip.domains.challenge.domain.ChallengeType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeTypeResponse {
    private Long challengeTypeId;
    private String code;
    private String name;

    @Builder
    public ChallengeTypeResponse(Long challengeTypeId, String code, String name) {
        this.challengeTypeId = challengeTypeId;
        this.code = code;
        this.name = name;
    }

    public static ChallengeTypeResponse from(ChallengeType challengeType) {
        return ChallengeTypeResponse.builder()
                .challengeTypeId(challengeType.getChallengeTypeId())
                .code(challengeType.getCode())
                .name(challengeType.getName())
                .build();
    }
}
