package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.internal.bs;
import com.google.android.gms.internal.cx;

public final class bk extends bs.a {
    private boolean gA = false;
    private boolean gB = false;
    private RelativeLayout gC;
    private final Activity gs;
    private bm gt;
    private bo gu;
    private cw gv;
    private b gw;
    private bp gx;
    private FrameLayout gy;
    private WebChromeClient.CustomViewCallback gz;

    private static final class a extends Exception {
        public a(String str) {
            super(str);
        }
    }

    private static final class b {
        public final ViewGroup.LayoutParams gE;
        public final ViewGroup gF;
        public final int index;

        public b(cw cwVar) throws a {
            this.gE = cwVar.getLayoutParams();
            ViewParent parent = cwVar.getParent();
            if (parent instanceof ViewGroup) {
                this.gF = (ViewGroup) parent;
                this.index = this.gF.indexOfChild(cwVar);
                this.gF.removeView(cwVar);
                cwVar.l(true);
                return;
            }
            throw new a("Could not get the parent of the WebView for an overlay.");
        }
    }

    public bk(Activity activity) {
        this.gs = activity;
    }

    private void Z() {
        if (this.gs.isFinishing() && !this.gB) {
            this.gB = true;
            if (this.gs.isFinishing()) {
                if (this.gv != null) {
                    this.gv.az();
                    this.gC.removeView(this.gv);
                    if (this.gw != null) {
                        this.gv.l(false);
                        this.gw.gF.addView(this.gv, this.gw.index, this.gw.gE);
                    }
                }
                if (this.gt != null && this.gt.gI != null) {
                    this.gt.gI.A();
                }
            }
        }
    }

    private static RelativeLayout.LayoutParams a(int i, int i2, int i3, int i4) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i3, i4);
        layoutParams.setMargins(i, i2, 0, 0);
        layoutParams.addRule(10);
        layoutParams.addRule(9);
        return layoutParams;
    }

    public static void a(Context context, bm bmVar) {
        Intent intent = new Intent();
        intent.setClassName(context, AdActivity.CLASS_NAME);
        intent.putExtra("com.google.android.gms.ads.internal.overlay.useClientJar", bmVar.ej.iM);
        bm.a(intent, bmVar);
        intent.addFlags(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
        context.startActivity(intent);
    }

    private void h(boolean z) throws a {
        this.gs.requestWindowFeature(1);
        Window window = this.gs.getWindow();
        window.setFlags(1024, 1024);
        setRequestedOrientation(this.gt.orientation);
        if (Build.VERSION.SDK_INT >= 11) {
            ct.r("Enabling hardware acceleration on the AdActivity window.");
            cp.a(window);
        }
        this.gC = new RelativeLayout(this.gs);
        this.gC.setBackgroundColor(-16777216);
        this.gs.setContentView(this.gC);
        boolean aJ = this.gt.gJ.aC().aJ();
        if (z) {
            this.gv = cw.a(this.gs, this.gt.gJ.y(), true, aJ, (h) null, this.gt.ej);
            this.gv.aC().a((q) null, (bn) null, this.gt.gK, this.gt.gO, true);
            this.gv.aC().a((cx.a) new cx.a() {
                public void a(cw cwVar) {
                    cwVar.aA();
                }
            });
            if (this.gt.go != null) {
                this.gv.loadUrl(this.gt.go);
            } else if (this.gt.gN != null) {
                this.gv.loadDataWithBaseURL(this.gt.gL, this.gt.gN, "text/html", "UTF-8", (String) null);
            } else {
                throw new a("No URL or HTML to display in ad overlay.");
            }
        } else {
            this.gv = this.gt.gJ;
            this.gv.setContext(this.gs);
        }
        this.gv.a(this);
        this.gC.addView(this.gv, -1, -1);
        if (!z) {
            this.gv.aA();
        }
        f(aJ);
    }

    public bo W() {
        return this.gu;
    }

    public void X() {
        if (this.gt != null) {
            setRequestedOrientation(this.gt.orientation);
        }
        if (this.gy != null) {
            this.gs.setContentView(this.gC);
            this.gy.removeAllViews();
            this.gy = null;
        }
        if (this.gz != null) {
            this.gz.onCustomViewHidden();
            this.gz = null;
        }
    }

    public void Y() {
        this.gC.removeView(this.gx);
        f(true);
    }

    public void a(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        this.gy = new FrameLayout(this.gs);
        this.gy.setBackgroundColor(-16777216);
        this.gy.addView(view, -1, -1);
        this.gs.setContentView(this.gy);
        this.gz = customViewCallback;
    }

    public void b(int i, int i2, int i3, int i4) {
        if (this.gu != null) {
            this.gu.setLayoutParams(a(i, i2, i3, i4));
        }
    }

    public void c(int i, int i2, int i3, int i4) {
        if (this.gu == null) {
            this.gu = new bo(this.gs, this.gv);
            this.gC.addView(this.gu, 0, a(i, i2, i3, i4));
            this.gv.aC().m(false);
        }
    }

    public void close() {
        this.gs.finish();
    }

    public void f(boolean z) {
        this.gx = new bp(this.gs, z ? 50 : 32);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layoutParams.addRule(z ? 11 : 9);
        this.gx.g(this.gt.gM);
        this.gC.addView(this.gx, layoutParams);
    }

    public void g(boolean z) {
        if (this.gx != null) {
            this.gx.g(z);
        }
    }

    public void onCreate(Bundle bundle) {
        boolean z = false;
        if (bundle != null) {
            z = bundle.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false);
        }
        this.gA = z;
        try {
            this.gt = bm.a(this.gs.getIntent());
            if (this.gt == null) {
                throw new a("Could not get info for ad overlay.");
            }
            if (bundle == null) {
                if (this.gt.gI != null) {
                    this.gt.gI.B();
                }
                if (!(this.gt.gP == 1 || this.gt.gH == null)) {
                    this.gt.gH.w();
                }
            }
            switch (this.gt.gP) {
                case 1:
                    h(false);
                    return;
                case 2:
                    this.gw = new b(this.gt.gJ);
                    h(false);
                    return;
                case 3:
                    h(true);
                    return;
                case 4:
                    if (this.gA) {
                        this.gs.finish();
                        return;
                    } else if (!bh.a(this.gs, this.gt.gG, this.gt.gO)) {
                        this.gs.finish();
                        return;
                    } else {
                        return;
                    }
                default:
                    throw new a("Could not determine ad overlay type.");
            }
        } catch (a e) {
            ct.v(e.getMessage());
            this.gs.finish();
        }
    }

    public void onDestroy() {
        if (this.gu != null) {
            this.gu.destroy();
        }
        if (this.gv != null) {
            this.gC.removeView(this.gv);
        }
        Z();
    }

    public void onPause() {
        if (this.gu != null) {
            this.gu.pause();
        }
        X();
        if (this.gv != null && (!this.gs.isFinishing() || this.gw == null)) {
            co.a((WebView) this.gv);
        }
        Z();
    }

    public void onRestart() {
    }

    public void onResume() {
        if (this.gt != null && this.gt.gP == 4) {
            if (this.gA) {
                this.gs.finish();
            } else {
                this.gA = true;
            }
        }
        if (this.gv != null) {
            co.b(this.gv);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.gA);
    }

    public void onStart() {
    }

    public void onStop() {
        Z();
    }

    public void setRequestedOrientation(int i) {
        this.gs.setRequestedOrientation(i);
    }
}
