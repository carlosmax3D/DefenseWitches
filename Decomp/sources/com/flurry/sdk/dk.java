package com.flurry.sdk;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class dk {
    private static final String a = dk.class.getSimpleName();
    private boolean b;
    private List<dh> c;
    private long d = -1;

    private static String a(String str) {
        if (str == null || str.length() <= 4) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length() - 4; i++) {
            sb.append('*');
        }
        sb.append(str.substring(str.length() - 4));
        return sb.toString();
    }

    private boolean a(String str, DataInputStream dataInputStream) throws IOException {
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        eo.a(3, a, "File version: " + readUnsignedShort);
        if (readUnsignedShort > 2) {
            eo.a(6, a, "Unknown agent file version: " + readUnsignedShort);
            throw new IOException("Unknown agent file version: " + readUnsignedShort);
        } else if (readUnsignedShort >= 2) {
            String readUTF = dataInputStream.readUTF();
            eo.a(3, a, "Loading API key: " + a(str));
            if (readUTF.equals(str)) {
                ArrayList arrayList = new ArrayList();
                dataInputStream.readUTF();
                boolean readBoolean = dataInputStream.readBoolean();
                long readLong = dataInputStream.readLong();
                eo.a(3, a, "Loading session reports");
                int i = 0;
                while (true) {
                    int readUnsignedShort2 = dataInputStream.readUnsignedShort();
                    if (readUnsignedShort2 == 0) {
                        eo.a(3, a, "Persistent file loaded");
                        a(readBoolean);
                        a(readLong);
                        a((List<dh>) arrayList);
                        return true;
                    }
                    byte[] bArr = new byte[readUnsignedShort2];
                    dataInputStream.readFully(bArr);
                    arrayList.add(0, new dh(bArr));
                    i++;
                    eo.a(3, a, "Session report added: " + i);
                }
            } else {
                eo.a(3, a, "Api keys do not match, old: " + a(str) + ", new: " + a(readUTF));
                return false;
            }
        } else {
            eo.a(5, a, "Deleting old file version: " + readUnsignedShort);
            return false;
        }
    }

    public void a(long j) {
        this.d = j;
    }

    public void a(DataOutputStream dataOutputStream, String str, String str2) throws IOException {
        try {
            dataOutputStream.writeShort(46586);
            dataOutputStream.writeShort(2);
            dataOutputStream.writeUTF(str);
            dataOutputStream.writeUTF(str2);
            dataOutputStream.writeBoolean(this.b);
            dataOutputStream.writeLong(this.d);
            int size = this.c.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                byte[] a2 = this.c.get(size).a();
                int length = a2.length;
                if (length + 2 + dataOutputStream.size() > 50000) {
                    eo.a(6, a, "discarded sessions: " + size);
                    break;
                }
                dataOutputStream.writeShort(length);
                dataOutputStream.write(a2);
                size--;
            }
            dataOutputStream.writeShort(0);
            fe.a((Closeable) dataOutputStream);
        } catch (Throwable th) {
            fe.a((Closeable) dataOutputStream);
            throw th;
        }
    }

    public void a(List<dh> list) {
        this.c = list;
    }

    public void a(boolean z) {
        this.b = z;
    }

    public boolean a() {
        return this.b;
    }

    public boolean a(DataInputStream dataInputStream, String str) throws IOException {
        boolean z = false;
        try {
            int readUnsignedShort = dataInputStream.readUnsignedShort();
            eo.a(4, a, "Magic: " + readUnsignedShort);
            if (readUnsignedShort == 46586) {
                z = a(str, dataInputStream);
            } else {
                eo.a(3, a, "Unexpected file type");
            }
            fe.a((Closeable) dataInputStream);
            return z;
        } catch (Throwable th) {
            fe.a((Closeable) dataInputStream);
            throw th;
        }
    }

    public List<dh> b() {
        return this.c;
    }

    public long c() {
        return this.d;
    }
}
