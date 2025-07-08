package com.haruhanip.api.daily.controller;

import com.haruhanip.api.daily.api.DailyProblemQueryApi;
import com.haruhanip.api.daily.dto.DailyProblemResponse;
import com.haruhanip.api.daily.service.DailyProblemQueryService;
import com.haruhanip.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/daily-problem")
public class DailyProblemQueryController implements DailyProblemQueryApi {

    private final DailyProblemQueryService dailyProblemQueryService;

    @GetMapping("/daily/{dailyId}")
    public ResponseEntity<DailyProblemResponse> getDailyProblem(
            @PathVariable Long dailyId, @RequestParam int sequence) {
        DailyProblemResponse response = dailyProblemQueryService.getDailyProblem(dailyId, sequence);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<DailyProblemResponse> getDailyProblemByDate(
            @PathVariable Long categoryId, @RequestParam int sequence) {
        LocalDate date = LocalDate.now(); // Assuming you want to get today's daily problem
        DailyProblemResponse response = dailyProblemQueryService.getDailyProblemByDate(categoryId, date, sequence);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily/{dailyId}/solving")
    public ResponseEntity<DailyProblemResponse> getDailyProblemSolving(
            @PathVariable Long dailyId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        DailyProblemResponse response = dailyProblemQueryService.getNextDailyProblem(dailyId, userDetails.getId());
        return ResponseEntity.ok(response);
    }
}
