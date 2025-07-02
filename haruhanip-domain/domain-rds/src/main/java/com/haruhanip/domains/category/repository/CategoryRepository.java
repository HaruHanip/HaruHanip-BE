package com.haruhanip.domains.category.repository;

import com.haruhanip.domains.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
