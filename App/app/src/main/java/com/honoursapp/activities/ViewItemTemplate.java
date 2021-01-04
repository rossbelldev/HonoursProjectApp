package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.honoursapp.R;
import com.honoursapp.calculations.SwapIDBtoItem;
import com.honoursapp.classes.Item;
import com.honoursapp.classes.ItemDB;

import java.util.ArrayList;
import java.util.List;


public class ViewItemTemplate extends AppCompatActivity {


    //Buttons
    Button btnBurgerBar, btnAdd, btnBasket;

    //Text Views
    TextView tvTitle, tvDesc, tvAllergens, tvProteins;

    //List View
    ListView lvProteins;

    //ItemDB to be used across contexts
    ItemDB itemDB = new ItemDB();

    //Array list proteins
    ArrayList<String> proteins = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_template);

        //Pair all buttons
        btnBurgerBar = (Button) findViewById(R.id.btnBurgerBar);
        btnBasket = (Button) findViewById(R.id.btnBasket);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        //Pair all text views
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvAllergens = (TextView) findViewById(R.id.tvAllergens);
        tvProteins = (TextView) findViewById(R.id.tvProteins);

        //Pair list view
        lvProteins = (ListView) findViewById(R.id.lvProtein);

        //Get the passed object
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            itemDB = (ItemDB) extras.get("itemDb");
        }

        //Send it off to be swapped to a more usable format
        SwapIDBtoItem change = new SwapIDBtoItem();
        Item item = change.swap(itemDB);

        //React to the information which it returns
        tvTitle.setText(item.getName());
        tvDesc.setText(item.getDescription());
        tvAllergens.setText(item.getAllergens());

        proteins = item.getProteins();
        if(proteins.size() > 1){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, proteins);
            lvProteins.setAdapter(adapter);
        }else{
            tvProteins.setVisibility(View.GONE);
        }



    }
}