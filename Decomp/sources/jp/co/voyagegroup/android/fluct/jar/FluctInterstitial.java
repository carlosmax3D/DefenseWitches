package jp.co.voyagegroup.android.fluct.jar;

import android.content.Context;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctInterstitialManager;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import jp.co.voyagegroup.android.fluct.jar.util.FluctUtils;
import jp.co.voyagegroup.android.fluct.jar.util.Log;

public class FluctInterstitial {
    public static final int STATUS_AD_CLOSE = 2;
    public static final int STATUS_AD_INFO_ERROR = 8;
    public static final int STATUS_AD_RATE_CANCEL = 3;
    public static final int STATUS_AD_TAP = 1;
    public static final int STATUS_ANOTHER_ERROR = 100;
    public static final int STATUS_DISPLAY_DONE = 0;
    public static final int STATUS_MEDIA_ID_ERROR = 5;
    public static final int STATUS_NETWORK_ERROR = 4;
    public static final int STATUS_NONE_DATA = 6;
    public static final int STATUS_SIZE_ERROR = 7;
    private static final String TAG = "FluctInterstitial";
    private FluctInterstitialManager mInterstitialManager;

    public interface FluctInterstitialCallback {
        void onReceiveAdInfo(int i);
    }

    public FluctInterstitial(Context context) {
        Log.d(TAG, "FluctInterstitial : none MediaID ");
        Log.x("AD", "Create InterstitialView : none media");
        this.mInterstitialManager = new FluctInterstitialManager(context, FluctUtils.getDefaultMediaId(context));
    }

    public FluctInterstitial(Context context, String str) {
        Log.d(TAG, "FluctInterstitial : MediaID is " + str);
        Log.x("AD", "Create InterstitialView : Media");
        this.mInterstitialManager = new FluctInterstitialManager(context, str);
    }

    public void dismissIntersitialAd() {
        Log.d(TAG, "dismissIntersitialAd : ");
        Log.x("AD", "dismiss : ");
    }

    public final void setFluctInterstitialCallback(FluctInterstitialCallback fluctInterstitialCallback) {
        Log.d(TAG, "setFluctInterstitialCallback : ");
        Log.x("AD", "setCallback : ");
        this.mInterstitialManager.setFluctInterstitialCallback(fluctInterstitialCallback);
    }

    public void showIntersitialAd() {
        Log.d(TAG, "showIntersitialAd : ");
        Log.x("AD", "showIntersitial : ");
        this.mInterstitialManager.showIntersitialAd(FluctConstants.FRAME_BASE_COLOR);
    }

    public void showIntersitialAd(int i) {
        Log.d(TAG, "showIntersitialAd : frameColor is " + i);
        Log.x("AD", "showIntersitial : color ");
        this.mInterstitialManager.showIntersitialAd(-16777216 | i);
    }
}
