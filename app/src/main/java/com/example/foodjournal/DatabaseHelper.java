package com.example.foodjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class handles the SQLITE CRUD (create, update, delete) of our app
 * @version 1.0
 * @since 18-March-2021
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FoodJournal_CS246.db";
    public static final String TABLE_NAME = "food_entry";
    public static final String COL1 = "ID";
    public static final String COL2 = "FOOD_TYPE";
    public static final String COL3 = "FOOD_DESCRIPTION";
    public static final String COL4 = "FOOD_QUANTITY";
    public static final String COL5 = "DATE_INTAKE";
    public static final String COL6 = "FOOD_COMMENTS";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    /**
     * Method for creating the actual table for our food journal repository
     * @param db SQLiteDatabase
     * @return n/a
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  FOOD_TYPE TEXT, FOOD_DESCRIPTION TEXT, FOOD_QUANTITY INTEGER, " +
                "  DATE_INTAKE TEXT, FOOD_COMMENTS TEXT)";
        db.execSQL(createTable);
    }


    /**
     * Method for upgrading the existing table for our food journal.
     * It will remove the previous table by using "drop" command then
     * will recreate the table by calling the onCreate method.
     * @param db SQLiteDatabase
     * @param oldVersion integer not being used right now
     * @param newVersion integer not being used right now
     * @return n/a
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(upgradeTable);
        onCreate(db);
    }


    /**
     * Method for inserting record into the table for food journal entries
     * @param entry FoodEntry object
     * @return boolean - true if insert action was successful. false if an error occurred.
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    public boolean insertData(FoodEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, entry.getFoodType());
        contentValues.put(COL3, entry.getDescription());
        contentValues.put(COL4, entry.getQuantity());
        contentValues.put(COL5, entry.getEntryDate());
        contentValues.put(COL6, entry.getComments());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        return true;
    }


    /**
     * Method to get all the records from the table where food entries are stored.
     * @return res Cursor containing records of the given table
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }


    /**
     * Method to get records from the table stored in a List of FoodEntry object.
     * @return returnList ArrayList containing records of the given table
     * @version 1.0 initial release
     * @since 26-March-2021
     */
    public ArrayList<FoodEntry> getFoodReport() {

        ArrayList<FoodEntry> returnList = new ArrayList<FoodEntry>();

        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            int recordID = cursor.getInt(0);
            String foodType = cursor.getString(1);
            String foodDesc = cursor.getString(2);
            int foodQty = cursor.getInt(3);
            String recordDate = cursor.getString(4);
            String foodComment = cursor.getString(5);

            FoodEntry fe = new FoodEntry();
            fe.setRecordID(recordID);
            fe.setFoodType(foodType);
            fe.setDescription(foodDesc);
            fe.setQuantity(foodQty);
            fe.setEntryDate(recordDate);
            fe.setComments(foodComment);
            returnList.add(fe);
        }
        // clean up by closing objects
        cursor.close();
        db.close();
        return returnList;
    }


    /**
     * Method to get records from the table stored in a List of FoodEntry object.
     * @return returnList ArrayList containing records of the given table
     * @version 1.0 initial release
     * @since 26-March-2021
     */
    public ArrayList<FoodEntry> getFilteredReport(String dtFrom, String dtTo) {

        ArrayList<FoodEntry> returnList = new ArrayList<FoodEntry>();

        String queryString = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL5 + " BETWEEN '" + dtFrom + "' AND '" + dtTo + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            int recordID = cursor.getInt(0);
            String foodType = cursor.getString(1);
            String foodDesc = cursor.getString(2);
            int foodQty = cursor.getInt(3);
            String recordDate = cursor.getString(4);
            String foodComment = cursor.getString(5);

            FoodEntry fe = new FoodEntry();
            fe.setRecordID(recordID);
            fe.setFoodType(foodType);
            fe.setDescription(foodDesc);
            fe.setQuantity(foodQty);
            fe.setEntryDate(recordDate);
            fe.setComments(foodComment);
            returnList.add(fe);
        }
        // clean up by closing objects
        cursor.close();
        db.close();
        return returnList;
    }


    /**
     * Method to DELETE record from the table .
     * @return boolean - true if delete action was successful. false if an error occurred.
     * @param entry is a FoodEntry object
     * @version 1.0 initial release
     * @since 26-March-2021
     */
    public boolean deleteData(FoodEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + entry.getRecordID();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }


    // Alternative way to delete using record ID in the parameter
    public void deleteDID(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper.COL1 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }


    /**
     * Method for inserting record into the table for food journal entries
     * @param entry FoodEntry object
     * @return boolean - true if insert action was successful. false if an error occurred.
     * @version 1.0 initial release
     * @since 29-March-2021
     */
    public boolean updateData(FoodEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, entry.getFoodType());
        contentValues.put(COL3, entry.getDescription());
        contentValues.put(COL4, entry.getQuantity());
        contentValues.put(COL5, entry.getEntryDate());
        contentValues.put(COL6, entry.getComments());

        String queryRecExist = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = " + entry.getRecordID();
        Cursor cursor = db.rawQuery(queryRecExist, null);
        if (cursor.getCount() == 1) {

            String arguments = String.valueOf(entry.getRecordID());
            long result = db.update(TABLE_NAME, contentValues,
                    "id=?", new String[]{arguments});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /**
     * Method to UPDATE record from the table .
     * @param fid integer for record ID
     * @param ftype string for food type.
     * @param desc string for food description
     * @param qty integer for food quantity
     * @param fdate string for date intake
     * @param cmnts string for comments
     * @return boolean - true if update action was successful. false if an error occurred.
     */
    public boolean updateDataParam(int fid, String ftype, String desc,
                              int qty, String fdate, String cmnts) {
        SQLiteDatabase db = this.getWritableDatabase();
        String arguments = String.valueOf(fid);
        String queryString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = ?";

        ContentValues contentValues =  new ContentValues();
        contentValues.put("FOOD_TYPE", ftype);
        contentValues.put("FOOD_DESCRIPTION", desc);
        contentValues.put("FOOD_QUANTITY", qty);
        contentValues.put("DATE_INTAKE", fdate);
        contentValues.put("FOOD_COMMENTS", cmnts);

        Cursor cursor = db.rawQuery(queryString, new String[]{arguments});
        if (cursor.getCount() > 0) {
            long result = db.update(TABLE_NAME, contentValues,
                    "id=?", new String[]{arguments});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /**
     * Method to get the current record for editing purposes
     * @param id integer for record ID
     * @return Cursor
     */
    public Cursor getCurrentData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + id + "";
        Cursor res =  db.rawQuery( queryString, null );
        return res;
    }
}





