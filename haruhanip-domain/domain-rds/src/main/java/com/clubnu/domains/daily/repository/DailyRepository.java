package com.clubnu.domains.daily.repository;

import com.clubnu.domains.category.domain.Category;
import com.clubnu.domains.daily.domain.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyRepository extends JpaRepository<Daily, Long>{
    List<Daily> findAllByDailyDate(LocalDate dailyDate);

    List<Daily> findAllByDailyDateAndCategory(LocalDate dailyDate, Category category);

    Optional<Daily> findByCategoryAndDailyDate(Category category, LocalDate date);
}
