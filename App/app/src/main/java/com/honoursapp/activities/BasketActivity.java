package com.honoursapp.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.honoursapp.R;
import com.honoursapp.classes.OrderHolder;
import com.honoursapp.classes.adapters.CustomArrayAdapterBasket;
import com.honoursapp.classes.items.ItemOrder;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {

    //Buttons
    Button btnPay;

    //ListView
    ListView lvOrder;

    //Array Lists to be populated with the info to put on the order
    ArrayList<String> nameAndProtein = new ArrayList<>();
    ArrayList<String> priceString = new ArrayList<>();
    ArrayList<String> qtyString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        //Get the order from the order holder
        final ArrayList<ItemOrder> order = OrderHolder.getInstance().order;

        //Pair the buttons
        btnPay = (Button) findViewById(R.id.btnPay);


        //Pair the list view
        lvOrder = (ListView) findViewById(R.id.lvOrder);

        //Custom back button to make the user go back to the order screen
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent i = new Intent(getApplicationContext(), OrderActivity.class);
                i.putExtra("method",0);
                startActivity(i);
            }
        };

        getOnBackPressedDispatcher().addCallback(this,callback);

        //Display the information (if the order is not null)
        if(order != null){

            //Prepare the information for the adapter
            for(int i = 0; i < order.size(); i++){

                String name = order.get(i).getName();
                String protein = order.get(i).getProtein();
                String qty = String.valueOf(order.get(i).getQty());
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
                qtyString.add(qty);

            }

            CustomArrayAdapterBasket cust = new CustomArrayAdapterBasket(this, nameAndProtein, priceString, qtyString);
            lvOrder.setAdapter(cust);

            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                    startActivity(i);
                }
            });

        }

    }

}