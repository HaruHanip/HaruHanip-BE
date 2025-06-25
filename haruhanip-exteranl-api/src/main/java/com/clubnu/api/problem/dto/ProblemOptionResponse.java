package com.clubnu.api.problem.dto;

import com.clubnu.domains.problem.domain.ProblemOption;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProblemOptionResponse {
    @JsonProperty("option_index")
    private final Integer optionIndex;
    private final String content;

    @Builder
    public ProblemOptionResponse(Integer optionIndex, String content) {
        this.optionIndex = optionIndex;
        this.content = content;
    }

    public static ProblemOptionResponse from(ProblemOption option) {
        return ProblemOptionResponse.builder()
                .optionIndex(option.getOptionIndex())
                .content(option.getContent())
                .build();
    }
}
