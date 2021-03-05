package com.example.foodjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;



public class FoodReport extends AppCompatActivity {

    View appBackground3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_report);

        // Set background
        appBackground3 = findViewById(R.id.appbg3);
        appBackground3.setBackgroundResource(R.drawable.bg_blue);

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
