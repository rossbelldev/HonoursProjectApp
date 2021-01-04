package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.honoursapp.R;
import com.honoursapp.calculations.SwapIDBtoItem;
import com.honoursapp.classes.Item;
import com.honoursapp.classes.ItemDB;

public class ViewItemTemplate extends AppCompatActivity {

    ItemDB itemDB = new ItemDB();

    //Buttons

    //Text Views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_template);

        //Get the passed object
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            itemDB = (ItemDB) extras.get("itemDb");
        }

        //Send it off to be swapped to a more usable format
        SwapIDBtoItem change = new SwapIDBtoItem();
        Item item = change.swap(itemDB);
        //System.out.println(itemDB.getName());


        //React to the information which it returns

    }
}