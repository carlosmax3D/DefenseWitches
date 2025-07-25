package com.ansca.corona.input;

import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.ansca.corona.ApplicationContextProvider;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.ansca.corona.input.InputDeviceContext;
import com.ansca.corona.input.InputDeviceMonitor;
import java.util.HashMap;
import java.util.Iterator;

public final class InputDeviceServices extends ApplicationContextProvider {
    /* access modifiers changed from: private */
    public static InputDeviceInterfaceCollection sDeviceCollection = new InputDeviceInterfaceCollection();
    /* access modifiers changed from: private */
    public static InputDeviceMonitor sDeviceMonitor = null;
    private static int sNextCoronaDeviceId = 1;
    /* access modifiers changed from: private */
    public static HashMap<CoronaRuntime, CoronaRuntimeTaskDispatcher> sTaskDispatcherMap = new HashMap<>();

    private static class ApiLevel16 {
        private ApiLevel16() {
        }

        public static void vibrateInputDeviceUsing(int i, VibrationSettings vibrationSettings) {
            Vibrator vibrator;
            InputDevice device = InputDevice.getDevice(i);
            if (device != null && (vibrator = device.getVibrator()) != null && vibrator.hasVibrator()) {
                vibrator.vibrate(100);
            }
        }
    }

    private static class ApiLevel9 {
        private ApiLevel9() {
        }

        public static int[] fetchAndroidDeviceIds() {
            int[] deviceIds = InputDevice.getDeviceIds();
            return deviceIds == null ? new int[0] : deviceIds;
        }
    }

    private static class CoronaRuntimeEventHandler implements CoronaRuntimeListener {
        public void onExiting(CoronaRuntime coronaRuntime) {
            synchronized (InputDeviceServices.sTaskDispatcherMap) {
                InputDeviceServices.sTaskDispatcherMap.remove(coronaRuntime);
            }
            InputDeviceServices.sDeviceMonitor.stop();
        }

        public void onLoaded(CoronaRuntime coronaRuntime) {
            synchronized (InputDeviceServices.sTaskDispatcherMap) {
                InputDeviceServices.sTaskDispatcherMap.put(coronaRuntime, new CoronaRuntimeTaskDispatcher(coronaRuntime));
            }
        }

        public void onResumed(CoronaRuntime coronaRuntime) {
            InputDeviceServices.sDeviceMonitor.start();
        }

        public void onStarted(CoronaRuntime coronaRuntime) {
            InputDeviceServices.sDeviceMonitor.start();
        }

        public void onSuspended(CoronaRuntime coronaRuntime) {
            InputDeviceServices.sDeviceMonitor.stop();
        }
    }

    private static class InputDeviceContextEventHandler implements InputDeviceContext.OnStatusChangedListener, InputDeviceContext.OnAxisDataReceivedListener, InputDeviceContext.VibrateRequestHandler {
        public static InputDeviceContextEventHandler INSTANCE = new InputDeviceContextEventHandler();

        public void onAxisDataReceived(InputDeviceContext inputDeviceContext, AxisDataEventInfo axisDataEventInfo) {
            InputDeviceInterface byCoronaDeviceId;
            if (inputDeviceContext != null && axisDataEventInfo != null && InputDeviceServices.sDeviceMonitor.isRunning()) {
                synchronized (InputDeviceServices.class) {
                    byCoronaDeviceId = InputDeviceServices.sDeviceCollection.getByCoronaDeviceId(inputDeviceContext.getCoronaDeviceId());
                }
                if (byCoronaDeviceId != null) {
                    RaiseAxisEventTask raiseAxisEventTask = new RaiseAxisEventTask(byCoronaDeviceId, axisDataEventInfo);
                    synchronized (InputDeviceServices.sTaskDispatcherMap) {
                        for (CoronaRuntimeTaskDispatcher send : InputDeviceServices.sTaskDispatcherMap.values()) {
                            send.send(raiseAxisEventTask);
                        }
                    }
                }
            }
        }

        public void onHandleVibrateRequest(InputDeviceContext inputDeviceContext, VibrationSettings vibrationSettings) {
            if (inputDeviceContext != null && Build.VERSION.SDK_INT >= 16 && inputDeviceContext.getDeviceInfo().hasAndroidDeviceId()) {
                ApiLevel16.vibrateInputDeviceUsing(inputDeviceContext.getDeviceInfo().getAndroidDeviceId(), vibrationSettings);
            }
        }

