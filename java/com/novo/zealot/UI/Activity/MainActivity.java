package com.novo.zealot.UI.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.novo.zealot.R;
import com.novo.zealot.UI.Fragment.DietFragment;
import com.novo.zealot.UI.Fragment.LogFragment;
import com.novo.zealot.UI.Fragment.ProfileFragment;
import com.novo.zealot.UI.Fragment.RunFragment;
import com.novo.zealot.Utils.GlobalUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private FragmentManager fragmentManager;
    private LogFragment logFragment;
    private RunFragment runFragment;
    private DietFragment dietFragment;
    private ProfileFragment profileFragment;
    private String isFirstUse = null;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_log:
                    if (logFragment == null) {
                        logFragment = new LogFragment();
                    }
                    switchFragment(logFragment);
                    break;
                case R.id.navigation_diet:
                    if (dietFragment == null) {
                        dietFragment = new DietFragment();
                    }
                    switchFragment(dietFragment);
                    break;
                case R.id.navigation_run:
                    if (runFragment == null) {
                        runFragment = new RunFragment();
                    }
                    switchFragment(runFragment);
                    break;
                case R.id.navigation_profile:
                    if (profileFragment == null) {
                        profileFragment = new ProfileFragment();
                    }
                    switchFragment(profileFragment);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragment manager initialization
        fragmentManager = getSupportFragmentManager();

        // Bottom navigation bar setup
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_run);

        GlobalUtil.getInstance().setContext(getApplicationContext());
        GlobalUtil.getInstance().mainActivity = this;

        // Check if it's the first use
        try (InputStream inputStream = openFileInput("config");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            isFirstUse = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isFirstUse == null || !isFirstUse.equals("false")) {
            Intent intent = new Intent(MainActivity.this, firstUseActivity.class);
            startActivity(intent);
        }
    }

    // Method to switch between fragments
    private void switchFragment(Fragment fragment) {
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment f : fragments) {
            if (!f.equals(fragment) && !f.isHidden()) {
                fragmentManager.beginTransaction().hide(f).commit();
            }
        }
        if (fragment.isAdded()) {
            fragmentManager.beginTransaction().show(fragment).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fl_main, fragment).commit();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
}
