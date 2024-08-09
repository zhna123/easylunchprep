package com.zhna123.easylunchprep.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotEmpty(message = "Email must not be empty.")
    private String email;
    @Size(min = 2, message = "First name needs to have at least two characters.")
    private String firstName;
    @NotEmpty(message = "Last name must not be empty.")
    private String lastName;
    @NotEmpty(message = "Password must not be empty.")
    private String password;
    @Embedded
    private DietaryPreferences preferences = new DietaryPreferences();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Lunchbox> lunchboxes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Food> userFood;

    public User() {
    }

    public User(Long id, String email, String firstName, String lastName, String password, DietaryPreferences preferences,
                List<Lunchbox> lunchboxes, List<Food> userFood) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.preferences = preferences;
        this.lunchboxes = lunchboxes;
        this.userFood = userFood;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPreferences(DietaryPreferences preferences) {
        this.preferences = preferences;
    }

    public void setLunchboxes(List<Lunchbox> lunchboxes) {
        this.lunchboxes = lunchboxes;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DietaryPreferences getPreferences() {
        return preferences;
    }

    public List<Lunchbox> getLunchboxes() {
        return lunchboxes;
    }

    public List<Food> getUserFood() {
        return userFood;
    }

    public void setUserFood(List<Food> userFood) {
        this.userFood = userFood;
    }

    @Override
    public String toString() {
        return STR."User{id=\{id}, email='\{email}\{'\''}, firstName='\{firstName}\{'\''}, lastName='\{lastName}\{'\''}, password='\{password}\{'\''}, preferences=\{preferences}, lunchboxes=\{lunchboxes}, userFood=\{userFood}\{'}'}";
    }
}
