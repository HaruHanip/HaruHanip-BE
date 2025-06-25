package com.clubnu.domains.daily.domain;

import com.clubnu.domains.category.domain.Category;
import com.clubnu.domains.problem.domain.Problem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Daily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private Long dailyId;

    @Column(name = "daily_date", nullable = false)
    private LocalDate dailyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(
            mappedBy = "daily",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DailyProblem> dailyProblems = new ArrayList<>();

    @Builder
    public Daily(LocalDate dailyDate, Category category) {
        this.dailyDate = dailyDate;
        this.category = category;
    }

    public void addProblem(Problem problem) {
        DailyProblem dailyProblem = DailyProblem.builder()
                .daily(this)
                .problem(problem)
                .sequence(dailyProblems.size() + 1) // Sequence is 1-based
                .build();
        this.dailyProblems.add(dailyProblem);
    }
}
