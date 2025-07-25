package com.tapjoy;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;

public class TapjoyURLConnection {
    private static final String TAG = "TapjoyURLConnection";
    public static final int TYPE_GET = 0;
    public static final int TYPE_POST = 1;

    public String getContentLength(String str) {
        String str2 = null;
        try {
            String replaceAll = str.replaceAll(" ", "%20");
            TapjoyLog.i(TAG, "requestURL: " + replaceAll);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(replaceAll).openConnection();
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(30000);
            str2 = httpURLConnection.getHeaderField("content-length");
        } catch (Exception e) {
            TapjoyLog.e(TAG, "Exception: " + e.toString());
        }
        TapjoyLog.i(TAG, "content-length: " + str2);
        return str2;
    }

    public TapjoyHttpURLResponse getRedirectFromURL(String str) {
        return getResponseFromURL(str, BuildConfig.FLAVOR, 0, true);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String str) {
        return getResponseFromURL(str, BuildConfig.FLAVOR, 0);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String str, String str2) {
        return getResponseFromURL(str, str2, 0);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String str, String str2, int i) {
        return getResponseFromURL(str, str2, i, false);
    }

    /* JADX WARNING: type inference failed for: r17v31, types: [java.net.URLConnection] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00e9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tapjoy.TapjoyHttpURLResponse getResponseFromURL(java.lang.String r21, java.lang.String r22, int r23, boolean r24) {
        /*
            r20 = this;
            com.tapjoy.TapjoyHttpURLResponse r16 = new com.tapjoy.TapjoyHttpURLResponse
            r16.<init>()
            r3 = 0
            r9 = 0
            r14 = 0
            r8 = 0
            java.lang.StringBuilder r17 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02bb }
            r17.<init>()     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            r1 = r21
            java.lang.StringBuilder r17 = r0.append(r1)     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            r1 = r22
            java.lang.StringBuilder r17 = r0.append(r1)     // Catch:{ Exception -> 0x02bb }
            java.lang.String r12 = r17.toString()     // Catch:{ Exception -> 0x02bb }
            java.lang.String r18 = "TapjoyURLConnection"
            java.lang.StringBuilder r17 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02bb }
            r17.<init>()     // Catch:{ Exception -> 0x02bb }
            java.lang.String r19 = "http "
            r0 = r17
            r1 = r19
            java.lang.StringBuilder r19 = r0.append(r1)     // Catch:{ Exception -> 0x02bb }
            if (r23 != 0) goto L_0x01e6
            java.lang.String r17 = "get"
        L_0x0037:
            r0 = r19
            r1 = r17
            java.lang.StringBuilder r17 = r0.append(r1)     // Catch:{ Exception -> 0x02bb }
            java.lang.String r19 = ": "
            r0 = r17
            r1 = r19
            java.lang.StringBuilder r17 = r0.append(r1)     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            java.lang.StringBuilder r17 = r0.append(r12)     // Catch:{ Exception -> 0x02bb }
            java.lang.String r17 = r17.toString()     // Catch:{ Exception -> 0x02bb }
            r0 = r18
            r1 = r17
            com.tapjoy.TapjoyLog.i(r0, r1)     // Catch:{ Exception -> 0x02bb }
            java.lang.String r17 = "unit_test_mode"
            java.lang.String r17 = com.tapjoy.TapjoyConnectCore.getConnectFlagValue(r17)     // Catch:{ Exception -> 0x02bb }
            java.lang.String r18 = ""
            r0 = r17
            r1 = r18
            if (r0 == r1) goto L_0x01f5
            org.apache.http.impl.client.DefaultHttpClient r2 = new org.apache.http.impl.client.DefaultHttpClient     // Catch:{ Exception -> 0x02bb }
            r2.<init>()     // Catch:{ Exception -> 0x02bb }
            r17 = 1
            r0 = r23
            r1 = r17
            if (r0 != r1) goto L_0x01ea
            org.apache.http.client.methods.HttpPost r11 = new org.apache.http.client.methods.HttpPost     // Catch:{ Exception -> 0x02bb }
            r11.<init>(r12)     // Catch:{ Exception -> 0x02bb }
            org.apache.http.HttpResponse r13 = r2.execute(r11)     // Catch:{ Exception -> 0x02bb }
        L_0x007e:
            org.apache.http.StatusLine r17 = r13.getStatusLine()     // Catch:{ Exception -> 0x02bb }
            int r17 = r17.getStatusCode()     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            r1 = r16
            r1.statusCode = r0     // Catch:{ Exception -> 0x02bb }
            java.io.BufferedReader r10 = new java.io.BufferedReader     // Catch:{ Exception -> 0x02bb }
            java.io.InputStreamReader r17 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x02bb }
            org.apache.http.HttpEntity r18 = r13.getEntity()     // Catch:{ Exception -> 0x02bb }
            java.io.InputStream r18 = r18.getContent()     // Catch:{ Exception -> 0x02bb }
            r17.<init>(r18)     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            r10.<init>(r0)     // Catch:{ Exception -> 0x02bb }
            r9 = r10
        L_0x00a1:
            if (r24 != 0) goto L_0x0260
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02bb }
            r15.<init>()     // Catch:{ Exception -> 0x02bb }
        L_0x00a8:
            java.lang.String r8 = r9.readLine()     // Catch:{ Exception -> 0x00c9 }
            if (r8 == 0) goto L_0x0255
            java.lang.StringBuilder r17 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c9 }
            r17.<init>()     // Catch:{ Exception -> 0x00c9 }
            r0 = r17
            java.lang.StringBuilder r17 = r0.append(r8)     // Catch:{ Exception -> 0x00c9 }
            r18 = 10
            java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r17 = r17.toString()     // Catch:{ Exception -> 0x00c9 }
            r0 = r17
            r15.append(r0)     // Catch:{ Exception -> 0x00c9 }
            goto L_0x00a8
        L_0x00c9:
            r5 = move-exception
            r10 = r9
        L_0x00cb:
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "Exception: "
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = r5.toString()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tapjoy.TapjoyLog.e(r17, r18)
            if (r3 == 0) goto L_0x02d5
            r0 = r16
            java.lang.String r0 = r0.response     // Catch:{ Exception -> 0x02cc }
            r17 = r0
            if (r17 != 0) goto L_0x02d5
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ Exception -> 0x02cc }
            java.io.InputStreamReader r17 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x02cc }
            java.io.InputStream r18 = r3.getErrorStream()     // Catch:{ Exception -> 0x02cc }
            r17.<init>(r18)     // Catch:{ Exception -> 0x02cc }
            r0 = r17
            r9.<init>(r0)     // Catch:{ Exception -> 0x02cc }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02d1 }
            r14.<init>()     // Catch:{ Exception -> 0x02d1 }
        L_0x0106:
            java.lang.String r8 = r9.readLine()     // Catch:{ Exception -> 0x0127 }
            if (r8 == 0) goto L_0x02c0
            java.lang.StringBuilder r17 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0127 }
            r17.<init>()     // Catch:{ Exception -> 0x0127 }
            r0 = r17
            java.lang.StringBuilder r17 = r0.append(r8)     // Catch:{ Exception -> 0x0127 }
            r18 = 10
            java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ Exception -> 0x0127 }
            java.lang.String r17 = r17.toString()     // Catch:{ Exception -> 0x0127 }
            r0 = r17
            r14.append(r0)     // Catch:{ Exception -> 0x0127 }
            goto L_0x0106
        L_0x0127:
            r6 = move-exception
        L_0x0128:
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "Exception trying to get error code/content: "
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = r6.toString()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tapjoy.TapjoyLog.e(r17, r18)
        L_0x0144:
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.String r18 = "--------------------"
            com.tapjoy.TapjoyLog.i(r17, r18)
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "response status: "
            java.lang.StringBuilder r18 = r18.append(r19)
            r0 = r16
            int r0 = r0.statusCode
            r19 = r0
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tapjoy.TapjoyLog.i(r17, r18)
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "response size: "
            java.lang.StringBuilder r18 = r18.append(r19)
            r0 = r16
            int r0 = r0.contentLength
            r19 = r0
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tapjoy.TapjoyLog.i(r17, r18)
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.String r18 = "response: "
            com.tapjoy.TapjoyLog.v(r17, r18)
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = ""
            java.lang.StringBuilder r18 = r18.append(r19)
            r0 = r16
            java.lang.String r0 = r0.response
            r19 = r0
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tapjoy.TapjoyLog.v(r17, r18)
            r0 = r16
            java.lang.String r0 = r0.redirectURL
            r17 = r0
            if (r17 == 0) goto L_0x01de
            r0 = r16
            java.lang.String r0 = r0.redirectURL
            r17 = r0
            int r17 = r17.length()
            if (r17 <= 0) goto L_0x01de
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "redirectURL: "
            java.lang.StringBuilder r18 = r18.append(r19)
            r0 = r16
            java.lang.String r0 = r0.redirectURL
            r19 = r0
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tapjoy.TapjoyLog.i(r17, r18)
        L_0x01de:
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.String r18 = "--------------------"
            com.tapjoy.TapjoyLog.i(r17, r18)
            return r16
        L_0x01e6:
            java.lang.String r17 = "post"
            goto L_0x0037
        L_0x01ea:
            org.apache.http.client.methods.HttpGet r11 = new org.apache.http.client.methods.HttpGet     // Catch:{ Exception -> 0x02bb }
            r11.<init>(r12)     // Catch:{ Exception -> 0x02bb }
            org.apache.http.HttpResponse r13 = r2.execute(r11)     // Catch:{ Exception -> 0x02bb }
            goto L_0x007e
        L_0x01f5:
            java.net.URL r7 = new java.net.URL     // Catch:{ Exception -> 0x02bb }
            r7.<init>(r12)     // Catch:{ Exception -> 0x02bb }
            java.net.URLConnection r17 = r7.openConnection()     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x02bb }
            r3 = r0
            if (r24 == 0) goto L_0x020c
            r17 = 0
            r0 = r17
            r3.setInstanceFollowRedirects(r0)     // Catch:{ Exception -> 0x02bb }
        L_0x020c:
            r17 = 15000(0x3a98, float:2.102E-41)
            r0 = r17
            r3.setConnectTimeout(r0)     // Catch:{ Exception -> 0x02bb }
            r17 = 30000(0x7530, float:4.2039E-41)
            r0 = r17
            r3.setReadTimeout(r0)     // Catch:{ Exception -> 0x02bb }
            r17 = 1
            r0 = r23
            r1 = r17
            if (r0 != r1) goto L_0x0229
            java.lang.String r17 = "POST"
            r0 = r17
            r3.setRequestMethod(r0)     // Catch:{ Exception -> 0x02bb }
        L_0x0229:
            r3.connect()     // Catch:{ Exception -> 0x02bb }
            int r17 = r3.getResponseCode()     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            r1 = r16
            r1.statusCode = r0     // Catch:{ Exception -> 0x02bb }
            java.util.Map r17 = r3.getHeaderFields()     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            r1 = r16
            r1.headerFields = r0     // Catch:{ Exception -> 0x02bb }
            if (r24 != 0) goto L_0x00a1
            java.io.BufferedReader r10 = new java.io.BufferedReader     // Catch:{ Exception -> 0x02bb }
            java.io.InputStreamReader r17 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x02bb }
            java.io.InputStream r18 = r3.getInputStream()     // Catch:{ Exception -> 0x02bb }
            r17.<init>(r18)     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            r10.<init>(r0)     // Catch:{ Exception -> 0x02bb }
            r9 = r10
            goto L_0x00a1
        L_0x0255:
            java.lang.String r17 = r15.toString()     // Catch:{ Exception -> 0x00c9 }
            r0 = r17
            r1 = r16
            r1.response = r0     // Catch:{ Exception -> 0x00c9 }
            r14 = r15
        L_0x0260:
            r0 = r16
            int r0 = r0.statusCode     // Catch:{ Exception -> 0x02bb }
            r17 = r0
            r18 = 302(0x12e, float:4.23E-43)
            r0 = r17
            r1 = r18
            if (r0 != r1) goto L_0x027c
            java.lang.String r17 = "Location"
            r0 = r17
            java.lang.String r17 = r3.getHeaderField(r0)     // Catch:{ Exception -> 0x02bb }
            r0 = r17
            r1 = r16
            r1.redirectURL = r0     // Catch:{ Exception -> 0x02bb }
        L_0x027c:
            java.lang.String r17 = "content-length"
            r0 = r17
            java.lang.String r4 = r3.getHeaderField(r0)     // Catch:{ Exception -> 0x02bb }
            if (r4 == 0) goto L_0x0294
            java.lang.Integer r17 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x029d }
            int r17 = r17.intValue()     // Catch:{ Exception -> 0x029d }
            r0 = r17
            r1 = r16
            r1.contentLength = r0     // Catch:{ Exception -> 0x029d }
        L_0x0294:
            if (r9 == 0) goto L_0x029a
            r9.close()     // Catch:{ Exception -> 0x02bb }
            r9 = 0
        L_0x029a:
            r3 = 0
            goto L_0x0144
        L_0x029d:
            r5 = move-exception
            java.lang.String r17 = "TapjoyURLConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02bb }
            r18.<init>()     // Catch:{ Exception -> 0x02bb }
            java.lang.String r19 = "Exception: "
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x02bb }
            java.lang.String r19 = r5.toString()     // Catch:{ Exception -> 0x02bb }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x02bb }
            java.lang.String r18 = r18.toString()     // Catch:{ Exception -> 0x02bb }
            com.tapjoy.TapjoyLog.e(r17, r18)     // Catch:{ Exception -> 0x02bb }
            goto L_0x0294
        L_0x02bb:
            r5 = move-exception
            r15 = r14
            r10 = r9
            goto L_0x00cb
        L_0x02c0:
            java.lang.String r17 = r14.toString()     // Catch:{ Exception -> 0x0127 }
            r0 = r17
            r1 = r16
            r1.response = r0     // Catch:{ Exception -> 0x0127 }
            goto L_0x0144
        L_0x02cc:
            r6 = move-exception
            r14 = r15
            r9 = r10
            goto L_0x0128
        L_0x02d1:
            r6 = move-exception
            r14 = r15
            goto L_0x0128
        L_0x02d5:
            r14 = r15
            r9 = r10
            goto L_0x0144
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tapjoy.TapjoyURLConnection.getResponseFromURL(java.lang.String, java.lang.String, int, boolean):com.tapjoy.TapjoyHttpURLResponse");
    }

    public TapjoyHttpURLResponse getResponseFromURL(String str, Map<String, String> map) {
        return getResponseFromURL(str, TapjoyUtil.convertURLParams(map, false), 0);
    }

    public TapjoyHttpURLResponse getResponseFromURL(String str, Map<String, String> map, int i) {
        return getResponseFromURL(str, TapjoyUtil.convertURLParams(map, false), i);
    }
}
