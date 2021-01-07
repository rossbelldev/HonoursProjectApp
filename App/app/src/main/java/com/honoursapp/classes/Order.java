package com.honoursapp.classes;

import com.honoursapp.classes.items.ItemOrder;

import java.util.ArrayList;

public class Order {

    ArrayList<ItemOrder> orderItems;
    public User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
