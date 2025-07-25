package jp.stargarage.g2metrics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.facebook.appevents.AppEventsConstants;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
final class AppInfoLoader implements Runnable {
    private final IAsyncInfoLoadCallBack callBack;
    private final Context context;

    AppInfoLoader(Context context, IAsyncInfoLoadCallBack iAsyncInfoLoadCallBack) {
        this.context = context;
        this.callBack = iAsyncInfoLoadCallBack;
    }

    @Override // java.lang.Runnable
    public void run() {
        AppInfo appInfo = new AppInfo();
        appInfo.package_name = this.context.getPackageName();
        PackageManager packageManager = this.context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.context.getPackageName(), 0);
            appInfo.name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appInfo.version = String.valueOf(packageInfo.versionCode);
            appInfo.displayVersion = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            appInfo.name = "unknown";
            appInfo.version = AppEventsConstants.EVENT_PARAM_VALUE_NO;
            appInfo.displayVersion = AppEventsConstants.EVENT_PARAM_VALUE_NO;
        }
        this.callBack.onLoadComplete(appInfo);
    }
}
