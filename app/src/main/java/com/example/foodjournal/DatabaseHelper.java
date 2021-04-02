package com.example.foodjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * This class handles the SQLITE CRUD (create, read, update, delete) of our app
 * @version 1.0
 * @since 18-March-2021
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "CS246 DatabaseHelper";
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
     * @since 18-March-2021
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"Creating a table");
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
     * @since 18-March-2021
     */
    public boolean addEntry(FoodEntry entry) {
        Log.d(TAG, "Adding a entry at the table database...");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, entry.getFoodType());
        contentValues.put(COL3, entry.getDescription());
        contentValues.put(COL4, entry.getQuantity());
        contentValues.put(COL5, entry.getEntryDate());
        contentValues.put(COL6, entry.getComments());

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }


    /**
     * Method for inserting record into the table for food journal entries
     * @param entry FoodEntry object
     * @return boolean - true if insert action was successful. false if an error occurred.
     * @since 29-March-2021
     */
    public boolean updateEntry(FoodEntry entry) {
        Log.d(TAG, "Updating record from the table...");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, entry.getFoodType());
        contentValues.put(COL3, entry.getDescription());
        contentValues.put(COL4, entry.getQuantity());
        contentValues.put(COL5, entry.getEntryDate());
        contentValues.put(COL6, entry.getComments());

        String queryRecExist = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, COL1, entry.getRecordID());
        Cursor cursor = db.rawQuery(queryRecExist, null);
        if (cursor.getCount() == 1) {

            String arguments = String.valueOf(entry.getRecordID());
            long result = db.update(TABLE_NAME, contentValues,
                    "id=?", new String[]{arguments});
            return result != -1;
        } else {
            return false;
        }
    }


    /**
     * Method to DELETE record from the table .
     * @return boolean - true if delete action was successful. false if an error occurred.
     * @param entry is a FoodEntry object
     * @since 26-March-2021
     */
    public boolean deleteEntry(FoodEntry entry) {
        Log.d(TAG, "Deleting record from the table...");
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString;
        queryString = String.format("DELETE FROM %s WHERE %s = %d", TABLE_NAME, COL1, entry.getRecordID());

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }


    /**
     * Method to get all the records from the table where food entries are stored.
     * @return res Cursor containing records of the given table
     * @since 18-March-2021
     */
    public Cursor getAllData() {
        Log.d(TAG, "Getting all the record from the table database");
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(String.format("SELECT * FROM %s", TABLE_NAME), null);
    }


    /**
     * Method to get the current record for editing purposes
     * @param id integer for record ID
     * @return Cursor
     */
    public Cursor getCurrentData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, COL1, id);
        return db.rawQuery( queryString, null );
    }


    /**
     * Method to get records from the table stored in a List of FoodEntry object.
     * @return returnList ArrayList containing records of the given table
     * @since 26-March-2021
     */
    public ArrayList<FoodEntry> getAllEntries() {

        ArrayList<FoodEntry> returnList = new ArrayList<>();

        String queryString = String.format("SELECT * FROM %s", TABLE_NAME);
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
     * @since 26-March-2021
     */
    public ArrayList<FoodEntry> getFilteredEntry(String dtFrom, String dtTo) {

        ArrayList<FoodEntry> returnList = new ArrayList<>();

        String queryString = String.format("SELECT * FROM %s WHERE %s BETWEEN '%s' AND '%s'", TABLE_NAME, COL5, dtFrom, dtTo);
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
     * Alternative method to UPDATE record from the table using parameters.
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
        String queryString = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COL1);

        ContentValues contentValues =  new ContentValues();
        contentValues.put("FOOD_TYPE", ftype);
        contentValues.put("FOOD_DESCRIPTION", desc);
        contentValues.put("FOOD_QUANTITY", qty);
        contentValues.put("DATE_INTAKE", fdate);
        contentValues.put("FOOD_COMMENTS", cmnts);

        Cursor cursor = db.rawQuery(queryString, new String[]{arguments});
        if (cursor.getCount() > 0) {
            int result = db.update(TABLE_NAME, contentValues,
                    "id=?", new String[]{arguments});
            return result != -1;
        } else return false;
    }


    /**
     * Alternative method to delete entry using record ID in the parameter
     * @param id
     */
    public void deleteID(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper.COL1 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}





