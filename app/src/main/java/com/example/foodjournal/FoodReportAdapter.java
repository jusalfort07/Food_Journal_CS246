package com.example.foodjournal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.util.ArrayList;


public class FoodReportAdapter extends ArrayAdapter<FoodEntry> {

    private final int layout;
    private static final String TAG = "FoodReportAdapter";
    ArrayList<FoodEntry> dataList;
    FoodReportAdapter adapter;

    public FoodReportAdapter(Context context, int resource, ArrayList<FoodEntry> data) {
        super(context, resource, data);
        dataList = data;
        layout = resource;
        this.adapter = this;
        Log.i(TAG, "MS - MyListAdapter object created.");
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder;
        FoodEntry fids = dataList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.desc = (TextView) convertView.findViewById(R.id.lst_desc);
            viewHolder.date = (TextView) convertView.findViewById(R.id.lst_date);
            viewHolder.info = (TextView) convertView.findViewById(R.id.lst_info);
            viewHolder.fEdit = (ImageButton) convertView.findViewById(R.id.lst_edit);
            viewHolder.fDelete = (ImageButton) convertView.findViewById(R.id.lst_delete);
            convertView.setTag(viewHolder);
//          Log.i(TAG, "MS - Null object set.");
        }

        mainViewHolder = (ViewHolder) convertView.getTag();
//      Log.i(TAG, "MS - convertView.getTag Success");

        mainViewHolder.fEdit.setOnClickListener(v -> {
            Intent foodEntryIntent = new Intent(getContext(), FoodEntryActivity.class);
            foodEntryIntent.putExtra("currentID", fids.getRecordID());
            foodEntryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(foodEntryIntent);
        });

        mainViewHolder.fDelete.setOnClickListener(v -> {
                DatabaseHelper db = new DatabaseHelper(getContext());
                db.deleteEntry(fids);
                dataList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(getContext(),"Deleted Record ID: " + fids.getRecordID() +
                        " | Date: " + fids.getEntryDate() +
                        " | Desc: " + fids.getDescription() +
                        " | Qty: " + fids.getQuantity(), Toast.LENGTH_LONG).show();
        });

        String combined_info;
        // Version 1.0 beta
        //combined_info = "TYPE: " + fids.getFoodType() + " | QTY: "+fids.getQuantity();
        combined_info = "(Quantity: "+fids.getQuantity()+")";
        mainViewHolder.desc.setText(fids.getDescription());
        mainViewHolder.date.setText(fids.getEntryDate());
        mainViewHolder.info.setText(combined_info);

        return convertView;
    }

    static class ViewHolder {
        TextView desc;
        TextView date;
        TextView info;
        ImageButton fEdit;
        ImageButton fDelete;
    }
}

