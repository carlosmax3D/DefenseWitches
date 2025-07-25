package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import android.content.Context;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.AdManager;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdfurikunNativeAd {
    private String mAppID;
    private Context mContext;
    private OnAdfurikunNativeAdListener mOnAdfurikunNativeAdListener;
    private AdManager.OnNativeAdLoadListener mOnNativeAdLoadListener = new AdManager.OnNativeAdLoadListener() { // from class: jp.tjkapp.adfurikunsdk.AdfurikunNativeAd.1
        @Override // jp.tjkapp.adfurikunsdk.AdManager.OnNativeAdLoadListener
        public void onLoadErr(int i) {
            AdfurikunNativeAd.this.isClick = false;
            AdfurikunNativeAd.this.mUserAdId = BuildConfig.FLAVOR;
            AdfurikunNativeAd.this.mClickURL = BuildConfig.FLAVOR;
            AdfurikunNativeAd.this.mRecClickParam = BuildConfig.FLAVOR;
            if (AdfurikunNativeAd.this.mOnAdfurikunNativeAdListener != null) {
                AdfurikunNativeAd.this.mOnAdfurikunNativeAdListener.onNativeAdLoadError(i, BuildConfig.FLAVOR);
            }
            AdfurikunNativeAd.this.isLoading = false;
        }

        @Override // jp.tjkapp.adfurikunsdk.AdManager.OnNativeAdLoadListener
        public void onLoadFinish(API_Controller2.API_ResultParam aPI_ResultParam) {
            if (aPI_ResultParam == null) {
                onLoadErr(-5);
                return;
            }
            AdfurikunNativeAd.this.isClick = false;
            AdfurikunNativeAd.this.mUserAdId = aPI_ResultParam.user_ad_id;
            AdfurikunNativeAd.this.mClickURL = aPI_ResultParam.click_url;
            AdfurikunNativeAd.this.mRecClickParam = aPI_ResultParam.rec_click_param;
            if (AdfurikunNativeAd.this.mOnAdfurikunNativeAdListener != null) {
                if (aPI_ResultParam.nativeAdInfo != null) {
                    AdfurikunNativeAd.this.mOnAdfurikunNativeAdListener.onNativeAdLoadFinish(aPI_ResultParam.nativeAdInfo, aPI_ResultParam.adnetwork_key);
                } else {
                    AdfurikunNativeAd.this.mOnAdfurikunNativeAdListener.onNativeAdLoadError(aPI_ResultParam.err, aPI_ResultParam.adnetwork_key);
                }
            }
            AdfurikunNativeAd.this.isLoading = false;
        }
    };
    private boolean isLoading = false;
    private boolean isClick = false;
    private String mUserAdId = BuildConfig.FLAVOR;
    private String mClickURL = BuildConfig.FLAVOR;
    private String mRecClickParam = BuildConfig.FLAVOR;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public static class AdfurikunNativeAdInfo {
        public String img_url = BuildConfig.FLAVOR;
        public String link_url = BuildConfig.FLAVOR;
        public String title = BuildConfig.FLAVOR;
        public String text = BuildConfig.FLAVOR;
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface OnAdfurikunNativeAdListener {
        void onNativeAdLoadError(int i, String str);

        void onNativeAdLoadFinish(AdfurikunNativeAdInfo adfurikunNativeAdInfo, String str);
    }

    public AdfurikunNativeAd(Activity activity, String str, OnAdfurikunNativeAdListener onAdfurikunNativeAdListener) {
        this.mContext = activity.getApplicationContext();
        this.mAppID = str;
        this.mOnAdfurikunNativeAdListener = onAdfurikunNativeAdListener;
    }

    public boolean getNativeAd() {
        if (this.isLoading) {
            return false;
        }
        this.isLoading = true;
        AdManager.getInstance(this.mContext).getNativeAd(this.mAppID, this.mOnNativeAdLoadListener);
        return true;
    }

    public void recClick() {
        if (this.isClick) {
            return;
        }
        this.isClick = true;
        if (this.mUserAdId == null || this.mUserAdId.length() <= 0) {
            return;
        }
        new RecTask2(this.mContext, this.mAppID, this.mUserAdId, 0, null, BuildConfig.FLAVOR, this.mRecClickParam).forceLoad();
    }
}
