package com.google.ads.mediation.jsadapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.WebView;
import com.google.android.gms.internal.ct;

public final class AdViewCheckTask implements Runnable {
    public static final int BACKGROUND_COLOR = 0;
    /* access modifiers changed from: private */
    public final JavascriptAdapter r;
    /* access modifiers changed from: private */
    public final Handler s = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final long t;
    /* access modifiers changed from: private */
    public long u;

    private final class a extends AsyncTask<Void, Void, Boolean> {
        private final int v;
        private final int w;
        private final WebView x;
        private Bitmap y;

        public a(int i, int i2, WebView webView) {
            this.v = i2;
            this.w = i;
            this.x = webView;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public synchronized Boolean doInBackground(Void... voidArr) {
            boolean z2;
            int width = this.y.getWidth();
            int height = this.y.getHeight();
            if (width == 0 || height == 0) {
                z2 = false;
            } else {
                int i = 0;
                for (int i2 = 0; i2 < width; i2 += 10) {
                    for (int i3 = 0; i3 < height; i3 += 10) {
                        if (this.y.getPixel(i2, i3) != 0) {
                            i++;
                        }
                    }
                }
                z2 = Boolean.valueOf(((double) i) / (((double) (width * height)) / 100.0d) > 0.1d);
            }
            return z2;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(Boolean bool) {
            AdViewCheckTask.a(AdViewCheckTask.this);
            if (bool.booleanValue()) {
                AdViewCheckTask.this.r.sendAdReceivedUpdate();
            } else if (AdViewCheckTask.this.u > 0) {
                if (ct.n(2)) {
                    ct.r("Ad not detected, scheduling another run.");
                }
                AdViewCheckTask.this.s.postDelayed(AdViewCheckTask.this, AdViewCheckTask.this.t);
            } else {
                ct.r("Ad not detected, Not scheduling anymore runs.");
                AdViewCheckTask.this.r.sendAdNotReceivedUpdate();
            }
        }

        /* access modifiers changed from: protected */
        public synchronized void onPreExecute() {
            this.y = Bitmap.createBitmap(this.w, this.v, Bitmap.Config.ARGB_8888);
            this.x.setVisibility(0);
            this.x.measure(View.MeasureSpec.makeMeasureSpec(this.w, 0), View.MeasureSpec.makeMeasureSpec(this.v, 0));
            this.x.layout(0, 0, this.w, this.v);
            this.x.draw(new Canvas(this.y));
            this.x.invalidate();
        }
    }

    public AdViewCheckTask(JavascriptAdapter javascriptAdapter, long j, long j2) {
        this.r = javascriptAdapter;
        this.t = j;
        this.u = j2;
    }

    static /* synthetic */ long a(AdViewCheckTask adViewCheckTask) {
        long j = adViewCheckTask.u - 1;
        adViewCheckTask.u = j;
        return j;
    }

    public void run() {
        if (this.r != null && !this.r.shouldStopAdCheck()) {
            new a(this.r.getWebViewWidth(), this.r.getWebViewHeight(), this.r.getWebView()).execute(new Void[0]);
        }
    }

    public void start() {
        this.s.postDelayed(this, this.t);
    }
}
