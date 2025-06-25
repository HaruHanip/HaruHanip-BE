package com.clubnu.api.problem.dto;

import com.clubnu.domains.problem.domain.Problem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProblemResponse {

    private final Long problemId;

    private final String title;

    private final String description;

    private final String difficulty;

    private final Integer correctOption;

    private final String explanation;

    private final Long categoryId;

    private final String categoryName;

    private final List<ProblemOptionResponse> options;

    @Builder
    public ProblemResponse(Long problemId, String title, String description, String difficulty,
                           Integer correctOption, String explanation, Long categoryId,
                           String categoryName, List<ProblemOptionResponse> options) {
        this.problemId = problemId;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.correctOption = correctOption;
        this.explanation = explanation;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.options = options;
    }

    public static ProblemResponse from(Problem problem) {
        return ProblemResponse.builder()
                .problemId(problem.getProblemId())
                .title(problem.getTitle())
                .description(problem.getDescription())
                .difficulty(problem.getDifficulty().name())
                .correctOption(problem.getCorrectOption())
                .explanation(problem.getExplanation())
                .categoryId(problem.getCategory().getCategoryId())
                .categoryName(problem.getCategory().getName())
                .options(problem.getOptions().stream()
                        .map(ProblemOptionResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }


}
