package com.example.foodjournal;


import android.util.Log;

public class UserSettings {

    // Class Member variables
    String name;
    String email;
    Boolean sendReport;
    String frequency;
    private static final String TAG = "CS246 UserSettingsClass";

    // Default constructor
    public UserSettings() {
        this.name = "";
        this.email = "";
        this.sendReport = true;
        this.frequency = "weekly";
    }

    // Getters
    public String getName() {
        Log.d(TAG, "Getting name...");
        return name;
    }
    public String getEmail() {
        Log.d(TAG, "Getting email...");
        return email;
    }
    public Boolean getSendReport() {
        Log.d(TAG, "Getting send report value...");
        return sendReport;
    }
    public String getFrequency() {
        Log.d(TAG, "Getting frequency...");
        return frequency;
    }

    // Setters
    public void setName(String name) {
        Log.d(TAG, "Setting name...");
        this.name = name;
    }
    public void setEmail(String email) {
        Log.d(TAG, "Setting email...");
        this.email = email;
    }
    public void setSendReport(Boolean sendReport) {
        Log.d(TAG, "Setting send report...");
        this.sendReport = sendReport;
    }
    public void setFrequency(String frequency) {
        Log.d(TAG, "Setting frequency...");
        this.frequency = frequency;
    }
}
