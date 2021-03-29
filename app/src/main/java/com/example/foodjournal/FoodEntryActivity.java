package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Class which handles the actual page for entering of records into this app.
 * @version 1.0 initial release
 * @since 18-March-2021
 */
public class FoodEntryActivity extends AppCompatActivity {

    View appBackground2;
    DatabaseHelper myDB;
    EditText foodDescTxt, foodQtyTxt, dtIntakeTxt, foodCommentsTxt;
    RadioGroup foodType;
    RadioButton solidType, liquidType;
    RadioButton selectedFoodType;
    Button btnSave, btnView, btnReturn2;
    Integer currentRecordID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_entry);

        // Set activity background
        appBackground2 = findViewById(R.id.appbg2);
        appBackground2.setBackgroundResource(R.drawable.bg_blue);

        // Set variables to corresponding fields, buttons on the layout
        myDB = new DatabaseHelper(this);

        foodDescTxt = (EditText) findViewById(R.id.txt_Description);
        foodQtyTxt = (EditText) findViewById(R.id.txt_Quantity);
        dtIntakeTxt = (EditText) findViewById(R.id.txt_EntryDate);
        foodCommentsTxt = (EditText) findViewById(R.id.txt_Comments);

        foodType = (RadioGroup) findViewById(R.id.idRadioGroup);
        //foodType.check(R.id.idSolid);
        solidType = (RadioButton) findViewById(R.id.idSolid);
        liquidType = (RadioButton) findViewById(R.id.idLiquid);

        btnSave = (Button) findViewById(R.id.btn_save);
        btnView = (Button) findViewById(R.id.btn_view);
        btnReturn2 = (Button) findViewById(R.id.btn_return2);

        foodDescTxt.addTextChangedListener(formTextWatcher);
        foodQtyTxt.addTextChangedListener(formTextWatcher);
        dtIntakeTxt.addTextChangedListener(formTextWatcher);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int Value = extras.getInt("currentID");

            if (Value > 0) {
                Cursor rs = myDB.getCurrentData(Value);
                currentRecordID = Value;
                rs.moveToFirst();

                String ftype = rs.getString(rs.getColumnIndex(DatabaseHelper.COL2));
                String desc = rs.getString(rs.getColumnIndex(DatabaseHelper.COL3));
                String qty = rs.getString(rs.getColumnIndex(DatabaseHelper.COL4));
                String dtIn = rs.getString(rs.getColumnIndex(DatabaseHelper.COL5));
                String cmnt = rs.getString(rs.getColumnIndex(DatabaseHelper.COL6));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Log.i("TEST", "This is rs type value ["+ftype+"]");

                if (ftype.equals("Solid")) {
                    solidType.setChecked(true);

                }
                if (ftype.equals("Liquid")) {
                    liquidType.setChecked(true);
                }
                foodDescTxt.setText((CharSequence)desc);
                foodQtyTxt.setText((CharSequence)qty);
                dtIntakeTxt.setText((CharSequence)dtIn);
                foodCommentsTxt.setText((CharSequence)cmnt);

                // Call the updateData method
                updateData(currentRecordID);
            }
        } else {

            // If current ID is blank then call the addData method instead
            addData();
        }

        // The viewData() method is only used for testing purposes.
        // It will show the current records using a custom message.
        // viewData();

        // Set the "View" button to show the FoodReportActivity
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodEntryActivity.this, FoodReportActivity.class));
            }
        });

        // Set "Return to Main" button
        btnReturn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // Date and Time Picker codes...
        dtIntakeTxt.setInputType(InputType.TYPE_NULL);
        dtIntakeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dtIntakeTxt);
            }
        });
    }


    /**
     * Method which add new entry into SQLite table
     * This method is triggered when the user clicked on the Save button.
     * This will use the FoodEntry class and will inform user of the outcome
     * using the Toast (message screen).
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    public void addData() {
        btnSave.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int selectedID = foodType.getCheckedRadioButtonId();
                    selectedFoodType = (RadioButton) findViewById(selectedID);

                    FoodEntry entry = new FoodEntry();
                    entry.setFoodType(selectedFoodType.getText().toString());
                    entry.setDescription(foodDescTxt.getText().toString());
                    entry.setQuantity(Integer.parseInt(foodQtyTxt.getText().toString()));
                    entry.setEntryDate(dtIntakeTxt.getText().toString());
                    entry.setComments(foodCommentsTxt.getText().toString());

                    boolean isInserted = myDB.insertData(entry);
                    if (isInserted) {
                        Toast.makeText(FoodEntryActivity.this,"Entry Saved!", Toast.LENGTH_LONG).show();

                        // Clearing the form after successfully saving the data
                        clearForm((ViewGroup) findViewById(R.id.form_FoodEntry));
                    } else {
                        Toast.makeText(FoodEntryActivity.this,"Entry NOT Saved!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        );
    }


    /**
     * Method is currently assigned on the VIEW button.
     * This will show records on the fly and is only used to ensure that records
     * are being inserted into the database. This will be replaced once the report
     * activity is functioning.
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    public void viewData() {
        btnView.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor res = myDB.getAllData();
                    if (res.getCount() == 0) {
                        showCustomMessage("Error", "Food Journal is empty!");
                        return;
                    } else {

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID: " + res.getString(0) + "\n");
                            buffer.append("TYPE: " + res.getString(1) + "\n");
                            buffer.append("DESC: " + res.getString(2) + "\n");
                            buffer.append("QTY: " + res.getString(3) + "\n");
                            buffer.append("DATE: " + res.getString(4) + "\n");
                            buffer.append("COMMENTS: " + res.getString(5) + "\n\n");
                        }
                        // Show all data
                        showCustomMessage("Food Journal", buffer.toString());
                    }
                }
            }
        );
    }


    /**
     * Method to create custom message window. Currently being used to show the records for testing
     * This can also be used for other messages needed within this app.
     * @param title string used as title of the custom window
     * @param msg string information that will be displayed inside the custom window
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    public void showCustomMessage(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }


    /**
     * Method which shows the system calendar and time selection windows for
     * entering the date and time when the food/liquid is taken by the user.
     * @param date_time_entry field where date and time is on the page
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    private void showDateTimeDialog(final EditText date_time_entry) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date_time_entry.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(FoodEntryActivity.this,timeSetListener,
                        calendar.get(HOUR_OF_DAY),
                        calendar.get(MINUTE),
                        false).show();
            }
        };

        new DatePickerDialog(this, dateSetListener,
                calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DAY_OF_MONTH)).show();
    }


    /**
     * Method used to reset all input fields on the Food Entry form of the app
     * @param group view group object or name to be cleared
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);

            foodType.clearCheck();
        }
    }


    /**
     * This will "Disable" the SAVE button until the user fill in the following fields:
     * 1. Description
     * 2. Quantity
     * 3. Date when the food was taken
     */
    private TextWatcher formTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String descInput = foodDescTxt.getText().toString().trim();
            String qtyInput = foodQtyTxt.getText().toString().trim();
            String dateInput = dtIntakeTxt.getText().toString().trim();

            btnSave.setEnabled(!descInput.isEmpty() && !qtyInput.isEmpty() && !dateInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
//        EditText foodDescTxt, foodQtyTxt, dtIntakeTxt, foodCommentsTxt;
    };


    /**
     * Method which add new entry into SQLite table
     * This method is triggered when the user clicked on the Save button.
     * This will use the FoodEntry class and will inform user of the outcome
     * using the Toast (message screen).
     * @version 1.0 initial release
     * @since 18-March-2021
     */
    public void updateData(int recID) {
        btnSave.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int selectedID = foodType.getCheckedRadioButtonId();
                    selectedFoodType = (RadioButton) findViewById(selectedID);

                    FoodEntry current = new FoodEntry();
                    current.setRecordID(recID);
                    current.setFoodType(selectedFoodType.getText().toString());
                    current.setDescription(foodDescTxt.getText().toString());
                    current.setQuantity(Integer.parseInt(foodQtyTxt.getText().toString()));
                    current.setEntryDate(dtIntakeTxt.getText().toString());
                    current.setComments(foodCommentsTxt.getText().toString());

                    boolean isUpdated = myDB.updateData(current);
                    if (isUpdated) {
                        Toast.makeText(FoodEntryActivity.this,"Entry Updated!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(FoodEntryActivity.this, FoodReportActivity.class));

                    } else {
                        Toast.makeText(FoodEntryActivity.this,"Entry NOT Updated!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        );
    }
}