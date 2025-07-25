package com.google.android.gms.plus.model.moments;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.Cif;

public final class MomentBuffer extends DataBuffer<Moment> {
    public MomentBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    public Moment get(int i) {
        return new Cif(this.nE, i);
    }
}
