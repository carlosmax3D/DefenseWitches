package com.ansca.corona.maps;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.ansca.corona.Controller;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.MessageBasedTimer;
import com.ansca.corona.storage.FileContentProvider;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import jp.stargarage.g2metrics.BuildConfig;

public class MapView extends FrameLayout {
    /* access modifiers changed from: private */
    public Controller fController;
    /* access modifiers changed from: private */
    public CoronaRuntime fCoronaRuntime;
    /* access modifiers changed from: private */
    public Location fCurrentLocation = null;
    private AtomicInteger fIdCounter = new AtomicInteger(1);
    private boolean fIsCurrentLocationTrackingEnabled = false;
    /* access modifiers changed from: private */
    public boolean fIsMapLoaded = false;
    private boolean fIsScrollEnabled = true;
    private boolean fIsZoomEnabled = true;
    /* access modifiers changed from: private */
    public ProgressBar fLoadingIndicatorView;
    /* access modifiers changed from: private */
    public LocationBounds fMapLocationBounds = null;
    private MapType fMapType = MapType.STANDARD;
    /* access modifiers changed from: private */
    public Hashtable<Integer, MapMarker> fMarkerTable = new Hashtable<>();
    private LinkedList<Runnable> fOperationQueue = new LinkedList<>();
    /* access modifiers changed from: private */
    public MessageBasedTimer fWatchdogTimer;
    /* access modifiers changed from: private */
    public WebView fWebView;

    private class JavaScriptInterface {
        private final String[] fCurrentLocationMarkerResourceNames;

        private JavaScriptInterface() {
            this.fCurrentLocationMarkerResourceNames = new String[]{"ic_maps_indicator_current_position", "ic_maps_indicator_current_position_anim1", "ic_maps_indicator_current_position_anim2", "ic_maps_indicator_current_position_anim3", "ic_maps_indicator_current_position_anim2", "ic_maps_indicator_current_position_anim1"};
        }

