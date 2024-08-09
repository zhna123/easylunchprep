package com.zhna123.easylunchprep.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.zhna123.easylunchprep.entity.Category;
import com.zhna123.easylunchprep.entity.Food;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FoodService {

    @Value("${s3.base.url}")
    private String s3BaseUrl;

    public List<Food> getFoodByCategory(String category) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = Food.class.getResourceAsStream(STR."/data/\{category}.json");
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Food.class);
        List<Food> foodList =  objectMapper.readValue(inputStream, collectionType);
        // append s3 base url
        return foodList.stream().map(food -> new Food(food.getId(),
                food.getName(),
                food.getDescription(),
                s3BaseUrl + food.getImage(),
                food.getCategory() == null ? Category.valueOf(category.toUpperCase()) : food.getCategory(),
                        food.getUser()))
                .toList();
    }
}
