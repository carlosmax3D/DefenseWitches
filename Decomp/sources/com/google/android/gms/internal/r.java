package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.ViewSwitcher;
import com.google.android.gms.dynamic.b;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bu;
import com.google.android.gms.internal.bz;
import com.tapjoy.TJAdUnitConstants;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;

public final class r extends ac.a implements al, av, bn, bq, bu.a, q {
    private final bb ed;
    private final a ee;
    private final s ef = new s(this);

    private static final class a {
        public final String adUnitId;
        public final ViewSwitcher eg;
        public final Context eh;
        public final h ei;
        public final cu ej;
        public ab ek;
        public cm el;
        public x em;
        public cj en;
        public ae eo;

        public a(Context context, x xVar, String str, cu cuVar) {
            if (xVar.eG) {
                this.eg = null;
            } else {
                this.eg = new ViewSwitcher(context);
                this.eg.setMinimumWidth(xVar.widthPixels);
                this.eg.setMinimumHeight(xVar.heightPixels);
                this.eg.setVisibility(4);
            }
            this.em = xVar;
            this.adUnitId = str;
            this.eh = context;
            this.ei = new h(g.a(cuVar.iJ, context));
            this.ej = cuVar;
        }
    }

    public r(Context context, x xVar, String str, bb bbVar, cu cuVar) {
        this.ee = new a(context, xVar, str, cuVar);
        this.ed = bbVar;
        ct.t("Use AdRequest.Builder.addTestDevice(\"" + cs.l(context) + "\") to get test ads on this device.");
        co.i(context);
    }

    private void I() {
        ct.t("Ad closing.");
        if (this.ee.ek != null) {
            try {
                this.ee.ek.onAdClosed();
            } catch (RemoteException e) {
                ct.b("Could not call AdListener.onAdClosed().", e);
            }
        }
    }

    private void J() {
        ct.t("Ad leaving application.");
        if (this.ee.ek != null) {
            try {
                this.ee.ek.onAdLeftApplication();
            } catch (RemoteException e) {
                ct.b("Could not call AdListener.onAdLeftApplication().", e);
            }
        }
    }

    private void K() {
        ct.t("Ad opening.");
        if (this.ee.ek != null) {
            try {
                this.ee.ek.onAdOpened();
            } catch (RemoteException e) {
                ct.b("Could not call AdListener.onAdOpened().", e);
            }
        }
    }

    private void L() {
        ct.t("Ad finished loading.");
        if (this.ee.ek != null) {
            try {
                this.ee.ek.onAdLoaded();
            } catch (RemoteException e) {
                ct.b("Could not call AdListener.onAdLoaded().", e);
            }
        }
    }

    private boolean M() {
        boolean z = true;
        if (!co.a(this.ee.eh.getPackageManager(), this.ee.eh.getPackageName(), "android.permission.INTERNET")) {
            if (!this.ee.em.eG) {
                cs.a(this.ee.eg, this.ee.em, "Missing internet permission in AndroidManifest.xml.", "Missing internet permission in AndroidManifest.xml. You must have the following declaration: <uses-permission android:name=\"android.permission.INTERNET\" />");
            }
            z = false;
        }
        if (!co.h(this.ee.eh)) {
            if (!this.ee.em.eG) {
                cs.a(this.ee.eg, this.ee.em, "Missing AdActivity with android:configChanges in AndroidManifest.xml.", "Missing AdActivity with android:configChanges in AndroidManifest.xml. You must have the following declaration within the <application> element: <activity android:name=\"com.google.android.gms.ads.AdActivity\" android:configChanges=\"keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize\" />");
            }
            z = false;
        }
        if (!z && !this.ee.em.eG) {
            this.ee.eg.setVisibility(0);
        }
        return z;
    }

    private void N() {
        if (this.ee.en == null) {
            ct.v("Ad state was null when trying to ping click URLs.");
            return;
        }
        ct.r("Pinging click URLs.");
        if (this.ee.en.fK != null) {
            co.a(this.ee.eh, this.ee.ej.iJ, this.ee.en.fK);
        }
        if (this.ee.en.is != null && this.ee.en.is.fK != null) {
            az.a(this.ee.eh, this.ee.ej.iJ, this.ee.en, this.ee.adUnitId, false, this.ee.en.is.fK);
        }
    }

