package com.honoursapp.classes.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.honoursapp.R;

import java.util.ArrayList;

// https://www.javatpoint.com/android-custom-listview

public class CustomArrayAdapterProteins extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> name;
    private final ArrayList<String> price;

    public CustomArrayAdapterProteins(Activity context, ArrayList<String> name, ArrayList<String> price){

        super(context, R.layout.list_protein_items, name);

        this.context = context;
        this.name = name;
        this.price = price;
    }

    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_protein_items,null); //,true);

        TextView tvName = (TextView) rowView.findViewById(R.id.tvProteinName);
        TextView tvPrice = (TextView) rowView.findViewById(R.id.tvPrice);

        tvName.setText(name.get(position));
        String p = "£" + price.get(position);
        tvPrice.setText(p);

        return rowView;
    }

}
