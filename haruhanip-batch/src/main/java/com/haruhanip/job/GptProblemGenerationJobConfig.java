package com.haruhanip.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import com.haruhanip.partitioner.CategoryPartitioner;

@Configuration
@RequiredArgsConstructor
public class GptProblemGenerationJobConfig {

    private final JobRepository jobRepository;
    private final CategoryPartitioner categoryPartitioner;

    /**
     * 실제 배치 흐름을 정의하는 Job 빈
     */
    @Bean
    public Job gptProblemGenerationJob(Step partitionedGptStep) {
        return new JobBuilder("gptProblemGenerationJob", jobRepository)
                .start(partitionedGptStep)
                .build();
    }

    /**
     * 파티셔닝된 Step 빈
     */
    @Bean
    public Step partitionedGptStep(TaskExecutor gptTaskExecutor,
                                   Step gptStep) {
        return new StepBuilder("partitionedGptStep", jobRepository)
                .partitioner("gptStep", categoryPartitioner)
                .step(gptStep)
                .gridSize(4)
                .taskExecutor(gptTaskExecutor)
                .build();
    }

    /**
     * 병렬 실행을 위한 TaskExecutor
     */
    @Bean
    public TaskExecutor gptTaskExecutor() {
        SimpleAsyncTaskExecutor exec = new SimpleAsyncTaskExecutor("gpt-part-");
        exec.setConcurrencyLimit(4);
        return exec;
    }
}
