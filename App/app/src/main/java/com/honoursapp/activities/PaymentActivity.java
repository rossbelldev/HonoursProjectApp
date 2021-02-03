package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.honoursapp.R;
import com.honoursapp.classes.holders.MethodHolder;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    //Spinner
    Spinner spTableChoice;

    //Edit Text
    EditText etPickupName;

    //Text views
    TextView tvTableSelect, tvPickupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Find out what the method is, if it is 0 then ask them which table they are at
        //Otherwise, ask for their name and other requirements

        //Get the method from the method holder
        final ArrayList<Integer> methodHolder = MethodHolder.getInstance().method;

        //Pair Spinner
        spTableChoice = (Spinner) findViewById(R.id.spTableSelection);

        //Pair text views
        tvTableSelect = (TextView) findViewById(R.id.tvTableSelect);
        tvPickupName = (TextView) findViewById(R.id.tvPickupName);

        //Pair edit texts
        etPickupName = (EditText) findViewById(R.id.etPickupName);

        //Set adapter for spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.tables_array, android.R.layout.simple_spinner_item);
        spTableChoice.setAdapter(spinnerAdapter);

        int method = methodHolder.get(0);

        if(method == 0){
            //Ordering to table, show table number drop down selection and title
            tvTableSelect.setVisibility(View.VISIBLE);
            spTableChoice.setVisibility(View.VISIBLE);
        }else{
            //Ordering for collection, take account name for collection name
            tvPickupName.setVisibility(View.VISIBLE);
            etPickupName.setVisibility(View.VISIBLE);
        }

    }
}