package jp.tjkapp.adfurikunsdk;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;

import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.AdInfo;
import jp.tjkapp.adfurikunsdk.AdfurikunNativeAd;
import jp.tjkapp.adfurikunsdk.FileUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_Controller2 extends AsyncTaskLoader<API_Controller2.API_ResultParam> {
    private APILoadListener mAPILoadListener;
    private AdInfo.AdInfoDetail mAdInfoDetail;
    private String mAppID;
    private int mBannerKind;
    private Context mContext;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface APILoadListener {
        void onLoadFinish(API_ResultParam aPI_ResultParam);
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public static class API_CpntrolParam {
        public String idfa;
        public FileUtil.IPUA ipua;
        public String pkg_name;
        public String useragent;

        public API_CpntrolParam(Context context) throws IOException {
            this.useragent = FileUtil.getUserAgent(context);
            this.idfa = FileUtil.getIDFA(context);
            this.pkg_name = context.getPackageName();
            this.ipua = FileUtil.getIPUA(context, LogUtil.getInstance(context), this.useragent);
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public static class API_ResultParam {
        public int err = 0;
        public String adnetwork_key = BuildConfig.FLAVOR;
        public String user_ad_id = BuildConfig.FLAVOR;
        public String html = BuildConfig.FLAVOR;
        public AdfurikunNativeAd.AdfurikunNativeAdInfo nativeAdInfo = null;
        public String imp_price = BuildConfig.FLAVOR;
        public String imp_url = BuildConfig.FLAVOR;
        public String click_url = BuildConfig.FLAVOR;
        public String rec_imp_param = BuildConfig.FLAVOR;
        public String rec_click_param = BuildConfig.FLAVOR;
    }

    public API_Controller2(APILoadListener aPILoadListener, Context context, String str, int i, AdInfo.AdInfoDetail adInfoDetail) {
        super(context);
        this.mAPILoadListener = aPILoadListener;
        this.mContext = context;
        this.mAppID = str;
        this.mBannerKind = i;
        this.mAdInfoDetail = new AdInfo.AdInfoDetail(adInfoDetail);
    }

    @Override // android.support.v4.content.Loader
    public void deliverResult(API_ResultParam aPI_ResultParam) {
        super.deliverResult(aPI_ResultParam);
        try {
            if (this.mAPILoadListener != null) {
                this.mAPILoadListener.onLoadFinish(aPI_ResultParam);
            }
        } catch (Exception e) {
        }
    }

    @Override // android.support.v4.content.AsyncTaskLoader
    public API_ResultParam loadInBackground() {
        API_ResultParam aPI_ResultParam = new API_ResultParam();
        if (!ApiAccessUtil.isConnected(this.mContext)) {
            aPI_ResultParam.err = -6;
        } else if (this.mAdInfoDetail != null) {
            aPI_ResultParam.adnetwork_key = this.mAdInfoDetail.adnetwork_key;
            aPI_ResultParam.user_ad_id = this.mAdInfoDetail.user_ad_id;
            String className = AdNetworkKey.getClassName(this.mAdInfoDetail.adnetwork_key);
            if (className == null || className.length() <= 0) {
                aPI_ResultParam.err = -2;
            } else {
                try {
                    API_Base aPI_Base = (API_Base) Class.forName(className).newInstance();
                    API_CpntrolParam aPI_CpntrolParam = new API_CpntrolParam(this.mContext);
                    aPI_Base.setContext(this.mContext);
                    aPI_Base.getContent(aPI_ResultParam, this.mAppID, this.mAdInfoDetail.html, this.mAdInfoDetail.param, aPI_CpntrolParam, LogUtil.getInstance(this.mContext), this.mBannerKind);
                } catch (Exception e) {
                    aPI_ResultParam.err = -2;
                }
            }
        } else {
            aPI_ResultParam.err = -5;
        }
        return aPI_ResultParam;
    }
}
