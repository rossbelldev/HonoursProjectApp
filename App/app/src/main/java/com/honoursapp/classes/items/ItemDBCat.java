package com.honoursapp.classes.items;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemDBCat implements Serializable {

    ArrayList<ItemDB> items;

    public ItemDBCat(){

    }

    public ArrayList<ItemDB> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDB> items) {
        this.items = items;
    }
}
