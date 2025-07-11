package com.haruhanip.api.challenge.controller;

import com.haruhanip.api.challenge.api.ChallengeCommandApi;
import com.haruhanip.api.challenge.dto.ChallengeCreateRequest;
import com.haruhanip.api.challenge.service.ChallengeCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
@Validated
public class ChallengeCommandController implements ChallengeCommandApi {

    private final ChallengeCommandService challengeCommandService;

    @PostMapping("/type/{challengeTypeId}")
    public ResponseEntity<Void> createChallenge(@PathVariable Long challengeTypeId,
                                               @RequestBody @Valid ChallengeCreateRequest request) {
        challengeCommandService.createChallenge(challengeTypeId, request);
        return  ResponseEntity.ok().build();
    }

}
