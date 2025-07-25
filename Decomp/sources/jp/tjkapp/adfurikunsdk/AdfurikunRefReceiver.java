package jp.tjkapp.adfurikunsdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.stargarage.g2metrics.BuildConfig;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdfurikunRefReceiver extends BroadcastReceiver {
    private static final String LOGTAG = "AdfurikunRefReceiver";
    private static final String METANAME_PRADID = "jp.tjkapp.adfurikunsdk.PRAD_ID";
    private static boolean OUTLOG = false;
    private static String PAKAGENAME = "jp.tjkapp.adfurikunsdk.AdfurikunRefReceiver";
    private static boolean isReceived = false;

    private String getMatchedParam(String str, String str2) {
        String strGroup = BuildConfig.FLAVOR;
        Matcher matcher = Pattern.compile(str2).matcher(str);
        while (matcher.find()) {
            if (1 < matcher.groupCount()) {
                strGroup = matcher.group(1);
            }
        }
        return strGroup;
    }

    public static boolean isReceivedRef(Context context) {
        if (isReceived) {
            Log.i(LOGTAG, "isReceivedRef:normal");
            return true;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(AdfurikunRefReportConversion.PREFERENCE_KEY, 0);
        String string = sharedPreferences.getString(AdfurikunRefReportConversion.PREFERENCE_KEY_APP_ID, BuildConfig.FLAVOR);
        String string2 = sharedPreferences.getString(AdfurikunRefReportConversion.PREFERENCE_KEY_PRAPP_ID, BuildConfig.FLAVOR);
        if (string == BuildConfig.FLAVOR || string2 == BuildConfig.FLAVOR) {
            Log.i(LOGTAG, "isReceivedRef:nop");
            return false;
        }
        isReceived = true;
        Log.i(LOGTAG, "isReceivedRef:preferences");
        return true;
    }

    public static void outputLogCat(boolean z) {
        OUTLOG = z;
    }

    public static void setPackageName(String str) {
        PAKAGENAME = str;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws PackageManager.NameNotFoundException {
        String strTrim;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                OUTLOG = applicationInfo.metaData.getBoolean("adfurikun_test", false);
            } else {
                Log.v(LOGTAG, "onReceive:md null");
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        Log.v(LOGTAG, "onReceive");
        try {
            try {
                strTrim = Uri.decode(intent.getStringExtra("referrer")).trim();
            } catch (Error e2) {
                Log.v(LOGTAG, "Error: " + e2.getMessage());
            }
        } catch (Exception e3) {
            Log.v(LOGTAG, e3.toString());
        }
        if (strTrim.equals(BuildConfig.FLAVOR)) {
            return;
        }
        String matchedParam = getMatchedParam(strTrim, "app_id\\=(([^&?\"']*))");
        String matchedParam2 = getMatchedParam(strTrim, "pr_ad_id\\=(([^&?\"']*))");
        if (OUTLOG) {
            Log.v(LOGTAG, "onReceive:prmA:" + matchedParam);
        }
        if (OUTLOG) {
            Log.v(LOGTAG, "onReceive:prmP:" + matchedParam2);
        }
        try {
            if (matchedParam.length() > 0 && matchedParam2.length() > 0) {
                if (OUTLOG) {
                    Log.v(LOGTAG, "onReceive:ckmtnm:HIT");
                }
                SharedPreferences.Editor editorEdit = context.getSharedPreferences(AdfurikunRefReportConversion.PREFERENCE_KEY, 0).edit();
                editorEdit.putString(AdfurikunRefReportConversion.PREFERENCE_KEY_APP_ID, matchedParam);
                editorEdit.putString(AdfurikunRefReportConversion.PREFERENCE_KEY_PRAPP_ID, matchedParam2);
                editorEdit.putLong(AdfurikunRefReportConversion.PREFERENCE_KEY_DATE, new Date().getTime());
                editorEdit.commit();
                isReceived = true;
            }
        } catch (Exception e4) {
            Log.v(LOGTAG, e4.toString());
        }
        Log.v(LOGTAG, "onReceive:finish");
    }
}
