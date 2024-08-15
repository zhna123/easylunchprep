package com.zhna123.easylunchprep.repository;

import com.zhna123.easylunchprep.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUserWithAllInformation() {
        DietaryPreferences dietaryPreferences = new DietaryPreferences();
        dietaryPreferences.setDairyFree(true);

        dietaryPreferences.setExcludes(List.of("nuts"));
        User user = new User(null, "user1@email.com", "John", "Doe", "pwd",
                dietaryPreferences, Collections.emptyList(), Collections.emptyList());

        Food userFood = new Food(null, "apple salad", "", "", Category.FRUITS, user);
        Food recFood = new Food(null, "apple", "", "", Category.FRUITS, null);

        Lunchbox lunchbox1 = new Lunchbox();
        lunchbox1.setUser(user);
        lunchbox1.setName("lunchbox 1");
        lunchbox1.setFavorite(false);
        lunchbox1.setFoods(List.of(userFood));

        Lunchbox lunchbox2 = new Lunchbox();
        lunchbox1.setUser(user);
        lunchbox1.setName("lunchbox 2");
        lunchbox1.setFavorite(false);
        lunchbox1.setFoods(List.of(recFood));

        user.setLunchboxes(List.of(lunchbox1, lunchbox2));
        user.setUserFood(List.of(userFood));

        User savedUser = userRepository.save(user);

        // test user detail
        Assertions.assertEquals(user, savedUser);
        // test lunchbox
        Assertions.assertEquals(2, savedUser.getLunchboxes().size());
        Assertions.assertTrue(savedUser.getLunchboxes().containsAll(List.of(lunchbox1, lunchbox2)));
        // test dietary preference
        Assertions.assertTrue(savedUser.getPreferences().isDairyFree());
        Assertions.assertFalse(savedUser.getPreferences().isGlutenFree());
        Assertions.assertTrue(savedUser.getPreferences().getExcludes().contains("nuts"));
        // test user food
        Assertions.assertEquals(1, savedUser.getUserFood().size());
        Assertions.assertEquals(userFood, savedUser.getUserFood().getFirst());
    }

    @Test
    public void testSaveUserWithMinimalInformation() {
        User user = new User(null, "user1@email.com", "John", "Doe", "pwd",
                new DietaryPreferences(), Collections.emptyList(), Collections.emptyList());
        User savedUser = userRepository.save(user);
        Assertions.assertEquals(savedUser, user);
    }
}
