package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public final class ix {
    private final int Hw;
    private final byte[] buffer;
    private int position;

    public static class a extends IOException {
        a(int i, int i2) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
        }
    }

    private ix(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.position = i;
        this.Hw = i + i2;
    }

    public static int aD(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            return bytes.length + bR(bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported.");
        }
    }

    public static ix b(byte[] bArr, int i, int i2) {
        return new ix(bArr, i, i2);
    }

    public static int bN(int i) {
        if (i >= 0) {
            return bR(i);
        }
        return 10;
    }

    public static int bP(int i) {
        return bR(jb.g(i, 0));
    }

    public static int bR(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (-268435456 & i) == 0 ? 4 : 5;
    }

    public static int d(int i, long j) {
        return bP(i) + q(j);
    }

    public static int e(int i, int i2) {
        return bP(i) + bN(i2);
    }

    public static int e(int i, String str) {
        return bP(i) + aD(str);
    }

    public static ix i(byte[] bArr) {
        return b(bArr, 0, bArr.length);
    }

    public static int q(long j) {
        return s(t(j));
    }

    public static int s(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if ((-16384 & j) == 0) {
            return 2;
        }
        if ((-2097152 & j) == 0) {
            return 3;
        }
        if ((-268435456 & j) == 0) {
            return 4;
        }
        if ((-34359738368L & j) == 0) {
            return 5;
        }
        if ((-4398046511104L & j) == 0) {
            return 6;
        }
        if ((-562949953421312L & j) == 0) {
            return 7;
        }
        if ((-72057594037927936L & j) == 0) {
            return 8;
        }
        return (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    public static long t(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public void aC(String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        bQ(bytes.length);
        j(bytes);
    }

    public void b(byte b) throws IOException {
        if (this.position == this.Hw) {
            throw new a(this.position, this.Hw);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = b;
    }

    public void b(int i, long j) throws IOException {
        f(i, 0);
        o(j);
    }

    public void b(int i, String str) throws IOException {
        f(i, 2);
        aC(str);
    }

    public void bM(int i) throws IOException {
        if (i >= 0) {
            bQ(i);
        } else {
            r((long) i);
        }
    }

    public void bO(int i) throws IOException {
        b((byte) i);
    }

    public void bQ(int i) throws IOException {
        while ((i & -128) != 0) {
            bO((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            i >>>= 7;
        }
        bO(i);
    }

    public void c(int i, long j) throws IOException {
        f(i, 0);
        p(j);
    }

    public void c(byte[] bArr, int i, int i2) throws IOException {
        if (this.Hw - this.position >= i2) {
            System.arraycopy(bArr, i, this.buffer, this.position, i2);
            this.position += i2;
            return;
        }
        throw new a(this.position, this.Hw);
    }

    public void d(int i, int i2) throws IOException {
        f(i, 0);
        bM(i2);
    }

    public void f(int i, int i2) throws IOException {
        bQ(jb.g(i, i2));
    }

    public int ge() {
        return this.Hw - this.position;
    }

    public void gf() {
        if (ge() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void j(byte[] bArr) throws IOException {
        c(bArr, 0, bArr.length);
    }

    public void o(long j) throws IOException {
        r(j);
    }

    public void p(long j) throws IOException {
        r(t(j));
    }

    public void r(long j) throws IOException {
        while ((-128 & j) != 0) {
            bO((((int) j) & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            j >>>= 7;
        }
        bO((int) j);
    }
}
