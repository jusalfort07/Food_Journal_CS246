package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnView, btnSettings;
    View appBackGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // First comment test
        // Second comment to practice merging conflicts
        // Third comment from Michael for testing...can someone please confirm that they can see this.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set main background
        appBackGround = findViewById(R.id.appbg);
        appBackGround.setBackgroundResource(R.drawable.bg_blue);

        // Assigning buttons
        btnStart = findViewById(R.id.btn_start);
        btnView = findViewById(R.id.btn_view);
        btnSettings = findViewById(R.id.btn_settings);
    }
}