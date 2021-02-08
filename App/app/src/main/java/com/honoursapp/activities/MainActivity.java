package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.honoursapp.R;
import com.honoursapp.classes.holders.MethodHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Create buttons to be used across program
    Button btnBurger, btnOrderToTable, btnOrderForCollection, btnBookTable;

    // Firebase
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise Buttons
        btnBurger = (Button) findViewById(R.id.btnBurger);
        btnOrderToTable = (Button) findViewById(R.id.btnOrderToTable);
        btnOrderForCollection = (Button) findViewById(R.id.btnOrderForCollection);
        btnBookTable = (Button) findViewById(R.id.btnBookTable);

        // Initialise the firebase authentication
        auth = FirebaseAuth.getInstance();

        // Get the boolean for the method
        ArrayList<Integer> method = MethodHolder.getInstance().method;

        // Check to see if the user is signed in
        FirebaseUser u = auth.getCurrentUser();
        if(u == null){
            // The user is not signed in, or does not have and account, redirect them to sign up page
            Intent i = new Intent(getApplicationContext(), RegisterSignInActivity.class);
            startActivity(i);
        }

        // On click listeners for buttons
        btnOrderToTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open the order to table view
                Intent i = new Intent(v.getContext(), OrderActivity.class);
                method.clear();
                method.add(0);
                startActivity(i);
            }
        });

        btnOrderForCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open the order for collection view
                Intent i = new Intent(v.getContext(), OrderActivity.class);
                method.clear();
                method.add(1);
                startActivity(i);
            }
        });

        btnBookTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open the book table view
                Intent i = new Intent(v.getContext(), BookTableActivity.class);
                startActivity(i);
            }
        });

        btnBurger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display the burger bar menu (currently lets people sign in etc)
                Intent i = new Intent(v.getContext(), RegisterSignInActivity.class);
                startActivity(i);
            }
        });

    }
}