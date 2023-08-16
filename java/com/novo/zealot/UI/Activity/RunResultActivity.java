package com.novo.zealot.UI.Activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.novo.zealot.R;
import com.novo.zealot.Utils.DataUtil;
import com.novo.zealot.Utils.DateUtil;

import java.util.Date;

public class RunResultActivity extends Activity {

    private static final String TAG = "RunResultActivity";
    private Button finishResultButton;
    private TextView startTimeTextView, endTimeTextView, distanceTextView, speedTextView, durationTextView;

    private Date startTime, endTime;
    private int runDuration = 0, runDistance = 0;
    private float averageSpeed = 0;
    private double roundedCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_result);

        initializeViews();
        extractDataFromIntent();
        calculateCalories();
        displayRunData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void initializeViews() {
        finishResultButton = findViewById(R.id.btn_resultFinish);
        finishResultButton.setOnClickListener(v -> {
            Intent intent = new Intent(RunResultActivity.this, CustomActivity.class);
            intent.putExtra("caloriesRounded", roundedCalories);
            startActivity(intent);
        });

        startTimeTextView = findViewById(R.id.tv_resultStartTime);
        endTimeTextView = findViewById(R.id.tv_resultEndTime);
        distanceTextView = findViewById(R.id.tv_resultDistance);
        durationTextView = findViewById(R.id.tv_resultDuration);
        speedTextView = findViewById(R.id.tv_resultSpeed);
    }
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        startTime = (Date) bundle.get("startTime");
        endTime = (Date) bundle.get("endTime");
        runDuration = (int) bundle.get("duration");

        boolean isValid = (boolean) bundle.get("isValid");
        if (isValid) {
            Log.d(TAG, "data is valid");
            runDistance = (int) bundle.get("distance");
            averageSpeed = (float) bundle.get("avgSpeed");
        }

        Log.d(TAG, startTime + " " + endTime + " " + runDistance + " " + runDuration + " " + averageSpeed);
    }

    private void displayRunData() {
        startTimeTextView.setText(DateUtil.getFormattedTime(startTime));
        endTimeTextView.setText(DateUtil.getFormattedTime(endTime));
        distanceTextView.setText("" + runDistance);
        speedTextView.setText("" + (((int) (averageSpeed * 100)) / 100.0));
        durationTextView.setText(DataUtil.getFormattedTime(runDuration));
    }

    private void calculateCalories() {
        double metValue = 6.0; // Assume the MET value for running (jogging)

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String weight = sharedPreferences.getString("weight", "");
        double weightInKg = Double.valueOf(weight) / 1000.0;

        double calories = metValue * 3.5 * weightInKg * runDuration / 3600.0;
        roundedCalories = Math.round(calories * 100.0) / 100.0;
    }
}

