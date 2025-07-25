package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class LocationTask implements CoronaRuntimeTask {
    private double myAccuracy;
    private double myAltitude;
    private double myBearing;
    private double myLatitude;
    private double myLongitude;
    private double mySpeed;
    private double myTime;

    public LocationTask(double d) {
        this.myLatitude = 0.0d;
        this.myLongitude = 0.0d;
        this.myAltitude = 0.0d;
        this.myAccuracy = 0.0d;
        this.mySpeed = 0.0d;
        this.myBearing = d;
        this.myTime = 0.0d;
    }

    public LocationTask(double d, double d2, double d3, double d4, double d5, double d6, double d7) {
        this.myLatitude = d;
        this.myLongitude = d2;
        this.myAltitude = d3;
        this.myAccuracy = d4;
        this.mySpeed = d5;
        this.myBearing = d6;
        this.myTime = d7;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.locationEvent(coronaRuntime, this.myLatitude, this.myLongitude, this.myAltitude, this.myAccuracy, this.mySpeed, this.myBearing, this.myTime);
    }
}
