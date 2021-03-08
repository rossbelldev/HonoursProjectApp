package com.receiverapp.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.receiverapp.R;
import com.receiverapp.activities.classes.ItemOrder;
import com.receiverapp.activities.classes.Order;

import java.util.ArrayList;

public class ViewOrderTemplateActivity extends AppCompatActivity {

    // Text Views
    TextView tvOrderId, tvDest;

    // List Views
    ListView lvOrderItems;

    // Buttons
    Button btnAccept, btnReject;

    // Order which has been passed
    Order order;

    // Order items list, String and not
    ArrayList<ItemOrder> ioList = new ArrayList<>();
    ArrayList<String> itemsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_template);

        // Retrieve the order which has been passed
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            order = (Order) extras.get("order");
        }

        // Pair the Text Views
        tvOrderId = (TextView) findViewById(R.id.tvOrderId);
        tvDest = (TextView) findViewById(R.id.tvDest);

        // Pair the List view
        lvOrderItems = (ListView) findViewById(R.id.lvOrderItems);

        // Pair the buttons
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnReject = (Button) findViewById(R.id.btnReject);

        // Display the appropriate information
        displayInfo();

        // Onclick listeners for the buttons
        // btnReject
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the reject function
                reject(order.getName());
            }
        });

        // btnAccept
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the accept function
                accept(order.getName());
            }
        });

    }

    // Function for displaying the order information
    public void displayInfo(){
        // The order which has been passed and retrieved via the extras will contain all of the information necessary
        // Display the order id
        tvOrderId.setText(order.getName());
        // Display the order destination
        tvDest.setText(order.getDestination());
        // Add all of the items on the order to a list
        ioList = order.getOrderItems();
        // Get the name from each of these items to be displayed
        for(ItemOrder io : ioList){
            String name = io.getName();
            String protein = io.getProtein();
            int qty = io.getQty();
            if(protein != null){
                name = protein + " " + name;
            }
            if(qty > 1){
                name = qty + " x " + name;
            }
            itemsList.add(name);
        }
        // Populate the list view with this information
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, itemsList);
        lvOrderItems.setAdapter(adapter);
    }

    // Function for processing rejected order requests
    private void reject(String orderId){
        // Add the order to the rejected orders database field
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("RejectedOrders").child(orderId);
        ref.setValue(order);
        // Call the remove function to remove the order from orders, since it has been added to rejected
        remove(orderId);
        // Refund the order
        refund();
    }

    // Function for processing accepted order requests
    private void accept(String orderId){
        // Add the order to the accepted orders database field
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("AcceptedOrders").child(orderId);
        ref.setValue(order);
        // Call the remove function to remove the order from orders, since it has been added to accepted
        remove(orderId);
        // Start the print order activity, pass the order which is to be printed
        Intent i = new Intent(getApplicationContext(), PrintActivity.class);
        i.putExtra("order", order);
        startActivity(i);
    }

    // Function for removing the order from the Orders field.
    private void remove(String orderId){
        // Remove the order from the orders field, as it will have been added to accepted or rejected orders
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Orders").child(orderId);
        ref.removeValue();
    }

    // Function for refunding the order upon rejection by the restaurant
    private void refund(){
        // TODO: Make a refund function
    }
}