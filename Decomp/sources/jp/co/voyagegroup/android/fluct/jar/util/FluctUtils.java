package jp.co.voyagegroup.android.fluct.jar.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.net.URLEncoder;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctPreferences;
import jp.stargarage.g2metrics.BuildConfig;

public class FluctUtils {
    private static final String TAG = "FluctUtils";

    public static Object bytesToObject(byte[] bArr) {
        Log.d(TAG, "bytesToObject : ");
        if (bArr != null) {
            try {
                return new ObjectInputStream(new ByteArrayInputStream(bArr)).readObject();
            } catch (StreamCorruptedException e) {
                Log.e(TAG, "bytesToObject : StreamCorruptedException is " + e.getLocalizedMessage());
            } catch (OptionalDataException e2) {
                Log.e(TAG, "bytesToObject : OptionalDataException is " + e2.getLocalizedMessage());
            } catch (IOException e3) {
                Log.e(TAG, "bytesToObject : IOException is " + e3.getLocalizedMessage());
            } catch (ClassNotFoundException e4) {
                Log.e(TAG, "bytesToObject : ClassNotFoundException is " + e4.getLocalizedMessage());
            }
        }
        return null;
    }

    public static boolean checkPermission(Context context) {
        Log.d(TAG, "checkPermission : ");
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        return (packageManager.checkPermission("android.permission.INTERNET", packageName) == -1 || packageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", packageName) == -1) ? false : true;
    }

    public static String getAdid(Context context) {
        Log.d(TAG, "getAdid : ");
        String adid = FluctPreferences.getInstance().getAdid();
        Log.v(TAG, "getAdid : adid is " + adid);
        return adid;
    }

    public static String getConfigURL(String str) {
        Log.d(TAG, "getConfigURL : mediaId is " + str);
        return new StringBuilder(String.valueOf(getConfigUrl()) + FluctConstants.CONFIG_URL_PARAM).toString().replaceAll(FluctConstants.REPLACE_APPLICATION_ID, URLEncoder.encode(str)).replaceAll(FluctConstants.REPLACE_SDK_VERSION, URLEncoder.encode(String.valueOf(FluctConstants.SDK_VERSION_PREFIX) + Build.VERSION.RELEASE.split("\\.")[0]));
    }

    private static String getConfigUrl() {
        Log.d(TAG, "getConfigUrl : ");
        return FluctConstants.CONFIG_PROC_URL;
    }

    public static int getCurrentTimeSec() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static String getDefaultMediaId(Context context) {
        Log.d(TAG, "getDefaultMediaId : ");
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            return (bundle == null || bundle.get(FluctConstants.META_DATA_MEDIA_ID) == null) ? BuildConfig.FLAVOR : bundle.get(FluctConstants.META_DATA_MEDIA_ID).toString();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getDefaultMediaId : NameNotFoundException is " + e.getLocalizedMessage());
            return BuildConfig.FLAVOR;
        }
    }

    public static String getNotAdTrackingParam(Context context) {
        Log.d(TAG, "getNotAdTrackingParam : ");
        String notAdTrackingParam = FluctPreferences.getInstance().getNotAdTrackingParam();
        Log.v(TAG, "getNotAdTrackingParam : Not Ad tracking param is " + notAdTrackingParam);
        return notAdTrackingParam;
    }

    public static boolean isNetWorkAvailable(Context context) {
        Log.d(TAG, "isNetWorkAvailable : ");
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

    public static byte[] objectToBytes(Object obj) {
        Log.d(TAG, "objectToBytes : ");
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            Log.e(TAG, "objectToBytes : IOException is " + e.getLocalizedMessage());
            return null;
        }
    }

    public static String replaceParams(Context context, String str) {
        Log.d(TAG, "replaceUrlParams : String is " + str);
        if (str.indexOf(FluctConstants.REPLACE_AD_ID_NOT_TRACKING_URL_PARAM) > -1) {
            str = str.replaceAll(FluctConstants.REPLACE_AD_ID_NOT_TRACKING_URL_PARAM, getNotAdTrackingParam(context));
        }
        if (str.indexOf(FluctConstants.REPLACE_AD_ID_URL_PARAM) > -1) {
            str = str.replaceAll(FluctConstants.REPLACE_AD_ID_URL_PARAM, getAdid(context));
        }
        Log.v(TAG, "replaceUrlParams : Replaced String is " + str);
        return str;
    }
}
