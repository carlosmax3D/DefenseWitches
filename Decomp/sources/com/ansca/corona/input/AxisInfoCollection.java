package com.ansca.corona.input;

import java.util.ArrayList;
import java.util.Iterator;

public class AxisInfoCollection implements Iterable<AxisInfo> {
    private ArrayList<AxisInfo> fCollection = new ArrayList<>();

    public void add(AxisInfo axisInfo) {
        if (axisInfo != null && this.fCollection.indexOf(axisInfo) < 0) {
            this.fCollection.add(axisInfo);
        }
    }

    public void addAll(Iterable<AxisInfo> iterable) {
        if (iterable != null) {
            for (AxisInfo add : iterable) {
                add(add);
            }
        }
    }

    public void clear() {
        this.fCollection.clear();
    }

    public boolean contains(AxisInfo axisInfo) {
        if (axisInfo == null) {
            return false;
        }
        return this.fCollection.contains(axisInfo);
    }

    public AxisInfo getByIndex(int i) {
        if (i < 0 || i >= this.fCollection.size()) {
            return null;
        }
        return this.fCollection.get(i);
    }

    public int indexOf(AxisInfo axisInfo) {
        if (axisInfo == null) {
            return -1;
        }
        return this.fCollection.indexOf(axisInfo);
    }

    public Iterator<AxisInfo> iterator() {
        return this.fCollection.iterator();
    }

    public boolean remove(AxisInfo axisInfo) {
        if (axisInfo == null) {
            return false;
        }
        return this.fCollection.remove(axisInfo);
    }

    public int size() {
        return this.fCollection.size();
    }
}
