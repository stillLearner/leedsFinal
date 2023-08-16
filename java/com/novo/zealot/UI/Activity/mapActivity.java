package com.novo.zealot.UI.Activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.novo.zealot.Bean.RunRecord;
import com.novo.zealot.R;
import com.novo.zealot.Utils.DataUtil;
import com.novo.zealot.Utils.GlobalUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class mapActivity extends Activity implements AMapLocationListener,
        LocationSource {

    private static final String TAG = "mapActivity";

    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 123;
    private MapView mapView = null;
    private AMap aMap;
    PopupMenu popup = null;

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private LocationSource.OnLocationChangedListener mListener = null;
    private boolean isFirstLoc = true;
    List<LatLng> path = new ArrayList<LatLng>();
    LatLng lastLatLng, nowLatLng;
    int distanceThisTime = 0;
    float avgSpeed = 0;
    Date startTime;
    boolean isNormalMap = true;

    int count = 2;

    TextView tv_mapSpeed, tv_mapDuration, tv_mapUnit;
    TickerView tv_mapDistance;

    FloatingActionButton btn_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Initialize Map View
        initializeMapView(savedInstanceState);

        // Initialize UI components
        initializeUIComponents();

        // Setup map controls
        setupMapControls();

        // Set the button to change the map type
        configureMapTypeToggleButton();
    }

    private void initializeMapView(Bundle savedInstanceState) {
        // Get the map control reference
        mapView = findViewById(R.id.bmapView);
        // Create the map when the activity executes onCreate
        mapView.onCreate(savedInstanceState);
    }

    private void initializeUIComponents() {
        // Initialize the start time
        startTime = new Date();

        // Initialize data display components
        tv_mapDuration = findViewById(R.id.tv_mapDuration);
        tv_mapSpeed = findViewById(R.id.tv_mapSpeed);
        tv_mapUnit = findViewById(R.id.tv_mapUnit);
        tv_mapDistance = findViewById(R.id.tv_mapDistance);
        tv_mapDistance.setCharacterLists(TickerUtils.provideNumberList());
        tv_mapDistance.setAnimationDuration(500);

        // Setup the stop button with a click listener
        btn_stop = findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleStopButtonClick();
            }
        });
    }

    private void setupMapControls() {
        // Initialize the map controller object
        init();

        // Basic map setup
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);

        // Setup location settings
        UiSettings settings = aMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);  // Show location button
        aMap.setLocationSource(this);  // Set location source
        aMap.setMyLocationEnabled(true);  // Trigger location and show the location layer

        // Customize location icon
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(myLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        aMap.setMyLocationStyle(myLocationStyle);

        // Start location services
        try {
            initLoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureMapTypeToggleButton() {
        ImageButton btn_changeMap = findViewById(R.id.btn_changeMap);
        btn_changeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMapType();
            }
        });
    }

    private void handleStopButtonClick() {
        Intent intent = new Intent(mapActivity.this, RunResultActivity.class);
        // Transfer data to the result page
        intent.putExtra("startTime", startTime);
        intent.putExtra("endTime", new Date());
        intent.putExtra("duration", (int) ((new Date().getTime() - startTime.getTime()) / 1000));

        // If distance is less than 10, don't save to the database
        if (distanceThisTime < 10) {
            Toast.makeText(getApplicationContext(), "Distance is too short, invalid!", Toast.LENGTH_SHORT).show();
            intent.putExtra("isValid", false);
        } else {
            // Save this run's data to the database
            saveRunDataToDatabase();
            intent.putExtra("isValid", true);
            intent.putExtra("distance", distanceThisTime);
            intent.putExtra("avgSpeed", avgSpeed);
        }

        startActivity(intent);
        finish();
    }

    private void saveRunDataToDatabase() {
        RunRecord runRecord = new RunRecord();
        runRecord.setEndTime(new Date());
        runRecord.setDistance(distanceThisTime);
        runRecord.setDuration((int) ((new Date().getTime() - startTime.getTime()) / 1000));
        runRecord.setAvgSpeed(avgSpeed);

        if (GlobalUtil.getInstance().databaseHelper.addRecord(runRecord)) {
            Toast.makeText(getApplicationContext(), "Recorded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Recorded failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleMapType() {
        if (isNormalMap) {
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);  // Set to satellite map
            isNormalMap = false;
        } else {
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);  // Set to normal map
        }
    }

    void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
    }

    //定位
    private void initLoc() throws Exception {
        // Request location permissions if needed
        requestLocationPermissions();

        // Update privacy settings
        AMapLocationClient.updatePrivacyShow(getApplicationContext(), true, true);
        AMapLocationClient.updatePrivacyAgree(getApplicationContext(), true);

        // Initialize location client
        initializeLocationClient();

        // Start the location updates
        mLocationClient.startLocation();
    }

    private void requestLocationPermissions() {
        // Check for ACCESS_FINE_LOCATION permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE); // Custom code for this request
        }

        // Check for ACCESS_COARSE_LOCATION permission
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE); // Custom code for this request
        }
    }

    private void initializeLocationClient() {
        // Create location client and set the callback listener
        try {
            mLocationClient = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mLocationClient.setLocationListener(this);

        // Initialize location parameters
        mLocationOption = new AMapLocationClientOption();

        // Set location mode to high accuracy
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        // Set the option to return address information
        mLocationOption.setNeedAddress(true);

        // Set the option to not only locate once (default is false)
        mLocationOption.setOnceLocation(false);

        // Set the option to force refresh WiFi (default is true)
        mLocationOption.setWifiActiveScan(true);

        // Set the option to not allow mock locations (default is false)
        mLocationOption.setMockEnable(false);

        // Set the interval for location updates (default is 2000ms)
        mLocationOption.setInterval(1000);

        // Set the location parameters to the location client
        mLocationClient.setLocationOption(mLocationOption);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //Avoiding the first two misalignments
                if (count-- < 0) {
                    Log.d(TAG, "location changed");

                    amapLocation.getLocationType();
                    amapLocation.getAccuracy();
                    amapLocation.getAddress();
                    amapLocation.getCountry();
                    amapLocation.getProvince();
                    amapLocation.getCity();
                    amapLocation.getDistrict();
                    amapLocation.getStreet();
                    amapLocation.getStreetNum();
                    amapLocation.getCityCode();
                    amapLocation.getAdCode();
                    amapLocation.getGpsAccuracyStatus();

                    float nowSpeed = amapLocation.getSpeed();

                    //Calculate the average speed, i.e. (current speed + current average speed)/2
                    if (isFirstLoc) {
                        avgSpeed = nowSpeed;
                    } else {
                        avgSpeed = (avgSpeed + nowSpeed) / 2;
                    }

                    if (!isFirstLoc) {
                        if ((int) AMapUtils.calculateLineDistance(nowLatLng, lastLatLng) < 100) {
                            lastLatLng = nowLatLng;
                        } else {
                            Toast.makeText(getApplicationContext()
                                    , "Instantaneous movement??Canceled"
                                    , Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    double latitude = amapLocation.getLatitude();
                    double longitude = amapLocation.getLongitude();

                    nowLatLng = new LatLng(latitude, longitude);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    String locTime = df.format(date);
                    path.add(nowLatLng);

                    Polyline polyline = aMap.addPolyline(
                            new PolylineOptions()
                                    .addAll(path)
                                    .width(10)
                                    .color(Color.argb(255, 255, 0, 0)));

//                Toast.makeText(getApplicationContext(), locTime, Toast.LENGTH_SHORT).show();

                    if (!isFirstLoc) {
                        int tempDistance = (int) AMapUtils.calculateLineDistance(nowLatLng, lastLatLng);

                        int duration = (int) (new Date().getTime() - startTime.getTime()) / 1000;
                        tv_mapDuration.setText(DataUtil.getFormattedTime(duration));

                        distanceThisTime += tempDistance;
                        if (distanceThisTime > 1000) {
                            double showDisKM = distanceThisTime / 1000.0;
                            tv_mapDistance.setText(showDisKM + "");
                            tv_mapUnit.setText("公里");
                        } else {
                            tv_mapDistance.setText(distanceThisTime + "");
                        }
                        if (nowSpeed == 0) {
                            tv_mapSpeed.setText("--.--");
                        } else {
                            tv_mapSpeed.setText(nowSpeed + "");
                        }
                    }

                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(nowLatLng));
                    mListener.onLocationChanged(amapLocation);
                    if (isFirstLoc) {
                        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                        aMap.moveCamera(CameraUpdateFactory.changeTilt(0));
                        isFirstLoc = false;
                    }
                }
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                Toast.makeText(getApplicationContext(), "Locate Error: " + amapLocation.getErrorCode(), Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        mapView.onDestroy();
        mLocationClient.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        mLocationClient.stopLocation();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "Press again to homepage", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}