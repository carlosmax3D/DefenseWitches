package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class VideoEndedTask implements CoronaRuntimeTask {
    private int fMediaId;

    public VideoEndedTask(int i) {
        this.fMediaId = i;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.videoEndCallback(coronaRuntime, this.fMediaId);
    }
}
