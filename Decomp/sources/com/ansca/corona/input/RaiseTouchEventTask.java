package com.ansca.corona.input;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class RaiseTouchEventTask implements CoronaRuntimeTask {
    private TouchTracker fTouchTracker;

    public RaiseTouchEventTask(TouchTracker touchTracker) {
        this.fTouchTracker = touchTracker.clone();
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        if (this.fTouchTracker != null) {
            JavaToNativeShim.touchEvent(coronaRuntime, this.fTouchTracker);
        }
    }
}
