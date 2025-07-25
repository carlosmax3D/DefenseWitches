package com.threatmetrix.TrustDefenderMobile;

class PutXML implements Runnable {
    private static final String TAG = PutXML.class.getName();
    private String fp_server = null;
    private String org_id = null;
    private String session_id = null;
    private int timeout = 10000;
    private String w = null;

    public PutXML(String str, String str2, String str3, String str4, int i) {
        this.fp_server = str;
        this.org_id = str2;
        this.session_id = str3;
        this.w = str4;
        this.timeout = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0090 A[SYNTHETIC, Splitter:B:23:0x0090] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a0 A[SYNTHETIC, Splitter:B:32:0x00a0] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ab A[SYNTHETIC, Splitter:B:38:0x00ab] */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r10 = this;
            r6 = 0
            java.net.Socket r7 = new java.net.Socket     // Catch:{ UnknownHostException -> 0x0086, IOException -> 0x0096 }
            java.lang.String r8 = r10.fp_server     // Catch:{ UnknownHostException -> 0x0086, IOException -> 0x0096 }
            r9 = 8080(0x1f90, float:1.1322E-41)
            r7.<init>(r8, r9)     // Catch:{ UnknownHostException -> 0x0086, IOException -> 0x0096 }
            int r8 = r10.timeout     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r7.setSoTimeout(r8)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.io.OutputStream r4 = r7.getOutputStream()     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.io.BufferedWriter r5 = new java.io.BufferedWriter     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.io.PrintWriter r8 = new java.io.PrintWriter     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r8.<init>(r4)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r5.<init>(r8)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.io.InputStream r9 = r7.getInputStream()     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r8.<init>(r9)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r3.<init>(r8)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.String r9 = "<handle sig=FF44EE55 session_id="
            r8.<init>(r9)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.String r9 = r10.session_id     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.String r9 = " org_id="
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.String r9 = r10.org_id     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.String r9 = " w="
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.String r9 = r10.w     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.String r9 = " />"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            java.lang.String r8 = r8.toString()     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r5.write(r8)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r5.flush()     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            int r8 = r3.read()     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r9 = -1
            if (r8 == r9) goto L_0x007c
            r8 = 1
            char[] r1 = new char[r8]     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r8 = 0
            r9 = 0
            r1[r8] = r9     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            r2 = 0
        L_0x006f:
            r8 = 15
            if (r2 >= r8) goto L_0x0079
            r5.write(r1)     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
            int r2 = r2 + 1
            goto L_0x006f
        L_0x0079:
            r3.read()     // Catch:{ UnknownHostException -> 0x00b7, IOException -> 0x00b4, all -> 0x00b1 }
        L_0x007c:
            java.lang.String r8 = TAG
            r7.close()     // Catch:{ IOException -> 0x0083 }
            r6 = r7
        L_0x0082:
            return
        L_0x0083:
            r8 = move-exception
            r6 = r7
            goto L_0x0082
        L_0x0086:
            r0 = move-exception
        L_0x0087:
            java.lang.String r8 = "Failed to connect to the fp server"
            android.util.Log.w(r8, r0)     // Catch:{ all -> 0x00a6 }
            java.lang.String r8 = TAG
            if (r6 == 0) goto L_0x0082
            r6.close()     // Catch:{ IOException -> 0x0094 }
            goto L_0x0082
        L_0x0094:
            r8 = move-exception
            goto L_0x0082
        L_0x0096:
            r0 = move-exception
        L_0x0097:
            java.lang.String r8 = "Failed to read/write to the fp server"
            android.util.Log.w(r8, r0)     // Catch:{ all -> 0x00a6 }
            java.lang.String r8 = TAG
            if (r6 == 0) goto L_0x0082
            r6.close()     // Catch:{ IOException -> 0x00a4 }
            goto L_0x0082
        L_0x00a4:
            r8 = move-exception
            goto L_0x0082
        L_0x00a6:
            r8 = move-exception
        L_0x00a7:
            java.lang.String r9 = TAG
            if (r6 == 0) goto L_0x00ae
            r6.close()     // Catch:{ IOException -> 0x00af }
        L_0x00ae:
            throw r8
        L_0x00af:
            r9 = move-exception
            goto L_0x00ae
        L_0x00b1:
            r8 = move-exception
            r6 = r7
            goto L_0x00a7
        L_0x00b4:
            r0 = move-exception
            r6 = r7
            goto L_0x0097
        L_0x00b7:
            r0 = move-exception
            r6 = r7
            goto L_0x0087
        */
        throw new UnsupportedOperationException("Method not decompiled: com.threatmetrix.TrustDefenderMobile.PutXML.run():void");
    }
}
