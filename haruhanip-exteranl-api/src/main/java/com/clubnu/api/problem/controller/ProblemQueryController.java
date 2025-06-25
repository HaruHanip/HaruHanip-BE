package com.clubnu.api.problem.controller;

import com.clubnu.api.problem.api.ProblemQueryApi;
import com.clubnu.api.problem.dto.ProblemResponse;
import com.clubnu.api.problem.service.ProblemQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/problem")
public class ProblemQueryController implements ProblemQueryApi {

    private final ProblemQueryService problemQueryService;

    @GetMapping("/{problemId}")
    public ResponseEntity<ProblemResponse> getProblemById(@PathVariable Long problemId) {
        ProblemResponse response = problemQueryService.getProblemById(problemId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProblemResponse>> getAllProblems() {
        List<ProblemResponse> responses = problemQueryService.getAllProblems();
        return ResponseEntity.ok(responses);
    }
}
