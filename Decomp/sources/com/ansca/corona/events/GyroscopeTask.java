package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class GyroscopeTask implements CoronaRuntimeTask {
    private double myDeltaTime;
    private double myXRotation;
    private double myYRotation;
    private double myZRotation;

    public GyroscopeTask(double d, double d2, double d3, double d4) {
        this.myXRotation = d;
        this.myYRotation = d2;
        this.myZRotation = d3;
        this.myDeltaTime = d4;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.gyroscopeEvent(coronaRuntime, this.myXRotation, this.myYRotation, this.myZRotation, this.myDeltaTime);
    }
}
