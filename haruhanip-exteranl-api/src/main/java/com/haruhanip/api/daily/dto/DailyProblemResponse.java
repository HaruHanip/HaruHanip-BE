package com.haruhanip.api.daily.dto;

import com.haruhanip.api.problem.dto.ProblemResponse;
import com.haruhanip.domains.daily.domain.DailyProblem;
import com.haruhanip.domains.problem.domain.Problem;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DailyProblemResponse {

    private final int sequence;

    private final ProblemResponse problem;


    @Builder
    public DailyProblemResponse(int sequence, Problem problem, String title) {
        this.sequence = sequence;
        this.problem = ProblemResponse.from(problem);
    }

    public static DailyProblemResponse from(DailyProblem dailyProblem) {
        return DailyProblemResponse.builder()
                .sequence(dailyProblem.getSequence())
                .problem(dailyProblem.getProblem())
                .build();
    }
}
