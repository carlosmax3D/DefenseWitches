package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class cz extends cx {
    public cz(cw cwVar, boolean z) {
        super(cwVar, z);
    }

    private static WebResourceResponse d(Context context, String str, String str2) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
        try {
            co.a(context, str, true, httpURLConnection);
            httpURLConnection.connect();
            return new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream(co.a((Readable) new InputStreamReader(httpURLConnection.getInputStream())).getBytes("UTF-8")));
        } finally {
            httpURLConnection.disconnect();
        }
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        try {
            if (!"mraid.js".equalsIgnoreCase(new File(str).getName())) {
                return super.shouldInterceptRequest(webView, str);
            }
            if (!(webView instanceof cw)) {
                ct.v("Tried to intercept request from a WebView that wasn't an AdWebView.");
                return super.shouldInterceptRequest(webView, str);
            }
            cw cwVar = (cw) webView;
            cwVar.aC().Y();
            if (cwVar.y().eG) {
                ct.u("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_interstitial.js)");
                return d(cwVar.getContext(), this.gv.aE().iJ, "http://media.admob.com/mraid/v1/mraid_app_interstitial.js");
            } else if (cwVar.aF()) {
                ct.u("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_expanded_banner.js)");
                return d(cwVar.getContext(), this.gv.aE().iJ, "http://media.admob.com/mraid/v1/mraid_app_expanded_banner.js");
            } else {
                ct.u("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_banner.js)");
                return d(cwVar.getContext(), this.gv.aE().iJ, "http://media.admob.com/mraid/v1/mraid_app_banner.js");
            }
        } catch (IOException e) {
            ct.v("Could not fetching MRAID JS. " + e.getMessage());
            return super.shouldInterceptRequest(webView, str);
        }
    }
}
