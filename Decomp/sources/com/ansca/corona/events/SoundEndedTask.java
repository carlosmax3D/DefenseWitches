package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class SoundEndedTask implements CoronaRuntimeTask {
    private int fMediaId;

    public SoundEndedTask(int i) {
        this.fMediaId = i;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.soundEndCallback(coronaRuntime, this.fMediaId);
    }
}
