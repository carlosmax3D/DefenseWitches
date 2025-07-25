package com.ansca.corona.input;

import java.util.Iterator;

public class ReadOnlyAxisInfoCollection implements Iterable<AxisInfo> {
    private AxisInfoCollection fCollection;

    public ReadOnlyAxisInfoCollection(AxisInfoCollection axisInfoCollection) {
        this.fCollection = axisInfoCollection == null ? new AxisInfoCollection() : axisInfoCollection;
    }

    public boolean contains(AxisInfo axisInfo) {
        return this.fCollection.contains(axisInfo);
    }

    public AxisInfo getByIndex(int i) {
        return this.fCollection.getByIndex(i);
    }

    public int indexOf(AxisInfo axisInfo) {
        return this.fCollection.indexOf(axisInfo);
    }

    public Iterator<AxisInfo> iterator() {
        return this.fCollection.iterator();
    }

    public int size() {
        return this.fCollection.size();
    }
}
