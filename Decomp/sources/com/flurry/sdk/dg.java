package com.flurry.sdk;

import android.os.Looper;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import jp.stargarage.g2metrics.BuildConfig;

public class dg {
    static final Integer a = 50;
    private static final String d = dg.class.getSimpleName();
    String b;
    LinkedHashMap<String, List<String>> c;

    public dg(String str) {
        a(str);
    }

    private synchronized boolean a(File file) {
        boolean z;
        z = false;
        if (file != null) {
            if (file.exists()) {
                eo.a(4, d, "Trying to delete persistence file : " + file.getAbsolutePath());
                z = file.delete();
                if (z) {
                    eo.a(4, d, "Deleted persistence file");
                } else {
                    eo.a(6, d, "Cannot delete persistence file");
                }
            }
        }
        return z;
    }

    private synchronized boolean a(String str, List<String> list) {
        DataOutputStream dataOutputStream;
        boolean z;
        boolean z2 = false;
        synchronized (this) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                eo.a(6, d, "saveToFile(byte[], ID) running on the MAIN thread!");
            }
            File fileStreamPath = Cdo.a().b().getFileStreamPath(".FlurrySenderIndex.info." + str);
            try {
                if (!fd.a(fileStreamPath)) {
                    fe.a((Closeable) null);
                } else {
                    dataOutputStream = new DataOutputStream(new FileOutputStream(fileStreamPath));
                    try {
                        dataOutputStream.writeShort(list.size());
                        for (int i = 0; i < list.size(); i++) {
                            byte[] bytes = list.get(i).getBytes();
                            int length = bytes.length;
                            eo.a(4, d, "write iter " + i + " dataLength = " + length);
                            dataOutputStream.writeShort(length);
                            dataOutputStream.write(bytes);
                        }
                        dataOutputStream.writeShort(0);
                        z = true;
                        fe.a((Closeable) dataOutputStream);
                    } catch (Throwable th) {
                        th = th;
                        try {
                            eo.a(6, d, BuildConfig.FLAVOR, th);
                            fe.a((Closeable) dataOutputStream);
                            z = false;
                            z2 = z;
                            return z2;
                        } catch (Throwable th2) {
                            th = th2;
                            fe.a((Closeable) dataOutputStream);
                            throw th;
                        }
                    }
                    z2 = z;
                }
            } catch (Throwable th3) {
                th = th3;
                dataOutputStream = null;
                fe.a((Closeable) dataOutputStream);
                throw th;
            }
        }
        return z2;
    }

    private synchronized void c() {
        LinkedList linkedList = new LinkedList(this.c.keySet());
        b();
        if (!linkedList.isEmpty()) {
            a(this.b, (List<String>) linkedList);
        }
    }

    private synchronized List<String> e(String str) {
        ArrayList arrayList;
        DataInputStream dataInputStream;
        Throwable th;
        ArrayList arrayList2 = null;
        synchronized (this) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                eo.a(6, d, "readFromFile(byte[], ID) running on the MAIN thread!");
            }
            File fileStreamPath = Cdo.a().b().getFileStreamPath(".FlurrySenderIndex.info." + str);
            if (fileStreamPath.exists()) {
                try {
                    dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                    try {
                        int readUnsignedShort = dataInputStream.readUnsignedShort();
                        if (readUnsignedShort == 0) {
                            fe.a((Closeable) dataInputStream);
                        } else {
                            arrayList = new ArrayList(readUnsignedShort);
                            int i = 0;
                            while (i < readUnsignedShort) {
                                try {
                                    int readUnsignedShort2 = dataInputStream.readUnsignedShort();
                                    eo.a(4, d, "read iter " + i + " dataLength = " + readUnsignedShort2);
                                    byte[] bArr = new byte[readUnsignedShort2];
                                    dataInputStream.readFully(bArr);
                                    arrayList.add(new String(bArr));
                                    i++;
                                } catch (Throwable th2) {
                                    th = th2;
                                    try {
                                        eo.a(6, d, "Error when loading persistent file", th);
                                        fe.a((Closeable) dataInputStream);
                                        arrayList2 = arrayList;
                                        return arrayList2;
                                    } catch (Throwable th3) {
                                        th = th3;
                                        fe.a((Closeable) dataInputStream);
                                        throw th;
                                    }
                                }
                            }
                            if (dataInputStream.readUnsignedShort() == 0) {
                            }
                            fe.a((Closeable) dataInputStream);
                            arrayList2 = arrayList;
                        }
                    } catch (Throwable th4) {
                        Throwable th5 = th4;
                        arrayList = null;
                        th = th5;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    dataInputStream = null;
                    fe.a((Closeable) dataInputStream);
                    throw th;
                }
            } else {
                eo.a(5, d, "Agent cache file doesn't exist.");
                arrayList = null;
                arrayList2 = arrayList;
            }
        }
        return arrayList2;
    }

    public List<String> a() {
        return new ArrayList(this.c.keySet());
    }

    public synchronized void a(df dfVar, String str) {
        LinkedList linkedList;
        boolean z = false;
        synchronized (this) {
            eo.a(4, d, "addBlockInfo");
            String a2 = dfVar.a();
            List list = this.c.get(str);
            if (list == null) {
                eo.a(4, d, "New Data Key");
                linkedList = new LinkedList();
                z = true;
            } else {
                linkedList = list;
            }
            linkedList.add(a2);
            if (linkedList.size() > a.intValue()) {
                b((String) linkedList.get(0));
                linkedList.remove(0);
            }
            this.c.put(str, linkedList);
            a(str, (List<String>) linkedList);
            if (z) {
                c();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str) {
        this.c = new LinkedHashMap<>();
        this.b = str + "Main";
        List<String> e = e(this.b);
        if (e != null) {
            for (String next : e) {
                List<String> e2 = e(next);
                if (e2 != null) {
                    this.c.put(next, e2);
                }
            }
        }
    }

    public boolean a(String str, String str2) {
        List list = this.c.get(str2);
        boolean z = false;
        if (list != null) {
            b(str);
            z = list.remove(str);
        }
        if (list == null || list.isEmpty()) {
            d(str2);
        } else {
            this.c.put(str2, list);
            a(str2, (List<String>) list);
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        a(Cdo.a().b().getFileStreamPath(".FlurrySenderIndex.info." + this.b));
    }

    /* access modifiers changed from: package-private */
    public boolean b(String str) {
        return new df(str).c();
    }

    public List<String> c(String str) {
        return this.c.get(str);
    }

    public synchronized boolean d(String str) {
        boolean a2;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            eo.a(6, d, "discardOutdatedBlocksForDataKey(ID) running on the MAIN thread!");
        }
        File fileStreamPath = Cdo.a().b().getFileStreamPath(".FlurrySenderIndex.info." + str);
        List<String> c2 = c(str);
        if (c2 != null) {
            eo.a(4, d, "discardOutdatedBlocksForDataKey: notSentBlocks = " + c2.size());
            for (int i = 0; i < c2.size(); i++) {
                String str2 = c2.get(i);
                b(str2);
                eo.a(4, d, "discardOutdatedBlocksForDataKey: removed block = " + str2);
            }
        }
        this.c.remove(str);
        a2 = a(fileStreamPath);
        c();
        return a2;
    }
}
