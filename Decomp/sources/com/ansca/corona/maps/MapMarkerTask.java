package com.ansca.corona.maps;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class MapMarkerTask implements CoronaRuntimeTask {
    MapMarker fMarker;

    public MapMarkerTask(MapMarker mapMarker) {
        this.fMarker = mapMarker;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        if (this.fMarker.getListener() != -1 && this.fMarker.getListener() != -2) {
            JavaToNativeShim.mapMarkerEvent(coronaRuntime, this.fMarker.getMarkerId(), this.fMarker.getListener(), this.fMarker.getLatitude(), this.fMarker.getLongitude());
        }
    }
}
