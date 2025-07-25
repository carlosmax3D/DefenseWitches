package com.threatmetrix.TrustDefenderMobile;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.ansca.corona.Crypto;
import com.ansca.corona.purchasing.StoreName;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

class ApplicationInfoGatherer {
    private static final String TAG = ApplicationInfoGatherer.class.getSimpleName();

    ApplicationInfoGatherer() {
    }

    private static void checkInstalledPackages(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(0)) {
                getHashForPackage(applicationInfo.sourceDir);
            }
        }
    }

    private static void checkRunningPackages(Context context) {
        try {
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
            PackageManager packageManager = context.getPackageManager();
            for (ActivityManager.RunningTaskInfo next : runningTasks) {
                new StringBuilder("Application executed : ").append(next.baseActivity != null ? next.baseActivity.toShortString() : StoreName.NONE).append("\t\t ID: ").append(next.id);
                String packageName = next.baseActivity.getPackageName();
                if (packageManager != null) {
                    try {
                        ApplicationInfo applicationInfo = packageManager.getPackageInfo(packageName, 0).applicationInfo;
                        if (applicationInfo != null) {
                            getHashForPackage(applicationInfo.sourceDir);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        String str = TAG;
                    }
                }
            }
        } catch (SecurityException e2) {
            Log.e(TAG, "No perms: ", e2);
        }
    }

    static String checkThisPackage(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return null;
        }
        String str = applicationInfo.sourceDir;
        String str2 = TAG;
        new StringBuilder("sourceDir: ").append(str);
        return getHashForPackage(str);
    }

    private static String getHashForPackage(String str) {
        if (NativeGatherer.INSTANCE.isAvailable()) {
            return NativeGatherer.INSTANCE.hashFile(str);
        }
        String str2 = TAG;
        try {
            MessageDigest instance = MessageDigest.getInstance(Crypto.ALGORITHM_MD5);
            try {
                FileInputStream fileInputStream = new FileInputStream(str);
                String str3 = null;
                byte[] bArr = new byte[8192];
                while (true) {
                    try {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        instance.update(bArr, 0, read);
                    } catch (IOException e) {
                        Log.e(TAG, "Unable to process file for MD5", e);
                        try {
                            fileInputStream.close();
                        } catch (IOException e2) {
                            Log.e(TAG, "Exception on closing MD5 input stream", e2);
                        }
                    } catch (Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            Log.e(TAG, "Exception on closing MD5 input stream", e3);
                        }
                        throw th;
                    }
                }
                str3 = String.format("%32s", new Object[]{new BigInteger(1, instance.digest()).toString(16)}).replace(' ', '0');
                try {
                    fileInputStream.close();
                } catch (IOException e4) {
                    Log.e(TAG, "Exception on closing MD5 input stream", e4);
                }
                String str4 = TAG;
                new StringBuilder("Got : ").append(str3);
                return str3;
            } catch (FileNotFoundException e5) {
                Log.e(TAG, "Exception while getting FileInputStream", e5);
                return null;
            }
        } catch (NoSuchAlgorithmException e6) {
            Log.e(TAG, "Exception while getting digest", e6);
            return null;
        }
    }
}
