package com.example.foodjournal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Class that starts the execution of the program.
 * @author java-champs
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CS246 MainActivity";
    Button btnStart, btnView, btnSettings, btnCredit;
    View appBackground;


    /**
     * Initialize the activity.
     * @author java-champs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "The main activity has been loaded correctly");

        // Set main background
        appBackground = findViewById(R.id.appbg);
        appBackground.setBackgroundResource(R.drawable.bg_blue);

        // Assigning buttons
        btnStart = findViewById(R.id.btn_start);
        btnView = findViewById(R.id.btn_view);
        btnSettings = findViewById(R.id.btn_settings);
        btnCredit = findViewById(R.id.btn_credit);

        //When click the start button, call a new activity to type a food entry
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodEntryActivity.class));
                Log.d(TAG, "The user clicked the start entry button...");
            }
        });

        //When click the View button, call a new activity to see the report with all the food you record.
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodReportActivity.class));
                Log.d(TAG, "The user clicked the view journal button...");
            }
        });

        //When click the Settings button, call a new activity to config the user settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserSettingsActivity.class));
                Log.d(TAG, "The user clicked the user settings button...");
            }
        });

        //When click the credits button, call a new activity to show the credits
        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreditActivity.class));
                Log.d(TAG, "The user clicked the credit activity...");
            }
        });
    }

}
















