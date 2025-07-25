package com.tapjoy;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import java.util.Hashtable;

public final class TapjoyConnect {
    private static final String TAG = "TapjoyConnect";
    /* access modifiers changed from: private */
    public static volatile boolean hasConnected = false;
    /* access modifiers changed from: private */
    public static TapjoyCache tapjoyCache = null;
    private static TapjoyConnect tapjoyConnectInstance = null;
    /* access modifiers changed from: private */
    public static TapjoyDisplayAd tapjoyDisplayAd = null;
    /* access modifiers changed from: private */
    public static TapjoyEvent tapjoyEvent = null;
    /* access modifiers changed from: private */
    public static TapjoyFullScreenAd tapjoyFullScreenAd = null;
    /* access modifiers changed from: private */
    public static TJCOffers tapjoyOffers = null;
    /* access modifiers changed from: private */
    public static TJPoints tapjoyPoints = null;
    /* access modifiers changed from: private */
    public static TapjoyVideo tapjoyVideo = null;

    private TapjoyConnect(Context context, String str, String str2, Hashtable<String, ?> hashtable, TapjoyConnectNotifier tapjoyConnectNotifier) throws TapjoyException {
        TapjoyConnectCore.requestTapjoyConnect(context, str, str2, hashtable, tapjoyConnectNotifier);
    }

    public static void enableLogging(boolean z) {
        TapjoyLog.enableLogging(z);
    }

    public static TapjoyConnect getTapjoyConnectInstance() {
        if (hasConnected) {
            return tapjoyConnectInstance;
        }
        Log.e(TAG, "----------------------------------------");
        Log.e(TAG, "ERROR -- call requestTapjoyConnect and make sure to receive the Tapjoy connectSuccess response from the TapjoyConnectNotifier before calling any other Tapjoy methods");
        Log.e(TAG, "----------------------------------------");
        return null;
    }

    public static boolean requestTapjoyConnect(Context context, String str, String str2) {
        return requestTapjoyConnect(context, str, str2, (Hashtable<String, ?>) null);
    }

    public static boolean requestTapjoyConnect(Context context, String str, String str2, Hashtable<String, ?> hashtable) {
        return requestTapjoyConnect(context, str, str2, hashtable, (TapjoyConnectNotifier) null);
    }

    public static boolean requestTapjoyConnect(final Context context, String str, String str2, Hashtable<String, ?> hashtable, final TapjoyConnectNotifier tapjoyConnectNotifier) {
        TapjoyConnectCore.setSDKType("event");
        try {
            tapjoyConnectInstance = new TapjoyConnect(context, str, str2, hashtable, new TapjoyConnectNotifier() {
                public void connectFail() {
                    if (tapjoyConnectNotifier != null) {
                        tapjoyConnectNotifier.connectFail();
                    }
                }

                public void connectSuccess() {
                    TJCOffers unused = TapjoyConnect.tapjoyOffers = new TJCOffers(context);
                    TJPoints unused2 = TapjoyConnect.tapjoyPoints = new TJPoints(context);
                    TapjoyFullScreenAd unused3 = TapjoyConnect.tapjoyFullScreenAd = new TapjoyFullScreenAd(context);
                    TapjoyDisplayAd unused4 = TapjoyConnect.tapjoyDisplayAd = new TapjoyDisplayAd(context);
                    TapjoyCache unused5 = TapjoyConnect.tapjoyCache = new TapjoyCache(context);
                    TapjoyEvent unused6 = TapjoyConnect.tapjoyEvent = new TapjoyEvent(context);
                    TapjoyVideo unused7 = TapjoyConnect.tapjoyVideo = new TapjoyVideo(context);
                    try {
                        TJEventOptimizer.init(context);
                        boolean unused8 = TapjoyConnect.hasConnected = true;
                        if (tapjoyConnectNotifier != null) {
                            tapjoyConnectNotifier.connectSuccess();
                        }
                    } catch (InterruptedException e) {
                        connectFail();
                    }
                }
            });
            return true;
        } catch (TapjoyIntegrationException e) {
            Log.e(TAG, "IntegrationException: " + e.getMessage());
            if (tapjoyConnectNotifier != null) {
                tapjoyConnectNotifier.connectFail();
            }
            return false;
        } catch (TapjoyException e2) {
            Log.e(TAG, "TapjoyException: " + e2.getMessage());
            if (tapjoyConnectNotifier != null) {
                tapjoyConnectNotifier.connectFail();
            }
            return false;
        }
    }

    public void actionComplete(String str) {
        TapjoyConnectCore.getInstance().actionComplete(str);
    }

    public void appPause() {
        TapjoyConnectCore.getInstance().appPause();
    }

    public void appResume() {
        TapjoyConnectCore.getInstance().appResume();
    }

    public void awardTapPoints(int i, TapjoyAwardPointsNotifier tapjoyAwardPointsNotifier) {
        if (tapjoyPoints != null && hasConnected) {
            tapjoyPoints.awardTapPoints(i, tapjoyAwardPointsNotifier);
        }
    }

    public void cacheVideos() {
        if (tapjoyVideo != null && hasConnected) {
            tapjoyVideo.cacheVideos();
        }
    }

    public void enableDisplayAdAutoRefresh(boolean z) {
        if (tapjoyDisplayAd != null && hasConnected) {
            tapjoyDisplayAd.enableAutoRefresh(z);
        }
    }

    public void enablePaidAppWithActionID(String str) {
        TapjoyConnectCore.getInstance().enablePaidAppWithActionID(str);
    }

    public String getAppID() {
        return TapjoyConnectCore.getAppID();
    }

