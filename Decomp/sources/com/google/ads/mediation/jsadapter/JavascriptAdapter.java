package com.google.ads.mediation.jsadapter;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.mediation.EmptyNetworkExtras;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.android.gms.internal.ct;

public final class JavascriptAdapter implements MediationBannerAdapter<EmptyNetworkExtras, JavascriptServerParameters> {
    private WebView C;
    private FrameLayout D;
    private boolean E;
    private MediationBannerListener k;
    private int v;
    private int w;

    public void destroy() {
        this.E = true;
    }

    public Class<EmptyNetworkExtras> getAdditionalParametersType() {
        return EmptyNetworkExtras.class;
    }

    public View getBannerView() {
        return this.D;
    }

    public Class<JavascriptServerParameters> getServerParametersType() {
        return JavascriptServerParameters.class;
    }

    public WebView getWebView() {
        return this.C;
    }

    public int getWebViewHeight() {
        return this.v;
    }

    public int getWebViewWidth() {
        return this.w;
    }

    public void passbackReceived() {
        ct.r("Passback received");
        sendAdNotReceivedUpdate();
    }

    public void requestBannerAd(MediationBannerListener mediationBannerListener, Activity activity, JavascriptServerParameters javascriptServerParameters, AdSize adSize, MediationAdRequest mediationAdRequest, EmptyNetworkExtras emptyNetworkExtras) {
        this.k = mediationBannerListener;
        this.v = javascriptServerParameters.height != null ? javascriptServerParameters.height.intValue() : adSize.getHeightInPixels(activity);
        this.w = javascriptServerParameters.width != null ? javascriptServerParameters.width.intValue() : adSize.getWidthInPixels(activity);
        this.E = false;
        this.C = new WebView(activity);
        this.C.getSettings().setJavaScriptEnabled(true);
        this.C.setWebViewClient(new BannerWebViewClient(this, javascriptServerParameters.passBackUrl));
        this.C.setBackgroundColor(0);
        this.D = new FrameLayout(activity);
        this.D.addView(this.C, new FrameLayout.LayoutParams(this.w, this.v, 17));
        this.C.loadDataWithBaseURL((String) null, javascriptServerParameters.htmlScript, "text/html", "utf-8", (String) null);
    }

    public void sendAdNotReceivedUpdate() {
        if (!this.E) {
            this.E = true;
            this.k.onFailedToReceiveAd(this, AdRequest.ErrorCode.NO_FILL);
        }
    }

    public void sendAdReceivedUpdate() {
        if (!this.E) {
            this.E = true;
            this.k.onReceivedAd(this);
        }
    }

    public boolean shouldStopAdCheck() {
        return this.E;
    }

    public void startCheckingForAd() {
        new AdViewCheckTask(this, 200, 100).start();
    }
}
