package com.haruhanip.api.user.dto;

public record UserProblemSolveRequest(
        Long problemId,
        Integer selectedOptionIndex,
        Boolean isCorrect
) {
}
