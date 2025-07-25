package com.google.ads.mediation.jsadapter;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.internal.ct;
import java.net.URI;
import java.net.URISyntaxException;

public final class BannerWebViewClient extends WebViewClient {
    private final String A;
    private boolean B = false;
    private final JavascriptAdapter r;

    public BannerWebViewClient(JavascriptAdapter javascriptAdapter, String str) {
        this.A = c(str);
        this.r = javascriptAdapter;
    }

    private boolean b(String str) {
        String c = c(str);
        if (TextUtils.isEmpty(c)) {
            return false;
        }
        try {
            URI uri = new URI(c);
            if ("passback".equals(uri.getScheme())) {
                ct.r("Passback received");
                this.r.sendAdNotReceivedUpdate();
                return true;
            } else if (TextUtils.isEmpty(this.A)) {
                return false;
            } else {
                URI uri2 = new URI(this.A);
                String host = uri2.getHost();
                String host2 = uri.getHost();
                String path = uri2.getPath();
                String path2 = uri.getPath();
                if (!equals(host, host2) || !equals(path, path2)) {
                    return false;
                }
                ct.r("Passback received");
                this.r.sendAdNotReceivedUpdate();
                return true;
            }
        } catch (URISyntaxException e) {
            ct.s(e.getMessage());
            return false;
        }
    }

    private String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return str.endsWith("/") ? str.substring(0, str.length() - 1) : str;
        } catch (IndexOutOfBoundsException e) {
            ct.s(e.getMessage());
            return str;
        }
    }

    private static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public void onLoadResource(WebView webView, String str) {
        ct.u("onLoadResource: " + str);
        if (!b(str)) {
            super.onLoadResource(webView, str);
        }
    }

    public void onPageFinished(WebView webView, String str) {
        ct.u("onPageFinished: " + str);
        super.onPageFinished(webView, str);
        if (!this.B) {
            this.r.startCheckingForAd();
            this.B = true;
        }
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        ct.u("shouldOverrideUrlLoading: " + str);
        if (!b(str)) {
            return false;
        }
        ct.r("shouldOverrideUrlLoading: received passback url");
        return true;
    }
}
