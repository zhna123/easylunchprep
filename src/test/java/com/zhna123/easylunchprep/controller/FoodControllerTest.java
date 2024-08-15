package com.zhna123.easylunchprep.controller;

import com.zhna123.easylunchprep.entity.Category;
import com.zhna123.easylunchprep.entity.Food;
import com.zhna123.easylunchprep.entity.User;
import com.zhna123.easylunchprep.repository.FoodRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodController.class)
public class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodRepository foodRepository;

    @Test
    @WithMockUser(username = "user")
    public void testGetFoodById() throws Exception {
        Food userFood = new Food(1L, "apple salad", "", "/path", Category.FRUITS, new User());
        Mockito.when(foodRepository.findById(1L)).thenReturn(Optional.of(userFood));
        mockMvc.perform(get("/food/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("apple salad"));
    }
}
