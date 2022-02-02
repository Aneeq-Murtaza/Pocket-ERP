package com.example.pocketerp;

// All imported libraries
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class login extends AppCompatActivity {

    // Initializing Components
    Button loginbtn;
    TextView regbtn;
    Spinner spinner;
    EditText password;

    // Create method runs first
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Adding Spinners
        spinner = (Spinner) findViewById(R.id.usernameid);

        // Setting values with original components
        loginbtn = findViewById(R.id.loginbtn);
        regbtn = findViewById(R.id.myloginid);
        password = findViewById(R.id.setpassword);

        // Calling event listeners
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkpassword();

            }
        });

        String selectQuery123 = "select * from registerinfo";
        SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS registerinfo(id integer primary key autoincrement,cname varchar,caddress varchar,phone varchar, email varchar,username varchar,password varchar)");
        Cursor cursor = db.rawQuery(selectQuery123, null);
        if (cursor.getCount() <= 0) {
            insert();
        }

        getusername();
    }

    public void getusername() {
        try {
            ArrayList<String> arrayList1 = new ArrayList<String>();
            String selectQuery = "select * from registerinfo";
            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS inventinfo(id integer primary key autoincrement,name varchar,description varchar,qty varchar, price varchar)");
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                do {

                    @SuppressLint("Range") String cusname1 = cursor.getString(cursor.getColumnIndex("username"));
                    arrayList1.add(cusname1);

                }
                while (cursor.moveToNext());

                ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList1);
                arrayadapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinner.setAdapter(arrayadapter);
                arrayadapter.notifyDataSetChanged();
                cursor.close();
                db.close();
            }
        } catch (Exception er) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void insert() {


        try {
            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS registerinfo(id integer primary key autoincrement,cname varchar,caddress varchar,phone varchar, email varchar,username varchar,password varchar)");
            String sql = "insert into registerinfo(cname,caddress,phone,email,username,password) values(?,?,?,?,?,?)";
            SQLiteStatement statements = db.compileStatement(sql);
            statements.bindString(1, "Pocket ERP");
            statements.bindString(2, "Karachi");
            statements.bindString(3, "+92 22922929");
            statements.bindString(4, "pocketerp@gmail.com");
            statements.bindString(5, "Admin");
            statements.bindString(6, "admin");
            statements.execute();
            db.close();

        } catch (Exception er) {
            Toast.makeText(this, er.getMessage(), Toast.LENGTH_SHORT).show();


        }
    }

    public void checkpassword() {

        try {

            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS registerinfo(id integer primary key autoincrement,cname varchar,caddress varchar,phone varchar, email varchar,username varchar,password varchar)");
            Cursor c = db.rawQuery("select * from registerinfo", null);

            int cusername = c.getColumnIndex("username");
            int cpassword = c.getColumnIndex("password");

            ArrayList<String> marraylist = new ArrayList<String>();

            if (c.moveToFirst()) {
                do {

                    marraylist.add(c.getString(cusername));
                    marraylist.add(c.getString(cpassword));

                }
                while (c.moveToNext());

                if(marraylist.get(0).equals(spinner.getSelectedItem().toString()) && marraylist.get(1).equals(password.getText().toString()))
                {
                    Intent intent =new Intent(getApplicationContext(), dashboard.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                }

                c.close();
                db.close();
            }

        }
        catch (Exception er)
        {
            Toast.makeText(getApplicationContext(), "Error in display", Toast.LENGTH_SHORT).show();
        }

    }


}