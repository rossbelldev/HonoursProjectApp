package com.honoursapp.classes;

import com.honoursapp.classes.items.ItemOrder;

import java.util.ArrayList;

public class Order {

    ArrayList<ItemOrder> orderItems;
    public String destination;
    public User user;
    public double totalPrice;

    //Default Constructor
    public Order(){

    }

    //getters and setters
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
