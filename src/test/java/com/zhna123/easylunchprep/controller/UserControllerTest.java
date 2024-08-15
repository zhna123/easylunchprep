package com.zhna123.easylunchprep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhna123.easylunchprep.entity.*;
import com.zhna123.easylunchprep.repository.FoodRepository;
import com.zhna123.easylunchprep.repository.LunchboxRepository;
import com.zhna123.easylunchprep.repository.UserRepository;
import com.zhna123.easylunchprep.security.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserRepository userRepository;

    @MockBean
    LunchboxRepository lunchboxRepository;

    @MockBean
    FoodRepository foodRepository;

    @Test
    @WithMockUser(username = "user")
    public void testFindById() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Jenny");
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Kai");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.firstName").value(user1.getFirstName()));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @WithMockUser(username = "user")
    public void testCreateUser() throws Exception {
        User user = new User(null, "user1@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());

        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user")
    public void testCreateUser_InvalidEmail() throws Exception {
        User user = new User(null, "", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());

        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user")
    public void testUpdateUserById() throws Exception {
        User user = new User(1L, "user@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        User updatedUser = new User(user.getId(), "user@email.com", "John_2", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatedUser.getFirstName()));
    }

    @Test
    @WithMockUser(username = "user")
    public void testUpdateUserById_InvalidName() throws Exception {
        User user = new User(1L, "user@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        User updatedUser = new User(user.getId(), "user@email.com", "", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user")
    public void testRetrieveLunchboxesForUser() throws Exception {
        User user = new User(1L, "user@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        Lunchbox lunchbox = new Lunchbox(1L, "lunchbox 1", Collections.emptyList(), false, user);
        user.setLunchboxes(List.of(lunchbox));

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/users/1/lunchboxes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("lunchbox 1"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testCreateLunchboxForUser() throws Exception {
        User user = new User(1L, "user@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Lunchbox lunchbox = new Lunchbox(1L, "lunchbox 1", Collections.emptyList(), false, user);
        Mockito.when(lunchboxRepository.save(any(Lunchbox.class))).thenReturn(lunchbox);

        mockMvc.perform(post("/users/1/lunchboxes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(lunchbox)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user")
    public void testCreateLunchboxForUser_InvalidName() throws Exception {
        User user = new User(1L, "user@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Lunchbox lunchbox = new Lunchbox(1L, "", Collections.emptyList(), false, user);
        Mockito.when(lunchboxRepository.save(any(Lunchbox.class))).thenReturn(lunchbox);

        mockMvc.perform(post("/users/1/lunchboxes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lunchbox)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user")
    public void testRetrieveFoodForUser() throws Exception {
        User user = new User(1L, "user@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        Food userFood = new Food(1L, "apple salad", "", "", Category.FRUITS, user);
        user.setUserFood(List.of(userFood));

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/users/1/food"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("apple salad"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testRetrieveFoodForUserByCategory() throws Exception {
        User user = new User(1L, "user@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        Food userFood1 = new Food(1L, "apple salad", "", "", Category.FRUITS, user);
        Food userFood2 = new Food(2L, "vegetable salad", "", "", Category.VEGETABLES, user);

        user.setUserFood(List.of(userFood1, userFood2));

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(foodRepository.findByUserAndCategory(user, Category.VEGETABLES)).thenReturn(List.of(userFood2));

        mockMvc.perform(get(STR."/users/\{user.getId()}/food/vegetables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("vegetable salad"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testCreateFoodForUser() throws Exception {
        User user = new User(1L, "user@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Food userFood = new Food(1L, "apple salad", "", "/path", Category.FRUITS, user);
        Mockito.when(foodRepository.save(any(Food.class))).thenReturn(userFood);

        mockMvc.perform(post("/users/1/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userFood)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user")
    public void testCreateFoodForUser_EmptyField() throws Exception {
        User user = new User(1L, "user@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Food userFood = new Food(1L, "apple salad", "", "/path", Category.FRUITS, user);
        Mockito.when(foodRepository.save(any(Food.class))).thenReturn(userFood);

        mockMvc.perform(post("/users/1/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userFood)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value(""));
    }
}
