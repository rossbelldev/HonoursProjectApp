package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.honoursapp.R;
import com.honoursapp.calculations.SwapIDBtoItem;
import com.honoursapp.classes.OrderHolder;
import com.honoursapp.classes.adapters.CustomArrayAdapterProteins;
import com.honoursapp.classes.items.Item;
import com.honoursapp.classes.items.ItemDB;
import com.honoursapp.classes.items.ItemOrder;

import java.util.ArrayList;


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
    ArrayList<String> pricesString = new ArrayList<>();

    //Item Order initialised
    ItemOrder iOrder = new ItemOrder();

    //Order to be passed between intents
    ArrayList<ItemOrder> order = new ArrayList<>();

    //Price set boolean
    boolean priceSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_template);

        //Get the order from the Order data holder
        final ArrayList<ItemOrder> order = OrderHolder.getInstance().order;

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
        final Item item = change.swap(itemDB);

        //React to the information which it returns
        tvTitle.setText(item.getName());
        tvDesc.setText(item.getDescription());
        tvAllergens.setText(item.getAllergens());

        proteins = item.getProteins();

        for(int i = 0; i < item.getPrices().size(); i++){
            String s = item.getPrices().get(i).toString();
            pricesString.add(s);
        }

        if(proteins != null){
            CustomArrayAdapterProteins custAd = new CustomArrayAdapterProteins(this, item.getProteins(), pricesString);
            lvProteins.setAdapter(custAd);

            lvProteins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //Uncheck all other items, then check the one that needs to be checked
                    int len = adapterView.getLastVisiblePosition() - adapterView.getFirstVisiblePosition();

                    if(len > 2){
                        for(int ii = 0; ii < len; ii++){
                            View v = adapterView.getChildAt(ii);
                            RadioButton r = (RadioButton) v.findViewById(R.id.rbSelect);
                            r.setChecked(false);
                        }
                    }else{
                        for(int ii = 0; ii < adapterView.getCount(); ii++){

                            View v = adapterView.getChildAt(ii);
                            RadioButton r = (RadioButton) v.findViewById(R.id.rbSelect);
                            r.setChecked(false);
                        }
                    }

                    RadioButton r = (RadioButton) view.findViewById(R.id.rbSelect);
                    r.setChecked(true);

                    //Set the item attributes
                    iOrder.setProtein(proteins.get(i));
                    iOrder.setPrice(item.getPrices().get(i));
                    priceSet = true;
                }
            });

        }else{
            tvProteins.setVisibility(View.GONE);
        }

        //Onclick listener for the add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check that there is a protein selected (if it is needed)
                if(iOrder.getProtein() == null && item.getProteins().size() > 1){
                    Toast.makeText(ViewItemTemplate.this, "Please choose a protein!", Toast.LENGTH_SHORT).show();
                }else{

                    iOrder.setName(item.getName());

                    //If there is only one possible price, get that
                    if(!priceSet){
                        iOrder.setPrice(item.getPrices().get(0));
                        order.add(iOrder);
                        String msg = iOrder.name + " has been added!";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }else{
                        order.add(iOrder);
                        String msg = iOrder.protein + " " + iOrder.name + " has been added!";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        //Onclick listener for the basket button
        btnBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), BasketActivity.class);
                startActivity(i);
            }
        });


    }
}