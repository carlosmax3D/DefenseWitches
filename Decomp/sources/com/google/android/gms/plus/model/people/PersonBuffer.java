package com.google.android.gms.plus.model.people;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.c;
import com.google.android.gms.internal.ig;
import com.google.android.gms.internal.ir;

public final class PersonBuffer extends DataBuffer<Person> {
    private final c<ig> FZ;

    public PersonBuffer(DataHolder dataHolder) {
        super(dataHolder);
        if (dataHolder.getMetadata() == null || !dataHolder.getMetadata().getBoolean("com.google.android.gms.plus.IsSafeParcelable", false)) {
            this.FZ = null;
        } else {
            this.FZ = new c<>(dataHolder, ig.CREATOR);
        }
    }

    public Person get(int i) {
        return this.FZ != null ? this.FZ.get(i) : new ir(this.nE, i);
    }
}
