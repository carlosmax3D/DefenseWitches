package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class FillerTask2 extends AsyncTaskLoader {
    private static final String FILLER_URL = "http://d830x8j3o1b2k.cloudfront.net/adfurikun/api/get-default-script/app_id/[app_id]";
    private String IDFA;
    private String fillerFileName;
    private String mAppID;
    private Context mContext;
    private OnLoadListener mLoadListener;
    private LogUtil mLog;
    private String mUserAgent;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface OnLoadListener {
        void onLoadFinish(Integer num);
    }

    public FillerTask2(OnLoadListener onLoadListener, Context context, LogUtil logUtil, String str, String str2, String str3) {
        super(context);
        this.fillerFileName = BuildConfig.FLAVOR;
        this.mLoadListener = onLoadListener;
        this.mContext = context;
        this.mLog = logUtil;
        this.mAppID = str;
        this.mUserAgent = str2;
        this.IDFA = str3;
        this.fillerFileName = FileUtil.getFillerFilePath(this.mContext, this.mAppID);
    }

    @Override // android.support.v4.content.Loader
    public void deliverResult(Object obj) {
        super.deliverResult(obj);
        try {
            if (this.mLoadListener != null) {
                this.mLoadListener.onLoadFinish(Integer.valueOf(obj.toString()));
            }
        } catch (Exception e) {
        }
    }

    @Override // android.support.v4.content.AsyncTaskLoader
    public synchronized Integer loadInBackground() {
        int iValueOf;
        iValueOf = 0;
        try {
            ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(FILLER_URL.replace("[app_id]", this.mAppID), this.mLog, this.mUserAgent, true);
            iValueOf = Integer.valueOf(webAPIResultCallWebAPI.return_code);
            if (webAPIResultCallWebAPI.return_code == 200) {
                webAPIResultCallWebAPI.message = ApiAccessUtil.replaceIDFA(this.mContext, webAPIResultCallWebAPI.message, this.mLog);
                FileUtil.saveStringFile(this.fillerFileName, webAPIResultCallWebAPI.message);
            }
        } catch (Error e) {
        } catch (Exception e2) {
        }
        return iValueOf;
    }
}
