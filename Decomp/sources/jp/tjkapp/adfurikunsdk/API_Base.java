package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import java.util.Iterator;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
abstract class API_Base {
    protected Context mContext = null;

    API_Base() {
    }

    public abstract void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception;

    protected String getValueFromJSON(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (str2.equals(next)) {
                    return jSONObject.getString(next);
                }
            }
        } catch (Exception e) {
        }
        return BuildConfig.FLAVOR;
    }

    protected String replaceCode(String str) {
        return str.trim().replaceAll("\r\n", BuildConfig.FLAVOR).replaceAll("\r", BuildConfig.FLAVOR).replaceAll("\n", BuildConfig.FLAVOR).replaceAll("\t", BuildConfig.FLAVOR).replace("\\n", " ").replace("\\t", " ").replace("'", "\\'").replace("\\\"", "”").replace("\u2028", " ");
    }

    protected void setContext(Context context) {
        this.mContext = context;
    }
}
