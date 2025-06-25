package com.clubnu.api.daily.controller;

import com.clubnu.api.daily.api.DailyCommandApi;
import com.clubnu.api.daily.dto.DailyCreateRequest;
import com.clubnu.api.daily.service.DailyCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/daily")
public class DailyCommandController implements DailyCommandApi {

    private final DailyCommandService dailyCommandService;

    @PostMapping
    public ResponseEntity<Void> createDaily(@RequestBody @Valid DailyCreateRequest request) {
        dailyCommandService.createDaily(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{dailyId}")
    public ResponseEntity<Void> deleteDaily(@PathVariable Long dailyId) {
        dailyCommandService.deleteDaily(dailyId);
        return ResponseEntity.ok().build();
    }
}
