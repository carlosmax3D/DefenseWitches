package com.ansca.corona.maps;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class MapAddressRequestFailedTask implements CoronaRuntimeTask {
    private String fErrorMessage;

    public MapAddressRequestFailedTask(String str) {
        this.fErrorMessage = str;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.mapAddressRequestFailedEvent(coronaRuntime, this.fErrorMessage);
    }
}
