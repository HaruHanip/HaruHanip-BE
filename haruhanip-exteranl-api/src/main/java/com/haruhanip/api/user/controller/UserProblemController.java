package com.haruhanip.api.user.controller;

import com.haruhanip.api.user.api.UserProblemApi;
import com.haruhanip.api.user.dto.UserDailyResultResponse;
import com.haruhanip.api.user.dto.UserProblemSolveRequest;
import com.haruhanip.api.user.service.UserProblemService;
import com.haruhanip.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/problem/solve")
public class UserProblemController implements UserProblemApi {

    private final UserProblemService userProblemService;

    @PostMapping
    public ResponseEntity<Void> solveProblem(
            @AuthenticationPrincipal CustomUserDetails userDetails
            , @RequestBody @Valid UserProblemSolveRequest request
    ) {
        userProblemService.solveProblem(userDetails.getId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/result/{dailyId}")
    public ResponseEntity<UserDailyResultResponse> getUserDailyResult(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long dailyId
    ) {
        return ResponseEntity.ok(userProblemService.getUserDailyResult(userDetails.getId(), dailyId));
    }

}
