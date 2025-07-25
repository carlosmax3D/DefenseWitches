package com.flurry.sdk;

import android.os.Looper;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;
import jp.stargarage.g2metrics.BuildConfig;

public class df {
    private static final String d = df.class.getSimpleName();
    String a;
    long b;
    int c;
    private File e;

    public df() {
        this.a = null;
        this.b = -1;
        this.c = -1;
        this.e = null;
        this.a = UUID.randomUUID().toString();
        this.e = Cdo.a().b().getFileStreamPath(".flurrydatasenderblock." + this.a);
    }

    public df(String str) {
        this.a = null;
        this.b = -1;
        this.c = -1;
        this.e = null;
        this.a = str;
        this.e = Cdo.a().b().getFileStreamPath(".flurrydatasenderblock." + this.a);
    }

    public String a() {
        return this.a;
    }

    public boolean a(byte[] bArr) {
        DataOutputStream dataOutputStream;
        boolean z = false;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            eo.a(6, d, "setData(byte[]) running on the MAIN thread!");
        }
        eo.a(4, d, "Writing FlurryDataSenderBlockInfo: " + this.e.getAbsolutePath());
        try {
            if (!fd.a(this.e)) {
                fe.a((Closeable) null);
            } else {
                dataOutputStream = new DataOutputStream(new FileOutputStream(this.e));
                try {
                    int length = bArr.length;
                    dataOutputStream.writeShort(length);
                    dataOutputStream.write(bArr);
                    dataOutputStream.writeShort(0);
                    z = true;
                    this.c = length;
                    this.b = System.currentTimeMillis();
                    fe.a((Closeable) dataOutputStream);
                } catch (Throwable th) {
                    th = th;
                    try {
                        eo.a(6, d, BuildConfig.FLAVOR, th);
                        fe.a((Closeable) dataOutputStream);
                        return z;
                    } catch (Throwable th2) {
                        th = th2;
                        fe.a((Closeable) dataOutputStream);
                        throw th;
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
            dataOutputStream = null;
            fe.a((Closeable) dataOutputStream);
            throw th;
        }
        return z;
    }

    public byte[] b() {
        DataInputStream dataInputStream;
        Throwable th;
        byte[] bArr = null;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            eo.a(6, d, "getData() running on the MAIN thread!");
        }
        if (this.e.exists()) {
            eo.a(4, d, "Reading FlurryDataSenderBlockInfo: " + this.e.getAbsolutePath());
            try {
                dataInputStream = new DataInputStream(new FileInputStream(this.e));
                try {
                    int readUnsignedShort = dataInputStream.readUnsignedShort();
                    if (readUnsignedShort == 0) {
                        fe.a((Closeable) dataInputStream);
                    } else {
                        bArr = new byte[readUnsignedShort];
                        dataInputStream.readFully(bArr);
                        if (dataInputStream.readUnsignedShort() == 0) {
                        }
                        fe.a((Closeable) dataInputStream);
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                dataInputStream = null;
                th = th3;
                fe.a((Closeable) dataInputStream);
                throw th;
            }
        } else {
            eo.a(4, d, "Agent cache file doesn't exist.");
        }
        return bArr;
    }

    public boolean c() {
        if (this.e.exists()) {
            if (this.e.delete()) {
                eo.a(4, d, "Deleted persistence file");
                this.b = -1;
                this.c = -1;
                return true;
            }
            eo.a(6, d, "Cannot delete persistence file");
        }
        return false;
    }
}
