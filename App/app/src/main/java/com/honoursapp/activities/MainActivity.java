package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.honoursapp.R;

public class MainActivity extends AppCompatActivity {

    //Create buttons to be used across program
    Button btnBurger, btnOrderToTable, btnOrderForCollection, btnBookTable, btnCheck;

    //Firebase
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise Buttons
        btnBurger = (Button) findViewById(R.id.btnBurger);
        btnOrderToTable = (Button) findViewById(R.id.btnOrderToTable);
        btnOrderForCollection = (Button) findViewById(R.id.btnOrderForCollection);
        btnBookTable = (Button) findViewById(R.id.btnBookTable);
        btnCheck = (Button) findViewById(R.id.btnCheckSignIn);

        //Initialise the firebase authentication
        auth = FirebaseAuth.getInstance();

        //On click listeners for buttons
        btnOrderToTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open the order to table view
                Intent i = new Intent(v.getContext(), OrderActivity.class);
                i.putExtra("method", 0);
                startActivity(i);
            }
        });

        btnOrderForCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open the order for collection view
                Intent i = new Intent(v.getContext(), OrderActivity.class);
                i.putExtra("method", 1);
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

        //Can test if a user is signed in or not. If they are then proceed, else get them to register. (Will be done automatically at some point)
        /*
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check to see if the user is signed in
                FirebaseUser u = auth.getCurrentUser();
                if(u != null){
                    String s = "The user is currently signed in! " + u.getEmail();
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "No user is currently signed in", Toast.LENGTH_LONG).show();
                }
            }
        });
         */

    }
}