package com.ansca.corona.input;

import android.os.Build;
import android.view.KeyEvent;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class RaiseKeyEventTask implements CoronaRuntimeTask {
    private InputDeviceInterface fDevice;
    /* access modifiers changed from: private */
    public KeyEvent fKeyEvent;

    private static class ApiLevel11 {
        private ApiLevel11() {
        }

        public static boolean isCapsLockOnFor(KeyEvent keyEvent) {
            return keyEvent.isCapsLockOn();
        }

        public static boolean isCtrlPressedFor(KeyEvent keyEvent) {
            return keyEvent.isCtrlPressed();
        }
    }

    public RaiseKeyEventTask(InputDeviceInterface inputDeviceInterface, KeyEvent keyEvent) {
        if (keyEvent == null) {
            throw new NullPointerException();
        }
        this.fDevice = inputDeviceInterface;
        this.fKeyEvent = keyEvent;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        if (this.fKeyEvent != null) {
            boolean z = false;
            boolean isShiftPressed = this.fKeyEvent.isShiftPressed();
            if (Build.VERSION.SDK_INT >= 11) {
                z = ApiLevel11.isCtrlPressedFor(this.fKeyEvent);
                isShiftPressed |= ApiLevel11.isCapsLockOnFor(this.fKeyEvent);
            }
            boolean keyEvent = JavaToNativeShim.keyEvent(coronaRuntime, this.fDevice, KeyPhase.from(this.fKeyEvent), this.fKeyEvent.getKeyCode(), isShiftPressed, this.fKeyEvent.isAltPressed(), z);
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            if (coronaActivity != null && !keyEvent && !(this.fKeyEvent instanceof CoronaKeyEvent)) {
                coronaActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
                        if (coronaActivity != null && !coronaActivity.isFinishing()) {
                            coronaActivity.dispatchKeyEvent(new CoronaKeyEvent(RaiseKeyEventTask.this.fKeyEvent));
                        }
                    }
                });
            }
        }
    }
}
