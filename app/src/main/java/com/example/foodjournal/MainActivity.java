package com.example.foodjournal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

/**
 * Class that starts the execution of the program.
 * @author java-champs
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button btnStart, btnView, btnSettings;
    View appBackground;

//    // Shared preferences
//    SharedPreferences mPrefs;
//    Editor prefsEditor;

    /**
     * Initialize the activity.
     * @author java-champs
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set main background
        appBackground = findViewById(R.id.appbg);
        appBackground.setBackgroundResource(R.drawable.bg_blue);

        // Assigning buttons
        btnStart = findViewById(R.id.btn_start);
        btnView = findViewById(R.id.btn_view);
        btnSettings = findViewById(R.id.btn_settings);

        //When click the start button, call a new activity to type a food entry
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodEntryActivity.class));
            }
        });

        //When click the View button, call a new activity to see the report with all the food you record.
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodReportActivity.class));
            }
        });

        //When click the Settings button, call a new activity to config the user settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserSettingsActivity.class));
            }
        });
    }
}
















