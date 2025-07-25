package com.ansca.corona;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.ansca.corona.MessageBasedTimer;
import com.ansca.corona.events.AccelerometerTask;
import com.ansca.corona.events.GyroscopeTask;
import com.ansca.corona.events.LocationTask;

class CoronaSensorManager {
    /* access modifiers changed from: private */
    public AccelerometerMonitor myAccelerometerMonitor;
    private boolean[] myActiveSensors = new boolean[6];
    /* access modifiers changed from: private */
    public CoronaRuntime myCoronaRuntime;
    /* access modifiers changed from: private */
    public GyroscopeMonitor myGyroscopeMonitor;
    /* access modifiers changed from: private */
    public float myLastHeading = -1.0f;
    /* access modifiers changed from: private */
    public CoronaLocationListener myLocationListener;
    /* access modifiers changed from: private */
    public LocationManager myLocationManager;
    /* access modifiers changed from: private */
    public double myLocationThreshold = 0.0d;
    /* access modifiers changed from: private */
    public Sensor myOrientationSensor;
    /* access modifiers changed from: private */
    public SensorEventListener myOrientationSensorListener;
    /* access modifiers changed from: private */
    public SensorManager mySensorManager;

    private class AccelerometerMonitor extends SensorMonitor {
        /* access modifiers changed from: private */
        public boolean fHasReceivedMeasurement = false;
        /* access modifiers changed from: private */
        public boolean fHasReceivedSample = false;
        /* access modifiers changed from: private */
        public boolean fHasSkippedFirstMeasurement = false;
        /* access modifiers changed from: private */
        public long fLastSampleTimestamp = 0;
        /* access modifiers changed from: private */
        public SensorMeasurement fLastSensorMeasurement;

