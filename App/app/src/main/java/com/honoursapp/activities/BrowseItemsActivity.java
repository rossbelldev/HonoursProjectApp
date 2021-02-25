package com.honoursapp.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.honoursapp.R;
import com.honoursapp.classes.items.ItemDB;


import java.util.ArrayList;
import java.util.Arrays;

public class BrowseItemsActivity extends AppCompatActivity {

    // Buttons
    Button btnBurgerBar, btnProceed;

    // List View
    ListView lvItems;

    // String for the extras
    String category;

    // Text Views
    TextView tvCat;

    // Array list for the list of items to be displayed
    ArrayList<String> toDisplay = new ArrayList<>();
    ArrayList<ItemDB> list = new ArrayList<>();
    ArrayList<String> drinks = new ArrayList<>(Arrays.asList("Soft Drinks","Beer","Wine","Cider","Hot Drinks","Vodka","Rum","Whisky","Liqueurs"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_items);

        // Get the extras to work out what to display
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            category = extras.getString("category");
        }

        // Buttons
        btnBurgerBar = (Button) findViewById(R.id.btnBurgerBar);
        btnProceed = (Button) findViewById(R.id.btnProceed);

        // List view
        lvItems = (ListView) findViewById(R.id.lvItems);

        // Text Views
        tvCat = (TextView) findViewById(R.id.tvCat);

        // Update the title of the page
        tvCat.setText(category);

        // Custom back button to make the user go back to the order screen
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent i = new Intent(getApplicationContext(), OrderActivity.class);
                i.putExtra("method",0);
                startActivity(i);
            }
        };

        getOnBackPressedDispatcher().addCallback(this,callback);

        // Adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDisplay);
        lvItems.setAdapter(adapter);

        // On click for the basket button
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), BasketActivity.class);
                startActivity(i);
            }
        });

        // Check to see if sub categories are needed
        if(category.equals("Drinks")){
            // Sub categories will be retrieved from the other branch of the database (drinks branch)
            // Clear the todisplay and re-populate it
            toDisplay.clear();
            toDisplay.addAll(drinks);
            adapter.notifyDataSetChanged();

            // Get which sub-category is clicked
            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Update the values of "Soft Drinks" and "Hot Drinks" to be compatible with the database
                    drinks.set(0,"SoftDrinks");
                    drinks.set(4,"HotDrinks");

                    // Get the database child for the slot i which is selected
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("1_wnfdbNOIqLl_-gC_sE0PtXb2oXo1-g0i8ldwXqgRkI").child(drinks.get(i));

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Clear the toDisplay and re-populate it
                            toDisplay.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                ItemDB itmDb = ds.getValue(ItemDB.class);
                                list.add(itmDb);
                                toDisplay.add(itmDb.getName());
                            }
                            // Change what is displayed
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Problem: " + error, Toast.LENGTH_LONG).show();
                        }
                    });

                    // Onclick for the items (drinks or otherwise)
                    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            // Change the category
                            category = drinks.get(i);

                            Intent intent = new Intent(view.getContext(), ViewItemTemplate.class);
                            intent.putExtra("itemDb", list.get(i));
                            intent.putExtra("category", category);
                            startActivity(intent);
                        }
                    });
                }
            });

        }else{
            // Else the fetch will execute
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("1OcAx3H04_kHY_cgU-Z8pAyuTEDuVNCit5Z9ohFt2L-4").child(category);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Clear the todisplay and re-populate it
                    toDisplay.clear();
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        ItemDB itmDb = ds.getValue(ItemDB.class);
                        list.add(itmDb);
                        toDisplay.add(itmDb.getName());
                    }
                    // Change what the user sees depending on what they clicked
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Problem: " + databaseError, Toast.LENGTH_LONG).show();
                }

            });

            // Onclick for the items (drinks or otherwise)
            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(view.getContext(), ViewItemTemplate.class);
                    intent.putExtra("itemDb", list.get(i));
                    intent.putExtra("category", category);
                    startActivity(intent);
                }
            });

        }

    }

}