        public void onStatusChanged(InputDeviceContext inputDeviceContext, InputDeviceStatusEventInfo inputDeviceStatusEventInfo) {
            InputDeviceInterface byCoronaDeviceId;
            if (inputDeviceContext != null && inputDeviceStatusEventInfo != null && InputDeviceServices.sDeviceMonitor.isRunning()) {
                synchronized (InputDeviceServices.class) {
                    byCoronaDeviceId = InputDeviceServices.sDeviceCollection.getByCoronaDeviceId(inputDeviceContext.getCoronaDeviceId());
                }
                if (byCoronaDeviceId != null) {
                    RaiseInputDeviceStatusChangedEventTask raiseInputDeviceStatusChangedEventTask = new RaiseInputDeviceStatusChangedEventTask(byCoronaDeviceId, inputDeviceStatusEventInfo);
                    synchronized (InputDeviceServices.sTaskDispatcherMap) {
                        for (CoronaRuntimeTaskDispatcher send : InputDeviceServices.sTaskDispatcherMap.values()) {
                            send.send(raiseInputDeviceStatusChangedEventTask);
                        }
                    }
                }
            }
        }
    }

    private static class InputDeviceMonitorEventHandler implements InputDeviceMonitor.Listener {
        public void onInputDeviceConnected(int i) {
            InputDeviceServices.updateCollectionWithAndroidDeviceId(i);
        }

        public void onInputDeviceDisconnected(int i) {
            InputDeviceInterfaceCollection clone;
            synchronized (InputDeviceServices.class) {
                clone = InputDeviceServices.sDeviceCollection.clone();
            }
            if (clone != null) {
                Iterator<InputDeviceInterface> it = clone.iterator();
                while (it.hasNext()) {
                    InputDeviceInterface next = it.next();
                    if (next.getDeviceInfo().getAndroidDeviceId() == i) {
                        next.getContext().beginUpdate();
                        next.getContext().update(ConnectionState.DISCONNECTED);
                        if (next.getDeviceInfo().getPlayerNumber() > 0) {
                            next.getContext().update(next.getDeviceInfo().cloneWithoutPlayerNumber());
                        }
                        next.getContext().endUpdate();
                    }
                }
            }
        }

        public void onInputDeviceReconfigured(int i) {
            InputDeviceServices.updateCollectionWithAndroidDeviceId(i);
        }
    }

    static {
        CoronaEnvironment.addRuntimeListener(new CoronaRuntimeEventHandler());
    }

    public InputDeviceServices(Context context) {
        super(context);
        if (sDeviceMonitor == null) {
            sDeviceMonitor = new InputDeviceMonitor(context);
            sDeviceMonitor.setListener(new InputDeviceMonitorEventHandler());
        }
        if (Build.VERSION.SDK_INT >= 9 && sDeviceCollection.size() <= 0) {
            fetchAll();
        }
    }

    private static InputDeviceContext add(InputDeviceInfo inputDeviceInfo) {
        InputDeviceContext inputDeviceContext = null;
        synchronized (InputDeviceServices.class) {
            if (inputDeviceInfo != null) {
                if (!inputDeviceInfo.hasAndroidDeviceId() || sDeviceCollection.getByAndroidDeviceIdAndType(inputDeviceInfo.getAndroidDeviceId(), inputDeviceInfo.getType()) == null) {
                    int i = sNextCoronaDeviceId;
                    sNextCoronaDeviceId++;
                    inputDeviceContext = new InputDeviceContext(i, inputDeviceInfo);
                    sDeviceCollection.add(new InputDeviceInterface(inputDeviceContext));
                    inputDeviceContext.addListener(InputDeviceContextEventHandler.INSTANCE);
                    if (inputDeviceInfo.hasAndroidDeviceId()) {
                        inputDeviceContext.setVibrateRequestHandler(InputDeviceContextEventHandler.INSTANCE);
                    }
                }
            }
        }
        return inputDeviceContext;
    }

