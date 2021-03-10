package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
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

public class FoodEntryActivity extends AppCompatActivity {

    View appBackground2;
    DatabaseHelper myDB;
    EditText foodDescTxt, foodQtyTxt, dtIntakeTxt, foodCommentsTxt;
    RadioGroup foodType;

    RadioButton selectedFoodType;
    Button btnSave, btnView, btnReturn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_entry);

        // Set variables to corresponding fields, buttons on the layout
        myDB = new DatabaseHelper(this);

        foodDescTxt = (EditText) findViewById(R.id.txt_Description);
        foodQtyTxt = (EditText) findViewById(R.id.txt_Quantity);
        dtIntakeTxt = (EditText) findViewById(R.id.txt_EntryDate);
        foodCommentsTxt = (EditText) findViewById(R.id.txt_Comments);

        foodType = (RadioGroup) findViewById(R.id.idRadioGroup);
        foodType.check(R.id.idSolid);

        btnSave = (Button) findViewById(R.id.btn_save);
        btnView = (Button) findViewById(R.id.btn_view);
        btnReturn2 = (Button) findViewById(R.id.btn_return2);
        addData();
        viewData();

        // Set background
        appBackground2 = findViewById(R.id.appbg2);
        appBackground2.setBackgroundResource(R.drawable.bg_blue);

        // Set return button
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

    // Add new entry into SQLite
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
                        clearForm((ViewGroup) findViewById(R.id.form_FoodEntry));
                    } else {
                        Toast.makeText(FoodEntryActivity.this,"Entry NOT Saved!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        );
    }


    // View all records in SQLite
    // This is for testing only and will show records on the fly
    // To be replace with simple show "Food Report" page.
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


    // Custom message view to show the records for testing... can also be used for other messages
    public void showCustomMessage(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }


    // Date and Time picker method...
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


    // Reset entry form
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
}