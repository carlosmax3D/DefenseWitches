package com.google.android.gms.appstate;

import com.google.android.gms.common.data.DataHolder;

public final class b extends com.google.android.gms.common.data.b implements AppState {
    b(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    /* renamed from: aK */
    public AppState freeze() {
        return new a(this);
    }

    public boolean equals(Object obj) {
        return a.a(this, obj);
    }

    public byte[] getConflictData() {
        return getByteArray("conflict_data");
    }

    public String getConflictVersion() {
        return getString("conflict_version");
    }

    public int getKey() {
        return getInteger("key");
    }

    public byte[] getLocalData() {
        return getByteArray("local_data");
    }

    public String getLocalVersion() {
        return getString("local_version");
    }

    public boolean hasConflict() {
        return !M("conflict_version");
    }

    public int hashCode() {
        return a.a(this);
    }

    public String toString() {
        return a.b(this);
    }
}
