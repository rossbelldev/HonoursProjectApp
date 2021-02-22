package com.receiverapp.activities.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.receiverapp.R;
import com.receiverapp.activities.classes.Order;

import java.util.ArrayList;


public class RequestedOrdersActivity extends AppCompatActivity {

    // TextView
    TextView tvTitle;

    // Buttons
    Button btnRefresh;

    // ListView
    ListView lvOrdersRequested;

    // Array list for items to be displayed
    ArrayList<String> toDisplay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_orders);

        // Pair the textViews
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        // Pair the ListView
        lvOrdersRequested = (ListView) findViewById(R.id.lvOrdersRequested);

        // Pair the Buttons
        btnRefresh = (Button) findViewById(R.id.btnRefresh);

        // Get the latest orders
        getRequestedOrders();

        // Refresh button on click listener
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequestedOrders();
            }
        });

    }

    // Function to retrieve the orders and display them in a list view
    private void getRequestedOrders(){

        // Array adapter for the list view
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, toDisplay);
        lvOrdersRequested.setAdapter(adapter);

        // Database reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Orders");

        // Event listener for the reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear to display
                toDisplay.clear();

                // For each data snapshot retrieved
                for(DataSnapshot ds : snapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    toDisplay.add(order.getName());
                }

                // Update what is displayed on the array adapter
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Problem: " + error, Toast.LENGTH_LONG).show();
            }
        });

    }
}