        /* JADX WARNING: Removed duplicated region for block: B:21:0x007c A[SYNTHETIC, Splitter:B:21:0x007c] */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x0081  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0088 A[SYNTHETIC, Splitter:B:27:0x0088] */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x008d  */
        /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
        @android.webkit.JavascriptInterface
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String getCurrentLocationMarkerUrlByIndex(int r12) {
            /*
                r11 = this;
                r7 = 0
                if (r12 < 0) goto L_0x0008
                java.lang.String[] r8 = r11.fCurrentLocationMarkerResourceNames
                int r8 = r8.length
                if (r12 < r8) goto L_0x0009
            L_0x0008:
                return r7
            L_0x0009:
                java.io.File r2 = new java.io.File
                com.ansca.corona.maps.MapView r8 = com.ansca.corona.maps.MapView.this
                com.ansca.corona.maps.MapView r9 = com.ansca.corona.maps.MapView.this
                android.content.Context r9 = r9.getContext()
                java.lang.String r10 = "getInternalResourceCachesDirectory"
                java.io.File r8 = r8.getInternalCacheDirectory(r9, r10)
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String[] r10 = r11.fCurrentLocationMarkerResourceNames
                r10 = r10[r12]
                java.lang.StringBuilder r9 = r9.append(r10)
                java.lang.String r10 = ".png"
                java.lang.StringBuilder r9 = r9.append(r10)
                java.lang.String r9 = r9.toString()
                r2.<init>(r8, r9)
                boolean r8 = r2.exists()
                if (r8 != 0) goto L_0x006d
                r0 = 0
                r5 = 0
                com.ansca.corona.storage.ResourceServices r4 = new com.ansca.corona.storage.ResourceServices     // Catch:{ Exception -> 0x0076 }
                android.content.Context r8 = com.ansca.corona.CoronaEnvironment.getApplicationContext()     // Catch:{ Exception -> 0x0076 }
                r4.<init>(r8)     // Catch:{ Exception -> 0x0076 }
                java.lang.String[] r8 = r11.fCurrentLocationMarkerResourceNames     // Catch:{ Exception -> 0x0076 }
                r8 = r8[r12]     // Catch:{ Exception -> 0x0076 }
                int r3 = r4.getDrawableResourceId(r8)     // Catch:{ Exception -> 0x0076 }
                android.content.res.Resources r8 = r4.getResources()     // Catch:{ Exception -> 0x0076 }
                android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeResource(r8, r3)     // Catch:{ Exception -> 0x0076 }
                java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0076 }
                r6.<init>(r2)     // Catch:{ Exception -> 0x0076 }
                android.graphics.Bitmap$CompressFormat r8 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x009a, all -> 0x0097 }
                r9 = 100
                r0.compress(r8, r9, r6)     // Catch:{ Exception -> 0x009a, all -> 0x0097 }
                r6.flush()     // Catch:{ Exception -> 0x009a, all -> 0x0097 }
                if (r6 == 0) goto L_0x0068
                r6.close()     // Catch:{ Exception -> 0x0091 }
            L_0x0068:
                if (r0 == 0) goto L_0x006d
                r0.recycle()
            L_0x006d:
                java.net.URI r7 = r2.toURI()
                java.lang.String r7 = r7.toString()
                goto L_0x0008
            L_0x0076:
                r1 = move-exception
            L_0x0077:
                r1.printStackTrace()     // Catch:{ all -> 0x0085 }
                if (r5 == 0) goto L_0x007f
                r5.close()     // Catch:{ Exception -> 0x0093 }
            L_0x007f:
                if (r0 == 0) goto L_0x0008
                r0.recycle()
                goto L_0x0008
            L_0x0085:
                r7 = move-exception
            L_0x0086:
                if (r5 == 0) goto L_0x008b
                r5.close()     // Catch:{ Exception -> 0x0095 }
            L_0x008b:
                if (r0 == 0) goto L_0x0090
                r0.recycle()
            L_0x0090:
                throw r7
            L_0x0091:
                r7 = move-exception
                goto L_0x0068
            L_0x0093:
                r8 = move-exception
                goto L_0x007f
            L_0x0095:
                r8 = move-exception
                goto L_0x008b
            L_0x0097:
                r7 = move-exception
                r5 = r6
                goto L_0x0086
            L_0x009a:
                r1 = move-exception
                r5 = r6
                goto L_0x0077
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.maps.MapView.JavaScriptInterface.getCurrentLocationMarkerUrlByIndex(int):java.lang.String");
        }

        @JavascriptInterface
        public int getCurrentLocationMarkerUrlCount() {
            return this.fCurrentLocationMarkerResourceNames.length;
        }

        @JavascriptInterface
        public String getMapTypeName() {
            MapType mapType = MapView.this.getMapType();
            if (mapType == null) {
                mapType = MapType.STANDARD;
            }
            return mapType.toInvariantString();
        }

        @JavascriptInterface
        public boolean isScrollEnabled() {
            return MapView.this.isScrollEnabled();
        }

        @JavascriptInterface
        public boolean isZoomEnabled() {
            return MapView.this.isZoomEnabled();
        }

        @JavascriptInterface
        public void onCurrentLocationLost() {
            Location unused = MapView.this.fCurrentLocation = null;
        }

        @JavascriptInterface
        public void onCurrentLocationReceived(double d, double d2, double d3, double d4, double d5, double d6, double d7, long j) {
            Location location = new Location(BuildConfig.FLAVOR);
            location.setLatitude(d);
            location.setLongitude(d2);
            location.setAccuracy((float) d3);
            location.setAltitude(d4);
            location.setBearing((float) d6);
            location.setSpeed((float) d7);
            location.setTime(j);
            Location unused = MapView.this.fCurrentLocation = location;
        }

        @JavascriptInterface
        public void onMapBoundsChanged(double d, double d2, double d3, double d4) {
            LocationBounds unused = MapView.this.fMapLocationBounds = new LocationBounds(d, d2, d3, d4);
        }

        @JavascriptInterface
        public void onMapLoadFinished() {
            MapView.this.getHandler().post(new Runnable() {
                public void run() {
                    boolean unused = MapView.this.fIsMapLoaded = true;
                    MapView.this.fWatchdogTimer.stop();
                    MapView.this.fWebView.setVisibility(MapView.this.getVisibility());
                    MapView.this.fWebView.setAnimation(MapView.this.getAnimation());
                    MapView.this.fWebView.invalidate();
                    MapView.this.setBackgroundDrawable((Drawable) null);
                    if (Build.VERSION.SDK_INT >= 11) {
                        Class<View> cls = View.class;
                        try {
                            cls.getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(MapView.this.fLoadingIndicatorView, new Object[]{1, null});
                            View.class.getMethod("setAlpha", new Class[]{Float.TYPE}).invoke(MapView.this.fLoadingIndicatorView, new Object[]{Float.valueOf(0.0f)});
                        } catch (Exception e) {
                        }
                    } else {
                        MapView.this.removeView(MapView.this.fLoadingIndicatorView);
                    }
                    MapView.this.requestExecuteQueuedOperations();
                }
            });
        }

        @JavascriptInterface
        public void onMapTapped(double d, double d2) {
            if (MapView.this.fCoronaRuntime != null && MapView.this.fCoronaRuntime.isRunning()) {
                MapView.this.fCoronaRuntime.getTaskDispatcher().send(new MapTappedTask(MapView.this.getId(), d, d2));
            }
        }

        @JavascriptInterface
        public void onMarkerTouch(int i) {
            MapMarker mapMarker;
            if (MapView.this.fCoronaRuntime != null && MapView.this.fCoronaRuntime.isRunning() && (mapMarker = (MapMarker) MapView.this.fMarkerTable.get(Integer.valueOf(i))) != null) {
                MapView.this.fCoronaRuntime.getTaskDispatcher().send(new MapMarkerTask(mapMarker));
            }
        }
    }

    private class LocationBounds {
        private Location fNorthEastLocation;
        private Location fSouthWestLocation = new Location(BuildConfig.FLAVOR);

        public LocationBounds(double d, double d2, double d3, double d4) {
            this.fSouthWestLocation.setLatitude(d);
            this.fSouthWestLocation.setLongitude(d2);
            this.fNorthEastLocation = new Location(BuildConfig.FLAVOR);
            this.fNorthEastLocation.setLatitude(d3);
            this.fNorthEastLocation.setLongitude(d4);
        }

        public boolean contains(Location location) {
            if (location != null && location.getLatitude() >= this.fSouthWestLocation.getLatitude() && location.getLatitude() <= this.fNorthEastLocation.getLatitude()) {
                return this.fSouthWestLocation.getLongitude() <= this.fNorthEastLocation.getLongitude() ? location.getLongitude() >= this.fSouthWestLocation.getLongitude() && location.getLongitude() <= this.fNorthEastLocation.getLongitude() : location.getLongitude() <= this.fSouthWestLocation.getLongitude() && location.getLongitude() >= this.fNorthEastLocation.getLongitude();
            }
            return false;
        }
    }

    private class WebViewEventHandler extends WebViewClient {
        private WebViewEventHandler() {
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            MapView.this.fWebView.setVisibility(8);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str == null || str.toLowerCase().contains("maps.google.com/maps?")) {
                return true;
            }
            MapView.this.fController.openUrl(str);
            return true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x01fd A[SYNTHETIC, Splitter:B:45:0x01fd] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0202 A[SYNTHETIC, Splitter:B:48:0x0202] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MapView(android.content.Context r20, com.ansca.corona.CoronaRuntime r21, com.ansca.corona.Controller r22) {
        /*
            r19 = this;
            r19.<init>(r20)
            java.lang.String r14 = "android.permission.INTERNET"
            r15 = 0
            r0 = r20
            r0.enforceCallingOrSelfPermission(r14, r15)
            r0 = r21
            r1 = r19
            r1.fCoronaRuntime = r0
            r0 = r22
            r1 = r19
            r1.fController = r0
            java.util.concurrent.atomic.AtomicInteger r14 = new java.util.concurrent.atomic.AtomicInteger
            r15 = 1
            r14.<init>(r15)
            r0 = r19
            r0.fIdCounter = r14
            java.util.Hashtable r14 = new java.util.Hashtable
            r14.<init>()
            r0 = r19
            r0.fMarkerTable = r14
            java.util.LinkedList r14 = new java.util.LinkedList
            r14.<init>()
            r0 = r19
            r0.fOperationQueue = r14
            r14 = 0
            r0 = r19
            r0.fIsMapLoaded = r14
            com.ansca.corona.maps.MapType r14 = com.ansca.corona.maps.MapType.STANDARD
            r0 = r19
            r0.fMapType = r14
            r14 = 0
            r0 = r19
            r0.fIsCurrentLocationTrackingEnabled = r14
            r14 = 1
            r0 = r19
            r0.fIsZoomEnabled = r14
            r14 = 1
            r0 = r19
            r0.fIsScrollEnabled = r14
            r14 = 0
            r0 = r19
            r0.fCurrentLocation = r14
            r14 = 0
            r0 = r19
            r0.fMapLocationBounds = r14
            r14 = -12303292(0xffffffffff444444, float:-2.6088314E38)
            r0 = r19
            r0.setBackgroundColor(r14)
            android.widget.ProgressBar r14 = new android.widget.ProgressBar
            r0 = r20
            r14.<init>(r0)
            r0 = r19
            r0.fLoadingIndicatorView = r14
            r0 = r19
            android.widget.ProgressBar r14 = r0.fLoadingIndicatorView
            android.widget.FrameLayout$LayoutParams r15 = new android.widget.FrameLayout$LayoutParams
            r16 = -2
            r17 = -2
            r18 = 17
            r15.<init>(r16, r17, r18)
            r14.setLayoutParams(r15)
            r0 = r19
            android.widget.ProgressBar r14 = r0.fLoadingIndicatorView
            r0 = r19
            r0.addView(r14)
            com.ansca.corona.maps.MapView$1 r14 = new com.ansca.corona.maps.MapView$1
            r0 = r19
            r1 = r20
            r14.<init>(r1)
            r0 = r19
            r0.fWebView = r14
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            android.widget.FrameLayout$LayoutParams r15 = new android.widget.FrameLayout$LayoutParams
            r16 = -1
            r17 = -1
            r15.<init>(r16, r17)
            r14.setLayoutParams(r15)
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            r15 = 0
            r14.setVerticalScrollBarEnabled(r15)
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            r15 = 0
            r14.setHorizontalScrollBarEnabled(r15)
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            r15 = 4
            r14.setVisibility(r15)
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            r0 = r19
            r0.addView(r14)
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            android.webkit.WebSettings r13 = r14.getSettings()
            r14 = 0
            r13.setSupportZoom(r14)
            r14 = 0
            r13.setBuiltInZoomControls(r14)
            r14 = 1
            r13.setLoadWithOverviewMode(r14)
            r14 = 1
            r13.setUseWideViewPort(r14)
            r14 = 1
            r13.setJavaScriptEnabled(r14)
            r14 = 1
            r13.setGeolocationEnabled(r14)
            android.webkit.WebSettings$PluginState r14 = android.webkit.WebSettings.PluginState.ON
            r13.setPluginState(r14)
            r14 = 1
            r13.setDomStorageEnabled(r14)
            java.lang.String r14 = "getInternalWebCachesDirectory"
            r0 = r19
            r1 = r20
            java.io.File r14 = r0.getInternalCacheDirectory(r1, r14)
            java.lang.String r14 = r14.getAbsolutePath()
            r13.setAppCachePath(r14)
            r14 = 1
            r13.setAllowFileAccess(r14)
            r14 = 1
            r13.setAppCacheEnabled(r14)
            int r14 = android.os.Build.VERSION.SDK_INT
            r15 = 11
            if (r14 < r15) goto L_0x0130
            java.lang.Class<android.webkit.WebSettings> r14 = android.webkit.WebSettings.class
            java.lang.String r15 = "setEnableSmoothTransition"
            r16 = 1
            r0 = r16
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x01dd }
            r16 = r0
            r17 = 0
            java.lang.Class r18 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x01dd }
            r16[r17] = r18     // Catch:{ Exception -> 0x01dd }
            java.lang.reflect.Method r12 = r14.getMethod(r15, r16)     // Catch:{ Exception -> 0x01dd }
            r14 = 1
            java.lang.Object[] r14 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x01dd }
            r15 = 0
            r16 = 1
            java.lang.Boolean r16 = java.lang.Boolean.valueOf(r16)     // Catch:{ Exception -> 0x01dd }
            r14[r15] = r16     // Catch:{ Exception -> 0x01dd }
            r12.invoke(r13, r14)     // Catch:{ Exception -> 0x01dd }
        L_0x0130:
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            com.ansca.corona.maps.MapView$WebViewEventHandler r15 = new com.ansca.corona.maps.MapView$WebViewEventHandler
            r16 = 0
            r0 = r19
            r1 = r16
            r15.<init>()
            r14.setWebViewClient(r15)
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            com.ansca.corona.maps.MapView$2 r15 = new com.ansca.corona.maps.MapView$2
            r0 = r19
            r15.<init>()
            r14.setWebChromeClient(r15)
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            com.ansca.corona.maps.MapView$JavaScriptInterface r15 = new com.ansca.corona.maps.MapView$JavaScriptInterface
            r16 = 0
            r0 = r19
            r1 = r16
            r15.<init>()
            java.lang.String r16 = "corona"
            r14.addJavascriptInterface(r15, r16)
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            com.ansca.corona.maps.MapView$3 r15 = new com.ansca.corona.maps.MapView$3
            r0 = r19
            r15.<init>()
            r14.setOnTouchListener(r15)
            int r14 = android.os.Build.VERSION.SDK_INT
            r15 = 16
            if (r14 >= r15) goto L_0x017b
            r19.disableHardwareAcceleration()
        L_0x017b:
            java.lang.String r10 = "corona_map_view"
            java.io.File r5 = new java.io.File
            java.lang.String r14 = "getInternalResourceCachesDirectory"
            r0 = r19
            r1 = r20
            java.io.File r14 = r0.getInternalCacheDirectory(r1, r14)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.StringBuilder r15 = r15.append(r10)
            java.lang.String r16 = ".html"
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r15 = r15.toString()
            r5.<init>(r14, r15)
            boolean r14 = r5.exists()
            if (r14 != 0) goto L_0x0206
            r6 = 0
            r7 = 0
            com.ansca.corona.storage.ResourceServices r11 = new com.ansca.corona.storage.ResourceServices     // Catch:{ Exception -> 0x025a }
            r0 = r20
            r11.<init>(r0)     // Catch:{ Exception -> 0x025a }
            int r9 = r11.getRawResourceId(r10)     // Catch:{ Exception -> 0x025a }
            android.content.res.Resources r14 = r20.getResources()     // Catch:{ Exception -> 0x025a }
            java.io.InputStream r6 = r14.openRawResource(r9)     // Catch:{ Exception -> 0x025a }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x025a }
            r8.<init>(r5)     // Catch:{ Exception -> 0x025a }
            r14 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r14]     // Catch:{ Exception -> 0x01e8, all -> 0x0257 }
        L_0x01c3:
            int r3 = r6.read(r2)     // Catch:{ Exception -> 0x01e8, all -> 0x0257 }
            if (r3 > 0) goto L_0x01e3
            r8.flush()     // Catch:{ Exception -> 0x01e8, all -> 0x0257 }
            if (r6 == 0) goto L_0x01d1
            r6.close()     // Catch:{ Exception -> 0x024d }
        L_0x01d1:
            if (r8 == 0) goto L_0x01d6
            r8.close()     // Catch:{ Exception -> 0x024f }
        L_0x01d6:
            boolean r14 = r5.exists()
            if (r14 != 0) goto L_0x0206
        L_0x01dc:
            return
        L_0x01dd:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x0130
        L_0x01e3:
            r14 = 0
            r8.write(r2, r14, r3)     // Catch:{ Exception -> 0x01e8, all -> 0x0257 }
            goto L_0x01c3
        L_0x01e8:
            r4 = move-exception
            r7 = r8
        L_0x01ea:
            r4.printStackTrace()     // Catch:{ all -> 0x01fa }
            if (r6 == 0) goto L_0x01f2
            r6.close()     // Catch:{ Exception -> 0x0251 }
        L_0x01f2:
            if (r7 == 0) goto L_0x01dc
            r7.close()     // Catch:{ Exception -> 0x01f8 }
            goto L_0x01dc
        L_0x01f8:
            r14 = move-exception
            goto L_0x01dc
        L_0x01fa:
            r14 = move-exception
        L_0x01fb:
            if (r6 == 0) goto L_0x0200
            r6.close()     // Catch:{ Exception -> 0x0253 }
        L_0x0200:
            if (r7 == 0) goto L_0x0205
            r7.close()     // Catch:{ Exception -> 0x0255 }
        L_0x0205:
            throw r14
        L_0x0206:
            r0 = r19
            android.webkit.WebView r14 = r0.fWebView
            java.net.URI r15 = r5.toURI()
            java.lang.String r15 = r15.toString()
            r14.loadUrl(r15)
            com.ansca.corona.MessageBasedTimer r14 = new com.ansca.corona.MessageBasedTimer
            r14.<init>()
            r0 = r19
            r0.fWatchdogTimer = r14
            r0 = r19
            com.ansca.corona.MessageBasedTimer r14 = r0.fWatchdogTimer
            android.os.Handler r15 = new android.os.Handler
            r15.<init>()
            r14.setHandler(r15)
            r0 = r19
            com.ansca.corona.MessageBasedTimer r14 = r0.fWatchdogTimer
            r16 = 30
            com.ansca.corona.TimeSpan r15 = com.ansca.corona.TimeSpan.fromSeconds(r16)
            r14.setInterval(r15)
            r0 = r19
            com.ansca.corona.MessageBasedTimer r14 = r0.fWatchdogTimer
            com.ansca.corona.maps.MapView$4 r15 = new com.ansca.corona.maps.MapView$4
            r0 = r19
            r15.<init>()
            r14.setListener(r15)
            r0 = r19
            com.ansca.corona.MessageBasedTimer r14 = r0.fWatchdogTimer
            r14.start()
            goto L_0x01dc
        L_0x024d:
            r14 = move-exception
            goto L_0x01d1
        L_0x024f:
            r14 = move-exception
            goto L_0x01d6
        L_0x0251:
            r14 = move-exception
            goto L_0x01f2
        L_0x0253:
            r15 = move-exception
            goto L_0x0200
        L_0x0255:
            r15 = move-exception
            goto L_0x0205
        L_0x0257:
            r14 = move-exception
            r7 = r8
            goto L_0x01fb
        L_0x025a:
            r4 = move-exception
            goto L_0x01ea
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.maps.MapView.<init>(android.content.Context, com.ansca.corona.CoronaRuntime, com.ansca.corona.Controller):void");
    }

    /* access modifiers changed from: private */
    public String createHtmlTextFrom(String str) {
        return (str == null || str.length() <= 0) ? str : str.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.io.File} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.File getInternalCacheDirectory(android.content.Context r10, java.lang.String r11) {
        /*
            r9 = this;
            r4 = 0
            java.io.File r1 = com.ansca.corona.CoronaEnvironment.getCachesDirectory(r10)
            java.lang.Class<com.ansca.corona.CoronaEnvironment> r5 = com.ansca.corona.CoronaEnvironment.class
            java.lang.reflect.Method[] r3 = r5.getDeclaredMethods()
            int r6 = r3.length
            r5 = r4
        L_0x000d:
            if (r5 >= r6) goto L_0x0032
            r2 = r3[r5]
            java.lang.String r4 = r2.getName()
            boolean r4 = r4.equals(r11)
            if (r4 == 0) goto L_0x002e
            r4 = 1
            r2.setAccessible(r4)     // Catch:{ Exception -> 0x0033 }
            r4 = 0
            r7 = 1
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x0033 }
            r8 = 0
            r7[r8] = r10     // Catch:{ Exception -> 0x0033 }
            java.lang.Object r4 = r2.invoke(r4, r7)     // Catch:{ Exception -> 0x0033 }
            r0 = r4
            java.io.File r0 = (java.io.File) r0     // Catch:{ Exception -> 0x0033 }
            r1 = r0
        L_0x002e:
            int r4 = r5 + 1
            r5 = r4
            goto L_0x000d
        L_0x0032:
            return r1
        L_0x0033:
            r4 = move-exception
            goto L_0x002e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.maps.MapView.getInternalCacheDirectory(android.content.Context, java.lang.String):java.io.File");
    }

    /* access modifiers changed from: private */
    public void requestExecuteQueuedOperations() {
        if (isLoaded()) {
            while (this.fOperationQueue.size() > 0) {
                Runnable remove = this.fOperationQueue.remove();
                if (remove != null) {
                    remove.run();
                }
            }
        }
    }

    public void addMarker(final MapMarker mapMarker) {
        this.fOperationQueue.add(new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder(64);
                sb.append("javascript:addMarker(");
                sb.append(Integer.toString(mapMarker.getMarkerId()));
                sb.append(",");
                sb.append(Double.toString(mapMarker.getLatitude()));
                sb.append(",");
                sb.append(Double.toString(mapMarker.getLongitude()));
                sb.append(",");
                if (mapMarker.getTitle() != null) {
                    sb.append("\"");
                    sb.append(MapView.this.createHtmlTextFrom(mapMarker.getTitle()));
                    sb.append("\"");
                } else {
                    sb.append("null");
                }
                sb.append(",");
                if (mapMarker.getSubtitle() != null) {
                    sb.append("\"");
                    sb.append(MapView.this.createHtmlTextFrom(mapMarker.getSubtitle()));
                    sb.append("\"");
                } else {
                    sb.append("null");
                }
                sb.append(", ");
                if (mapMarker.getImageFile() != null) {
                    sb.append("\"");
                    sb.append(FileContentProvider.createContentUriForFile(MapView.this.getContext(), mapMarker.getImageFile()).toString());
                    sb.append("\"");
                } else {
                    sb.append("null");
                }
                sb.append(")");
                MapView.this.fWebView.loadUrl(sb.toString());
            }
        });
        requestExecuteQueuedOperations();
        synchronized (this.fMarkerTable) {
            this.fMarkerTable.put(Integer.valueOf(mapMarker.getMarkerId()), mapMarker);
        }
    }

