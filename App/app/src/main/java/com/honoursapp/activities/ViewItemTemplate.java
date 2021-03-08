package com.honoursapp.activities;

import  androidx.appcompat.app.AppCompatActivity;

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
import com.honoursapp.calculations.OrganiseOrder;
import com.honoursapp.calculations.SwapIDBtoItem;
import com.honoursapp.classes.holders.OrderHolder;
import com.honoursapp.classes.adapters.CustomArrayAdapterProteins;
import com.honoursapp.classes.items.Item;
import com.honoursapp.classes.items.ItemDB;
import com.honoursapp.classes.items.ItemOrder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ViewItemTemplate extends AppCompatActivity {


    // Buttons
    Button btnBurgerBar, btnAdd, btnBasket;

    // Text Views
    TextView tvTitle, tvDesc, tvAllergens, tvProteins;

    // List View
    ListView lvProteins;

    // ItemDB to be used across contexts
    ItemDB itemDB = new ItemDB();

    // Category String to be passed back on item selection
    String category;

    // Array list proteins
    ArrayList<String> proteins = new ArrayList<>();
    ArrayList<String> pricesString = new ArrayList<>();

    // Item Order initialised
    ItemOrder iOrder = new ItemOrder();

    // Price set boolean
    boolean priceSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_template);

        // Get the order from the Order data holder
        final ArrayList<ItemOrder> order = OrderHolder.getInstance().order;

        // Pair all buttons
        btnBurgerBar = (Button) findViewById(R.id.btnBurgerBar);
        btnBasket = (Button) findViewById(R.id.btnBasket);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        // Pair all text views
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvAllergens = (TextView) findViewById(R.id.tvAllergens);
        tvProteins = (TextView) findViewById(R.id.tvProteins);

        // Pair list view
        lvProteins = (ListView) findViewById(R.id.lvProtein);

        // Get the passed object
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            itemDB = (ItemDB) extras.get("itemDb");
            category = extras.getString("category");
        }


        // Send it off to be swapped to a more usable format
        SwapIDBtoItem change = new SwapIDBtoItem();
        final Item item = change.swap(itemDB);

        // React to the information which it returns
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

                    // Uncheck all other items, then check the one that needs to be checked
                    int len = adapterView.getLastVisiblePosition() - adapterView.getFirstVisiblePosition();

                    if(len >= 2){
                        for(int ii = 0; ii <= len; ii++){
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

                    // Set the item attributes
                    iOrder.setProtein(proteins.get(i));
                    iOrder.setPrice(item.getPrices().get(i));
                    priceSet = true;
                }
            });

        }else{
            tvProteins.setVisibility(View.GONE);
        }

        // Onclick listener for the add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean add = false;
                boolean found = false;

                // Formatting for the price to round and display properly
                DecimalFormat priceFormat = new DecimalFormat("#.##");

                // Set the name to be checked
                iOrder.setName(item.getName());

                if(order.size() > 0){
                    // The order already has items on it
                    // Check to see if it contains the item which is trying to be added. (for(ItemOrder io : order){} loop does not work here for some reason)
                    for(int i = 0; i < order.size(); i++){
                        ItemOrder io = order.get(i);
                        // For each ItemOrder in the order array list
                        if(io.getName().equals(iOrder.getName())){
                            // The item name is the same, check to see if it has a protein
                            int qty = io.getQty();
                            if(iOrder.getProtein() != null){
                                // The item being added has a protein, check it too
                                if(io.getProtein().equals(iOrder.getProtein())){
                                    // The protein is the same, increase qty
                                    qty++;
                                    io.setQty(qty);

                                    double price = iOrder.getPrice();
                                    price = price * qty;
                                    price = Double.parseDouble(priceFormat.format(price));
                                    io.setPrice(price);

                                    String msg = iOrder.protein + " " + iOrder.name + " qty has increased!";
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                                    returnUser();
                                }

                                found = true;

                            }else{
                                // The item being added does not have a protein, and the name is the same, increase qty
                                found = true;

                                qty++;
                                io.setQty(qty);

                                double price = iOrder.getPrice();
                                price = price * qty;
                                price = Double.parseDouble(priceFormat.format(price));
                                io.setPrice(price);

                                String msg = iOrder.name + " qty has increased!";
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                                returnUser();
                            }

                        }

                    }

                    if(!found){
                        // The item has not been found on the whole list, therefore add it.
                        add = true;
                    }

                }else{
                    // The item being added is the first item on the order, add it
                    add = true;
                }

                if(add){
                    // Check that there is a protein selected (if it is needed)
                    if(iOrder.getProtein() == null && item.getProteins().size() > 1){
                        Toast.makeText(ViewItemTemplate.this, "Please choose a protein!", Toast.LENGTH_SHORT).show();
                    }else{
                        // The item has not yet been added to the order, can be added normally
                        iOrder.setName(item.getName());
                        iOrder.setQty(1);

                        // Format the category for the order, i.e. make the category mains, drinks, sides etc
                        OrganiseOrder org = new OrganiseOrder();
                        String formattedCat = org.formatCat(category);

                        iOrder.setCat(formattedCat);

                        // If there is only one possible price, get that
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

                    returnUser();

                }

            }

        });

        // Onclick listener for the basket button
        btnBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), BasketActivity.class);
                startActivity(i);
            }

        });

    }

    private void returnUser(){
        // Return the user back to the previous screen they were on
        Intent i = new Intent(getApplicationContext(), OrderActivity.class);
        //i.putExtra("category",category);
        startActivity(i);
    }

}