package com.honoursapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.honoursapp.R;
import com.honoursapp.classes.Order;
import com.honoursapp.classes.items.ItemDB;
import com.honoursapp.classes.items.ItemOrder;

import java.util.ArrayList;
import java.util.Arrays;

public class BrowseItemsActivity extends AppCompatActivity {

    //Buttons
    Button btnBurgerBar, btnBasket, btnProceed;

    //List View
    ListView lvItems;

    //String for the extras
    String category;

    //Array list for the list of items to be displayed
    ArrayList<String> toDisplay = new ArrayList<>();
    ArrayList<ItemDB> list = new ArrayList<>();

    //Order to be passed between activities
    ArrayList<ItemOrder> order = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_items);

        //Get the extras to work out what to display
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            category = extras.getString("category");
            order = (ArrayList<ItemOrder>) extras.get("order");
        }

        //Buttons
        btnBurgerBar = (Button) findViewById(R.id.btnBurgerBar);
        btnBasket = (Button) findViewById(R.id.btnBasket);
        btnProceed = (Button) findViewById(R.id.btnProceed);

        //List view
        lvItems = (ListView) findViewById(R.id.lvItems);

        //Adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDisplay);
        lvItems.setAdapter(adapter);

        //Check to see if sub categories are needed
        if(category.equals("Drinks")){
            //Sub category will be the list provided (not the search)
            String[] cats = {"Soft Drinks","Hot Drinks","Beer","Wine","Cider","Vodka","Rum","Whisky","Gin","Alcohol Free","Other"};
            toDisplay.addAll(Arrays.asList(cats));
        }else{
            //Else the fetch will execute
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("1OcAx3H04_kHY_cgU-Z8pAyuTEDuVNCit5Z9ohFt2L-4").child(category);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        ItemDB itmDb = ds.getValue(ItemDB.class);
                        list.add(itmDb);
                        toDisplay.add(itmDb.getName());
                    }
                    //Change what the user sees depending on what they clicked
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Problem: " + databaseError, Toast.LENGTH_LONG).show();
                }

            });

        }

        //Onclick for the items
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ViewItemTemplate.class);
                intent.putExtra("itemDb", list.get(i));
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });

    }

}
