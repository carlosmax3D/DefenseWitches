package com.ansca.corona.input;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;
import java.util.Iterator;

public class RaiseMultitouchEventTask implements CoronaRuntimeTask {
    private TouchTrackerCollection fTouchTrackers;

    public RaiseMultitouchEventTask(TouchTrackerCollection touchTrackerCollection) {
        this.fTouchTrackers = touchTrackerCollection.clone();
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.multitouchEventBegin(coronaRuntime);
        Iterator<TouchTracker> it = this.fTouchTrackers.iterator();
        while (it.hasNext()) {
            JavaToNativeShim.multitouchEventAdd(coronaRuntime, it.next());
        }
        JavaToNativeShim.multitouchEventEnd(coronaRuntime);
    }
}
