package com.honoursapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.honoursapp.R;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;

import java.util.ArrayList;

public class PrintTestActivity extends AppCompatActivity {

    //Button for print and pair
    Button btnPrint, btnPair;

    //Printing
    Printing pri;

    //Edit Text
    EditText etTextInp;

    //String to be used across contexts
    String inp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_test);

        //Initialise print tooth
        Printooth.INSTANCE.init(this);

        //Pair the buttons
        btnPrint = (Button) findViewById(R.id.btnPrint);
        btnPair = (Button) findViewById(R.id.btnPair);

        //Pair the edit text
        etTextInp = (EditText) findViewById(R.id.etTextInp);

//        if(pri != null){
//            pri.setPrintingCallback(this);
//        }

        //When the pair button is clicked
        btnPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If it is paired then unpair, else pair
                if(Printooth.INSTANCE.hasPairedPrinter()){
                    Printooth.INSTANCE.removeCurrentPrinter();
                }else{
                    startActivityForResult(new Intent(PrintTestActivity.this, ScanningActivity.class),ScanningActivity.SCANNING_FOR_PRINTER);
                }
            }
        });

        //When the print button is clicked
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If there is no paired printer then pair
                if(!Printooth.INSTANCE.hasPairedPrinter()){
                    startActivityForResult(new Intent(PrintTestActivity.this, ScanningActivity.class),ScanningActivity.SCANNING_FOR_PRINTER);
                }else{
                    print();
                }

            }
        });

        changePairAndUnpair();

    }

    private void changePairAndUnpair() {
        if (Printooth.INSTANCE.hasPairedPrinter())
            btnPair.setText(new StringBuilder("Un-pair ").append(Printooth.INSTANCE
                    .getPairedPrinter().getName()).toString());
        else
            btnPair.setText("Pair with Printer");
    }

    private void print(){
        ArrayList<Printable> prints = new ArrayList<>();
        prints.add(new RawPrintable.Builder(new byte[]{27, 100, 4}).build());

        inp = etTextInp.getText().toString();

        //Set the text to be printed
//        prints.add(new TextPrintable.Builder()
//                .setText("Order Number 001")
//                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
//                .setNewLinesAfter(1)
//                .build());    r

        prints.add(new TextPrintable.Builder()
                .setText(inp)
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());

        //pri.print(prints);
        Printooth.INSTANCE.printer().print(prints);

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