    private void O() {
        if (this.ee.en != null) {
            this.ee.en.gJ.destroy();
            this.ee.en = null;
        }
    }

    private void a(int i) {
        ct.v("Failed to load ad: " + i);
        if (this.ee.ek != null) {
            try {
                this.ee.ek.onAdFailedToLoad(i);
            } catch (RemoteException e) {
                ct.b("Could not call AdListener.onAdFailedToLoad().", e);
            }
        }
    }

    private void b(View view) {
        this.ee.eg.addView(view, new ViewGroup.LayoutParams(-2, -2));
    }

    private void b(boolean z) {
        if (this.ee.en == null) {
            ct.v("Ad state was null when trying to ping impression URLs.");
            return;
        }
        ct.r("Pinging Impression URLs.");
        if (this.ee.en.fL != null) {
            co.a(this.ee.eh, this.ee.ej.iJ, this.ee.en.fL);
        }
        if (!(this.ee.en.is == null || this.ee.en.is.fL == null)) {
            az.a(this.ee.eh, this.ee.ej.iJ, this.ee.en, this.ee.adUnitId, z, this.ee.en.is.fL);
        }
        if (this.ee.en.gb != null && this.ee.en.gb.fG != null) {
            az.a(this.ee.eh, this.ee.ej.iJ, this.ee.en, this.ee.adUnitId, z, this.ee.en.gb.fG);
        }
    }

    private boolean b(cj cjVar) {
        if (cjVar.hy) {
            try {
                View view = (View) c.b(cjVar.gc.getView());
                View nextView = this.ee.eg.getNextView();
                if (nextView != null) {
                    this.ee.eg.removeView(nextView);
                }
                try {
                    b(view);
                } catch (Throwable th) {
                    ct.b("Could not add mediation view to view hierarchy.", th);
                    return false;
                }
            } catch (RemoteException e) {
                ct.b("Could not get View from mediation adapter.", e);
                return false;
            }
        } else if (cjVar.it != null) {
            cjVar.gJ.a(cjVar.it);
            this.ee.eg.removeAllViews();
            this.ee.eg.setMinimumWidth(cjVar.it.widthPixels);
            this.ee.eg.setMinimumHeight(cjVar.it.heightPixels);
            b((View) cjVar.gJ);
        }
        if (this.ee.eg.getChildCount() > 1) {
            this.ee.eg.showNext();
        }
        if (this.ee.en != null) {
            View nextView2 = this.ee.eg.getNextView();
            if (nextView2 instanceof cw) {
                ((cw) nextView2).a(this.ee.eh, this.ee.em);
            } else if (nextView2 != null) {
                this.ee.eg.removeView(nextView2);
            }
            if (this.ee.en.gc != null) {
                try {
                    this.ee.en.gc.destroy();
                } catch (RemoteException e2) {
                    ct.v("Could not destroy previous mediation adapter.");
                }
            }
        }
        this.ee.eg.setVisibility(0);
        return true;
    }

