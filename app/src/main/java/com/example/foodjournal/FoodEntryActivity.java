package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RadioButton solidType, liquidType, selectedFoodType;
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
        foodType.check(R.id.idSolid);
        solidType = (RadioButton) findViewById(R.id.idSolid);
        liquidType = (RadioButton) findViewById(R.id.idLiquid);

        btnSave = (Button) findViewById(R.id.btn_save);
        btnView = (Button) findViewById(R.id.btn_view);
        btnReturn2 = (Button) findViewById(R.id.btn_return2);
        // Disabled by default to prevent saving of record when no user have put entries
        btnSave.setEnabled(false);
        btnSave.setBackground(getDrawable(R.drawable.btn_ltgray));

        foodDescTxt.addTextChangedListener(formTextWatcher);
        foodQtyTxt.addTextChangedListener(formTextWatcher);
        dtIntakeTxt.addTextChangedListener(formTextWatcher);

        // Date and Time Picker codes...
        dtIntakeTxt.setInputType(InputType.TYPE_NULL);
        dtIntakeTxt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDateTimeDialog(dtIntakeTxt);
                dtIntakeTxt.setOnClickListener(v1 -> showDateTimeDialog(dtIntakeTxt));
            }
        });

        // Set the "View" button to show the FoodReportActivity
        btnView.setOnClickListener(v -> startActivity(new Intent(FoodEntryActivity.this, FoodReportActivity.class)));

        // Set "Return to Main" button
        btnReturn2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

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
        String viewAlternative;
        viewAlternative = "NO";
        if (viewAlternative.equals("YES")) {
            viewData();
        }
    }


    /**
     * Method which add new entry into SQLite table
     * This method is triggered when the user clicked on the Save button.
     * This will use the FoodEntry class and will inform user of the outcome
     * using the Toast (message screen).
     * @since 18-March-2021
     */
    public void addData() {
        btnSave.setOnClickListener(
                (View.OnClickListener) v -> {
                    int selectedID = foodType.getCheckedRadioButtonId();
                    selectedFoodType = (RadioButton) findViewById(selectedID);

                    FoodEntry entry = new FoodEntry();
                    entry.setFoodType(selectedFoodType.getText().toString());
                    entry.setDescription(foodDescTxt.getText().toString());
                    entry.setQuantity(Integer.parseInt(foodQtyTxt.getText().toString()));
                    entry.setEntryDate(dtIntakeTxt.getText().toString());
                    entry.setComments(foodCommentsTxt.getText().toString());

                    boolean isInserted = myDB.addEntry(entry);
                    if (isInserted) {
                        Toast.makeText(FoodEntryActivity.this,
                                "Entry Saved!",
                                Toast.LENGTH_SHORT).show();

                        // Clearing the form after successfully saving the data
                        clearForm((ViewGroup) findViewById(R.id.form_FoodEntry));
                        foodType.check(R.id.idSolid);
                    } else {
                        Toast.makeText(FoodEntryActivity.this,
                                "Entry NOT Saved!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    /**
     * Method is currently assigned on the VIEW button.
     * This will show records on the fly and is only used to ensure that records
     * are being inserted into the database. This will be replaced once the report
     * activity is functioning.
     * @since 18-March-2021
     */
    public void viewData() {
        btnView.setOnClickListener(
                v -> {
                    Cursor res = myDB.getAllData();
                    if (res.getCount() == 0) {
                        showCustomMessage("Error", "Food Journal is empty!");
                    } else {
                        StringBuilder buffer = new StringBuilder();
                        while (res.moveToNext()) {
                            buffer.append("ID: ").append(res.getString(0)).append("\n");
                            buffer.append("TYPE: ").append(res.getString(1)).append("\n");
                            buffer.append("DESC: ").append(res.getString(2)).append("\n");
                            buffer.append("QTY: ").append(res.getString(3)).append("\n");
                            buffer.append("DATE: ").append(res.getString(4)).append("\n");
                            buffer.append("COMMENTS: ").append(res.getString(5)).append("\n\n");
                        }
                        // Show all data
                        showCustomMessage("Food Journal", buffer.toString());
                    }
                }
        );
    }


    /**
     * Method to create custom message window. Currently being used to show the records for testing
     * This can also be used for other messages needed within this app.
     * @param title string used as title of the custom window
     * @param msg string information that will be displayed inside the custom window
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
     * @since 18-March-2021
     */
    private void showDateTimeDialog(final EditText date_time_entry) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date_time_entry.setText(simpleDateFormat.format(calendar.getTime()));
            };
            new TimePickerDialog(FoodEntryActivity.this,timeSetListener,
                    calendar.get(HOUR_OF_DAY),
                    calendar.get(MINUTE),
                    false).show();
        };

        new DatePickerDialog(this, dateSetListener,
                calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DAY_OF_MONTH)).show();
    }


    /**
     * Method used to reset all input fields on the Food Entry form of the app
     * @param group view group object or name to be cleared
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
        }
        foodType.clearCheck();
        btnSave.setEnabled(false);
        btnSave.setBackground(getDrawable(R.drawable.btn_ltgray));
    }


    /**
     * This will "Disable" the SAVE button until the user fill in the following fields:
     * 1. Description
     * 2. Quantity
     * 3. Date when the food was taken
     */
    private final TextWatcher formTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String descInput = foodDescTxt.getText().toString().trim();
            String qtyInput = foodQtyTxt.getText().toString().trim();
            String dateInput = dtIntakeTxt.getText().toString().trim();

            btnSave.setEnabled(!descInput.isEmpty() && !qtyInput.isEmpty() && !dateInput.isEmpty());
            btnSave.setBackground(getDrawable(R.drawable.btn_ltblue));
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    /**
     * Method which add new entry into SQLite table
     * This method is triggered when the user clicked on the Save button.
     * This will use the FoodEntry class and will inform user of the outcome
     * using the Toast (message screen).
     * @since 18-March-2021
     */
    public void updateData(int recID) {
        btnSave.setOnClickListener(
                (View.OnClickListener) v -> {

                    int selectedID = foodType.getCheckedRadioButtonId();
                    selectedFoodType = (RadioButton) findViewById(selectedID);

                    FoodEntry current = new FoodEntry();
                    current.setRecordID(recID);
                    current.setFoodType(selectedFoodType.getText().toString());
                    current.setDescription(foodDescTxt.getText().toString());
                    current.setQuantity(Integer.parseInt(foodQtyTxt.getText().toString()));
                    current.setEntryDate(dtIntakeTxt.getText().toString());
                    current.setComments(foodCommentsTxt.getText().toString());

                    boolean isUpdated = myDB.updateEntry(current);
                    if (isUpdated) {
                        Toast.makeText(FoodEntryActivity.this,"Entry Updated!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(FoodEntryActivity.this, FoodReportActivity.class));
                    } else {
                        Toast.makeText(FoodEntryActivity.this,"Entry NOT Updated!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}