package com.ansca.corona.input;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class InputDeviceTypeSet implements Iterable<InputDeviceType>, Cloneable {
    private LinkedHashSet<InputDeviceType> fCollection = new LinkedHashSet<>();

    public void add(InputDeviceType inputDeviceType) {
        if (inputDeviceType != null) {
            this.fCollection.add(inputDeviceType);
        }
    }

    public void addAll(Iterable<InputDeviceType> iterable) {
        if (iterable != null) {
            for (InputDeviceType add : iterable) {
                add(add);
            }
        }
    }

    public void clear() {
        this.fCollection.clear();
    }

    public InputDeviceTypeSet clone() {
        try {
            return (InputDeviceTypeSet) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean contains(InputDeviceType inputDeviceType) {
        if (inputDeviceType == null) {
            return false;
        }
        return this.fCollection.contains(inputDeviceType);
    }

    public boolean equals(InputDeviceTypeSet inputDeviceTypeSet) {
        if (inputDeviceTypeSet == null) {
            return false;
        }
        return this.fCollection.equals(inputDeviceTypeSet.fCollection);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof InputDeviceTypeSet)) {
            return false;
        }
        return equals((InputDeviceTypeSet) obj);
    }

    public Iterator<InputDeviceType> iterator() {
        return this.fCollection.iterator();
    }

    public boolean remove(InputDeviceType inputDeviceType) {
        if (inputDeviceType == null) {
            return false;
        }
        return this.fCollection.remove(inputDeviceType);
    }

    public boolean removeAll(Iterable<InputDeviceType> iterable) {
        if (iterable == null) {
            return false;
        }
        boolean z = false;
        for (InputDeviceType remove : iterable) {
            z |= remove(remove);
        }
        return z;
    }

    public int size() {
        return this.fCollection.size();
    }

    public int toAndroidSourcesBitField() {
        int i = 0;
        Iterator it = this.fCollection.iterator();
        while (it.hasNext()) {
            i |= ((InputDeviceType) it.next()).toAndroidSourceId();
        }
        return i;
    }
}
