package com.honoursapp.classes;

import com.honoursapp.classes.items.ItemOrder;

import java.util.ArrayList;

public class OrderHolder {

    public final ArrayList<ItemOrder> order = new ArrayList<>();

    private OrderHolder(){

    }

    public static OrderHolder getInstance(){
        if(instance == null){
            instance = new OrderHolder();
        }
        return instance;
    }

    private static OrderHolder instance;

}
