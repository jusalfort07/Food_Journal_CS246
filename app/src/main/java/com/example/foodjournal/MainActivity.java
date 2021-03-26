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

    // Shared preferences
    SharedPreferences mPrefs;
    Editor prefsEditor;

    /**
     * Initialize the activity.
     * @author java-champs
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "MS - onCreate: Started.");
        // Get shared preferences
        mPrefs = getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        UserSettings myGetUserSettings = getUserSettings();
        if (myGetUserSettings != null) {
            System.out.println("user name: " + myGetUserSettings.getName());
            System.out.println("email: " + myGetUserSettings.getEmail());
            System.out.println("send report?: " + myGetUserSettings.getSendReport());
            System.out.println("frequency: " + myGetUserSettings.getFrequency());
        } else {
            UserSettings us = new UserSettings();
            us.setName("Gabriel");
            us.setEmail("gabriel@example.com");
            us.setSendReport(false);
            System.out.println("user name: " + us.getName());
            System.out.println("email: " + us.getEmail());
            System.out.println("send report?: " + us.getSendReport());
            System.out.println("frequency: " + us.getFrequency());
        }

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
                startActivity(new Intent(MainActivity.this, UserSettings.class));
            }
        });
    }

    /**
     * Create a new object with the user setting values.
     * @author java-champs
     * @version 1.0
     * @return UserSettings object.
     */
    public UserSettings getUserSettings(){
        Gson gson = new Gson();
        String json = mPrefs.getString("UserSettings", "");
        UserSettings us = gson.fromJson(json, UserSettings.class);
        return us;
    }

    /**
     * Save the user settings values.
     * @author java-champs
     * @version 1.0
     * @param us UserSettings object
     */
    public void saveUserSettings(UserSettings us){
        Gson gson = new Gson();
        String json = gson.toJson(us);
        prefsEditor.putString("UserSettings", json);
        prefsEditor.commit();
        Toast.makeText(getApplicationContext(), "User settings stored", Toast.LENGTH_SHORT).show();
    }
}
















