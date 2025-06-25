package com.clubnu.api.problem.api;

import com.clubnu.api.problem.dto.ProblemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Problem Query API", description = "문제 조회 API")
public interface ProblemQueryApi {

    @Operation(summary = "단일 문제 조회", description = "문제 ID로 단일 문제를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Problem Response Example", value = """
                            {
                               "problemId": 1,
                               "title": "Two Sum",
                               "description": "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.",
                               "difficulty": "EASY",
                               "correctOption": 2,
                               "explanation": "Use a one-pass hash map to record each number's complement and its index.",
                               "categoryId": 3,
                               "categoryName": "Algorithms",
                               "options": [
                                 {
                                   "optionIndex": 0,
                                   "content": "Brute force: check all pairs"
                                 },
                                 {
                                   "optionIndex": 1,
                                   "content": "Sort and two pointers"
                                 },
                                 {
                                   "optionIndex": 2,
                                   "content": "One-pass hash map"
                                 },
                                 {
                                   "optionIndex": 3,
                                   "content": "Binary search for complement"
                                 }
                               ]
                             }
                    """)
            })),
    })
    public ResponseEntity<ProblemResponse> getProblemById(@PathVariable Long problemId);

    @Operation(summary = "모든 문제 조회", description = "모든 문제를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Problem List Response Example", value = """
                            [
                              {
                                "problemId": 1,
                                "title": "Two Sum",
                                "description": "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.",
                                "difficulty": "EASY",
                                "correctOption": 2,
                                "explanation": "Use a one-pass hash map to record each number's complement and its index.",
                                "categoryId": 3,
                                "categoryName": "Algorithms",
                                "options": [
                                  {
                                    "optionIndex": 0,
                                    "content": "Brute force: check all pairs"
                                  },
                                  {
                                    "optionIndex": 1,
                                    "content": "Sort and two pointers"
                                  },
                                  {
                                    "optionIndex": 2,
                                    "content": "One-pass hash map"
                                  },
                                  {
                                    "optionIndex": 3,
                                    "content": "Binary search for complement"
                                  }
                                ]
                              }
                            ]
                    """)
            })),
    })
    public ResponseEntity<List<ProblemResponse>> getAllProblems();
}
