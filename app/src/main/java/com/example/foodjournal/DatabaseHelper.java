package com.example.foodjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class handles the SQLITE CRUD (create, update, delete) of our app
 * @version 1.0
 * @since 18-March-2021
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FoodJournal_CS246.db";
    public static final String TABLE_NAME = "food_entry";
    //public static final String COL1 = "ID";
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
     * @return true if insert action was successful. false if an error occurred.
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
}









