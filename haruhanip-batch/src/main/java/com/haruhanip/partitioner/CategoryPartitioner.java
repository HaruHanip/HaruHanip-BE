package com.haruhanip.partitioner;

import com.haruhanip.domains.category.domain.Category;
import com.haruhanip.domains.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CategoryPartitioner implements Partitioner {

    private static final String PARTITION_KEY = "categoryId";
    private final CategoryRepository categoryRepository;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        List<Category> categories = categoryRepository.findAll();
        Map<String, ExecutionContext> result = new HashMap<>();
        for (Category category : categories) {
            ExecutionContext context = new ExecutionContext();
            context.putLong(PARTITION_KEY, category.getCategoryId());
            String key = "partition" + category.getCategoryId();
            result.put(key, context);
        }
        return result;
    }
}
