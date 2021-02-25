package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSettings extends AppCompatActivity {

    String name;
    String email;
    Boolean sendReport;
    String frequency;
    View appBackground4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        // Set background
        appBackground4 = findViewById(R.id.appbg4);
        appBackground4.setBackgroundResource(R.drawable.bg_blue);

        // Set return button
        Button btnReturn4 = (Button) findViewById(R.id.btn_return4);
        btnReturn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }


    public UserSettings() {
        this.name = "";
        this.email = "";
        this.sendReport = true;
        this.frequency = "weekly";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSendReport(Boolean sendReport) {
        this.sendReport = sendReport;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getSendReport() {
        return sendReport;
    }

    public String getFrequency() {
        return frequency;
    }
}