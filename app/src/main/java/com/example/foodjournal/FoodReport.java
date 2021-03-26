package com.example.foodjournal;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.foodjournal.DatabaseHelper.TABLE_NAME;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;


public class FoodReport extends AppCompatActivity {

    View appBackground3;
    EditText startDate, endDate;
    Button search;

    DatabaseHelper myDB;
    ArrayList food_desc, food_qty, food_dtIntake, food_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_report);

        // Set background
        appBackground3 = findViewById(R.id.appbg3);
        appBackground3.setBackgroundResource(R.drawable.bg_blue);

        //Setting of the variables
        startDate = (EditText) findViewById(R.id.txt_start_date);
        endDate = (EditText) findViewById(R.id.txt_end_date);
        search = (Button) findViewById(R.id.btn_search);

        myDB = new DatabaseHelper(FoodReport.this);

        food_desc = new ArrayList();
        food_qty = new ArrayList();
        food_dtIntake = new ArrayList();
        food_comments = new ArrayList();

        // Set return button
        Button btnReturn3 = (Button) findViewById(R.id.btn_return3);
        btnReturn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        //Date and time picker codes for start and end dates
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(startDate);
            }
        });

        endDate.setInputType(InputType.TYPE_NULL);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(endDate);
            }
        });
        addTable();
    }

    //Add table layout and rows dynamically
    public void addTable(){
        TableLayout reportTable = (TableLayout) FoodReport.this.findViewById(R.id.table_report);
        TableRow headerRow = new TableRow(this);
        reportTable.setStretchAllColumns(true);
        reportTable.setBackgroundColor(Color.parseColor("#ffffff"));


        //Create headers for our table
        TextView tv0 = new TextView(this);
        tv0.setText("Description");
        tv0.setTextColor(Color.BLACK);
        tv0.setPadding(5,5,5,5);
        headerRow.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText("Quantity");
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(5,5,5,5);
        headerRow.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText("Date");
        tv2.setTextColor(Color.BLACK);
        tv2.setPadding(5,5,5,5);
        headerRow.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText("Actions");
        tv3.setTextColor(Color.BLACK);
        tv3.setPadding(5,5,5,5);
        headerRow.addView(tv3);

        reportTable.addView(headerRow);

        Cursor cursor = myDB.getAllData();
        if(cursor.getCount()  == 0){
            Toast.makeText(this,  "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                TableRow rows = new TableRow(this);
                TextView textView0 = new TextView(this);
                textView0.setText(cursor.getString(2));
                textView0.setTextColor(Color.BLACK);
                textView0.setPadding(5, 5, 5, 5);
                rows.addView(textView0);

                TextView textView1 = new TextView(this);
                textView1.setText(cursor.getString(3));
                textView1.setTextColor(Color.BLACK);
                textView1.setPadding(5, 5, 5, 5);
                rows.addView(textView1);

                TextView textView2 = new TextView(this);
                textView2.setText(cursor.getString(4));
                textView2.setTextColor(Color.BLACK);
                textView2.setPadding(5, 5, 5, 5);
                rows.addView(textView2);

                reportTable.addView(rows);
            }
        }
    }



    // Date and Time picker method...
    private void showDateTimeDialog(final EditText date_time_entry) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date_time_entry.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(FoodReport.this,timeSetListener,
                        calendar.get(HOUR_OF_DAY),
                        calendar.get(MINUTE),
                        false).show();
            }
        };

        new DatePickerDialog(this, dateSetListener,
                calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DAY_OF_MONTH)).show();
    }

}
