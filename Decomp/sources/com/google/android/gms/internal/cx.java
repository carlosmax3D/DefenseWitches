package com.google.android.gms.internal;

import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.HashMap;

public class cx extends WebViewClient {
    private al fm;
    private final Object fx = new Object();
    protected final cw gv;
    private final HashMap<String, an> iU = new HashMap<>();
    private q iV;
    private bn iW;
    private a iX;
    private boolean iY = false;
    private boolean iZ;
    private bq ja;

    public interface a {
        void a(cw cwVar);
    }

    public cx(cw cwVar, boolean z) {
        this.gv = cwVar;
        this.iZ = z;
    }

    private void a(bm bmVar) {
        bk.a(this.gv.getContext(), bmVar);
    }

    private static boolean b(Uri uri) {
        String scheme = uri.getScheme();
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    private void c(Uri uri) {
        String path = uri.getPath();
        an anVar = this.iU.get(path);
        if (anVar != null) {
            HashMap hashMap = new HashMap();
            UrlQuerySanitizer urlQuerySanitizer = new UrlQuerySanitizer();
            urlQuerySanitizer.setAllowUnregisteredParamaters(true);
            urlQuerySanitizer.setUnregisteredParameterValueSanitizer(UrlQuerySanitizer.getAllButNulLegal());
            urlQuerySanitizer.parseUrl(uri.toString());
            for (UrlQuerySanitizer.ParameterValuePair next : urlQuerySanitizer.getParameterList()) {
                hashMap.put(next.mParameter, next.mValue);
            }
            if (ct.n(2)) {
                ct.u("Received GMSG: " + path);
                for (String str : hashMap.keySet()) {
                    ct.u("  " + str + ": " + ((String) hashMap.get(str)));
                }
            }
            anVar.a(this.gv, hashMap);
            return;
        }
        ct.v("No GMSG handler found for GMSG: " + uri);
    }

    public final void Y() {
        synchronized (this.fx) {
            this.iY = false;
            this.iZ = true;
            final bk aB = this.gv.aB();
            if (aB != null) {
                if (!cs.ay()) {
                    cs.iI.post(new Runnable() {
                        public void run() {
                            aB.Y();
                        }
                    });
                } else {
                    aB.Y();
                }
            }
        }
    }

    public final void a(bj bjVar) {
        bn bnVar = null;
        boolean aF = this.gv.aF();
        q qVar = (!aF || this.gv.y().eG) ? this.iV : null;
        if (!aF) {
            bnVar = this.iW;
        }
        a(new bm(bjVar, qVar, bnVar, this.ja, this.gv.aE()));
    }

    public final void a(a aVar) {
        this.iX = aVar;
    }

    public void a(q qVar, bn bnVar, al alVar, bq bqVar, boolean z) {
        a("/appEvent", (an) new ak(alVar));
        a("/canOpenURLs", am.fn);
        a("/click", am.fo);
        a("/close", am.fp);
        a("/customClose", am.fq);
        a("/httpTrack", am.fr);
        a("/log", am.fs);
        a("/open", am.ft);
        a("/touch", am.fu);
        a("/video", am.fv);
        this.iV = qVar;
        this.iW = bnVar;
        this.fm = alVar;
        this.ja = bqVar;
        m(z);
    }

    public final void a(String str, an anVar) {
        this.iU.put(str, anVar);
    }

    public final void a(boolean z, int i) {
        a(new bm((!this.gv.aF() || this.gv.y().eG) ? this.iV : null, this.iW, this.ja, this.gv, z, i, this.gv.aE()));
    }

    public final void a(boolean z, int i, String str) {
        bn bnVar = null;
        boolean aF = this.gv.aF();
        q qVar = (!aF || this.gv.y().eG) ? this.iV : null;
        if (!aF) {
            bnVar = this.iW;
        }
        a(new bm(qVar, bnVar, this.fm, this.ja, this.gv, z, i, str, this.gv.aE()));
    }

    public final void a(boolean z, int i, String str, String str2) {
        bn bnVar = null;
        boolean aF = this.gv.aF();
        q qVar = (!aF || this.gv.y().eG) ? this.iV : null;
        if (!aF) {
            bnVar = this.iW;
        }
        a(new bm(qVar, bnVar, this.fm, this.ja, this.gv, z, i, str, str2, this.gv.aE()));
    }

    public boolean aJ() {
        boolean z;
        synchronized (this.fx) {
            z = this.iZ;
        }
        return z;
    }

    public final void m(boolean z) {
        this.iY = z;
    }

    public final void onPageFinished(WebView webView, String str) {
        if (this.iX != null) {
            this.iX.a(this.gv);
            this.iX = null;
        }
    }

    public final void reset() {
        synchronized (this.fx) {
            this.iU.clear();
            this.iV = null;
            this.iW = null;
            this.iX = null;
            this.fm = null;
            this.iY = false;
            this.iZ = false;
            this.ja = null;
        }
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        Uri uri;
        ct.u("AdWebView shouldOverrideUrlLoading: " + str);
        Uri parse = Uri.parse(str);
        if ("gmsg".equalsIgnoreCase(parse.getScheme()) && "mobileads.google.com".equalsIgnoreCase(parse.getHost())) {
            c(parse);
        } else if (this.iY && webView == this.gv && b(parse)) {
            return super.shouldOverrideUrlLoading(webView, str);
        } else {
            if (!this.gv.willNotDraw()) {
                try {
                    h aD = this.gv.aD();
                    if (aD != null && aD.a(parse)) {
                        parse = aD.a(parse, this.gv.getContext());
                    }
                    uri = parse;
                } catch (i e) {
                    ct.v("Unable to append parameter to URL: " + str);
                    uri = parse;
                }
                a(new bj("android.intent.action.VIEW", uri.toString(), (String) null, (String) null, (String) null, (String) null, (String) null));
            } else {
                ct.v("AdWebView unable to handle URL: " + str);
            }
        }
        return true;
    }
}
