package com.ansca.corona.purchasing;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import com.ansca.corona.CoronaEnvironment;
import java.util.LinkedHashSet;

public class StoreServices {
    public static final String AMAZON_MARKETPLACE_APP_PACKAGE_NAME = "com.amazon.venezia";
    public static final String GOOGLE_MARKETPLACE_APP_PACKAGE_NAME_1 = "com.android.vending";
    public static final String GOOGLE_MARKETPLACE_APP_PACKAGE_NAME_2 = "com.google.android.feedback";
    public static final String SAMSUNG_MARKETPLACE_APP_PACKAGE_NAME = "com.sec.android.app.samsungapps";

    private StoreServices() {
    }

    public static String[] getAvailableAppStoreNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        String lowerCase = Build.MANUFACTURER.toLowerCase();
        if (isPackageNameInstalled("com.android.vending") || isPackageNameInstalled(GOOGLE_MARKETPLACE_APP_PACKAGE_NAME_2)) {
            linkedHashSet.add(StoreName.GOOGLE);
        }
        if (isPackageNameInstalled(AMAZON_MARKETPLACE_APP_PACKAGE_NAME)) {
            linkedHashSet.add(StoreName.AMAZON);
        }
        if (isPackageNameInstalled(SAMSUNG_MARKETPLACE_APP_PACKAGE_NAME)) {
            linkedHashSet.add(StoreName.SAMSUNG);
        }
        String property = System.getProperty("ro.nook.manufacturer");
        if ((lowerCase.contains("barnes") && lowerCase.contains("noble")) || (property != null && property.length() > 0)) {
            linkedHashSet.add(StoreName.NOOK);
        }
        if (linkedHashSet.size() > 0) {
            return (String[]) linkedHashSet.toArray(new String[0]);
        }
        return null;
    }

    public static String[] getAvailableInAppStoreNames() {
        if (!isInAppStoreAvailable(StoreName.GOOGLE)) {
            return null;
        }
        return new String[]{StoreName.GOOGLE};
    }

    public static String getDefaultInAppStoreName() {
        return isInAppStoreAvailable(StoreName.GOOGLE) ? StoreName.GOOGLE : StoreName.NONE;
    }

    public static String getStoreApplicationWasPurchasedFrom() {
        try {
            Context applicationContext = CoronaEnvironment.getApplicationContext();
            return StoreName.fromPackageName(applicationContext.getPackageManager().getInstallerPackageName(applicationContext.getPackageName()));
        } catch (Exception e) {
            return StoreName.NONE;
        }
    }

    public static String getTargetedAppStoreName() {
        String str = null;
        try {
            Context applicationContext = CoronaEnvironment.getApplicationContext();
            ApplicationInfo applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
            if (!(applicationInfo == null || applicationInfo.metaData == null || (str = applicationInfo.metaData.getString("targetedAppStore")) == null)) {
                str = str.trim();
            }
        } catch (Exception e) {
        }
        return (str == null || str.length() <= 0) ? StoreName.NONE : str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
        r0 = getAvailableAppStoreNames();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isAppStoreAvailable(java.lang.String r3) {
        /*
            r1 = 0
            boolean r2 = com.ansca.corona.purchasing.StoreName.isNotValid(r3)
            if (r2 == 0) goto L_0x0008
        L_0x0007:
            return r1
        L_0x0008:
            java.lang.String[] r0 = getAvailableAppStoreNames()
            if (r0 == 0) goto L_0x0007
            int r2 = java.util.Arrays.binarySearch(r0, r3)
            if (r2 < 0) goto L_0x0007
            r1 = 1
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.purchasing.StoreServices.isAppStoreAvailable(java.lang.String):boolean");
    }

    public static boolean isInAppStoreAvailable(String str) {
        if (StoreName.isNotValid(str)) {
            return false;
        }
        if (str.equals(StoreName.GOOGLE)) {
            return isPackageNameInstalled("com.android.vending") || isPackageNameInstalled(GOOGLE_MARKETPLACE_APP_PACKAGE_NAME_2);
        }
        if (str.equals(StoreName.AMAZON)) {
        }
        return false;
    }

    private static boolean isPackageNameInstalled(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        try {
            CoronaEnvironment.getApplicationContext().getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
