package com.flurry.sdk;

import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import com.tapjoy.TapjoyConstants;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class ea {
    private static final String a = ea.class.getSimpleName();
    private static final Set<String> b = i();
    private static String c;

    public static synchronized String a() {
        String str;
        synchronized (ea.class) {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                throw new IllegalStateException("Must be called from a background thread!");
            }
            if (TextUtils.isEmpty(c)) {
                c = g();
            }
            str = c;
        }
        return str;
    }

    private static String a(DataInput dataInput) throws IOException {
        if (1 != dataInput.readInt()) {
            return null;
        }
        return dataInput.readUTF();
    }

    private static void a(String str, DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(1);
        dataOutput.writeUTF(str);
    }

    static void a(String str, File file) {
        DataOutputStream dataOutputStream;
        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            try {
                a(str, (DataOutput) dataOutputStream);
                fe.a((Closeable) dataOutputStream);
            } catch (Throwable th) {
                th = th;
                try {
                    eo.a(6, a, "Error when saving phoneId", th);
                    fe.a((Closeable) dataOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    fe.a((Closeable) dataOutputStream);
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            dataOutputStream = null;
            fe.a((Closeable) dataOutputStream);
            throw th;
        }
    }

    static boolean a(String str) {
        return !TextUtils.isEmpty(str) && !c(str.toLowerCase(Locale.US));
    }

    static String b() {
        String string = Settings.Secure.getString(Cdo.a().b().getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
        if (!a(string)) {
            return null;
        }
        return "AND" + string;
    }

    private static String b(DataInput dataInput) throws IOException {
        if (46586 != dataInput.readUnsignedShort() || 2 != dataInput.readUnsignedShort()) {
            return null;
        }
        dataInput.readUTF();
        return dataInput.readUTF();
    }

    static void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            File fileStreamPath = Cdo.a().b().getFileStreamPath(h());
            if (fd.a(fileStreamPath)) {
                a(str, fileStreamPath);
            }
        }
    }

    static String c() {
        String e = e();
        if (TextUtils.isEmpty(e)) {
            e = f();
            if (TextUtils.isEmpty(e)) {
                e = d();
            }
            b(e);
        }
        return e;
    }

    private static boolean c(String str) {
        return b.contains(str);
    }

    static String d() {
        return "ID" + Long.toString(Double.doubleToLongBits(Math.random()) + (37 * (System.nanoTime() + ((long) (dx.c(Cdo.a().b()).hashCode() * 37)))), 16);
    }

    static String e() {
        DataInputStream dataInputStream;
        Throwable th;
        String str = null;
        File fileStreamPath = Cdo.a().b().getFileStreamPath(h());
        if (fileStreamPath != null && fileStreamPath.exists()) {
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    str = a((DataInput) dataInputStream);
                    fe.a((Closeable) dataInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        eo.a(6, a, "Error when loading phoneId", th);
                        fe.a((Closeable) dataInputStream);
                        return str;
                    } catch (Throwable th3) {
                        th = th3;
                        fe.a((Closeable) dataInputStream);
                        throw th;
                    }
                }
            } catch (Throwable th4) {
                dataInputStream = null;
                th = th4;
                fe.a((Closeable) dataInputStream);
                throw th;
            }
        }
        return str;
    }

    static String f() {
        String[] list;
        File fileStreamPath;
        DataInputStream dataInputStream;
        Throwable th;
        String str = null;
        File filesDir = Cdo.a().b().getFilesDir();
        if (!(filesDir == null || (list = filesDir.list(new FilenameFilter() {
            public boolean accept(File file, String str) {
                return str.startsWith(".flurryagent.");
            }
        })) == null || list.length == 0 || (fileStreamPath = Cdo.a().b().getFileStreamPath(list[0])) == null || !fileStreamPath.exists())) {
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    str = b((DataInput) dataInputStream);
                    fe.a((Closeable) dataInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        eo.a(6, a, "Error when loading phoneId", th);
                        fe.a((Closeable) dataInputStream);
                        return str;
                    } catch (Throwable th3) {
                        th = th3;
                        fe.a((Closeable) dataInputStream);
                        throw th;
                    }
                }
            } catch (Throwable th4) {
                dataInputStream = null;
                th = th4;
                fe.a((Closeable) dataInputStream);
                throw th;
            }
        }
        return str;
    }

    private static String g() {
        String b2 = b();
        return !TextUtils.isEmpty(b2) ? b2 : c();
    }

    private static String h() {
        return ".flurryb.";
    }

    private static Set<String> i() {
        HashSet hashSet = new HashSet();
        hashSet.add("null");
        hashSet.add("9774d56d682e549c");
        hashSet.add("dead00beef");
        return Collections.unmodifiableSet(hashSet);
    }
}
