package com.receiverapp.activities.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    ArrayList<Order> orderList = new ArrayList<>();

    // The order path
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_orders);

        // Retrieve the extras (in this case will be the path (Orders or OrdersBar))
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            path = extras.getString("path");
        }

        // Pair the textViews
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        // Pair the ListView
        lvOrdersRequested = (ListView) findViewById(R.id.lvOrdersRequested);

        // Pair the Buttons
        btnRefresh = (Button) findViewById(R.id.btnRefresh);

        // Get the latest orders
        getRequestedOrders(path);

        // Refresh button on click listener
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequestedOrders(path);
            }
        });

        // Onclick listener for the list view
        lvOrdersRequested.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ViewOrderTemplateActivity.class);
                intent.putExtra("order", orderList.get(i));
                startActivity(intent);
            }
        });

    }

    // Function to retrieve the orders and display them in a list view
    private void getRequestedOrders(String path){

        // Array adapter for the list view
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, toDisplay);
        lvOrdersRequested.setAdapter(adapter);

        // Database reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(path);

        // Event listener for the reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear to display
                toDisplay.clear();

                // For each data snapshot retrieved
                for(DataSnapshot ds : snapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    toDisplay.add(order.getDestination());
                    // Add the order to a list of orders to be used by the list view
                    orderList.add(order);
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