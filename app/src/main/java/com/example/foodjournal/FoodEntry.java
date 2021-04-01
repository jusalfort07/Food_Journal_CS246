package com.example.foodjournal;


import android.util.Log;

/**
 * Class which handles the entering of records from the fields into the database.
 * @version 1.0 initial release
 * @version 1.1 added recordID variable, setter and getter.
 * @since 18-March-2021
 */
public class FoodEntry {

    private static final String TAG = "CS246 FoodEntryClass";
    // Member variables
    String foodType;
    String description;
    Integer quantity;
    String entryDate;
    String comments;
    Integer recordID;

    /**
     * Default constructor without any parameters nor return
     * @since 18-March-2021
     */
    public FoodEntry() {
        this.setFoodType("Solid");
        this.setDescription("");
        this.setQuantity(1);
        this.comments = "";
    }

    // Getters and setters
    /**
     * Method for getting the value of a class variable
     * @return foodType value
     * @since 18-March-2021
     */
    public String getFoodType() {
        Log.d(TAG, "Getting food type...");
        return foodType;
    }
    /**
     * Method for getting the value of a class variable
     * @return description value
     * @since 18-March-2021
     */
    public String getDescription() {
        Log.d(TAG, "Getting description...");
        return description;
    }
    /**
     * Method for getting the value of a class variable
     * @return quantity value
     * @since 18-March-2021
     */
    public Integer getQuantity() {
        Log.d(TAG, "Getting quantity...");
        return quantity;
    }
    /**
     * Method for getting the value of the class variable
     * @return entryDate value
     * @since 18-March-2021
     */
    public String getEntryDate() {
        Log.d(TAG, "Getting entry date...");
        return entryDate;
    }
    /**
     * Method for getting the value of the class variable
     * @return comments value
     * @since 18-March-2021
     */
    public String getComments() {
        Log.d(TAG, "Getting comments...");
        return comments;
    }

    /**
     * Method for getting the value of the class variable
     * @return recordID integer value
     * @since 26-March-2021
     */
    public Integer getRecordID() {
        Log.d(TAG, "Getting record ID...");
        return recordID;
    }

    /**
     * Method for setting a class variable value
     * @param description string describing the food/liquid intake
     * @since 18-March-2021
     */
    public void setDescription(String description) {
        Log.d(TAG, "Setting description...");
        this.description = description;
    }
    /**
     * Method for setting a class variable value
     * @param quantity integer for the amount of food/liquid intake
     * @since 18-March-2021
     */
    public void setQuantity(Integer quantity) {
        Log.d(TAG, "Setting quantity...");
        this.quantity = quantity;
    }
    /**
     * Method for setting a class variable value
     * @param entryDate string date and time for when the food/liquid intake
     * @since 18-March-2021
     */
    public void setEntryDate(String entryDate) {
        Log.d(TAG, "Setting entry date...");
        this.entryDate = entryDate;
    }
    /**
     * Method for setting a class variable value
     * @param comments string for further information on the food/liquid intake
     * @since 18-March-2021
     */
    public void setComments(String comments) {
        Log.d(TAG, "Setting comments...");
        this.comments = comments;
    }
    /**
     * Method for setting a class variable value
     * @param foodType string whether the intake is solid (food) or liquid
     * @since 18-March-2021
     */
    public void setFoodType(String foodType) {
        Log.d(TAG, "Setting food type...");
        this.foodType = foodType;
    }

    /**
     * Method for setting a class variable value
     * @param recID integer whether the intake is solid (food) or liquid
     * @since 26-March-2021
     */
    public void setRecordID(Integer recID) {
        Log.d(TAG, "Setting record ID...");
        this.recordID = recID;
    }
}