        private class SensorHandler implements SensorEventListener {
            private SensorHandler() {
            }

            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null) {
                    if (!AccelerometerMonitor.this.fHasSkippedFirstMeasurement) {
                        boolean unused = AccelerometerMonitor.this.fHasSkippedFirstMeasurement = true;
                        return;
                    }
                    AccelerometerMonitor.this.fLastSensorMeasurement.copyFrom(sensorEvent);
                    boolean unused2 = AccelerometerMonitor.this.fHasReceivedMeasurement = true;
                }
            }
        }

        private class TimerHandler implements MessageBasedTimer.Listener {
            private TimerHandler() {
            }

            public void onTimerElapsed() {
                Controller controller = CoronaSensorManager.this.myCoronaRuntime.getController();
                if (controller == null || !AccelerometerMonitor.this.fHasReceivedMeasurement) {
                    return;
                }
                if (!AccelerometerMonitor.this.fHasReceivedSample) {
                    long unused = AccelerometerMonitor.this.fLastSampleTimestamp = AccelerometerMonitor.this.fLastSensorMeasurement.timestamp;
                    boolean unused2 = AccelerometerMonitor.this.fHasReceivedSample = true;
                    return;
                }
                if (CoronaSensorManager.compareSensorTimestamps(AccelerometerMonitor.this.fLastSensorMeasurement.timestamp, AccelerometerMonitor.this.fLastSampleTimestamp + ((AccelerometerMonitor.this.getInterval().getTotalMilliseconds() * 1000000) / 2)) <= 0) {
                    AccelerometerMonitor.this.fLastSensorMeasurement.timestamp = AccelerometerMonitor.this.fLastSampleTimestamp + (AccelerometerMonitor.this.getInterval().getTotalMilliseconds() * 1000000);
                }
                double subtractSensorTimestamps = ((double) CoronaSensorManager.subtractSensorTimestamps(AccelerometerMonitor.this.fLastSensorMeasurement.timestamp, AccelerometerMonitor.this.fLastSampleTimestamp)) * 1.0E-9d;
                long unused3 = AccelerometerMonitor.this.fLastSampleTimestamp = AccelerometerMonitor.this.fLastSensorMeasurement.timestamp;
                boolean isNaturalOrientationPortrait = controller.isNaturalOrientationPortrait();
                char c = isNaturalOrientationPortrait ? (char) 0 : 1;
                char c2 = isNaturalOrientationPortrait ? (char) 1 : 0;
                double d = ((double) (-AccelerometerMonitor.this.fLastSensorMeasurement.values[c])) / 10.0d;
                double d2 = ((double) (-AccelerometerMonitor.this.fLastSensorMeasurement.values[c2])) / 10.0d;
                double d3 = ((double) (-AccelerometerMonitor.this.fLastSensorMeasurement.values[2])) / 10.0d;
                if (!isNaturalOrientationPortrait) {
                    d2 *= -1.0d;
                }
                if (CoronaSensorManager.this.myCoronaRuntime != null && CoronaSensorManager.this.myCoronaRuntime.isRunning()) {
                    CoronaSensorManager.this.myCoronaRuntime.getTaskDispatcher().send(new AccelerometerTask(d, d2, d3, subtractSensorTimestamps));
                }
            }
        }

        public AccelerometerMonitor() {
            super();
            this.fLastSensorMeasurement = new SensorMeasurement();
            this.fLastSensorMeasurement.values = new float[]{0.0f, 0.0f, 0.0f};
            setSensorListener(new SensorHandler());
            setTimerListener(new TimerHandler());
        }

        public int getSensorType() {
            return 1;
        }

        /* access modifiers changed from: protected */
        public void onStarting() {
            this.fHasSkippedFirstMeasurement = false;
            this.fHasReceivedMeasurement = false;
            this.fHasReceivedSample = false;
        }
    }

    private class CoronaLocationListener implements LocationListener {
        private boolean fHasReceivedData;
        private boolean fSupportsGps;
        private boolean fSupportsNetwork;

        private CoronaLocationListener() {
            this.fHasReceivedData = false;
            this.fSupportsGps = false;
            this.fSupportsNetwork = false;
        }

        public void onLocationChanged(Location location) {
            if (location.getProvider().equals("gps") || !this.fHasReceivedData || !this.fSupportsGps || !CoronaSensorManager.this.myLocationManager.isProviderEnabled("gps")) {
                this.fHasReceivedData = true;
                if (CoronaSensorManager.this.myCoronaRuntime != null && CoronaSensorManager.this.myCoronaRuntime.isRunning()) {
                    CoronaSensorManager.this.myCoronaRuntime.getTaskDispatcher().send(new LocationTask(location.getLatitude(), location.getLongitude(), location.getAltitude(), (double) location.getAccuracy(), (double) location.getSpeed(), (double) location.getBearing(), ((double) location.getTime()) / 1000.0d));
                }
            }
        }

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public void setSupportsGps() {
            this.fSupportsGps = true;
        }

        public void setSupportsNetwork() {
            this.fSupportsNetwork = true;
        }
    }

    private class GyroscopeMonitor extends SensorMonitor {
        /* access modifiers changed from: private */
        public boolean fHasReceivedMeasurement = false;
        /* access modifiers changed from: private */
        public boolean fHasReceivedSample = false;
        /* access modifiers changed from: private */
        public boolean fHasSkippedFirstMeasurement = false;
        /* access modifiers changed from: private */
        public long fLastSampleTimestamp = 0;
        /* access modifiers changed from: private */
        public SensorMeasurement fLastSensorMeasurement;

        private class SensorHandler implements SensorEventListener {
            private SensorHandler() {
            }

            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null) {
                    if (!GyroscopeMonitor.this.fHasSkippedFirstMeasurement) {
                        boolean unused = GyroscopeMonitor.this.fHasSkippedFirstMeasurement = true;
                        return;
                    }
                    GyroscopeMonitor.this.fLastSensorMeasurement.copyFrom(sensorEvent);
                    boolean unused2 = GyroscopeMonitor.this.fHasReceivedMeasurement = true;
                }
            }
        }

        private class TimerHandler implements MessageBasedTimer.Listener {
            private TimerHandler() {
            }

            public void onTimerElapsed() {
                Controller controller = CoronaSensorManager.this.myCoronaRuntime.getController();
                if (controller == null || !GyroscopeMonitor.this.fHasReceivedMeasurement) {
                    return;
                }
                if (!GyroscopeMonitor.this.fHasReceivedSample) {
                    long unused = GyroscopeMonitor.this.fLastSampleTimestamp = GyroscopeMonitor.this.fLastSensorMeasurement.timestamp;
                    boolean unused2 = GyroscopeMonitor.this.fHasReceivedSample = true;
                    return;
                }
                if (CoronaSensorManager.compareSensorTimestamps(GyroscopeMonitor.this.fLastSensorMeasurement.timestamp, GyroscopeMonitor.this.fLastSampleTimestamp + ((GyroscopeMonitor.this.getInterval().getTotalMilliseconds() * 1000000) / 2)) <= 0) {
                    GyroscopeMonitor.this.fLastSensorMeasurement.timestamp = GyroscopeMonitor.this.fLastSampleTimestamp + (GyroscopeMonitor.this.getInterval().getTotalMilliseconds() * 1000000);
                }
                double subtractSensorTimestamps = ((double) CoronaSensorManager.subtractSensorTimestamps(GyroscopeMonitor.this.fLastSensorMeasurement.timestamp, GyroscopeMonitor.this.fLastSampleTimestamp)) * 1.0E-9d;
                long unused3 = GyroscopeMonitor.this.fLastSampleTimestamp = GyroscopeMonitor.this.fLastSensorMeasurement.timestamp;
                boolean isNaturalOrientationPortrait = controller.isNaturalOrientationPortrait();
                char c = isNaturalOrientationPortrait ? (char) 0 : 1;
                char c2 = isNaturalOrientationPortrait ? (char) 1 : 0;
                double d = (double) GyroscopeMonitor.this.fLastSensorMeasurement.values[c];
                double d2 = (double) GyroscopeMonitor.this.fLastSensorMeasurement.values[c2];
                double d3 = (double) GyroscopeMonitor.this.fLastSensorMeasurement.values[2];
                if (!isNaturalOrientationPortrait) {
                    d2 *= -1.0d;
                }
                if (CoronaSensorManager.this.myCoronaRuntime != null && CoronaSensorManager.this.myCoronaRuntime.isRunning()) {
                    CoronaSensorManager.this.myCoronaRuntime.getTaskDispatcher().send(new GyroscopeTask(d, d2, d3, subtractSensorTimestamps));
                }
            }
        }

        public GyroscopeMonitor() {
            super();
            this.fLastSensorMeasurement = new SensorMeasurement();
            this.fLastSensorMeasurement.values = new float[]{0.0f, 0.0f, 0.0f};
            setSensorListener(new SensorHandler());
            setTimerListener(new TimerHandler());
        }

        public int getSensorType() {
            return 4;
        }

        /* access modifiers changed from: protected */
        public void onStarting() {
            this.fHasSkippedFirstMeasurement = false;
            this.fHasReceivedMeasurement = false;
            this.fHasReceivedSample = false;
        }
    }

    private class SensorMeasurement {
        public int accuracy = 0;
        public Sensor sensor = null;
        public long timestamp = 0;
        public float[] values = null;

        public SensorMeasurement() {
        }

        public void copyFrom(SensorEvent sensorEvent) {
            if (sensorEvent != null) {
                this.accuracy = sensorEvent.accuracy;
                this.sensor = sensorEvent.sensor;
                this.timestamp = sensorEvent.timestamp;
                if (this.values == null) {
                    this.values = new float[sensorEvent.values.length];
                }
                int i = 0;
                while (i < this.values.length && i < sensorEvent.values.length) {
                    this.values[i] = sensorEvent.values[i];
                    i++;
                }
            }
        }
    }

    private abstract class SensorMonitor {
        private boolean fIsRunning = false;
        private SensorEventListener fSensorListener = null;
        private MessageBasedTimer fTimer = new MessageBasedTimer();

        public SensorMonitor() {
            setIntervalInHertz(10);
        }

        private int getSensorDelay() {
            long totalMilliseconds = this.fTimer.getInterval().getTotalMilliseconds();
            if (totalMilliseconds >= 200) {
                return 3;
            }
            if (totalMilliseconds >= 60) {
                return 2;
            }
            return totalMilliseconds >= 20 ? 1 : 0;
        }

        public TimeSpan getInterval() {
            return this.fTimer.getInterval();
        }

        public int getIntervalInHertz() {
            return (int) (1000 / this.fTimer.getInterval().getTotalMilliseconds());
        }

        public abstract int getSensorType();

        public boolean isRunning() {
            return this.fIsRunning;
        }

        /* access modifiers changed from: protected */
        public void onStarting() {
        }

        /* access modifiers changed from: protected */
        public void onStopped() {
        }

        public void setIntervalInHertz(int i) {
            TimeSpan fromMilliseconds = TimeSpan.fromMilliseconds((long) (1000 / i));
            if (!this.fTimer.getInterval().equals(fromMilliseconds)) {
                boolean isRunning = isRunning();
                if (isRunning) {
                    stop();
                }
                this.fTimer.setInterval(fromMilliseconds);
                if (isRunning) {
                    start();
                }
            }
        }

        /* access modifiers changed from: protected */
        public void setSensorListener(SensorEventListener sensorEventListener) {
            if (sensorEventListener == null) {
                throw new NullPointerException();
            } else if (sensorEventListener != this.fSensorListener) {
                boolean isRunning = isRunning();
                if (isRunning) {
                    stop();
                }
                this.fSensorListener = sensorEventListener;
                if (isRunning) {
                    start();
                }
            }
        }

        /* access modifiers changed from: protected */
        public void setTimerListener(MessageBasedTimer.Listener listener) {
            this.fTimer.setListener(listener);
        }

        public void start() {
            if (!isRunning()) {
                try {
                    onStarting();
                    SensorManager sensorManager = (SensorManager) CoronaEnvironment.getApplicationContext().getSystemService("sensor");
                    sensorManager.registerListener(this.fSensorListener, sensorManager.getDefaultSensor(getSensorType()), getSensorDelay());
                    this.fIsRunning = true;
                    if (this.fTimer.getListener() != null) {
                        if (CoronaSensorManager.this.myCoronaRuntime.getController().getHandler() != null) {
                            this.fTimer.setHandler(CoronaSensorManager.this.myCoronaRuntime.getController().getHandler());
                        }
                        this.fTimer.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop() {
            if (isRunning()) {
                try {
                    ((SensorManager) CoronaEnvironment.getApplicationContext().getSystemService("sensor")).unregisterListener(this.fSensorListener);
                    this.fTimer.stop();
                    this.fIsRunning = false;
                    onStopped();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    CoronaSensorManager(CoronaRuntime coronaRuntime) {
        this.myCoronaRuntime = coronaRuntime;
    }

    public static int compareSensorTimestamps(long j, long j2) {
        if (j2 == Long.MIN_VALUE) {
            j2++;
        }
        long j3 = j - j2;
        if (j3 < 0) {
            return -1;
        }
        return j3 == 0 ? 0 : 1;
    }

    private void startType(final int i) {
        if (this.mySensorManager != null && this.myCoronaRuntime.getController() != null) {
            this.myCoronaRuntime.getController().getHandler().post(new Runnable() {
                public void run() {
                    switch (i) {
                        case 1:
                            CoronaSensorManager.this.myAccelerometerMonitor.start();
                            return;
                        case 2:
                            CoronaSensorManager.this.myGyroscopeMonitor.start();
                            return;
                        case 3:
                            Context context = CoronaSensorManager.this.myCoronaRuntime.getController().getContext();
                            if (context != null) {
                                PackageManager packageManager = context.getPackageManager();
                                boolean hasSystemFeature = packageManager.hasSystemFeature("android.hardware.location.gps");
                                boolean hasSystemFeature2 = packageManager.hasSystemFeature("android.hardware.location.network");
                                if (hasSystemFeature || hasSystemFeature2) {
                                    Location location = null;
                                    boolean z = false;
                                    CoronaLocationListener unused = CoronaSensorManager.this.myLocationListener = new CoronaLocationListener();
                                    if (hasSystemFeature && context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
                                        CoronaSensorManager.this.myLocationListener.setSupportsGps();
                                        CoronaSensorManager.this.myLocationManager.requestLocationUpdates("gps", 1000, (float) CoronaSensorManager.this.myLocationThreshold, CoronaSensorManager.this.myLocationListener);
                                        z = true;
                                        location = CoronaSensorManager.this.myLocationManager.getLastKnownLocation("gps");
                                    }
                                    if (hasSystemFeature2 && context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                                        CoronaSensorManager.this.myLocationListener.setSupportsNetwork();
                                        CoronaSensorManager.this.myLocationManager.requestLocationUpdates("network", 1000, (float) CoronaSensorManager.this.myLocationThreshold, CoronaSensorManager.this.myLocationListener);
                                        z = true;
                                        if (location == null) {
                                            location = CoronaSensorManager.this.myLocationManager.getLastKnownLocation("network");
                                        }
                                    }
                                    if (context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
                                        CoronaSensorManager.this.myLocationManager.requestLocationUpdates("passive", 1000, (float) CoronaSensorManager.this.myLocationThreshold, CoronaSensorManager.this.myLocationListener);
                                        z = true;
                                        if (location == null) {
                                            location = CoronaSensorManager.this.myLocationManager.getLastKnownLocation("passive");
                                        }
                                    }
                                    if (!(CoronaSensorManager.this.myCoronaRuntime == null || !CoronaSensorManager.this.myCoronaRuntime.isRunning() || location == null)) {
                                        CoronaSensorManager.this.myCoronaRuntime.getTaskDispatcher().send(new LocationTask(location.getLatitude(), location.getLongitude(), location.getAltitude(), (double) location.getAccuracy(), (double) location.getSpeed(), (double) location.getBearing(), ((double) location.getTime()) / 1000.0d));
                                    }
                                    if (!z) {
                                        Log.v("Corona", "Warning: " + "This application does not have permission to read your current location.");
                                        Controller controller = CoronaSensorManager.this.myCoronaRuntime.getController();
                                        if (controller != null) {
                                            controller.showNativeAlert("Corona", "This application does not have permission to read your current location.", new String[]{"OK"});
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                Log.v("Corona", "Unable to set up location listener. This device is incapable of providing location data.");
                                return;
                            }
                            return;
                        case 4:
                            Sensor unused2 = CoronaSensorManager.this.myOrientationSensor = CoronaSensorManager.this.mySensorManager.getDefaultSensor(3);
                            SensorEventListener unused3 = CoronaSensorManager.this.myOrientationSensorListener = new SensorEventListener() {
                                public void onAccuracyChanged(Sensor sensor, int i) {
                                }

                                /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
                                    return;
                                 */
                                /* Code decompiled incorrectly, please refer to instructions dump. */
                                public void onSensorChanged(android.hardware.SensorEvent r7) {
                                    /*
                                        r6 = this;
                                        monitor-enter(r6)
                                        com.ansca.corona.CoronaSensorManager$1 r2 = com.ansca.corona.CoronaSensorManager.AnonymousClass1.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaSensorManager r2 = com.ansca.corona.CoronaSensorManager.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaRuntime r2 = r2.myCoronaRuntime     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.Controller r0 = r2.getController()     // Catch:{ all -> 0x0071 }
                                        if (r0 != 0) goto L_0x0011
                                        monitor-exit(r6)     // Catch:{ all -> 0x0071 }
                                    L_0x0010:
                                        return
                                    L_0x0011:
                                        float[] r2 = r7.values     // Catch:{ all -> 0x0071 }
                                        r3 = 0
                                        r1 = r2[r3]     // Catch:{ all -> 0x0071 }
                                        boolean r2 = r0.isNaturalOrientationPortrait()     // Catch:{ all -> 0x0071 }
                                        if (r2 != 0) goto L_0x0027
                                        r2 = 1119092736(0x42b40000, float:90.0)
                                        float r1 = r1 - r2
                                        r2 = 0
                                        int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
                                        if (r2 >= 0) goto L_0x0027
                                        r2 = 1135869952(0x43b40000, float:360.0)
                                        float r1 = r1 + r2
                                    L_0x0027:
                                        com.ansca.corona.CoronaSensorManager$1 r2 = com.ansca.corona.CoronaSensorManager.AnonymousClass1.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaSensorManager r2 = com.ansca.corona.CoronaSensorManager.this     // Catch:{ all -> 0x0071 }
                                        float r2 = r2.myLastHeading     // Catch:{ all -> 0x0071 }
                                        int r2 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
                                        if (r2 == 0) goto L_0x006f
                                        com.ansca.corona.CoronaSensorManager$1 r2 = com.ansca.corona.CoronaSensorManager.AnonymousClass1.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaSensorManager r2 = com.ansca.corona.CoronaSensorManager.this     // Catch:{ all -> 0x0071 }
                                        float unused = r2.myLastHeading = r1     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaSensorManager$1 r2 = com.ansca.corona.CoronaSensorManager.AnonymousClass1.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaSensorManager r2 = com.ansca.corona.CoronaSensorManager.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaRuntime r2 = r2.myCoronaRuntime     // Catch:{ all -> 0x0071 }
                                        if (r2 == 0) goto L_0x006f
                                        com.ansca.corona.CoronaSensorManager$1 r2 = com.ansca.corona.CoronaSensorManager.AnonymousClass1.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaSensorManager r2 = com.ansca.corona.CoronaSensorManager.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaRuntime r2 = r2.myCoronaRuntime     // Catch:{ all -> 0x0071 }
                                        boolean r2 = r2.isRunning()     // Catch:{ all -> 0x0071 }
                                        if (r2 == 0) goto L_0x006f
                                        com.ansca.corona.CoronaSensorManager$1 r2 = com.ansca.corona.CoronaSensorManager.AnonymousClass1.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaSensorManager r2 = com.ansca.corona.CoronaSensorManager.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaRuntime r2 = r2.myCoronaRuntime     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaRuntimeTaskDispatcher r2 = r2.getTaskDispatcher()     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.events.LocationTask r3 = new com.ansca.corona.events.LocationTask     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaSensorManager$1 r4 = com.ansca.corona.CoronaSensorManager.AnonymousClass1.this     // Catch:{ all -> 0x0071 }
                                        com.ansca.corona.CoronaSensorManager r4 = com.ansca.corona.CoronaSensorManager.this     // Catch:{ all -> 0x0071 }
                                        float r4 = r4.myLastHeading     // Catch:{ all -> 0x0071 }
                                        double r4 = (double) r4     // Catch:{ all -> 0x0071 }
                                        r3.<init>(r4)     // Catch:{ all -> 0x0071 }
                                        r2.send(r3)     // Catch:{ all -> 0x0071 }
                                    L_0x006f:
                                        monitor-exit(r6)     // Catch:{ all -> 0x0071 }
                                        goto L_0x0010
                                    L_0x0071:
                                        r2 = move-exception
                                        monitor-exit(r6)     // Catch:{ all -> 0x0071 }
                                        throw r2
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.CoronaSensorManager.AnonymousClass1.AnonymousClass1.onSensorChanged(android.hardware.SensorEvent):void");
                                }
                            };
                            CoronaSensorManager.this.mySensorManager.registerListener(CoronaSensorManager.this.myOrientationSensorListener, CoronaSensorManager.this.myOrientationSensor, 2);
                            return;
                        default:
                            return;
                    }
                }
            });
        }
    }

    private void stopType(final int i) {
        this.myCoronaRuntime.getController().getHandler().post(new Runnable() {
            public void run() {
                switch (i) {
                    case 1:
                        CoronaSensorManager.this.myAccelerometerMonitor.stop();
                        return;
                    case 2:
                        CoronaSensorManager.this.myGyroscopeMonitor.stop();
                        return;
                    case 3:
                        if (CoronaSensorManager.this.myLocationListener != null) {
                            CoronaSensorManager.this.myLocationManager.removeUpdates(CoronaSensorManager.this.myLocationListener);
                            CoronaLocationListener unused = CoronaSensorManager.this.myLocationListener = null;
                            return;
                        }
                        return;
                    case 4:
                        if (CoronaSensorManager.this.myOrientationSensorListener != null) {
                            CoronaSensorManager.this.mySensorManager.unregisterListener(CoronaSensorManager.this.myOrientationSensorListener);
                            SensorEventListener unused2 = CoronaSensorManager.this.myOrientationSensorListener = null;
                            float unused3 = CoronaSensorManager.this.myLastHeading = -1.0f;
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        });
    }

    public static long subtractSensorTimestamps(long j, long j2) {
        if (j2 == Long.MIN_VALUE) {
            j2++;
        }
        return j - j2;
    }

    public boolean hasAccelerometer() {
        return this.mySensorManager != null && this.mySensorManager.getSensorList(1).size() > 0;
    }

    public boolean hasGyroscope() {
        return this.mySensorManager != null && this.mySensorManager.getSensorList(4).size() > 0;
    }

    public void init() {
        if (this.myCoronaRuntime.getController() != null) {
            this.mySensorManager = (SensorManager) this.myCoronaRuntime.getController().getContext().getSystemService("sensor");
            this.myLocationManager = (LocationManager) this.myCoronaRuntime.getController().getContext().getSystemService("location");
            this.myAccelerometerMonitor = new AccelerometerMonitor();
            this.myGyroscopeMonitor = new GyroscopeMonitor();
            for (int i = 0; i < this.myActiveSensors.length; i++) {
                this.myActiveSensors[i] = false;
            }
        }
    }

    public boolean isActive(int i) {
        return this.myActiveSensors[i];
    }

    public void pause() {
        for (int i = 0; i < 6; i++) {
            stopType(i);
        }
    }

    public void resume() {
        for (int i = 0; i < 6; i++) {
            if (this.myActiveSensors[i]) {
                startType(i);
            }
        }
    }

    public void setAccelerometerInterval(int i) {
        this.myAccelerometerMonitor.setIntervalInHertz(i);
    }

    public void setEventNotification(int i, boolean z) {
        if (z) {
            start(i);
        } else {
            stop(i);
        }
    }

    public void setGyroscopeInterval(int i) {
        this.myGyroscopeMonitor.setIntervalInHertz(i);
    }

    public void setLocationAccuracy(double d) {
    }

    public void setLocationThreshold(double d) {
        this.myLocationThreshold = d;
    }

    public void start(int i) {
        if (!this.myActiveSensors[i]) {
            startType(i);
            this.myActiveSensors[i] = true;
        }
    }

    public void stop() {
        for (int i = 0; i < 6; i++) {
            stop(i);
        }
    }

    public void stop(int i) {
        if (this.myActiveSensors[i]) {
            stopType(i);
            this.myActiveSensors[i] = false;
        }
    }
}
