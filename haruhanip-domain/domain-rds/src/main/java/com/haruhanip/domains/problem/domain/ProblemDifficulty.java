package com.haruhanip.domains.problem.domain;

public enum ProblemDifficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String description;

    ProblemDifficulty(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
