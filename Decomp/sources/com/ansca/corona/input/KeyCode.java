package com.ansca.corona.input;

import android.os.Build;
import android.view.KeyEvent;
import com.ansca.corona.JavaToNativeShim;

public class KeyCode {
    private int fAndroidNumericId;
    private String fCoronaStringId = null;

    private static class ApiLevel12 {
        private ApiLevel12() {
        }

        public static String getSymbolicNameFromKeyCode(int i) {
            return KeyEvent.keyCodeToString(i);
        }
    }

    private KeyCode(int i) {
        this.fAndroidNumericId = i;
    }

    public static KeyCode from(KeyEvent keyEvent) {
        if (keyEvent != null) {
            return fromAndroidKeyCode(keyEvent.getKeyCode());
        }
        throw new NullPointerException();
    }

    public static KeyCode fromAndroidKeyCode(int i) {
        return new KeyCode(i);
    }

    public int toAndroidKeyCode() {
        return this.fAndroidNumericId;
    }

    public String toAndroidSymbolicName() {
        return Build.VERSION.SDK_INT >= 12 ? ApiLevel12.getSymbolicNameFromKeyCode(this.fAndroidNumericId) : (this.fAndroidNumericId <= 0 || this.fAndroidNumericId > KeyEvent.getMaxKeyCode()) ? "KEYCODE_UNKNOWN" : Integer.toString(this.fAndroidNumericId);
    }

    public String toCoronaStringId() {
        if (this.fCoronaStringId == null) {
            this.fCoronaStringId = JavaToNativeShim.getKeyNameFromKeyCode(this.fAndroidNumericId);
            if (this.fCoronaStringId == null) {
                this.fCoronaStringId = "unknown";
            }
        }
        return this.fCoronaStringId;
    }

    public String toString() {
        return toCoronaStringId();
    }
}
