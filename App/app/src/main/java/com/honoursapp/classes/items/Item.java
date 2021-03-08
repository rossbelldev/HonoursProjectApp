package com.honoursapp.classes.items;

import java.util.ArrayList;

public class Item {

    String name;
    ArrayList<String> proteins;
    String allergens;
    ArrayList<Double> prices;
    String description;

    // Default constructor
    public Item(){

    }

    // Getters and setters
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<String> getProteins() {
        return proteins;
    }

    public void setProteins(ArrayList<String> proteins) {
        this.proteins = proteins;
    }

    public String getAllergens(){
        return allergens;
    }

    public void setAllergens(String allergens){
        this.allergens = allergens;
    }

    public ArrayList<Double> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Double> prices) {
        this.prices = prices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
