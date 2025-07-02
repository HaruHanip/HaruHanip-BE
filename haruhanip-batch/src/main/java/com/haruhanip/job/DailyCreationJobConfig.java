package com.haruhanip.job;

import com.haruhanip.partitioner.CategoryPartitioner;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DailyCreationJobConfig {

    /**
     * Daily Creation Job: 각 카테고리별 Daily 묶음 생성
     */
    @Bean
    public Job dailyCreationJob(
            JobRepository jobRepository,
            @Qualifier("partitionedDailyStep") Step partitionedDailyStep
    ) {
        return new JobBuilder("dailyCreationJob", jobRepository)
                .start(partitionedDailyStep)
                .build();
    }

    /**
     * 파티셔닝된 Step: 카테고리별 병렬 처리
     */
    @Bean
    public Step partitionedDailyStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            TaskExecutor dailyTaskExecutor,
            CategoryPartitioner categoryPartitioner,
            @Qualifier("createDailyStep") Step createDailyStep
    ) {
        return new StepBuilder("partitionedDailyStep", jobRepository)
                .partitioner("createDailyStep", categoryPartitioner)
                .step(createDailyStep)
                .gridSize(4)
                .taskExecutor(dailyTaskExecutor)
                .build();
    }

    /**
     * TaskExecutor for partitioned steps
     */
    @Bean
    public TaskExecutor dailyTaskExecutor() {
        SimpleAsyncTaskExecutor exec = new SimpleAsyncTaskExecutor("daily-part-");
        exec.setConcurrencyLimit(4);
        return exec;
    }
}
