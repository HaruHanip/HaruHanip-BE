package com.haruhanip.api.challenge.dto;

public record ChallengeCreateRequest(
        String name,
        String description,
        Integer duration,
        boolean targetAll
) {
}
