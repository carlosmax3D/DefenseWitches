package com.ansca.corona.maps;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class MapTappedTask implements CoronaRuntimeTask {
    int fId;
    double fLatitude;
    double fLongitude;

    public MapTappedTask(int i, double d, double d2) {
        this.fId = i;
        this.fLongitude = d2;
        this.fLatitude = d;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.mapTappedEvent(coronaRuntime, this.fId, this.fLatitude, this.fLongitude);
    }
}
