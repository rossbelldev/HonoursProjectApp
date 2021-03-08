package com.receiverapp.activities.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.receiverapp.R;
import com.receiverapp.activities.classes.ItemOrder;
import com.receiverapp.activities.classes.Order;

import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;

import java.util.ArrayList;

public class PrintActivity extends AppCompatActivity {

    // Buttons
    Button btnPair, btnPrint;

    // Text view
    TextView tvPrintPrev;

    // Order to be populated from extras
    Order order = new Order();

    // Printing
    Printing pri;

    String orderString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        // Initialise print tooth
        Printooth.INSTANCE.init(this);

        // Retrieve the extras
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            order = (Order) extras.get("order");
        }

        // Pair the Buttons
        btnPair = (Button) findViewById(R.id.btnPair);
        btnPrint = (Button) findViewById(R.id.btnPrint);

        // Pair the Text View
        tvPrintPrev = (TextView) findViewById(R.id.tvPrintPreview);

        // Populate the text view
        showOrder();

        // Pair button onclick
        btnPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the printer is paired, then un-pair, else pair
                if(Printooth.INSTANCE.hasPairedPrinter()){
                    Printooth.INSTANCE.removeCurrentPrinter();
                }else{
                    startActivityForResult(new Intent(PrintActivity.this, ScanningActivity.class),ScanningActivity.SCANNING_FOR_PRINTER);
                }
            }
        });

        // Print button onclick
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If there is no paired printer, then pair
                if(!Printooth.INSTANCE.hasPairedPrinter()){
                    startActivityForResult(new Intent(PrintActivity.this, ScanningActivity.class),ScanningActivity.SCANNING_FOR_PRINTER);
                }else{
                    print();
                }
            }
        });

        // Call the function to update UI.
        changePairAndUnpair();

    }

    // Function for printing the order
    private void print(){

        ArrayList<Printable> prints = new ArrayList<>();
        prints.add(new RawPrintable.Builder(new byte[]{27,100,4}).build());

        prints.add(new TextPrintable.Builder()
                .setText(orderString)
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());

        Printooth.INSTANCE.printer().print(prints);
    }

    // Function to sort the display depending on what printer is connected to
    private void changePairAndUnpair(){
        if (Printooth.INSTANCE.hasPairedPrinter())
            btnPair.setText(new StringBuilder("Un-pair ").append(Printooth.INSTANCE
                    .getPairedPrinter().getName()).toString());
        else
            btnPair.setText("Pair with Printer");
    }

    // Function for displaying the order
    private void showOrder(){

        // Display the order by Name/Table, then the items in order they appear
        orderString = "Destination: " + order.getDestination() + "\n\n\n";

        // For all the items, add them to the string
        for(ItemOrder io : order.getOrderItems()){

            String protein = io.getProtein();
            String name = io.getName();
            int qty = io.getQty();

            if(protein != null){
                // There is a protein, display it
                orderString = orderString + qty + " x " + protein + " " + name + "\n";
            }else{
                // No protein, add item as normal
                orderString = orderString + qty + " x " + name + "\n";
            }

        }

        // Add the price to the bottom of the order, followed by 3 blank likes (for printer tear)
        double price = order.getTotalPrice();
        orderString = orderString + "\n\nTotal price: £" + price + "\n\n\n";

        // Update the text view to show this information
        tvPrintPrev.setText(orderString);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK)
            initPrinting();
        changePairAndUnpair();
    }

    private void initPrinting() {
        if (!Printooth.INSTANCE.hasPairedPrinter())
            pri = Printooth.INSTANCE.printer();
    }


}