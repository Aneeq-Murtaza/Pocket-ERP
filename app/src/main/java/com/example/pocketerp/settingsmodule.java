package com.example.pocketerp;

// All libraries
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class settingsmodule extends AppCompatActivity {

    // Initializing components
    Button updatebtn;
    EditText companyname;
    EditText companyaddress;
    EditText phonenumber;
    EditText email;
    EditText username;
    EditText password;

    // create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsmodule);

        // Getting id's of all variables
        updatebtn=findViewById(R.id.updatebtnsettings);
        companyname=findViewById(R.id.companyname);
        companyaddress=findViewById(R.id.companyaddress);
        phonenumber=findViewById(R.id.phonenumber);
        email=findViewById(R.id.emailaddress);
        username=findViewById(R.id.setusername);
        password=findViewById(R.id.setpassword);

        // Database table creation
        String selectQuery123 = "select * from registerinfo";
        SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS registerinfo(id integer primary key autoincrement,cname varchar,caddress varchar,phone varchar, email varchar,username varchar,password varchar)");
        Cursor cursor = db.rawQuery(selectQuery123, null);
        if(cursor.getCount()<=0)
        {
            insert();
        }
        // Onclick Listeners
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit();

            }
        });
        display();
    }
    // Function to insert sales date in sales table
    public void insert()
    {


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
                //Toast.makeText(this, "Company Info Inserted", Toast.LENGTH_SHORT).show();
                db.close();

            } catch (Exception er) {
                Toast.makeText(this, er.getMessage(), Toast.LENGTH_SHORT).show();


        }
    }

    public void display() {

            try {

                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS registerinfo(id integer primary key autoincrement,cname varchar,caddress varchar,phone varchar, email varchar,username varchar,password varchar)");
                final Cursor c = db.rawQuery("select * from registerinfo", null);
                int sid = c.getColumnIndex("id");
                int cname = c.getColumnIndex("cname");
                int caddress = c.getColumnIndex("caddress");
                int cphone = c.getColumnIndex("phone");
                int cemail = c.getColumnIndex("email");
                int cusername = c.getColumnIndex("username");
                int cpassword = c.getColumnIndex("password");

                final ArrayList<settingsclass> stud = new ArrayList<settingsclass>();

                if (c.moveToFirst()) {
                    do {
                        settingsclass stu = new settingsclass();
                        stu.id = c.getString(sid);
                        stu.companyname = c.getString(cname);
                        stu.companyaddress = c.getString(caddress);
                        stu.companyphone = c.getString(cphone);
                        stu.companyemail = c.getString(cemail);
                        stu.companyuser = c.getString(cusername);
                        stu.companypass = c.getString(cpassword);
                        stud.add(stu);
                        companyname.setText(stu.companyname);
                        companyaddress.setText(stu.companyaddress);
                        phonenumber.setText(stu.companyphone);
                        email.setText(stu.companyemail);
                        username.setText(stu.companyuser);
                        password.setText(stu.companypass);

                    }
                    while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }
            catch (Exception er)
            {
                Toast.makeText(getApplicationContext(), "Error in display", Toast.LENGTH_SHORT).show();
            }

    }



    // Function to edit sale's
    public void edit() {
        try {
            String companyname1 = companyname.getText().toString();
            String companyaddress1 = companyaddress.getText().toString();
            String phonenumber1 = phonenumber.getText().toString();
            String email1 = email.getText().toString();
            String username1 = username.getText().toString();
            String password1 = password.getText().toString();

            if(companyname1.equals("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Company Name to Update", Toast.LENGTH_SHORT).show();

            }
            else{

                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                String sql = "update registerinfo set cname=?,caddress=?,phone=?,email=?,username=?,password=? where id=1";
                SQLiteStatement statements = db.compileStatement(sql);
                statements.bindString(1, companyname1);
                statements.bindString(2, companyaddress1);
                statements.bindString(3, phonenumber1);
                statements.bindString(4, email1);
                statements.bindString(5, username1);
                statements.bindString(6, password1);
                statements.execute();
                Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
                db.close();
                display();


            }

        } catch (Exception er) {
            Toast.makeText(this, "Error in updating sales module", Toast.LENGTH_SHORT).show();
        }

    }
}