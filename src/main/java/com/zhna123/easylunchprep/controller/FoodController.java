package com.zhna123.easylunchprep.controller;

import com.zhna123.easylunchprep.entity.Food;
import com.zhna123.easylunchprep.exception.FoodNotFoundException;
import com.zhna123.easylunchprep.repository.FoodRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FoodController {

    private final FoodRepository foodRepository;

    public FoodController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @GetMapping(path = "/food/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Optional<Food> food =  foodRepository.findById(id);
        if (food.isEmpty()) {
            throw new FoodNotFoundException(STR."food not found: id=\{id}");
        }
        return ResponseEntity.ok(food.get());
    }
}
