package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class FoodEntry extends AppCompatActivity {

    View appBackground2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_entry);

        // Set background
        appBackground2 = findViewById(R.id.appbg2);
        appBackground2.setBackgroundResource(R.drawable.bg_blue);
    }
}