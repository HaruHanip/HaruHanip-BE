package com.haruhanip.api.user.dto;

import com.haruhanip.api.challenge.dto.ChallengeResponse;
import com.haruhanip.domains.challenge.domain.Challenge;
import com.haruhanip.domains.user.domain.User;
import com.haruhanip.domains.user.domain.UserChallengeProgress;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserChallengeProgressResponse {

    private Long userChallengeProgressId;
    private UserProfileResponse userProfileResponse;
    private ChallengeResponse challengeResponse;
    private LocalDate startDate;
    private LocalDate endDate;
    private int currentStreak;
    private boolean completed;

    @Builder
    public UserChallengeProgressResponse(Long userChallengeProgressId, UserProfileResponse userProfileResponse,
                                         ChallengeResponse challengeResponse, LocalDate startDate,
                                         LocalDate endDate, int currentStreak, boolean completed) {
        this.userChallengeProgressId = userChallengeProgressId;
        this.userProfileResponse = userProfileResponse;
        this.challengeResponse = challengeResponse;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentStreak = currentStreak;
        this.completed = completed;
    }

    public static UserChallengeProgressResponse from(UserChallengeProgress userChallengeProgress) {
        User user = userChallengeProgress.getUser();
        Challenge challenge = userChallengeProgress.getChallenge();

        return UserChallengeProgressResponse.builder()
                .userChallengeProgressId(userChallengeProgress.getUserChallengeProgressId())
                .userProfileResponse(UserProfileResponse.from(user))
                .challengeResponse(ChallengeResponse.from(challenge))
                .startDate(userChallengeProgress.getStartDate())
                .endDate(userChallengeProgress.getEndDate())
                .currentStreak(userChallengeProgress.getCurrentStreak())
                .completed(userChallengeProgress.isCompleted())
                .build();
    }
}
