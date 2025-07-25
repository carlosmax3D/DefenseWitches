package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class AccelerometerTask implements CoronaRuntimeTask {
    private double myDeltaTime;
    private double myX;
    private double myY;
    private double myZ;

    public AccelerometerTask(double d, double d2, double d3, double d4) {
        this.myX = d;
        this.myY = d2;
        this.myZ = d3;
        this.myDeltaTime = d4;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.accelerometerEvent(coronaRuntime, this.myX, this.myY, this.myZ, this.myDeltaTime);
    }
}
