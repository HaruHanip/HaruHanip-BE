package com.haruhanip.dto;

import com.haruhanip.domains.problem.domain.ProblemDifficulty;

import java.util.List;

public record ProblemDto(
        String title,
        String description,
        List<OptionDto> options,
        int correctOption,
        String explanation,
        ProblemDifficulty difficulty
) {}
