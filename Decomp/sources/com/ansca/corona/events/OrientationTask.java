package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class OrientationTask implements CoronaRuntimeTask {
    int myNewOrientation;
    int myOldOrientation;

    public OrientationTask(int i, int i2) {
        this.myNewOrientation = i;
        this.myOldOrientation = i2;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.orientationChanged(coronaRuntime, this.myNewOrientation, this.myOldOrientation);
    }
}
