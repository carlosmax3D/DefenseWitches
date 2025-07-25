package com.tapjoy.mraid.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.tapjoy.mraid.controller.MraidSensor;
import java.util.List;

public class Accel implements SensorEventListener {
    private static final int FORCE_THRESHOLD = 1000;
    private static final int SHAKE_COUNT = 2;
    private static final int SHAKE_DURATION = 2000;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int TIME_THRESHOLD = 100;
    private boolean bAccReady;
    private boolean bMagReady;
    private float[] mAccVals = {0.0f, 0.0f, 0.0f};
    private float[] mActualOrientation = {-1.0f, -1.0f, -1.0f};
    String mKey;
    private float[] mLastAccVals = {0.0f, 0.0f, 0.0f};
    private long mLastForce;
    private long mLastShake;
    private long mLastTime;
    private float[] mMagVals;
    MraidSensor mSensorController;
    private int mSensorDelay = 3;
    private int mShakeCount;
    int registeredHeadingListeners = 0;
    int registeredShakeListeners = 0;
    int registeredTiltListeners = 0;
    private SensorManager sensorManager;

    public Accel(Context context, MraidSensor mraidSensor) {
        this.mSensorController = mraidSensor;
        this.sensorManager = (SensorManager) context.getSystemService("sensor");
    }

    private void start() {
        List<Sensor> sensorList = this.sensorManager.getSensorList(1);
        if (sensorList.size() > 0) {
            this.sensorManager.registerListener(this, sensorList.get(0), this.mSensorDelay);
        }
    }

    private void startMag() {
        List<Sensor> sensorList = this.sensorManager.getSensorList(2);
        if (sensorList.size() > 0) {
            this.sensorManager.registerListener(this, sensorList.get(0), this.mSensorDelay);
            start();
        }
    }

    public float getHeading() {
        return this.mActualOrientation[0];
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case 1:
                this.mLastAccVals = this.mAccVals;
                this.mAccVals = (float[]) sensorEvent.values.clone();
                this.bAccReady = true;
                break;
            case 2:
                this.mMagVals = (float[]) sensorEvent.values.clone();
                this.bMagReady = true;
                break;
        }
        if (this.mMagVals != null && this.mAccVals != null && this.bAccReady && this.bMagReady) {
            this.bAccReady = false;
            this.bMagReady = false;
            float[] fArr = new float[9];
            SensorManager.getRotationMatrix(fArr, new float[9], this.mAccVals, this.mMagVals);
            this.mActualOrientation = new float[3];
            SensorManager.getOrientation(fArr, this.mActualOrientation);
            this.mSensorController.onHeadingChange(this.mActualOrientation[0]);
        }
        if (sensorEvent.sensor.getType() == 1) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.mLastForce > 500) {
                this.mShakeCount = 0;
            }
            if (currentTimeMillis - this.mLastTime > 100) {
                if ((Math.abs(((((this.mAccVals[0] + this.mAccVals[1]) + this.mAccVals[2]) - this.mLastAccVals[0]) - this.mLastAccVals[1]) - this.mLastAccVals[2]) / ((float) (currentTimeMillis - this.mLastTime))) * 10000.0f > 1000.0f) {
                    int i = this.mShakeCount + 1;
                    this.mShakeCount = i;
                    if (i >= 2 && currentTimeMillis - this.mLastShake > 2000) {
                        this.mLastShake = currentTimeMillis;
                        this.mShakeCount = 0;
                        this.mSensorController.onShake();
                    }
                    this.mLastForce = currentTimeMillis;
                }
                this.mLastTime = currentTimeMillis;
                this.mSensorController.onTilt(this.mAccVals[0], this.mAccVals[1], this.mAccVals[2]);
            }
        }
    }

    public void setSensorDelay(int i) {
        this.mSensorDelay = i;
        if (this.registeredTiltListeners > 0 || this.registeredShakeListeners > 0) {
            stop();
            start();
        }
    }

    public void startTrackingHeading() {
        if (this.registeredHeadingListeners == 0) {
            startMag();
        }
        this.registeredHeadingListeners++;
    }

    public void startTrackingShake() {
        if (this.registeredShakeListeners == 0) {
            setSensorDelay(1);
            start();
        }
        this.registeredShakeListeners++;
    }

    public void startTrackingTilt() {
        if (this.registeredTiltListeners == 0) {
            start();
        }
        this.registeredTiltListeners++;
    }

    public void stop() {
        if (this.registeredHeadingListeners == 0 && this.registeredShakeListeners == 0 && this.registeredTiltListeners == 0) {
            this.sensorManager.unregisterListener(this);
        }
    }

    public void stopAllListeners() {
        this.registeredTiltListeners = 0;
        this.registeredShakeListeners = 0;
        this.registeredHeadingListeners = 0;
        try {
            stop();
        } catch (Exception e) {
        }
    }

    public void stopTrackingHeading() {
        if (this.registeredHeadingListeners > 0) {
            int i = this.registeredHeadingListeners - 1;
            this.registeredHeadingListeners = i;
            if (i == 0) {
                stop();
            }
        }
    }

    public void stopTrackingShake() {
        if (this.registeredShakeListeners > 0) {
            int i = this.registeredShakeListeners - 1;
            this.registeredShakeListeners = i;
            if (i == 0) {
                setSensorDelay(3);
                stop();
            }
        }
    }

    public void stopTrackingTilt() {
        if (this.registeredTiltListeners > 0) {
            int i = this.registeredTiltListeners - 1;
            this.registeredTiltListeners = i;
            if (i == 0) {
                stop();
            }
        }
    }
}
