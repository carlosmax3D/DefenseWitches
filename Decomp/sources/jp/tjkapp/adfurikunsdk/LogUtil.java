package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class LogUtil {
    private static LogUtil mInstance = null;
    private boolean isDebugable;

    private LogUtil(Context context) throws PackageManager.NameNotFoundException {
        this.isDebugable = false;
        int testMode = FileUtil.getTestMode(context);
        if (testMode == 0) {
            this.isDebugable = true;
            return;
        }
        if (testMode == 1) {
            this.isDebugable = false;
            return;
        }
        this.isDebugable = false;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                this.isDebugable = applicationInfo.metaData.getBoolean("adfurikun_test", false);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    public static synchronized LogUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LogUtil(context.getApplicationContext());
        }
        return mInstance;
    }

    public void debug(String str, String str2) {
        if (this.isDebugable) {
            Log.d(str, str2);
        }
    }

    public void debug_e(String str, Exception exc) {
        if (this.isDebugable) {
            String message = exc.getMessage();
            if (message == null) {
                message = exc.toString();
            }
            if (message != null) {
                Log.e(str, message);
            } else {
                Log.e(str, "Exception is no message!");
            }
        }
    }

    public void debug_e(String str, String str2) {
        if (this.isDebugable) {
            Log.e(str, str2);
        }
    }

    public void debug_e(String str, String str2, Exception exc) {
        if (this.isDebugable) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            String message = exc.getMessage();
            if (message == null) {
                message = exc.toString();
            }
            if (message != null) {
                sb.append(message);
            } else {
                sb.append("Exception is no message!");
            }
            Log.e(str, sb.toString());
        }
    }

    public void debug_i(String str, String str2) {
        if (this.isDebugable) {
            Log.i(str, str2);
        }
    }

    public void detail(String str, String str2) {
        if (this.isDebugable && Constants.DETAIL_LOG) {
            Log.v(str, str2);
        }
    }

    public boolean isDebugMode() {
        return this.isDebugable;
    }
}
