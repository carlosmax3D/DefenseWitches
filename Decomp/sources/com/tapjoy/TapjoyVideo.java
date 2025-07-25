package com.tapjoy;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import com.google.android.gms.drive.DriveFile;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilderFactory;
import jp.stargarage.g2metrics.BuildConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TapjoyVideo {
    private static final String TAG = "TapjoyVideo";
    private static TapjoyVideo tapjoyVideo = null;
    private static TapjoyVideoNotifier tapjoyVideoNotifier;
    /* access modifiers changed from: private */
    public boolean cache3g = false;
    /* access modifiers changed from: private */
    public boolean cacheAuto = false;
    /* access modifiers changed from: private */
    public boolean cacheWifi = false;
    /* access modifiers changed from: private */
    public Hashtable<String, OldTapjoyVideoData> cachedVideos;
    Context context;
    private String imageCacheDir = null;
    /* access modifiers changed from: private */
    public boolean initialized = false;
    /* access modifiers changed from: private */
    public Hashtable<String, OldTapjoyVideoData> uncachedVideos;
    private String videoCacheDir = null;
    /* access modifiers changed from: private */
    public int videoCacheLimit = 5;
    /* access modifiers changed from: private */
    public Vector<String> videoQueue;
    private OldTapjoyVideoData videoToPlay;

    public TapjoyVideo(Context context2) {
        this.context = context2;
        tapjoyVideo = this;
        if (Environment.getExternalStorageDirectory() != null) {
            this.videoCacheDir = Environment.getExternalStorageDirectory().toString() + "/tjcache/data/";
            this.imageCacheDir = Environment.getExternalStorageDirectory().toString() + "/tjcache/tmp/";
            TapjoyUtil.deleteFileOrDirectory(new File(Environment.getExternalStorageDirectory().toString() + "/tapjoy/"));
            TapjoyUtil.deleteFileOrDirectory(new File(this.imageCacheDir));
        }
        this.videoQueue = new Vector<>();
        this.uncachedVideos = new Hashtable<>();
        this.cachedVideos = new Hashtable<>();
        if (TapjoyConnectCore.getConnectFlagValue(TapjoyConnectFlag.VIDEO_CACHE_COUNT) != null && TapjoyConnectCore.getConnectFlagValue(TapjoyConnectFlag.VIDEO_CACHE_COUNT).length() > 0) {
            try {
                TapjoyLog.i(TAG, "Setting video cache count to: " + TapjoyConnectCore.getConnectFlagValue(TapjoyConnectFlag.VIDEO_CACHE_COUNT));
                setVideoCacheCount(Integer.parseInt(TapjoyConnectCore.getConnectFlagValue(TapjoyConnectFlag.VIDEO_CACHE_COUNT)));
            } catch (Exception e) {
                TapjoyLog.e(TAG, "Error, invalid value for video_cache_count: " + TapjoyConnectCore.getConnectFlagValue(TapjoyConnectFlag.VIDEO_CACHE_COUNT));
            }
        }
        init();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0186  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0195 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cacheVideoFromURL(java.lang.String r29) {
        /*
            r28 = this;
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder
            r25.<init>()
            java.lang.String r26 = "download and cache video from: "
            java.lang.StringBuilder r25 = r25.append(r26)
            r0 = r25
            r1 = r29
            java.lang.StringBuilder r25 = r0.append(r1)
            java.lang.String r25 = r25.toString()
            com.tapjoy.TapjoyLog.i(r24, r25)
            long r22 = java.lang.System.currentTimeMillis()
            r15 = 0
            r6 = 0
            r11 = 0
            r17 = 0
            r9 = 0
            r19 = 0
            r20 = 0
            java.net.URL r10 = new java.net.URL     // Catch:{ SocketTimeoutException -> 0x0293, Exception -> 0x023d }
            r0 = r29
            r10.<init>(r0)     // Catch:{ SocketTimeoutException -> 0x0293, Exception -> 0x023d }
            java.net.URLConnection r5 = r10.openConnection()     // Catch:{ SocketTimeoutException -> 0x0293, Exception -> 0x023d }
            r24 = 15000(0x3a98, float:2.102E-41)
            r0 = r24
            r5.setConnectTimeout(r0)     // Catch:{ SocketTimeoutException -> 0x0293, Exception -> 0x023d }
            r24 = 30000(0x7530, float:4.2039E-41)
            r0 = r24
            r5.setReadTimeout(r0)     // Catch:{ SocketTimeoutException -> 0x0293, Exception -> 0x023d }
            r5.connect()     // Catch:{ SocketTimeoutException -> 0x0293, Exception -> 0x023d }
            java.io.BufferedInputStream r12 = new java.io.BufferedInputStream     // Catch:{ SocketTimeoutException -> 0x0293, Exception -> 0x023d }
            java.io.InputStream r24 = r5.getInputStream()     // Catch:{ SocketTimeoutException -> 0x0293, Exception -> 0x023d }
            r0 = r24
            r12.<init>(r0)     // Catch:{ SocketTimeoutException -> 0x0293, Exception -> 0x023d }
            java.io.File r8 = new java.io.File     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r0 = r28
            java.lang.String r0 = r0.videoCacheDir     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r24 = r0
            r0 = r24
            r8.<init>(r0)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r24 = 0
            java.lang.String r25 = "/"
            r0 = r29
            r1 = r25
            int r25 = r0.lastIndexOf(r1)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            int r25 = r25 + 1
            r0 = r29
            r1 = r24
            r2 = r25
            java.lang.String r19 = r0.substring(r1, r2)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r24 = "/"
            r0 = r29
            r1 = r24
            int r24 = r0.lastIndexOf(r1)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            int r24 = r24 + 1
            r0 = r29
            r1 = r24
            java.lang.String r9 = r0.substring(r1)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r24 = 0
            r25 = 46
            r0 = r25
            int r25 = r9.indexOf(r0)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r0 = r24
            r1 = r25
            java.lang.String r9 = r9.substring(r0, r1)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r25.<init>()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r26 = "fileDir: "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r8)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r25 = r25.toString()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            com.tapjoy.TapjoyLog.i(r24, r25)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r25.<init>()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r26 = "path: "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r0 = r25
            r1 = r19
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r25 = r25.toString()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            com.tapjoy.TapjoyLog.i(r24, r25)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r25.<init>()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r26 = "file name: "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r9)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r25 = r25.toString()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            com.tapjoy.TapjoyLog.i(r24, r25)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            boolean r24 = r8.mkdirs()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            if (r24 == 0) goto L_0x010e
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r25.<init>()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r26 = "created directory at: "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r26 = r8.getPath()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.lang.String r25 = r25.toString()     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            com.tapjoy.TapjoyLog.i(r24, r25)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
        L_0x010e:
            java.io.File r21 = new java.io.File     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r0 = r28
            java.lang.String r0 = r0.videoCacheDir     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            r24 = r0
            r0 = r21
            r1 = r24
            r0.<init>(r1, r9)     // Catch:{ SocketTimeoutException -> 0x0296, Exception -> 0x0284 }
            java.io.FileOutputStream r18 = new java.io.FileOutputStream     // Catch:{ SocketTimeoutException -> 0x029a, Exception -> 0x0287 }
            r0 = r18
            r1 = r21
            r0.<init>(r1)     // Catch:{ SocketTimeoutException -> 0x029a, Exception -> 0x0287 }
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            r25.<init>()     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            java.lang.String r26 = "downloading video file to: "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            java.lang.String r26 = r21.toString()     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            java.lang.String r25 = r25.toString()     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            com.tapjoy.TapjoyLog.i(r24, r25)     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            r24 = 1024(0x400, float:1.435E-42)
            r0 = r24
            byte[] r4 = new byte[r0]     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
        L_0x0148:
            int r14 = r12.read(r4)     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            r24 = -1
            r0 = r24
            if (r14 == r0) goto L_0x0209
            r24 = 0
            r0 = r18
            r1 = r24
            r0.write(r4, r1, r14)     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            goto L_0x0148
        L_0x015c:
            r7 = move-exception
            r20 = r21
            r17 = r18
            r11 = r12
        L_0x0162:
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder
            r25.<init>()
            java.lang.String r26 = "Network timeout: "
            java.lang.StringBuilder r25 = r25.append(r26)
            java.lang.String r26 = r7.toString()
            java.lang.StringBuilder r25 = r25.append(r26)
            java.lang.String r25 = r25.toString()
            com.tapjoy.TapjoyLog.e(r24, r25)
            r15 = 1
            r6 = 1
        L_0x0180:
            r24 = 1
            r0 = r24
            if (r15 != r0) goto L_0x0193
            java.lang.String r24 = "TapjoyVideo"
            java.lang.String r25 = "Network timeout"
            com.tapjoy.TapjoyLog.i(r24, r25)
            r11.close()     // Catch:{ Exception -> 0x0281 }
            r17.close()     // Catch:{ Exception -> 0x0281 }
        L_0x0193:
            if (r15 != 0) goto L_0x027b
            if (r6 != 0) goto L_0x027b
            r0 = r28
            java.util.Vector<java.lang.String> r0 = r0.videoQueue     // Catch:{ Exception -> 0x025d }
            r24 = r0
            r25 = 0
            java.lang.Object r13 = r24.elementAt(r25)     // Catch:{ Exception -> 0x025d }
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ Exception -> 0x025d }
            r0 = r28
            java.util.Hashtable<java.lang.String, com.tapjoy.OldTapjoyVideoData> r0 = r0.uncachedVideos     // Catch:{ Exception -> 0x025d }
            r24 = r0
            r0 = r24
            java.lang.Object r16 = r0.get(r13)     // Catch:{ Exception -> 0x025d }
            com.tapjoy.OldTapjoyVideoData r16 = (com.tapjoy.OldTapjoyVideoData) r16     // Catch:{ Exception -> 0x025d }
            java.lang.String r24 = r20.getAbsolutePath()     // Catch:{ Exception -> 0x025d }
            r0 = r16
            r1 = r24
            r0.setLocalFilePath(r1)     // Catch:{ Exception -> 0x025d }
            r0 = r28
            java.util.Hashtable<java.lang.String, com.tapjoy.OldTapjoyVideoData> r0 = r0.cachedVideos     // Catch:{ Exception -> 0x025d }
            r24 = r0
            r0 = r24
            r1 = r16
            r0.put(r13, r1)     // Catch:{ Exception -> 0x025d }
            r0 = r28
            java.util.Hashtable<java.lang.String, com.tapjoy.OldTapjoyVideoData> r0 = r0.uncachedVideos     // Catch:{ Exception -> 0x025d }
            r24 = r0
            r0 = r24
            r0.remove(r13)     // Catch:{ Exception -> 0x025d }
            r0 = r28
            java.util.Vector<java.lang.String> r0 = r0.videoQueue     // Catch:{ Exception -> 0x025d }
            r24 = r0
            r25 = 0
            r24.removeElementAt(r25)     // Catch:{ Exception -> 0x025d }
            r28.setVideoIDs()     // Catch:{ Exception -> 0x025d }
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x025d }
            r25.<init>()     // Catch:{ Exception -> 0x025d }
            java.lang.String r26 = "video cached in: "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ Exception -> 0x025d }
            long r26 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x025d }
            long r26 = r26 - r22
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ Exception -> 0x025d }
            java.lang.String r26 = "ms"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ Exception -> 0x025d }
            java.lang.String r25 = r25.toString()     // Catch:{ Exception -> 0x025d }
            com.tapjoy.TapjoyLog.i(r24, r25)     // Catch:{ Exception -> 0x025d }
        L_0x0208:
            return
        L_0x0209:
            r18.close()     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            r12.close()     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            r25.<init>()     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            java.lang.String r26 = "FILE SIZE: "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            long r26 = r21.length()     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            java.lang.String r25 = r25.toString()     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            com.tapjoy.TapjoyLog.i(r24, r25)     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            long r24 = r21.length()     // Catch:{ SocketTimeoutException -> 0x015c, Exception -> 0x028c }
            r26 = 0
            int r24 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
            if (r24 != 0) goto L_0x0236
            r15 = 1
        L_0x0236:
            r20 = r21
            r17 = r18
            r11 = r12
            goto L_0x0180
        L_0x023d:
            r7 = move-exception
        L_0x023e:
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder
            r25.<init>()
            java.lang.String r26 = "Error caching video file: "
            java.lang.StringBuilder r25 = r25.append(r26)
            java.lang.String r26 = r7.toString()
            java.lang.StringBuilder r25 = r25.append(r26)
            java.lang.String r25 = r25.toString()
            com.tapjoy.TapjoyLog.e(r24, r25)
            r6 = 1
            goto L_0x0180
        L_0x025d:
            r7 = move-exception
            java.lang.String r24 = "TapjoyVideo"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder
            r25.<init>()
            java.lang.String r26 = "error caching video ???: "
            java.lang.StringBuilder r25 = r25.append(r26)
            java.lang.String r26 = r7.toString()
            java.lang.StringBuilder r25 = r25.append(r26)
            java.lang.String r25 = r25.toString()
            com.tapjoy.TapjoyLog.e(r24, r25)
            goto L_0x0208
        L_0x027b:
            r24 = 2
            videoNotifierError(r24)
            goto L_0x0208
        L_0x0281:
            r24 = move-exception
            goto L_0x0193
        L_0x0284:
            r7 = move-exception
            r11 = r12
            goto L_0x023e
        L_0x0287:
            r7 = move-exception
            r20 = r21
            r11 = r12
            goto L_0x023e
        L_0x028c:
            r7 = move-exception
            r20 = r21
            r17 = r18
            r11 = r12
            goto L_0x023e
        L_0x0293:
            r7 = move-exception
            goto L_0x0162
        L_0x0296:
            r7 = move-exception
            r11 = r12
            goto L_0x0162
        L_0x029a:
            r7 = move-exception
            r20 = r21
            r11 = r12
            goto L_0x0162
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tapjoy.TapjoyVideo.cacheVideoFromURL(java.lang.String):void");
    }

    public static TapjoyVideo getInstance() {
        return tapjoyVideo;
    }

    /* access modifiers changed from: private */
    public boolean handleGetVideosResponse(String str) {
        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
        TapjoyLog.i(TAG, "========================================");
        try {
            Document parse = newInstance.newDocumentBuilder().parse(new ByteArrayInputStream(str.getBytes("UTF-8")));
            parse.getDocumentElement().normalize();
            NodeList elementsByTagName = parse.getElementsByTagName("TapjoyVideos");
            NodeList childNodes = elementsByTagName.item(0).getChildNodes();
            NamedNodeMap attributes = elementsByTagName.item(0).getAttributes();
            if (!(attributes.getNamedItem(TapjoyConstants.VIDEO_ATTRIBUTE_CACHE_AUTO) == null || attributes.getNamedItem(TapjoyConstants.VIDEO_ATTRIBUTE_CACHE_AUTO).getNodeValue() == null)) {
                this.cacheAuto = Boolean.valueOf(attributes.getNamedItem(TapjoyConstants.VIDEO_ATTRIBUTE_CACHE_AUTO).getNodeValue()).booleanValue();
            }
            if (!(attributes.getNamedItem(TapjoyConstants.VIDEO_ATTRIBUTE_CACHE_WIFI) == null || attributes.getNamedItem(TapjoyConstants.VIDEO_ATTRIBUTE_CACHE_WIFI).getNodeValue() == null)) {
                this.cacheWifi = Boolean.valueOf(attributes.getNamedItem(TapjoyConstants.VIDEO_ATTRIBUTE_CACHE_WIFI).getNodeValue()).booleanValue();
            }
            if (!(attributes.getNamedItem(TapjoyConstants.VIDEO_ATTRIBUTE_CACHE_MOBILE) == null || attributes.getNamedItem(TapjoyConstants.VIDEO_ATTRIBUTE_CACHE_MOBILE).getNodeValue() == null)) {
                this.cache3g = Boolean.valueOf(attributes.getNamedItem(TapjoyConstants.VIDEO_ATTRIBUTE_CACHE_MOBILE).getNodeValue()).booleanValue();
            }
            TapjoyLog.i(TAG, "cacheAuto: " + this.cacheAuto);
            TapjoyLog.i(TAG, "cacheWifi: " + this.cacheWifi);
            TapjoyLog.i(TAG, "cache3g: " + this.cache3g);
            TapjoyLog.i(TAG, "nodelistParent length: " + elementsByTagName.getLength());
            TapjoyLog.i(TAG, "nodelist length: " + childNodes.getLength());
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                OldTapjoyVideoData oldTapjoyVideoData = new OldTapjoyVideoData();
                if (item != null && item.getNodeType() == 1) {
                    Element element = (Element) item;
                    String nodeTrimValue = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("ClickURL"));
                    if (nodeTrimValue != null && !nodeTrimValue.equals(BuildConfig.FLAVOR)) {
                        oldTapjoyVideoData.setClickURL(nodeTrimValue);
                    }
                    String nodeTrimValue2 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("OfferID"));
                    if (nodeTrimValue2 != null && !nodeTrimValue2.equals(BuildConfig.FLAVOR)) {
                        oldTapjoyVideoData.setOfferID(nodeTrimValue2);
                    }
                    String nodeTrimValue3 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("Name"));
                    if (nodeTrimValue3 != null && !nodeTrimValue3.equals(BuildConfig.FLAVOR)) {
                        oldTapjoyVideoData.setVideoAdName(nodeTrimValue3);
                    }
                    String nodeTrimValue4 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("Amount"));
                    if (nodeTrimValue4 != null && !nodeTrimValue4.equals(BuildConfig.FLAVOR)) {
                        oldTapjoyVideoData.setCurrencyAmount(nodeTrimValue4);
                    }
                    String nodeTrimValue5 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("CurrencyName"));
                    if (nodeTrimValue5 != null && !nodeTrimValue5.equals(BuildConfig.FLAVOR)) {
                        oldTapjoyVideoData.setCurrencyName(nodeTrimValue5);
                    }
                    String nodeTrimValue6 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("VideoURL"));
                    if (nodeTrimValue6 != null && !nodeTrimValue6.equals(BuildConfig.FLAVOR)) {
                        oldTapjoyVideoData.setVideoURL(nodeTrimValue6);
                    }
                    String nodeTrimValue7 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("IconURL"));
                    if (nodeTrimValue7 != null && !nodeTrimValue7.equals(BuildConfig.FLAVOR)) {
                        oldTapjoyVideoData.setIconURL(nodeTrimValue7);
                    }
                    TapjoyLog.i(TAG, "-----");
                    TapjoyLog.i(TAG, "videoObject.offerID: " + oldTapjoyVideoData.getOfferId());
                    TapjoyLog.i(TAG, "videoObject.videoAdName: " + oldTapjoyVideoData.getVideoAdName());
                    TapjoyLog.i(TAG, "videoObject.videoURL: " + oldTapjoyVideoData.getVideoURL());
                    NodeList childNodes2 = element.getElementsByTagName("Buttons").item(0).getChildNodes();
                    for (int i2 = 0; i2 < childNodes2.getLength(); i2++) {
                        NodeList childNodes3 = childNodes2.item(i2).getChildNodes();
                        if (childNodes3.getLength() != 0) {
                            String str2 = BuildConfig.FLAVOR;
                            String str3 = BuildConfig.FLAVOR;
                            for (int i3 = 0; i3 < childNodes3.getLength(); i3++) {
                                if (((Element) childNodes3.item(i3)) != null) {
                                    String tagName = ((Element) childNodes3.item(i3)).getTagName();
                                    if (tagName.equals("Name") && childNodes3.item(i3).getFirstChild() != null) {
                                        str2 = childNodes3.item(i3).getFirstChild().getNodeValue();
                                    } else if (tagName.equals("URL") && childNodes3.item(i3).getFirstChild() != null) {
                                        str3 = childNodes3.item(i3).getFirstChild().getNodeValue();
                                    }
                                }
                            }
                            TapjoyLog.i(TAG, "name: " + str2 + ", url: " + str3);
                            oldTapjoyVideoData.addButton(str2, str3);
                        }
                    }
                    this.videoQueue.addElement(oldTapjoyVideoData.getOfferId());
                    this.uncachedVideos.put(oldTapjoyVideoData.getOfferId(), oldTapjoyVideoData);
                }
            }
            TapjoyLog.i(TAG, "========================================");
            return true;
        } catch (Exception e) {
            TapjoyLog.e(TAG, "Error parsing XML: " + e.toString());
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void printCachedVideos() {
        TapjoyLog.i(TAG, "cachedVideos size: " + this.cachedVideos.size());
        for (Map.Entry next : this.cachedVideos.entrySet()) {
            TapjoyLog.i(TAG, "key: " + ((String) next.getKey()) + ", name: " + ((OldTapjoyVideoData) next.getValue()).getVideoAdName());
        }
    }

    /* access modifiers changed from: private */
    public void setVideoIDs() {
        String str = BuildConfig.FLAVOR;
        if (this.cachedVideos != null && this.cachedVideos.size() > 0) {
            Enumeration<String> keys = this.cachedVideos.keys();
            while (keys.hasMoreElements()) {
                str = str + keys.nextElement();
                if (keys.hasMoreElements()) {
                    str = str + ",";
                }
            }
            TapjoyLog.i(TAG, "cachedVideos size: " + this.cachedVideos.size());
        }
        TapjoyLog.i(TAG, "videoIDs: [" + str + "]");
    }

    /* access modifiers changed from: private */
    public boolean validateCachedVideos() {
        boolean z = true;
        File[] listFiles = new File(this.videoCacheDir).listFiles();
        if (this.uncachedVideos == null) {
            TapjoyLog.e(TAG, "Error: uncachedVideos is null");
            z = false;
        }
        if (this.cachedVideos == null) {
            TapjoyLog.e(TAG, "Error: cachedVideos is null");
            z = false;
        }
        if (this.videoQueue == null) {
            TapjoyLog.e(TAG, "Error: videoQueue is null");
            z = false;
        }
        if (!z || listFiles == null) {
            return false;
        }
        for (int i = 0; i < listFiles.length; i++) {
            String name = listFiles[i].getName();
            TapjoyLog.i(TAG, "-----");
            TapjoyLog.i(TAG, "Examining cached file[" + i + "]: " + listFiles[i].getAbsolutePath() + " --- " + listFiles[i].getName());
            if (this.uncachedVideos.containsKey(name)) {
                TapjoyLog.i(TAG, "Local file found");
                OldTapjoyVideoData oldTapjoyVideoData = this.uncachedVideos.get(name);
                if (oldTapjoyVideoData != null) {
                    String contentLength = new TapjoyURLConnection().getContentLength(oldTapjoyVideoData.getVideoURL());
                    TapjoyLog.i(TAG, "local file size: " + listFiles[i].length() + " vs. target: " + contentLength);
                    if (contentLength == null || ((long) Integer.parseInt(contentLength)) != listFiles[i].length()) {
                        TapjoyLog.i(TAG, "file size mismatch --- deleting video: " + listFiles[i].getAbsolutePath());
                        TapjoyUtil.deleteFileOrDirectory(listFiles[i]);
                    } else {
                        oldTapjoyVideoData.setLocalFilePath(listFiles[i].getAbsolutePath());
                        this.cachedVideos.put(name, oldTapjoyVideoData);
                        this.uncachedVideos.remove(name);
                        this.videoQueue.remove(name);
                        TapjoyLog.i(TAG, "VIDEO PREVIOUSLY CACHED -- " + name + ", location: " + oldTapjoyVideoData.getLocalFilePath());
                    }
                }
            } else {
                TapjoyLog.i(TAG, "VIDEO EXPIRED? removing video from cache: " + name + " --- " + listFiles[i].getAbsolutePath());
                TapjoyUtil.deleteFileOrDirectory(listFiles[i]);
            }
        }
        return true;
    }

    public static void videoNotifierComplete() {
        if (tapjoyVideoNotifier != null) {
            tapjoyVideoNotifier.videoComplete();
        }
    }

    public static void videoNotifierError(int i) {
        if (tapjoyVideoNotifier != null) {
            tapjoyVideoNotifier.videoError(i);
        }
    }

    public static void videoNotifierStart() {
        if (tapjoyVideoNotifier != null) {
            tapjoyVideoNotifier.videoStart();
        }
    }

    public void cacheVideos() {
        new Thread(new Runnable() {
            public void run() {
                TapjoyLog.i(TapjoyVideo.TAG, "--- cacheAllVideos called ---");
                int i = 0;
                while (!TapjoyVideo.this.initialized) {
                    try {
                        Thread.sleep(500);
                        i = (int) (((long) i) + 500);
                        if (((long) i) > 10000) {
                            TapjoyLog.e(TapjoyVideo.TAG, "Error during cacheVideos.  Timeout while waiting for initVideos to finish.");
                            return;
                        }
                    } catch (Exception e) {
                        TapjoyLog.e(TapjoyVideo.TAG, "Exception in cacheAllVideos: " + e.toString());
                    }
                }
                TapjoyLog.i(TapjoyVideo.TAG, "cacheVideos connection_type: " + TapjoyConnectCore.getConnectionType());
                TapjoyLog.i(TapjoyVideo.TAG, "cache3g: " + TapjoyVideo.this.cache3g);
                TapjoyLog.i(TapjoyVideo.TAG, "cacheWifi: " + TapjoyVideo.this.cacheWifi);
                if ((!TapjoyVideo.this.cache3g || !TapjoyConnectCore.getConnectionType().equals(TapjoyConstants.TJC_CONNECTION_TYPE_MOBILE)) && (!TapjoyVideo.this.cacheWifi || !TapjoyConnectCore.getConnectionType().equals(TapjoyConstants.TJC_CONNECTION_TYPE_WIFI))) {
                    TapjoyLog.i(TapjoyVideo.TAG, " * Skipping caching videos because of video flags and connection_type...");
                } else if (!"mounted".equals(Environment.getExternalStorageState())) {
                    TapjoyLog.i(TapjoyVideo.TAG, "Media storage unavailable.  Aborting caching videos.");
                    TapjoyVideo.videoNotifierError(1);
                    return;
                } else {
                    while (TapjoyVideo.this.cachedVideos.size() < TapjoyVideo.this.videoCacheLimit && TapjoyVideo.this.videoQueue.size() > 0) {
                        TapjoyVideo.this.cacheVideoFromURL(((OldTapjoyVideoData) TapjoyVideo.this.uncachedVideos.get(TapjoyVideo.this.videoQueue.elementAt(0))).getVideoURL());
                    }
                }
                TapjoyVideo.this.printCachedVideos();
            }
        }).start();
    }

    public OldTapjoyVideoData getCurrentVideoData() {
        return this.videoToPlay;
    }

    public void init() {
        TapjoyLog.i(TAG, "initVideoAd");
        if (!TapjoyConnectCore.isVideoEnabled()) {
            TapjoyLog.i(TAG, "Videos have been configured to be disabled. Aborting video initialization... ");
            return;
        }
        setVideoIDs();
        new Thread(new Runnable() {
            public void run() {
                boolean z = false;
                TapjoyHttpURLResponse responseFromURL = new TapjoyURLConnection().getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_GET_VIDEOS_URL_PATH, TapjoyConnectCore.getURLParams());
                if (responseFromURL.response != null && responseFromURL.response.length() > 0) {
                    z = TapjoyVideo.this.handleGetVideosResponse(responseFromURL.response);
                }
                if (z) {
                    boolean unused = TapjoyVideo.this.validateCachedVideos();
                    TapjoyVideo.this.setVideoIDs();
                    boolean unused2 = TapjoyVideo.this.initialized = true;
                    if (TapjoyVideo.this.cacheAuto) {
                        TapjoyLog.i(TapjoyVideo.TAG, "trying to cache because of cache_auto flag...");
                        TapjoyVideo.this.cacheVideos();
                    }
                    TapjoyLog.i(TapjoyVideo.TAG, "------------------------------");
                    TapjoyLog.i(TapjoyVideo.TAG, "------------------------------");
                    TapjoyLog.i(TapjoyVideo.TAG, "INIT DONE!");
                    TapjoyLog.i(TapjoyVideo.TAG, "------------------------------");
                    return;
                }
                TapjoyVideo.videoNotifierError(2);
            }
        }).start();
    }

    public void setVideoCacheCount(int i) {
        this.videoCacheLimit = i;
    }

    public void setVideoNotifier(TapjoyVideoNotifier tapjoyVideoNotifier2) {
        tapjoyVideoNotifier = tapjoyVideoNotifier2;
    }

    public boolean startVideo(String str, String str2, String str3, String str4, String str5, String str6) {
        File file;
        boolean z = true;
        TapjoyLog.i(TAG, "Starting video activity with video: " + str);
        if (str == null || str4 == null || str5 == null || str.length() == 0 || str4.length() == 0 || str5.length() == 0) {
            TapjoyLog.i(TAG, "aborting video playback... invalid or missing parameter");
            return false;
        }
        this.videoToPlay = this.cachedVideos.get(str);
        if (this.videoToPlay == null) {
            TapjoyLog.i(TAG, "video not cached... checking uncached videos");
            this.videoToPlay = this.uncachedVideos.get(str);
            if (this.videoToPlay == null) {
                if (str6 == null || str6.length() <= 0) {
                    TapjoyLog.e(TAG, "no video data and no video url - aborting playback...");
                    return false;
                }
                OldTapjoyVideoData oldTapjoyVideoData = new OldTapjoyVideoData();
                oldTapjoyVideoData.setOfferID(str);
                oldTapjoyVideoData.setCurrencyName(str2);
                oldTapjoyVideoData.setCurrencyAmount(str3);
                oldTapjoyVideoData.setClickURL(str4);
                oldTapjoyVideoData.setWebviewURL(str5);
                oldTapjoyVideoData.setVideoURL(str6);
                this.uncachedVideos.put(str, oldTapjoyVideoData);
                this.videoToPlay = this.uncachedVideos.get(str);
            }
            z = false;
        }
        this.videoToPlay.setCurrencyName(str2);
        this.videoToPlay.setCurrencyAmount(str3);
        this.videoToPlay.setClickURL(str4);
        this.videoToPlay.setWebviewURL(str5);
        this.videoToPlay.setVideoURL(str6);
        TapjoyLog.i(TAG, "videoToPlay: " + this.videoToPlay.getOfferId());
        TapjoyLog.i(TAG, "amount: " + this.videoToPlay.getCurrencyAmount());
        TapjoyLog.i(TAG, "currency: " + this.videoToPlay.getCurrencyName());
        TapjoyLog.i(TAG, "clickURL: " + this.videoToPlay.getClickURL());
        TapjoyLog.i(TAG, "webviewURL: " + this.videoToPlay.getWebviewURL());
        TapjoyLog.i(TAG, "videoURL: " + this.videoToPlay.getVideoURL());
        if (!z || this.videoToPlay.getLocalFilePath() == null || ((file = new File(this.videoToPlay.getLocalFilePath())) != null && file.exists())) {
            Intent intent = new Intent(this.context, TapjoyVideoView.class);
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            intent.putExtra(TapjoyConstants.EXTRA_VIDEO_DATA, this.videoToPlay);
            this.context.startActivity(intent);
            return true;
        }
        TapjoyLog.e(TAG, "video file does not exist.");
        return false;
    }
}
