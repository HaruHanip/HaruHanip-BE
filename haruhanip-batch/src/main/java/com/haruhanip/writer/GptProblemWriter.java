package com.haruhanip.writer;

import com.haruhanip.domains.problem.domain.Problem;
import com.haruhanip.domains.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class GptProblemWriter implements ItemWriter<List<Problem>> {

    private final ProblemRepository problemRepo;

    @Override
    public void write(Chunk<? extends List<Problem>> chunks) {
        for (List<Problem> problems : chunks) {
            if (!problems.isEmpty()) {
                problemRepo.saveAll(problems);
            }
        }
    }
}

