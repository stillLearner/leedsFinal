package com.novo.zealot.UI.Fragment;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novo.zealot.Bean.RunRecord;
import com.novo.zealot.R;
import com.novo.zealot.UI.Activity.CountDownActivity;
import com.novo.zealot.UI.Activity.mapActivity;
import com.novo.zealot.Utils.DateUtil;
import com.novo.zealot.Utils.GlobalUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.List;

public class RunFragment extends Fragment {
    public static final String TAG = "RunFragment";
    private TickerView distanceTodayView;
    private LinearLayout runButton;
    private Context appContext;
    private TextView distanceUnitTextView;

    public RunFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);

        initializeViews(view);
        setOnClickListener();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.appContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTodayDistance();
    }

    // Initialize all view components
    private void initializeViews(View view) {
        // Set TickerView for displaying today's distance
        distanceTodayView = view.findViewById(R.id.tv_todayDistance);
        distanceTodayView.setCharacterLists(TickerUtils.provideNumberList());
        distanceTodayView.setAnimationDuration(2000);

        runButton = view.findViewById(R.id.btn_run);
        distanceUnitTextView = view.findViewById(R.id.tv_unit);
    }

    // Set click listener for the run button
    private void setOnClickListener() {
        runButton.setOnClickListener(v -> {
            Intent intent = new Intent(appContext, mapActivity.class);
            appContext.startActivity(intent);
        });
    }

    // Update the display with today's total distance
    private void updateTodayDistance() {
        String todayDate = DateUtil.getFormattedDate();
        List<RunRecord> results = GlobalUtil.getInstance().databaseHelper.queryRecord(todayDate);

        // Calculate total distance today
        int todayTotalDistance = 0;
        for (RunRecord record : results) {
            todayTotalDistance += record.getDistance();
        }

        // If distance is less than 1000, display in meters; otherwise, display in kilometers
        if (todayTotalDistance < 1000) {
            distanceTodayView.setText(String.valueOf(todayTotalDistance));
        } else {
            double disKM = todayTotalDistance / 1000.0;
            distanceTodayView.setText(String.valueOf(disKM));
            distanceUnitTextView.setText("km");
        }
    }
}


