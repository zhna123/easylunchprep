package com.zhna123.easylunchprep.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;


@Entity
public class Lunchbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Lunchbox name must not be empty.")
    private String name;

    @ManyToMany
    @JoinTable(name = "lunchbox_food",
            joinColumns = @JoinColumn(name = "lunchbox_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private List<Food> foods;
    private Boolean favorite;

    // don't need user details when fetching lunchbox
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Lunchbox() {
    }

    public Lunchbox(Long id, String name, List<Food> foods, Boolean favorite, User user) {
        this.id = id;
        this.name = name;
        this.foods = foods;
        this.favorite = favorite;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
