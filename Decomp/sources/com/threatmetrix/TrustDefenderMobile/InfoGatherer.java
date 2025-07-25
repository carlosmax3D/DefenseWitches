package com.threatmetrix.TrustDefenderMobile;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import com.facebook.internal.AnalyticsEvents;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import jp.stargarage.g2metrics.BuildConfig;

class InfoGatherer {
    private static final String TAG = InfoGatherer.class.getSimpleName();

    static class TZInfo {
        int dstDiff = 0;
        int gmtOffset = 0;

        TZInfo() {
        }
    }

    InfoGatherer() {
    }

    static List<String> checkURLs(Context context, List<String> list) throws InterruptedException {
        String host;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (list != null && !list.isEmpty()) {
            String[] checkURLs = NativeGatherer.INSTANCE.checkURLs((String[]) list.toArray(new String[list.size()]));
            String str = Build.TAGS;
            PackageManager packageManager = context.getPackageManager();
            for (String next : list) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                boolean z = false;
                URI uri = null;
                try {
                    uri = new URI(next);
                } catch (URISyntaxException e) {
                    String str2 = TAG;
                }
                if (uri != null) {
                    if (uri.getScheme().equals("file")) {
                        z = new File(uri).exists();
                    } else if (uri.getScheme().equals("tags")) {
                        if (!(str == null || (host = uri.getHost()) == null || host.isEmpty())) {
                            z = str.contains(host);
                        }
                    } else if (uri.getScheme().equals("pkg") && packageManager != null) {
                        try {
                            packageManager.getPackageInfo(uri.getHost(), 1);
                            z = true;
                        } catch (PackageManager.NameNotFoundException e2) {
                        }
                    }
                    if (z) {
                        arrayList.add(next);
                    } else {
                        i++;
                    }
                }
            }
            String str3 = TAG;
            new StringBuilder("matched ").append(i).append("/").append(list.size());
            if (checkURLs != null && checkURLs.length > 0) {
                String[] strArr = checkURLs;
                int length = checkURLs.length;
                for (int i2 = 0; i2 < length; i2++) {
                    arrayList.add("z" + strArr[i2]);
                }
            }
            Collections.sort(arrayList);
            if (!arrayList.isEmpty() && Log.isLoggable(TAG, 3)) {
                String str4 = TAG;
                new StringBuilder("found ").append(StringUtils.ListToSeperatedString(arrayList, ";"));
            }
        }
        return arrayList;
    }

    private static String getCPUInfo() {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, new String[]{"Processor", "BogoMips", "Hardware", "Serial"});
        String infoFromFile = getInfoFromFile("/proc/cpuinfo", arrayList, ":");
        String str = TAG;
        new StringBuilder("getCPUInfo returned: ").append(infoFromFile);
        return infoFromFile;
    }

    static String getDeviceFingerprint(Context context, Context context2) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        if (Thread.currentThread().isInterrupted()) {
            return BuildConfig.FLAVOR;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context2.getSystemService("phone");
        String str2 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        if (telephonyManager.getPhoneType() == 1) {
            str2 = telephonyManager.getNetworkOperatorName();
        }
        sb.append(str2);
        sb.append(telephonyManager.getSimCountryIso());
        StatWrapper statWrapper = new StatWrapper(Environment.getDataDirectory().getPath());
        sb.append((((((float) statWrapper.getBlockCount()) * ((float) statWrapper.getBlockSize())) / 1024.0f) / 1024.0f) / 1024.0f);
        DisplayWrapper displayWrapper = new DisplayWrapper(((WindowManager) context.getSystemService("window")).getDefaultDisplay());
        sb.append(displayWrapper.getWidth()).append("x").append(displayWrapper.getHeight());
        String str3 = TAG;
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, new String[]{"Processor", "BogoMips", "Hardware", "Serial"});
        String infoFromFile = getInfoFromFile("/proc/cpuinfo", arrayList, ":");
        String str4 = TAG;
        new StringBuilder("getCPUInfo returned: ").append(infoFromFile);
        sb.append(infoFromFile);
        String str5 = TAG;
        ArrayList arrayList2 = new ArrayList();
        Collections.addAll(arrayList2, new String[]{"MemTotal"});
        String infoFromFile2 = getInfoFromFile("/proc/meminfo", arrayList2, ":");
        String str6 = TAG;
        new StringBuilder("getMemInfo returned: ").append(infoFromFile2);
        sb.append(infoFromFile2);
        sb.append(Build.DEVICE).append(" ").append(Build.MODEL).append(" ").append(Build.PRODUCT).append(" ").append(Build.MANUFACTURER).append(" ").append(Build.VERSION.RELEASE);
        String str7 = TAG;
        new StringBuilder("getDeviceFingerprint returned: hash(").append(sb.toString()).append(")");
        return StringUtils.MD5(sb.toString());
    }

    static String getDeviceState() {
        StringBuilder sb = new StringBuilder();
        StatWrapper statWrapper = new StatWrapper(Environment.getDataDirectory().getPath());
        long blockSize = statWrapper.getBlockSize();
        long availableBlocks = statWrapper.getAvailableBlocks();
        float f = 1.0f;
        if (availableBlocks * blockSize != 0) {
            f = (((((float) availableBlocks) * ((float) blockSize)) / 1024.0f) / 1024.0f) / 1024.0f;
        }
        sb.append(f);
        sb.append(" - ");
        sb.append(Long.toString((System.currentTimeMillis() - SystemClock.elapsedRealtime()) / 1000));
        String str = TAG;
        new StringBuilder("getDeviceState: ").append(StringUtils.MD5(sb.toString()));
        return StringUtils.MD5(sb.toString());
    }

    static String getFontHashAndCount(StringBuilder sb) {
        if (NativeGatherer.INSTANCE.isAvailable()) {
            List<String> fontList = NativeGatherer.INSTANCE.getFontList("/system/fonts");
            if (fontList == null || fontList.isEmpty() || fontList.size() != 2) {
                return null;
            }
            String str = fontList.get(0);
            sb.append(fontList.get(1));
            return str;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        String[] list = new File("/system/fonts/").list();
        if (list != null) {
            for (String str2 : list) {
                if (str2 != null && str2.endsWith(".ttf")) {
                    StringBuilder sb2 = new StringBuilder(str2);
                    arrayList.add(sb2.substring(0, sb2.length() - 4));
                }
            }
        }
        StringBuilder sb3 = new StringBuilder();
        for (String append : arrayList) {
            sb3.append(append);
        }
        sb.append(arrayList.size());
        return StringUtils.MD5(sb3.toString());
    }

    private static List<String> getFontList() {
        ArrayList arrayList = new ArrayList();
        String[] list = new File("/system/fonts/").list();
        if (list != null) {
            String[] strArr = list;
            int length = list.length;
            for (int i = 0; i < length; i++) {
                String str = strArr[i];
                if (str != null && str.endsWith(".ttf")) {
                    StringBuilder sb = new StringBuilder(str);
                    arrayList.add(sb.substring(0, sb.length() - 4));
                }
            }
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0082 A[SYNTHETIC, Splitter:B:34:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b8 A[SYNTHETIC, Splitter:B:51:0x00b8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getInfoFromFile(java.lang.String r13, java.util.List<java.lang.String> r14, java.lang.String r15) {
        /*
            r12 = 1
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r0 = 0
            if (r13 == 0) goto L_0x0085
            if (r14 == 0) goto L_0x0085
            java.io.File r10 = new java.io.File
            r10.<init>(r13)
            boolean r10 = r10.exists()
            if (r10 == 0) goto L_0x0085
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ IOException -> 0x00db }
            java.io.FileReader r10 = new java.io.FileReader     // Catch:{ IOException -> 0x00db }
            java.io.File r11 = new java.io.File     // Catch:{ IOException -> 0x00db }
            r11.<init>(r13)     // Catch:{ IOException -> 0x00db }
            r10.<init>(r11)     // Catch:{ IOException -> 0x00db }
            r1.<init>(r10)     // Catch:{ IOException -> 0x00db }
        L_0x0025:
            java.lang.String r9 = r1.readLine()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r9 == 0) goto L_0x00c7
            java.lang.String r7 = ""
            if (r15 == 0) goto L_0x008a
            boolean r10 = r15.isEmpty()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 != 0) goto L_0x008a
            java.util.List r8 = com.threatmetrix.TrustDefenderMobile.StringUtils.splitNonRegex(r9, r15)     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            boolean r10 = r8.isEmpty()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 != 0) goto L_0x0025
            r10 = 0
            java.lang.Object r10 = r8.get(r10)     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            java.lang.String r6 = r10.trim()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            int r10 = r6.length()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 == 0) goto L_0x0025
            boolean r10 = r14.contains(r6)     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 == 0) goto L_0x0067
            int r10 = r8.size()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 <= r12) goto L_0x0067
            r10 = 1
            java.lang.Object r10 = r8.get(r10)     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            java.lang.String r7 = r10.trim()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
        L_0x0067:
            boolean r10 = r7.isEmpty()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 != 0) goto L_0x0025
            int r10 = r4.length()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 <= 0) goto L_0x0078
            java.lang.String r10 = ","
            r4.append(r10)     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
        L_0x0078:
            r4.append(r7)     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            goto L_0x0025
        L_0x007c:
            r10 = move-exception
            r0 = r1
        L_0x007e:
            java.lang.String r10 = TAG     // Catch:{ all -> 0x00d9 }
            if (r0 == 0) goto L_0x0085
            r0.close()     // Catch:{ IOException -> 0x00d1 }
        L_0x0085:
            java.lang.String r10 = r4.toString()
            return r10
        L_0x008a:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            r2.<init>()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            java.util.Iterator r3 = r14.iterator()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
        L_0x0093:
            boolean r10 = r3.hasNext()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 == 0) goto L_0x00bc
            java.lang.Object r5 = r3.next()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            boolean r10 = r9.contains(r5)     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 == 0) goto L_0x0093
            int r10 = r2.length()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 <= 0) goto L_0x00b0
            java.lang.String r10 = ","
            r2.append(r10)     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
        L_0x00b0:
            r2.append(r9)     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            goto L_0x0093
        L_0x00b4:
            r10 = move-exception
            r0 = r1
        L_0x00b6:
            if (r0 == 0) goto L_0x00bb
            r0.close()     // Catch:{ IOException -> 0x00d5 }
        L_0x00bb:
            throw r10
        L_0x00bc:
            int r10 = r2.length()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            if (r10 == 0) goto L_0x0067
            java.lang.String r7 = r2.toString()     // Catch:{ IOException -> 0x007c, all -> 0x00b4 }
            goto L_0x0067
        L_0x00c7:
            r1.close()     // Catch:{ IOException -> 0x00cc }
            r0 = r1
            goto L_0x0085
        L_0x00cc:
            r10 = move-exception
            java.lang.String r10 = TAG
            r0 = r1
            goto L_0x0085
        L_0x00d1:
            r10 = move-exception
            java.lang.String r10 = TAG
            goto L_0x0085
        L_0x00d5:
            r11 = move-exception
            java.lang.String r11 = TAG
            goto L_0x00bb
        L_0x00d9:
            r10 = move-exception
            goto L_0x00b6
        L_0x00db:
            r10 = move-exception
            goto L_0x007e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.threatmetrix.TrustDefenderMobile.InfoGatherer.getInfoFromFile(java.lang.String, java.util.List, java.lang.String):java.lang.String");
    }

    private static String getMemInfo() {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, new String[]{"MemTotal"});
        String infoFromFile = getInfoFromFile("/proc/meminfo", arrayList, ":");
        String str = TAG;
        new StringBuilder("getMemInfo returned: ").append(infoFromFile);
        return infoFromFile;
    }

    static boolean getTimeZoneInfo(TZInfo tZInfo) {
        if (tZInfo == null) {
            throw new IllegalArgumentException("tzInfo cannot be null");
        }
        TimeZone timeZone = TimeZone.getDefault();
        if (timeZone != null) {
            tZInfo.gmtOffset = timeZone.getRawOffset() / 60000;
            tZInfo.dstDiff = timeZone.getDSTSavings() / 60000;
            String str = TAG;
            new StringBuilder("getTimeZoneInfo: dstDiff=").append(tZInfo.dstDiff).append(" gmfOffset=").append(tZInfo.gmtOffset);
            return true;
        }
        Log.w(TAG, "getTimeZoneInfo: FAILED");
        return false;
    }

    static String getURLs(List<URI> list) throws InterruptedException {
        String obj;
        Map<String, String> splitQuery;
        boolean containsKey;
        StringBuilder sb = new StringBuilder();
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (URI next : list) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            } else if (next != null) {
                if (next.getScheme().equals("file")) {
                    File file = new File(next.getPath());
                    String query = next.getQuery();
                    if (query != null && !query.isEmpty()) {
                        if (file.exists() && ((containsKey = splitQuery.containsKey("grep")) || splitQuery.containsKey("keys"))) {
                            String str = (splitQuery = StringUtils.splitQuery(query)).get("sep");
                            if (!containsKey && (str == null || str.isEmpty())) {
                                str = ":";
                            }
                            String str2 = containsKey ? splitQuery.get("grep") : splitQuery.get("keys");
                            if (str2 == null || str2.isEmpty()) {
                                break;
                            }
                            String infoFromFile = getInfoFromFile(file.getAbsolutePath(), StringUtils.splitNonRegex(str2, ","), str);
                            if (infoFromFile != null && !infoFromFile.isEmpty()) {
                                if (sb.length() > 0) {
                                    sb.append(";");
                                }
                                sb.append(next.getPath()).append("=").append(infoFromFile);
                            }
                        }
                    } else {
                        if (sb.length() > 0) {
                            sb.append(";");
                        }
                        sb.append(next.getPath()).append("=").append(file.exists() ? "true" : "false");
                    }
                } else if (next.getScheme().equals("intro")) {
                    try {
                        String host = next.getHost();
                        String path = next.getPath();
                        if (host == null || host.isEmpty()) {
                            String str3 = TAG;
                        } else if (path == null || path.isEmpty()) {
                            String str4 = TAG;
                        } else {
                            if (path.startsWith("/")) {
                                path = path.substring(1);
                            }
                            Class cls = WrapperHelper.getClass(host);
                            if (cls == null) {
                                String str5 = TAG;
                                new StringBuilder("getURLs: failed to find class: ").append(host);
                            } else {
                                Method method = WrapperHelper.getMethod(cls, path, new Class[0]);
                                if (method != null) {
                                    Object invoke = WrapperHelper.invoke(cls, method, new Object[0]);
                                    if (!(invoke == null || (obj = invoke.toString()) == null)) {
                                        if (sb.length() > 0) {
                                            sb.append(";");
                                        }
                                        sb.append(next.getHost()).append(next.getPath()).append("=").append(obj);
                                    }
                                } else {
                                    Field field = WrapperHelper.getField(cls, path);
                                    if (field != null) {
                                        Object value = WrapperHelper.getValue(cls, field);
                                        if (value != null && (value instanceof String)) {
                                            if (sb.length() > 0) {
                                                sb.append(";");
                                            }
                                            sb.append(next.getHost()).append(next.getPath()).append("=").append((String) value);
                                        }
                                    } else {
                                        String str6 = TAG;
                                        new StringBuilder("getURLs: failed to find method or field: ").append(path);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        if (sb.length() > 0) {
            String str7 = TAG;
            new StringBuilder("found ").append(sb.toString());
        }
        return sb.toString();
    }
}
