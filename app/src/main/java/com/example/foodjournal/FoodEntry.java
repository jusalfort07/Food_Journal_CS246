package com.example.foodjournal;


import android.util.Log;

/**
 * Class which handles the entering of records from the fields into the database.
 * @version 1.0 initial release
 * @version 1.1 added recordID variable, setter and getter.
 * @since 18-March-2021
 */
public class FoodEntry {

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
        return foodType;
    }
    /**
     * Method for getting the value of a class variable
     * @return description value
     * @since 18-March-2021
     */
    public String getDescription() {
        return description;
    }
    /**
     * Method for getting the value of a class variable
     * @return quantity value
     * @since 18-March-2021
     */
    public Integer getQuantity() {
        return quantity;
    }
    /**
     * Method for getting the value of the class variable
     * @return entryDate value
     * @since 18-March-2021
     */
    public String getEntryDate() {
        return entryDate;
    }
    /**
     * Method for getting the value of the class variable
     * @return comments value
     * @since 18-March-2021
     */
    public String getComments() {
        return comments;
    }

    /**
     * Method for getting the value of the class variable
     * @return recordID integer value
     * @since 26-March-2021
     */
    public Integer getRecordID() {
        return recordID;
    }

    /**
     * Method for setting a class variable value
     * @param description string describing the food/liquid intake
     * @since 18-March-2021
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Method for setting a class variable value
     * @param quantity integer for the amount of food/liquid intake
     * @since 18-March-2021
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    /**
     * Method for setting a class variable value
     * @param entryDate string date and time for when the food/liquid intake
     * @since 18-March-2021
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
    /**
     * Method for setting a class variable value
     * @param comments string for further information on the food/liquid intake
     * @since 18-March-2021
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    /**
     * Method for setting a class variable value
     * @param foodType string whether the intake is solid (food) or liquid
     * @since 18-March-2021
     */
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    /**
     * Method for setting a class variable value
     * @param recID integer whether the intake is solid (food) or liquid
     * @since 26-March-2021
     */
    public void setRecordID(Integer recID) {
        this.recordID = recID;
    }
}
