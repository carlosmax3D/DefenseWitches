package com.ansca.corona.input;

public class AxisSettings implements Cloneable {
    private float fAccuracy = 0.0f;
    private boolean fIsProvidingAbsoluteValues = true;
    private float fMaxValue = 1.0f;
    private float fMinValue = -1.0f;
    private AxisType fType = AxisType.UNKNOWN;

    public AxisSettings clone() {
        try {
            return (AxisSettings) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean equals(AxisSettings axisSettings) {
        return axisSettings != null && axisSettings.fType.equals(this.fType) && Math.abs(axisSettings.fMinValue - this.fMinValue) <= 0.001f && Math.abs(axisSettings.fMaxValue - this.fMaxValue) <= 0.001f && Math.abs(axisSettings.fAccuracy - this.fAccuracy) <= 0.001f && axisSettings.fIsProvidingAbsoluteValues == this.fIsProvidingAbsoluteValues;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AxisSettings)) {
            return false;
        }
        return equals((AxisSettings) obj);
    }

    public float getAccuracy() {
        return this.fAccuracy;
    }

    public float getMaxValue() {
        return this.fMaxValue;
    }

    public float getMinValue() {
        return this.fMinValue;
    }

    public AxisType getType() {
        return this.fType;
    }

    public boolean isProvidingAbsoluteValues() {
        return this.fIsProvidingAbsoluteValues;
    }

    public void setAccuracy(float f) {
        this.fAccuracy = f;
    }

    public void setIsProvidingAbsoluteValues(boolean z) {
        this.fIsProvidingAbsoluteValues = z;
    }

    public void setMaxValue(float f) {
        this.fMaxValue = f;
    }

    public void setMinValue(float f) {
        this.fMinValue = f;
    }

    public void setType(AxisType axisType) {
        if (axisType == null) {
            axisType = AxisType.UNKNOWN;
        }
        this.fType = axisType;
    }
}
