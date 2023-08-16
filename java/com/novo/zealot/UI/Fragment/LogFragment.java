package com.novo.zealot.UI.Fragment;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.novo.zealot.R;
import com.novo.zealot.UI.Activity.ChartActivity;
import com.novo.zealot.UI.Activity.LogActivity;
import com.novo.zealot.Utils.DataUtil;
import com.novo.zealot.Utils.GlobalUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class LogFragment extends Fragment {

    public static final String TAG = "LogFragment";

    private TextView tv_bestDistanceUnit;
    private TickerView tv_bestDistance, tv_bestSpeed, tv_bestTime;
    private RelativeLayout rl_log, rl_chart;

    public LogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        initializeViews(view);
        setOnClickListeners();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBestStats();
    }

    // Initialize all view components
    private void initializeViews(View view) {
        tv_bestDistanceUnit = view.findViewById(R.id.tv_bestDistanceUnit);

        tv_bestDistance = view.findViewById(R.id.tv_bestDistance);
        tv_bestDistance.setCharacterLists(TickerUtils.provideNumberList());
        tv_bestDistance.setAnimationDuration(1000);

        tv_bestSpeed = view.findViewById(R.id.tv_bestSpeed);
        tv_bestSpeed.setCharacterLists(TickerUtils.provideNumberList());
        tv_bestSpeed.setAnimationDuration(1000);

        tv_bestTime = view.findViewById(R.id.tv_bestTime);
        tv_bestTime.setCharacterLists(TickerUtils.provideNumberList());
        tv_bestTime.setAnimationDuration(1000);

        rl_log = view.findViewById(R.id.rl_log);
        rl_chart = view.findViewById(R.id.rl_chart);
    }

    // Set click listeners for buttons
    private void setOnClickListeners() {
        // Click listener to check the history
        rl_log.setOnClickListener(v -> startNewActivity(LogActivity.class));

        // Click listener to check the charts
        rl_chart.setOnClickListener(v -> startNewActivity(ChartActivity.class));
    }

    // Start new activity with a slide animation
    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // Update the display with the best distance, speed, and time
    private void updateBestStats() {
        // Acquisition of longest distance, fastest speed, longest time
        int bestDistance = (int) GlobalUtil.getInstance().databaseHelper.queryBestDistance();
        if (bestDistance < 1000) {
            tv_bestDistanceUnit.setText("m");
        } else {
            bestDistance = (bestDistance / 100) / 10; // Convert to KM
        }

        double bestSpeed = ((int) (GlobalUtil.getInstance().databaseHelper.queryBestSpeed() * 100)) / 100.0;
        String bestTime = DataUtil.getFormattedTime(GlobalUtil.getInstance().databaseHelper.queryBestTime());

        tv_bestDistance.setText(String.valueOf(bestDistance));
        tv_bestSpeed.setText(String.valueOf(bestSpeed));
        tv_bestTime.setText(bestTime);
    }
}

