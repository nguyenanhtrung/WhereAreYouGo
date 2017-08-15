package com.example.android.whereareyougo.ui.data.database.entity;

import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by nguyenanhtrung on 14/08/2017.
 */

public class LocationUpdateSetting {
    private long interval;
    private long fastInterval;
    private int priority;
    private float displacementMeter;

    public LocationUpdateSetting(long interval, long fastInterval, int priority, float displacementMeter) {
        this.interval = interval;
        this.fastInterval = fastInterval;
        this.priority = priority;
        this.displacementMeter = displacementMeter;
    }

    public LocationUpdateSetting() {
        interval = MyKey.MIN_INTERVAL_UPDATE_LOCATION;
        fastInterval = MyKey.MIN_INTERVAL_UPDATE_LOCATION / 2;
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
        displacementMeter = 100;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
        this.fastInterval = interval / 2;
    }

    public long getFastInterval() {
        return fastInterval;
    }

    public void setFastInterval(long fastInterval) {
        this.fastInterval = fastInterval;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public float getDisplacementMeter() {
        return displacementMeter;
    }

    public void setDisplacementMeter(float displacementMeter) {
        this.displacementMeter = displacementMeter;
    }
}
