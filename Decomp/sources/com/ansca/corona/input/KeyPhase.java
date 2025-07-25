package com.ansca.corona.input;

import android.view.KeyEvent;

public class KeyPhase {
    public static final KeyPhase DOWN = new KeyPhase(0, 0, "down");
    public static final KeyPhase UP = new KeyPhase(1, 1, "up");
    private int fAndroidNumericId;
    private int fCoronaNumericId;
    private String fCoronaStringId;

    private KeyPhase(int i, int i2, String str) {
        this.fAndroidNumericId = i;
        this.fCoronaNumericId = i2;
        this.fCoronaStringId = str;
    }

    public static KeyPhase from(KeyEvent keyEvent) {
        if (keyEvent != null) {
            return fromKeyEventAction(keyEvent.getAction());
        }
        throw new NullPointerException();
    }

    public static KeyPhase fromKeyEventAction(int i) {
        return i == 1 ? UP : DOWN;
    }

    public int toCoronaNumericId() {
        return this.fCoronaNumericId;
    }

    public String toCoronaStringId() {
        return this.fCoronaStringId;
    }

    public int toKeyEventAction() {
        return this.fAndroidNumericId;
    }

    public String toString() {
        return this.fCoronaStringId;
    }
}
