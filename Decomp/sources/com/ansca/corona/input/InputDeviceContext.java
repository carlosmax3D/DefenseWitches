package com.ansca.corona.input;

import com.ansca.corona.input.InputDeviceStatusEventInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class InputDeviceContext {
    private HashMap<Integer, AxisDataPoint> fAxisData;
    private ArrayList<AxisDataEventInfo> fAxisEvents;
    private ConnectionState fConnectionState;
    private int fCoronaDeviceId;
    private InputDeviceInfo fDeviceInfo;
    private boolean fIsConnected;
    private ArrayList<Listener> fListeners;
    private InputDeviceStatusEventInfo.Settings fStatusEventSettings;
    private VibrateRequestHandler fVibrateRequestHandler;

    public interface Listener {
    }

    public interface OnAxisDataReceivedListener extends Listener {
        void onAxisDataReceived(InputDeviceContext inputDeviceContext, AxisDataEventInfo axisDataEventInfo);
    }

    public interface OnStatusChangedListener extends Listener {
        void onStatusChanged(InputDeviceContext inputDeviceContext, InputDeviceStatusEventInfo inputDeviceStatusEventInfo);
    }

    public interface VibrateRequestHandler {
        void onHandleVibrateRequest(InputDeviceContext inputDeviceContext, VibrationSettings vibrationSettings);
    }

    InputDeviceContext(int i, InputDeviceInfo inputDeviceInfo) {
        if (inputDeviceInfo == null) {
            throw new NullPointerException();
        }
        this.fCoronaDeviceId = i;
        this.fDeviceInfo = inputDeviceInfo;
        this.fIsConnected = false;
        this.fConnectionState = ConnectionState.DISCONNECTED;
        this.fAxisData = new HashMap<>();
        this.fAxisEvents = new ArrayList<>();
        this.fVibrateRequestHandler = null;
        this.fListeners = new ArrayList<>();
        this.fStatusEventSettings = null;
    }

    public synchronized void addListener(Listener listener) {
        if (listener != null) {
            if (!this.fListeners.contains(listener)) {
                this.fListeners.add(listener);
            }
        }
    }

    public void beginUpdate() {
        if (this.fStatusEventSettings == null) {
            this.fStatusEventSettings = new InputDeviceStatusEventInfo.Settings();
        }
    }

    public void endUpdate() {
        ArrayList arrayList;
        ArrayList arrayList2;
        InputDeviceStatusEventInfo.Settings settings = this.fStatusEventSettings;
        if (settings != null) {
            this.fStatusEventSettings = null;
            boolean z = this.fAxisEvents.size() > 0;
            boolean hasConnectionStateChanged = settings.hasConnectionStateChanged() | settings.wasReconfigured();
            if (z || hasConnectionStateChanged) {
                synchronized (this) {
                    arrayList = (ArrayList) this.fAxisEvents.clone();
                    this.fAxisEvents.clear();
                    arrayList2 = (ArrayList) this.fListeners.clone();
                }
                if (arrayList2.size() > 0) {
                    InputDeviceStatusEventInfo inputDeviceStatusEventInfo = new InputDeviceStatusEventInfo(settings);
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        Listener listener = (Listener) it.next();
                        if (hasConnectionStateChanged && (listener instanceof OnStatusChangedListener)) {
                            ((OnStatusChangedListener) listener).onStatusChanged(this, inputDeviceStatusEventInfo);
                        }
                        if (z && (listener instanceof OnAxisDataReceivedListener)) {
                            Iterator it2 = arrayList.iterator();
                            while (it2.hasNext()) {
                                ((OnAxisDataReceivedListener) listener).onAxisDataReceived(this, (AxisDataEventInfo) it2.next());
                            }
                        }
                    }
                }
            }
        }
    }

    public synchronized AxisDataPoint getAxisDataByIndex(int i) {
        return this.fAxisData.get(Integer.valueOf(i));
    }

    public ConnectionState getConnectionState() {
        return this.fConnectionState;
    }

    public int getCoronaDeviceId() {
        return this.fCoronaDeviceId;
    }

    public InputDeviceInfo getDeviceInfo() {
        return this.fDeviceInfo;
    }

    public VibrateRequestHandler getVibrateRequestHandler() {
        return this.fVibrateRequestHandler;
    }

    public boolean isConnected() {
        return this.fConnectionState.isConnected();
    }

    public boolean isUpdating() {
        return this.fStatusEventSettings != null;
    }

    public synchronized void removeListener(Listener listener) {
        if (listener != null) {
            this.fListeners.remove(listener);
        }
    }

    public void setVibrateRequestHandler(VibrateRequestHandler vibrateRequestHandler) {
        this.fVibrateRequestHandler = vibrateRequestHandler;
    }

    public void update(int i, AxisDataPoint axisDataPoint) {
        AxisInfo byIndex;
        AxisDataPoint axisDataPoint2;
        if (axisDataPoint != null && (byIndex = this.fDeviceInfo.getAxes().getByIndex(i)) != null) {
            if (axisDataPoint.getValue() > byIndex.getMaxValue()) {
                axisDataPoint = new AxisDataPoint(byIndex.getMaxValue(), axisDataPoint.getTimestamp());
            } else if (axisDataPoint.getValue() < byIndex.getMinValue()) {
                axisDataPoint = new AxisDataPoint(byIndex.getMinValue(), axisDataPoint.getTimestamp());
            }
            Integer valueOf = Integer.valueOf(i);
            synchronized (this) {
                axisDataPoint2 = this.fAxisData.get(valueOf);
            }
            if (axisDataPoint2 != null) {
                float accuracy = byIndex.getAccuracy();
                if (accuracy <= 0.0f) {
                    accuracy = 0.01f;
                }
                if (axisDataPoint.getValue() < axisDataPoint2.getValue() + accuracy && axisDataPoint.getValue() > axisDataPoint2.getValue() - accuracy) {
                    return;
                }
            }
            boolean isUpdating = isUpdating();
            if (!isUpdating) {
                beginUpdate();
            }
            synchronized (this) {
                this.fAxisData.put(valueOf, axisDataPoint);
                this.fAxisEvents.add(new AxisDataEventInfo(this.fDeviceInfo, i, axisDataPoint));
            }
            if (!isUpdating) {
                endUpdate();
            }
        }
    }

    public void update(ConnectionState connectionState) {
        if (connectionState == null) {
            throw new NullPointerException();
        } else if (connectionState != this.fConnectionState) {
            this.fConnectionState = connectionState;
            boolean isUpdating = isUpdating();
            if (!isUpdating) {
                beginUpdate();
            }
            this.fStatusEventSettings.setHasConnectionStateChanged(true);
            if (!isUpdating) {
                endUpdate();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void update(InputDeviceInfo inputDeviceInfo) {
        if (inputDeviceInfo == null) {
            throw new NullPointerException();
        } else if (!this.fDeviceInfo.equals(inputDeviceInfo)) {
            this.fDeviceInfo = inputDeviceInfo;
            boolean isUpdating = isUpdating();
            if (!isUpdating) {
                beginUpdate();
            }
            this.fStatusEventSettings.setWasReconfigured(true);
            if (!isUpdating) {
                endUpdate();
            }
        }
    }

    public void update(InputDeviceSettings inputDeviceSettings) {
        update(InputDeviceInfo.from(inputDeviceSettings));
    }

    public void vibrate() {
        VibrateRequestHandler vibrateRequestHandler;
        if (this.fDeviceInfo.canVibrate() && (vibrateRequestHandler = this.fVibrateRequestHandler) != null) {
            vibrateRequestHandler.onHandleVibrateRequest(this, (VibrationSettings) null);
        }
    }
}
