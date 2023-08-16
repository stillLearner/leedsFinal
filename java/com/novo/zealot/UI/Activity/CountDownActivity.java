package com.novo.zealot.UI.Activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.novo.zealot.R;

public class CountDownActivity extends Activity {

    // Constants
    private static final int COUNTDOWN_SECONDS = 3;
    private static final int COUNTDOWN_INTERVAL = 1000;

    // Countdown TextView
    private TextView tv_countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        tv_countDown = findViewById(R.id.tv_countDown);
        tv_countDown.setText("Testing");
        // Start Countdown
        startCountDown();
    }

    /**
     * Start the countdown timer.
     */
    private void startCountDown() {
        new CountDownTimer(COUNTDOWN_SECONDS * COUNTDOWN_INTERVAL, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / COUNTDOWN_INTERVAL;
                Log.d("CountDown", "Seconds remaining: " + secondsRemaining);
                tv_countDown.setText(String.valueOf(secondsRemaining));
            }

            @Override
            public void onFinish() {
                tv_countDown.setText("GO!");
                startMapActivity();
            }
        }.start();
    }

    /**
     * Start the Map Activity.
     */
    private void startMapActivity() {
        Intent intent = new Intent(this, mapActivity.class); // Assume the correct class name is MapActivity
        startActivity(intent);
        finish();
    }
}
