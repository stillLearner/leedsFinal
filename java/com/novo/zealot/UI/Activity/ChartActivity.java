package com.novo.zealot.UI.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.novo.zealot.Bean.DayRecord;
import com.novo.zealot.R;
import com.novo.zealot.Utils.GlobalUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;

public class ChartActivity extends Activity {

    private static final String TAG = "ChartActivity";

    private TextView timeToQueryTextView;

    private LineChart chart;
    private XAxis xAxis;
    private YAxis leftYAxis;
    private YAxis rightYAxis;
    private Legend legend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        chart = findViewById(R.id.lc_data);
        timeToQueryTextView = findViewById(R.id.tv_timeToQuery);

        String currentTime = getCurrentTime();
        timeToQueryTextView.setText(currentTime);

        initChart(chart);
        List<DayRecord> records = GlobalUtil.getInstance().databaseHelper.queryDayRecord(currentTime.substring(0, 4), currentTime.substring(5));
        showLineChart(records, "Records this week", Color.CYAN);

        timeToQueryTextView.setOnClickListener(v -> {
            DatePicker picker = new DatePicker(ChartActivity.this, DatePicker.YEAR_MONTH);
            picker.setRange(2000, 2019);
            picker.setSelectedItem(2019, 6);
            picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                @Override
                public void onDatePicked(String year, String month) {
                    timeToQueryTextView.setText(year + "/" + month);
                    List<DayRecord> list = GlobalUtil.getInstance().databaseHelper.queryDayRecord(year, month);
                    showLineChart(list, "data", Color.CYAN);
                }
            });

            picker.show();
        });
    }

    private String getCurrentTime() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        return nowYear + "/" + (nowMonth < 10 ? "0" + nowMonth : nowMonth);
    }

    private void initChart(LineChart lineChart) {
        // Chart general settings
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.setDragEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.animateY(2000);
        lineChart.animateX(1000);

        // X and Y axis settings
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYAxis = lineChart.getAxisRight();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(1f);
        xAxis.setGranularity(1f);
        leftYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMinimum(0f);

        // Legend settings
        legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
    }

    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    public void showLineChart(List<DayRecord> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (DayRecord data : dataList) {
            Entry entry = new Entry(data.getDay(), data.getDistance());
            entries.add(entry);
        }
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet);
        chart.setData(lineData);
        chart.invalidate();
    }
}
