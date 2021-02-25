package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class FoodReport extends AppCompatActivity {

    View appBackground3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_report);

        // Set background
        appBackground3 = findViewById(R.id.appbg3);
        appBackground3.setBackgroundResource(R.drawable.bg_blue);
    }
}