package com.ansca.corona.input;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class RaiseInputDeviceStatusChangedEventTask implements CoronaRuntimeTask {
    private InputDeviceInterface fDevice;
    private InputDeviceStatusEventInfo fEventInfo;

    public RaiseInputDeviceStatusChangedEventTask(InputDeviceInterface inputDeviceInterface, InputDeviceStatusEventInfo inputDeviceStatusEventInfo) {
        if (inputDeviceInterface == null || inputDeviceStatusEventInfo == null) {
            throw new NullPointerException();
        }
        this.fDevice = inputDeviceInterface;
        this.fEventInfo = inputDeviceStatusEventInfo;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.inputDeviceStatusEvent(coronaRuntime, this.fDevice, this.fEventInfo);
    }
}
