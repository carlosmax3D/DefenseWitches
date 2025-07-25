package jp.tjkapp.adfurikunsdk;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.IOException;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class RecTask2 extends AsyncTaskLoader<Void> {
    public static final int RECTYPE_CLICK = 0;
    public static final int RECTYPE_IMPRESSION = 1;
    private String IDFA;
    private String mAppID;
    private String mBeaconUrl;
    private Context mContext;
    private int mErr;
    private String mImpPrice;
    private String mOptParam;
    private int mRecType;
    private String mUserAdId;

    public RecTask2(Context context, String str, String str2, int i, String str3, String str4, String str5) {
        super(context);
        this.mContext = context;
        this.mAppID = str;
        this.mUserAdId = str2;
        this.mRecType = i;
        this.mImpPrice = str3;
        this.mBeaconUrl = str4;
        this.mOptParam = str5;
        this.IDFA = FileUtil.getIDFA(context);
        this.mErr = Constants.WEBAPI_NOERR;
    }

    @Override // android.support.v4.content.AsyncTaskLoader
    public Void loadInBackground() {
        String userAgent = FileUtil.getUserAgent(this.mContext);
        LogUtil logUtil = LogUtil.getInstance(this.mContext);
        if (!ApiAccessUtil.isConnected(this.mContext)) {
            this.mErr = Constants.WEBAPI_CONNECTEDERR;
            return null;
        }
        ApiAccessUtil.WebAPIResult webAPIResultRecClick = null;
        try {
            webAPIResultRecClick = this.mRecType == 0 ? ApiAccessUtil.recClick(this.mAppID, this.mUserAdId, logUtil, userAgent, this.IDFA, this.mOptParam) : ApiAccessUtil.recImpression(this.mAppID, this.mUserAdId, logUtil, userAgent, this.IDFA, this.mImpPrice, this.mOptParam);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        if (webAPIResultRecClick.return_code != 200) {
            this.mErr = webAPIResultRecClick.return_code;
        }
        if (this.mBeaconUrl == null || this.mBeaconUrl.length() <= 0) {
            return null;
        }
        try {
            ApiAccessUtil.callWebAPI(this.mBeaconUrl, logUtil, userAgent, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
