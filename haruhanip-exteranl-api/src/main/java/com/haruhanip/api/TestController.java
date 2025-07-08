package com.haruhanip.api;

import com.haruhanip.scheduler.DailyJobScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final DailyJobScheduler dailyJobScheduler;

    @GetMapping("/run-daily-job")
    public ResponseEntity<String> runDailyJob() throws Exception {
        dailyJobScheduler.scheduleDailyGeneration();
        return ResponseEntity.ok("Daily job has been triggered.");
    }
}
