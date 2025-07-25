package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.ansca.corona.Crypto;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class e implements d {
    protected MotionEvent du;
    protected DisplayMetrics dv;
    protected j dw;
    private k dx;

    protected e(Context context, j jVar, k kVar) {
        this.dw = jVar;
        this.dx = kVar;
        try {
            this.dv = context.getResources().getDisplayMetrics();
        } catch (UnsupportedOperationException e) {
            this.dv = new DisplayMetrics();
            this.dv.density = 1.0f;
        }
    }

    private String a(Context context, String str, boolean z) {
        byte[] c;
        try {
            synchronized (this) {
                b();
                if (z) {
                    c(context);
                } else {
                    b(context);
                }
                c = c();
            }
            return c.length == 0 ? Integer.toString(5) : a(c, str);
        } catch (NoSuchAlgorithmException e) {
            return Integer.toString(7);
        } catch (UnsupportedEncodingException e2) {
            return Integer.toString(7);
        } catch (IOException e3) {
            return Integer.toString(3);
        }
    }

    private void b() {
        this.dx.reset();
    }

    private byte[] c() throws IOException {
        return this.dx.h();
    }

    public String a(Context context) {
        return a(context, (String) null, false);
    }

    public String a(Context context, String str) {
        return a(context, str, true);
    }

    /* access modifiers changed from: package-private */
    public String a(byte[] bArr, String str) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
        byte[] array;
        if (bArr.length > 239) {
            b();
            a(20, 1);
            bArr = c();
        }
        if (bArr.length < 239) {
            byte[] bArr2 = new byte[(239 - bArr.length)];
            new SecureRandom().nextBytes(bArr2);
            array = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).put(bArr2).array();
        } else {
            array = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).array();
        }
        MessageDigest instance = MessageDigest.getInstance(Crypto.ALGORITHM_MD5);
        instance.update(array);
        byte[] array2 = ByteBuffer.allocate(256).put(instance.digest()).put(array).array();
        byte[] bArr3 = new byte[256];
        new b().a(array2, bArr3);
        if (str != null && str.length() > 0) {
            a(str, bArr3);
        }
        return this.dw.a(bArr3, true);
    }

    public void a(int i, int i2, int i3) {
        if (this.du != null) {
            this.du.recycle();
        }
        this.du = MotionEvent.obtain(0, (long) i3, 1, ((float) i) * this.dv.density, ((float) i2) * this.dv.density, 0.0f, 0.0f, 0, 0.0f, 0.0f, 0, 0);
    }

    /* access modifiers changed from: protected */
    public void a(int i, long j) throws IOException {
        this.dx.b(i, j);
    }

    /* access modifiers changed from: protected */
    public void a(int i, String str) throws IOException {
        this.dx.b(i, str);
    }

    public void a(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.du != null) {
                this.du.recycle();
            }
            this.du = MotionEvent.obtain(motionEvent);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str, byte[] bArr) throws UnsupportedEncodingException {
        if (str.length() > 32) {
            str = str.substring(0, 32);
        }
        new iv(str.getBytes("UTF-8")).h(bArr);
    }

    /* access modifiers changed from: protected */
    public abstract void b(Context context);

    /* access modifiers changed from: protected */
    public abstract void c(Context context);
}
