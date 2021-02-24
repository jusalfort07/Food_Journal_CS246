package com.example.foodjournal;

public class UserSettings {

    String name;
    String email;
    Boolean sendReport;
    String frequency;

    public UserSettings(String name, String email, Boolean sendR, String freq) {
            this.name = name;
            this.email = email;
            this.sendReport = sendR;
            this.frequency = freq;
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
