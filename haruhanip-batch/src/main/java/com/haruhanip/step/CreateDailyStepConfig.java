package com.haruhanip.step;

import com.haruhanip.reader.CategoryIdReader;
import com.haruhanip.processor.DailyItemProcessor;
import com.haruhanip.writer.DailyItemWriter;
import com.haruhanip.domains.daily.domain.Daily;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateDailyStepConfig extends DefaultBatchConfiguration {

    @Bean(name = "createDailyStep")
    public Step createDailyStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            CategoryIdReader categoryIdReader,
            DailyItemProcessor dailyItemProcessor,
            DailyItemWriter dailyWriter
    ) {
        return new StepBuilder("createDailyStep", jobRepository)
                .<Long, Daily>chunk(1, transactionManager)
                .reader(categoryIdReader)
                .processor(dailyItemProcessor)
                .writer(dailyWriter)
                .build();
    }
}
