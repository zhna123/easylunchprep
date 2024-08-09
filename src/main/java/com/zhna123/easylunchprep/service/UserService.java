//package com.zhna123.easylunchprep.service;
//
//import com.zhna123.easylunchprep.entity.*;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
////@Service
//public class UserService {
//
//    // TESTING DATA!!
//
//    private static DietaryPreferences preferences =
//            new DietaryPreferences(false, false, false, false,
//                    false, false, false, false,
//                    Optional.empty(), Optional.empty());
//
//    private static Food food1 = new Food(10l, "blueberry", Category.FRUITS);
//    private static Food food2 = new Food(20l, "broccoli", Category.VEGETABLES);
//
//    private static Lunchbox lunchbox = new Lunchbox("my lunchbox 1", new Food[]{food1, food2}, false);
//
//    private static List<User> users = new ArrayList<>();
//
//    private static long userCount = 0l;
//
//    static {
//        users.add(new User(++userCount, "a1@email.com", "John1", "Doe1", "pwd1", preferences, lunchbox));
//        users.add(new User(++userCount, "a2@email.com", "John2", "Doe2", "pwd2", preferences, lunchbox));
//        users.add(new User(++userCount, "a3@email.com", "John3", "Doe3", "pwd3", preferences, lunchbox));
//        users.add(new User(++userCount, "a4@email.com", "John4", "Doe4", "pwd4", preferences, lunchbox));
//        users.add(new User(++ userCount, "a5@email.com", "John5", "Doe5", "pwd5", preferences, lunchbox));
//    }
//
//    public User findOne(Long id) {
//        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
//    }
//
//    public User save(User user) {
//        user.setId(++userCount);
//        users.add(user);
//        return user;
//    }
//}
