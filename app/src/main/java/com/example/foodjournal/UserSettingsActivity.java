package com.example.foodjournal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class UserSettingsActivity extends AppCompatActivity {

    // Activity variables
    View appBackground4;
    EditText txtName, txtEmail;
    CheckBox chkSendRpt, chkWeek, chkMonth;
    RadioButton optWeek, optMonth, selectedFrequency;
    RadioGroup grpFrequency;
    Button btnSave;

    // Shared preferences
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        // Get shared preferences
        mPrefs = getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        // Assign variables for each field
        txtName = (EditText) findViewById(R.id.name);
        txtEmail = (EditText) findViewById(R.id.email);
        chkSendRpt = (CheckBox) findViewById(R.id.sendReport);
        optWeek = (RadioButton) findViewById(R.id.weekly);
        optMonth = (RadioButton) findViewById(R.id.monthly);
        grpFrequency = (RadioGroup) findViewById(R.id.rptFrequency);

        // Upload user settings data if shared preferences is not null
        UserSettings myGetUserSettings = getUserSettings();
        if (myGetUserSettings != null) {
            txtName.setText(myGetUserSettings.getName());
            txtEmail.setText(myGetUserSettings.getEmail());

            if(myGetUserSettings.getSendReport() == true) {
                chkSendRpt.setChecked(true);
            } else {
                chkSendRpt.setChecked(false);
            }

            if (myGetUserSettings.getFrequency().equals("Weekly")) {
                optWeek.setChecked(true);
            }

            if (myGetUserSettings.getFrequency().equals("Monthly")) {
                optMonth.setChecked(true);
            }

            System.out.println("frequency: " + myGetUserSettings.getFrequency());
        }

        // Set save button
        btnSave = (Button) findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOption = grpFrequency.getCheckedRadioButtonId();
                selectedFrequency = (RadioButton) findViewById(selectedOption);

                UserSettings us = new UserSettings();
                us.setName(txtName.getText().toString());
                us.setEmail(txtEmail.getText().toString());
                us.setFrequency(selectedFrequency.getText().toString());
                if (chkSendRpt.isChecked()) {
                    us.setSendReport(true);
                } else {
                    us.setSendReport(false);
                }
                saveUserSettings(us);
                System.out.println(selectedFrequency.getText().toString());
                Toast.makeText(UserSettingsActivity.this, "Settings Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set background
        appBackground4 = findViewById(R.id.appbg4);
        appBackground4.setBackgroundResource(R.drawable.bg_blue);

        // Set return button
        Button btnReturn4 = (Button) findViewById(R.id.btn_return4);
        btnReturn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    // Save activity instance
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

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



