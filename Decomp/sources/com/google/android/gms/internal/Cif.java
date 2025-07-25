package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;

/* renamed from: com.google.android.gms.internal.if  reason: invalid class name */
public final class Cif extends b implements Moment {
    private id Fo;

    public Cif(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    private id fu() {
        synchronized (this) {
            if (this.Fo == null) {
                byte[] byteArray = getByteArray("momentImpl");
                Parcel obtain = Parcel.obtain();
                obtain.unmarshall(byteArray, 0, byteArray.length);
                obtain.setDataPosition(0);
                this.Fo = id.CREATOR.createFromParcel(obtain);
                obtain.recycle();
            }
        }
        return this.Fo;
    }

    /* renamed from: ft */
    public id freeze() {
        return fu();
    }

    public String getId() {
        return fu().getId();
    }

    public ItemScope getResult() {
        return fu().getResult();
    }

    public String getStartDate() {
        return fu().getStartDate();
    }

    public ItemScope getTarget() {
        return fu().getTarget();
    }

    public String getType() {
        return fu().getType();
    }

    public boolean hasId() {
        return fu().hasId();
    }

    public boolean hasResult() {
        return fu().hasId();
    }

    public boolean hasStartDate() {
        return fu().hasStartDate();
    }

    public boolean hasTarget() {
        return fu().hasTarget();
    }

    public boolean hasType() {
        return fu().hasType();
    }
}
