package com.clubnu.domains.category.repository;

import com.clubnu.domains.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
