package com.haruhanip.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("categoryIdReader")
@StepScope
public class CategoryIdReader implements ItemReader<Long> {

    private final Long categoryId;
    private boolean read = false;

    public CategoryIdReader(@Value("#{stepExecutionContext['categoryId']}") Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public Long read() {
        if (!read) {
            read = true;
            return categoryId;
        }
        return null;
    }
}