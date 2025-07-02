package com.haruhanip.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class DailyJobScheduler {

    private final JobLauncher jobLauncher;

    @Qualifier("gptProblemGenerationJob")
    private final Job gptProblemGenerationJob;

    @Qualifier("dailyCreationJob")
    private final Job dailyCreationJob;

    /**
     * 매일 3시에
     *  1) GPT로 오늘 문제(카테고리별 10문제) 생성 Job 실행
     *  2) 그 결과로 Daily 묶음 생성 Job 실행
     */
    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    public void scheduleDailyGeneration() throws Exception {
        // unique parameter, 두 Job이 같은 파라미터를 공유해도 되지만
        // 필요하다면 이름을 달리할 수도 있습니다.
        JobParameters params = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();

        // 1) GPT 문제 생성
        jobLauncher.run(gptProblemGenerationJob, params);

        // 2) Today Daily 생성
        jobLauncher.run(dailyCreationJob, params);
    }
}
