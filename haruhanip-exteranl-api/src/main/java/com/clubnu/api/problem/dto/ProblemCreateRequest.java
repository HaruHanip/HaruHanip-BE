package com.clubnu.api.problem.dto;

import com.clubnu.domains.problem.domain.ProblemDifficulty;

import java.util.List;

public record ProblemCreateRequest(
        String title,
        String description,
        ProblemDifficulty difficulty,
        Integer correctOption,
        String explanation,
        Long categoryId,
        List<ProblemOptionCreateRequest> problemOptions
) {
}
