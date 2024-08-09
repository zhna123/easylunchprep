package com.zhna123.easylunchprep.entity;

import jakarta.persistence.*;

import java.util.List;

@Embeddable
public class DietaryPreferences {

    @Column(columnDefinition = "boolean default false")
    private Boolean vegetarian = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean vegan = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean glutenFree = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean dairyFree = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean nutFree = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean halal = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean kosher = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean noAddedSugar = false;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "includes", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "item", nullable = false)
    private List<String> includes;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "excludes", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "item", nullable = false)
    private List<String> excludes;

    public DietaryPreferences() {
    }

    public Boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Boolean isVegan() {
        return vegan;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    public Boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(Boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public Boolean isDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(Boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public Boolean isNutFree() {
        return nutFree;
    }

    public void setNutFree(Boolean nutFree) {
        this.nutFree = nutFree;
    }

    public Boolean isHalal() {
        return halal;
    }

    public void setHalal(Boolean halal) {
        this.halal = halal;
    }

    public Boolean isKosher() {
        return kosher;
    }

    public void setKosher(Boolean kosher) {
        this.kosher = kosher;
    }

    public Boolean isNoAddedSugar() {
        return noAddedSugar;
    }

    public void setNoAddedSugar(Boolean noAddedSugar) {
        this.noAddedSugar = noAddedSugar;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    @Override
    public String toString() {
        return STR."DietaryPreferences{vegetarian=\{vegetarian}, vegan=\{vegan}, glutenFree=\{glutenFree}, dairyFree=\{dairyFree}, nutFree=\{nutFree}, halal=\{halal}, kosher=\{kosher}, noAddedSugar=\{noAddedSugar}, includes=\{includes}, excludes=\{excludes}\{'}'}";
    }
}
