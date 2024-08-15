package com.zhna123.easylunchprep.repository;

import com.zhna123.easylunchprep.entity.Category;
import com.zhna123.easylunchprep.entity.Food;
import com.zhna123.easylunchprep.entity.Lunchbox;
import com.zhna123.easylunchprep.entity.User;
import com.zhna123.easylunchprep.exception.LunchboxNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@DataJpaTest
public class LunchboxRepositoryTest {

    @Autowired
    LunchboxRepository lunchboxRepository;

    @Test
    public void testSaveLunchboxWithFood() {

        User user = new User();
        Lunchbox existing = new Lunchbox(null, "lunchbox 1", Collections.emptyList(), false, user);

        Food userFood1 = new Food(null, "apple salad", "", "", Category.FRUITS, user);
        Food userFood2 = new Food(null, "fried rice", "", "", Category.GRAIN, user);
        Food recFood = new Food(null, "milk", "", "", Category.DAIRY, null);

        existing.setFoods(List.of(userFood1, userFood2, recFood));
        Lunchbox savedLunchbox = lunchboxRepository.save(existing);

        Assertions.assertEquals(List.of(userFood1, userFood2, recFood), savedLunchbox.getFoods());
    }
}

