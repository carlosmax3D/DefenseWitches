package com.flurry.sdk;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;

public final class fd {
    private static String a = fd.class.getSimpleName();

    public static File a(boolean z) {
        File file = null;
        Context b = Cdo.a().b();
        if (z && "mounted".equals(Environment.getExternalStorageState()) && (Build.VERSION.SDK_INT >= 19 || b.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
            file = b.getExternalFilesDir((String) null);
        }
        return file == null ? b.getFilesDir() : file;
    }

    @Deprecated
    public static void a(File file, String str) {
        FileOutputStream fileOutputStream;
        if (file == null) {
            eo.a(4, a, "No persistent file specified.");
        } else if (str == null) {
            eo.a(4, a, "No data specified; deleting persistent file: " + file.getAbsolutePath());
            file.delete();
        } else {
            eo.a(4, a, "Writing persistent data: " + file.getAbsolutePath());
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    fileOutputStream.write(str.getBytes());
                    fe.a((Closeable) fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    try {
                        eo.a(6, a, "Error writing persistent file", th);
                        fe.a((Closeable) fileOutputStream);
                    } catch (Throwable th2) {
                        th = th2;
                        fe.a((Closeable) fileOutputStream);
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                fe.a((Closeable) fileOutputStream);
                throw th;
            }
        }
    }

    public static boolean a(File file) {
        if (file == null || file.getAbsoluteFile() == null) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return true;
        }
        if (parentFile.mkdirs() || parentFile.isDirectory()) {
            return true;
        }
        eo.a(6, a, "Unable to create persistent dir: " + parentFile);
        return false;
    }

    public static File b(boolean z) {
        Context b = Cdo.a().b();
        File file = null;
        if (z && "mounted".equals(Environment.getExternalStorageState()) && (Build.VERSION.SDK_INT >= 19 || b.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
            file = b.getExternalCacheDir();
        }
        return file == null ? b.getCacheDir() : file;
    }

    public static boolean b(File file) {
        if (file != null && file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                if (!b(new File(file, file2))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String c(java.io.File r7) {
        /*
            r4 = 4
            r0 = 0
            if (r7 == 0) goto L_0x000a
            boolean r1 = r7.exists()
            if (r1 != 0) goto L_0x0012
        L_0x000a:
            java.lang.String r1 = a
            java.lang.String r2 = "Persistent file doesn't exist."
            com.flurry.sdk.eo.a((int) r4, (java.lang.String) r1, (java.lang.String) r2)
        L_0x0011:
            return r0
        L_0x0012:
            java.lang.String r1 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Loading persistent data: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r7.getAbsolutePath()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.flurry.sdk.eo.a((int) r4, (java.lang.String) r1, (java.lang.String) r2)
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x006d, all -> 0x0064 }
            r2.<init>(r7)     // Catch:{ Throwable -> 0x006d, all -> 0x0064 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x004c }
            r1.<init>()     // Catch:{ Throwable -> 0x004c }
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ Throwable -> 0x004c }
        L_0x003c:
            int r4 = r2.read(r3)     // Catch:{ Throwable -> 0x004c }
            if (r4 <= 0) goto L_0x0060
            java.lang.String r5 = new java.lang.String     // Catch:{ Throwable -> 0x004c }
            r6 = 0
            r5.<init>(r3, r6, r4)     // Catch:{ Throwable -> 0x004c }
            r1.append(r5)     // Catch:{ Throwable -> 0x004c }
            goto L_0x003c
        L_0x004c:
            r1 = move-exception
        L_0x004d:
            r3 = 6
            java.lang.String r4 = a     // Catch:{ all -> 0x006b }
            java.lang.String r5 = "Error when loading persistent file"
            com.flurry.sdk.eo.a(r3, r4, r5, r1)     // Catch:{ all -> 0x006b }
            com.flurry.sdk.fe.a((java.io.Closeable) r2)
            r1 = r0
        L_0x0059:
            if (r1 == 0) goto L_0x0011
            java.lang.String r0 = r1.toString()
            goto L_0x0011
        L_0x0060:
            com.flurry.sdk.fe.a((java.io.Closeable) r2)
            goto L_0x0059
        L_0x0064:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0067:
            com.flurry.sdk.fe.a((java.io.Closeable) r2)
            throw r0
        L_0x006b:
            r0 = move-exception
            goto L_0x0067
        L_0x006d:
            r1 = move-exception
            r2 = r0
            goto L_0x004d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.fd.c(java.io.File):java.lang.String");
    }
}
