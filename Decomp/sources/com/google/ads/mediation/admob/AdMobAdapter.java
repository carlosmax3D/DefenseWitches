package com.google.ads.mediation.admob;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import com.google.android.gms.internal.bg;
import com.google.android.gms.internal.cs;
import java.util.Date;
import java.util.Set;

public final class AdMobAdapter implements MediationBannerAdapter<AdMobExtras, AdMobServerParameters>, MediationInterstitialAdapter<AdMobExtras, AdMobServerParameters> {
    private AdView h;
    private InterstitialAd i;

    private static final class a extends AdListener {
        private final AdMobAdapter j;
        private final MediationBannerListener k;

        public a(AdMobAdapter adMobAdapter, MediationBannerListener mediationBannerListener) {
            this.j = adMobAdapter;
            this.k = mediationBannerListener;
        }

        public void onAdClosed() {
            this.k.onDismissScreen(this.j);
        }

        public void onAdFailedToLoad(int i) {
            this.k.onFailedToReceiveAd(this.j, bg.h(i));
        }

        public void onAdLeftApplication() {
            this.k.onLeaveApplication(this.j);
        }

        public void onAdLoaded() {
            this.k.onReceivedAd(this.j);
        }

        public void onAdOpened() {
            this.k.onClick(this.j);
            this.k.onPresentScreen(this.j);
        }
    }

    private static final class b extends AdListener {
        private final AdMobAdapter j;
        private final MediationInterstitialListener l;

        public b(AdMobAdapter adMobAdapter, MediationInterstitialListener mediationInterstitialListener) {
            this.j = adMobAdapter;
            this.l = mediationInterstitialListener;
        }

        public void onAdClosed() {
            this.l.onDismissScreen(this.j);
        }

        public void onAdFailedToLoad(int i) {
            this.l.onFailedToReceiveAd(this.j, bg.h(i));
        }

        public void onAdLeftApplication() {
            this.l.onLeaveApplication(this.j);
        }

        public void onAdLoaded() {
            this.l.onReceivedAd(this.j);
        }

        public void onAdOpened() {
            this.l.onPresentScreen(this.j);
        }
    }

    private static AdRequest a(Context context, MediationAdRequest mediationAdRequest, AdMobExtras adMobExtras, AdMobServerParameters adMobServerParameters) {
        AdRequest.Builder builder = new AdRequest.Builder();
        Date birthday = mediationAdRequest.getBirthday();
        if (birthday != null) {
            builder.setBirthday(birthday);
        }
        AdRequest.Gender gender = mediationAdRequest.getGender();
        if (gender != null) {
            builder.setGender(bg.a(gender));
        }
        Set<String> keywords = mediationAdRequest.getKeywords();
        if (keywords != null) {
            for (String addKeyword : keywords) {
                builder.addKeyword(addKeyword);
            }
        }
        if (mediationAdRequest.isTesting()) {
            builder.addTestDevice(cs.l(context));
        }
        if (adMobServerParameters.tagForChildDirectedTreatment != -1) {
            builder.tagForChildDirectedTreatment(adMobServerParameters.tagForChildDirectedTreatment == 1);
        }
        if (adMobExtras == null) {
            adMobExtras = new AdMobExtras(new Bundle());
        }
        Bundle extras = adMobExtras.getExtras();
        extras.putInt("gw", 1);
        extras.putString("mad_hac", adMobServerParameters.allowHouseAds);
        if (!TextUtils.isEmpty(adMobServerParameters.adJson)) {
            extras.putString("_ad", adMobServerParameters.adJson);
        }
        extras.putBoolean("_noRefresh", true);
        builder.addNetworkExtras(adMobExtras);
        return builder.build();
    }

    public void destroy() {
        if (this.h != null) {
            this.h.destroy();
            this.h = null;
        }
        if (this.i != null) {
            this.i = null;
        }
    }

    public Class<AdMobExtras> getAdditionalParametersType() {
        return AdMobExtras.class;
    }

    public View getBannerView() {
        return this.h;
    }

    public Class<AdMobServerParameters> getServerParametersType() {
        return AdMobServerParameters.class;
    }

    public void requestBannerAd(MediationBannerListener mediationBannerListener, Activity activity, AdMobServerParameters adMobServerParameters, AdSize adSize, MediationAdRequest mediationAdRequest, AdMobExtras adMobExtras) {
        this.h = new AdView(activity);
        this.h.setAdSize(new com.google.android.gms.ads.AdSize(adSize.getWidth(), adSize.getHeight()));
        this.h.setAdUnitId(adMobServerParameters.adUnitId);
        this.h.setAdListener(new a(this, mediationBannerListener));
        this.h.loadAd(a(activity, mediationAdRequest, adMobExtras, adMobServerParameters));
    }

    public void requestInterstitialAd(MediationInterstitialListener mediationInterstitialListener, Activity activity, AdMobServerParameters adMobServerParameters, MediationAdRequest mediationAdRequest, AdMobExtras adMobExtras) {
        this.i = new InterstitialAd(activity);
        this.i.setAdUnitId(adMobServerParameters.adUnitId);
        this.i.setAdListener(new b(this, mediationInterstitialListener));
        this.i.loadAd(a(activity, mediationAdRequest, adMobExtras, adMobServerParameters));
    }

    public void showInterstitial() {
        this.i.show();
    }
}
