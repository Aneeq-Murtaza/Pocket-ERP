package com.example.pocketerp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Statisticsmodule extends AppCompatActivity {

    TextView salesview;
    TextView purchaseview;
    TextView productsview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticsmodule);
        salesview=findViewById(R.id.salesviewno);
        purchaseview=findViewById(R.id.purchaseviewno);
        productsview=findViewById(R.id.productviewno);
        getsales();
        getproducts();
        getallpurchases();
    }

    public void getsales() {



        String selectQuery = "SELECT count(*) FROM salesinfo";
        SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS salesinfo(id integer primary key autoincrement,title varchar,cname varchar,pname varchar, qty varchar,price varchar)");
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            int mycount=cursor.getInt(0);
            salesview.setText(String.valueOf(mycount));
        }
        cursor.close();
        db.close();

    }

//    public int getpurchases() {
//
//
//        String selectQuery = "SELECT max(id) as id FROM salesinfo";
//        SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS salesinfo(id integer primary key autoincrement,title varchar,cname varchar,pname varchar, qty varchar,price varchar)");
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToFirst();
//        @SuppressLint("Range") int maxid = cursor.getInt(cursor.getColumnIndex("id"));
//        String newmaxid = String.valueOf(maxid + 1);
//        salesid.setText(newmaxid);
//        cursor.close();
//        db.close();
//
//        return maxid;
//
//    }

    public void getproducts() {


        String selectQuery = "SELECT count(*) FROM inventinfo";
        SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS inventinfo(id integer primary key autoincrement,name varchar,description varchar,qty varchar, price varchar)");

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int mycount = cursor.getInt(0);
            productsview.setText(String.valueOf(mycount));
        }
        cursor.close();
        db.close();

    }

    public void getallpurchases() {


        String selectQuery = "SELECT count(*) FROM purchaseinfo";
        SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS purchaseinfo(id integer primary key autoincrement,title varchar,purname varchar,pname varchar, qty varchar,price varchar)");

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int mycount = cursor.getInt(0);
            purchaseview.setText(String.valueOf(mycount));
        }
        cursor.close();
        db.close();

    }

}