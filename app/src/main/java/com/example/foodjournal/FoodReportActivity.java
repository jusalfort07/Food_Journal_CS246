package com.example.foodjournal;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class FoodReportActivity extends AppCompatActivity {

//    View appBackground3;
    DatabaseHelper foodDB;
    private static final String TAG = "FoodReportActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_report_ms);
        Log.i(TAG, "MS - onCreate: Started.");

        ListView rptListView = (ListView) findViewById(R.id.listView);
        foodDB = new DatabaseHelper(this);
        Log.i(TAG, "MS - Database Object Created.");

//        ArrayList<String> theList = new ArrayList<String>();
//        Cursor data = foodDB.getAllData();
//
//        if (data.getCount() == 0) {
//            Toast.makeText(this, "There are no entries in this journal!", Toast.LENGTH_LONG).show();
//        } else {
//            Log.i(TAG, "MS - about to read DB.");
//            while (data.moveToNext()) {
//                theList.add(data.getString(2));
//            }
//            Log.i(TAG, "MS - finished reading DB.");
//            rptListView.setAdapter(new MyListAdapter(this, R.layout.adapter_food_report, theList));
//        }

        ArrayList<FoodEntry> theNewList = new ArrayList<FoodEntry>();
        theNewList = foodDB.getFoodReport();
        Log.i(TAG, "MS - DB List created.");
        rptListView.setAdapter(new FoodReportAdapter(this, R.layout.adapter_food_report, theNewList));

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
    }
}
