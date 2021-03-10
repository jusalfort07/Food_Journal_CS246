package com.example.foodjournal;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FoodEntry {
    // Member variables
    String foodType;
    String description;
    Integer quantity;
    String entryDate;
    String comments;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();

    // Default constructor
    public FoodEntry() {
        this.setFoodType("Solid");
        this.setDescription("");
        this.setQuantity(1);
        this.comments = "";
    }

    // Getters and setters
    public String getFoodType() {
        return foodType;
    }
    public String getDescription() {
        return description;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public String getEntryDate() {
        return entryDate;
    }
    public String getComments() {
        return comments;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
