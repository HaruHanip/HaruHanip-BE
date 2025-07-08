package com.haruhanip.api.daily.controller;

import com.haruhanip.api.daily.api.DailyQueryApi;
import com.haruhanip.api.daily.dto.DailyIdResponse;
import com.haruhanip.api.daily.dto.DailyResponse;
import com.haruhanip.api.daily.service.DailyQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/daily")
public class DailyQueryController implements DailyQueryApi {

    private final DailyQueryService dailyQueryService;

    @GetMapping("/{dailyId}")
    public ResponseEntity<DailyResponse> getDaily(@PathVariable Long dailyId) {
        DailyResponse response = dailyQueryService.getDaily(dailyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<DailyResponse> getDailyByDate(
            @PathVariable Long categoryId) {
        LocalDate date = LocalDate.now(); // Assuming you want to get today's daily
        DailyResponse response = dailyQueryService.getDailyByDate(categoryId, date);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/today/category/{categoryId}")
    public ResponseEntity<DailyIdResponse> getTodayDailyId(
            @PathVariable Long categoryId
    ) {
        LocalDate date = LocalDate.now(); // Assuming you want to get today's daily
        DailyIdResponse response = dailyQueryService.getTodayDailyId(date, categoryId);
        return ResponseEntity.ok(response);
    }
}
