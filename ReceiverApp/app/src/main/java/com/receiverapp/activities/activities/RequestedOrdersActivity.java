package com.receiverapp.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.receiverapp.R;


public class RequestedOrdersActivity extends AppCompatActivity {

    // TextView
    TextView tvTitle;

    // ListView
    ListView lvOrdersRequested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_orders);

        // Pair the textViews
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        // Pair the ListView
        lvOrdersRequested = (ListView) findViewById(R.id.lvOrdersRequested);

        // Populate the list view with orders which have come in


    }
}