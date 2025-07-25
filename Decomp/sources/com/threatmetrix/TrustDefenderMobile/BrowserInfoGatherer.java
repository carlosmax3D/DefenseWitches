package com.threatmetrix.TrustDefenderMobile;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import jp.stargarage.g2metrics.BuildConfig;

class BrowserInfoGatherer extends BrowserInfo {
    /* access modifiers changed from: private */
    public static final String TAG = BrowserInfoGatherer.class.getSimpleName();
    private CountDownLatch m_browser_info_latch = null;
    /* access modifiers changed from: private */
    public Context m_context = null;
    /* access modifiers changed from: private */
    public JSExecutor m_jsExec = null;
    /* access modifiers changed from: private */
    public JavaScriptInterface m_jsInterface = null;
    private boolean m_jsProblem = false;
    /* access modifiers changed from: private */
    public boolean m_needWebView = false;
    private int m_options = 0;

    static class CompleteBrowserInfoRequest implements Runnable {
        BrowserInfoGatherer m_info = null;
        CountDownLatch m_latch = null;

        public CompleteBrowserInfoRequest(BrowserInfoGatherer browserInfoGatherer, CountDownLatch countDownLatch) {
            this.m_info = browserInfoGatherer;
            this.m_latch = countDownLatch;
        }

        public void run() {
            throw new NoSuchMethodError();
        }
    }

    BrowserInfoGatherer() {
    }

