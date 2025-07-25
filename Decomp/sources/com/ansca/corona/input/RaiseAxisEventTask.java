package com.ansca.corona.input;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class RaiseAxisEventTask implements CoronaRuntimeTask {
    private AxisDataEventInfo fDataEventInfo;
    private InputDeviceInterface fDevice;

    public RaiseAxisEventTask(InputDeviceInterface inputDeviceInterface, AxisDataEventInfo axisDataEventInfo) {
        if (inputDeviceInterface == null || axisDataEventInfo == null) {
            throw new NullPointerException();
        }
        this.fDevice = inputDeviceInterface;
        this.fDataEventInfo = axisDataEventInfo;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.axisEvent(coronaRuntime, this.fDevice, this.fDataEventInfo);
    }
}
