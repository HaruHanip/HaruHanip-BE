package com.haruhanip.domains.challenge.domain;

import com.haruhanip.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Challenge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chanllege_type_id", nullable = false)
    private ChallengeType challengeType;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "duration_days", nullable = false)
    private int durationDays;

    @Column(name = "target_all", nullable = false)
    private boolean targetAll;

    @Builder
    public Challenge(String name, ChallengeType challengeType, String description, int durationDays, boolean targetAll) {
        this.name = name;
        this.challengeType = challengeType;
        this.description = description;
        this.durationDays = durationDays;
        this.targetAll = targetAll;
    }

}
