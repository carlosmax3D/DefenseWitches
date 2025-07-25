package com.tapjoy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TapjoyCache {
    private static final int CACHE_CONNECT_TIMEOUT = 15000;
    public static final String CACHE_DIRECTORY_NAME = "Tapjoy/Cache/";
    public static final int CACHE_LIMIT = -1;
    private static final int CACHE_READ_TIMEOUT = 30000;
    private static final long DEFAULT_TIME_TO_LIVE = 86400;
    private static final int NUMBER_OF_THREDS = 5;
    private static final String TAG = "TapjoyCache";
    private static TapjoyCache _instance = null;
    public static boolean unit_test_mode = false;
    /* access modifiers changed from: private */
    public TapjoyCacheMap _cachedAssets;
    private Context _context;
    /* access modifiers changed from: private */
    public Vector<String> _currentlyDownloading;
    private int _eventPreloadCount = 0;
    private int _eventPreloadLimit = 3;
    /* access modifiers changed from: private */
    public File _tajoyCacheDir;
    /* access modifiers changed from: private */
    public boolean _verboseDebugging;
    private ExecutorService executor;

    public class CacheAssetThread implements Callable<Boolean> {
        private String _offerId;
        private long _timeToLive;
        private URL _url;

        public CacheAssetThread(URL url, String str, long j) {
            this._url = url;
            this._offerId = str;
            this._timeToLive = j;
            if (this._timeToLive <= 0) {
                this._timeToLive = TapjoyCache.DEFAULT_TIME_TO_LIVE;
            }
            TapjoyCache.this._currentlyDownloading.add(TapjoyCache.this.getHashFromURL(this._url.toString()));
        }

        /* JADX WARNING: Removed duplicated region for block: B:46:0x03b0 A[SYNTHETIC, Splitter:B:46:0x03b0] */
        /* JADX WARNING: Removed duplicated region for block: B:49:0x03b5 A[SYNTHETIC, Splitter:B:49:0x03b5] */
        /* JADX WARNING: Removed duplicated region for block: B:57:0x0406 A[SYNTHETIC, Splitter:B:57:0x0406] */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x040b A[SYNTHETIC, Splitter:B:60:0x040b] */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x0416 A[SYNTHETIC, Splitter:B:65:0x0416] */
        /* JADX WARNING: Removed duplicated region for block: B:68:0x041b A[SYNTHETIC, Splitter:B:68:0x041b] */
        /* JADX WARNING: Removed duplicated region for block: B:92:? A[RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:93:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Boolean call() throws java.lang.Exception {
            /*
                r24 = this;
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                r0 = r24
                java.net.URL r0 = r0._url
                r19 = r0
                java.lang.String r19 = r19.toString()
                java.lang.String r15 = r18.getHashFromURL(r19)
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                com.tapjoy.TapjoyCacheMap r18 = r18._cachedAssets
                r0 = r18
                boolean r18 = r0.containsKey(r15)
                if (r18 == 0) goto L_0x00f0
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                com.tapjoy.TapjoyCacheMap r18 = r18._cachedAssets
                r0 = r18
                java.lang.Object r5 = r0.get(r15)
                com.tapjoy.TapjoyCachedAssetData r5 = (com.tapjoy.TapjoyCachedAssetData) r5
                java.io.File r6 = new java.io.File
                java.lang.String r18 = r5.getLocalFilePath()
                r0 = r18
                r6.<init>(r0)
                boolean r18 = r6.exists()
                if (r18 == 0) goto L_0x00e7
                r0 = r24
                long r0 = r0._timeToLive
                r18 = r0
                r20 = 0
                int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
                if (r18 == 0) goto L_0x00ca
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                com.tapjoy.TapjoyCacheMap r18 = r18._cachedAssets
                r0 = r18
                java.lang.Object r18 = r0.get(r15)
                com.tapjoy.TapjoyCachedAssetData r18 = (com.tapjoy.TapjoyCachedAssetData) r18
                r0 = r24
                long r0 = r0._timeToLive
                r20 = r0
                r0 = r18
                r1 = r20
                r0.resetTimeToLive(r1)
            L_0x0074:
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                boolean r18 = r18._verboseDebugging
                if (r18 == 0) goto L_0x00a2
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "Reseting time to live for "
                java.lang.StringBuilder r19 = r19.append(r20)
                r0 = r24
                java.net.URL r0 = r0._url
                r20 = r0
                java.lang.String r20 = r20.toString()
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
            L_0x00a2:
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                java.util.Vector r18 = r18._currentlyDownloading
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r19 = r0
                r0 = r24
                java.net.URL r0 = r0._url
                r20 = r0
                java.lang.String r20 = r20.toString()
                java.lang.String r19 = r19.getHashFromURL(r20)
                r18.remove(r19)
                r18 = 1
                java.lang.Boolean r18 = java.lang.Boolean.valueOf(r18)
            L_0x00c9:
                return r18
            L_0x00ca:
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                com.tapjoy.TapjoyCacheMap r18 = r18._cachedAssets
                r0 = r18
                java.lang.Object r18 = r0.get(r15)
                com.tapjoy.TapjoyCachedAssetData r18 = (com.tapjoy.TapjoyCachedAssetData) r18
                r20 = 86400(0x15180, double:4.26873E-319)
                r0 = r18
                r1 = r20
                r0.resetTimeToLive(r1)
                goto L_0x0074
            L_0x00e7:
                com.tapjoy.TapjoyCache r18 = com.tapjoy.TapjoyCache.getInstance()
                r0 = r18
                r0.removeAssetFromCache(r15)
            L_0x00f0:
                long r18 = java.lang.System.currentTimeMillis()
                r20 = 1000(0x3e8, double:4.94E-321)
                long r16 = r18 / r20
                r10 = 0
                r13 = 0
                java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x033d }
                java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x033d }
                r18.<init>()     // Catch:{ Exception -> 0x033d }
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this     // Catch:{ Exception -> 0x033d }
                r19 = r0
                java.io.File r19 = r19._tajoyCacheDir     // Catch:{ Exception -> 0x033d }
                java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x033d }
                java.lang.String r19 = "/"
                java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x033d }
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this     // Catch:{ Exception -> 0x033d }
                r19 = r0
                r0 = r24
                java.net.URL r0 = r0._url     // Catch:{ Exception -> 0x033d }
                r20 = r0
                java.lang.String r20 = r20.toString()     // Catch:{ Exception -> 0x033d }
                java.lang.String r19 = r19.getHashFromURL(r20)     // Catch:{ Exception -> 0x033d }
                java.lang.String r19 = com.tapjoy.TapjoyUtil.SHA256(r19)     // Catch:{ Exception -> 0x033d }
                java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x033d }
                java.lang.String r18 = r18.toString()     // Catch:{ Exception -> 0x033d }
                r0 = r18
                r4.<init>(r0)     // Catch:{ Exception -> 0x033d }
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                boolean r18 = r18._verboseDebugging
                if (r18 == 0) goto L_0x0170
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "Downloading and caching asset from: "
                java.lang.StringBuilder r19 = r19.append(r20)
                r0 = r24
                java.net.URL r0 = r0._url
                r20 = r0
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r20 = " to "
                java.lang.StringBuilder r19 = r19.append(r20)
                r0 = r19
                java.lang.StringBuilder r19 = r0.append(r4)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
            L_0x0170:
                r0 = r24
                java.net.URL r0 = r0._url     // Catch:{ SocketTimeoutException -> 0x0367, Exception -> 0x03bd }
                r18 = r0
                java.net.URLConnection r7 = r18.openConnection()     // Catch:{ SocketTimeoutException -> 0x0367, Exception -> 0x03bd }
                r18 = 15000(0x3a98, float:2.102E-41)
                r0 = r18
                r7.setConnectTimeout(r0)     // Catch:{ SocketTimeoutException -> 0x0367, Exception -> 0x03bd }
                r18 = 30000(0x7530, float:4.2039E-41)
                r0 = r18
                r7.setReadTimeout(r0)     // Catch:{ SocketTimeoutException -> 0x0367, Exception -> 0x03bd }
                r7.connect()     // Catch:{ SocketTimeoutException -> 0x0367, Exception -> 0x03bd }
                java.io.BufferedInputStream r11 = new java.io.BufferedInputStream     // Catch:{ SocketTimeoutException -> 0x0367, Exception -> 0x03bd }
                java.io.InputStream r18 = r7.getInputStream()     // Catch:{ SocketTimeoutException -> 0x0367, Exception -> 0x03bd }
                r0 = r18
                r11.<init>(r0)     // Catch:{ SocketTimeoutException -> 0x0367, Exception -> 0x03bd }
                java.io.BufferedOutputStream r14 = new java.io.BufferedOutputStream     // Catch:{ SocketTimeoutException -> 0x043b, Exception -> 0x0434, all -> 0x042d }
                java.io.FileOutputStream r18 = new java.io.FileOutputStream     // Catch:{ SocketTimeoutException -> 0x043b, Exception -> 0x0434, all -> 0x042d }
                r0 = r18
                r0.<init>(r4)     // Catch:{ SocketTimeoutException -> 0x043b, Exception -> 0x0434, all -> 0x042d }
                r0 = r18
                r14.<init>(r0)     // Catch:{ SocketTimeoutException -> 0x043b, Exception -> 0x0434, all -> 0x042d }
                com.tapjoy.TapjoyUtil.writeFileToDevice(r11, r14)     // Catch:{ SocketTimeoutException -> 0x043f, Exception -> 0x0437, all -> 0x0430 }
                if (r11 == 0) goto L_0x01ac
                r11.close()     // Catch:{ IOException -> 0x041f }
            L_0x01ac:
                if (r14 == 0) goto L_0x01b1
                r14.close()     // Catch:{ IOException -> 0x0422 }
            L_0x01b1:
                com.tapjoy.TapjoyCachedAssetData r12 = new com.tapjoy.TapjoyCachedAssetData
                r0 = r24
                java.net.URL r0 = r0._url
                r18 = r0
                java.lang.String r18 = r18.toString()
                java.lang.String r19 = r4.getAbsolutePath()
                r0 = r24
                long r0 = r0._timeToLive
                r20 = r0
                r0 = r18
                r1 = r19
                r2 = r20
                r12.<init>(r0, r1, r2)
                r0 = r24
                java.lang.String r0 = r0._offerId
                r18 = r0
                if (r18 == 0) goto L_0x01e3
                r0 = r24
                java.lang.String r0 = r0._offerId
                r18 = r0
                r0 = r18
                r12.setOfferID(r0)
            L_0x01e3:
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                com.tapjoy.TapjoyCacheMap r18 = r18._cachedAssets
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r19 = r0
                r0 = r24
                java.net.URL r0 = r0._url
                r20 = r0
                java.lang.String r20 = r20.toString()
                java.lang.String r19 = r19.getHashFromURL(r20)
                r0 = r18
                r1 = r19
                r0.put((java.lang.String) r1, (com.tapjoy.TapjoyCachedAssetData) r12)
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                boolean r18 = r18._verboseDebugging
                if (r18 == 0) goto L_0x0314
                java.lang.String r18 = "TapjoyCache"
                java.lang.String r19 = "------------ Asset Cached ------------"
                com.tapjoy.TapjoyLog.i(r18, r19)
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "URL: "
                java.lang.StringBuilder r19 = r19.append(r20)
                r0 = r24
                java.net.URL r0 = r0._url
                r20 = r0
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "File location: "
                java.lang.StringBuilder r19 = r19.append(r20)
                r0 = r19
                java.lang.StringBuilder r19 = r0.append(r4)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "File size: "
                java.lang.StringBuilder r19 = r19.append(r20)
                long r20 = r4.length()
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "Downloaded from: "
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r20 = r12.getAssetURL()
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "Cached in: "
                java.lang.StringBuilder r19 = r19.append(r20)
                long r20 = java.lang.System.currentTimeMillis()
                r22 = 1000(0x3e8, double:4.94E-321)
                long r20 = r20 / r22
                long r20 = r20 - r16
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r20 = " seconds"
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "Timestamp: "
                java.lang.StringBuilder r19 = r19.append(r20)
                long r20 = r12.getTimestampInSeconds()
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "Time of death: "
                java.lang.StringBuilder r19 = r19.append(r20)
                long r20 = r12.getTimeOfDeathInSeconds()
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder
                r19.<init>()
                java.lang.String r20 = "Time to live: "
                java.lang.StringBuilder r19 = r19.append(r20)
                long r20 = r12.getTimeToLiveInSeconds()
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r20 = " seconds"
                java.lang.StringBuilder r19 = r19.append(r20)
                java.lang.String r19 = r19.toString()
                com.tapjoy.TapjoyLog.i(r18, r19)
                java.lang.String r18 = "TapjoyCache"
                java.lang.String r19 = "--------------------------------------"
                com.tapjoy.TapjoyLog.i(r18, r19)
            L_0x0314:
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                java.util.Vector r18 = r18._currentlyDownloading
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r19 = r0
                r0 = r24
                java.net.URL r0 = r0._url
                r20 = r0
                java.lang.String r20 = r20.toString()
                java.lang.String r19 = r19.getHashFromURL(r20)
                r18.remove(r19)
                r18 = 1
                java.lang.Boolean r18 = java.lang.Boolean.valueOf(r18)
                goto L_0x00c9
            L_0x033d:
                r9 = move-exception
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r18 = r0
                java.util.Vector r18 = r18._currentlyDownloading
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this
                r19 = r0
                r0 = r24
                java.net.URL r0 = r0._url
                r20 = r0
                java.lang.String r20 = r20.toString()
                java.lang.String r19 = r19.getHashFromURL(r20)
                r18.remove(r19)
                r18 = 0
                java.lang.Boolean r18 = java.lang.Boolean.valueOf(r18)
                goto L_0x00c9
            L_0x0367:
                r8 = move-exception
            L_0x0368:
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ all -> 0x0413 }
                r19.<init>()     // Catch:{ all -> 0x0413 }
                java.lang.String r20 = "Network timeout during caching: "
                java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ all -> 0x0413 }
                java.lang.String r20 = r8.toString()     // Catch:{ all -> 0x0413 }
                java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ all -> 0x0413 }
                java.lang.String r19 = r19.toString()     // Catch:{ all -> 0x0413 }
                com.tapjoy.TapjoyLog.e(r18, r19)     // Catch:{ all -> 0x0413 }
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this     // Catch:{ all -> 0x0413 }
                r18 = r0
                java.util.Vector r18 = r18._currentlyDownloading     // Catch:{ all -> 0x0413 }
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this     // Catch:{ all -> 0x0413 }
                r19 = r0
                r0 = r24
                java.net.URL r0 = r0._url     // Catch:{ all -> 0x0413 }
                r20 = r0
                java.lang.String r20 = r20.toString()     // Catch:{ all -> 0x0413 }
                java.lang.String r19 = r19.getHashFromURL(r20)     // Catch:{ all -> 0x0413 }
                r18.remove(r19)     // Catch:{ all -> 0x0413 }
                com.tapjoy.TapjoyUtil.deleteFileOrDirectory(r4)     // Catch:{ all -> 0x0413 }
                r18 = 0
                java.lang.Boolean r18 = java.lang.Boolean.valueOf(r18)     // Catch:{ all -> 0x0413 }
                if (r10 == 0) goto L_0x03b3
                r10.close()     // Catch:{ IOException -> 0x0425 }
            L_0x03b3:
                if (r13 == 0) goto L_0x00c9
                r13.close()     // Catch:{ IOException -> 0x03ba }
                goto L_0x00c9
            L_0x03ba:
                r19 = move-exception
                goto L_0x00c9
            L_0x03bd:
                r8 = move-exception
            L_0x03be:
                java.lang.String r18 = "TapjoyCache"
                java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ all -> 0x0413 }
                r19.<init>()     // Catch:{ all -> 0x0413 }
                java.lang.String r20 = "Error caching asset: "
                java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ all -> 0x0413 }
                java.lang.String r20 = r8.toString()     // Catch:{ all -> 0x0413 }
                java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ all -> 0x0413 }
                java.lang.String r19 = r19.toString()     // Catch:{ all -> 0x0413 }
                com.tapjoy.TapjoyLog.e(r18, r19)     // Catch:{ all -> 0x0413 }
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this     // Catch:{ all -> 0x0413 }
                r18 = r0
                java.util.Vector r18 = r18._currentlyDownloading     // Catch:{ all -> 0x0413 }
                r0 = r24
                com.tapjoy.TapjoyCache r0 = com.tapjoy.TapjoyCache.this     // Catch:{ all -> 0x0413 }
                r19 = r0
                r0 = r24
                java.net.URL r0 = r0._url     // Catch:{ all -> 0x0413 }
                r20 = r0
                java.lang.String r20 = r20.toString()     // Catch:{ all -> 0x0413 }
                java.lang.String r19 = r19.getHashFromURL(r20)     // Catch:{ all -> 0x0413 }
                r18.remove(r19)     // Catch:{ all -> 0x0413 }
                com.tapjoy.TapjoyUtil.deleteFileOrDirectory(r4)     // Catch:{ all -> 0x0413 }
                r18 = 0
                java.lang.Boolean r18 = java.lang.Boolean.valueOf(r18)     // Catch:{ all -> 0x0413 }
                if (r10 == 0) goto L_0x0409
                r10.close()     // Catch:{ IOException -> 0x0427 }
            L_0x0409:
                if (r13 == 0) goto L_0x00c9
                r13.close()     // Catch:{ IOException -> 0x0410 }
                goto L_0x00c9
            L_0x0410:
                r19 = move-exception
                goto L_0x00c9
            L_0x0413:
                r18 = move-exception
            L_0x0414:
                if (r10 == 0) goto L_0x0419
                r10.close()     // Catch:{ IOException -> 0x0429 }
            L_0x0419:
                if (r13 == 0) goto L_0x041e
                r13.close()     // Catch:{ IOException -> 0x042b }
            L_0x041e:
                throw r18
            L_0x041f:
                r18 = move-exception
                goto L_0x01ac
            L_0x0422:
                r18 = move-exception
                goto L_0x01b1
            L_0x0425:
                r19 = move-exception
                goto L_0x03b3
            L_0x0427:
                r19 = move-exception
                goto L_0x0409
            L_0x0429:
                r19 = move-exception
                goto L_0x0419
            L_0x042b:
                r19 = move-exception
                goto L_0x041e
            L_0x042d:
                r18 = move-exception
                r10 = r11
                goto L_0x0414
            L_0x0430:
                r18 = move-exception
                r13 = r14
                r10 = r11
                goto L_0x0414
            L_0x0434:
                r8 = move-exception
                r10 = r11
                goto L_0x03be
            L_0x0437:
                r8 = move-exception
                r13 = r14
                r10 = r11
                goto L_0x03be
            L_0x043b:
                r8 = move-exception
                r10 = r11
                goto L_0x0368
            L_0x043f:
                r8 = move-exception
                r13 = r14
                r10 = r11
                goto L_0x0368
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tapjoy.TapjoyCache.CacheAssetThread.call():java.lang.Boolean");
        }
    }

    public TapjoyCache(Context context) {
        if (_instance == null || unit_test_mode) {
            _instance = this;
            this._context = context;
            this._cachedAssets = new TapjoyCacheMap(context, -1);
            this._currentlyDownloading = new Vector<>();
            this.executor = Executors.newFixedThreadPool(5);
            init();
        }
    }

    /* access modifiers changed from: private */
    public String getHashFromURL(String str) {
        if (str.startsWith("//")) {
            str = "http:" + str;
        }
        try {
            return new URL(str).getFile();
        } catch (MalformedURLException e) {
            return BuildConfig.FLAVOR;
        }
    }

    public static TapjoyCache getInstance() {
        return _instance;
    }

    private void init() {
        removeOldCacheDataFromDevice();
        this._tajoyCacheDir = new File(this._context.getFilesDir() + "/" + CACHE_DIRECTORY_NAME);
        if (!this._tajoyCacheDir.exists() && !this._tajoyCacheDir.mkdirs()) {
            TapjoyLog.e(TAG, "Error initalizing cache");
            _instance = null;
        }
        loadCachedAssets();
    }

    private void loadCachedAssets() {
        SharedPreferences sharedPreferences = this._context.getSharedPreferences(TapjoyConstants.PREF_TAPJOY_CACHE, 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (Map.Entry next : sharedPreferences.getAll().entrySet()) {
            File file = new File((String) next.getKey());
            if (!file.exists() || !file.isFile()) {
                edit.remove((String) next.getKey()).commit();
            } else {
                TapjoyCachedAssetData fromRawJSONString = TapjoyCachedAssetData.fromRawJSONString(next.getValue().toString());
                if (fromRawJSONString != null) {
                    String hashFromURL = getHashFromURL(fromRawJSONString.getAssetURL());
                    if (hashFromURL == null || BuildConfig.FLAVOR.equals(hashFromURL) || hashFromURL.length() <= 0) {
                        edit.remove((String) next.getKey()).commit();
                    } else if (fromRawJSONString.getTimeOfDeathInSeconds() < System.currentTimeMillis() / 1000) {
                        if (this._verboseDebugging) {
                            TapjoyLog.i(TAG, "Asset expired, removing from cache");
                        }
                        if (fromRawJSONString.getLocalFilePath() != null && fromRawJSONString.getLocalFilePath().length() > 0) {
                            TapjoyUtil.deleteFileOrDirectory(new File(fromRawJSONString.getLocalFilePath()));
                        }
                    } else {
                        this._cachedAssets.put(hashFromURL, fromRawJSONString);
                    }
                } else {
                    edit.remove((String) next.getKey()).commit();
                }
            }
        }
    }

    private void removeOldCacheDataFromDevice() {
        if (Environment.getExternalStorageDirectory() != null) {
            TapjoyUtil.deleteFileOrDirectory(new File(Environment.getExternalStorageDirectory(), "tapjoy"));
            TapjoyUtil.deleteFileOrDirectory(new File(Environment.getExternalStorageDirectory(), "tjcache/tmp/"));
        }
    }

    public static void setInstance(TapjoyCache tapjoyCache) {
        _instance = tapjoyCache;
    }

    public Future<Boolean> cacheAssetFromJSONObject(JSONObject jSONObject) {
        try {
            String string = jSONObject.getString("url");
            Long valueOf = Long.valueOf(DEFAULT_TIME_TO_LIVE);
            return cacheAssetFromURL(string, jSONObject.optString(TapjoyConstants.TJC_EVENT_OFFER_ID), Long.valueOf(jSONObject.optLong(TapjoyConstants.TJC_TIME_TO_LIVE)).longValue());
        } catch (JSONException e) {
            return null;
        }
    }

    public Future<Boolean> cacheAssetFromURL(String str, String str2, long j) {
        try {
            URL url = new URL(str);
            if (this._currentlyDownloading.contains(getHashFromURL(str))) {
                return null;
            }
            return startCachingThread(url, str2, j);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public void cacheAssetGroup(final JSONArray jSONArray, final TapjoyCacheNotifier tapjoyCacheNotifier) {
        if (jSONArray != null && jSONArray.length() > 0) {
            new Thread() {
                public void run() {
                    ArrayList<Future> arrayList = new ArrayList<>();
                    int i = 1;
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        try {
                            Future<Boolean> cacheAssetFromJSONObject = TapjoyCache.this.cacheAssetFromJSONObject(jSONArray.getJSONObject(i2));
                            if (cacheAssetFromJSONObject != null) {
                                arrayList.add(cacheAssetFromJSONObject);
                            }
                        } catch (JSONException e) {
                        }
                    }
                    for (Future future : arrayList) {
                        try {
                            if (!((Boolean) future.get()).booleanValue()) {
                                i = 2;
                            }
                            if (TapjoyCache.this._verboseDebugging) {
                                TapjoyLog.i(TapjoyCache.TAG, "Caching thread completed");
                            }
                        } catch (InterruptedException e2) {
                            i = 2;
                        } catch (ExecutionException e3) {
                            i = 2;
                        }
                    }
                    if (tapjoyCacheNotifier != null) {
                        tapjoyCacheNotifier.cachingComplete(i);
                    }
                }
            }.start();
        } else if (tapjoyCacheNotifier != null) {
            tapjoyCacheNotifier.cachingComplete(1);
        }
    }

    public String cachedAssetsToJSON() {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry entry : this._cachedAssets.entrySet()) {
            try {
                jSONObject.put(((String) entry.getKey()).toString(), ((TapjoyCachedAssetData) entry.getValue()).toRawJSONString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    public void clearTapjoyCache() {
        TapjoyUtil.deleteFileOrDirectory(this._tajoyCacheDir);
        if (this._tajoyCacheDir.mkdirs()) {
        }
        this._cachedAssets = new TapjoyCacheMap(this._context, -1);
    }

    public void decrementEventCacheCount() {
        this._eventPreloadCount--;
        if (this._eventPreloadCount < 0) {
            this._eventPreloadCount = 0;
        }
        printEventCacheInformation();
    }

    public TapjoyCacheMap getCachedData() {
        return this._cachedAssets;
    }

    public TapjoyCachedAssetData getCachedDataForURL(String str) {
        String hashFromURL = getHashFromURL(str);
        if (hashFromURL != BuildConfig.FLAVOR) {
            return (TapjoyCachedAssetData) this._cachedAssets.get(hashFromURL);
        }
        return null;
    }

    public String getCachedOfferIDs() {
        ArrayList arrayList = new ArrayList();
        if (this._cachedAssets == null) {
            return BuildConfig.FLAVOR;
        }
        for (Map.Entry value : this._cachedAssets.entrySet()) {
            String offerId = ((TapjoyCachedAssetData) value.getValue()).getOfferId();
            if (!(offerId == null || offerId.length() == 0 || arrayList.contains(offerId))) {
                arrayList.add(offerId);
            }
        }
        return TextUtils.join(",", arrayList);
    }

    public int getEventPreloadCount() {
        return this._eventPreloadCount;
    }

    public int getEventPreloadLimit() {
        return this._eventPreloadLimit;
    }

    public String getPathOfCachedURL(String str) {
        String hashFromURL = getHashFromURL(str);
        if (hashFromURL == BuildConfig.FLAVOR || !this._cachedAssets.containsKey(hashFromURL)) {
            return str;
        }
        TapjoyCachedAssetData tapjoyCachedAssetData = (TapjoyCachedAssetData) this._cachedAssets.get(hashFromURL);
        if (new File(tapjoyCachedAssetData.getLocalFilePath()).exists()) {
            return tapjoyCachedAssetData.getLocalURL();
        }
        getInstance().removeAssetFromCache(str);
        return str;
    }

    public void incrementEventCacheCount() {
        this._eventPreloadCount++;
        if (this._eventPreloadCount > this._eventPreloadLimit) {
            this._eventPreloadCount = this._eventPreloadLimit;
        }
        printEventCacheInformation();
    }

    public boolean isURLCached(String str) {
        return this._cachedAssets.get(str) != null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
        r0 = getHashFromURL(r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isURLDownloading(java.lang.String r4) {
        /*
            r3 = this;
            r1 = 0
            java.util.Vector<java.lang.String> r2 = r3._currentlyDownloading
            if (r2 == 0) goto L_0x0016
            java.lang.String r0 = r3.getHashFromURL(r4)
            java.lang.String r2 = ""
            if (r0 == r2) goto L_0x0016
            java.util.Vector<java.lang.String> r2 = r3._currentlyDownloading
            boolean r2 = r2.contains(r0)
            if (r2 == 0) goto L_0x0016
            r1 = 1
        L_0x0016:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tapjoy.TapjoyCache.isURLDownloading(java.lang.String):boolean");
    }

    public void printCacheInformation() {
        TapjoyLog.i(TAG, "------------- Cache Data -------------");
        TapjoyLog.i(TAG, "Number of files in cache: " + this._cachedAssets.size());
        TapjoyLog.i(TAG, "Cache Size: " + TapjoyUtil.fileOrDirectorySize(this._tajoyCacheDir));
        TapjoyLog.i(TAG, "--------------------------------------");
    }

    public void printEventCacheInformation() {
    }

    public boolean removeAssetFromCache(String str) {
        String hashFromURL = getHashFromURL(str);
        return (hashFromURL == BuildConfig.FLAVOR || this._cachedAssets.remove((Object) hashFromURL) == null) ? false : true;
    }

    public void setEventPreloadLimit(int i) {
        this._eventPreloadLimit = i;
    }

    public Future<Boolean> startCachingThread(URL url, String str, long j) {
        if (url != null) {
            return this.executor.submit(new CacheAssetThread(url, str, j));
        }
        return null;
    }
}
