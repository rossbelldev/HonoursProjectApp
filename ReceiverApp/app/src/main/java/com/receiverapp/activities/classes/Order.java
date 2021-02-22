package com.receiverapp.activities.classes;

import java.util.ArrayList;

public class Order {

    public String name;
    ArrayList<ItemOrder> orderItems;
    public String destination;
    public User user;
    public double totalPrice;

    // Default Constructor
    public Order(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and setters
    public ArrayList<ItemOrder> getOrderItems(){
        return orderItems;
    }

    public void setOrderItems(ArrayList<ItemOrder> orderItems){
        this.orderItems = orderItems;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

