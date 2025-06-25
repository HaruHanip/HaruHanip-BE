package com.clubnu.domains.daily.domain;

import com.clubnu.domains.problem.domain.Problem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "daily_problem")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_id", nullable = false)
    private Daily daily;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(nullable = false)
    private int sequence;

    @Builder
    public DailyProblem(Daily daily, Problem problem, int sequence) {
        this.daily = daily;
        this.problem = problem;
        this.sequence = sequence;
    }
}

