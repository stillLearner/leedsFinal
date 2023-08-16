package com.novo.zealot.Bean;



import com.novo.zealot.Utils.DateUtil;

import java.util.Date;
import java.util.UUID;

public class RunRecord {

    public final String TAG = "RunRecord";

    // Identifier
    private String uuid;
    // Date
    private String date;
    // Distance
    private double distance;
    // Duration
    private int duration;
    // Average speed
    private double avgSpeed;
    // Start time
    private Date startTime;
    // End time
    private Date endTime;


    public RunRecord(){
        uuid = UUID.randomUUID().toString();
        date = DateUtil.getFormattedDate();
        startTime = new Date();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
