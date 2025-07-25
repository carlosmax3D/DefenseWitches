package com.google.android.gms.internal;

import java.io.IOException;

class m implements k {
    private ix dR;
    private byte[] dS;
    private final int dT;

    public m(int i) {
        this.dT = i;
        reset();
    }

    public void b(int i, long j) throws IOException {
        this.dR.b(i, j);
    }

    public void b(int i, String str) throws IOException {
        this.dR.b(i, str);
    }

    public byte[] h() throws IOException {
        int ge = this.dR.ge();
        if (ge < 0) {
            throw new IOException();
        } else if (ge == 0) {
            return this.dS;
        } else {
            byte[] bArr = new byte[(this.dS.length - ge)];
            System.arraycopy(this.dS, 0, bArr, 0, bArr.length);
            return bArr;
        }
    }

    public void reset() {
        this.dS = new byte[this.dT];
        this.dR = ix.i(this.dS);
    }
}
