package com.haruhanip.api.challenge.controller;

import com.haruhanip.api.challenge.api.ChallengeQueryApi;
import com.haruhanip.api.challenge.dto.ChallengeResponse;
import com.haruhanip.api.challenge.service.ChallengeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
@Validated
public class ChallengeQueryController implements ChallengeQueryApi {

    private final ChallengeQueryService challengeQueryService;

    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> getAllChallenges() {
        List<ChallengeResponse> responses = challengeQueryService.getAllChallenges();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<ChallengeResponse> getChallenge(@PathVariable Long challengeId) {
        return ResponseEntity.ok(challengeQueryService.getChallenge(challengeId));
    }
}