    public void destroy() {
        removeAllMarkers();
        if (this.fWebView != null) {
            this.fWebView.destroy();
            this.fWebView = null;
        }
        if (this.fWatchdogTimer != null) {
            this.fWatchdogTimer.stop();
        }
    }

    public void disableHardwareAcceleration() {
        setLayerType(1, (Paint) null);
    }

    public Location getCurrentLocation() {
        return this.fCurrentLocation;
    }

    public MapType getMapType() {
        return this.fMapType;
    }

    public int getNewMarkerId() {
        return this.fIdCounter.getAndIncrement();
    }

    public boolean isCurrentLocationTrackingEnabled() {
        return this.fIsCurrentLocationTrackingEnabled;
    }

    public boolean isCurrentLocationVisible() {
        Location location = this.fCurrentLocation;
        LocationBounds locationBounds = this.fMapLocationBounds;
        if (location == null || locationBounds == null) {
            return false;
        }
        return locationBounds.contains(location);
    }

    public boolean isLoaded() {
        return this.fWebView != null && this.fIsMapLoaded;
    }

    public boolean isScrollEnabled() {
        return this.fIsScrollEnabled;
    }

    public boolean isZoomEnabled() {
        return this.fIsZoomEnabled;
    }

    public void removeAllMarkers() {
        Collection<MapMarker> values;
        this.fOperationQueue.add(new Runnable() {
            public void run() {
                MapView.this.fWebView.loadUrl("javascript:removeAllMarkers()");
            }
        });
        requestExecuteQueuedOperations();
        synchronized (this.fMarkerTable) {
            values = this.fMarkerTable.values();
            this.fMarkerTable.clear();
        }
        for (MapMarker next : values) {
            if (next != null) {
                next.deleteRef(this.fCoronaRuntime);
            }
        }
    }

