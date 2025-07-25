package com.flurry.sdk;

import com.flurry.sdk.cx;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;

public class dh {
    private static final String b = dh.class.getSimpleName();
    byte[] a;

    public dh(di diVar) throws IOException {
        DataOutputStream dataOutputStream;
        int i;
        DataOutputStream dataOutputStream2 = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeShort(3);
                dataOutputStream.writeUTF(diVar.a());
                dataOutputStream.writeLong(diVar.b());
                dataOutputStream.writeLong(diVar.c());
                dataOutputStream.writeLong(diVar.d());
                Map<String, String> e = diVar.e();
                if (e == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(e.size());
                    for (Map.Entry next : e.entrySet()) {
                        dataOutputStream.writeUTF((String) next.getKey());
                        dataOutputStream.writeUTF((String) next.getValue());
                        dataOutputStream.writeByte(0);
                    }
                }
                dataOutputStream.writeUTF(diVar.f());
                dataOutputStream.writeUTF(diVar.g());
                dataOutputStream.writeByte(diVar.h());
                dataOutputStream.writeUTF(diVar.i());
                if (diVar.j() == null) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeDouble(a(diVar.j().getLatitude()));
                    dataOutputStream.writeDouble(a(diVar.j().getLongitude()));
                    dataOutputStream.writeFloat(diVar.j().getAccuracy());
                }
                dataOutputStream.writeInt(diVar.k());
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(diVar.l());
                if (diVar.m() == null) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeLong(diVar.m().longValue());
                }
                Map<String, cx.a> n = diVar.n();
                if (n == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(n.size());
                    for (Map.Entry next2 : n.entrySet()) {
                        dataOutputStream.writeUTF((String) next2.getKey());
                        dataOutputStream.writeInt(((cx.a) next2.getValue()).a);
                    }
                }
                List<db> o = diVar.o();
                if (o == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(o.size());
                    for (db e2 : o) {
                        dataOutputStream.write(e2.e());
                    }
                }
                dataOutputStream.writeBoolean(diVar.p());
                List<da> r = diVar.r();
                if (r != null) {
                    int i2 = 0;
                    int i3 = 0;
                    int i4 = 0;
                    while (true) {
                        if (i2 >= r.size()) {
                            i = i4;
                            break;
                        }
                        i3 += r.get(i2).a();
                        if (i3 > 160000) {
                            eo.a(5, b, "Error Log size exceeded. No more event details logged.");
                            i = i4;
                            break;
                        }
                        i4++;
                        i2++;
                    }
                } else {
                    i = 0;
                }
                dataOutputStream.writeInt(diVar.q());
                dataOutputStream.writeShort(i);
                for (int i5 = 0; i5 < i; i5++) {
                    dataOutputStream.write(r.get(i5).b());
                }
                dataOutputStream.writeInt(-1);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(0);
                this.a = byteArrayOutputStream.toByteArray();
                fe.a((Closeable) dataOutputStream);
            } catch (IOException e3) {
                e = e3;
                dataOutputStream2 = dataOutputStream;
                try {
                    eo.a(6, b, BuildConfig.FLAVOR, e);
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    dataOutputStream = dataOutputStream2;
                    fe.a((Closeable) dataOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fe.a((Closeable) dataOutputStream);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            eo.a(6, b, BuildConfig.FLAVOR, e);
            throw e;
        } catch (Throwable th3) {
            th = th3;
            dataOutputStream = null;
            fe.a((Closeable) dataOutputStream);
            throw th;
        }
    }

    public dh(byte[] bArr) {
        this.a = bArr;
    }

    /* access modifiers changed from: package-private */
    public double a(double d) {
        return ((double) Math.round(d * 1000.0d)) / 1000.0d;
    }

    public byte[] a() {
        return this.a;
    }
}
