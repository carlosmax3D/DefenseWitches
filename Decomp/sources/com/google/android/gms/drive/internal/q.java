package com.google.android.gms.drive.internal;

import com.google.android.gms.internal.iw;
import com.google.android.gms.internal.ix;
import com.google.android.gms.internal.iy;
import com.google.android.gms.internal.iz;
import com.google.android.gms.internal.jb;
import java.io.IOException;
import jp.stargarage.g2metrics.BuildConfig;

public final class q extends iz {
    public static final q[] rs = new q[0];
    public String rt = BuildConfig.FLAVOR;
    public long ru = -1;
    public long rv = -1;
    private int rw = -1;
    public int versionCode = 1;

    public static q e(byte[] bArr) throws iy {
        return (q) iz.a(new q(), bArr);
    }

    /* renamed from: a */
    public q b(iw iwVar) throws IOException {
        while (true) {
            int fU = iwVar.fU();
            switch (fU) {
                case 0:
                    break;
                case 8:
                    this.versionCode = iwVar.fW();
                    continue;
                case 18:
                    this.rt = iwVar.readString();
                    continue;
                case 24:
                    this.ru = iwVar.fX();
                    continue;
                case 32:
                    this.rv = iwVar.fX();
                    continue;
                default:
                    if (!jb.a(iwVar, fU)) {
                        break;
                    } else {
                        continue;
                    }
            }
        }
        return this;
    }

    public void a(ix ixVar) throws IOException {
        ixVar.d(1, this.versionCode);
        ixVar.b(2, this.rt);
        ixVar.c(3, this.ru);
        ixVar.c(4, this.rv);
    }

    public int cP() {
        int e = 0 + ix.e(1, this.versionCode) + ix.e(2, this.rt) + ix.d(3, this.ru) + ix.d(4, this.rv);
        this.rw = e;
        return e;
    }
}
