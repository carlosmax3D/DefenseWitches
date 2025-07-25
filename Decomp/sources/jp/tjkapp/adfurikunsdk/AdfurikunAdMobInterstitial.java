package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.customevent.CustomEventInterstitial;
import com.google.ads.mediation.customevent.CustomEventInterstitialListener;
import java.lang.Thread;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdfurikunAdMobInterstitial implements CustomEventInterstitial, Thread.UncaughtExceptionHandler {
    private Activity mActivity = null;
    private CustomEventInterstitialListener mCustomEventInterstitialListener;

    @Override // com.google.ads.mediation.customevent.CustomEvent
    public void destroy() {
        AdMobIntersAd.adMobIntersFinalize();
    }

    @Override // com.google.ads.mediation.customevent.CustomEventInterstitial
    public void requestInterstitialAd(CustomEventInterstitialListener customEventInterstitialListener, Activity activity, String str, String str2, MediationAdRequest mediationAdRequest, Object obj) {
        this.mCustomEventInterstitialListener = customEventInterstitialListener;
        this.mActivity = activity;
        AdMobIntersAd.setAdMobIntersInfo(activity, str2);
        if (AdMobIntersAd.isLoadFinished()) {
            this.mCustomEventInterstitialListener.onReceivedAd();
        } else {
            this.mCustomEventInterstitialListener.onFailedToReceiveAd();
        }
    }

    @Override // com.google.ads.mediation.customevent.CustomEventInterstitial
    public void showInterstitial() {
        if (AdMobIntersAd.showAdMobIntersAd(this.mActivity, this.mCustomEventInterstitialListener)) {
            if (this.mCustomEventInterstitialListener != null) {
                this.mCustomEventInterstitialListener.onPresentScreen();
            }
        } else if (this.mCustomEventInterstitialListener != null) {
            this.mCustomEventInterstitialListener.onDismissScreen();
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
    }
}
