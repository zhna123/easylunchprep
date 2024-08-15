package com.zhna123.easylunchprep.service;

import com.zhna123.easylunchprep.entity.Food;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class FoodServiceTest {

    @Autowired
    FoodService service;

    @Value("${s3.base.url}")
    private String s3BaseUrl;

    @Test
    public void testGetFoodByCategoryFromJSONFile() throws IOException {
        Assertions.assertEquals("http://test-url.com", s3BaseUrl);

        List<Food> foodList = service.getFoodByCategory("fruits");
        Assertions.assertEquals(3, foodList.size());
        List<String> expectedFoodNameList = new ArrayList<>(3);
        expectedFoodNameList.add("Apple");
        expectedFoodNameList.add("Banana");
        expectedFoodNameList.add(("Orange"));
        List<String> foodNameList = foodList.stream().map(Food::getName).toList();
        Assertions.assertEquals(expectedFoodNameList, foodNameList);
    }

}
