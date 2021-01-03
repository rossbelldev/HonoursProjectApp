package com.honoursapp.retrievals;

import android.widget.ArrayAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.honoursapp.classes.Item;

import java.util.ArrayList;

class ItemsGrabber {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    public ArrayList<Item> grab(String cat){
        ArrayList<Item> list = new ArrayList<>();



        return list;
    }

}
