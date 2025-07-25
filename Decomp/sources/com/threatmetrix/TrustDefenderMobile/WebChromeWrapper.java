package com.threatmetrix.TrustDefenderMobile;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

class WebChromeWrapper extends WebChromeClient {
    private final String TAG = WebChromeWrapper.class.getName();
    private final JavaScriptInterface m_jsInterface;

    public WebChromeWrapper(JavaScriptInterface javaScriptInterface) {
        this.m_jsInterface = javaScriptInterface;
    }

    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        String str3 = this.TAG;
        new StringBuilder("onJsAlert() -").append(str2);
        this.m_jsInterface.getString(str2);
        jsResult.confirm();
        return true;
    }
}
