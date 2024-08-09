package com.zhna123.easylunchprep.controller;

import com.zhna123.easylunchprep.entity.Food;
import com.zhna123.easylunchprep.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class SharedFoodController {

    private final FoodService foodService;

    public SharedFoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping(path = "/food/recommend/{category}")
    public ResponseEntity<List<Food>> getFoodByCategory(@PathVariable String category) {
        try {
            return ResponseEntity.ok(foodService.getFoodByCategory(category));
        } catch (IOException e) {
            throw new RuntimeException(STR."Error reading food data: category:\{category}", e);
        }
    }
}
