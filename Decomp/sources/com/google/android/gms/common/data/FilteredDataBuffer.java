package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.internal.ds;

public abstract class FilteredDataBuffer<T> extends DataBuffer<T> {
    protected final DataBuffer<T> mDataBuffer;

    public FilteredDataBuffer(DataBuffer<T> dataBuffer) {
        super((DataHolder) null);
        ds.d(dataBuffer);
        ds.a(!(dataBuffer instanceof FilteredDataBuffer), "Not possible to have nested FilteredDataBuffers.");
        this.mDataBuffer = dataBuffer;
    }

    public void close() {
        this.mDataBuffer.close();
    }

    /* access modifiers changed from: protected */
    public abstract int computeRealPosition(int i);

    public T get(int i) {
        return this.mDataBuffer.get(computeRealPosition(i));
    }

    public Bundle getMetadata() {
        return this.mDataBuffer.getMetadata();
    }

    public boolean isClosed() {
        return this.mDataBuffer.isClosed();
    }
}
