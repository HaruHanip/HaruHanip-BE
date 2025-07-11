package com.haruhanip.domains.user.domain;

import com.haruhanip.domains.challenge.domain.Challenge;
import com.haruhanip.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "user_challenge_progress",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_challenge",
                columnNames = { "user_id", "challenge_id" }
        )
)
public class UserChallengeProgress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_challenge_progress_id")
    private Long userChallengeProgressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "current_streak", nullable = false)
    private int currentStreak;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Builder
    public UserChallengeProgress(User user, Challenge challenge, LocalDate startDate, int currentStreak, boolean completed) {
        this.user = user;
        this.challenge = challenge;
        this.startDate = startDate;
        this.currentStreak = currentStreak;
        this.completed = completed;
    }
}
