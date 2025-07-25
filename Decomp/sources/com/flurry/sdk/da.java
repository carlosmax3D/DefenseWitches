package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import jp.stargarage.g2metrics.BuildConfig;

public final class da {
    private int a;
    private long b;
    private String c;
    private String d;
    private String e;
    private Throwable f;

    public da(int i, long j, String str, String str2, String str3, Throwable th) {
        this.a = i;
        this.b = j;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = th;
    }

    public int a() {
        return b().length;
    }

    public byte[] b() {
        DataOutputStream dataOutputStream;
        Throwable th;
        byte[] bArr;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeShort(this.a);
                dataOutputStream.writeLong(this.b);
                dataOutputStream.writeUTF(this.c);
                dataOutputStream.writeUTF(this.d);
                dataOutputStream.writeUTF(this.e);
                if (this.f != null) {
                    if (this.c == "uncaught") {
                        dataOutputStream.writeByte(3);
                    } else {
                        dataOutputStream.writeByte(2);
                    }
                    dataOutputStream.writeByte(2);
                    StringBuilder sb = new StringBuilder(BuildConfig.FLAVOR);
                    String property = System.getProperty("line.separator");
                    for (StackTraceElement append : this.f.getStackTrace()) {
                        sb.append(append);
                        sb.append(property);
                    }
                    if (this.f.getCause() != null) {
                        sb.append(property);
                        sb.append("Caused by: ");
                        for (StackTraceElement append2 : this.f.getCause().getStackTrace()) {
                            sb.append(append2);
                            sb.append(property);
                        }
                    }
                    byte[] bytes = sb.toString().getBytes();
                    dataOutputStream.writeInt(bytes.length);
                    dataOutputStream.write(bytes);
                } else {
                    dataOutputStream.writeByte(1);
                    dataOutputStream.writeByte(0);
                }
                dataOutputStream.flush();
                bArr = byteArrayOutputStream.toByteArray();
                fe.a((Closeable) dataOutputStream);
            } catch (IOException e2) {
            }
        } catch (IOException e3) {
            dataOutputStream = null;
            try {
                bArr = new byte[0];
                fe.a((Closeable) dataOutputStream);
                return bArr;
            } catch (Throwable th2) {
                th = th2;
                fe.a((Closeable) dataOutputStream);
                throw th;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            dataOutputStream = null;
            th = th4;
            fe.a((Closeable) dataOutputStream);
            throw th;
        }
        return bArr;
    }

    public String c() {
        return this.c;
    }
}
