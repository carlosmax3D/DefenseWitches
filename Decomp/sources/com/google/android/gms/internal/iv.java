package com.google.android.gms.internal;

import android.support.v4.view.MotionEventCompat;
import com.flurry.android.Constants;

public class iv {
    private final byte[] Hm = new byte[256];
    private int Hn;
    private int Ho;

    public iv(byte[] bArr) {
        for (int i = 0; i < 256; i++) {
            this.Hm[i] = (byte) i;
        }
        byte b = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            b = (b + this.Hm[i2] + bArr[i2 % bArr.length]) & Constants.UNKNOWN;
            byte b2 = this.Hm[i2];
            this.Hm[i2] = this.Hm[b];
            this.Hm[b] = b2;
        }
        this.Hn = 0;
        this.Ho = 0;
    }

    public void h(byte[] bArr) {
        int i = this.Hn;
        int i2 = this.Ho;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) & MotionEventCompat.ACTION_MASK;
            i2 = (i2 + this.Hm[i]) & MotionEventCompat.ACTION_MASK;
            byte b = this.Hm[i];
            this.Hm[i] = this.Hm[i2];
            this.Hm[i2] = b;
            bArr[i3] = (byte) (bArr[i3] ^ this.Hm[(this.Hm[i] + this.Hm[i2]) & MotionEventCompat.ACTION_MASK]);
        }
        this.Hn = i;
        this.Ho = i2;
    }
}
