package com.haruhanip.writer;

import com.haruhanip.domains.daily.domain.Daily;
import com.haruhanip.domains.daily.repository.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

@Component("dailyWriter")
@StepScope
@RequiredArgsConstructor
public class DailyItemWriter implements ItemWriter<Daily> {

    private final DailyRepository dailyRepository;

    @Override
    public void write(Chunk<? extends Daily> items) throws Exception {
        // Chunk implements Iterable<Daily>, so you can pass it directly
        if (items.iterator().hasNext()) {
            dailyRepository.saveAll(items);
        }
    }
}
