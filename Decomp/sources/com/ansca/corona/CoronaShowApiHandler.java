package com.ansca.corona;

import com.ansca.corona.listeners.CoronaShowApiListener;

class CoronaShowApiHandler implements CoronaShowApiListener {
    /* access modifiers changed from: private */
    public CoronaActivity fActivity;
    /* access modifiers changed from: private */
    public CoronaRuntime fCoronaRuntime;

    public CoronaShowApiHandler(CoronaActivity coronaActivity, CoronaRuntime coronaRuntime) {
        this.fActivity = coronaActivity;
        this.fCoronaRuntime = coronaRuntime;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: java.lang.String} */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c9, code lost:
        r1 = (java.lang.String) r13.get("nookAppEAN");
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean showAppStoreWindow(java.util.HashMap<java.lang.String, java.lang.Object> r13) {
        /*
            r12 = this;
            r9 = 0
            com.ansca.corona.CoronaActivity r0 = r12.fActivity
            if (r0 != 0) goto L_0x0006
        L_0x0005:
            return r9
        L_0x0006:
            java.lang.String r6 = com.ansca.corona.purchasing.StoreServices.getTargetedAppStoreName()
            java.lang.String r10 = "none"
            boolean r10 = r6.equals(r10)
            if (r10 == 0) goto L_0x0016
            java.lang.String r6 = com.ansca.corona.purchasing.StoreServices.getStoreApplicationWasPurchasedFrom()
        L_0x0016:
            java.lang.String r10 = "none"
            boolean r10 = r6.equals(r10)
            if (r10 == 0) goto L_0x0052
            if (r13 == 0) goto L_0x0052
            java.lang.String[] r2 = com.ansca.corona.purchasing.StoreServices.getAvailableAppStoreNames()
            java.lang.String r10 = "supportedAndroidStores"
            java.lang.Object r3 = r13.get(r10)
            if (r2 == 0) goto L_0x0052
            boolean r10 = r3 instanceof java.util.HashMap
            if (r10 == 0) goto L_0x0052
            java.util.HashMap r3 = (java.util.HashMap) r3
            java.util.Collection r10 = r3.values()
            java.util.Iterator r10 = r10.iterator()
        L_0x003a:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x0052
            java.lang.Object r4 = r10.next()
            boolean r11 = r4 instanceof java.lang.String
            if (r11 == 0) goto L_0x003a
            r7 = r4
            java.lang.String r7 = (java.lang.String) r7
            int r11 = java.util.Arrays.binarySearch(r2, r7)
            if (r11 < 0) goto L_0x003a
            r6 = r7
        L_0x0052:
            r5 = 0
            if (r13 == 0) goto L_0x0065
            java.lang.String r10 = "androidAppPackageName"
            java.lang.Object r8 = r13.get(r10)
            boolean r10 = r8 instanceof java.lang.String
            if (r10 == 0) goto L_0x0065
            java.lang.String r8 = (java.lang.String) r8
            java.lang.String r5 = r8.trim()
        L_0x0065:
            if (r5 == 0) goto L_0x006d
            int r10 = r5.length()
            if (r10 > 0) goto L_0x0071
        L_0x006d:
            java.lang.String r5 = r0.getPackageName()
        L_0x0071:
            java.lang.String r10 = "google"
            boolean r10 = r6.equals(r10)
            if (r10 == 0) goto L_0x0098
            com.ansca.corona.CoronaRuntime r9 = r12.fCoronaRuntime
            com.ansca.corona.Controller r9 = r9.getController()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "market://details?id="
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r5)
            java.lang.String r10 = r10.toString()
            boolean r9 = r9.openUrl(r10)
            goto L_0x0005
        L_0x0098:
            java.lang.String r10 = "amazon"
            boolean r10 = r6.equals(r10)
            if (r10 == 0) goto L_0x00bf
            com.ansca.corona.CoronaRuntime r9 = r12.fCoronaRuntime
            com.ansca.corona.Controller r9 = r9.getController()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "http://www.amazon.com/gp/mas/dl/android?p="
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r5)
            java.lang.String r10 = r10.toString()
            boolean r9 = r9.openUrl(r10)
            goto L_0x0005
        L_0x00bf:
            java.lang.String r10 = "nook"
            boolean r10 = r6.equals(r10)
            if (r10 == 0) goto L_0x00e4
            if (r13 == 0) goto L_0x0005
            java.lang.String r10 = "nookAppEAN"
            java.lang.Object r1 = r13.get(r10)
            java.lang.String r1 = (java.lang.String) r1
            if (r1 == 0) goto L_0x0005
            int r10 = r1.length()
            if (r10 <= 0) goto L_0x0005
            com.ansca.corona.CoronaShowApiHandler$1 r9 = new com.ansca.corona.CoronaShowApiHandler$1
            r9.<init>(r1)
            r0.runOnUiThread(r9)
            r9 = 1
            goto L_0x0005
        L_0x00e4:
            java.lang.String r10 = "samsung"
            boolean r10 = r6.equals(r10)
            if (r10 == 0) goto L_0x0005
            com.ansca.corona.CoronaRuntime r9 = r12.fCoronaRuntime
            com.ansca.corona.Controller r9 = r9.getController()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "samsungapps://ProductDetail/"
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r5)
            java.lang.String r10 = r10.toString()
            boolean r9 = r9.openUrl(r10)
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.CoronaShowApiHandler.showAppStoreWindow(java.util.HashMap):boolean");
    }

    public void showCameraWindowForImage(String str) {
        if (this.fActivity != null) {
            this.fActivity.showCameraWindowForImage(str);
        }
    }

    public void showCameraWindowForVideo(int i, int i2) {
        if (this.fActivity != null) {
            this.fActivity.showCameraWindowForVideo(i, i2);
        }
    }

    public void showSelectImageWindowUsing(String str) {
        if (this.fActivity != null) {
            this.fActivity.showSelectImageWindowUsing(str);
        }
    }

    public void showSelectVideoWindow() {
        if (this.fActivity != null) {
            this.fActivity.showSelectVideoWindow();
        }
    }

    public void showSendMailWindowUsing(MailSettings mailSettings) {
        if (this.fActivity != null) {
            this.fActivity.showSendMailWindowUsing(mailSettings);
        }
    }

    public void showSendSmsWindowUsing(SmsSettings smsSettings) {
        if (this.fActivity != null) {
            this.fActivity.showSendSmsWindowUsing(smsSettings);
        }
    }
}
