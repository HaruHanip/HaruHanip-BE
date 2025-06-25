package com.clubnu.api.problem.dto;

public record ProblemOptionCreateRequest(
        Integer optionIndex,
        String content
) {
}
