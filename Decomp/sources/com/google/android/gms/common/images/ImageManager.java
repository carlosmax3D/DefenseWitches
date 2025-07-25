package com.google.android.gms.common.images;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.images.a;
import com.google.android.gms.internal.ds;
import com.google.android.gms.internal.ek;
import com.google.android.gms.internal.fg;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    /* access modifiers changed from: private */
    public static final Object ob = new Object();
    /* access modifiers changed from: private */
    public static HashSet<Uri> oc = new HashSet<>();
    private static ImageManager od;
    private static ImageManager oe;
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final ExecutorService of = Executors.newFixedThreadPool(4);
    /* access modifiers changed from: private */
    public final b og;
    /* access modifiers changed from: private */
    public final Map<a, ImageReceiver> oh;
    /* access modifiers changed from: private */
    public final Map<Uri, ImageReceiver> oi;

    private final class ImageReceiver extends ResultReceiver {
        private final Uri mUri;
        /* access modifiers changed from: private */
        public final ArrayList<a> oj;
        boolean ok = false;

        ImageReceiver(Uri uri) {
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
            this.oj = new ArrayList<>();
        }

        public void bB() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            ImageManager.this.mContext.sendBroadcast(intent);
        }

        public void c(a aVar) {
            ds.a(!this.ok, "Cannot add an ImageRequest when mHandlingRequests is true");
            ds.N("ImageReceiver.addImageRequest() must be called in the main thread");
            this.oj.add(aVar);
        }

        public void d(a aVar) {
            ds.a(!this.ok, "Cannot remove an ImageRequest when mHandlingRequests is true");
            ds.N("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.oj.remove(aVar);
        }

        public void onReceiveResult(int i, Bundle bundle) {
            ImageManager.this.of.execute(new c(this.mUri, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    private static final class a {
        static int a(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    private static final class b extends ek<a.C0002a, Bitmap> {
        public b(Context context) {
            super(q(context));
        }

        private static int q(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            return (int) (((float) (((!((context.getApplicationInfo().flags & AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START) != 0) || !fg.cD()) ? activityManager.getMemoryClass() : a.a(activityManager)) * AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START)) * 0.33f);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public int sizeOf(a.C0002a aVar, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void entryRemoved(boolean z, a.C0002a aVar, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z, aVar, bitmap, bitmap2);
        }
    }

    private final class c implements Runnable {
        private final Uri mUri;
        private final ParcelFileDescriptor om;

        public c(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.mUri = uri;
            this.om = parcelFileDescriptor;
        }

        public void run() {
            ds.O("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean z = false;
            Bitmap bitmap = null;
            if (this.om != null) {
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(this.om.getFileDescriptor());
                } catch (OutOfMemoryError e) {
                    Log.e("ImageManager", "OOM while loading bitmap for uri: " + this.mUri, e);
                    z = true;
                }
                try {
                    this.om.close();
                } catch (IOException e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ImageManager.this.mHandler.post(new f(this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                Log.w("ImageManager", "Latch interrupted while posting " + this.mUri);
            }
        }
    }

    private final class d implements Runnable {
        private final a on;

        public d(a aVar) {
            this.on = aVar;
        }

        public void run() {
            ds.N("LoadImageRunnable must be executed on the main thread");
            boolean unused = ImageManager.this.b(this.on);
            a.C0002a aVar = this.on.op;
            if (aVar.uri == null) {
                this.on.b(ImageManager.this.mContext, true);
                return;
            }
            Bitmap a = ImageManager.this.a(aVar);
            if (a != null) {
                this.on.a(ImageManager.this.mContext, a, true);
                return;
            }
            this.on.r(ImageManager.this.mContext);
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.oi.get(aVar.uri);
            if (imageReceiver == null) {
                imageReceiver = new ImageReceiver(aVar.uri);
                ImageManager.this.oi.put(aVar.uri, imageReceiver);
            }
            imageReceiver.c(this.on);
            if (this.on.os != 1) {
                ImageManager.this.oh.put(this.on, imageReceiver);
            }
            synchronized (ImageManager.ob) {
                if (!ImageManager.oc.contains(aVar.uri)) {
                    ImageManager.oc.add(aVar.uri);
                    imageReceiver.bB();
                }
            }
        }
    }

    private static final class e implements ComponentCallbacks2 {
        private final b og;

        public e(b bVar) {
            this.og = bVar;
        }

        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onLowMemory() {
            this.og.evictAll();
        }

        public void onTrimMemory(int i) {
            if (i >= 60) {
                this.og.evictAll();
            } else if (i >= 20) {
                this.og.trimToSize(this.og.size() / 2);
            }
        }
    }

    private final class f implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        private final CountDownLatch mX;
        private boolean oo;

        public f(Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.oo = z;
            this.mX = countDownLatch;
        }

        private void a(ImageReceiver imageReceiver, boolean z) {
            imageReceiver.ok = true;
            ArrayList a = imageReceiver.oj;
            int size = a.size();
            for (int i = 0; i < size; i++) {
                a aVar = (a) a.get(i);
                if (z) {
                    aVar.a(ImageManager.this.mContext, this.mBitmap, false);
                } else {
                    aVar.b(ImageManager.this.mContext, false);
                }
                if (aVar.os != 1) {
                    ImageManager.this.oh.remove(aVar);
                }
            }
            imageReceiver.ok = false;
        }

        public void run() {
            ds.N("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (ImageManager.this.og != null) {
                if (this.oo) {
                    ImageManager.this.og.evictAll();
                    System.gc();
                    this.oo = false;
                    ImageManager.this.mHandler.post(this);
                    return;
                } else if (z) {
                    ImageManager.this.og.put(new a.C0002a(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.oi.remove(this.mUri);
            if (imageReceiver != null) {
                a(imageReceiver, z);
            }
            this.mX.countDown();
            synchronized (ImageManager.ob) {
                ImageManager.oc.remove(this.mUri);
            }
        }
    }

    private ImageManager(Context context, boolean z) {
        this.mContext = context.getApplicationContext();
        if (z) {
            this.og = new b(this.mContext);
            if (fg.cG()) {
                bz();
            }
        } else {
            this.og = null;
        }
        this.oh = new HashMap();
        this.oi = new HashMap();
    }

    /* access modifiers changed from: private */
    public Bitmap a(a.C0002a aVar) {
        if (this.og == null) {
            return null;
        }
        return (Bitmap) this.og.get(aVar);
    }

    public static ImageManager a(Context context, boolean z) {
        if (z) {
            if (oe == null) {
                oe = new ImageManager(context, true);
            }
            return oe;
        }
        if (od == null) {
            od = new ImageManager(context, false);
        }
        return od;
    }

    /* access modifiers changed from: private */
    public boolean b(a aVar) {
        ds.N("ImageManager.cleanupHashMaps() must be called in the main thread");
        if (aVar.os == 1) {
            return true;
        }
        ImageReceiver imageReceiver = this.oh.get(aVar);
        if (imageReceiver == null) {
            return true;
        }
        if (imageReceiver.ok) {
            return false;
        }
        this.oh.remove(aVar);
        imageReceiver.d(aVar);
        return true;
    }

    private void bz() {
        this.mContext.registerComponentCallbacks(new e(this.og));
    }

    public static ImageManager create(Context context) {
        return a(context, false);
    }

    public void a(a aVar) {
        ds.N("ImageManager.loadImage() must be called in the main thread");
        boolean b2 = b(aVar);
        d dVar = new d(aVar);
        if (b2) {
            dVar.run();
        } else {
            this.mHandler.post(dVar);
        }
    }

    public void loadImage(ImageView imageView, int i) {
        a aVar = new a(i);
        aVar.a(imageView);
        a(aVar);
    }

    public void loadImage(ImageView imageView, Uri uri) {
        a aVar = new a(uri);
        aVar.a(imageView);
        a(aVar);
    }

    public void loadImage(ImageView imageView, Uri uri, int i) {
        a aVar = new a(uri);
        aVar.F(i);
        aVar.a(imageView);
        a(aVar);
    }

    public void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        a aVar = new a(uri);
        aVar.a(onImageLoadedListener);
        a(aVar);
    }

    public void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        a aVar = new a(uri);
        aVar.F(i);
        aVar.a(onImageLoadedListener);
        a(aVar);
    }
}
