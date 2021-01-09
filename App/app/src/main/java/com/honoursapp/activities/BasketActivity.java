package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.honoursapp.R;
import com.honoursapp.classes.adapters.CustomArrayAdapterBasket;
import com.honoursapp.classes.items.ItemOrder;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {

    //Buttons
    Button btnPay;

    //ListView
    ListView lvOrder;

    //ArrayLsit<ItemOrder> to be retrieved from extras
    ArrayList<ItemOrder> order = new ArrayList<>();

    //Array Lists to be populated with the info to put on the order
    ArrayList<String> nameAndProtein = new ArrayList<>();
    ArrayList<String> priceString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        //Get the extras
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            order = (ArrayList<ItemOrder>) extras.get("order");
        }

        //Pair the buttons
        btnPay = (Button) findViewById(R.id.btnPay);

        //Pair the list view
        lvOrder = (ListView) findViewById(R.id.lvOrder);

        //Display the information (if the order is not null)
        if(order != null){

            //Prepare the information for the adapter
            for(int i = 0; i < order.size(); i++){

                String name = order.get(i).getName();
                String protein = order.get(i).getProtein();
                //If there is a protein
                if(protein != null){
                    String proteinAndName = protein + " " + name;
                    nameAndProtein.add(proteinAndName);
                }else{
                    //There is no protein
                    nameAndProtein.add(name);
                }

                String price = String.valueOf(order.get(i).getPrice());
                priceString.add(price);

            }

            CustomArrayAdapterBasket cust = new CustomArrayAdapterBasket(this, nameAndProtein, priceString);
            lvOrder.setAdapter(cust);

        }

    }
}