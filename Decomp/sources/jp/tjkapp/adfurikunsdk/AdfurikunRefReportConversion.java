package jp.tjkapp.adfurikunsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.stargarage.g2metrics.BuildConfig;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdfurikunRefReportConversion implements Runnable {
    private static final String LOGTAG = "AdfurikunRefReportConversion";
    public static final String PREFERENCE_KEY = "adfurikun_report_conversion";
    public static final String PREFERENCE_KEY_APP_ID = "APP_ID";
    public static final String PREFERENCE_KEY_DATE = "DATE";
    public static final String PREFERENCE_KEY_PRAPP_ID = "PRAPP_ID";
    private long lgDate;
    private Context mContext;
    private String strAppID;
    private String strPrAdID;
    private String strUserAgent;
    private static boolean OUTLOG = false;
    private static String SENDURL = BuildConfig.FLAVOR;
    private static String URL_REPORT = "adfurikunpr/api/rec-cnv?aid=%%app_id%%&paid=%%pr_ad_id%%&uid=%%idfa%%";
    private String srtUserAdID = BuildConfig.FLAVOR;
    private long limitTime = 86400;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    private class reportToAdfurikun extends AsyncTaskLoader {
        public reportToAdfurikun(Context context) {
            super(context);
        }

        private void cleanPreference() {
            SharedPreferences.Editor editorEdit = AdfurikunRefReportConversion.this.mContext.getSharedPreferences(AdfurikunRefReportConversion.PREFERENCE_KEY, 0).edit();
            editorEdit.putString(AdfurikunRefReportConversion.PREFERENCE_KEY_APP_ID, BuildConfig.FLAVOR);
            editorEdit.putString(AdfurikunRefReportConversion.PREFERENCE_KEY_PRAPP_ID, BuildConfig.FLAVOR);
            editorEdit.commit();
        }

        @Override // android.support.v4.content.AsyncTaskLoader
        public Object loadInBackground() {
            String strReplace = (ApiAccessUtil.getConversionBaseUrl() + AdfurikunRefReportConversion.URL_REPORT).replace("%%app_id%%", AdfurikunRefReportConversion.this.strAppID).replace("%%pr_ad_id%%", AdfurikunRefReportConversion.this.strPrAdID).replace("%%idfa%%", AdfurikunRefReportConversion.this.srtUserAdID);
            String unused = AdfurikunRefReportConversion.SENDURL = strReplace;
            if (AdfurikunRefReportConversion.OUTLOG) {
                Log.v(AdfurikunRefReportConversion.LOGTAG, "doInBackground:" + strReplace);
            } else {
                Log.v(AdfurikunRefReportConversion.LOGTAG, "doInBackground");
            }
            try {
                HttpGet httpGet = new HttpGet(strReplace);
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                httpGet.setHeader("User-Agent", AdfurikunRefReportConversion.this.strUserAgent);
                int statusCode = defaultHttpClient.execute(httpGet).getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    cleanPreference();
                }
                Log.v(AdfurikunRefReportConversion.LOGTAG, "doInBackground:finish:" + statusCode);
                return null;
            } catch (ClientProtocolException e) {
                Log.v(AdfurikunRefReportConversion.LOGTAG, e.getMessage());
                return null;
            } catch (IOException e2) {
                Log.v(AdfurikunRefReportConversion.LOGTAG, e2.getMessage());
                return null;
            }
        }
    }

    @SuppressLint({"NewApi"})
    public AdfurikunRefReportConversion(Context context) throws PackageManager.NameNotFoundException {
        this.strPrAdID = BuildConfig.FLAVOR;
        this.strAppID = BuildConfig.FLAVOR;
        this.strUserAgent = BuildConfig.FLAVOR;
        this.lgDate = -1L;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                OUTLOG = applicationInfo.metaData.getBoolean("adfurikun_test", false);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        Log.v(LOGTAG, "construct");
        this.mContext = context;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_KEY, 0);
            this.strAppID = sharedPreferences.getString(PREFERENCE_KEY_APP_ID, BuildConfig.FLAVOR);
            this.strPrAdID = sharedPreferences.getString(PREFERENCE_KEY_PRAPP_ID, BuildConfig.FLAVOR);
            this.lgDate = sharedPreferences.getLong(PREFERENCE_KEY_DATE, this.lgDate);
            if (this.limitTime < new Date().getTime() - this.lgDate) {
                if (OUTLOG) {
                    Log.v(LOGTAG, "dateover");
                    return;
                }
                return;
            }
            this.strUserAgent = "Adfurikun; " + Build.MODEL + "; Android " + Build.VERSION.RELEASE + "; ";
            Object[] declaredFields = Class.forName("android.os.Build$VERSION_CODES").getDeclaredFields();
            String str = BuildConfig.FLAVOR;
            for (int i = 0; i < declaredFields.length; i++) {
                String string = declaredFields[i].toString();
                if (declaredFields[i].getInt(declaredFields[i]) == Build.VERSION.SDK_INT) {
                    Matcher matcher = Pattern.compile("([^.]*$)").matcher(string);
                    if (matcher.find()) {
                        str = matcher.toMatchResult().group() + "(" + Build.VERSION.SDK_INT + ")";
                    }
                }
            }
            this.strUserAgent += str + "; ";
        } catch (Error e2) {
            if (OUTLOG) {
                Log.v(LOGTAG, "exception:" + e2);
            }
        } catch (Exception e3) {
            if (OUTLOG) {
                Log.v(LOGTAG, "exception:" + e3);
            }
        }
    }

    public static void doConversionReport(Context context) throws PackageManager.NameNotFoundException {
        doConversionReport(context, 1, 1);
    }

    public static void doConversionReport(final Context context, final int i, final int i2) throws PackageManager.NameNotFoundException {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                OUTLOG = applicationInfo.metaData.getBoolean("adfurikun_test", false);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (OUTLOG) {
            Log.v(LOGTAG, "doConversionReport");
        }
        new Thread(new Runnable() { // from class: jp.tjkapp.adfurikunsdk.AdfurikunRefReportConversion.1
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                boolean z = true;
                boolean z2 = false;
                int i3 = 0;
                while (z) {
                    if (AdfurikunRefReportConversion.OUTLOG) {
                        Log.v(AdfurikunRefReportConversion.LOGTAG, "doConversionReport:LOOP:" + i3);
                    }
                    try {
                        Thread.sleep(i2);
                    } catch (Exception e2) {
                    }
                    if (AdfurikunRefReceiver.isReceivedRef(context)) {
                        if (AdfurikunRefReportConversion.OUTLOG) {
                            Log.v(AdfurikunRefReportConversion.LOGTAG, "doConversionReport:KTKR");
                        }
                        if (FileUtil.getIDFA(context) != BuildConfig.FLAVOR) {
                            if (AdfurikunRefReportConversion.OUTLOG) {
                                Log.v(AdfurikunRefReportConversion.LOGTAG, "doConversionReport:report[00]");
                            }
                            new Thread(new AdfurikunRefReportConversion(context)).start();
                            z = false;
                            z2 = true;
                        } else if (AdfurikunRefReportConversion.OUTLOG) {
                            Log.v(AdfurikunRefReportConversion.LOGTAG, "doConversionReport:not idfa");
                        }
                    }
                    i3++;
                    if (i3 > i) {
                        z = false;
                    }
                }
                if (!z2 && AdfurikunRefReceiver.isReceivedRef(context)) {
                    if (AdfurikunRefReportConversion.OUTLOG) {
                        Log.v(AdfurikunRefReportConversion.LOGTAG, "doConversionReport:report[01]");
                    }
                    new Thread(new AdfurikunRefReportConversion(context)).start();
                }
                if (AdfurikunRefReportConversion.OUTLOG) {
                    Log.v(AdfurikunRefReportConversion.LOGTAG, "doConversionReport:finish");
                }
            }
        }).start();
    }

    public static String getSendURL() {
        return SENDURL;
    }

    public static void outputLogCat(boolean z) {
        OUTLOG = z;
    }

    public static void setReportURL(String str) {
        URL_REPORT = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.v(LOGTAG, "run");
        if (this.strAppID.equals(BuildConfig.FLAVOR) && this.strPrAdID.equals(BuildConfig.FLAVOR)) {
            if (OUTLOG) {
                Log.v(LOGTAG, "not param end");
            }
        } else if (this.lgDate != -1) {
            this.srtUserAdID = FileUtil.getIDFA(this.mContext);
            new reportToAdfurikun(this.mContext).forceLoad();
        } else if (OUTLOG) {
            Log.v(LOGTAG, "lgDate -1 end");
        }
    }
}
