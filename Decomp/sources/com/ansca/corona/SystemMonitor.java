package com.ansca.corona;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import com.ansca.corona.events.EventManager;
import com.ansca.corona.events.RunnableEvent;

public class SystemMonitor {
    private Context fContext;
    /* access modifiers changed from: private */
    public CoronaRuntime fCoronaRuntime;
    private boolean fIsLowOnMemory;
    /* access modifiers changed from: private */
    public boolean fIsScreenOn;
    private boolean fIsSilentModeEnabled;
    private SystemEventHandler fSystemEventHandler;

    private static class SystemEventHandler extends BroadcastReceiver {
        private CoronaApiListener fListener;
        private SystemMonitor fMonitor;

        public SystemEventHandler(SystemMonitor systemMonitor) {
            if (systemMonitor == null) {
                throw new NullPointerException();
            }
            this.fMonitor = systemMonitor;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            intentFilter.addAction("android.media.RINGER_MODE_CHANGED");
            this.fMonitor.getContext().registerReceiver(this, intentFilter);
            this.fListener = null;
        }

        public void dispose() {
            this.fMonitor.getContext().unregisterReceiver(this);
        }

        public void onReceive(Context context, Intent intent) {
            String action;
            if (intent != null && (action = intent.getAction()) != null && action.length() > 0) {
                if (action.equals("android.intent.action.SCREEN_OFF") && this.fListener != null) {
                    boolean unused = this.fMonitor.fIsScreenOn = false;
                    this.fListener.onScreenLockStateChanged(true);
                } else if (action.equals("android.intent.action.SCREEN_ON") && this.fListener != null) {
                    boolean unused2 = this.fMonitor.fIsScreenOn = true;
                    this.fListener.onScreenLockStateChanged(this.fMonitor.isScreenLocked());
                } else if (action.equals("android.intent.action.USER_PRESENT") && this.fListener != null) {
                    this.fListener.onScreenLockStateChanged(false);
                } else if (action.equals("android.media.RINGER_MODE_CHANGED")) {
                    this.fMonitor.isSilentModeEnabled();
                }
            }
        }

        public void setCoronaApiListener(CoronaApiListener coronaApiListener) {
            this.fListener = coronaApiListener;
        }
    }

    public SystemMonitor(CoronaRuntime coronaRuntime, Context context) {
        if (context == null) {
            throw new NullPointerException();
        }
        this.fContext = context;
        this.fSystemEventHandler = null;
        this.fIsScreenOn = true;
        this.fIsLowOnMemory = false;
        this.fIsSilentModeEnabled = false;
        this.fCoronaRuntime = coronaRuntime;
    }

    /* access modifiers changed from: private */
    public Context getContext() {
        return this.fContext;
    }

    public boolean isLowOnMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) this.fContext.getSystemService("activity")).getMemoryInfo(memoryInfo);
        if (this.fIsLowOnMemory != memoryInfo.lowMemory) {
            this.fIsLowOnMemory = memoryInfo.lowMemory;
            EventManager eventManager = this.fCoronaRuntime.getController().getEventManager();
            if (this.fIsLowOnMemory && eventManager != null) {
                eventManager.addEvent(new RunnableEvent(new Runnable() {
                    public void run() {
                        if (SystemMonitor.this.fCoronaRuntime.getController() != null) {
                            JavaToNativeShim.memoryWarningEvent(SystemMonitor.this.fCoronaRuntime);
                        }
                    }
                }));
            }
        }
        return this.fIsLowOnMemory;
    }

    public boolean isRunning() {
        return this.fSystemEventHandler != null;
    }

    public boolean isScreenLocked() {
        return ((KeyguardManager) this.fContext.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    public boolean isScreenOff() {
        return !this.fIsScreenOn;
    }

    public boolean isScreenOn() {
        return this.fIsScreenOn;
    }

    public boolean isScreenUnlocked() {
        return !isScreenLocked();
    }

    public boolean isSilentModeEnabled() {
        boolean z = ((AudioManager) this.fContext.getSystemService("audio")).getRingerMode() != 2;
        if (z != this.fIsSilentModeEnabled) {
            this.fIsSilentModeEnabled = z;
        }
        return this.fIsSilentModeEnabled;
    }

    public void setCoronaApiListener(CoronaApiListener coronaApiListener) {
        this.fSystemEventHandler.setCoronaApiListener(coronaApiListener);
    }

    public void start() {
        if (this.fSystemEventHandler == null && !this.fCoronaRuntime.isCoronaKit()) {
            this.fSystemEventHandler = new SystemEventHandler(this);
        }
    }

    public void stop() {
        if (this.fSystemEventHandler != null) {
            this.fSystemEventHandler.dispose();
            this.fSystemEventHandler = null;
            this.fIsSilentModeEnabled = false;
        }
    }

    public void update() {
        isLowOnMemory();
    }
}
