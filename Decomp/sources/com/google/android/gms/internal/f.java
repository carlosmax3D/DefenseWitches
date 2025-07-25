package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.internal.l;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;

public abstract class f extends e {
    private static Method dA;
    private static Method dB;
    private static Method dC;
    private static Method dD;
    private static String dE;
    private static l dF;
    static boolean dG = false;
    private static Method dy;
    private static Method dz;
    private static long startTime = 0;

    static class a extends Exception {
        public a() {
        }

        public a(Throwable th) {
            super(th);
        }
    }

    protected f(Context context, j jVar, k kVar) {
        super(context, jVar, kVar);
    }

    static String a(Context context, j jVar) throws a {
        if (dA == null) {
            throw new a();
        }
        try {
            ByteBuffer byteBuffer = (ByteBuffer) dA.invoke((Object) null, new Object[]{context});
            if (byteBuffer != null) {
                return jVar.a(byteBuffer.array(), true);
            }
            throw new a();
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    static ArrayList<Long> a(MotionEvent motionEvent, DisplayMetrics displayMetrics) throws a {
        if (dB == null || motionEvent == null) {
            throw new a();
        }
        try {
            return (ArrayList) dB.invoke((Object) null, new Object[]{motionEvent, displayMetrics});
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    protected static synchronized void a(String str, Context context, j jVar) {
        synchronized (f.class) {
            if (!dG) {
                try {
                    dF = new l(jVar, (SecureRandom) null);
                    dE = str;
                    e(context);
                    startTime = e().longValue();
                    dG = true;
                } catch (a | UnsupportedOperationException e) {
                }
            }
        }
    }

    static String b(Context context, j jVar) throws a {
        if (dD == null) {
            throw new a();
        }
        try {
            ByteBuffer byteBuffer = (ByteBuffer) dD.invoke((Object) null, new Object[]{context});
            if (byteBuffer != null) {
                return jVar.a(byteBuffer.array(), true);
            }
            throw new a();
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    private static String b(byte[] bArr, String str) throws a {
        try {
            return new String(dF.c(bArr, str), "UTF-8");
        } catch (l.a e) {
            throw new a(e);
        } catch (UnsupportedEncodingException e2) {
            throw new a(e2);
        }
    }

    static String d() throws a {
        if (dE != null) {
            return dE;
        }
        throw new a();
    }

    static String d(Context context) throws a {
        if (dC == null) {
            throw new a();
        }
        try {
            String str = (String) dC.invoke((Object) null, new Object[]{context});
            if (str != null) {
                return str;
            }
            throw new a();
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    static Long e() throws a {
        if (dy == null) {
            throw new a();
        }
        try {
            return (Long) dy.invoke((Object) null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    private static void e(Context context) throws a {
        try {
            byte[] d = dF.d(n.getKey());
            byte[] c = dF.c(d, n.i());
            File cacheDir = context.getCacheDir();
            if (cacheDir == null && (cacheDir = context.getDir("dex", 0)) == null) {
                throw new a();
            }
            File createTempFile = File.createTempFile("ads", ".jar", cacheDir);
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
            fileOutputStream.write(c, 0, c.length);
            fileOutputStream.close();
            DexClassLoader dexClassLoader = new DexClassLoader(createTempFile.getAbsolutePath(), cacheDir.getAbsolutePath(), (String) null, context.getClassLoader());
            Class loadClass = dexClassLoader.loadClass(b(d, n.j()));
            Class loadClass2 = dexClassLoader.loadClass(b(d, n.p()));
            Class loadClass3 = dexClassLoader.loadClass(b(d, n.n()));
            Class loadClass4 = dexClassLoader.loadClass(b(d, n.t()));
            Class loadClass5 = dexClassLoader.loadClass(b(d, n.l()));
            Class loadClass6 = dexClassLoader.loadClass(b(d, n.r()));
            dy = loadClass.getMethod(b(d, n.k()), new Class[0]);
            dz = loadClass2.getMethod(b(d, n.q()), new Class[0]);
            dA = loadClass3.getMethod(b(d, n.o()), new Class[]{Context.class});
            dB = loadClass4.getMethod(b(d, n.u()), new Class[]{MotionEvent.class, DisplayMetrics.class});
            dC = loadClass5.getMethod(b(d, n.m()), new Class[]{Context.class});
            dD = loadClass6.getMethod(b(d, n.s()), new Class[]{Context.class});
            String name = createTempFile.getName();
            createTempFile.delete();
            new File(cacheDir, name.replace(".jar", ".dex")).delete();
        } catch (FileNotFoundException e) {
            throw new a(e);
        } catch (IOException e2) {
            throw new a(e2);
        } catch (ClassNotFoundException e3) {
            throw new a(e3);
        } catch (l.a e4) {
            throw new a(e4);
        } catch (NoSuchMethodException e5) {
            throw new a(e5);
        } catch (NullPointerException e6) {
            throw new a(e6);
        }
    }

    static String f() throws a {
        if (dz == null) {
            throw new a();
        }
        try {
            return (String) dz.invoke((Object) null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0027 A[ExcHandler: a | IOException (e java.lang.Throwable), Splitter:B:1:0x0001] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(android.content.Context r5) {
        /*
            r4 = this;
            r0 = 1
            java.lang.String r1 = f()     // Catch:{ a -> 0x002f, IOException -> 0x0027 }
            r4.a((int) r0, (java.lang.String) r1)     // Catch:{ a -> 0x002f, IOException -> 0x0027 }
        L_0x0008:
            r0 = 2
            java.lang.String r1 = d()     // Catch:{ a -> 0x002d, IOException -> 0x0027 }
            r4.a((int) r0, (java.lang.String) r1)     // Catch:{ a -> 0x002d, IOException -> 0x0027 }
        L_0x0010:
            r0 = 25
            java.lang.Long r1 = e()     // Catch:{ a -> 0x002b, IOException -> 0x0027 }
            long r2 = r1.longValue()     // Catch:{ a -> 0x002b, IOException -> 0x0027 }
            r4.a((int) r0, (long) r2)     // Catch:{ a -> 0x002b, IOException -> 0x0027 }
        L_0x001d:
            r0 = 24
            java.lang.String r1 = d(r5)     // Catch:{ a -> 0x0029, IOException -> 0x0027 }
            r4.a((int) r0, (java.lang.String) r1)     // Catch:{ a -> 0x0029, IOException -> 0x0027 }
        L_0x0026:
            return
        L_0x0027:
            r0 = move-exception
            goto L_0x0026
        L_0x0029:
            r0 = move-exception
            goto L_0x0026
        L_0x002b:
            r0 = move-exception
            goto L_0x001d
        L_0x002d:
            r0 = move-exception
            goto L_0x0010
        L_0x002f:
            r0 = move-exception
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.f.b(android.content.Context):void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x008a A[ExcHandler: a | IOException (e java.lang.Throwable), Splitter:B:6:0x0010] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c(android.content.Context r7) {
        /*
            r6 = this;
            r0 = 2
            java.lang.String r1 = d()     // Catch:{ a -> 0x0097, IOException -> 0x008a }
            r6.a((int) r0, (java.lang.String) r1)     // Catch:{ a -> 0x0097, IOException -> 0x008a }
        L_0x0008:
            r0 = 1
            java.lang.String r1 = f()     // Catch:{ a -> 0x0094, IOException -> 0x008a }
            r6.a((int) r0, (java.lang.String) r1)     // Catch:{ a -> 0x0094, IOException -> 0x008a }
        L_0x0010:
            java.lang.Long r0 = e()     // Catch:{ a -> 0x0092, IOException -> 0x008a }
            long r0 = r0.longValue()     // Catch:{ a -> 0x0092, IOException -> 0x008a }
            r2 = 25
            r6.a((int) r2, (long) r0)     // Catch:{ a -> 0x0092, IOException -> 0x008a }
            long r2 = startTime     // Catch:{ a -> 0x0092, IOException -> 0x008a }
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x0034
            r2 = 17
            long r4 = startTime     // Catch:{ a -> 0x0092, IOException -> 0x008a }
            long r0 = r0 - r4
            r6.a((int) r2, (long) r0)     // Catch:{ a -> 0x0092, IOException -> 0x008a }
            r0 = 23
            long r2 = startTime     // Catch:{ a -> 0x0092, IOException -> 0x008a }
            r6.a((int) r0, (long) r2)     // Catch:{ a -> 0x0092, IOException -> 0x008a }
        L_0x0034:
            android.view.MotionEvent r0 = r6.du     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            android.util.DisplayMetrics r1 = r6.dv     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            java.util.ArrayList r1 = a((android.view.MotionEvent) r0, (android.util.DisplayMetrics) r1)     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            r2 = 14
            r0 = 0
            java.lang.Object r0 = r1.get(r0)     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            long r4 = r0.longValue()     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            r6.a((int) r2, (long) r4)     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            r2 = 15
            r0 = 1
            java.lang.Object r0 = r1.get(r0)     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            long r4 = r0.longValue()     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            r6.a((int) r2, (long) r4)     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            int r0 = r1.size()     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            r2 = 3
            if (r0 < r2) goto L_0x0073
            r2 = 16
            r0 = 2
            java.lang.Object r0 = r1.get(r0)     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            long r0 = r0.longValue()     // Catch:{ a -> 0x0090, IOException -> 0x008a }
            r6.a((int) r2, (long) r0)     // Catch:{ a -> 0x0090, IOException -> 0x008a }
        L_0x0073:
            r0 = 27
            com.google.android.gms.internal.j r1 = r6.dw     // Catch:{ a -> 0x008e, IOException -> 0x008a }
            java.lang.String r1 = a((android.content.Context) r7, (com.google.android.gms.internal.j) r1)     // Catch:{ a -> 0x008e, IOException -> 0x008a }
            r6.a((int) r0, (java.lang.String) r1)     // Catch:{ a -> 0x008e, IOException -> 0x008a }
        L_0x007e:
            r0 = 29
            com.google.android.gms.internal.j r1 = r6.dw     // Catch:{ a -> 0x008c, IOException -> 0x008a }
            java.lang.String r1 = b((android.content.Context) r7, (com.google.android.gms.internal.j) r1)     // Catch:{ a -> 0x008c, IOException -> 0x008a }
            r6.a((int) r0, (java.lang.String) r1)     // Catch:{ a -> 0x008c, IOException -> 0x008a }
        L_0x0089:
            return
        L_0x008a:
            r0 = move-exception
            goto L_0x0089
        L_0x008c:
            r0 = move-exception
            goto L_0x0089
        L_0x008e:
            r0 = move-exception
            goto L_0x007e
        L_0x0090:
            r0 = move-exception
            goto L_0x0073
        L_0x0092:
            r0 = move-exception
            goto L_0x0034
        L_0x0094:
            r0 = move-exception
            goto L_0x0010
        L_0x0097:
            r0 = move-exception
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.f.c(android.content.Context):void");
    }
}
