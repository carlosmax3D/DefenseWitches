package com.ansca.corona.storage;

import android.content.Context;
import android.content.res.Resources;
import com.ansca.corona.ApplicationContextProvider;
import com.ansca.corona.CoronaEnvironment;
import jp.stargarage.g2metrics.BuildConfig;

public class ResourceServices extends ApplicationContextProvider {
    public static final int INVALID_RESOURCE_ID = 0;
    private static String sPackageName = null;
    private static Class sRClass = null;

    public ResourceServices(Context context) {
        super(context);
    }

    public int getDrawableResourceId(String str) {
        return getResourceId(str, "drawable");
    }

    public int getLayoutResourceId(String str) {
        return getResourceId(str, "layout");
    }

    public String getPackageName() {
        Package packageR;
        if (sPackageName == null) {
            sPackageName = BuildConfig.FLAVOR;
            Class rClass = getRClass();
            if (!(rClass == null || (packageR = rClass.getPackage()) == null)) {
                sPackageName = packageR.getName();
            }
        }
        return sPackageName;
    }

    public Class getRClass() {
        if (sRClass == null) {
            try {
                sRClass = Class.forName(getApplicationContext().getPackageName() + ".R");
            } catch (Exception e) {
            }
            if (sRClass == null) {
                try {
                    sRClass = Class.forName(CoronaEnvironment.class.getPackage().getName() + ".R");
                } catch (Exception e2) {
                }
            }
        }
        return sRClass;
    }

    public int getRawResourceId(String str) {
        return getResourceId(str, "raw");
    }

    public int getResourceId(String str, String str2) {
        Resources resources;
        if (str == null || str.length() <= 0 || (resources = getResources()) == null) {
            return 0;
        }
        return resources.getIdentifier(str, str2, getPackageName());
    }

    public Resources getResources() {
        return getApplicationContext().getResources();
    }

    public int getStringResourceId(String str) {
        return getResourceId(str, "string");
    }
}
