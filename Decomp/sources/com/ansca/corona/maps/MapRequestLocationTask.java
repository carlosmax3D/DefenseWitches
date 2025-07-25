package com.ansca.corona.maps;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class MapRequestLocationTask implements CoronaRuntimeTask {
    private double fLatitude;
    private int fListener;
    private double fLongitude;
    private String fOriginalRequest;

    public MapRequestLocationTask(int i, double d, double d2, String str) {
        this.fLatitude = d;
        this.fLongitude = d2;
        this.fOriginalRequest = str;
        this.fListener = i;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.mapRequestLocationEvent(coronaRuntime, this.fListener, this.fLatitude, this.fLongitude, this.fOriginalRequest);
    }
}
