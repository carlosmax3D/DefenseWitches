package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.os.Handler;
import java.util.Date;
import java.util.HashMap;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.AdInfo;
import jp.tjkapp.adfurikunsdk.GetIdfaTask;
import jp.tjkapp.adfurikunsdk.GetInfoTask2;
import org.json.JSONException;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class AdManager {
    private static AdManager mInstance = null;
    private Context mContext;
    private Handler mHandler = new Handler();
    private final HashMap<String, AdInfo> mAdInfo = new HashMap<>();

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface GetInfoListener {
        void onLoadErr(int i);

        void onLoadFinish(AdInfo adInfo);
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface OnNativeAdLoadListener {
        void onLoadErr(int i);

        void onLoadFinish(API_Controller2.API_ResultParam aPI_ResultParam);
    }

    private AdManager(Context context) {
        this.mContext = context;
        getIdfa();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AdInfo getAdInfo(Context context, String str) {
        String strLoadStringFile = FileUtil.loadStringFile(FileUtil.getGetInfoFilePath(context, str));
        if (strLoadStringFile == null || strLoadStringFile.length() <= 0) {
            return null;
        }
        return ApiAccessUtil.stringToAdInfo(context, str, strLoadStringFile, true);
    }

    public static synchronized AdManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AdManager(context.getApplicationContext());
        }
        return mInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reternStatus(final OnNativeAdLoadListener onNativeAdLoadListener, final int i, final API_Controller2.API_ResultParam aPI_ResultParam) {
        new Handler().post(new Runnable() { // from class: jp.tjkapp.adfurikunsdk.AdManager.3
            @Override // java.lang.Runnable
            public void run() {
                if (onNativeAdLoadListener != null) {
                    if (aPI_ResultParam != null) {
                        onNativeAdLoadListener.onLoadFinish(aPI_ResultParam);
                    } else {
                        onNativeAdLoadListener.onLoadErr(i);
                    }
                }
            }
        });
    }

    protected synchronized void getIdfa() {
        this.mHandler.post(new Runnable() { // from class: jp.tjkapp.adfurikunsdk.AdManager.1
            @Override // java.lang.Runnable
            public void run() {
                new GetIdfaTask(new GetIdfaTask.OnLoadListener() { // from class: jp.tjkapp.adfurikunsdk.AdManager.1.1
                    @Override // jp.tjkapp.adfurikunsdk.GetIdfaTask.OnLoadListener
                    public void onLoadFinish(String str) {
                    }
                }, AdManager.this.mContext).forceLoad();
            }
        });
    }

    protected void getInfo(final String str, final GetInfoListener getInfoListener) {
        getIdfa();
        boolean z = false;
        long adLastTime = FileUtil.getAdLastTime(this.mContext, str);
        long time = new Date().getTime();
        if (adLastTime != -1 && Constants.GETINFO_RETRY_TIME > time - adLastTime) {
            AdInfo adInfo = this.mAdInfo.get(str);
            if (adInfo != null) {
                if (getInfoListener != null) {
                    getInfoListener.onLoadFinish(adInfo);
                }
                z = true;
            } else {
                AdInfo adInfo2 = getAdInfo(this.mContext, str);
                if (adInfo2 != null) {
                    this.mAdInfo.put(str, adInfo2);
                    if (getInfoListener != null) {
                        getInfoListener.onLoadFinish(adInfo2);
                    }
                    z = true;
                }
            }
        }
        if (z) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: jp.tjkapp.adfurikunsdk.AdManager.4
            @Override // java.lang.Runnable
            public void run() {
                new GetInfoTask2(new GetInfoTask2.OnLoadListener() { // from class: jp.tjkapp.adfurikunsdk.AdManager.4.1
                    @Override // jp.tjkapp.adfurikunsdk.GetInfoTask2.OnLoadListener
                    public void onLoadFinish(Integer num) {
                        if (num.intValue() != 200) {
                            if (num.intValue() == 400) {
                                if (getInfoListener != null) {
                                    getInfoListener.onLoadErr(-100);
                                    return;
                                }
                                return;
                            } else {
                                if (getInfoListener != null) {
                                    getInfoListener.onLoadErr(-6);
                                    return;
                                }
                                return;
                            }
                        }
                        AdInfo adInfo3 = AdManager.this.getAdInfo(AdManager.this.mContext, str);
                        if (adInfo3 == null) {
                            if (getInfoListener != null) {
                                getInfoListener.onLoadErr(-5);
                            }
                        } else {
                            AdManager.this.mAdInfo.put(str, adInfo3);
                            if (getInfoListener != null) {
                                getInfoListener.onLoadFinish(adInfo3);
                            }
                        }
                    }
                }, AdManager.this.mContext, str).forceLoad();
            }
        });
    }

    protected void getNativeAd(final String str, final OnNativeAdLoadListener onNativeAdLoadListener) {
        getInfo(str, new GetInfoListener() { // from class: jp.tjkapp.adfurikunsdk.AdManager.2
            @Override // jp.tjkapp.adfurikunsdk.AdManager.GetInfoListener
            public void onLoadErr(int i) {
                AdManager.this.reternStatus(onNativeAdLoadListener, i, null);
            }

            @Override // jp.tjkapp.adfurikunsdk.AdManager.GetInfoListener
            public void onLoadFinish(AdInfo adInfo) throws JSONException {
                if (adInfo == null) {
                    AdManager.this.reternStatus(onNativeAdLoadListener, -5, null);
                    return;
                }
                if (AdInfo.getAdType(adInfo.banner_kind) != 3) {
                    AdManager.this.reternStatus(onNativeAdLoadListener, AdError.NOT_NATIVE_ID, null);
                    return;
                }
                if (adInfo.getLocaleAdCt() <= 0) {
                    AdManager.this.reternStatus(onNativeAdLoadListener, AdError.SETTING_EMPTY, null);
                    return;
                }
                final AdInfo.AdInfoDetail nextAd = adInfo.getNextAd(Constants.AD_RANDOM);
                if (nextAd == null) {
                    AdManager.this.reternStatus(onNativeAdLoadListener, -5, null);
                    return;
                }
                try {
                    new API_Controller2(new API_Controller2.APILoadListener() { // from class: jp.tjkapp.adfurikunsdk.AdManager.2.1
                        @Override // jp.tjkapp.adfurikunsdk.API_Controller2.APILoadListener
                        public void onLoadFinish(API_Controller2.API_ResultParam aPI_ResultParam) {
                            if (aPI_ResultParam == null) {
                                AdManager.this.reternStatus(onNativeAdLoadListener, -5, null);
                                return;
                            }
                            if (aPI_ResultParam.err == 0 && aPI_ResultParam.nativeAdInfo != null) {
                                new RecTask2(AdManager.this.mContext, str, nextAd.user_ad_id, 1, aPI_ResultParam.imp_price, aPI_ResultParam.imp_url, aPI_ResultParam.rec_imp_param).forceLoad();
                            }
                            AdManager.this.reternStatus(onNativeAdLoadListener, aPI_ResultParam.err, aPI_ResultParam);
                        }
                    }, AdManager.this.mContext, str, adInfo.banner_kind, nextAd).forceLoad();
                } catch (Exception e) {
                    AdManager.this.reternStatus(onNativeAdLoadListener, -5, null);
                }
            }
        });
    }
}
