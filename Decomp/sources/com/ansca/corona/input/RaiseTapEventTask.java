package com.ansca.corona.input;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class RaiseTapEventTask implements CoronaRuntimeTask {
    private int fTapCount;
    private TouchPoint fTapPoint;

    public RaiseTapEventTask(TouchPoint touchPoint, int i) {
        if (touchPoint == null) {
            throw new NullPointerException();
        }
        this.fTapPoint = touchPoint;
        this.fTapCount = i;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.tapEvent(coronaRuntime, (int) this.fTapPoint.getX(), (int) this.fTapPoint.getY(), this.fTapCount);
    }
}
