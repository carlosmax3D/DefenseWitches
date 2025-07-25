package com.google.android.gms.internal;

import com.flurry.android.Constants;
import java.io.IOException;

public final class iw {
    private int Hp;
    private int Hq;
    private int Hr;
    private int Hs;
    private int Ht = Integer.MAX_VALUE;
    private int Hu = 64;
    private int Hv = 67108864;
    private final byte[] buffer;

    private iw(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.Hp = i;
        this.Hq = i + i2;
        this.Hr = i;
    }

    public static iw a(byte[] bArr, int i, int i2) {
        return new iw(bArr, i, i2);
    }

    public static long n(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    public void bI(int i) throws iy {
        if (this.Hs != i) {
            throw iy.gk();
        }
    }

    public boolean bJ(int i) throws IOException {
        switch (jb.bS(i)) {
            case 0:
                fW();
                return true;
            case 1:
                gb();
                return true;
            case 2:
                bL(fY());
                return true;
            case 3:
                fV();
                bI(jb.g(jb.bT(i), 4));
                return true;
            case 4:
                return false;
            case 5:
                ga();
                return true;
            default:
                throw iy.gl();
        }
    }

    public byte[] bK(int i) throws IOException {
        if (i < 0) {
            throw iy.gh();
        } else if (this.Hr + i > this.Ht) {
            bL(this.Ht - this.Hr);
            throw iy.gg();
        } else if (i <= this.Hq - this.Hr) {
            byte[] bArr = new byte[i];
            System.arraycopy(this.buffer, this.Hr, bArr, 0, i);
            this.Hr += i;
            return bArr;
        } else {
            throw iy.gg();
        }
    }

    public void bL(int i) throws IOException {
        if (i < 0) {
            throw iy.gh();
        } else if (this.Hr + i > this.Ht) {
            bL(this.Ht - this.Hr);
            throw iy.gg();
        } else if (i <= this.Hq - this.Hr) {
            this.Hr += i;
        } else {
            throw iy.gg();
        }
    }

    public int fU() throws IOException {
        if (gc()) {
            this.Hs = 0;
            return 0;
        }
        this.Hs = fY();
        if (this.Hs != 0) {
            return this.Hs;
        }
        throw iy.gj();
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public void fV() throws java.io.IOException {
        /*
            r1 = this;
        L_0x0000:
            int r0 = r1.fU()
            if (r0 == 0) goto L_0x000c
            boolean r0 = r1.bJ(r0)
            if (r0 != 0) goto L_0x0000
        L_0x000c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.iw.fV():void");
    }

    public int fW() throws IOException {
        return fY();
    }

    public long fX() throws IOException {
        return n(fZ());
    }

    public int fY() throws IOException {
        byte gd = gd();
        if (gd >= 0) {
            return gd;
        }
        byte b = gd & Byte.MAX_VALUE;
        byte gd2 = gd();
        if (gd2 >= 0) {
            return b | (gd2 << 7);
        }
        byte b2 = b | ((gd2 & Byte.MAX_VALUE) << 7);
        byte gd3 = gd();
        if (gd3 >= 0) {
            return b2 | (gd3 << 14);
        }
        byte b3 = b2 | ((gd3 & Byte.MAX_VALUE) << 14);
        byte gd4 = gd();
        if (gd4 >= 0) {
            return b3 | (gd4 << 21);
        }
        byte b4 = b3 | ((gd4 & Byte.MAX_VALUE) << 21);
        byte gd5 = gd();
        byte b5 = b4 | (gd5 << 28);
        if (gd5 >= 0) {
            return b5;
        }
        for (int i = 0; i < 5; i++) {
            if (gd() >= 0) {
                return b5;
            }
        }
        throw iy.gi();
    }

    public long fZ() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte gd = gd();
            j |= ((long) (gd & Byte.MAX_VALUE)) << i;
            if ((gd & 128) == 0) {
                return j;
            }
        }
        throw iy.gi();
    }

    public int ga() throws IOException {
        return (gd() & Constants.UNKNOWN) | ((gd() & Constants.UNKNOWN) << 8) | ((gd() & Constants.UNKNOWN) << 16) | ((gd() & Constants.UNKNOWN) << 24);
    }

    public long gb() throws IOException {
        byte gd = gd();
        byte gd2 = gd();
        return ((((long) gd2) & 255) << 8) | (((long) gd) & 255) | ((((long) gd()) & 255) << 16) | ((((long) gd()) & 255) << 24) | ((((long) gd()) & 255) << 32) | ((((long) gd()) & 255) << 40) | ((((long) gd()) & 255) << 48) | ((((long) gd()) & 255) << 56);
    }

    public boolean gc() {
        return this.Hr == this.Hq;
    }

    public byte gd() throws IOException {
        if (this.Hr == this.Hq) {
            throw iy.gg();
        }
        byte[] bArr = this.buffer;
        int i = this.Hr;
        this.Hr = i + 1;
        return bArr[i];
    }

    public String readString() throws IOException {
        int fY = fY();
        if (fY > this.Hq - this.Hr || fY <= 0) {
            return new String(bK(fY), "UTF-8");
        }
        String str = new String(this.buffer, this.Hr, fY, "UTF-8");
        this.Hr = fY + this.Hr;
        return str;
    }
}
