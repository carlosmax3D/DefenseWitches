package jp.co.voyagegroup.android.fluct.jar.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.drive.DriveFile;
import java.util.ArrayList;
import java.util.List;
import jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctConversionEntity;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting;
import jp.co.voyagegroup.android.fluct.jar.util.FluctUtils;
import jp.co.voyagegroup.android.fluct.jar.util.Log;
import jp.co.voyagegroup.android.fluct.jar.web.FluctHttpAccess;

public class FluctConversion {
    private static final String TAG = "FluctConversion";
    /* access modifiers changed from: private */
    public static FluctConversionThread sSetConversionThread = null;

    private static class FluctConversionThread extends Thread {
        private Context mContext;

        public FluctConversionThread(Context context) {
            Log.d(FluctConversion.TAG, "FluctConversionThread : ");
            this.mContext = context;
        }

        private void checkConversionEntity(Context context, FluctConversionEntity fluctConversionEntity) {
            Log.d(FluctConversion.TAG, "checkConversionEntity : ");
            if (fluctConversionEntity == null) {
                Log.e(FluctConversion.TAG, "checkConversionEntity : fluctConversion is null");
                return;
            }
            String browserOpenUrl = fluctConversionEntity.getBrowserOpenUrl();
            ArrayList<String> convUrl = fluctConversionEntity.getConvUrl();
            if (convUrl == null && browserOpenUrl == null) {
                Log.e(FluctConversion.TAG, "checkConversionEntity : urls is null and browserOpenUrl is null");
            } else {
                executeConversion(context, convUrl, browserOpenUrl);
            }
        }

        private void executeConversion(Context context, List<String> list, String str) {
            Log.d(FluctConversion.TAG, "executeConversion : ");
            boolean z = false;
            if (list.size() == 0 && str == null) {
                z = true;
            } else if (list.size() > 0) {
                Log.v(FluctConversion.TAG, "executeConversion : execute conversion urls");
                FluctHttpAccess fluctHttpAccess = new FluctHttpAccess();
                for (int i = 0; i < list.size(); i++) {
                    list.set(i, FluctUtils.replaceParams(context, list.get(i)));
                }
                z = fluctHttpAccess.executeUrls(list);
            } else if (str != null && FluctUtils.isNetWorkAvailable(context)) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(FluctUtils.replaceParams(context, str)));
                if (!(context instanceof Activity)) {
                    intent.setFlags(DriveFile.MODE_READ_ONLY);
                }
                Log.v(FluctConversion.TAG, "executeConversion : startActivity call");
                context.startActivity(intent);
                z = true;
            }
            if (z) {
                FluctDbAccess.setConversionFlag(context, 1);
                Log.v(FluctConversion.TAG, "executeConversion : set conversion success");
                return;
            }
            Log.e(FluctConversion.TAG, "executeConversion : set conversion failed");
        }

        public void run() {
            Log.d(FluctConversion.TAG, "FluctConversionThread : run ");
            String defaultMediaId = FluctUtils.getDefaultMediaId(this.mContext);
            try {
                int conversionFlag = FluctDbAccess.getConversionFlag(this.mContext);
                Log.v(FluctConversion.TAG, "FluctConversionThread : status is " + conversionFlag);
                if (conversionFlag == 0) {
                    FluctSetting netConfig = FluctConfig.getInstance().getNetConfig(this.mContext, defaultMediaId);
                    if (netConfig == null) {
                        this.mContext = null;
                        FluctConversion.sSetConversionThread = null;
                        return;
                    }
                    checkConversionEntity(this.mContext, netConfig.getFluctConversion());
                    this.mContext = null;
                    FluctConversion.sSetConversionThread = null;
                }
            } catch (Exception e) {
                Log.e(FluctConversion.TAG, "FluctConversionThread : Exception is " + e.getLocalizedMessage());
            } finally {
                this.mContext = null;
                FluctConversion.sSetConversionThread = null;
            }
        }
    }

    public static void setConversion(Context context) {
        Log.d(TAG, "setConversion : ");
        if (sSetConversionThread == null) {
            sSetConversionThread = new FluctConversionThread(context);
            sSetConversionThread.start();
        }
    }
}
