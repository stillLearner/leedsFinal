package com.novo.zealot.UI.Activity;

import android.app.Activity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.novo.zealot.Adapter.LogAdapter;
import com.novo.zealot.Bean.RunRecord;
import com.novo.zealot.R;
import com.novo.zealot.Utils.GlobalUtil;

import java.util.List;

public class LogActivity extends Activity implements View.OnTouchListener {

    RecyclerView rv_log;
    LogAdapter logAdapter;

    LinearLayout ll_noData;

    LinearLayout ll_logActivity;

    // Minimum speed for a right swipe gesture
    private static final int XSPEED_MIN = 200;

    // Minimum distance for a right swipe gesture
    private static final int XDISTANCE_MIN = 150;

    // Record the horizontal coordinate when the finger is pressed down.
    private float xDown;

    // Record the horizontal coordinate when the finger is moving.
    private float xMove;

    // Used for calculating the speed of finger swipe.
    private VelocityTracker mVelocityTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        rv_log = findViewById(R.id.rv_log);

        ll_logActivity = findViewById(R.id.ll_logActivity);
        ll_logActivity.setOnTouchListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LogActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_log.setLayoutManager(linearLayoutManager);

        ll_noData = findViewById(R.id.ll_noData);

        DividerItemDecoration decoration = new DividerItemDecoration(LogActivity.this, DividerItemDecoration.VERTICAL);
        rv_log.addItemDecoration(decoration);

        List<RunRecord> records = GlobalUtil.getInstance().databaseHelper.queryRecord();

        if (records.size() != 0) {
            ll_noData.setVisibility(LinearLayout.INVISIBLE);

            logAdapter = new LogAdapter(records);
            rv_log.setAdapter(logAdapter);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                // Calculate the distance moved
                int distanceX = (int) (xMove - xDown);
                // Get the current swipe speed
                int xSpeed = getScrollVelocity();
                // If the moved distance is greater than our set minimum distance and the swipe speed is greater than our set speed,
                // return to the previous activity
                if (distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
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


    /**
     * Create a VelocityTracker object and add the touch sliding events of the content interface to it.
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * Recycle the VelocityTracker object.
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * Get the speed of the finger's sliding on the content interface.
     *
     * @return The sliding speed, in pixels per second.
     */

    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
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
}
