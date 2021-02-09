package com.honoursapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.honoursapp.R;
import com.honoursapp.calculations.OrganiseOrder;
import com.honoursapp.classes.Order;
import com.honoursapp.classes.PayPalClientIDConfig;
import com.honoursapp.classes.holders.MethodHolder;
import com.honoursapp.classes.holders.OrderHolder;
import com.honoursapp.classes.items.ItemOrder;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class PayActivity extends AppCompatActivity {

    // Spinner
    Spinner spTableChoice;

    // Edit Text
    EditText etPickupName;

    // Text views
    TextView tvTableSelect, tvPickupName;

    // Buttons
    Button btnPay;

    // Order to be used
    Order order = new Order();

    // Order total price
    double totalPrice = 0;

    // Requisites for PayPal
    private int payPalRequestCode = 12;

    private static PayPalConfiguration payPalConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PayPalClientIDConfig.paypalId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent i = new Intent(this, PayPalService.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        startService(i);

        // Get the method from the method holder
        final ArrayList<Integer> methodHolder = MethodHolder.getInstance().method;

        // Get the order from the Order data holder
        final ArrayList<ItemOrder> orderHeld = OrderHolder.getInstance().order;

        // Pair Spinner
        spTableChoice = (Spinner) findViewById(R.id.spTableSelection);

        // Pair text views
        tvTableSelect = (TextView) findViewById(R.id.tvTableSelect);
        tvPickupName = (TextView) findViewById(R.id.tvPickupName);

        // Pair edit texts
        etPickupName = (EditText) findViewById(R.id.etPickupName);

        // Pair Buttons
        btnPay = (Button) findViewById(R.id.btnPay);

        // Set adapter for spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.tables_array, android.R.layout.simple_spinner_item);
        spTableChoice.setAdapter(spinnerAdapter);

        // Find out what the method is, if it is 0 then ask them which table they are at
        // Otherwise, ask for their name and other requirements
        int method = methodHolder.get(0);

        if(method == 0){
            // Ordering to table, show table number drop down selection and title
            tvTableSelect.setVisibility(View.VISIBLE);
            spTableChoice.setVisibility(View.VISIBLE);
        }else{
            // Ordering for collection, take account name for collection name
            tvPickupName.setVisibility(View.VISIBLE);
            etPickupName.setVisibility(View.VISIBLE);
        }

        // Determine the total cost of the order
        for(ItemOrder io : orderHeld){
            totalPrice+=io.getPrice();
        }

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Determine the location of the order (table number or person to collect it depending on the method)
                String location = "";
                boolean met = false;
                if(method == 0){
                    location = spTableChoice.getSelectedItem().toString();
                    met = true;
                }else if(method == 1 && !etPickupName.getText().toString().isEmpty()){
                    met = true;
                    location = etPickupName.getText().toString();
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter a pickup name!", Toast.LENGTH_SHORT).show();
                }

                if(met){
                    // Finalise the order as an order object
                    order.setDestination(location);
                    order.setOrderItems(orderHeld);
                    order.setTotalPrice(totalPrice);

                    // Move to payment now
                    PayPalPay(totalPrice);
                }

            }

        });

    }

    private void PayPalPay(double totalPrice){

        PayPalPayment payment = new PayPalPayment(new BigDecimal(totalPrice), "GBP", "Order Alma",  PayPalPayment.PAYMENT_INTENT_SALE);

        Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        i.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(i, payPalRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == payPalRequestCode){
            if(requestCode == Activity.RESULT_OK){
                Toast.makeText(getApplicationContext(), "Payment made!", Toast.LENGTH_LONG).show();
                addOrderDB();
            }else{
                Toast.makeText(getApplicationContext(), "Payment unsuccessful!", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void addOrderDB(){
        // Format the order so that the items are shown in the right way
        OrganiseOrder(order);
        // Generate the order id
        String id = UUID.randomUUID().toString();

        // Get the firebase reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Orders").child(id);

        // Update the order
        ref.setValue(order);
    }
}