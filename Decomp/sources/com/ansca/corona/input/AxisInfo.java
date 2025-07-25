package com.ansca.corona.input;

public class AxisInfo {
    private AxisSettings fSettings;

    private AxisInfo(AxisSettings axisSettings) {
        if (axisSettings == null) {
            throw new NullPointerException();
        }
        this.fSettings = axisSettings.clone();
    }

    public static AxisInfo from(AxisSettings axisSettings) {
        if (axisSettings == null) {
            return null;
        }
        return new AxisInfo(axisSettings);
    }

    public boolean equals(AxisInfo axisInfo) {
        if (axisInfo == null) {
            return false;
        }
        return this.fSettings.equals(axisInfo.fSettings);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AxisInfo)) {
            return false;
        }
        return equals((AxisInfo) obj);
    }

    public float getAccuracy() {
        return this.fSettings.getAccuracy();
    }

    public float getMaxValue() {
        return this.fSettings.getMaxValue();
    }

    public float getMinValue() {
        return this.fSettings.getMinValue();
    }

    public AxisType getType() {
        return this.fSettings.getType();
    }

    public boolean isProvidingAbsoluteValues() {
        return this.fSettings.isProvidingAbsoluteValues();
    }
}
