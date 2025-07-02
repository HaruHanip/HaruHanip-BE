package com.haruhanip.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haruhanip.domains.category.domain.Category;
import com.haruhanip.domains.category.repository.CategoryRepository;
import com.haruhanip.domains.problem.domain.Problem;
import com.haruhanip.domains.problem.domain.ProblemOption;
import com.haruhanip.dto.ProblemDto;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class GptProblemProcessor implements ItemProcessor<Long, List<Problem>> {

    private final CategoryRepository categoryRepository;
    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;

    @Value("${app.gpt.prompt-template}")
    private String promptTemplate;

    @Value("${app.gpt.system-message}")
    private String systemMessage;

    @Value("${app.gpt.model}")
    private String modelName;

    @Value("${app.gpt.temperature}")
    private double temperature;

    @Value("${app.gpt.max-completion-tokens}")
    private int maxTokens;

    @Override
    public List<Problem> process(Long categoryId) throws Exception {
        // 1) 카테고리 조회
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));

        // 2) 프롬프트 완성
        String userPrompt = String.format(promptTemplate, category.getName());

        Prompt prompt = new Prompt(
                List.of(
                        new SystemMessage(systemMessage),
                        new UserMessage(userPrompt)
                ),
                OpenAiChatOptions.builder()
                        .model(modelName)
                        .temperature(temperature)
                        .maxCompletionTokens(maxTokens)
                        .build()
        );

        // 3) GPT 호출
        ChatResponse aiResponse = chatModel.call(prompt);
        String content = aiResponse.getResult().getOutput().getText();

        // 4) JSON → DTO 파싱
        List<ProblemDto> dtos = objectMapper.readValue(
                content,
                new TypeReference<>() {}
        );

        // 5) DTO → 엔티티 변환
        return dtos.stream()
                .map(dto -> {
                    Problem p = Problem.builder()
                            .category(category)
                            .title(dto.title())
                            .description(dto.description())
                            .correctOption(dto.correctOption())
                            .explanation(dto.explanation())
                            .difficulty(dto.difficulty())
                            .build();
                    dto.options().forEach(o ->
                            p.addOption(new ProblemOption(o.optionIndex(), o.content()))
                    );
                    return p;
                })
                .toList();
    }

    // 내부 DTO


}
