package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnView, btnSettings;
    View appBackground;

    // Shared preferences
    SharedPreferences mPrefs;
    Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodEntry.class));
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodReport.class));
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserSettings.class));
            }
        });
    }

    public UserSettings getUserSettings(){
        Gson gson = new Gson();
        String json = mPrefs.getString("UserSettings", "");
        UserSettings us = gson.fromJson(json, UserSettings.class);
        return us;
    }

    public void saveUserSettings(UserSettings us){
        Gson gson = new Gson();
        String json = gson.toJson(us);
        prefsEditor.putString("UserSettings", json);
        prefsEditor.commit();
        Toast.makeText(getApplicationContext(), "User settings stored", Toast.LENGTH_SHORT).show();
    }
}
















