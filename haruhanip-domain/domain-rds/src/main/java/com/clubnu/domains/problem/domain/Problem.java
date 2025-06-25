package com.clubnu.domains.problem.domain;

import com.clubnu.domains.category.domain.Category;
import com.clubnu.domains.daily.domain.Daily;
import com.clubnu.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "problem")
public class Problem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long problemId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "difficulty", nullable = false)
    private ProblemDifficulty difficulty;

    @Column(name = "correct_option", nullable = false)
    private Integer correctOption;

    @Column(name = "explanation", nullable = false)
    private String explanation;

    @OneToMany(
            mappedBy = "problem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProblemOption> options = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder(toBuilder = true)
    public Problem(String title, String description, ProblemDifficulty difficulty, Integer correctOption, String explanation, Category category) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.correctOption = correctOption;
        this.explanation = explanation;
        this.category = category;
    }

    public void addOption(ProblemOption option) {
        option.setProblem(this);
        options.add(option);
    }

}
