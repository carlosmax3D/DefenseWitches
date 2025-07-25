package com.ansca.corona.maps;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class MapRequestLocationFailedTask implements CoronaRuntimeTask {
    private String fErrorMessage;
    private int fListener;
    private String fOriginalRequest;

    public MapRequestLocationFailedTask(int i, String str, String str2) {
        this.fErrorMessage = str;
        this.fListener = i;
        this.fOriginalRequest = str2;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.mapRequestLocationFailed(coronaRuntime, this.fListener, this.fErrorMessage, this.fOriginalRequest);
    }
}
