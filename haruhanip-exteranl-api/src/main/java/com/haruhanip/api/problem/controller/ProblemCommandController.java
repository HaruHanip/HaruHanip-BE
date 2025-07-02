package com.haruhanip.api.problem.controller;

import com.haruhanip.api.problem.api.ProblemCommandApi;
import com.haruhanip.api.problem.dto.ProblemCreateRequest;
import com.haruhanip.api.problem.service.ProblemCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/problem")
public class ProblemCommandController implements ProblemCommandApi {

    private final ProblemCommandService problemCommandService;

    @PostMapping
    public ResponseEntity<Void> createProblem(@RequestBody @Valid ProblemCreateRequest request) {
        problemCommandService.createProblem(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{problemId}")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long problemId) {
        problemCommandService.deleteProblem(problemId);
        return ResponseEntity.ok().build();
    }
}
