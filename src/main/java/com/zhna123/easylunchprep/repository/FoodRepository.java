package com.zhna123.easylunchprep.repository;

import com.zhna123.easylunchprep.entity.Category;
import com.zhna123.easylunchprep.entity.Food;
import com.zhna123.easylunchprep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByUserAndCategory(User user, Category category);
}
