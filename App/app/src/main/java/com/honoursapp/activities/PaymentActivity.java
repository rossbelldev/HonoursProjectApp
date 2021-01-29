package com.honoursapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.honoursapp.R;
import com.honoursapp.classes.holders.MethodHolder;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Find out what the method is, if it is 0 then ask them which table they are at
        //Otherwise, ask for their name and other requirements

        //Get the method from the method holder
        final ArrayList<Integer> methodHolder = MethodHolder.getInstance().method;

        int method = methodHolder.get(0);

        if(method == 0){
            //Ordering to table
        }else{
            //Ordering for collection
        }

    }
}