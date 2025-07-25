package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.net.Uri;
import android.os.Build;
import android.view.MotionEvent;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.drive.DriveFile;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;

public final class cw extends WebView implements DownloadListener {
    private x fU;
    private final cu fV;
    private final Object fx = new Object();
    private final h he;
    private final cx iN;
    private final a iO;
    private bk iP;
    private boolean iQ;
    private boolean iR;

    private static class a extends MutableContextWrapper {
        private Activity iS;
        private Context iT;

        public a(Context context) {
            super(context);
            setBaseContext(context);
        }

        public void setBaseContext(Context context) {
            this.iT = context.getApplicationContext();
            this.iS = context instanceof Activity ? (Activity) context : null;
            super.setBaseContext(this.iT);
        }

        public void startActivity(Intent intent) {
            if (this.iS != null) {
                this.iS.startActivity(intent);
                return;
            }
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            this.iT.startActivity(intent);
        }
    }

    private cw(a aVar, x xVar, boolean z, boolean z2, h hVar, cu cuVar) {
        super(aVar);
        this.iO = aVar;
        this.fU = xVar;
        this.iQ = z;
        this.he = hVar;
        this.fV = cuVar;
        setBackgroundColor(0);
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        co.a((Context) aVar, cuVar.iJ, settings);
        if (Build.VERSION.SDK_INT >= 17) {
            cq.a(getContext(), settings);
        } else if (Build.VERSION.SDK_INT >= 11) {
            cp.a(getContext(), settings);
        }
        setDownloadListener(this);
        if (Build.VERSION.SDK_INT >= 11) {
            this.iN = new cz(this, z2);
        } else {
            this.iN = new cx(this, z2);
        }
        setWebViewClient(this.iN);
        if (Build.VERSION.SDK_INT >= 14) {
            setWebChromeClient(new da(this));
        } else if (Build.VERSION.SDK_INT >= 11) {
            setWebChromeClient(new cy(this));
        }
        aG();
    }

    public static cw a(Context context, x xVar, boolean z, boolean z2, h hVar, cu cuVar) {
        return new cw(new a(context), xVar, z, z2, hVar, cuVar);
    }

    private void aG() {
        synchronized (this.fx) {
            if (this.iQ || this.fU.eG) {
                if (Build.VERSION.SDK_INT < 14) {
                    ct.r("Disabling hardware acceleration on an overlay.");
                    aH();
                } else {
                    ct.r("Enabling hardware acceleration on an overlay.");
                    aI();
                }
            } else if (Build.VERSION.SDK_INT < 18) {
                ct.r("Disabling hardware acceleration on an AdView.");
                aH();
            } else {
                ct.r("Enabling hardware acceleration on an AdView.");
                aI();
            }
        }
    }

    private void aH() {
        synchronized (this.fx) {
            if (!this.iR && Build.VERSION.SDK_INT >= 11) {
                cp.c(this);
            }
            this.iR = true;
        }
    }

    private void aI() {
        synchronized (this.fx) {
            if (this.iR && Build.VERSION.SDK_INT >= 11) {
                cp.d(this);
            }
            this.iR = false;
        }
    }

    public void a(Context context, x xVar) {
        synchronized (this.fx) {
            this.iO.setBaseContext(context);
            this.iP = null;
            this.fU = xVar;
            this.iQ = false;
            co.b(this);
            loadUrl("about:blank");
            this.iN.reset();
        }
    }

    public void a(bk bkVar) {
        synchronized (this.fx) {
            this.iP = bkVar;
        }
    }

    public void a(x xVar) {
        synchronized (this.fx) {
            this.fU = xVar;
            requestLayout();
        }
    }

