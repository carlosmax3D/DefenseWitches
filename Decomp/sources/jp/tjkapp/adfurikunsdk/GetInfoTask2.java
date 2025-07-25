package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.HttpResponseCode;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class GetInfoTask2 extends AsyncTaskLoader<Integer> {
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

    public GetInfoTask2(OnLoadListener onLoadListener, Context context, String str) {
        super(context);
        this.mLoadListener = onLoadListener;
        this.mContext = context;
        this.mLog = LogUtil.getInstance(this.mContext);
        this.mAppID = str;
        this.mUserAgent = FileUtil.getUserAgent(this.mContext);
    }

    private void deleteGetInfoData() {
        if (this.mContext != null) {
            FileUtil.setAdLastTime(this.mContext, this.mAppID, HttpResponseCode.BAD_REQUEST);
            FileUtil.deleteFile(FileUtil.getGetInfoFilePath(this.mContext, this.mAppID));
        }
    }

    private boolean isGetInfo(String str) throws JSONException {
        if (str != null && str.length() > 0) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String string = BuildConfig.FLAVOR;
                if (jSONObject.has("result")) {
                    string = jSONObject.getString("result");
                }
                if (string.equals("ok")) {
                    return true;
                }
                if (string.equals("error") && jSONObject.has("values")) {
                    JSONObject jSONObject2 = new JSONObject(jSONObject.getString("values"));
                    if (jSONObject2.has("message")) {
                        this.mLog.debug_e(Constants.TAG_NAME, "error=" + jSONObject2.getString("message"));
                    }
                }
            } catch (JSONException e) {
                this.mLog.debug_e(Constants.TAG_NAME, "JSONException");
                this.mLog.debug_e(Constants.TAG_NAME, e);
            }
        }
        return false;
    }

    private void saveGetInfoData(String str) {
        if (this.mContext != null) {
            FileUtil.setAdLastTime(this.mContext, this.mAppID, HttpResponseCode.f3212OK);
            FileUtil.saveStringFile(FileUtil.getGetInfoFilePath(this.mContext, this.mAppID), str);
        }
    }

    @Override // android.support.v4.content.Loader
    public void deliverResult(Integer num) {
        super.deliverResult((GetInfoTask2) num);
        try {
            if (this.mLoadListener != null) {
                this.mLoadListener.onLoadFinish(num);
            }
        } catch (Exception e) {
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.support.v4.content.AsyncTaskLoader
    public synchronized Integer loadInBackground() {
        Integer numValueOf;
        Integer.valueOf(0);
        ApiAccessUtil.WebAPIResult info = ApiAccessUtil.getInfo(this.mAppID, this.mLog, this.mUserAgent, true);
        numValueOf = Integer.valueOf(info.return_code);
        if (info.return_code == 200) {
            if (!isGetInfo(info.message)) {
                this.mLog.detail(Constants.TAG_NAME, "getInfo failed because of format2");
                deleteGetInfoData();
            } else if (ApiAccessUtil.stringToAdInfo(this.mContext, this.mAppID, info.message, false) != null) {
                this.mLog.detail(Constants.TAG_NAME, "getInfo is saved");
                saveGetInfoData(info.message);
            } else {
                this.mLog.detail(Constants.TAG_NAME, "getInfo failed because of format1");
                deleteGetInfoData();
            }
        } else if (info.return_code == 400) {
            this.mLog.detail(Constants.TAG_NAME, "getInfo failed because of sc400");
            deleteGetInfoData();
        } else {
            ApiAccessUtil.WebAPIResult info2 = ApiAccessUtil.getInfo(this.mAppID, this.mLog, this.mUserAgent, false);
            numValueOf = Integer.valueOf(info2.return_code);
            if (info2.return_code == 200) {
                if (isGetInfo(info2.message) && ApiAccessUtil.stringToAdInfo(this.mContext, this.mAppID, info2.message, false) != null) {
                    this.mLog.detail(Constants.TAG_NAME, "getInfo is saved");
                    saveGetInfoData(info2.message);
                }
            } else if (info2.return_code == 400) {
                this.mLog.detail(Constants.TAG_NAME, "getInfo failed because of sc400");
                deleteGetInfoData();
            }
        }
        return numValueOf;
    }
}
