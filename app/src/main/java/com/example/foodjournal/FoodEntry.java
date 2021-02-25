package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FoodEntry extends AppCompatActivity {

    View appBackground2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_entry);

        // Set background
        appBackground2 = findViewById(R.id.appbg2);
        appBackground2.setBackgroundResource(R.drawable.bg_blue);

        // Set return button
        Button btnReturn2 = (Button) findViewById(R.id.btn_return2);
        btnReturn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }
}