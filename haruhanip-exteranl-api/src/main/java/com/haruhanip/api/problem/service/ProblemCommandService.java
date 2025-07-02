package com.haruhanip.api.problem.service;

import com.haruhanip.api.problem.dto.ProblemCreateRequest;
import com.haruhanip.api.problem.dto.ProblemOptionCreateRequest;
import com.haruhanip.domains.category.domain.Category;
import com.haruhanip.domains.category.repository.CategoryRepository;
import com.haruhanip.domains.problem.domain.Problem;
import com.haruhanip.domains.problem.domain.ProblemOption;
import com.haruhanip.domains.problem.repository.ProblemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemCommandService {

    private final ProblemRepository problemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createProblem(ProblemCreateRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        Problem target = Problem.builder()
                .category(category)
                .title(request.title())
                .description(request.description())
                .difficulty(request.difficulty())
                .correctOption(request.correctOption())
                .explanation(request.explanation())
                .build();

        for (ProblemOptionCreateRequest optionRequest : request.problemOptions()) {
            target.addOption(
                    ProblemOption.builder()
                            .optionIndex(optionRequest.optionIndex())
                            .content(optionRequest.content())
                            .build()
            );
        }

        problemRepository.save(target);
    }

    @Transactional
    public void deleteProblem(Long problemId) {
        if (!problemRepository.existsById(problemId)) {
            throw new EntityNotFoundException("Problem not found: " + problemId);
        }
        problemRepository.deleteById(problemId);
    }
}
