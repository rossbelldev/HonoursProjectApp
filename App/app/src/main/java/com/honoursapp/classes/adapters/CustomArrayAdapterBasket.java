package com.honoursapp.classes.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.honoursapp.R;
import com.honoursapp.activities.BasketActivity;
import com.honoursapp.classes.holders.OrderHolder;
import com.honoursapp.classes.items.ItemOrder;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomArrayAdapterBasket extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> proteinAndName;
    private final ArrayList<String> price;
    private final ArrayList<String> qty;

    Button btnPlus, btnMinus;

    //Get the order from the Order data holder
    final ArrayList<ItemOrder> order = OrderHolder.getInstance().order;

    public CustomArrayAdapterBasket(Activity context, ArrayList<String> proteinAndName, ArrayList<String> price, ArrayList<String> qty){

        super(context, R.layout.list_basket_items, proteinAndName);

        this.context = context;
        this.proteinAndName = proteinAndName;
        this.price = price;
        this.qty = qty;
    }

    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_basket_items,null);

        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvPrice = (TextView) rowView.findViewById(R.id.tvPrice);
        TextView tvQty = (TextView) rowView.findViewById(R.id.tvQty);

        btnPlus = (Button) rowView.findViewById(R.id.btnPlus);
        btnMinus = (Button) rowView.findViewById(R.id.btnMinus);

        tvQty.setText(qty.get(position));
        tvName.setText(proteinAndName.get(position));

        String p = "£" + price.get(position);
        tvPrice.setText(p);

        //Onclick listeners for the add and subtract buttons
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i < order.size(); i++){

                    ItemOrder io = order.get(i);

                    String fullName = proteinAndName.get(position);
                    String fullNameOrder = order.get(i).getName();
                    String fullNameOrderProtein = order.get(i).getProtein() + " " + order.get(i).getName();

                    if(fullName.equals(fullNameOrder) || fullName.equals(fullNameOrderProtein)){
                        //The item is the one which the qty is trying to increase

                        //Get the qty of the item
                        int qty = io.getQty();
                        int qtyFirst = qty;
                        double priceCurrent = io.getPrice();
                        qty++;

                        //Set the text to be the number and the item to have it
                        tvQty.setText(qty+"");

                        io.setQty(qty);

                        //Update the price

                        //Formatting for the price to round and display properly
                        DecimalFormat priceFormat = new DecimalFormat("#.##");

                        double basePrice = (priceCurrent / qtyFirst);
                        double price = basePrice * qty;
                        price = Double.parseDouble(priceFormat.format(price));
                        io.setPrice(price);

                        //Update the text view for the price
                        String p = "£" + price;
                        tvPrice.setText(p);
                    }

                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < order.size(); i++){

                    ItemOrder io = order.get(i);

                    String fullName = proteinAndName.get(position);
                    String fullNameOrder = order.get(i).getName();
                    String fullNameOrderProtein = order.get(i).getProtein() + " " + order.get(i).getName();

                    if(fullName.equals(fullNameOrder) || fullName.equals(fullNameOrderProtein)){
                        //The item is the one which the qty is trying to increase

                        //Get the qty of the item
                        int qty = io.getQty();
                        int qtyFirst = qty;
                        double priceCurrent = io.getPrice();
                        qty--;

                        //Set the text to be the number and the item to have it
                        tvQty.setText(qty+"");

                        io.setQty(qty);

                        //Update the price

                        //Formatting for the price to round and display properly
                        DecimalFormat priceFormat = new DecimalFormat("#.##");

                        double basePrice = (priceCurrent / qtyFirst);
                        double price = basePrice * qty;
                        price = Double.parseDouble(priceFormat.format(price));
                        io.setPrice(price);

                        //Update the text view for the price
                        String p = "£" + price;
                        tvPrice.setText(p);

                        if(qty == 0){
                            order.remove(io);
                            //Refresh the page
                            Intent intent = new Intent(getContext(), BasketActivity.class);
                            context.startActivity(intent);
                        }
                    }

                }
            }
        });

        return rowView;
    }

}
