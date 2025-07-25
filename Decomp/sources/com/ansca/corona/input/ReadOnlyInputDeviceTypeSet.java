package com.ansca.corona.input;

import java.util.Iterator;

public class ReadOnlyInputDeviceTypeSet implements Iterable<InputDeviceType> {
    private InputDeviceTypeSet fCollection;

    public ReadOnlyInputDeviceTypeSet(InputDeviceTypeSet inputDeviceTypeSet) {
        this.fCollection = inputDeviceTypeSet == null ? new InputDeviceTypeSet() : inputDeviceTypeSet;
    }

    public boolean contains(InputDeviceType inputDeviceType) {
        if (inputDeviceType == null) {
            return false;
        }
        return this.fCollection.contains(inputDeviceType);
    }

    public Iterator<InputDeviceType> iterator() {
        return this.fCollection.iterator();
    }

    public int size() {
        return this.fCollection.size();
    }

    public int toAndroidSourcesBitField() {
        return this.fCollection.toAndroidSourcesBitField();
    }
}