    public void a(String str, Map<String, ?> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:AFMA_ReceiveMessage('");
        sb.append(str);
        sb.append("'");
        if (map != null) {
            try {
                String jSONObject = co.m(map).toString();
                sb.append(",");
                sb.append(jSONObject);
            } catch (JSONException e) {
                ct.v("Could not convert AFMA event parameters to JSON.");
                return;
            }
        }
        sb.append(");");
        ct.u("Dispatching AFMA event: " + sb);
        loadUrl(sb.toString());
    }

    public void aA() {
        HashMap hashMap = new HashMap(1);
        hashMap.put(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION, this.fV.iJ);
        a("onshow", (Map<String, ?>) hashMap);
    }

    public bk aB() {
        bk bkVar;
        synchronized (this.fx) {
            bkVar = this.iP;
        }
        return bkVar;
    }

    public cx aC() {
        return this.iN;
    }

    public h aD() {
        return this.he;
    }

    public cu aE() {
        return this.fV;
    }

    public boolean aF() {
        boolean z;
        synchronized (this.fx) {
            z = this.iQ;
        }
        return z;
    }

    public void az() {
        HashMap hashMap = new HashMap(1);
        hashMap.put(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION, this.fV.iJ);
        a("onhide", (Map<String, ?>) hashMap);
    }

    public void l(boolean z) {
        synchronized (this.fx) {
            this.iQ = z;
            aG();
        }
    }

    public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(str), str4);
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ct.r("Couldn't find an Activity to view url/mimetype: " + str + " / " + str4);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r10, int r11) {
        /*
            r9 = this;
            r0 = 2147483647(0x7fffffff, float:NaN)
            r8 = 1073741824(0x40000000, float:2.0)
            r7 = 8
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            java.lang.Object r4 = r9.fx
            monitor-enter(r4)
            boolean r1 = r9.isInEditMode()     // Catch:{ all -> 0x0094 }
            if (r1 != 0) goto L_0x0016
            boolean r1 = r9.iQ     // Catch:{ all -> 0x0094 }
            if (r1 == 0) goto L_0x001b
        L_0x0016:
            super.onMeasure(r10, r11)     // Catch:{ all -> 0x0094 }
            monitor-exit(r4)     // Catch:{ all -> 0x0094 }
        L_0x001a:
            return
        L_0x001b:
            int r2 = android.view.View.MeasureSpec.getMode(r10)     // Catch:{ all -> 0x0094 }
            int r3 = android.view.View.MeasureSpec.getSize(r10)     // Catch:{ all -> 0x0094 }
            int r5 = android.view.View.MeasureSpec.getMode(r11)     // Catch:{ all -> 0x0094 }
            int r1 = android.view.View.MeasureSpec.getSize(r11)     // Catch:{ all -> 0x0094 }
            if (r2 == r6) goto L_0x002f
            if (r2 != r8) goto L_0x00ad
        L_0x002f:
            r2 = r3
        L_0x0030:
            if (r5 == r6) goto L_0x0034
            if (r5 != r8) goto L_0x0035
        L_0x0034:
            r0 = r1
        L_0x0035:
            com.google.android.gms.internal.x r5 = r9.fU     // Catch:{ all -> 0x0094 }
            int r5 = r5.widthPixels     // Catch:{ all -> 0x0094 }
            if (r5 > r2) goto L_0x0041
            com.google.android.gms.internal.x r2 = r9.fU     // Catch:{ all -> 0x0094 }
            int r2 = r2.heightPixels     // Catch:{ all -> 0x0094 }
            if (r2 <= r0) goto L_0x0097
        L_0x0041:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0094 }
            r0.<init>()     // Catch:{ all -> 0x0094 }
            java.lang.String r2 = "Not enough space to show ad. Needs "
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0094 }
            com.google.android.gms.internal.x r2 = r9.fU     // Catch:{ all -> 0x0094 }
            int r2 = r2.widthPixels     // Catch:{ all -> 0x0094 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0094 }
            java.lang.String r2 = "x"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0094 }
            com.google.android.gms.internal.x r2 = r9.fU     // Catch:{ all -> 0x0094 }
            int r2 = r2.heightPixels     // Catch:{ all -> 0x0094 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0094 }
            java.lang.String r2 = " pixels, but only has "
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0094 }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x0094 }
            java.lang.String r2 = "x"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0094 }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0094 }
            java.lang.String r1 = " pixels."
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0094 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0094 }
            com.google.android.gms.internal.ct.v(r0)     // Catch:{ all -> 0x0094 }
            int r0 = r9.getVisibility()     // Catch:{ all -> 0x0094 }
            if (r0 == r7) goto L_0x008d
            r0 = 4
            r9.setVisibility(r0)     // Catch:{ all -> 0x0094 }
        L_0x008d:
            r0 = 0
            r1 = 0
            r9.setMeasuredDimension(r0, r1)     // Catch:{ all -> 0x0094 }
        L_0x0092:
            monitor-exit(r4)     // Catch:{ all -> 0x0094 }
            goto L_0x001a
        L_0x0094:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0094 }
            throw r0
        L_0x0097:
            int r0 = r9.getVisibility()     // Catch:{ all -> 0x0094 }
            if (r0 == r7) goto L_0x00a1
            r0 = 0
            r9.setVisibility(r0)     // Catch:{ all -> 0x0094 }
        L_0x00a1:
            com.google.android.gms.internal.x r0 = r9.fU     // Catch:{ all -> 0x0094 }
            int r0 = r0.widthPixels     // Catch:{ all -> 0x0094 }
            com.google.android.gms.internal.x r1 = r9.fU     // Catch:{ all -> 0x0094 }
            int r1 = r1.heightPixels     // Catch:{ all -> 0x0094 }
            r9.setMeasuredDimension(r0, r1)     // Catch:{ all -> 0x0094 }
            goto L_0x0092
        L_0x00ad:
            r2 = r0
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cw.onMeasure(int, int):void");
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.he != null) {
            this.he.a(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setContext(Context context) {
        this.iO.setBaseContext(context);
    }

    public x y() {
        x xVar;
        synchronized (this.fx) {
            xVar = this.fU;
        }
        return xVar;
    }
}
