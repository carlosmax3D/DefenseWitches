package com.google.android.gms.internal;

import java.io.IOException;

public abstract class iz {
    protected int rw = -1;

    public static final <T extends iz> T a(T t, byte[] bArr) throws iy {
        return b(t, bArr, 0, bArr.length);
    }

    public static final void a(iz izVar, byte[] bArr, int i, int i2) {
        try {
            ix b = ix.b(bArr, i, i2);
            izVar.a(b);
            b.gf();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final byte[] a(iz izVar) {
        byte[] bArr = new byte[izVar.cP()];
        a(izVar, bArr, 0, bArr.length);
        return bArr;
    }

    public static final <T extends iz> T b(T t, byte[] bArr, int i, int i2) throws iy {
        try {
            iw a = iw.a(bArr, i, i2);
            t.b(a);
            a.bI(0);
            return t;
        } catch (iy e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public abstract void a(ix ixVar) throws IOException;

    public abstract iz b(iw iwVar) throws IOException;

    public int cP() {
        this.rw = 0;
        return 0;
    }

    public String toString() {
        return ja.b(this);
    }
}
