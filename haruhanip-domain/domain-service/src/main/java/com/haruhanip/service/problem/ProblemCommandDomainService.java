package com.haruhanip.service.problem;

import com.haruhanip.domains.problem.domain.Problem;
import com.haruhanip.domains.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemCommandDomainService {

    private final ProblemRepository problemRepository;

    @Transactional
    public void createProblem(Problem problem) {
        problemRepository.save(problem);
    }

    @Transactional
    public void deleteProblem(Problem problem) {
        problemRepository.delete(problem);
    }
}
