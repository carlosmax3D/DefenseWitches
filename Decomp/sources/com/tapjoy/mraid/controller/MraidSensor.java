package com.tapjoy.mraid.controller;

import android.content.Context;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.listener.Accel;
import com.tapjoy.mraid.view.MraidView;

public class MraidSensor extends Abstract {
    private static final String TAG = "MRAID Sensor";
    final int INTERVAL = 1000;
    private Accel mAccel;
    private float mLastX = 0.0f;
    private float mLastY = 0.0f;
    private float mLastZ = 0.0f;

    public MraidSensor(MraidView mraidView, Context context) {
        super(mraidView, context);
        this.mAccel = new Accel(context, this);
    }

    public float getHeading() {
        TapjoyLog.d(TAG, "getHeading: " + this.mAccel.getHeading());
        return this.mAccel.getHeading();
    }

    public String getTilt() {
        String str = "{ x : \"" + this.mLastX + "\", y : \"" + this.mLastY + "\", z : \"" + this.mLastZ + "\"}";
        TapjoyLog.d(TAG, "getTilt: " + str);
        return str;
    }

    public void onHeadingChange(float f) {
        String str = "window.mraidview.fireChangeEvent({ heading: " + ((int) (((double) f) * 57.29577951308232d)) + "});";
        TapjoyLog.d(TAG, str);
        this.mMraidView.injectMraidJavaScript(str);
    }

    public void onShake() {
        this.mMraidView.injectMraidJavaScript("mraid.gotShake()");
    }

    public void onTilt(float f, float f2, float f3) {
        this.mLastX = f;
        this.mLastY = f2;
        this.mLastZ = f3;
        String str = "window.mraidview.fireChangeEvent({ tilt: " + getTilt() + "})";
        TapjoyLog.d(TAG, str);
        this.mMraidView.injectMraidJavaScript(str);
    }

    public void startHeadingListener() {
        this.mAccel.startTrackingHeading();
    }

    public void startShakeListener() {
        this.mAccel.startTrackingShake();
    }

    public void startTiltListener() {
        this.mAccel.startTrackingTilt();
    }

    /* access modifiers changed from: package-private */
    public void stop() {
    }

    public void stopAllListeners() {
        this.mAccel.stopAllListeners();
    }

    public void stopHeadingListener() {
        this.mAccel.stopTrackingHeading();
    }

    public void stopShakeListener() {
        this.mAccel.stopTrackingShake();
    }

    public void stopTiltListener() {
        this.mAccel.stopTrackingTilt();
    }
}
