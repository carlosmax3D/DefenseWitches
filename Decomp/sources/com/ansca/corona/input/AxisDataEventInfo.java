package com.ansca.corona.input;

public class AxisDataEventInfo {
    private int fAxisIndex;
    private AxisDataPoint fDataPoint;
    private InputDeviceInfo fDeviceInfo;

    public AxisDataEventInfo(InputDeviceInfo inputDeviceInfo, int i, AxisDataPoint axisDataPoint) {
        if (inputDeviceInfo == null || axisDataPoint == null) {
            throw new NullPointerException();
        }
        this.fDeviceInfo = inputDeviceInfo;
        this.fAxisIndex = i;
        this.fDataPoint = axisDataPoint;
    }

    public int getAxisIndex() {
        return this.fAxisIndex;
    }

    public AxisDataPoint getDataPoint() {
        return this.fDataPoint;
    }

    public InputDeviceInfo getDeviceInfo() {
        return this.fDeviceInfo;
    }
}