    private static void updateCollection() {
        InputDeviceInterfaceCollection clone;
        if (Build.VERSION.SDK_INT >= 9) {
            synchronized (InputDeviceServices.class) {
                clone = sDeviceCollection.clone();
            }
            if (clone != null) {
                int[] fetchAndroidDeviceIds = ApiLevel9.fetchAndroidDeviceIds();
                Iterator<InputDeviceInterface> it = clone.iterator();
                while (it.hasNext()) {
                    InputDeviceInterface next = it.next();
                    if (next.getDeviceInfo().hasAndroidDeviceId()) {
                        boolean z = false;
                        int length = fetchAndroidDeviceIds.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            }
                            if (next.getDeviceInfo().getAndroidDeviceId() == fetchAndroidDeviceIds[i]) {
                                z = true;
                                break;
                            }
                            i++;
                        }
                        if (!z) {
                            next.getContext().beginUpdate();
                            next.getContext().update(ConnectionState.DISCONNECTED);
                            if (next.getDeviceInfo().getPlayerNumber() > 0) {
                                next.getContext().update(next.getDeviceInfo().cloneWithoutPlayerNumber());
                            }
                            next.getContext().endUpdate();
                        }
                    }
                }
                for (int updateCollectionWithAndroidDeviceId : fetchAndroidDeviceIds) {
                    updateCollectionWithAndroidDeviceId(updateCollectionWithAndroidDeviceId);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void updateCollectionWithAndroidDeviceId(int i) {
        InputDeviceInterface byAndroidDeviceIdAndType;
        if (Build.VERSION.SDK_INT >= 9) {
            for (InputDeviceInfo next : InputDeviceInfo.collectionFromAndroidDeviceId(i)) {
                InputDeviceContext inputDeviceContext = null;
                synchronized (InputDeviceServices.class) {
                    byAndroidDeviceIdAndType = sDeviceCollection.getByAndroidDeviceIdAndType(i, next.getType());
                    if (byAndroidDeviceIdAndType == null && next.hasPermanentStringId()) {
                        byAndroidDeviceIdAndType = sDeviceCollection.getByPermanentStringIdAndTypeAndDisplayName(next.getPermanentStringId(), next.getType(), next.getDisplayName());
                    }
                    if (byAndroidDeviceIdAndType == null && Build.VERSION.SDK_INT < 16) {
                        byAndroidDeviceIdAndType = sDeviceCollection.getBy(ConnectionState.DISCONNECTED).getByTypeAndDisplayName(next.getType(), next.getDisplayName());
                    }
                }
                if (byAndroidDeviceIdAndType != null) {
                    inputDeviceContext = byAndroidDeviceIdAndType.getContext();
                }
                if (inputDeviceContext != null || (inputDeviceContext = add(next)) != null) {
                    inputDeviceContext.beginUpdate();
                    inputDeviceContext.update(ConnectionState.CONNECTED);
                    inputDeviceContext.update(next);
                    inputDeviceContext.endUpdate();
                }
            }
        }
    }

    public InputDeviceContext add(InputDeviceSettings inputDeviceSettings) {
        return add(InputDeviceInfo.from(inputDeviceSettings));
    }

    public InputDeviceInterfaceCollection fetchAll() {
        InputDeviceInterfaceCollection clone;
        updateCollection();
        synchronized (InputDeviceServices.class) {
            clone = sDeviceCollection.clone();
        }
        return clone == null ? new InputDeviceInterfaceCollection() : clone;
    }

    public InputDeviceInterfaceCollection fetchByAndroidDeviceId(int i) {
        updateCollectionWithAndroidDeviceId(i);
        InputDeviceInterfaceCollection inputDeviceInterfaceCollection = new InputDeviceInterfaceCollection();
        synchronized (InputDeviceServices.class) {
            Iterator<InputDeviceInterface> it = sDeviceCollection.iterator();
            while (it.hasNext()) {
                InputDeviceInterface next = it.next();
                if (next.getDeviceInfo().hasAndroidDeviceId() && next.getDeviceInfo().getAndroidDeviceId() == i) {
                    inputDeviceInterfaceCollection.add(next);
                }
            }
        }
        return inputDeviceInterfaceCollection;
    }

    public InputDeviceInterface fetchByAndroidDeviceIdAndType(int i, InputDeviceType inputDeviceType) {
        InputDeviceInterface byAndroidDeviceIdAndType;
        if (inputDeviceType == null) {
            return null;
        }
        updateCollectionWithAndroidDeviceId(i);
        synchronized (InputDeviceServices.class) {
            byAndroidDeviceIdAndType = sDeviceCollection.getByAndroidDeviceIdAndType(i, inputDeviceType);
        }
        return byAndroidDeviceIdAndType;
    }

    public InputDeviceInterface fetchByCoronaDeviceId(int i) {
        InputDeviceInterface byCoronaDeviceId;
        synchronized (InputDeviceServices.class) {
            byCoronaDeviceId = sDeviceCollection.getByCoronaDeviceId(i);
        }
        return byCoronaDeviceId;
    }

    public InputDeviceInterface fetchDeviceFrom(KeyEvent keyEvent) {
        if (keyEvent == null) {
            return null;
        }
        return fetchByAndroidDeviceIdAndType(keyEvent.getDeviceId(), InputDeviceType.from(keyEvent));
    }

    public InputDeviceInterface fetchDeviceFrom(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return null;
        }
        return fetchByAndroidDeviceIdAndType(motionEvent.getDeviceId(), InputDeviceType.from(motionEvent));
    }
}
