package com.novo.zealot.UI.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.novo.zealot.R;

import java.util.Random;

public class AboutActivity extends Activity implements View.OnTouchListener {

    private int touchCount = 0;

    // Constants for swipe detection
    private static final int MIN_SWIPE_SPEED = 200;
    private static final int MIN_SWIPE_DISTANCE = 150;

    // Variables to track swipe coordinates
    private float swipeStartX;
    private float swipeMoveX;

    // Velocity tracker for swipe detection
    private VelocityTracker velocityTracker;

    private LinearLayout aboutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutLayout = findViewById(R.id.layout_about);
        aboutLayout.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        trackSwipeVelocity(event);

        touchCount++;
        if (touchCount == 5) {
            touchCount = 0;
            String color = "#" + getRandomColor();
            Toast.makeText(AboutActivity.this, color, Toast.LENGTH_SHORT).show();
            aboutLayout.setBackgroundColor(Color.parseColor(color));
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                swipeStartX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                swipeMoveX = event.getRawX();
                int distanceX = (int) (swipeMoveX - swipeStartX);
                int xSpeed = getSwipeVelocity();
                if (distanceX > MIN_SWIPE_DISTANCE && xSpeed > MIN_SWIPE_SPEED) {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return true;
    }

    private void trackSwipeVelocity(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker() {
        velocityTracker.recycle();
        velocityTracker = null;
    }

    private int getSwipeVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        return true;
    }

    public static String getRandomColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return R + G + B;
    }
}