    public synchronized void removeMarker(final int i) {
        if (this.fOperationQueue != null) {
            this.fOperationQueue.add(new Runnable() {
                public void run() {
                    if (MapView.this.fWebView != null) {
                        StringBuilder sb = new StringBuilder(30);
                        sb.append("javascript:removeMarker(");
                        sb.append(i);
                        sb.append(")");
                        MapView.this.fWebView.loadUrl(sb.toString());
                    }
                }
            });
        }
        requestExecuteQueuedOperations();
        MapMarker mapMarker = this.fMarkerTable.get(Integer.valueOf(i));
        if (mapMarker != null) {
            mapMarker.deleteRef(this.fCoronaRuntime);
            this.fMarkerTable.remove(Integer.valueOf(i));
        }
    }

    public void setAnimation(Animation animation) {
        super.setAnimation(animation);
        if (this.fWebView != null) {
            this.fWebView.setAnimation(animation);
        }
        this.fLoadingIndicatorView.setAnimation(animation);
    }

    public void setCenter(double d, double d2, boolean z) {
        final double d3 = d;
        final double d4 = d2;
        final boolean z2 = z;
        this.fOperationQueue.add(new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder(64);
                sb.append("javascript:setCenter(");
                sb.append(Double.toString(d3));
                sb.append(",");
                sb.append(Double.toString(d4));
                sb.append(",");
                sb.append(Boolean.toString(z2));
                sb.append(")");
                MapView.this.fWebView.loadUrl(sb.toString());
            }
        });
        requestExecuteQueuedOperations();
    }

    public void setCurrentLocationTrackingEnabled(boolean z) {
        if (isLoaded()) {
            this.fWebView.loadUrl("javascript:setCurrentLocationTrackingEnabled(" + Boolean.toString(z) + ")");
        }
        this.fIsCurrentLocationTrackingEnabled = z;
    }

    public void setLayerType(int i, Paint paint) {
        if (Build.VERSION.SDK_INT >= 11) {
            Class<View> cls = View.class;
            try {
                cls.getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(this.fWebView, new Object[]{Integer.valueOf(i), paint});
            } catch (Exception e) {
            }
        }
    }

    public void setMapType(MapType mapType) {
        if (mapType != null) {
            if (isLoaded()) {
                if (mapType.equals(MapType.STANDARD)) {
                    this.fWebView.loadUrl("javascript:showRoadmapView()");
                } else if (mapType.equals(MapType.SATELLITE)) {
                    this.fWebView.loadUrl("javascript:showSatelliteView()");
                } else if (mapType.equals(MapType.HYBRID)) {
                    this.fWebView.loadUrl("javascript:showHybridView()");
                }
            }
            this.fMapType = mapType;
        }
    }

    public void setRegion(double d, double d2, double d3, double d4, boolean z) {
        final double d5 = d;
        final double d6 = d2;
        final double d7 = d3;
        final double d8 = d4;
        final boolean z2 = z;
        this.fOperationQueue.add(new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder(64);
                sb.append("javascript:setRegion(");
                sb.append(Double.toString(d5));
                sb.append(",");
                sb.append(Double.toString(d6));
                sb.append(",");
                sb.append(Double.toString(d7));
                sb.append(",");
                sb.append(Double.toString(d8));
                sb.append(",");
                sb.append(Boolean.toString(z2));
                sb.append(")");
                MapView.this.fWebView.loadUrl(sb.toString());
            }
        });
        requestExecuteQueuedOperations();
    }

    public void setScrollEnabled(boolean z) {
        if (isLoaded()) {
            this.fWebView.loadUrl("javascript:setPanningEnabled(" + Boolean.toString(z) + ")");
        }
        this.fIsScrollEnabled = z;
    }

    public void setVisibility(int i) {
        if (this.fWebView != null && this.fIsMapLoaded) {
            this.fWebView.setVisibility(i);
        }
        super.setVisibility(i);
    }

    public void setZoomEnabled(boolean z) {
        if (isLoaded()) {
            this.fWebView.loadUrl("javascript:setZoomingEnabled(" + Boolean.toString(z) + ")");
        }
        this.fIsZoomEnabled = z;
    }

    public void startAnimation(Animation animation) {
        super.startAnimation(animation);
        if (this.fWebView != null) {
            this.fWebView.startAnimation(animation);
        }
        this.fLoadingIndicatorView.startAnimation(animation);
    }
}