    public float getCurrencyMultiplier() {
        return TapjoyConnectCore.getInstance().getCurrencyMultiplier();
    }

    public void getDisplayAd(Activity activity, TapjoyDisplayAdNotifier tapjoyDisplayAdNotifier) {
        if (tapjoyDisplayAd != null && hasConnected) {
            tapjoyDisplayAd.getDisplayAd(activity, tapjoyDisplayAdNotifier);
        }
    }

    public void getDisplayAd(TapjoyDisplayAdNotifier tapjoyDisplayAdNotifier) {
        if (tapjoyDisplayAd != null && hasConnected) {
            tapjoyDisplayAd.getDisplayAd((Activity) null, tapjoyDisplayAdNotifier);
        }
    }

    public void getDisplayAdWithCurrencyID(Activity activity, String str, TapjoyDisplayAdNotifier tapjoyDisplayAdNotifier) {
        if (tapjoyDisplayAd != null && hasConnected) {
            tapjoyDisplayAd.getDisplayAd(activity, str, tapjoyDisplayAdNotifier);
        }
    }

    public void getDisplayAdWithCurrencyID(String str, TapjoyDisplayAdNotifier tapjoyDisplayAdNotifier) {
        if (tapjoyDisplayAd != null && hasConnected) {
            tapjoyDisplayAd.getDisplayAd((Activity) null, str, tapjoyDisplayAdNotifier);
        }
    }

    public void getFullScreenAd(TapjoyFullScreenAdNotifier tapjoyFullScreenAdNotifier) {
        if (tapjoyFullScreenAd != null && hasConnected) {
            tapjoyFullScreenAd.getFullScreenAd(tapjoyFullScreenAdNotifier);
        }
    }

    public void getFullScreenAdWithCurrencyID(String str, TapjoyFullScreenAdNotifier tapjoyFullScreenAdNotifier) {
        if (tapjoyFullScreenAd != null && hasConnected) {
            tapjoyFullScreenAd.getFullScreenAd(str, tapjoyFullScreenAdNotifier);
        }
    }

    public void getTapPoints(TapjoyNotifier tapjoyNotifier) {
        if (tapjoyPoints != null && hasConnected) {
            tapjoyPoints.getTapPoints(tapjoyNotifier);
        }
    }

    public String getUserID() {
        return TapjoyConnectCore.getUserID();
    }

    public void sendIAPEvent(String str, float f, int i, String str2) {
        if (tapjoyEvent != null && hasConnected) {
            tapjoyEvent.sendIAPEvent(str, f, i, str2);
        }
    }

    public void sendSegmentationParams(Hashtable<String, Object> hashtable) {
        TapjoyConnectCore.setSegmentationParams(hashtable);
        TapjoyConnectCore.getInstance().callConnect();
    }

    public void sendShutDownEvent() {
        if (tapjoyEvent != null && hasConnected) {
            tapjoyEvent.sendShutDownEvent();
        }
    }

    public void setCurrencyMultiplier(float f) {
        TapjoyConnectCore.getInstance().setCurrencyMultiplier(f);
    }

    public void setDisplayAdSize(String str) {
        if (tapjoyDisplayAd != null && hasConnected) {
            tapjoyDisplayAd.setDisplayAdSize(str);
        }
    }

    public void setEarnedPointsNotifier(TapjoyEarnedPointsNotifier tapjoyEarnedPointsNotifier) {
        if (tapjoyPoints != null && hasConnected) {
            tapjoyPoints.setEarnedPointsNotifier(tapjoyEarnedPointsNotifier);
        }
    }

    public void setTapjoyViewNotifier(TapjoyViewNotifier tapjoyViewNotifier) {
        TapjoyConnectCore.getInstance().setTapjoyViewNotifier(tapjoyViewNotifier);
    }

    public void setUserID(String str) {
        TapjoyConnectCore.setUserID(str);
    }

    public void setVideoCacheCount(int i) {
        if (tapjoyVideo != null && hasConnected) {
            tapjoyVideo.setVideoCacheCount(i);
        }
    }

    public void setVideoNotifier(TapjoyVideoNotifier tapjoyVideoNotifier) {
        if (tapjoyVideo != null && hasConnected) {
            tapjoyVideo.setVideoNotifier(tapjoyVideoNotifier);
        }
    }

    public void showFullScreenAd() {
        if (tapjoyFullScreenAd != null && hasConnected) {
            tapjoyFullScreenAd.showFullScreenAd();
        }
    }

    public void showOffers() {
        if (tapjoyOffers != null && hasConnected) {
            tapjoyOffers.showOffers((TapjoyOffersNotifier) null);
        }
    }

    public void showOffers(TapjoyOffersNotifier tapjoyOffersNotifier) {
        if (tapjoyOffers != null && hasConnected) {
            tapjoyOffers.showOffers(tapjoyOffersNotifier);
        }
    }

    public void showOffersWithCurrencyID(String str, boolean z) {
        if (tapjoyOffers != null && hasConnected) {
            tapjoyOffers.showOffersWithCurrencyID(str, z, (TapjoyOffersNotifier) null);
        }
    }

    public void showOffersWithCurrencyID(String str, boolean z, TapjoyOffersNotifier tapjoyOffersNotifier) {
        if (tapjoyOffers != null && hasConnected) {
            tapjoyOffers.showOffersWithCurrencyID(str, z, tapjoyOffersNotifier);
        }
    }

    public void spendTapPoints(int i, TapjoySpendPointsNotifier tapjoySpendPointsNotifier) {
        if (tapjoyPoints != null && hasConnected) {
            tapjoyPoints.spendTapPoints(i, tapjoySpendPointsNotifier);
        }
    }
}
