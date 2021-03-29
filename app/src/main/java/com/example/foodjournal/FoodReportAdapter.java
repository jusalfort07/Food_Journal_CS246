package com.example.foodjournal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.util.ArrayList;


public class FoodReportAdapter extends ArrayAdapter<FoodEntry> {

    private int layout;
    private static final String TAG = "FoodReportAdapter";
    ArrayList<FoodEntry> dataList = new ArrayList<FoodEntry>();
    FoodReportAdapter adapter;

    public FoodReportAdapter(Context context, int resource, ArrayList<FoodEntry> data) {
        super(context, resource, data);
        dataList = data;
        layout = resource;
        this.adapter = this;
//      Log.i(TAG, "MS - MyListAdapter object created.");
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        FoodEntry fids = dataList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.desc = (TextView) convertView.findViewById(R.id.lst_desc);
            viewHolder.date = (TextView) convertView.findViewById(R.id.lst_date);
            viewHolder.info = (TextView) convertView.findViewById(R.id.lst_info);
//            viewHolder.fEdit1 = (Button) convertView.findViewById(R.id.lst_edit);
//            viewHolder.fDelete1 = (Button) convertView.findViewById(R.id.lst_delete);
            viewHolder.fEdit = (ImageButton) convertView.findViewById(R.id.lst_edit);
            viewHolder.fDelete = (ImageButton) convertView.findViewById(R.id.lst_delete);
            convertView.setTag(viewHolder);
//          Log.i(TAG, "MS - Null object set.");
        }

        mainViewHolder = (ViewHolder) convertView.getTag();
//      Log.i(TAG, "MS - convertView.getTag Success");

        mainViewHolder.fEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodEntryIntent = new Intent(getContext(), FoodEntryActivity.class);
                foodEntryIntent.putExtra("currentID", fids.getRecordID());
                foodEntryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(foodEntryIntent);
            }
        });

        mainViewHolder.fDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DatabaseHelper db = new DatabaseHelper(getContext());
                    db.deleteEntry(fids);
                    dataList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(getContext(),"Deleted Record ID: " + fids.getRecordID() +
                            " | Date: " + fids.getEntryDate() +
                            " | Desc: " + fids.getDescription() +
                            " | Qty: " + fids.getQuantity(), Toast.LENGTH_LONG).show();
            }
        });

        mainViewHolder.desc.setText(fids.getDescription());
        mainViewHolder.date.setText(fids.getEntryDate());
        mainViewHolder.info.setText("TYPE: " + fids.getFoodType() + " | QTY: "+fids.getQuantity());

        return convertView;
    }

    static class ViewHolder {
        TextView desc;
        TextView date;
        TextView info;
        ImageButton fEdit;
        ImageButton fDelete;
        Button fEdit1;
        Button fDelete1;
    }
}

