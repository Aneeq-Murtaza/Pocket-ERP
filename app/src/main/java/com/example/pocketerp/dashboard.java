package com.example.pocketerp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class dashboard extends AppCompatActivity {

    EditText editText;
    CardView salescard;
    CardView inventoryscard;
    CardView purchasecard;
    CardView suppliercard;
    CardView customercard;
    CardView statisticscard;
    CardView settings;
    CardView invoics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        salescard = findViewById(R.id.salescard);
        inventoryscard = findViewById(R.id.inventorycard);
        purchasecard = findViewById(R.id.purchasecard);
        suppliercard = findViewById(R.id.suppliercard);
        customercard = findViewById(R.id.customercard);
        statisticscard = findViewById(R.id.statisticscard);
        settings = findViewById(R.id.settingscard);
        //invoics = findViewById(R.id.invoicecarfd);

        // Onclick Listeners
        salescard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),salesmodule.class);
                startActivity(intent);

            }
        });
        inventoryscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),inventorymodule.class);
                startActivity(intent);

            }
        });
        purchasecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),purchasemodule.class);
                startActivity(intent);

            }
        });

        suppliercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),suppliersmodule.class);
                startActivity(intent);

            }
        });
        customercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),customersmodule.class);
                startActivity(intent);

            }
        });
        statisticscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Statisticsmodule.class);
                startActivity(intent);

            }
        });
//        invoics.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(getApplicationContext(),invoicemodule.class);
//                startActivity(intent);
//
//            }
//        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), settingsmodule.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dashboard.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}