    static /* synthetic */ void access$500(BrowserInfoGatherer browserInfoGatherer) throws InterruptedException {
        String jSResult;
        if (!Thread.currentThread().isInterrupted()) {
            if (!((browserInfoGatherer.m_options & 32) == 0 || (jSResult = browserInfoGatherer.m_jsExec.getJSResult("(function () { var plugins_string='', i=0; for (p=navigator.plugins[0]; i< navigator.plugins.length;p=navigator.plugins[i++]) {  plugins_string += p.name + '<FIELD_SEP>' + p.description + '<FIELD_SEP>' + p.filename + '<FIELD_SEP>' + p.length.toString() + '<REC_SEP>'; } return plugins_string;})();")) == null)) {
                browserInfoGatherer.parseBrowserInfo_Plugins(jSResult);
            }
            if (!Thread.currentThread().isInterrupted() && (browserInfoGatherer.m_options & 4) != 0) {
                String jSResult2 = browserInfoGatherer.m_jsExec.getJSResult("navigator.mimeTypes.length");
                if (jSResult2 != null) {
                    try {
                        browserInfoGatherer.m_mimeTypeCount = Integer.parseInt(jSResult2);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "failed to convert", e);
                    }
                }
                browserInfoGatherer.m_mimeTypes = browserInfoGatherer.m_jsExec.getJSResult("(function () { var mime_string='', i=0; for (var m=navigator.mimeTypes[0]; i< navigator.mimeTypes.length;m=navigator.mimeTypes[i++]) {  mime_string += m.type; } return mime_string;})();");
                if (browserInfoGatherer.m_mimeTypes != null) {
                    browserInfoGatherer.m_mimeTypesHash = StringUtils.MD5(browserInfoGatherer.m_mimeTypes);
                    String str = TAG;
                    new StringBuilder("Got:").append(browserInfoGatherer.m_mimeTypes);
                    return;
                }
                browserInfoGatherer.m_mimeTypesHash = BuildConfig.FLAVOR;
            }
        }
    }

    private static HashMap<String, String> checkPlugin(ArrayList<HashMap<String, String>> arrayList, String str) {
        Iterator<HashMap<String, String>> it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap<String, String> next = it.next();
            String str2 = next.get("name");
            if (str2 != null && str2.toLowerCase(Locale.US).contains(str.toLowerCase(Locale.US))) {
                return next;
            }
        }
        return null;
    }

    private static String containsPluginNamed(String str, String str2, ArrayList<HashMap<String, String>> arrayList) {
        HashMap hashMap;
        String str3 = "false";
        Iterator<HashMap<String, String>> it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                hashMap = null;
                break;
            }
            HashMap next = it.next();
            String str4 = (String) next.get("name");
            if (str4 != null && str4.toLowerCase(Locale.US).contains(str.toLowerCase(Locale.US))) {
                hashMap = next;
                break;
            }
        }
        if (hashMap != null) {
            str3 = ((String) hashMap.get("name")).replace("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXY -]", BuildConfig.FLAVOR);
            if (!str3.isEmpty()) {
                str3 = "true";
            }
        }
        return str2 + "^" + str3 + "!";
    }

    private void getBrowserInfo() throws InterruptedException {
        String jSResult;
        if (!Thread.currentThread().isInterrupted()) {
            if (!((this.m_options & 32) == 0 || (jSResult = this.m_jsExec.getJSResult("(function () { var plugins_string='', i=0; for (p=navigator.plugins[0]; i< navigator.plugins.length;p=navigator.plugins[i++]) {  plugins_string += p.name + '<FIELD_SEP>' + p.description + '<FIELD_SEP>' + p.filename + '<FIELD_SEP>' + p.length.toString() + '<REC_SEP>'; } return plugins_string;})();")) == null)) {
                parseBrowserInfo_Plugins(jSResult);
            }
            if (!Thread.currentThread().isInterrupted() && (this.m_options & 4) != 0) {
                String jSResult2 = this.m_jsExec.getJSResult("navigator.mimeTypes.length");
                if (jSResult2 != null) {
                    try {
                        this.m_mimeTypeCount = Integer.parseInt(jSResult2);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "failed to convert", e);
                    }
                }
                this.m_mimeTypes = this.m_jsExec.getJSResult("(function () { var mime_string='', i=0; for (var m=navigator.mimeTypes[0]; i< navigator.mimeTypes.length;m=navigator.mimeTypes[i++]) {  mime_string += m.type; } return mime_string;})();");
                if (this.m_mimeTypes != null) {
                    this.m_mimeTypesHash = StringUtils.MD5(this.m_mimeTypes);
                    String str = TAG;
                    new StringBuilder("Got:").append(this.m_mimeTypes);
                    return;
                }
                this.m_mimeTypesHash = BuildConfig.FLAVOR;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseBrowserInfo() {
        /*
            r7 = this;
            r6 = 0
            r3 = 0
            int r4 = r7.m_options
            r4 = r4 & 32
            if (r4 == 0) goto L_0x0029
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r4 = r7.m_jsInterface
            java.util.ArrayList<java.lang.String> r4 = r4.returnedValues
            int r4 = r4.size()
            if (r4 <= 0) goto L_0x0029
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r4 = r7.m_jsInterface
            java.util.ArrayList<java.lang.String> r4 = r4.returnedValues
            java.lang.Object r1 = r4.get(r6)
            java.lang.String r1 = (java.lang.String) r1
            int r3 = r3 + 1
            if (r1 == 0) goto L_0x00b0
            boolean r4 = r1.isEmpty()
            if (r4 != 0) goto L_0x00b0
            r7.parseBrowserInfo_Plugins(r1)
        L_0x0029:
            java.lang.Thread r4 = java.lang.Thread.currentThread()
            boolean r4 = r4.isInterrupted()
            if (r4 != 0) goto L_0x00af
            int r4 = r7.m_options
            r4 = r4 & 4
            if (r4 == 0) goto L_0x00af
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r4 = r7.m_jsInterface
            java.util.ArrayList<java.lang.String> r4 = r4.returnedValues
            int r4 = r4.size()
            if (r4 <= r3) goto L_0x00af
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r4 = r7.m_jsInterface
            java.util.ArrayList<java.lang.String> r4 = r4.returnedValues
            java.lang.Object r2 = r4.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            int r3 = r3 + 1
            if (r2 == 0) goto L_0x00be
            boolean r4 = r2.isEmpty()
            if (r4 != 0) goto L_0x00be
            int r4 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x00b6 }
            r7.m_mimeTypeCount = r4     // Catch:{ NumberFormatException -> 0x00b6 }
        L_0x005d:
            int r4 = r7.m_mimeTypeCount
            if (r4 <= 0) goto L_0x0077
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r4 = r7.m_jsInterface
            java.util.ArrayList<java.lang.String> r4 = r4.returnedValues
            int r4 = r4.size()
            if (r4 <= r3) goto L_0x0077
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r4 = r7.m_jsInterface
            java.util.ArrayList<java.lang.String> r4 = r4.returnedValues
            java.lang.Object r4 = r4.get(r3)
            java.lang.String r4 = (java.lang.String) r4
            r7.m_mimeTypes = r4
        L_0x0077:
            java.lang.String r4 = r7.m_mimeTypes
            if (r4 == 0) goto L_0x00c1
            java.lang.String r4 = r7.m_mimeTypes
            java.lang.String r4 = com.threatmetrix.TrustDefenderMobile.StringUtils.MD5(r4)
            r7.m_mimeTypesHash = r4
            java.lang.String r4 = TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Got:"
            r4.<init>(r5)
            java.lang.String r5 = r7.m_mimeTypes
            r4.append(r5)
        L_0x0091:
            java.lang.String r4 = TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Got mime "
            r4.<init>(r5)
            int r5 = r7.m_mimeTypeCount
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ":"
            java.lang.StringBuilder r5 = r4.append(r5)
            java.lang.String r4 = r7.m_mimeTypes
            if (r4 == 0) goto L_0x00c6
            java.lang.String r4 = r7.m_mimeTypes
        L_0x00ac:
            r5.append(r4)
        L_0x00af:
            return
        L_0x00b0:
            java.lang.String r4 = ""
            r7.m_browserPluginsFromJSHash = r4
            goto L_0x0029
        L_0x00b6:
            r0 = move-exception
            java.lang.String r4 = TAG
            java.lang.String r5 = "failed to convert"
            android.util.Log.e(r4, r5, r0)
        L_0x00be:
            r7.m_mimeTypeCount = r6
            goto L_0x005d
        L_0x00c1:
            java.lang.String r4 = ""
            r7.m_mimeTypesHash = r4
            goto L_0x0091
        L_0x00c6:
            java.lang.String r4 = ""
            goto L_0x00ac
        */
        throw new UnsupportedOperationException("Method not decompiled: com.threatmetrix.TrustDefenderMobile.BrowserInfoGatherer.parseBrowserInfo():void");
    }

    private void parseBrowserInfo_Plugins(String str) {
        this.m_browserPluginsFromJS = str.replaceAll("(<FIELD_SEP>|<REC_SEP>)", BuildConfig.FLAVOR);
        this.m_browserPluginsFromJSHash = StringUtils.MD5(this.m_browserPluginsFromJS);
        ArrayList arrayList = new ArrayList();
        String[] split = str.split("<REC_SEP>");
        int length = split.length;
        int i = 0;
        while (i < length) {
            String str2 = split[i];
            if (!Thread.currentThread().isInterrupted()) {
                HashMap hashMap = new HashMap();
                String[] split2 = str2.split("<FIELD_SEP>");
                if (split2.length == 4) {
                    hashMap.put("name", split2[0]);
                    hashMap.put("description", split2[1]);
                    hashMap.put("filename", split2[2]);
                    hashMap.put("length", split2[3]);
                    arrayList.add(hashMap);
                }
                i++;
            } else {
                return;
            }
        }
        this.m_browserPluginCount = Integer.toString(arrayList.size());
        this.m_browserPlugins = containsPluginNamed("QuickTime Plug-in", "plugin_quicktime", arrayList) + containsPluginNamed("Adobe Acrobat", "plugin_adobe_acrobat", arrayList) + containsPluginNamed("Java", "plugin_java", arrayList) + containsPluginNamed("SVG Viewer", "plugin_svg_viewer", arrayList) + containsPluginNamed("Flash", "plugin_flash", arrayList) + containsPluginNamed("Windows Media Player", "plugin_windows_media_player", arrayList) + containsPluginNamed("Silverlight", "plugin_silverlight", arrayList) + containsPluginNamed("Real Player", "plugin_realplayer", arrayList) + containsPluginNamed("ShockWave Director", "plugin_shockwave", arrayList) + containsPluginNamed("VLC", "plugin_vlc_player", arrayList) + containsPluginNamed("DevalVR", "plugin_devalvr", arrayList);
        String str3 = TAG;
        new StringBuilder("Got").append(this.m_browserPluginCount).append(":").append(this.m_browserPlugins != null ? this.m_browserPlugins : BuildConfig.FLAVOR);
    }

    private boolean webViewOkay() {
        return this.m_jsExec != null && !this.m_jsProblem;
    }

    /* access modifiers changed from: package-private */
    public final void getBrowserInfoHelper() {
        boolean z = JSExecutor.isBrokenJSInterface() || JSExecutor.hasAsyncInterface();
        int i = 1;
        if (z) {
            if ((this.m_options & 32) != 0) {
                i = 1 + 1;
            }
            if ((this.m_options & 4) != 0) {
                i += 2;
            }
        }
        this.m_browser_info_latch = new CountDownLatch(i);
        Handler handler = new Handler(Looper.getMainLooper());
        String str = TAG;
        new StringBuilder("Firing off getBrowserInfo on UI thread using latch: ").append(this.m_browser_info_latch.hashCode()).append(" with count: ").append(i);
        this.m_jsInterface.setLatch(z ? this.m_browser_info_latch : null);
        handler.post(new CompleteBrowserInfoRequest(this, this.m_browser_info_latch) {
            public final void run() {
                try {
                    String unused = BrowserInfoGatherer.TAG;
                    BrowserInfoGatherer.access$500(this.m_info);
                } catch (InterruptedException e) {
                    String unused2 = BrowserInfoGatherer.TAG;
                }
                if (this.m_latch != null) {
                    String unused3 = BrowserInfoGatherer.TAG;
                    new StringBuilder("getBrowserInfo countdown using latch: ").append(this.m_latch.hashCode()).append(" with count: ").append(this.m_latch.getCount());
                    this.m_latch.countDown();
                }
            }
        });
    }

    public final String getBrowserStringFromJS() {
        if (this.m_browserPluginsFromJS == null) {
            if (this.m_jsExec == null || this.m_jsProblem) {
                this.m_browserStringFromJS = JSExecutor.getUserAgentString();
            } else {
                this.m_browserStringFromJS = this.m_jsExec.getUserAgentString(this.m_context);
            }
        }
        return this.m_browserStringFromJS;
    }

    /* access modifiers changed from: package-private */
    public final boolean initJSExecutor(Context context, boolean z, int i) {
        this.m_context = context;
        this.m_needWebView = z;
        this.m_options = i;
        if (!this.m_needWebView) {
            return false;
        }
        String str = TAG;
        new StringBuilder("initJSExecutor: jsProblem = ").append(this.m_jsProblem).append(", jsExec = ").append(this.m_jsExec).append(", hasBadOptions = ").append(this.m_jsExec != null ? Boolean.valueOf(this.m_jsExec.hasBadOptions(z)) : "true");
        if ((this.m_jsProblem || this.m_jsExec != null) && (this.m_jsExec == null || !this.m_jsExec.hasBadOptions(this.m_needWebView))) {
            String str2 = TAG;
        } else {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Handler handler = new Handler(Looper.getMainLooper());
            this.m_jsInterface = new JavaScriptInterface(JSExecutor.isBrokenJSInterface() || JSExecutor.hasAsyncInterface() ? countDownLatch : null);
            String str3 = TAG;
            new StringBuilder("Firing off initJSExecutor on UI thread using latch: ").append(countDownLatch.hashCode()).append(" with count: ").append(countDownLatch.getCount());
            handler.post(new CompleteBrowserInfoRequest(this, countDownLatch) {
                public final void run() {
                    String unused = BrowserInfoGatherer.TAG;
                    JSExecutor unused2 = this.m_info.m_jsExec = new JSExecutor(BrowserInfoGatherer.this.m_context, BrowserInfoGatherer.this.m_jsInterface, BrowserInfoGatherer.this.m_needWebView);
                    try {
                        this.m_info.m_jsExec.init();
                    } catch (InterruptedException e) {
                        Log.e(BrowserInfoGatherer.TAG, "Interrupted initing js engine");
                    }
                    String unused3 = BrowserInfoGatherer.TAG;
                    if (this.m_latch != null) {
                        String unused4 = BrowserInfoGatherer.TAG;
                        new StringBuilder("js exec init countdown using latch: ").append(this.m_latch.hashCode()).append(" with count: ").append(this.m_latch.getCount());
                        this.m_latch.countDown();
                    }
                }
            });
            try {
                if (!countDownLatch.await(10, TimeUnit.SECONDS)) {
                    this.m_jsProblem = true;
                    Log.e(TAG, "initJSExecutor no response from UI thread before timeout using init latch: " + countDownLatch.hashCode() + " with count: " + countDownLatch.getCount());
                    return false;
                }
            } catch (InterruptedException e) {
                Log.e(TAG, "Interrupted initing js engine");
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final boolean shouldCallBrowserInfoHelper() {
        return (this.m_jsExec != null && !this.m_jsProblem) && this.m_needWebView;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b8 A[Catch:{ InterruptedException -> 0x00f3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00e7 A[Catch:{ InterruptedException -> 0x00f3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0108 A[Catch:{ InterruptedException -> 0x00f3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x010d A[Catch:{ InterruptedException -> 0x00f3 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void waitForBrowserInfoHelper() {
        /*
            r7 = this;
            r2 = 1
            r1 = 0
            java.util.concurrent.CountDownLatch r3 = r7.m_browser_info_latch
            if (r3 == 0) goto L_0x00ec
            java.lang.String r3 = TAG     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r4 = "waiting for getBrowserInfo to finished, latch: "
            r3.<init>(r4)     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.concurrent.CountDownLatch r4 = r7.m_browser_info_latch     // Catch:{ InterruptedException -> 0x00f3 }
            long r4 = r4.getCount()     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r4 = " - "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.concurrent.CountDownLatch r4 = r7.m_browser_info_latch     // Catch:{ InterruptedException -> 0x00f3 }
            int r4 = r4.hashCode()     // Catch:{ InterruptedException -> 0x00f3 }
            r3.append(r4)     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.concurrent.CountDownLatch r3 = r7.m_browser_info_latch     // Catch:{ InterruptedException -> 0x00f3 }
            r4 = 10
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x00f3 }
            boolean r3 = r3.await(r4, r6)     // Catch:{ InterruptedException -> 0x00f3 }
            if (r3 == 0) goto L_0x0110
            boolean r3 = com.threatmetrix.TrustDefenderMobile.JSExecutor.isBrokenJSInterface()     // Catch:{ InterruptedException -> 0x00f3 }
            if (r3 != 0) goto L_0x0040
            boolean r3 = com.threatmetrix.TrustDefenderMobile.JSExecutor.hasAsyncInterface()     // Catch:{ InterruptedException -> 0x00f3 }
            if (r3 == 0) goto L_0x00ec
        L_0x0040:
            int r3 = r7.m_options     // Catch:{ InterruptedException -> 0x00f3 }
            r3 = r3 & 32
            if (r3 == 0) goto L_0x013e
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r3 = r7.m_jsInterface     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.ArrayList<java.lang.String> r3 = r3.returnedValues     // Catch:{ InterruptedException -> 0x00f3 }
            int r3 = r3.size()     // Catch:{ InterruptedException -> 0x00f3 }
            if (r3 <= 0) goto L_0x013e
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r1 = r7.m_jsInterface     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.ArrayList<java.lang.String> r1 = r1.returnedValues     // Catch:{ InterruptedException -> 0x00f3 }
            r3 = 0
            java.lang.Object r1 = r1.get(r3)     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ InterruptedException -> 0x00f3 }
            if (r1 == 0) goto L_0x00ed
            boolean r3 = r1.isEmpty()     // Catch:{ InterruptedException -> 0x00f3 }
            if (r3 != 0) goto L_0x00ed
            r7.parseBrowserInfo_Plugins(r1)     // Catch:{ InterruptedException -> 0x00f3 }
        L_0x0066:
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ InterruptedException -> 0x00f3 }
            boolean r1 = r1.isInterrupted()     // Catch:{ InterruptedException -> 0x00f3 }
            if (r1 != 0) goto L_0x00ec
            int r1 = r7.m_options     // Catch:{ InterruptedException -> 0x00f3 }
            r1 = r1 & 4
            if (r1 == 0) goto L_0x00ec
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r1 = r7.m_jsInterface     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.ArrayList<java.lang.String> r1 = r1.returnedValues     // Catch:{ InterruptedException -> 0x00f3 }
            int r1 = r1.size()     // Catch:{ InterruptedException -> 0x00f3 }
            if (r1 <= r2) goto L_0x00ec
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r1 = r7.m_jsInterface     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.ArrayList<java.lang.String> r1 = r1.returnedValues     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ InterruptedException -> 0x00f3 }
            int r2 = r2 + 1
            if (r1 == 0) goto L_0x0104
            boolean r3 = r1.isEmpty()     // Catch:{ InterruptedException -> 0x00f3 }
            if (r3 != 0) goto L_0x0104
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x00fc }
            r7.m_mimeTypeCount = r1     // Catch:{ NumberFormatException -> 0x00fc }
        L_0x009a:
            int r1 = r7.m_mimeTypeCount     // Catch:{ InterruptedException -> 0x00f3 }
            if (r1 <= 0) goto L_0x00b4
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r1 = r7.m_jsInterface     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.ArrayList<java.lang.String> r1 = r1.returnedValues     // Catch:{ InterruptedException -> 0x00f3 }
            int r1 = r1.size()     // Catch:{ InterruptedException -> 0x00f3 }
            if (r1 <= r2) goto L_0x00b4
            com.threatmetrix.TrustDefenderMobile.JavaScriptInterface r1 = r7.m_jsInterface     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.ArrayList<java.lang.String> r1 = r1.returnedValues     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ InterruptedException -> 0x00f3 }
            r7.m_mimeTypes = r1     // Catch:{ InterruptedException -> 0x00f3 }
        L_0x00b4:
            java.lang.String r1 = r7.m_mimeTypes     // Catch:{ InterruptedException -> 0x00f3 }
            if (r1 == 0) goto L_0x0108
            java.lang.String r1 = r7.m_mimeTypes     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r1 = com.threatmetrix.TrustDefenderMobile.StringUtils.MD5(r1)     // Catch:{ InterruptedException -> 0x00f3 }
            r7.m_mimeTypesHash = r1     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r1 = TAG     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r2 = "Got:"
            r1.<init>(r2)     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r2 = r7.m_mimeTypes     // Catch:{ InterruptedException -> 0x00f3 }
            r1.append(r2)     // Catch:{ InterruptedException -> 0x00f3 }
        L_0x00ce:
            java.lang.String r1 = TAG     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r2 = "Got mime "
            r1.<init>(r2)     // Catch:{ InterruptedException -> 0x00f3 }
            int r2 = r7.m_mimeTypeCount     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r2 = ":"
            java.lang.StringBuilder r2 = r1.append(r2)     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r1 = r7.m_mimeTypes     // Catch:{ InterruptedException -> 0x00f3 }
            if (r1 == 0) goto L_0x010d
            java.lang.String r1 = r7.m_mimeTypes     // Catch:{ InterruptedException -> 0x00f3 }
        L_0x00e9:
            r2.append(r1)     // Catch:{ InterruptedException -> 0x00f3 }
        L_0x00ec:
            return
        L_0x00ed:
            java.lang.String r1 = ""
            r7.m_browserPluginsFromJSHash = r1     // Catch:{ InterruptedException -> 0x00f3 }
            goto L_0x0066
        L_0x00f3:
            r0 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r2 = "getBrowserInfo interrupted"
            android.util.Log.e(r1, r2, r0)
            goto L_0x00ec
        L_0x00fc:
            r1 = move-exception
            java.lang.String r3 = TAG     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r4 = "failed to convert"
            android.util.Log.e(r3, r4, r1)     // Catch:{ InterruptedException -> 0x00f3 }
        L_0x0104:
            r1 = 0
            r7.m_mimeTypeCount = r1     // Catch:{ InterruptedException -> 0x00f3 }
            goto L_0x009a
        L_0x0108:
            java.lang.String r1 = ""
            r7.m_mimeTypesHash = r1     // Catch:{ InterruptedException -> 0x00f3 }
            goto L_0x00ce
        L_0x010d:
            java.lang.String r1 = ""
            goto L_0x00e9
        L_0x0110:
            java.lang.String r1 = TAG     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r3 = "getBrowserInfo no response from UI thread before timeout using latch: "
            r2.<init>(r3)     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.concurrent.CountDownLatch r3 = r7.m_browser_info_latch     // Catch:{ InterruptedException -> 0x00f3 }
            int r3 = r3.hashCode()     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r3 = " with count: "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ InterruptedException -> 0x00f3 }
            java.util.concurrent.CountDownLatch r3 = r7.m_browser_info_latch     // Catch:{ InterruptedException -> 0x00f3 }
            long r4 = r3.getCount()     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ InterruptedException -> 0x00f3 }
            java.lang.String r2 = r2.toString()     // Catch:{ InterruptedException -> 0x00f3 }
            android.util.Log.e(r1, r2)     // Catch:{ InterruptedException -> 0x00f3 }
            r1 = 1
            r7.m_jsProblem = r1     // Catch:{ InterruptedException -> 0x00f3 }
            goto L_0x00ec
        L_0x013e:
            r2 = r1
            goto L_0x0066
        */
        throw new UnsupportedOperationException("Method not decompiled: com.threatmetrix.TrustDefenderMobile.BrowserInfoGatherer.waitForBrowserInfoHelper():void");
    }
}
