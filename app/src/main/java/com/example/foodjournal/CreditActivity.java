package com.example.foodjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreditActivity extends AppCompatActivity {

    // Activity variables
    View appBackground5;
    Button btnReturn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        // Set background
        appBackground5 = findViewById(R.id.appbg5);
        appBackground5.setBackgroundResource(R.drawable.bg_blue);

        // Set return button
        btnReturn5 = (Button) findViewById(R.id.btn_return5);
        btnReturn5.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
