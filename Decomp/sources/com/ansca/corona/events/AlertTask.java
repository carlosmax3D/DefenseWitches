package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class AlertTask implements CoronaRuntimeTask {
    private boolean mCancelled;
    private int myButtonIndex;

    public AlertTask(int i, boolean z) {
        this.myButtonIndex = i;
        this.mCancelled = z;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.alertCallback(coronaRuntime, this.myButtonIndex, this.mCancelled);
    }
}
