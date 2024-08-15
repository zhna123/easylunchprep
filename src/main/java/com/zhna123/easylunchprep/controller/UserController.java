package com.zhna123.easylunchprep.controller;

import com.zhna123.easylunchprep.entity.Category;
import com.zhna123.easylunchprep.entity.Food;
import com.zhna123.easylunchprep.entity.Lunchbox;
import com.zhna123.easylunchprep.entity.User;
import com.zhna123.easylunchprep.exception.LunchboxNotFoundException;
import com.zhna123.easylunchprep.exception.UserNotFoundException;
import com.zhna123.easylunchprep.repository.FoodRepository;
import com.zhna123.easylunchprep.repository.LunchboxRepository;
import com.zhna123.easylunchprep.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository repository;
    private final LunchboxRepository lunchboxRepository;

    private final FoodRepository foodRepository;

    public UserController(UserRepository repository, LunchboxRepository lunchboxRepository, FoodRepository foodRepository) {
        this.repository = repository;
        this.lunchboxRepository = lunchboxRepository;
        this.foodRepository = foodRepository;
    }


    @GetMapping(path = "/users/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> user =  repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(STR."user not found: id=\{id}");
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = repository.save(user);
        // return back the uri of the user that is created
        // eg: /user/6
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")   // add path
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id,  @Valid @RequestBody User userDetail) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(STR."user not found: id=\{id}");
        }
        User existed = user.get();
        existed.setPassword(userDetail.getPassword());
        existed.setPreferences(userDetail.getPreferences());
        User updated = repository.save(existed);
        return ResponseEntity.ok(updated);
    }

    @GetMapping(path = "/users/{id}/lunchboxes")
    public ResponseEntity<List<Lunchbox>> retrieveLunchboxesForUser(@PathVariable Long id) {
        Optional<User> user =  repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(STR."user not found: id=\{id}");
        }
        return ResponseEntity.ok(user.get().getLunchboxes());
    }

    @PostMapping(path = "/users/{id}/lunchboxes")
    public ResponseEntity<Lunchbox> createLunchboxForUser(@PathVariable Long id, @Valid @RequestBody Lunchbox lunchbox) {
        Optional<User> user =  repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(STR."user not found: id=\{id}");
        }
        lunchbox.setUser(user.get());
        Lunchbox saved = lunchboxRepository.save((lunchbox));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/lunchboxes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping(path = "/users/{id}/food")
    public ResponseEntity<List<Food>> retrieveFoodForUser(@PathVariable Long id) {
        Optional<User> user =  repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(STR."user not found: id=\{id}");
        }
        return ResponseEntity.ok(user.get().getUserFood());
    }

    @GetMapping(path = "/users/{id}/food/{category}")
    public ResponseEntity<List<Food>> retrieveFoodForUserByCategory(@PathVariable Long id, @PathVariable String category) {
        Optional<User> user =  repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(STR."user not found: id=\{id}");
        }
        List<Food> foodList = foodRepository.findByUserAndCategory(user.get(), Category.valueOf(category.toUpperCase()));
        return ResponseEntity.ok(foodList);
    }

    @PostMapping(path = "/users/{id}/food")
    public ResponseEntity<Food> createFoodForUser(@PathVariable Long id, @Valid @RequestBody Food food) {
        Optional<User> user =  repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(STR."user not found: id=\{id}");
        }
        food.setUser(user.get());
        Food saved = foodRepository.save((food));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/food/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }
}
