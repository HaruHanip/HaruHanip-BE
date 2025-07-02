package com.haruhanip.api.problem.service;

import com.haruhanip.api.problem.dto.ProblemResponse;
import com.haruhanip.domains.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemQueryService {

    private final ProblemRepository problemRepository;

    @Transactional(readOnly = true)
    public ProblemResponse getProblemById(Long id) {
        return problemRepository.findById(id)
                .map(ProblemResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Problem not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ProblemResponse> getAllProblems() {
        return problemRepository.findAll()
                .stream()
                .map(ProblemResponse::from)
                .collect(Collectors.toList());
    }
}
