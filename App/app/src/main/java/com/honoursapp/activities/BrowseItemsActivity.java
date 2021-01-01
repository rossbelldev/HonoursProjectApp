package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.honoursapp.R;

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
    ArrayList<String> toDisplay;

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

        //Check to see if sub categories are needed
        if(category.equals("Drinks")){
            //Sub category will be the list provided (not the search)
            toDisplay = new ArrayList<>(Arrays.asList("Soft Drinks","Hot Drinks","Beer","Wine","Cider","Vodka","Rum","Whisky","Gin","Alcohol Free","Other"));
        }else{
            //Else the fetch will execute
            //Get the items from the categories (this is just hard coded at the moment)
            toDisplay = new ArrayList<>(Arrays.asList("Opt 1","Opt 2","Opt 3","Opt 4","Opt 5","Opt 6", "Opt 7","Opt 8","Opt 9",
                    "Opt 10","Opt 11","Opt 12","Opt 13"));
        }

        //Change what the user sees depending on what they clicked
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDisplay);
        lvItems.setAdapter(adapter);

        //Onclick for the items

    }
}