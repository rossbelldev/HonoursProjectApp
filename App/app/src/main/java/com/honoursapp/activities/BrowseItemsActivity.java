package com.honoursapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.honoursapp.classes.ItemDB;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_items);

        //Get the extras to work out what to display
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            category = extras.getString("category");
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

            final ArrayList<ItemDB> list = new ArrayList<>();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        ItemDB itmDb = ds.getValue(ItemDB.class);
                        System.out.println(itmDb.getName());
                        list.add(itmDb);
                        toDisplay.add(itmDb.getName());
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Problem: " + databaseError, Toast.LENGTH_LONG).show();
                }

            });


        }

        //Change what the user sees depending on what they clicked


        //Onclick for the items

    }

}

//Old Code
/*
                    if(ds.getValue().equals(cat)){




                        //To be used if needed
                        ArrayList<String> proteins = new ArrayList<>();
                        ArrayList<Double> prices = new ArrayList<>();

                        String name = ds.child("Name").toString();
                        String price = ds.child("Price").toString();
                        String protein = ds.child("Proteins").toString();
                        String allergens = ds.child("Allergens").toString();
                        String description = ds.child("Description").toString();

                        //If there are multiple, convert the Strings to doubles for price
                        if(price.contains(",")){

                            String[] pricesArray = price.split(",");
                            ArrayList<Double> pri = new ArrayList<>();

                            for(String p : pricesArray){
                                double d = Double.parseDouble(p);
                                pri.add(d);
                            }

                            itm.setPrices(pri);

                            ArrayList<String> pro = new ArrayList<>(Arrays.asList(protein.split(",")));
                            itm.setProteins(pro);

                        }else{
                            //Only one protein
                            double p = Double.parseDouble(price);
                            prices.add(p);

                            proteins.add(protein);

                            itm.setProteins(proteins);
                            itm.setPrices(prices);
                        }

                        itm.setName(name);
                        itm.setAllergens(allergens);
                        itm.setDescription(description);



                    }
                    */