    private bz.a c(v vVar) {
        PackageInfo packageInfo;
        Bundle bundle;
        ApplicationInfo applicationInfo = this.ee.eh.getApplicationInfo();
        try {
            packageInfo = this.ee.eh.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if (this.ee.em.eG || this.ee.eg.getParent() == null) {
            bundle = null;
        } else {
            int[] iArr = new int[2];
            this.ee.eg.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            DisplayMetrics displayMetrics = this.ee.eh.getResources().getDisplayMetrics();
            int width = this.ee.eg.getWidth();
            int height = this.ee.eg.getHeight();
            int i3 = (!this.ee.eg.isShown() || i + width <= 0 || i2 + height <= 0 || i > displayMetrics.widthPixels || i2 > displayMetrics.heightPixels) ? 0 : 1;
            bundle = new Bundle(5);
            bundle.putInt("x", i);
            bundle.putInt("y", i2);
            bundle.putInt(FluctConstants.XML_NODE_WIDTH, width);
            bundle.putInt(FluctConstants.XML_NODE_HEIGHT, height);
            bundle.putInt(TJAdUnitConstants.String.VISIBLE, i3);
        }
        return new bz.a(bundle, vVar, this.ee.em, this.ee.adUnitId, applicationInfo, packageInfo, ck.ar(), ck.iu, this.ee.ej);
    }

    public void A() {
        if (this.ee.em.eG) {
            O();
        }
        I();
    }

    public void B() {
        if (this.ee.em.eG) {
            b(false);
        }
        K();
    }

    public void C() {
        w();
    }

    public void D() {
        A();
    }

    public void E() {
        z();
    }

    public void F() {
        B();
    }

    public void G() {
        if (this.ee.en != null) {
            ct.v("Mediation adapter " + this.ee.en.gd + " refreshed, but mediation adapters should never refresh.");
        }
        b(true);
        L();
    }

    public void H() {
        eg.N("recordManualImpression must be called on the main UI thread.");
        if (this.ee.en == null) {
            ct.v("Ad state was null when trying to ping manual tracking URLs.");
            return;
        }
        ct.r("Pinging manual tracking URLs.");
        if (this.ee.en.hA != null) {
            co.a(this.ee.eh, this.ee.ej.iJ, this.ee.en.hA);
        }
    }

    public void a(ab abVar) {
        eg.N("setAdListener must be called on the main UI thread.");
        this.ee.ek = abVar;
    }

    public void a(ae aeVar) {
        eg.N("setAppEventListener must be called on the main UI thread.");
        this.ee.eo = aeVar;
    }

    public void a(cj cjVar) {
        this.ee.el = null;
        if (cjVar.errorCode != -1) {
            boolean z = cjVar.hr.extras != null ? cjVar.hr.extras.getBoolean("_noRefresh", false) : false;
            if (this.ee.em.eG) {
                co.a((WebView) cjVar.gJ);
            } else if (!z) {
                if (cjVar.fO > 0) {
                    this.ef.a(cjVar.hr, cjVar.fO);
                } else if (cjVar.is != null && cjVar.is.fO > 0) {
                    this.ef.a(cjVar.hr, cjVar.is.fO);
                } else if (!cjVar.hy && cjVar.errorCode == 2) {
                    this.ef.d(cjVar.hr);
                }
            }
            if (!(cjVar.errorCode != 3 || cjVar.is == null || cjVar.is.fM == null)) {
                ct.r("Pinging no fill URLs.");
                az.a(this.ee.eh, this.ee.ej.iJ, cjVar, this.ee.adUnitId, false, cjVar.is.fM);
            }
            if (cjVar.errorCode != -2) {
                a(cjVar.errorCode);
            } else if (this.ee.em.eG || b(cjVar)) {
                if (!(this.ee.en == null || this.ee.en.ge == null)) {
                    this.ee.en.ge.a((av) null);
                }
                if (cjVar.ge != null) {
                    cjVar.ge.a((av) this);
                }
                this.ee.en = cjVar;
                if (cjVar.it != null) {
                    this.ee.em = cjVar.it;
                }
                if (!this.ee.em.eG) {
                    b(false);
                }
                L();
            } else {
                a(0);
            }
        }
    }

    public void a(x xVar) {
        eg.N("setAdSize must be called on the main UI thread.");
        this.ee.em = xVar;
        if (this.ee.en != null) {
            this.ee.en.gJ.a(xVar);
        }
        if (this.ee.eg.getChildCount() > 1) {
            this.ee.eg.removeView(this.ee.eg.getNextView());
        }
        this.ee.eg.setMinimumWidth(xVar.widthPixels);
        this.ee.eg.setMinimumHeight(xVar.heightPixels);
        this.ee.eg.requestLayout();
    }

    public boolean a(v vVar) {
        cw a2;
        cw cwVar;
        eg.N("loadAd must be called on the main UI thread.");
        if (this.ee.el != null) {
            ct.v("An ad request is already in progress. Aborting.");
            return false;
        } else if (this.ee.em.eG && this.ee.en != null) {
            ct.v("An interstitial is already loading. Aborting.");
            return false;
        } else if (!M()) {
            return false;
        } else {
            ct.t("Starting ad request.");
            this.ef.cancel();
            bz.a c = c(vVar);
            if (this.ee.em.eG) {
                cw a3 = cw.a(this.ee.eh, this.ee.em, false, false, this.ee.ei, this.ee.ej);
                a3.aC().a(this, (bn) null, this, this, true);
                cwVar = a3;
            } else {
                View nextView = this.ee.eg.getNextView();
                if (nextView instanceof cw) {
                    a2 = (cw) nextView;
                    a2.a(this.ee.eh, this.ee.em);
                } else {
                    if (nextView != null) {
                        this.ee.eg.removeView(nextView);
                    }
                    a2 = cw.a(this.ee.eh, this.ee.em, false, false, this.ee.ei, this.ee.ej);
                    if (this.ee.em.eH == null) {
                        b((View) a2);
                    }
                }
                a2.aC().a(this, this, this, this, false);
                cwVar = a2;
            }
            this.ee.el = bu.a(this.ee.eh, c, this.ee.ei, cwVar, this.ed, this);
            return true;
        }
    }

    public void b(v vVar) {
        ViewParent parent = this.ee.eg.getParent();
        if (!(parent instanceof View) || !((View) parent).isShown() || !co.at()) {
            ct.t("Ad is not visible. Not refreshing ad.");
            this.ef.d(vVar);
            return;
        }
        a(vVar);
    }

    public void destroy() {
        eg.N("destroy must be called on the main UI thread.");
        this.ee.ek = null;
        this.ee.eo = null;
        this.ef.cancel();
        stopLoading();
        if (this.ee.eg != null) {
            this.ee.eg.removeAllViews();
        }
        if (this.ee.en != null && this.ee.en.gJ != null) {
            this.ee.en.gJ.destroy();
        }
    }

    public boolean isReady() {
        eg.N("isLoaded must be called on the main UI thread.");
        return this.ee.el == null && this.ee.en != null;
    }

    public void onAppEvent(String str, String str2) {
        if (this.ee.eo != null) {
            try {
                this.ee.eo.onAppEvent(str, str2);
            } catch (RemoteException e) {
                ct.b("Could not call the AppEventListener.", e);
            }
        }
    }

    public void pause() {
        eg.N("pause must be called on the main UI thread.");
        if (this.ee.en != null) {
            co.a((WebView) this.ee.en.gJ);
        }
    }

    public void resume() {
        eg.N("resume must be called on the main UI thread.");
        if (this.ee.en != null) {
            co.b(this.ee.en.gJ);
        }
    }

    public void showInterstitial() {
        eg.N("showInterstitial must be called on the main UI thread.");
        if (!this.ee.em.eG) {
            ct.v("Cannot call showInterstitial on a banner ad.");
        } else if (this.ee.en == null) {
            ct.v("The interstitial has not loaded.");
        } else if (this.ee.en.gJ.aF()) {
            ct.v("The interstitial is already showing.");
        } else {
            this.ee.en.gJ.l(true);
            if (this.ee.en.hy) {
                try {
                    this.ee.en.gc.showInterstitial();
                } catch (RemoteException e) {
                    ct.b("Could not show interstitial.", e);
                    O();
                }
            } else {
                bk.a(this.ee.eh, new bm(this, this, this, this.ee.en.gJ, this.ee.en.orientation, this.ee.ej));
            }
        }
    }

    public void stopLoading() {
        eg.N("stopLoading must be called on the main UI thread.");
        if (this.ee.en != null) {
            this.ee.en.gJ.stopLoading();
            this.ee.en = null;
        }
        if (this.ee.el != null) {
            this.ee.el.cancel();
        }
    }

    public void w() {
        N();
    }

    public b x() {
        eg.N("getAdFrame must be called on the main UI thread.");
        return c.h(this.ee.eg);
    }

    public x y() {
        eg.N("getAdSize must be called on the main UI thread.");
        return this.ee.em;
    }

    public void z() {
        J();
    }
}
