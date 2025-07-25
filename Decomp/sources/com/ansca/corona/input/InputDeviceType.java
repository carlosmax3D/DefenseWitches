package com.ansca.corona.input;

import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.facebook.internal.NativeProtocol;
import java.lang.reflect.Field;
import java.util.Iterator;

public class InputDeviceType {
    public static final InputDeviceType DPAD = new InputDeviceType(513, 9, "directionalPad");
    public static final InputDeviceType GAMEPAD = new InputDeviceType(1025, 8, "gamepad");
    public static final InputDeviceType JOYSTICK = new InputDeviceType(16777232, 7, "joystick");
    public static final InputDeviceType KEYBOARD = new InputDeviceType(257, 1, "keyboard");
    public static final InputDeviceType MOUSE = new InputDeviceType(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE, 2, "mouse");
    public static final InputDeviceType STYLUS = new InputDeviceType(16386, 3, "stylus");
    public static final InputDeviceType TOUCHPAD = new InputDeviceType(1048584, 5, "touchpad");
    public static final InputDeviceType TOUCHSCREEN = new InputDeviceType(4098, 6, "touchscreen");
    public static final InputDeviceType TRACKBALL = new InputDeviceType(NativeProtocol.MESSAGE_GET_INSTALL_DATA_REQUEST, 4, "trackball");
    public static final InputDeviceType UNKNOWN = new InputDeviceType(0, 0, "unknown");
    private static ReadOnlyInputDeviceTypeSet sTypeCollection;
    private int fAndroidSourceId;
    private int fCoronaIntegerId;
    private String fCoronaStringId;

    private static class ApiLevel9 {
        private ApiLevel9() {
        }

        public static int getSourceIdFrom(InputEvent inputEvent) {
            if (inputEvent != null) {
                return inputEvent.getSource();
            }
            throw new NullPointerException();
        }
    }

    static {
        InputDeviceType inputDeviceType;
        sTypeCollection = null;
        InputDeviceTypeSet inputDeviceTypeSet = new InputDeviceTypeSet();
        try {
            for (Field field : InputDeviceType.class.getDeclaredFields()) {
                if (field.getType().equals(InputDeviceType.class) && (inputDeviceType = (InputDeviceType) field.get((Object) null)) != null) {
                    inputDeviceTypeSet.add(inputDeviceType);
                }
            }
        } catch (Exception e) {
        }
        sTypeCollection = new ReadOnlyInputDeviceTypeSet(inputDeviceTypeSet);
    }

    private InputDeviceType(int i, int i2, String str) {
        this.fAndroidSourceId = i;
        this.fCoronaIntegerId = i2;
        this.fCoronaStringId = str;
    }

    public static InputDeviceTypeSet collectionFromAndroidSourcesBitField(int i) {
        InputDeviceTypeSet inputDeviceTypeSet = new InputDeviceTypeSet();
        Iterator<InputDeviceType> it = sTypeCollection.iterator();
        while (it.hasNext()) {
            InputDeviceType next = it.next();
            if (next != UNKNOWN && (next.toAndroidSourceId() & i) == next.toAndroidSourceId()) {
                inputDeviceTypeSet.add(next);
            }
        }
        if (inputDeviceTypeSet.size() <= 0) {
            inputDeviceTypeSet.add(UNKNOWN);
        }
        return inputDeviceTypeSet;
    }

    public static InputDeviceType from(KeyEvent keyEvent) {
        if (keyEvent == null) {
            return null;
        }
        return Build.VERSION.SDK_INT >= 9 ? fromAndroidSourceId(ApiLevel9.getSourceIdFrom(keyEvent)) : KEYBOARD;
    }

    public static InputDeviceType from(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return null;
        }
        return Build.VERSION.SDK_INT >= 9 ? fromAndroidSourceId(ApiLevel9.getSourceIdFrom(motionEvent)) : TOUCHSCREEN;
    }

    public static InputDeviceType fromAndroidSourceId(int i) {
        InputDeviceType inputDeviceType = UNKNOWN;
        Iterator<InputDeviceType> it = sTypeCollection.iterator();
        while (it.hasNext()) {
            InputDeviceType next = it.next();
            if (next != UNKNOWN && (next.toAndroidSourceId() & i) == next.toAndroidSourceId() && next.toAndroidSourceId() > inputDeviceType.toAndroidSourceId()) {
                inputDeviceType = next;
            }
        }
        return inputDeviceType;
    }

    public static ReadOnlyInputDeviceTypeSet getCollection() {
        return sTypeCollection;
    }

    public int hashCode() {
        return this.fAndroidSourceId;
    }

    public int toAndroidSourceId() {
        return this.fAndroidSourceId;
    }

    public int toCoronaIntegerId() {
        return this.fCoronaIntegerId;
    }

    public String toCoronaStringId() {
        return this.fCoronaStringId;
    }

    public String toString() {
        return this.fCoronaStringId;
    }
}
