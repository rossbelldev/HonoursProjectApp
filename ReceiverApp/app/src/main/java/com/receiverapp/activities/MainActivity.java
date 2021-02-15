package com.receiverapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.receiverapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database reference for the orders
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        // Detect changes when anything is added to this node
        myRef.
    }
}