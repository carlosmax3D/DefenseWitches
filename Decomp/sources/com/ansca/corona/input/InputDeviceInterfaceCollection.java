package com.ansca.corona.input;

import java.util.ArrayList;
import java.util.Iterator;

public class InputDeviceInterfaceCollection implements Iterable<InputDeviceInterface>, Cloneable {
    private ArrayList<InputDeviceInterface> fCollection = new ArrayList<>();

    public void add(InputDeviceInterface inputDeviceInterface) {
        if (inputDeviceInterface != null && this.fCollection.indexOf(inputDeviceInterface) < 0) {
            this.fCollection.add(inputDeviceInterface);
        }
    }

    public void addAll(Iterable<InputDeviceInterface> iterable) {
        if (iterable != null) {
            for (InputDeviceInterface add : iterable) {
                add(add);
            }
        }
    }

    public void clear() {
        this.fCollection.clear();
    }

    public InputDeviceInterfaceCollection clone() {
        try {
            return (InputDeviceInterfaceCollection) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean contains(InputDeviceInterface inputDeviceInterface) {
        if (inputDeviceInterface == null) {
            return false;
        }
        return this.fCollection.contains(inputDeviceInterface);
    }

    public boolean containsAndroidDeviceId(int i) {
        Iterator<InputDeviceInterface> it = this.fCollection.iterator();
        while (it.hasNext()) {
            InputDeviceInfo deviceInfo = it.next().getDeviceInfo();
            if (deviceInfo.hasAndroidDeviceId() && deviceInfo.getAndroidDeviceId() == i) {
                return true;
            }
        }
        return false;
    }

    public InputDeviceInterfaceCollection getBy(ConnectionState connectionState) {
        if (connectionState == null) {
            throw new NullPointerException();
        }
        InputDeviceInterfaceCollection inputDeviceInterfaceCollection = new InputDeviceInterfaceCollection();
        Iterator<InputDeviceInterface> it = this.fCollection.iterator();
        while (it.hasNext()) {
            InputDeviceInterface next = it.next();
            if (next.getConnectionState() == connectionState) {
                inputDeviceInterfaceCollection.add(next);
            }
        }
        return inputDeviceInterfaceCollection;
    }

    public InputDeviceInterface getByAndroidDeviceIdAndType(int i, InputDeviceType inputDeviceType) {
        if (inputDeviceType != null) {
            Iterator<InputDeviceInterface> it = this.fCollection.iterator();
            while (it.hasNext()) {
                InputDeviceInterface next = it.next();
                InputDeviceInfo deviceInfo = next.getDeviceInfo();
                if (deviceInfo.hasAndroidDeviceId() && deviceInfo.getAndroidDeviceId() == i && deviceInfo.getInputSources().contains(inputDeviceType)) {
                    return next;
                }
            }
        }
        return null;
    }

    public InputDeviceInterface getByCoronaDeviceId(int i) {
        Iterator<InputDeviceInterface> it = this.fCollection.iterator();
        while (it.hasNext()) {
            InputDeviceInterface next = it.next();
            if (next.getCoronaDeviceId() == i) {
                return next;
            }
        }
        return null;
    }

    public InputDeviceInterface getByIndex(int i) {
        if (i < 0 || i >= this.fCollection.size()) {
            return null;
        }
        return this.fCollection.get(i);
    }

    public InputDeviceInterface getByPermanentStringIdAndType(String str, InputDeviceType inputDeviceType) {
        if (!(str == null || str.length() <= 0 || inputDeviceType == null)) {
            Iterator<InputDeviceInterface> it = this.fCollection.iterator();
            while (it.hasNext()) {
                InputDeviceInterface next = it.next();
                InputDeviceInfo deviceInfo = next.getDeviceInfo();
                if (deviceInfo.hasPermanentStringId() && deviceInfo.getPermanentStringId().equals(str) && deviceInfo.getInputSources().contains(inputDeviceType)) {
                    return next;
                }
            }
        }
        return null;
    }

    public InputDeviceInterface getByPermanentStringIdAndTypeAndDisplayName(String str, InputDeviceType inputDeviceType, String str2) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        if (inputDeviceType == null) {
            return null;
        }
        if (str2 == null || str2.length() <= 0) {
            return null;
        }
        Iterator<InputDeviceInterface> it = this.fCollection.iterator();
        while (it.hasNext()) {
            InputDeviceInterface next = it.next();
            InputDeviceInfo deviceInfo = next.getDeviceInfo();
            if (deviceInfo.hasPermanentStringId() && deviceInfo.getPermanentStringId().equals(str) && deviceInfo.getInputSources().contains(inputDeviceType) && deviceInfo.getDisplayName().equals(str2)) {
                return next;
            }
        }
        return null;
    }

    public InputDeviceInterface getByTypeAndDisplayName(InputDeviceType inputDeviceType, String str) {
        if (inputDeviceType == null) {
            return null;
        }
        if (str == null || str.length() <= 0) {
            return null;
        }
        Iterator<InputDeviceInterface> it = this.fCollection.iterator();
        while (it.hasNext()) {
            InputDeviceInterface next = it.next();
            InputDeviceInfo deviceInfo = next.getDeviceInfo();
            if (deviceInfo.getInputSources().contains(inputDeviceType) && deviceInfo.getDisplayName().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public int indexOf(InputDeviceInterface inputDeviceInterface) {
        if (inputDeviceInterface == null) {
            return -1;
        }
        return this.fCollection.indexOf(inputDeviceInterface);
    }

    public Iterator<InputDeviceInterface> iterator() {
        return this.fCollection.iterator();
    }

    public boolean remove(InputDeviceInterface inputDeviceInterface) {
        if (inputDeviceInterface == null) {
            return false;
        }
        return this.fCollection.remove(inputDeviceInterface);
    }

    public int size() {
        return this.fCollection.size();
    }
}
