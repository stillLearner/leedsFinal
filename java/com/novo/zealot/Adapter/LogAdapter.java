package com.novo.zealot.Adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novo.zealot.Bean.RunRecord;
import com.novo.zealot.R;
import com.novo.zealot.Utils.DataUtil;
import com.novo.zealot.Utils.DateUtil;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.RecordHolder> {

    private final List<RunRecord> records;

    // Constructor to initialize the records list
    public LogAdapter(List<RunRecord> records) {
        this.records = records;
    }

    // ViewHolder class to hold individual record views
    static class RecordHolder extends RecyclerView.ViewHolder {
        TextView tv_recordDistance, tv_recordUnit, tv_recordStartTime, tv_recordAvgSpeed, tv_recordDuration;

        public RecordHolder(@NonNull View itemView) {
            super(itemView);
            initializeViews(itemView);
        }

        // Method to initialize views from log_item layout
        private void initializeViews(View itemView) {
            tv_recordDistance = itemView.findViewById(R.id.tv_recordDistance);
            tv_recordUnit = itemView.findViewById(R.id.tv_recordUnit);
            tv_recordStartTime = itemView.findViewById(R.id.tv_recordStartTime);
            tv_recordAvgSpeed = itemView.findViewById(R.id.tv_recordAvgSpeed);
            tv_recordDuration = itemView.findViewById(R.id.tv_recordDuration);
        }
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Inflate the log_item layout and return the RecordHolder
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.log_item, viewGroup, false);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder recordHolder, int i) {
        RunRecord record = records.get(i);
        displayRecordData(recordHolder, record);
    }

    // Method to display record data
    private void displayRecordData(RecordHolder recordHolder, RunRecord record) {
        // Display distance in kilometers or meters
        double distance = handleDistanceDisplay(recordHolder, record.getDistance());

        // Display start time, average speed, and duration
        recordHolder.tv_recordStartTime.setText(DateUtil.getFormattedTime(record.getStartTime()));
        recordHolder.tv_recordAvgSpeed.setText(String.format("%.2f", record.getAvgSpeed()));
        recordHolder.tv_recordDuration.setText(DataUtil.getFormattedTime(record.getDuration()));
    }

    // Method to handle distance display in either kilometers or meters
    private double handleDistanceDisplay(RecordHolder recordHolder, double distance) {
        if (distance > 1000) {
            displayInKilometers(recordHolder, distance / 1000.0);
        } else {
            displayInMeters(recordHolder, (int) distance);
        }
        return distance;
    }

    private void displayInKilometers(RecordHolder recordHolder, double distanceKM) {
        recordHolder.tv_recordDistance.setText(String.valueOf(distanceKM));
        recordHolder.tv_recordUnit.setText("km");
    }

    private void displayInMeters(RecordHolder recordHolder, int distance) {
        recordHolder.tv_recordDistance.setText(String.valueOf(distance));
        recordHolder.tv_recordUnit.setText("m");
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}


