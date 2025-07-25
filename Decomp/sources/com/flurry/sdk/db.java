package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public final class db {
    private int a;
    private String b;
    private Map<String, String> c;
    private long d;
    private boolean e;
    private boolean f;
    private long g;

    public db(int i, String str, Map<String, String> map, long j, boolean z) {
        this.a = i;
        this.b = str;
        this.c = map;
        this.d = j;
        this.e = z;
        if (this.e) {
            this.f = false;
        } else {
            this.f = true;
        }
    }

    public void a(long j) {
        this.f = true;
        this.g = j - this.d;
        eo.a(3, "FlurryAgent", "Ended event '" + this.b + "' (" + this.d + ") after " + this.g + "ms");
    }

    public void a(Map<String, String> map) {
        if (this.c == null || this.c.size() == 0) {
            this.c = map;
            return;
        }
        for (Map.Entry next : map.entrySet()) {
            if (this.c.containsKey(next.getKey())) {
                this.c.remove(next.getKey());
                this.c.put(next.getKey(), next.getValue());
            } else {
                this.c.put(next.getKey(), next.getValue());
            }
        }
    }

    public boolean a() {
        return this.e;
    }

    public boolean a(String str) {
        return this.e && this.g == 0 && this.b.equals(str);
    }

    public void b(Map<String, String> map) {
        this.c = map;
    }

    public boolean b() {
        return this.f;
    }

    public Map<String, String> c() {
        return this.c;
    }

    public int d() {
        return e().length;
    }

    public byte[] e() {
        DataOutputStream dataOutputStream;
        DataOutputStream dataOutputStream2;
        Throwable th;
        byte[] bArr;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream2.writeShort(this.a);
                dataOutputStream2.writeUTF(this.b);
                if (this.c == null) {
                    dataOutputStream2.writeShort(0);
                } else {
                    dataOutputStream2.writeShort(this.c.size());
                    for (Map.Entry next : this.c.entrySet()) {
                        dataOutputStream2.writeUTF(fe.a((String) next.getKey()));
                        dataOutputStream2.writeUTF(fe.a((String) next.getValue()));
                    }
                }
                dataOutputStream2.writeLong(this.d);
                dataOutputStream2.writeLong(this.g);
                dataOutputStream2.flush();
                bArr = byteArrayOutputStream.toByteArray();
                fe.a((Closeable) dataOutputStream2);
            } catch (IOException e2) {
                dataOutputStream = dataOutputStream2;
                try {
                    bArr = new byte[0];
                    fe.a((Closeable) dataOutputStream);
                    return bArr;
                } catch (Throwable th2) {
                    th = th2;
                    dataOutputStream2 = dataOutputStream;
                    fe.a((Closeable) dataOutputStream2);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fe.a((Closeable) dataOutputStream2);
                throw th;
            }
        } catch (IOException e3) {
            dataOutputStream = null;
            bArr = new byte[0];
            fe.a((Closeable) dataOutputStream);
            return bArr;
        } catch (Throwable th4) {
            dataOutputStream2 = null;
            th = th4;
            fe.a((Closeable) dataOutputStream2);
            throw th;
        }
        return bArr;
    }
}
