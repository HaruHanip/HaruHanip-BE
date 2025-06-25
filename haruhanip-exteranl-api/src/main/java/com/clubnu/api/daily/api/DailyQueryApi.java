package com.clubnu.api.daily.api;

import com.clubnu.api.daily.dto.DailyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Daily Query API", description = "데일리 조회 API")
public interface DailyQueryApi {

    @Operation(summary = "데일리 조회", description = "데일리 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Category Response Example", value = """
                            {
                              "daily_id": 5,
                              "daily_date": "2025-06-26",
                              "category_id": 2,
                              "category_name": "OS",
                              "problems": [
                                {
                                  "sequence": 1,
                                  "problem_id": 101,
                                  "title": "Process Scheduling"
                                },
                                {
                                  "sequence": 2,
                                  "problem_id": 102,
                                  "title": "Memory Management"
                                },
                                {
                                  "sequence": 3,
                                  "problem_id": 103,
                                  "title": "Page Replacement Algorithms"
                                }
                              ]
                            }
                                            
                """)
            })),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "데일리 없음")
    })
    public ResponseEntity<DailyResponse> getDaily(Long dailyId);

    @Operation(summary = "카테고리별 데일리 조회", description = "특정 카테고리의 데일리를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Daily by Category Response Example", value = """
                            {
                              "daily_id": 5,
                              "daily_date": "2025-06-26",
                              "category_id": 2,
                              "category_name": "OS",
                              "problems": [
                                {
                                  "sequence": 1,
                                  "problem_id": 101,
                                  "title": "Process Scheduling"
                                },
                                {
                                  "sequence": 2,
                                  "problem_id": 102,
                                  "title": "Memory Management"
                                },
                                {
                                  "sequence": 3,
                                  "problem_id": 103,
                                  "title": "Page Replacement Algorithms"
                                }
                              ]
                            }
                """)
            })),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "데일리 또는 카테고리 없음")
    })
    public ResponseEntity<DailyResponse> getDailyByDate(
            @PathVariable Long categoryId);
}
