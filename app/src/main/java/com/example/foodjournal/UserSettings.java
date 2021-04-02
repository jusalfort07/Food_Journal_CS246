package com.example.foodjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UserSettings {

    // Class Member variables
    String name;
    String email;
    Boolean sendReport;
    String frequency;

    // Default constructor
    public UserSettings() {
        this.name = "";
        this.email = "";
        this.sendReport = true;
        this.frequency = "weekly";
    }

    // Getters
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

    // Setters
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
}
