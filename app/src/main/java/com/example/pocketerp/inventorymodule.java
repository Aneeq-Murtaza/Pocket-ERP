package com.example.pocketerp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class inventorymodule extends AppCompatActivity {

    TextView mydate;
    TextView mytime;

    EditText id;
    EditText name;
    EditText desc;
    EditText qty;
    EditText price;

    Button save;
    Button display;
    Button update;
    Button delete;
    ListView lst1;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventorymodule);

        save = findViewById(R.id.inventsave);
        display = findViewById(R.id.inventnew);
        update= findViewById(R.id.inventupdate);
        delete = findViewById(R.id.inventdelete);

        id = (EditText) findViewById(R.id.salesid);
        name = (EditText) findViewById(R.id.inventname);
        desc = (EditText) findViewById(R.id.inventdesc);
        qty = (EditText) findViewById(R.id.inventquantity);
        price = (EditText) findViewById(R.id.inventprice);

        dtcalender();

        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dtcalender();

                            }
                        });
                    }
                } catch (Exception er) {

                }

            }


        };


        thread.start();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insert();

            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newnumber();
                name.setText("");
                desc.setText("");
                qty.setText("");
                price.setText("");

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                delete();

            }
        });



        newnumber();
        display();

    }

    public void dtcalender() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");
        String date = df.format(c.getTime());
        String time = dt.format(c.getTime());
        mydate = findViewById(R.id.datetextview);

        mytime = findViewById(R.id.timetextview);
        mydate.setText(date.toString());
        mytime.setText(time.toString());
    }


    public void insert() {

        try {

            String pname = name.getText().toString();
            String pdesc = desc.getText().toString();
            String pqty = qty.getText().toString();
            String pprice = price.getText().toString();
            if (pname == null) {
                Toast.makeText(getApplicationContext(), pname + "pnamehere", Toast.LENGTH_SHORT).show();
            } else {

                String selectQuery = String.format("SELECT * from inventinfo where name='%s'", pname);
//
                try {
                    SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS inventinfo(id integer primary key autoincrement,name varchar,description varchar,qty varchar, price varchar)");
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    cursor.moveToFirst();

                    if (cursor.getCount() > 0) {
                        Toast.makeText(getApplicationContext(), "This Product Already Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplicationContext(), "New", Toast.LENGTH_SHORT).show();
                        String sql = "insert into inventinfo(name,description,qty,price) values(?,?,?,?)";
                        SQLiteStatement statements = db.compileStatement(sql);
                        statements.bindString(1, pname);
                        statements.bindString(2, pdesc);
                        statements.bindString(3, pqty);
                        statements.bindString(4, pprice);

                        statements.execute();
                        Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();

                        cursor.close();
                        db.close();

                        name.setText("");
                        desc.setText("");
                        qty.setText("");
                        price.setText("");
                        newnumber();
                        display();
                        name.requestFocus();

                    }

                } catch (Exception er) {
                    Toast.makeText(this, er.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception er) {
            Toast.makeText(this, er.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public int newnumber() {


            String selectQuery = "SELECT max(id) as id FROM inventinfo";
            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS inventinfo(id integer primary key autoincrement,name varchar,description varchar,qty varchar, price varchar)");
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();

            @SuppressLint("Range") int maxid = cursor.getInt(cursor.getColumnIndex("id"));
            String newmaxid = String.valueOf(maxid + 1);
            id.setText(newmaxid);
            cursor.close();
            db.close();

            return maxid;

    }

    public void display() {
        try {



            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS inventinfo(id integer primary key autoincrement,name varchar,description varchar,qty varchar, price varchar)");
            lst1 = findViewById(R.id.inventlistview);
            final Cursor c = db.rawQuery("select * from inventinfo", null);
            int id1 = c.getColumnIndex("id");
            int name1 = c.getColumnIndex("name");
            int desc1 = c.getColumnIndex("description");
            int qty1 = c.getColumnIndex("qty");
            int price1 = c.getColumnIndex("price");

            titles.clear();
            arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles);
            lst1.setAdapter(arrayAdapter);

            final ArrayList<inventclass> stud = new ArrayList<inventclass>();

            if (c.moveToFirst()) {
                do {

                    inventclass stu = new inventclass();
                    stu.id = c.getString(id1);
                    stu.name = c.getString(name1);
                    stu.desc = c.getString(desc1);
                    stu.qty = c.getString(qty1);
                    stu.price = c.getString(price1);

                    stud.add(stu);
                    titles.add(c.getString(id1) + "   " + c.getString(name1));


                }
                while (c.moveToNext());
                arrayAdapter.notifyDataSetChanged();
                lst1.invalidateViews();
                c.close();
                db.close();
            }

            lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String as = titles.get(i).toString();
                    inventclass stu = stud.get(i);
                    id.setText(stu.id);
                    name.setText(stu.name);
                    desc.setText(stu.desc);
                    qty.setText(stu.qty);
                    price.setText(stu.price);

                }
            });
        } catch (Exception er) {
            Toast.makeText(this, "No Records Found", Toast.LENGTH_SHORT).show();
        }

    }

    public void delete() {
        try {

            String pid= id.getText().toString();
            String pname = name.getText().toString();
            if(name.equals("")) {
                Toast.makeText(getApplicationContext(), "Please Select Any Product to Delete", Toast.LENGTH_SHORT).show();
            }
            else{

                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                String sql = "delete from inventinfo where id=?";
                SQLiteStatement statements = db.compileStatement(sql);
                statements.bindString(1, pid);
                statements.execute();
                Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
                name.setText("");
                desc.setText("");
                qty.setText("");
                price.setText("");


                name.requestFocus();

                newnumber();
                display();
                db.close();
            }

        } catch (Exception er) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    public void edit() {
        try {
            String pid= id.getText().toString();
            String pname = name.getText().toString();
            String pdesc = desc.getText().toString();
            String pqty = qty.getText().toString();
            String pprice = price.getText().toString();


            if(name.equals("")) {
                Toast.makeText(getApplicationContext(), "Please Select Any Product to Update", Toast.LENGTH_SHORT).show();

            }
            else{

                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                String sql = "update inventinfo set name=?,description=?,qty=?,price=? where id=?";
                SQLiteStatement statements = db.compileStatement(sql);
                statements.bindString(1, pname);
                statements.bindString(2, pdesc);
                statements.bindString(3, pqty);
                statements.bindString(4, pprice);
                statements.bindString(5, pid);
                statements.execute();
                Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
                db.close();
                name.setText("");
                desc.setText("");
                qty.setText("");
                price.setText("");
                name.requestFocus();
                newnumber();
                display();
                db.close();
            }

        } catch (Exception er) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }
}