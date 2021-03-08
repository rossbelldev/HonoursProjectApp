package com.receiverapp.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;
import com.receiverapp.R;

public class MainActivity extends AppCompatActivity {

    // Buttons
    Button btnBar, btnRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pair the buttons
        btnBar = (Button) findViewById(R.id.btnBar);
        btnRestaurant = (Button) findViewById(R.id.btnRestaurant);

        // Onclick for btn Bar
        btnBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), BarActivity.class);
                startActivity(i);
            }
        });

        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RestaurantActivity.class);
                startActivity(i);
            }
        });

        // Unsubscribe from all
        FirebaseMessaging.getInstance().unsubscribeFromTopic("NewOrderRest");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("NewOrderBar");

    }
}