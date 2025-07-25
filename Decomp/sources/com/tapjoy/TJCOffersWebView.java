package com.tapjoy;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.webkit.WebView;

@TargetApi(9)
public class TJCOffersWebView extends TJAdUnitView {
    private static final String TAG = "Offers";
    private boolean isInitialLoad = true;

    public void handleWebViewOnPageFinished(WebView webView, String str) {
        if (this.isInitialLoad) {
            this.isInitialLoad = false;
            TJCOffers.getOffersNotifierResponse();
            TapjoyLog.i(TAG, "getOffersNotifierResponse fired");
            return;
        }
        super.handleWebViewOnPageFinished(webView, str);
    }

    public void handleWebViewOnReceivedError(WebView webView, int i, String str, String str2) {
        if (this.isInitialLoad) {
            this.isInitialLoad = false;
            finish();
            TJCOffers.getOffersNotifierResponseFailed("Failed to load offers from server");
            TapjoyLog.i(TAG, "getOffersNotifierResponseFailed fired");
            return;
        }
        super.handleWebViewOnReceivedError(webView, i, str, str2);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TapjoyConnectCore.viewDidOpen(0);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (!this.skipOfferWall && isFinishing()) {
            TapjoyConnectCore.viewWillClose(0);
            TapjoyConnectCore.viewDidClose(0);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.offersURL != null && this.webView != null && this.pauseCalled && this.redirectedActivity) {
            TapjoyLog.i(TAG, "onResume reload offer wall: " + this.offersURL);
            this.webView.loadUrl(this.offersURL);
            this.historyIndex++;
        }
    }
}
