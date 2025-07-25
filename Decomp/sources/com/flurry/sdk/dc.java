package com.flurry.sdk;

import android.os.Build;
import com.flurry.android.FlurryAgent;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.DigestOutputStream;
import java.util.List;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;

public class dc {
    private static final String a = dc.class.getSimpleName();
    private byte[] b = null;

    public dc(String str, String str2, boolean z, boolean z2, long j, long j2, List<dh> list, Map<dr, ByteBuffer> map, Map<String, List<String>> map2, Map<String, List<String>> map3, Map<String, Map<String, String>> map4, long j3) throws IOException {
        DataOutputStream dataOutputStream;
        byte[] bArr;
        DataOutputStream dataOutputStream2 = null;
        try {
            ed edVar = new ed();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, edVar);
            dataOutputStream = new DataOutputStream(digestOutputStream);
            try {
                dataOutputStream.writeShort(29);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeLong(0);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(3);
                dataOutputStream.writeShort(FlurryAgent.getAgentVersion());
                dataOutputStream.writeLong(j3);
                dataOutputStream.writeUTF(str);
                dataOutputStream.writeUTF(str2);
                dataOutputStream.writeShort(map.size());
                for (Map.Entry next : map.entrySet()) {
                    dataOutputStream.writeShort(((dr) next.getKey()).d);
                    byte[] array = ((ByteBuffer) next.getValue()).array();
                    dataOutputStream.writeShort(array.length);
                    dataOutputStream.write(array);
                }
                dataOutputStream.writeByte(0);
                dataOutputStream.writeBoolean(z);
                dataOutputStream.writeBoolean(z2);
                dataOutputStream.writeLong(j);
                dataOutputStream.writeLong(j2);
                dataOutputStream.writeShort(6);
                dataOutputStream.writeUTF("device.model");
                dataOutputStream.writeUTF(Build.MODEL);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.brand");
                dataOutputStream.writeUTF(Build.BRAND);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.id");
                dataOutputStream.writeUTF(Build.ID);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("version.release");
                dataOutputStream.writeUTF(Build.VERSION.RELEASE);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.device");
                dataOutputStream.writeUTF(Build.DEVICE);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.product");
                dataOutputStream.writeUTF(Build.PRODUCT);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeShort(map2 != null ? map2.keySet().size() : 0);
                if (map2 != null) {
                    eo.a(3, a, "sending referrer values because it exists");
                    for (Map.Entry next2 : map2.entrySet()) {
                        eo.a(3, a, "Referrer Entry:  " + ((String) next2.getKey()) + "=" + next2.getValue());
                        dataOutputStream.writeUTF((String) next2.getKey());
                        eo.a(3, a, "referrer key is :" + ((String) next2.getKey()));
                        dataOutputStream.writeShort(((List) next2.getValue()).size());
                        for (String str3 : (List) next2.getValue()) {
                            dataOutputStream.writeUTF(str3);
                            eo.a(3, a, "referrer value is :" + str3);
                        }
                    }
                }
                dataOutputStream.writeBoolean(false);
                int size = map3 != null ? map3.keySet().size() : 0;
                eo.a(3, a, "optionsMapSize is:  " + size);
                dataOutputStream.writeShort(size);
                if (map3 != null) {
                    eo.a(3, a, "sending launch options");
                    for (Map.Entry next3 : map3.entrySet()) {
                        eo.a(3, a, "Launch Options Key:  " + ((String) next3.getKey()));
                        dataOutputStream.writeUTF((String) next3.getKey());
                        dataOutputStream.writeShort(((List) next3.getValue()).size());
                        for (String str4 : (List) next3.getValue()) {
                            dataOutputStream.writeUTF(str4);
                            eo.a(3, a, "Launch Options value is :" + str4);
                        }
                    }
                }
                int size2 = map4 != null ? map4.keySet().size() : 0;
                eo.a(3, a, "numOriginAttributions is:  " + size);
                dataOutputStream.writeShort(size2);
                if (map4 != null) {
                    for (Map.Entry next4 : map4.entrySet()) {
                        eo.a(3, a, "Origin Atttribute Key:  " + ((String) next4.getKey()));
                        dataOutputStream.writeUTF((String) next4.getKey());
                        dataOutputStream.writeShort(((Map) next4.getValue()).size());
                        eo.a(3, a, "Origin Attribute Map Size for " + ((String) next4.getKey()) + ":  " + ((Map) next4.getValue()).size());
                        for (Map.Entry entry : ((Map) next4.getValue()).entrySet()) {
                            eo.a(3, a, "Origin Atttribute for " + ((String) next4.getKey()) + ":  " + ((String) entry.getKey()) + ":" + ((String) entry.getValue()));
                            dataOutputStream.writeUTF(entry.getKey() != null ? (String) entry.getKey() : BuildConfig.FLAVOR);
                            dataOutputStream.writeUTF(entry.getValue() != null ? (String) entry.getValue() : BuildConfig.FLAVOR);
                        }
                    }
                }
                int size3 = list.size();
                dataOutputStream.writeShort(size3);
                for (int i = 0; i < size3; i++) {
                    dataOutputStream.write(list.get(i).a());
                }
                dataOutputStream.writeShort(0);
                digestOutputStream.on(false);
                dataOutputStream.write(edVar.a());
                dataOutputStream.close();
                bArr = byteArrayOutputStream.toByteArray();
                fe.a((Closeable) dataOutputStream);
            } catch (Throwable th) {
                th = th;
                fe.a((Closeable) dataOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            dataOutputStream = null;
            fe.a((Closeable) dataOutputStream);
            throw th;
        }
        this.b = bArr;
    }

    public byte[] a() {
        return this.b;
    }
}
