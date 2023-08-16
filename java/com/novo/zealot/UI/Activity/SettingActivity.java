package com.novo.zealot.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hb.dialog.myDialog.MyAlertDialog;
import com.novo.zealot.R;
import com.novo.zealot.Utils.GlobalUtil;

public class SettingActivity extends Activity implements View.OnTouchListener {
    private LinearLayout deleteAllLayout;
    private LinearLayout aboutLayout;
    private static final int MIN_SWIPE_SPEED = 200;
    private static final int MIN_SWIPE_DISTANCE = 150;
    private float touchDownX;
    private float touchMoveX;
    private VelocityTracker velocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initializeViews();
        setListeners();
    }

    private void initializeViews() {
        RelativeLayout mainLayout = findViewById(R.id.layout_setting);
        mainLayout.setOnTouchListener(this);
        deleteAllLayout = findViewById(R.id.ll_deleteAll);
        aboutLayout = findViewById(R.id.ll_about);
    }

    private void setListeners() {
        deleteAllLayout.setOnClickListener(v -> showDeleteAllDialog());
        aboutLayout.setOnClickListener(v -> navigateToAbout());
    }

    private void showDeleteAllDialog() {
        final MyAlertDialog myAlertDialog = new MyAlertDialog(SettingActivity.this).builder()
                .setTitle("Confirm?")
                .setMsg("Confirm the deletion of all data, the operation is not recoverable！")
                .setPositiveButton("Confirm", v -> GlobalUtil.getInstance().databaseHelper.deleteAllData())
                .setNegativeButton("Cancel", v -> {
                });
        myAlertDialog.show();
    }

    private void navigateToAbout() {
        Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        handleTouchEvent(event);
        return true;
    }

    private void handleTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMoveX = event.getRawX();
                int distanceX = (int) (touchMoveX - touchDownX);
                int xSpeed = getScrollVelocity();
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
    }

    private void createVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker() {
        velocityTracker.recycle();
        velocityTracker = null;
    }

    private int getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        return Math.abs((int) velocityTracker.getXVelocity());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


