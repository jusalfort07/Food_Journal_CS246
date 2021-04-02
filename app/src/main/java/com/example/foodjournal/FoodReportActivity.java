package com.example.foodjournal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;


public class FoodReportActivity extends AppCompatActivity {

    DatabaseHelper foodDB;
    EditText dtFilterFromTxt, dtFilterToTxt;
    Button btnFilter;
    ListView rptListView;
    ArrayList<FoodEntry> theNewList;
    FoodReportAdapter rAdapter;
    private static final String TAG = "FoodReportActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_report_ms);
//        Log.i(TAG, "MS - onCreate: Started.");

        dtFilterFromTxt = (EditText) findViewById(R.id.txt_FilterStartDate);
        dtFilterToTxt = (EditText) findViewById(R.id.txt_FilterEndDate);
        rptListView = (ListView) findViewById(R.id.listView);
        btnFilter = (Button) findViewById(R.id.btn_filter);
        btnFilter.setFocusable(true);
        btnFilter.setFocusableInTouchMode(true);
        btnFilter.requestFocus();
        foodDB = new DatabaseHelper(this);
//        Log.i(TAG, "MS - Database Object Created.");

        theNewList = new ArrayList<FoodEntry>();
        String dtFrom = dtFilterFromTxt.getText().toString();
        String dtTo = dtFilterToTxt.getText().toString();
        if (dtFrom.matches("") || dtTo.matches("")) {
            theNewList = foodDB.getAllEntries();
        } else {
            theNewList = foodDB.getFilteredEntry(dtFrom, dtTo);
        }

        rptListView.setAdapter(new FoodReportAdapter(this, R.layout.adapter_food_report, theNewList));


        // Date and Time Picker codes...
        dtFilterFromTxt.setInputType(InputType.TYPE_NULL);
        dtFilterFromTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDateDialog(dtFilterFromTxt);
                }
            }
        });

        dtFilterToTxt.setInputType(InputType.TYPE_NULL);
        dtFilterToTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showDateDialog(dtFilterToTxt);
            }
        });

        // Set filter button
        Button btnFilter = (Button) findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dtFrom = dtFilterFromTxt.getText().toString();
                String dtTo = dtFilterToTxt.getText().toString();
                rptListView.setAdapter(null);
                if (dtFrom.matches("") || dtTo.matches("")) {
                    theNewList = foodDB.getAllEntries();
                    Toast.makeText(FoodReportActivity.this, "No Filter Applied", Toast.LENGTH_SHORT).show();
                } else {
                    theNewList.clear();
                    theNewList = foodDB.getFilteredEntry(dtFrom, dtTo);
                    int arrSize = theNewList.size();
                    Toast.makeText(FoodReportActivity.this, "Entries found: " + arrSize, Toast.LENGTH_SHORT).show();
                }
                rptListView.setAdapter(new FoodReportAdapter(FoodReportActivity.this, R.layout.adapter_food_report, theNewList));
            }
        });

        // Set clear button
        Button btnClear = (Button) findViewById(R.id.btn_clearfilter);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtFilterFromTxt.setText("");
                dtFilterToTxt.setText("");
                theNewList = foodDB.getAllEntries();
                rptListView.setAdapter(null);
                rptListView.setAdapter(new FoodReportAdapter(FoodReportActivity.this, R.layout.adapter_food_report, theNewList));
                Toast.makeText(FoodReportActivity.this, "Filter Cleared!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set return button
        Button btnReturn3 = (Button) findViewById(R.id.btn_return3);
        btnReturn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    /**
     * Method which shows the system calendar and time selection windows for
     * entering the date and time when the food/liquid is taken by the user.
     * @param date_entry field where date and time is on the page
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    private void showDateDialog(final EditText date_entry) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date_entry.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(this, dateSetListener,
                calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DAY_OF_MONTH)).show();
    }
}
