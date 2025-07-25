package com.facebook.internal;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import com.facebook.FacebookException;
import com.tapjoy.TapjoyConstants;
import java.lang.reflect.Method;

public class AttributionIdentifiers {
    private static final String ANDROID_ID_COLUMN_NAME = "androidid";
    private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    private static final Uri ATTRIBUTION_ID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
    private static final int CONNECTION_RESULT_SUCCESS = 0;
    private static final long IDENTIFIER_REFRESH_INTERVAL_MILLIS = 3600000;
    private static final String LIMIT_TRACKING_COLUMN_NAME = "limit_tracking";
    private static final String TAG = AttributionIdentifiers.class.getCanonicalName();
    private static AttributionIdentifiers recentlyFetchedIdentifiers;
    private String androidAdvertiserId;
    private String attributionId;
    private long fetchTime;
    private boolean limitTracking;

    private static AttributionIdentifiers getAndroidId(Context context) {
        Method methodQuietly;
        Object invokeMethodQuietly;
        AttributionIdentifiers attributionIdentifiers = new AttributionIdentifiers();
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new FacebookException("getAndroidId cannot be called on the main thread.");
            }
            Method methodQuietly2 = Utility.getMethodQuietly("com.google.android.gms.common.GooglePlayServicesUtil", "isGooglePlayServicesAvailable", (Class<?>[]) new Class[]{Context.class});
            if (methodQuietly2 != null) {
                Object invokeMethodQuietly2 = Utility.invokeMethodQuietly((Object) null, methodQuietly2, context);
                if ((invokeMethodQuietly2 instanceof Integer) && ((Integer) invokeMethodQuietly2).intValue() == 0 && (methodQuietly = Utility.getMethodQuietly("com.google.android.gms.ads.identifier.AdvertisingIdClient", "getAdvertisingIdInfo", (Class<?>[]) new Class[]{Context.class})) != null && (invokeMethodQuietly = Utility.invokeMethodQuietly((Object) null, methodQuietly, context)) != null) {
                    Method methodQuietly3 = Utility.getMethodQuietly(invokeMethodQuietly.getClass(), "getId", (Class<?>[]) new Class[0]);
                    Method methodQuietly4 = Utility.getMethodQuietly(invokeMethodQuietly.getClass(), "isLimitAdTrackingEnabled", (Class<?>[]) new Class[0]);
                    if (!(methodQuietly3 == null || methodQuietly4 == null)) {
                        attributionIdentifiers.androidAdvertiserId = (String) Utility.invokeMethodQuietly(invokeMethodQuietly, methodQuietly3, new Object[0]);
                        attributionIdentifiers.limitTracking = ((Boolean) Utility.invokeMethodQuietly(invokeMethodQuietly, methodQuietly4, new Object[0])).booleanValue();
                    }
                }
            }
            return attributionIdentifiers;
        } catch (Exception e) {
            Utility.logd(TapjoyConstants.TJC_ANDROID_ID, e);
        }
    }

    public static AttributionIdentifiers getAttributionIdentifiers(Context context) {
        if (recentlyFetchedIdentifiers != null && System.currentTimeMillis() - recentlyFetchedIdentifiers.fetchTime < IDENTIFIER_REFRESH_INTERVAL_MILLIS) {
            return recentlyFetchedIdentifiers;
        }
        AttributionIdentifiers androidId = getAndroidId(context);
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(ATTRIBUTION_ID_CONTENT_URI, new String[]{ATTRIBUTION_ID_COLUMN_NAME, ANDROID_ID_COLUMN_NAME, LIMIT_TRACKING_COLUMN_NAME}, (String) null, (String[]) null, (String) null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(ATTRIBUTION_ID_COLUMN_NAME);
                int columnIndex2 = cursor.getColumnIndex(ANDROID_ID_COLUMN_NAME);
                int columnIndex3 = cursor.getColumnIndex(LIMIT_TRACKING_COLUMN_NAME);
                androidId.attributionId = cursor.getString(columnIndex);
                if (columnIndex2 > 0 && columnIndex3 > 0 && androidId.getAndroidAdvertiserId() == null) {
                    androidId.androidAdvertiserId = cursor.getString(columnIndex2);
                    androidId.limitTracking = Boolean.parseBoolean(cursor.getString(columnIndex3));
                }
                if (cursor != null) {
                    cursor.close();
                }
                androidId.fetchTime = System.currentTimeMillis();
                recentlyFetchedIdentifiers = androidId;
                return androidId;
            } else if (cursor == null) {
                return androidId;
            } else {
                cursor.close();
                return androidId;
            }
        } catch (Exception e) {
            Log.d(TAG, "Caught unexpected exception in getAttributionId(): " + e.toString());
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public String getAndroidAdvertiserId() {
        return this.androidAdvertiserId;
    }

    public String getAttributionId() {
        return this.attributionId;
    }

    public boolean isTrackingLimited() {
        return this.limitTracking;
    }
}
