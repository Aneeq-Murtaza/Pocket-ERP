package com.example.pocketerp;

//All Libraries used in this activity
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class salesmodule extends AppCompatActivity {

    // Variable initialization
    TextView mydate;
    TextView mytime;
    Spinner customerspinner;
    Spinner productspinner;
    Button savesales;
    Button display;
    Button update;
    Button delete;
    ListView lst1;
    EditText salesid;
    EditText salestitle;
    EditText comapnyname;
    EditText productname;
    EditText productqty;
    EditText amount;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    // Create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesmodule);


        // Assigning values to variables with their id
        salesid=findViewById(R.id.salesid);
        salestitle=findViewById(R.id.salestitle);
        customerspinner=findViewById(R.id.customerspinner);
        productspinner=findViewById(R.id.usernameid);
        productqty=findViewById(R.id.salesquantity);
        amount=findViewById(R.id.salesamount);
        savesales=findViewById(R.id.savesales);
        display=findViewById(R.id.newsales);
        update=findViewById(R.id.updatesales);
        delete=findViewById(R.id.deletesales);

        // Onclick Listeners
        savesales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newnumber();
                salestitle.setText("");
                productqty.setText("");
                amount.setText("");
                customerspinner.setAdapter(null);
                productspinner.setAdapter(null);
                getallcustomers();
                getallproducts();
                salesid.requestFocus();
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

        // Calling date and time function
        dtcalender();

        // Threading for date and time
        Thread thread=new Thread() {
            @Override
            public void run() {

                try {
                    while(!isInterrupted())
                    {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dtcalender();

                            }
                        });
                    }
                }
                catch (Exception er)
                {

                }
            }
        };

        thread.start();
        getallproducts();
        getallcustomers();
        newnumber();
        display();

        productspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try {


                    String selectedItem = parent.getItemAtPosition(position).toString();
                    if (selectedItem.equals("Product Name")) {

                    } else {


                            ArrayList<String> arrayList1 = new ArrayList<String>();
                            String selectQuery =String.format("select * from inventinfo where name='%s'",selectedItem);
                            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                            db.execSQL("CREATE TABLE IF NOT EXISTS inventinfo(id integer primary key autoincrement,name varchar,description varchar,qty varchar, price varchar)");
                            Cursor cursor = db.rawQuery(selectQuery, null);
                            cursor.moveToFirst();

                            if (cursor.moveToFirst()) {
                                do {

                                    @SuppressLint("Range") String cusname1 = cursor.getString(cursor.getColumnIndex("qty"));
                                    arrayList1.add(cusname1);
                                    @SuppressLint("Range") String cusname2 = cursor.getString(cursor.getColumnIndex("price"));
                                    arrayList1.add(cusname2);

                                }
                                while (cursor.moveToNext());

                               productqty.setText(arrayList1.get(0));
                               amount.setText(arrayList1.get(1));

                                cursor.close();
                                db.close();
                            }




                    }
                }
                catch (Exception er)
                {
                    Toast.makeText(getApplicationContext(), "Error in Getting Product Qty and Price", Toast.LENGTH_SHORT).show();
                }

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }


    // Display date and time function
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

    // Gets all customer's in spinners
    public void getallcustomers()
    {
        try {
                ArrayList<String> arrayList1 = new ArrayList<String>();
                arrayList1.add("Customer Name");
                String selectQuery = "select * from customerinfo";
                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS customerinfo(id integer primary key autoincrement,name varchar)");
                Cursor cursor = db.rawQuery(selectQuery, null);
                cursor.moveToFirst();

                if (cursor.moveToFirst()) {
                    do {

                        @SuppressLint("Range") String cusname1 = cursor.getString(cursor.getColumnIndex("name"));
                        arrayList1.add(cusname1);

                        }
                while (cursor.moveToNext());
                customerspinner = (Spinner) findViewById(R.id.customerspinner);
                ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList1);
                arrayadapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                customerspinner.setAdapter(arrayadapter);
                arrayadapter.notifyDataSetChanged();
                cursor.close();
                db.close();
                }
            }
            catch (Exception er)
            {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
    }

    // Get all product to Spinner
    public void getallproducts()
    {
        try {
            ArrayList<String> arrayList1 = new ArrayList<String>();
            arrayList1.add("Product Name");
            String selectQuery = "select * from inventinfo";
            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS inventinfo(id integer primary key autoincrement,name varchar,description varchar,qty varchar, price varchar)");
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                do {

                    @SuppressLint("Range") String cusname1 = cursor.getString(cursor.getColumnIndex("name"));
                    arrayList1.add(cusname1);

                }
                while (cursor.moveToNext());


                productspinner = (Spinner) findViewById(R.id.usernameid);
                ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList1);
                arrayadapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                productspinner.setAdapter(arrayadapter);
                arrayadapter.notifyDataSetChanged();
                cursor.close();
                db.close();
            }

        }
        catch (Exception er)
        {
            Toast.makeText(getApplicationContext(), "Error in getting Products in sales module", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to insert sales date in sales table
    public void insert()
    {
            String salesidnew = salesid.getText().toString();
            String salestitlenew = salestitle.getText().toString();
            String customername = customerspinner.getSelectedItem().toString();
            String productname = productspinner.getSelectedItem().toString();
            String producttcquantity = productqty.getText().toString();
            String productamount = amount.getText().toString();

            if (salestitlenew == null) {
                Toast.makeText(getApplicationContext(), "Enter Sales Title", Toast.LENGTH_SHORT).show();
            } else {


                try {
                    SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS salesinfo(id integer primary key autoincrement,title varchar,cname varchar,pname varchar, qty varchar,price varchar)");
                    String sql = "insert into salesinfo(title,cname,pname,qty,price) values(?,?,?,?,?)";
                    SQLiteStatement statements = db.compileStatement(sql);
                    statements.bindString(1, salestitlenew);
                    statements.bindString(2, customername);
                    statements.bindString(3, productname);
                    statements.bindString(4, producttcquantity);
                    statements.bindString(5, productamount);
                    statements.execute();
                    Toast.makeText(this, "New Sales Added", Toast.LENGTH_SHORT).show();
                    clearfields();
                    db.close();

                } catch (Exception er) {
                    Toast.makeText(this, er.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
    }

    // Display the next id for new sales
    public int newnumber() {


        String selectQuery = "SELECT max(id) as id FROM salesinfo";
        SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS salesinfo(id integer primary key autoincrement,title varchar,cname varchar,pname varchar, qty varchar,price varchar)");
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        @SuppressLint("Range") int maxid = cursor.getInt(cursor.getColumnIndex("id"));
        String newmaxid = String.valueOf(maxid + 1);
        salesid.setText(newmaxid);
        cursor.close();
        db.close();

        return maxid;

    }

    // Display's all the sales in List view
    public void display() {
        try {

            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS salesinfo(id integer primary key autoincrement,title varchar,cname varchar,pname varchar, qty varchar,price varchar)");
            lst1 = findViewById(R.id.saleslistview);
            final Cursor c = db.rawQuery("select * from salesinfo", null);
            int sid = c.getColumnIndex("id");
            int stitle = c.getColumnIndex("title");
            int scname = c.getColumnIndex("cname");
            int spname = c.getColumnIndex("pname");
            int sqty = c.getColumnIndex("qty");
            int sprice = c.getColumnIndex("price");

            titles.clear();
            arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles);
            lst1.setAdapter(arrayAdapter);

            final ArrayList<salesclass> stud = new ArrayList<salesclass>();

            if (c.moveToFirst()) {
                do {
                    salesclass stu = new salesclass();
                    stu.id = c.getString(sid);
                    stu.titles = c.getString(stitle);
                    stu.customername = c.getString(scname);
                    stu.productname = c.getString(spname);
                    stu.quanity = c.getString(sqty);
                    stu.price = c.getString(sprice);
                    stud.add(stu);
                    titles.add(c.getString(sid) + "   " + c.getString(stitle));
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
                    salesclass stu = stud.get(i);
                    salesid.setText(stu.id);
                    salestitle.setText(stu.titles);
                    customerspinner.setAdapter(null);
                    productspinner.setAdapter(null);

                    ArrayList<String> customerlist1 = new ArrayList<String>();
                    customerlist1.add(stu.customername);
                    ArrayAdapter<String> customerarrayadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,customerlist1);
                    customerarrayadapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    customerspinner.setAdapter(customerarrayadapter);
                    customerarrayadapter.notifyDataSetChanged();

                    ArrayList<String> productlist1 = new ArrayList<String>();
                    productlist1.add(stu.productname);
                    ArrayAdapter<String> productarrayadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,productlist1);
                    productarrayadapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    productspinner.setAdapter(productarrayadapter);
                    productarrayadapter.notifyDataSetChanged();

                    productqty.setText(stu.quanity);
                    amount.setText(stu.price);
                }
            });
        } catch (Exception er) {
            Toast.makeText(this, "No Records Found", Toast.LENGTH_SHORT).show();
        }

    }
    // Function to delete any sale
    public void delete() {
        try {

            String salesidnew = salesid.getText().toString();
            String salestitlenew = salestitle.getText().toString();
            if(salestitlenew.equals("")) {
                Toast.makeText(getApplicationContext(), "Please give any title to sales", Toast.LENGTH_SHORT).show();
            }
            else{
                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                String sql = "delete from salesinfo where id=?";
                SQLiteStatement statements = db.compileStatement(sql);
                statements.bindString(1,salesidnew);
                statements.execute();
                Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
                clearfields();
            }
        } catch (Exception er) {
            Toast.makeText(this, "Error in deleting sales", Toast.LENGTH_SHORT).show();
        }

    }

    // Function to edit sale's
    public void edit() {
        try {
            String salesidnew = salesid.getText().toString();
            String salestitlenew = salestitle.getText().toString();
            String customername = customerspinner.getSelectedItem().toString();
            String productname = productspinner.getSelectedItem().toString();
            String producttcquantity = productqty.getText().toString();
            String productamount = amount.getText().toString();

            if(salestitlenew.equals("")) {
                Toast.makeText(getApplicationContext(), "Please Select Any Sales to Update", Toast.LENGTH_SHORT).show();

            }
            else{

                SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
                String sql = "update salesinfo set title=?,cname=?,pname=?,qty=?,price=? where id=?";
                SQLiteStatement statements = db.compileStatement(sql);
                statements.bindString(1, salestitlenew);
                statements.bindString(2, customername);
                statements.bindString(3, productname);
                statements.bindString(4, producttcquantity);
                statements.bindString(5, productamount);
                statements.bindString(6, salesidnew);
                statements.execute();
                Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
                db.close();
                clearfields();
            }

        } catch (Exception er) {
            Toast.makeText(this, "Error in updating sales module", Toast.LENGTH_SHORT).show();
        }

    }

    // Function to clear edit Text Fields
    public void clearfields()
    {
        newnumber();
        salestitle.setText("");
        productqty.setText("");
        amount.setText("");
        customerspinner.setAdapter(null);
        productspinner.setAdapter(null);
        getallcustomers();
        getallproducts();
        salesid.requestFocus();
        display();
    }

    public void removeproducts() {
        try {

            String pqty = productqty.getText().toString();
            String pprice = amount.getText().toString();
            String ppname = productspinner.getSelectedItem().toString();
            SQLiteDatabase db = openOrCreateDatabase("pocketerpsqlitedbnew", Context.MODE_PRIVATE, null);
            String sql = "update inventinfo set qty=?,price=? where name=?";
            SQLiteStatement statements = db.compileStatement(sql);
            statements.bindString(1, pqty);
            statements.bindString(2, pprice);
            statements.bindString(3, ppname);
            statements.execute();
            Toast.makeText(this, "Product Information Updated", Toast.LENGTH_SHORT).show();
            db.close();



        } catch (Exception er) {
            Toast.makeText(this, "Error updating product's after sales", Toast.LENGTH_SHORT).show();
        }

    }



}