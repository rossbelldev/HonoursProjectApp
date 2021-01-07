package com.honoursapp.classes.items;

import java.io.Serializable;

public class ItemDB implements Serializable {

    String name;
    String proteins;
    String description;
    String price;
    String allergens;

    public ItemDB(){}

    //Getters and setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String protein) {
        this.proteins = protein;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }
}
