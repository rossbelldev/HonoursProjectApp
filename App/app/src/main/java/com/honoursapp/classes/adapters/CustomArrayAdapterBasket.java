package com.honoursapp.classes.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.honoursapp.R;

import java.util.ArrayList;

public class CustomArrayAdapterBasket extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> proteinAndName;
    private final ArrayList<String> price;

    public CustomArrayAdapterBasket(Activity context, ArrayList<String> proteinAndName, ArrayList<String> price){

        super(context, R.layout.list_basket_items, proteinAndName);

        this.context = context;
        this.proteinAndName = proteinAndName;
        this.price = price;
    }

    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_basket_items,null);

        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvPrice = (TextView) rowView.findViewById(R.id.tvPrice);

        tvName.setText(proteinAndName.get(position));

        String p = "£" + price.get(position);
        tvPrice.setText(p);

        return rowView;
    }

}
