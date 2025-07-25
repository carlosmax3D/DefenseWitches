package com.ansca.corona.input;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.InputDevice;
import com.ansca.corona.MessageBasedTimer;
import com.ansca.corona.TimeSpan;

public class InputDeviceMonitor {
    private Context fContext;
    private EventHandler fEventHandler;
    private boolean fIsRunning;
    private Listener fListener;

    private static class ApiLevel16 {

        private static class EventHandler extends EventHandler implements InputManager.InputDeviceListener {
            private Handler fHandler = new Handler(Looper.getMainLooper());

            public EventHandler(InputDeviceMonitor inputDeviceMonitor) {
                super(inputDeviceMonitor);
            }

            private InputManager getInputManager() {
                return (InputManager) getDeviceMonitor().getContext().getSystemService("input");
            }

            public void onInputDeviceAdded(int i) {
                getDeviceMonitor().onInputDeviceConnected(i);
            }

            public void onInputDeviceChanged(int i) {
                getDeviceMonitor().onInputDeviceReconfigured(i);
            }

            public void onInputDeviceRemoved(int i) {
                getDeviceMonitor().onInputDeviceDisconnected(i);
            }

            public void subscribe() {
                InputManager inputManager = getInputManager();
                if (inputManager != null) {
                    inputManager.registerInputDeviceListener(this, this.fHandler);
                }
            }

            public void unsubscribe() {
                InputManager inputManager = getInputManager();
                if (inputManager != null) {
                    inputManager.unregisterInputDeviceListener(this);
                }
            }
        }

        private ApiLevel16() {
        }

        public static EventHandler createEventHandlerWith(InputDeviceMonitor inputDeviceMonitor) {
            return new EventHandler(inputDeviceMonitor);
        }
    }

    private static class ApiLevel9 {

        private static class EventHandler extends EventHandler implements MessageBasedTimer.Listener {
            private int[] fLastAndroidDeviceIdArray;
            private MessageBasedTimer fTimer = new MessageBasedTimer();

            public EventHandler(InputDeviceMonitor inputDeviceMonitor) {
                super(inputDeviceMonitor);
                this.fTimer.setHandler(new Handler(Looper.getMainLooper()));
                this.fTimer.setInterval(TimeSpan.fromSeconds(1));
                this.fTimer.setListener(this);
                this.fLastAndroidDeviceIdArray = new int[0];
            }

            public void onTimerElapsed() {
                int[] deviceIds = InputDevice.getDeviceIds();
                if (deviceIds == null) {
                    deviceIds = new int[0];
                }
                for (int i : deviceIds) {
                    boolean z = true;
                    int[] iArr = this.fLastAndroidDeviceIdArray;
                    int length = iArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (i == iArr[i2]) {
                            z = false;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (z) {
                        getDeviceMonitor().onInputDeviceConnected(i);
                    }
                }
                for (int i3 : this.fLastAndroidDeviceIdArray) {
                    boolean z2 = true;
                    int length2 = deviceIds.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length2) {
                            break;
                        } else if (deviceIds[i4] == i3) {
                            z2 = false;
                            break;
                        } else {
                            i4++;
                        }
                    }
                    if (z2) {
                        getDeviceMonitor().onInputDeviceDisconnected(i3);
                    }
                }
                this.fLastAndroidDeviceIdArray = deviceIds;
            }

            public void subscribe() {
                this.fLastAndroidDeviceIdArray = InputDevice.getDeviceIds();
                if (this.fLastAndroidDeviceIdArray == null) {
                    this.fLastAndroidDeviceIdArray = new int[0];
                }
                this.fTimer.start();
            }

            public void unsubscribe() {
                this.fTimer.stop();
            }
        }

        private ApiLevel9() {
        }

        public static EventHandler createEventHandlerWith(InputDeviceMonitor inputDeviceMonitor) {
            return new EventHandler(inputDeviceMonitor);
        }
    }

    private static abstract class EventHandler {
        private InputDeviceMonitor fDeviceMonitor;

        public EventHandler(InputDeviceMonitor inputDeviceMonitor) {
            if (inputDeviceMonitor == null) {
                throw new NullPointerException();
            }
            this.fDeviceMonitor = inputDeviceMonitor;
        }

        public InputDeviceMonitor getDeviceMonitor() {
            return this.fDeviceMonitor;
        }

        public abstract void subscribe();

        public abstract void unsubscribe();
    }

    public interface Listener {
        void onInputDeviceConnected(int i);

        void onInputDeviceDisconnected(int i);

        void onInputDeviceReconfigured(int i);
    }

    public InputDeviceMonitor(Context context) {
        if (context == null) {
            throw new NullPointerException();
        }
        this.fContext = context.getApplicationContext();
        this.fListener = null;
        this.fIsRunning = false;
        if (Build.VERSION.SDK_INT >= 16) {
            this.fEventHandler = ApiLevel16.createEventHandlerWith(this);
        } else if (Build.VERSION.SDK_INT >= 9) {
            this.fEventHandler = ApiLevel9.createEventHandlerWith(this);
        } else {
            this.fEventHandler = null;
        }
    }

    /* access modifiers changed from: private */
    public void onInputDeviceConnected(int i) {
        Listener listener = this.fListener;
        if (listener != null) {
            listener.onInputDeviceConnected(i);
        }
    }

    /* access modifiers changed from: private */
    public void onInputDeviceDisconnected(int i) {
        Listener listener = this.fListener;
        if (listener != null) {
            listener.onInputDeviceDisconnected(i);
        }
    }

    /* access modifiers changed from: private */
    public void onInputDeviceReconfigured(int i) {
        Listener listener = this.fListener;
        if (listener != null) {
            listener.onInputDeviceReconfigured(i);
        }
    }

    public boolean canMonitorDevices() {
        return this.fEventHandler != null;
    }

    public Context getContext() {
        return this.fContext;
    }

    public Listener getListener() {
        return this.fListener;
    }

    public boolean isRunning() {
        return this.fIsRunning;
    }

    public void setListener(Listener listener) {
        this.fListener = listener;
    }

    public void start() {
        if (!this.fIsRunning && this.fEventHandler != null) {
            this.fEventHandler.subscribe();
            this.fIsRunning = true;
        }
    }

    public void stop() {
        if (this.fIsRunning && this.fEventHandler != null) {
            this.fEventHandler.unsubscribe();
            this.fIsRunning = false;
        }
    }
}
