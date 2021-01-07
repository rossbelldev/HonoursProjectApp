package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.honoursapp.R;
import com.honoursapp.classes.Order;
import com.honoursapp.classes.items.ItemOrder;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderActivity extends AppCompatActivity {

    //Buttons
    Button btnBurgerBar, btnProceed, btnBasket;

    //List View
    ListView lvCategories;

    //Array list for categories with all the sections added by default (certain ones are removed for different methods
    ArrayList<String> categories = new ArrayList<>(Arrays.asList("Drinks","Starters","Curries","Tandoori","Specials","Vegetable Sides","Rice","Naan and Breads","Desserts"));

    //Initialise the order to be passed between
    ArrayList<ItemOrder> order = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Buttons
        btnBurgerBar = (Button) findViewById(R.id.btnBurgerBar);
        btnProceed = (Button) findViewById(R.id.btnProceed);
        btnBasket = (Button) findViewById(R.id.btnBasket);

        //List view
        lvCategories = (ListView) findViewById(R.id.lvCategories);

        //Get the extras which have been passed
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int choice = extras.getInt("method");
            //Can only be 0 or 1. 0 is to table, 1 is for collection
            //Different information will be displayed depending on the selection.
            if(choice == 1){
                //Order for collection options. Remove 'desserts' (currently index 8)
                categories.remove(8);
            }
        }

        //Show a list view for all of the items in categories
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        lvCategories.setAdapter(adapter);

        //Onclick for the items of list view
        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Start the browse items activity and pass the category which has been selected
                Intent i = new Intent(view.getContext(), BrowseItemsActivity.class);
                i.putExtra("category", categories.get(position));
                i.putExtra("order", order);
                startActivity(i);
            }
        });

    }
}