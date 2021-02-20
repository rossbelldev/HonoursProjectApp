package com.receiverapp.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;
import com.receiverapp.R;

public class MainActivity extends AppCompatActivity {

    //Buttons
    Button btnOrderReq, btnBookingReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pair the buttons
        btnBookingReq = (Button) findViewById(R.id.btnBookingsReq);
        btnOrderReq = (Button) findViewById(R.id.btnOrdersReq);

        // Detect changes when anything is added to this node
        FirebaseMessaging.getInstance().subscribeToTopic("NewOrder");

        // Onclick listener for btnBookingsReq
        btnBookingReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RequestedBookingsActivity.class);
                startActivity(i);
            }
        });

        // Onclick listener for btnOrdersReq
        btnOrderReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RequestedOrdersActivity.class);
                startActivity(i);
            }
        });

    }
}