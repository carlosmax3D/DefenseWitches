package com.google.android.gms.common.data;

import java.util.ArrayList;

public abstract class d<T> extends DataBuffer<T> {
    private boolean nZ = false;
    private ArrayList<Integer> oa;

    protected d(DataHolder dataHolder) {
        super(dataHolder);
    }

    private int E(int i) {
        if (i < 0 || i == this.oa.size()) {
            return 0;
        }
        return i == this.oa.size() + -1 ? this.nE.getCount() - this.oa.get(i).intValue() : this.oa.get(i + 1).intValue() - this.oa.get(i).intValue();
    }

    private void by() {
        synchronized (this) {
            if (!this.nZ) {
                int count = this.nE.getCount();
                this.oa = new ArrayList<>();
                if (count > 0) {
                    this.oa.add(0);
                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                    String string = this.nE.getString(primaryDataMarkerColumn, 0, this.nE.C(0));
                    int i = 1;
                    while (i < count) {
                        String string2 = this.nE.getString(primaryDataMarkerColumn, i, this.nE.C(i));
                        if (!string2.equals(string)) {
                            this.oa.add(Integer.valueOf(i));
                        } else {
                            string2 = string;
                        }
                        i++;
                        string = string2;
                    }
                }
                this.nZ = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int D(int i) {
        if (i >= 0 && i < this.oa.size()) {
            return this.oa.get(i).intValue();
        }
        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
    }

    /* access modifiers changed from: protected */
    public abstract T a(int i, int i2);

    public final T get(int i) {
        by();
        return a(D(i), E(i));
    }

    public int getCount() {
        by();
        return this.oa.size();
    }

    /* access modifiers changed from: protected */
    public abstract String getPrimaryDataMarkerColumn();
}
