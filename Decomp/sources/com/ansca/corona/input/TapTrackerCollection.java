package com.ansca.corona.input;

import java.util.ArrayList;
import java.util.Iterator;

public class TapTrackerCollection implements Iterable<TapTracker>, Cloneable {
    private ArrayList<TapTracker> fCollection = new ArrayList<>();

    public void add(TapTracker tapTracker) {
        if (tapTracker != null && this.fCollection.indexOf(tapTracker) < 0) {
            this.fCollection.add(tapTracker);
        }
    }

    public void clear() {
        this.fCollection.clear();
    }

    public TapTrackerCollection clone() {
        TapTrackerCollection tapTrackerCollection = new TapTrackerCollection();
        Iterator<TapTracker> it = this.fCollection.iterator();
        while (it.hasNext()) {
            tapTrackerCollection.fCollection.add(it.next().clone());
        }
        return tapTrackerCollection;
    }

    public boolean contains(TapTracker tapTracker) {
        if (tapTracker == null) {
            return false;
        }
        return this.fCollection.contains(tapTracker);
    }

    public boolean containsDeviceId(int i) {
        return getByDeviceId(i) != null;
    }

    public TapTracker getByDeviceId(int i) {
        Iterator<TapTracker> it = this.fCollection.iterator();
        while (it.hasNext()) {
            TapTracker next = it.next();
            if (next != null && next.getDeviceId() == i) {
                return next;
            }
        }
        return null;
    }

    public TapTracker getByIndex(int i) {
        if (i < 0 || i >= this.fCollection.size()) {
            return null;
        }
        return this.fCollection.get(i);
    }

    public int indexOf(TapTracker tapTracker) {
        if (tapTracker == null) {
            return -1;
        }
        return this.fCollection.indexOf(tapTracker);
    }

    public Iterator<TapTracker> iterator() {
        return this.fCollection.iterator();
    }

    public boolean remove(TapTracker tapTracker) {
        if (tapTracker == null) {
            return false;
        }
        return this.fCollection.remove(tapTracker);
    }

    public boolean removeByDeviceId(int i) {
        boolean z = false;
        while (remove(getByDeviceId(i))) {
            z = true;
        }
        return z;
    }

    public int size() {
        return this.fCollection.size();
    }
}
