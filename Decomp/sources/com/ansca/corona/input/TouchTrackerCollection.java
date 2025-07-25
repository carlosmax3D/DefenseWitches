package com.ansca.corona.input;

import java.util.ArrayList;
import java.util.Iterator;

public class TouchTrackerCollection implements Iterable<TouchTracker>, Cloneable {
    private ArrayList<TouchTracker> fCollection = new ArrayList<>();

    public void add(TouchTracker touchTracker) {
        if (touchTracker != null && this.fCollection.indexOf(touchTracker) < 0) {
            this.fCollection.add(touchTracker);
        }
    }

    public void clear() {
        this.fCollection.clear();
    }

    public TouchTrackerCollection clone() {
        TouchTrackerCollection touchTrackerCollection = new TouchTrackerCollection();
        Iterator<TouchTracker> it = this.fCollection.iterator();
        while (it.hasNext()) {
            touchTrackerCollection.fCollection.add(it.next().clone());
        }
        return touchTrackerCollection;
    }

    public boolean contains(TouchTracker touchTracker) {
        if (touchTracker == null) {
            return false;
        }
        return this.fCollection.contains(touchTracker);
    }

    public boolean containsDeviceAndPointerId(int i, int i2) {
        return getByDeviceAndPointerId(i, i2) != null;
    }

    public boolean containsTouchId(int i) {
        return getByTouchId(i) != null;
    }

    public TouchTracker getByDeviceAndPointerId(int i, int i2) {
        Iterator<TouchTracker> it = this.fCollection.iterator();
        while (it.hasNext()) {
            TouchTracker next = it.next();
            if (next != null && next.getDeviceId() == i && next.getPointerId() == i2) {
                return next;
            }
        }
        return null;
    }

    public TouchTracker getByIndex(int i) {
        if (i < 0 || i >= this.fCollection.size()) {
            return null;
        }
        return this.fCollection.get(i);
    }

    public TouchTracker getByTouchId(int i) {
        Iterator<TouchTracker> it = this.fCollection.iterator();
        while (it.hasNext()) {
            TouchTracker next = it.next();
            if (next != null && next.getTouchId() == i) {
                return next;
            }
        }
        return null;
    }

    public int indexOf(TouchTracker touchTracker) {
        if (touchTracker == null) {
            return -1;
        }
        return this.fCollection.indexOf(touchTracker);
    }

    public Iterator<TouchTracker> iterator() {
        return this.fCollection.iterator();
    }

    public boolean remove(TouchTracker touchTracker) {
        if (touchTracker == null) {
            return false;
        }
        return this.fCollection.remove(touchTracker);
    }

    public boolean removeByDeviceAndPointerId(int i, int i2) {
        boolean z = false;
        while (remove(getByDeviceAndPointerId(i, i2))) {
            z = true;
        }
        return z;
    }

    public boolean removeByDeviceId(int i) {
        boolean z = false;
        for (int size = this.fCollection.size() - 1; size >= 0; size--) {
            if (this.fCollection.get(size).getDeviceId() == i) {
                this.fCollection.remove(size);
                z = true;
            }
        }
        return z;
    }

    public boolean removeByTouchId(int i) {
        boolean z = false;
        while (remove(getByTouchId(i))) {
            z = true;
        }
        return z;
    }

    public int size() {
        return this.fCollection.size();
    }
}
