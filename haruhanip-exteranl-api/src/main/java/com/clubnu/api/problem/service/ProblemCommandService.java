package com.clubnu.api.problem.service;

import com.clubnu.api.problem.dto.ProblemCreateRequest;
import com.clubnu.api.problem.dto.ProblemOptionCreateRequest;
import com.clubnu.domains.category.domain.Category;
import com.clubnu.domains.category.repository.CategoryRepository;
import com.clubnu.domains.problem.domain.Problem;
import com.clubnu.domains.problem.domain.ProblemOption;
import com.clubnu.domains.problem.repository.ProblemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
