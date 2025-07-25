package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class ResizeTask implements CoronaRuntimeTask {
    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.resizeEvent(coronaRuntime);
    }
}
