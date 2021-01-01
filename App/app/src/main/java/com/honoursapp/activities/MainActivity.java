package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.honoursapp.R;

public class MainActivity extends AppCompatActivity {

    //Create buttons to be used across program
    Button btnBurger, btnOrderToTable, btnOrderForCollection, btnBookTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise Buttons
        btnBurger = (Button) findViewById(R.id.btnBurger);
        btnOrderToTable = (Button) findViewById(R.id.btnOrderToTable);
        btnOrderForCollection = (Button) findViewById(R.id.btnOrderForCollection);
        btnBookTable = (Button) findViewById(R.id.btnBookTable);

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
                //Display the burger bar menu
            }
        });

    }
}