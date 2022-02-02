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

public class suppliersmodule extends AppCompatActivity {

    TextView mydate;
    TextView mytime;
    EditText ed1;
    EditText ed2;
    Button saveid;
    Button displayid;
    Button update;
    Button delete;
    ListView lst1;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliersmodule);

        ed1 = findViewById(R.id.suppliername);

        ed2 = findViewById(R.id.salesid);

        saveid = findViewById(R.id.saveid);

        displayid = findViewById(R.id.displaybtn);

        update = findViewById(R.id.update);

        delete = findViewById(R.id.delete);

        displayid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newnumber();
                ed1.setText("");

            }
        });


        saveid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();

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
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }


        };


        thread.start();

    }
    public void dtcalender()
    {
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

        String id = ed2.getText().toString();
        String name = ed1.getText().toString();


        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter Supplier Name to Save", Toast.LENGTH_SHORT).show();
        }
        else {

            String selectQuery=String.format("SELECT * from supplierinfo where name='%s'",name);
            //Toast.makeText(getApplicationContext(), selectQuery, Toast.LENGTH_SHORT).show();

            try {
                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS supplierinfo(id integer primary key autoincrement,name varchar)");
                Cursor cursor = db.rawQuery(selectQuery, null);
                cursor.moveToFirst();

                if (cursor.getCount() > 0) {
                    Toast.makeText(getApplicationContext(), "This Supplier Is Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "New", Toast.LENGTH_SHORT).show();
                    String sql = "insert into supplierinfo(name) values(?)";
                    SQLiteStatement statements = db.compileStatement(sql);
                    statements.bindString(1, name);
                    statements.execute();
                    Toast.makeText(this, "Supplier Added", Toast.LENGTH_SHORT).show();
                    ed1.setText("");


                    ed1.requestFocus();

                    newnumber();
                    display();
                }
                cursor.close();
                db.close();
            }
            catch(Exception er){
                Toast.makeText(this, er.getMessage(), Toast.LENGTH_SHORT).show();
            }




        }

    }


    public void display() {
        try {



            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS supplierinfo(id integer primary key autoincrement,name varchar)");
            lst1 = findViewById(R.id.lstview);
            final Cursor c = db.rawQuery("select * from supplierinfo", null);
            int id = c.getColumnIndex("id");
            int name = c.getColumnIndex("name");

            titles.clear();
            arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles);
            lst1.setAdapter(arrayAdapter);

            final ArrayList<supplierclass> stud = new ArrayList<supplierclass>();

            if (c.moveToFirst()) {
                do {

                    supplierclass stu = new supplierclass();
                    stu.id = c.getString(id);
                    stu.name = c.getString(name);

                    stud.add(stu);
                    titles.add(c.getString(id) + "   " + c.getString(name));


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
                    supplierclass stu = stud.get(i);
                    ed2.setText(stu.id);
                    ed1.setText(stu.name);


                }
            });
        } catch (Exception er) {
            Toast.makeText(this, "No Records Found", Toast.LENGTH_SHORT).show();
        }

    }


    public void delete() {
        try {

            String id = ed2.getText().toString();
            String name = ed1.getText().toString();
            if(name.equals("")) {
                Toast.makeText(getApplicationContext(), "Please Select Any Supplier to Delete", Toast.LENGTH_SHORT).show();
            }
            else{

                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                String sql = "delete from supplierinfo where id=?";
                SQLiteStatement statements = db.compileStatement(sql);
                statements.bindString(1, id);
                statements.execute();
                Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
                ed1.setText("");
                ed2.setText("");

                ed1.requestFocus();

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

            String name = ed1.getText().toString();

            String id = ed2.getText().toString();


            if(name.equals("")) {
                Toast.makeText(getApplicationContext(), "Please Select Any Supplier to Update", Toast.LENGTH_SHORT).show();

            }
            else{

                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                //db.execSQL("CREATE TABLE IF NOT EXISTS records (id integer primary key autoincrement,name varchar, course varchar, fee varchar)");
                String sql = "update supplierinfo set name=? where id=?";
                SQLiteStatement statements = db.compileStatement(sql);
                statements.bindString(1, name);
                statements.bindString(2, id);
                statements.execute();
                Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
                db.close();
                ed1.setText("");
                ed2.setText("");


                ed1.requestFocus();

                newnumber();
                display();
            }

        } catch (Exception er) {
            Toast.makeText(this, "Course Not Register Due To Some Error", Toast.LENGTH_SHORT).show();
        }

    }


    public int newnumber() {



        String selectQuery = "SELECT max(id) as id FROM supplierinfo";
        SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS supplierinfo(id integer primary key autoincrement,name varchar)");
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        @SuppressLint("Range") int maxid = cursor.getInt(cursor.getColumnIndex("id"));
        String newmaxid=String.valueOf(maxid+1);
        ed2.setText(newmaxid);
        cursor.close();
        db.close();

        return maxid;


    }

}