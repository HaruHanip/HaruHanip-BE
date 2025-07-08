package com.haruhanip.domains.user.domain;

import com.haruhanip.domains.daily.domain.Daily;
import com.haruhanip.domains.daily.domain.DailyProblem;
import com.haruhanip.domains.problem.domain.Problem;
import com.haruhanip.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_problem_history")
public class UserProblemHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_problem_history_id")
    private Long userProblemHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_problem_id", nullable = false)
    private DailyProblem dailyProblem;

    @Column(name = "selected_option_index", nullable = false)
    private Integer selectedOptionIndex;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Builder
    public UserProblemHistory(User user, DailyProblem dailyProblem, Integer selectedOptionIndex, Boolean isCorrect) {
        this.user = user;
        this.dailyProblem = dailyProblem;
        this.selectedOptionIndex = selectedOptionIndex;
        this.isCorrect = isCorrect;
    }
}
