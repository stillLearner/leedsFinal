package com.novo.zealot.DB;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.novo.zealot.Bean.DayRecord;
import com.novo.zealot.Bean.RunRecord;
import com.novo.zealot.Utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ZealotDBOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "FitnessRecord";
    private static final String TAG = "ZealotDBOpenHelper";

    private static final String CREATE_TABLE_QUERY = "create table FitnessRecord("
            + "id integer primary key autoincrement, "
            + "uuid text, "
            + "date date, "
            + "distance double, "
            + "duration int, "
            + "avgSpeed double, "
            + "startTime date, "
            + "endTime date);";

    public ZealotDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // Adding Records to the Database

    public boolean addRecord(RunRecord runRecord) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = prepareContentValues(runRecord);
        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1) {
            Log.d(TAG, runRecord.getUuid() + " failed to add");
            return false;
        } else {
            Log.d(TAG, runRecord.getUuid() + " added successfully");
            return true;
        }
    }

    private ContentValues prepareContentValues(RunRecord runRecord) {
        ContentValues values = new ContentValues();
        values.put("uuid", runRecord.getUuid());
        values.put("date", runRecord.getDate());
        values.put("distance", runRecord.getDistance());
        values.put("duration", runRecord.getDuration());
        values.put("avgSpeed", runRecord.getAvgSpeed());
        values.put("startTime", DateUtil.getFormattedTime(runRecord.getStartTime()));
        values.put("endTime", DateUtil.getFormattedTime(runRecord.getEndTime()));
        return values;
    }

    // Query Methods

    public List<RunRecord> queryRecord(String queryDate) {
        List<RunRecord> results = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = executeQuery(db, queryDate);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                RunRecord runRecord = extractRunRecordFromCursor(cursor);
                results.add(runRecord);
            }
        }

        return results;
    }

    public List<RunRecord> queryRecord() {
        return queryRecord(null);
    }

    private Cursor executeQuery(SQLiteDatabase db, String queryDate) {
        String sql = queryDate == null
                ? "select * from " + TABLE_NAME + " order by startTime desc"
                : "select * from " + TABLE_NAME + " where date = ? order by startTime desc";
        return queryDate == null ? db.rawQuery(sql, null) : db.rawQuery(sql, new String[]{queryDate});
    }

    private RunRecord extractRunRecordFromCursor(Cursor cursor) {
        RunRecord runRecord = new RunRecord();
        runRecord.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
        runRecord.setDate(cursor.getString(cursor.getColumnIndex("date")));
        runRecord.setDistance(cursor.getDouble(cursor.getColumnIndex("distance")));
        runRecord.setDuration(cursor.getInt(cursor.getColumnIndex("duration")));
        runRecord.setAvgSpeed(cursor.getDouble(cursor.getColumnIndex("avgSpeed")));
        runRecord.setStartTime(DateUtil.strToTime(cursor.getString(cursor.getColumnIndex("startTime"))));
        runRecord.setEndTime(DateUtil.strToTime(cursor.getString(cursor.getColumnIndex("endTime"))));
        return runRecord;
    }

    public double queryBestDistance() {
        return executeQueryForDoubleResult("select max(distance) from " + TABLE_NAME);
    }

    public double queryBestSpeed() {
        return executeQueryForDoubleResult("select max(avgSpeed) from " + TABLE_NAME);
    }

    public int queryBestTime() {
        return executeQueryForIntResult("select max(duration) from " + TABLE_NAME);
    }

    public int queryAllDistance() {
        return executeQueryForIntResult("select sum(distance) from " + TABLE_NAME);
    }

    private double executeQueryForDoubleResult(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        double result = 0;
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getDouble(0);
        }
        cursor.close();
        return result;
    }

    private int executeQueryForIntResult(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int result = 0;
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getInt(0);
        }
        cursor.close();
        return result;
    }

    // Delete all data
    public void deleteAllData() {
        String sql = "delete from " + TABLE_NAME + " where 1 = 1 ";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    // Returns the distance traveled per day for the month to be queried
    public List<DayRecord> queryDayRecord(Object year, Object month) {
        String sql = "select distinct date from " + TABLE_NAME
                + " where substr(date(date),1,7) = ? order by date asc";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{year + "-" + month});

        Log.d(TAG, "cursor size: " + cursor.getCount());

        List<DayRecord> result = new LinkedList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String tempDate = cursor.getString(0);
                List<RunRecord> dayList = queryRecord(tempDate);
                float day = extractDayFromDate(tempDate);
                float allDistance = getTotalDistance(dayList);

                DayRecord dayRecord = new DayRecord();
                dayRecord.setDay(day);
                dayRecord.setDistance(allDistance);
                result.add(dayRecord);
            }
        }
        cursor.close();
        return result;
    }

    private float extractDayFromDate(String dateStr) {
        Date date = DateUtil.strToDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (float) calendar.get(Calendar.DATE);
    }

    private float getTotalDistance(List<RunRecord> dayList) {
        float allDistance = 0;
        for (RunRecord record : dayList) {
            allDistance += record.getDistance();
        }
        return allDistance;
    }

    // Enquiry number of running times
    public int queryNumOfTimes() {
        return executeQueryForIntResult("select count(*) from " + TABLE_NAME);
    }

}




