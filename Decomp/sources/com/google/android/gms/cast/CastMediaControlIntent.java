package com.google.android.gms.cast;

import android.text.TextUtils;
import java.util.Collection;

public final class CastMediaControlIntent {
    public static final String ACTION_SYNC_STATUS = "com.google.android.gms.cast.ACTION_SYNC_STATUS";
    public static final String CATEGORY_CAST = "com.google.android.gms.cast.CATEGORY_CAST";
    public static final String DEFAULT_MEDIA_RECEIVER_APPLICATION_ID = "CC1AD845";
    public static final int ERROR_CODE_REQUEST_FAILED = 1;
    public static final int ERROR_CODE_SESSION_START_FAILED = 2;
    public static final int ERROR_CODE_TEMPORARILY_DISCONNECTED = 3;
    public static final String EXTRA_CAST_APPLICATION_ID = "com.google.android.gms.cast.EXTRA_CAST_APPLICATION_ID";
    public static final String EXTRA_CAST_RELAUNCH_APPLICATION = "com.google.android.gms.cast.EXTRA_CAST_RELAUNCH_APPLICATION";
    public static final String EXTRA_CAST_STOP_APPLICATION_WHEN_SESSION_ENDS = "com.google.android.gms.cast.EXTRA_CAST_STOP_APPLICATION_WHEN_SESSION_ENDS";
    public static final String EXTRA_CUSTOM_DATA = "com.google.android.gms.cast.EXTRA_CUSTOM_DATA";
    public static final String EXTRA_DEBUG_LOGGING_ENABLED = "com.google.android.gms.cast.EXTRA_DEBUG_LOGGING_ENABLED";
    public static final String EXTRA_ERROR_CODE = "com.google.android.gms.cast.EXTRA_ERROR_CODE";

    private CastMediaControlIntent() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x004b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.lang.String r4, java.lang.String r5, java.util.Collection<java.lang.String> r6) throws java.lang.IllegalArgumentException {
        /*
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>(r4)
            if (r5 == 0) goto L_0x0031
            java.lang.String r0 = "[A-F0-9]+"
            boolean r0 = r5.matches(r0)
            if (r0 != 0) goto L_0x0028
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Invalid appliation ID: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0028:
            java.lang.String r0 = "/"
            java.lang.StringBuffer r0 = r1.append(r0)
            r0.append(r5)
        L_0x0031:
            if (r6 == 0) goto L_0x0081
            boolean r0 = r6.isEmpty()
            if (r0 == 0) goto L_0x0041
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Must specify at least one namespace"
            r0.<init>(r1)
            throw r0
        L_0x0041:
            java.util.Iterator r2 = r6.iterator()
        L_0x0045:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x006b
            java.lang.Object r0 = r2.next()
            java.lang.String r0 = (java.lang.String) r0
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 != 0) goto L_0x0063
            java.lang.String r0 = r0.trim()
            java.lang.String r3 = ""
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0045
        L_0x0063:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Namespaces must not be null or empty"
            r0.<init>(r1)
            throw r0
        L_0x006b:
            if (r5 != 0) goto L_0x0072
            java.lang.String r0 = "/"
            r1.append(r0)
        L_0x0072:
            java.lang.String r0 = "/"
            java.lang.StringBuffer r0 = r1.append(r0)
            java.lang.String r2 = ","
            java.lang.String r2 = android.text.TextUtils.join(r2, r6)
            r0.append(r2)
        L_0x0081:
            java.lang.String r0 = r1.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastMediaControlIntent.a(java.lang.String, java.lang.String, java.util.Collection):java.lang.String");
    }

    public static String categoryForCast(String str) throws IllegalArgumentException {
        if (str != null) {
            return a(CATEGORY_CAST, str, (Collection<String>) null);
        }
        throw new IllegalArgumentException("applicationId cannot be null");
    }

    public static String categoryForCast(String str, Collection<String> collection) {
        if (str == null) {
            throw new IllegalArgumentException("applicationId cannot be null");
        } else if (collection != null) {
            return a(CATEGORY_CAST, str, collection);
        } else {
            throw new IllegalArgumentException("namespaces cannot be null");
        }
    }

    public static String categoryForCast(Collection<String> collection) throws IllegalArgumentException {
        if (collection != null) {
            return a(CATEGORY_CAST, (String) null, collection);
        }
        throw new IllegalArgumentException("namespaces cannot be null");
    }

    public static String categoryForRemotePlayback() {
        return a("com.google.android.gms.cast.CATEGORY_CAST_REMOTE_PLAYBACK", (String) null, (Collection<String>) null);
    }

    public static String categoryForRemotePlayback(String str) throws IllegalArgumentException {
        if (!TextUtils.isEmpty(str)) {
            return a("com.google.android.gms.cast.CATEGORY_CAST_REMOTE_PLAYBACK", str, (Collection<String>) null);
        }
        throw new IllegalArgumentException("applicationId cannot be null or empty");
    }
}
