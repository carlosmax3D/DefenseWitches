package com.google.android.gms.common.data;

import com.google.android.gms.internal.eg;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class a<T> implements Iterator<T> {
    private final DataBuffer<T> mDataBuffer;
    private int nF = -1;

    public a(DataBuffer<T> dataBuffer) {
        this.mDataBuffer = (DataBuffer) eg.f(dataBuffer);
    }

    public boolean hasNext() {
        return this.nF < this.mDataBuffer.getCount() + -1;
    }

    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Cannot advance the iterator beyond " + this.nF);
        }
        DataBuffer<T> dataBuffer = this.mDataBuffer;
        int i = this.nF + 1;
        this.nF = i;
        return dataBuffer.get(i);
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
