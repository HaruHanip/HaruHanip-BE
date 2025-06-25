package com.clubnu.domains.problem.domain;

import com.clubnu.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "problem_option")
public class ProblemOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_option_id")
    private Long problemOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(name = "option_index", nullable = false)
    private Integer optionIndex;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public ProblemOption(Integer optionIndex, String content) {
        this.optionIndex = optionIndex;
        this.content = content;
    }

    protected void setProblem(Problem problem) {
        this.problem = problem;
    }
}
