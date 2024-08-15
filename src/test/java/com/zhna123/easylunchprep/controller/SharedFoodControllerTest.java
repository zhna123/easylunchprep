package com.zhna123.easylunchprep.controller;

import com.zhna123.easylunchprep.entity.Category;
import com.zhna123.easylunchprep.entity.Food;
import com.zhna123.easylunchprep.service.FoodService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SharedFoodController.class)
public class SharedFoodControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FoodService service;

    @Test
    @WithMockUser(username = "user")
    public void testGetFoodByCategory() throws Exception {
        Food recFood = new Food(1L, "apple", "", "/path", Category.FRUITS, null);
        Mockito.when(service.getFoodByCategory("fruits")).thenReturn(List.of(recFood));
        mockMvc.perform(get("/food/recommend/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("apple"));
    }
}
