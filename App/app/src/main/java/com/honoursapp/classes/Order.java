package com.honoursapp.classes;

import java.util.ArrayList;

public class Order {

    ArrayList<Item> orderItems;

    //Default Constructor
    public Order(){

    }

    //getters and setters
    public ArrayList<Item> getOrderItems(){
        return orderItems;
    }

    public void setOrderItems(ArrayList<Item> orderItems){
        this.orderItems = orderItems;
    }

}
