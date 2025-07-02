package com.haruhanip.api.problem.dto;

public record ProblemOptionCreateRequest(
        Integer optionIndex,
        String content
) {
}
