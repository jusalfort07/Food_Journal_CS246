package com.example.foodjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  FOOD_TYPE TEXT, FOOD_DESCRIPTION TEXT, FOOD_QUANTITY INTEGER, " +
                "  DATE_INTAKE TEXT, FOOD_COMMENTS TEXT)";
        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(upgradeTable);
        onCreate(db);
    }


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


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}









