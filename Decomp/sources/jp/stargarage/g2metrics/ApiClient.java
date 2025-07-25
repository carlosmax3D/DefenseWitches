package jp.stargarage.g2metrics;

import android.content.Context;
import android.os.AsyncTask;

final class ApiClient extends AsyncTask<Void, Void, Void> {
    private final IAsyncApiRequestCallBack callBack;
    private final Context context;
    private final ParamBase param;

    ApiClient(Context context2, ParamBase paramBase, IAsyncApiRequestCallBack iAsyncApiRequestCallBack) {
        this.context = context2;
        this.param = paramBase;
        this.callBack = iAsyncApiRequestCallBack;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x021d A[SYNTHETIC, Splitter:B:52:0x021d] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:54:0x0220=Splitter:B:54:0x0220, B:48:0x0206=Splitter:B:48:0x0206} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Void doInBackground(java.lang.Void... r29) {
        /*
            r28 = this;
            boolean r25 = jp.stargarage.g2metrics.Constant.devModel
            if (r25 == 0) goto L_0x0046
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param
            r25 = r0
            java.lang.String r25 = r25.getMethodType()
            java.lang.String r26 = "GET"
            boolean r25 = r25.equals(r26)
            if (r25 == 0) goto L_0x008b
            java.lang.String r25 = "G2Metrics"
            java.lang.StringBuilder r26 = new java.lang.StringBuilder
            r26.<init>()
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param
            r27 = r0
            java.lang.String r27 = r27.getMethodType()
            java.lang.StringBuilder r26 = r26.append(r27)
            java.lang.String r27 = ":"
            java.lang.StringBuilder r26 = r26.append(r27)
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param
            r27 = r0
            java.lang.String r27 = r27.getUrl()
            java.lang.StringBuilder r26 = r26.append(r27)
            java.lang.String r26 = r26.toString()
            android.util.Log.d(r25, r26)
        L_0x0046:
            r25 = 0
            java.lang.Integer r17 = java.lang.Integer.valueOf(r25)
            org.apache.http.params.BasicHttpParams r14 = new org.apache.http.params.BasicHttpParams     // Catch:{ Exception -> 0x00d5 }
            r14.<init>()     // Catch:{ Exception -> 0x00d5 }
            java.lang.Integer r25 = jp.stargarage.g2metrics.Constant.httpTimeout     // Catch:{ Exception -> 0x00d5 }
            int r25 = r25.intValue()     // Catch:{ Exception -> 0x00d5 }
            r0 = r25
            org.apache.http.params.HttpConnectionParams.setSoTimeout(r14, r0)     // Catch:{ Exception -> 0x00d5 }
            org.apache.http.impl.client.DefaultHttpClient r11 = new org.apache.http.impl.client.DefaultHttpClient     // Catch:{ Exception -> 0x00d5 }
            r11.<init>(r14)     // Catch:{ Exception -> 0x00d5 }
            r0 = r28
            android.content.Context r0 = r0.context     // Catch:{ Exception -> 0x00d5 }
            r25 = r0
            java.lang.String r26 = "connectivity"
            java.lang.Object r6 = r25.getSystemService(r26)     // Catch:{ Exception -> 0x00d5 }
            android.net.ConnectivityManager r6 = (android.net.ConnectivityManager) r6     // Catch:{ Exception -> 0x00d5 }
            android.net.NetworkInfo r23 = r6.getActiveNetworkInfo()     // Catch:{ Exception -> 0x00d5 }
            if (r23 == 0) goto L_0x007b
            boolean r25 = r23.isConnected()     // Catch:{ Exception -> 0x00d5 }
            if (r25 != 0) goto L_0x00eb
        L_0x007b:
            r0 = r28
            jp.stargarage.g2metrics.IAsyncApiRequestCallBack r0 = r0.callBack     // Catch:{ Exception -> 0x00d5 }
            r25 = r0
            r0 = r25
            r1 = r17
            r0.onFailure(r1)     // Catch:{ Exception -> 0x00d5 }
            r25 = 0
        L_0x008a:
            return r25
        L_0x008b:
            java.lang.String r25 = "G2Metrics"
            java.lang.StringBuilder r26 = new java.lang.StringBuilder
            r26.<init>()
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param
            r27 = r0
            java.lang.String r27 = r27.getMethodType()
            java.lang.StringBuilder r26 = r26.append(r27)
            java.lang.String r27 = ":"
            java.lang.StringBuilder r26 = r26.append(r27)
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param
            r27 = r0
            java.lang.String r27 = r27.getUrl()
            java.lang.StringBuilder r26 = r26.append(r27)
            java.lang.String r27 = "\rBODY:"
            java.lang.StringBuilder r26 = r26.append(r27)
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param
            r27 = r0
            org.json.JSONObject r27 = r27.toJson()
            java.lang.String r27 = r27.toString()
            java.lang.StringBuilder r26 = r26.append(r27)
            java.lang.String r26 = r26.toString()
            android.util.Log.d(r25, r26)
            goto L_0x0046
        L_0x00d5:
            r7 = move-exception
            r7.printStackTrace()
            r0 = r28
            jp.stargarage.g2metrics.IAsyncApiRequestCallBack r0 = r0.callBack
            r25 = r0
            r26 = 0
            java.lang.Integer r26 = java.lang.Integer.valueOf(r26)
            r25.onFailure(r26)
            r25 = 0
            goto L_0x008a
        L_0x00eb:
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param     // Catch:{ Exception -> 0x0188 }
            r25 = r0
            java.lang.String r25 = r25.getMethodType()     // Catch:{ Exception -> 0x0188 }
            java.lang.String r26 = "GET"
            boolean r25 = r25.equals(r26)     // Catch:{ Exception -> 0x0188 }
            if (r25 == 0) goto L_0x01bd
            org.apache.http.client.methods.HttpGet r13 = new org.apache.http.client.methods.HttpGet     // Catch:{ Exception -> 0x0188 }
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param     // Catch:{ Exception -> 0x0188 }
            r25 = r0
            java.lang.String r25 = r25.getUrl()     // Catch:{ Exception -> 0x0188 }
            r0 = r25
            r13.<init>(r0)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r25 = "Accept-Encoding"
            java.lang.String r26 = "gzip"
            r0 = r25
            r1 = r26
            r13.setHeader(r0, r1)     // Catch:{ Exception -> 0x0188 }
            org.apache.http.client.methods.HttpGet r25 = new org.apache.http.client.methods.HttpGet     // Catch:{ Exception -> 0x0188 }
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param     // Catch:{ Exception -> 0x0188 }
            r26 = r0
            java.lang.String r26 = r26.getUrl()     // Catch:{ Exception -> 0x0188 }
            r25.<init>(r26)     // Catch:{ Exception -> 0x0188 }
            r0 = r25
            org.apache.http.HttpResponse r16 = r11.execute(r0)     // Catch:{ Exception -> 0x0188 }
        L_0x012e:
            org.apache.http.StatusLine r25 = r16.getStatusLine()     // Catch:{ Exception -> 0x0188 }
            int r25 = r25.getStatusCode()     // Catch:{ Exception -> 0x0188 }
            java.lang.Integer r17 = java.lang.Integer.valueOf(r25)     // Catch:{ Exception -> 0x0188 }
            int r25 = r17.intValue()     // Catch:{ Exception -> 0x0188 }
            r26 = 200(0xc8, float:2.8E-43)
            r0 = r25
            r1 = r26
            if (r0 != r1) goto L_0x0295
            org.apache.http.HttpEntity r12 = r16.getEntity()     // Catch:{ Exception -> 0x0188 }
            java.io.InputStream r19 = r12.getContent()     // Catch:{ Exception -> 0x0188 }
            org.apache.http.Header r10 = r12.getContentEncoding()     // Catch:{ Exception -> 0x0188 }
            if (r10 == 0) goto L_0x016b
            java.lang.String r25 = r10.getValue()     // Catch:{ Exception -> 0x0188 }
            java.lang.String r26 = "gzip"
            boolean r25 = r25.contains(r26)     // Catch:{ Exception -> 0x0188 }
            if (r25 == 0) goto L_0x016b
            java.util.zip.GZIPInputStream r20 = new java.util.zip.GZIPInputStream     // Catch:{ Exception -> 0x0188 }
            r0 = r20
            r1 = r19
            r0.<init>(r1)     // Catch:{ Exception -> 0x0188 }
            r19 = r20
        L_0x016b:
            java.io.InputStreamReader r18 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0188 }
            r18.<init>(r19)     // Catch:{ Exception -> 0x0188 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0188 }
            r0 = r18
            r3.<init>(r0)     // Catch:{ Exception -> 0x0188 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0188 }
            r4.<init>()     // Catch:{ Exception -> 0x0188 }
        L_0x017c:
            java.lang.String r22 = r3.readLine()     // Catch:{ Exception -> 0x0188 }
            if (r22 == 0) goto L_0x0221
            r0 = r22
            r4.append(r0)     // Catch:{ Exception -> 0x0188 }
            goto L_0x017c
        L_0x0188:
            r7 = move-exception
            r7.printStackTrace()
            r0 = r28
            jp.stargarage.g2metrics.IAsyncApiRequestCallBack r0 = r0.callBack
            r25 = r0
            r0 = r25
            r1 = r17
            r0.onFailure(r1)
            boolean r25 = jp.stargarage.g2metrics.Constant.devModel
            if (r25 == 0) goto L_0x01b9
            java.lang.String r25 = "G2Metrics"
            java.lang.StringBuilder r26 = new java.lang.StringBuilder
            r26.<init>()
            java.lang.String r27 = "RESPONSE HTTP CODE:"
            java.lang.StringBuilder r26 = r26.append(r27)
            r0 = r26
            r1 = r17
            java.lang.StringBuilder r26 = r0.append(r1)
            java.lang.String r26 = r26.toString()
            android.util.Log.d(r25, r26)
        L_0x01b9:
            r25 = 0
            goto L_0x008a
        L_0x01bd:
            org.apache.http.client.methods.HttpPost r15 = new org.apache.http.client.methods.HttpPost     // Catch:{ Exception -> 0x0188 }
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param     // Catch:{ Exception -> 0x0188 }
            r25 = r0
            java.lang.String r25 = r25.getUrl()     // Catch:{ Exception -> 0x0188 }
            r0 = r25
            r15.<init>(r0)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r25 = "Accept-Encoding"
            java.lang.String r26 = "gzip"
            r0 = r25
            r1 = r26
            r15.setHeader(r0, r1)     // Catch:{ Exception -> 0x0188 }
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param     // Catch:{ Exception -> 0x0188 }
            r25 = r0
            org.json.JSONObject r25 = r25.toJson()     // Catch:{ Exception -> 0x0188 }
            java.lang.String r21 = r25.toString()     // Catch:{ Exception -> 0x0188 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0188 }
            r5.<init>()     // Catch:{ Exception -> 0x0188 }
            r8 = 0
            java.util.zip.GZIPOutputStream r9 = new java.util.zip.GZIPOutputStream     // Catch:{ all -> 0x021a }
            r9.<init>(r5)     // Catch:{ all -> 0x021a }
            java.lang.String r25 = "UTF-8"
            r0 = r21
            r1 = r25
            byte[] r25 = r0.getBytes(r1)     // Catch:{ all -> 0x02c9 }
            r0 = r25
            r9.write(r0)     // Catch:{ all -> 0x02c9 }
            if (r9 == 0) goto L_0x0206
            r9.close()     // Catch:{ IOException -> 0x02c3 }
        L_0x0206:
            org.apache.http.entity.ByteArrayEntity r2 = new org.apache.http.entity.ByteArrayEntity     // Catch:{ Exception -> 0x0188 }
            byte[] r25 = r5.toByteArray()     // Catch:{ Exception -> 0x0188 }
            r0 = r25
            r2.<init>(r0)     // Catch:{ Exception -> 0x0188 }
            r15.setEntity(r2)     // Catch:{ Exception -> 0x0188 }
            org.apache.http.HttpResponse r16 = r11.execute(r15)     // Catch:{ Exception -> 0x0188 }
            goto L_0x012e
        L_0x021a:
            r25 = move-exception
        L_0x021b:
            if (r8 == 0) goto L_0x0220
            r8.close()     // Catch:{ IOException -> 0x02c6 }
        L_0x0220:
            throw r25     // Catch:{ Exception -> 0x0188 }
        L_0x0221:
            r3.close()     // Catch:{ Exception -> 0x0188 }
            r18.close()     // Catch:{ Exception -> 0x0188 }
            r19.close()     // Catch:{ Exception -> 0x0188 }
            java.lang.String r21 = r4.toString()     // Catch:{ Exception -> 0x0188 }
            boolean r25 = jp.stargarage.g2metrics.Constant.devModel     // Catch:{ Exception -> 0x0188 }
            if (r25 == 0) goto L_0x024e
            java.lang.String r25 = "G2Metrics"
            java.lang.StringBuilder r26 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0188 }
            r26.<init>()     // Catch:{ Exception -> 0x0188 }
            java.lang.String r27 = "RESPONSE:"
            java.lang.StringBuilder r26 = r26.append(r27)     // Catch:{ Exception -> 0x0188 }
            r0 = r26
            r1 = r21
            java.lang.StringBuilder r26 = r0.append(r1)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r26 = r26.toString()     // Catch:{ Exception -> 0x0188 }
            android.util.Log.d(r25, r26)     // Catch:{ Exception -> 0x0188 }
        L_0x024e:
            org.apache.http.conn.ClientConnectionManager r25 = r11.getConnectionManager()     // Catch:{ Exception -> 0x0188 }
            r25.shutdown()     // Catch:{ Exception -> 0x0188 }
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param     // Catch:{ Exception -> 0x0188 }
            r25 = r0
            java.lang.Class r25 = r25.getResponseClass()     // Catch:{ Exception -> 0x0188 }
            if (r25 != 0) goto L_0x0270
            r0 = r28
            jp.stargarage.g2metrics.IAsyncApiRequestCallBack r0 = r0.callBack     // Catch:{ Exception -> 0x0188 }
            r25 = r0
            r26 = 0
            r25.onSuccess(r26)     // Catch:{ Exception -> 0x0188 }
        L_0x026c:
            r25 = 0
            goto L_0x008a
        L_0x0270:
            r0 = r28
            jp.stargarage.g2metrics.ParamBase r0 = r0.param     // Catch:{ Exception -> 0x0188 }
            r25 = r0
            java.lang.Class r25 = r25.getResponseClass()     // Catch:{ Exception -> 0x0188 }
            java.lang.Object r24 = r25.newInstance()     // Catch:{ Exception -> 0x0188 }
            jp.stargarage.g2metrics.ApiEntityBase r24 = (jp.stargarage.g2metrics.ApiEntityBase) r24     // Catch:{ Exception -> 0x0188 }
            r0 = r24
            r1 = r21
            r0.setDataByJsonStr(r1)     // Catch:{ Exception -> 0x0188 }
            r0 = r28
            jp.stargarage.g2metrics.IAsyncApiRequestCallBack r0 = r0.callBack     // Catch:{ Exception -> 0x0188 }
            r25 = r0
            r0 = r25
            r1 = r24
            r0.onSuccess(r1)     // Catch:{ Exception -> 0x0188 }
            goto L_0x026c
        L_0x0295:
            boolean r25 = jp.stargarage.g2metrics.Constant.devModel     // Catch:{ Exception -> 0x0188 }
            if (r25 == 0) goto L_0x02b5
            java.lang.String r25 = "G2Metrics"
            java.lang.StringBuilder r26 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0188 }
            r26.<init>()     // Catch:{ Exception -> 0x0188 }
            java.lang.String r27 = "RESPONSE HTTP CODE:"
            java.lang.StringBuilder r26 = r26.append(r27)     // Catch:{ Exception -> 0x0188 }
            r0 = r26
            r1 = r17
            java.lang.StringBuilder r26 = r0.append(r1)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r26 = r26.toString()     // Catch:{ Exception -> 0x0188 }
            android.util.Log.d(r25, r26)     // Catch:{ Exception -> 0x0188 }
        L_0x02b5:
            r0 = r28
            jp.stargarage.g2metrics.IAsyncApiRequestCallBack r0 = r0.callBack     // Catch:{ Exception -> 0x0188 }
            r25 = r0
            r0 = r25
            r1 = r17
            r0.onFailure(r1)     // Catch:{ Exception -> 0x0188 }
            goto L_0x026c
        L_0x02c3:
            r25 = move-exception
            goto L_0x0206
        L_0x02c6:
            r26 = move-exception
            goto L_0x0220
        L_0x02c9:
            r25 = move-exception
            r8 = r9
            goto L_0x021b
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.stargarage.g2metrics.ApiClient.doInBackground(java.lang.Void[]):java.lang.Void");
    }

    /* access modifiers changed from: package-private */
    public void executeSync() {
        doInBackground(new Void[0]);
    }
}
