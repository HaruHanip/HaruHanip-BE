package com.haruhanip.step;

import com.haruhanip.domains.problem.domain.Problem;
import com.haruhanip.processor.GptProblemProcessor;
import com.haruhanip.reader.CategoryIdReader;
import com.haruhanip.writer.GptProblemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GptProblemStepConfig extends DefaultBatchConfiguration {

    /**
     * 실제 GPT 호출 → DB 저장을 수행할 Step 빈
     */
    @Bean
    public Step gptStep(JobRepository jobRepository,
                        PlatformTransactionManager txManager,
                        CategoryIdReader categoryIdReader,
                        GptProblemProcessor gptProcessor,
                        GptProblemWriter gptWriter) {
        return new StepBuilder("gptStep", jobRepository)
                .<Long, List<Problem>>chunk(1, txManager)
                .reader(categoryIdReader)
                .processor(gptProcessor)
                .writer(gptWriter)
                .build();
    }
}
