package com.tapjoy;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.mraid.view.MraidView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressLint({"SetJavaScriptEnabled"})
public class TJAdUnitJSBridge {
    private static final String TAG = "TJAdUnitJSBridge";
    public boolean allowRedirect = true;
    /* access modifiers changed from: private */
    public View bannerView = null;
    /* access modifiers changed from: private */
    public Context context;
    public boolean customClose = false;
    public boolean didLaunchOtherActivity = false;
    /* access modifiers changed from: private */
    public boolean enabled;
    private TJEventData eventData;
    private TJWebViewJSInterface jsBridge;
    /* access modifiers changed from: private */
    public LocationListener locationListener;
    /* access modifiers changed from: private */
    public LocationManager locationManager;
    public String otherActivityCallbackID = null;
    private ProgressDialog progressDialog;
    /* access modifiers changed from: private */
    public TJAdUnitJSBridge self;
    public boolean shouldClose = false;
    /* access modifiers changed from: private */
    public WebView webView;

    @TargetApi(11)
    private class ShowWebView extends AsyncTask<Boolean, Void, Boolean[]> {
        WebView webView;

        public ShowWebView(WebView webView2) {
            this.webView = webView2;
        }

        /* access modifiers changed from: protected */
        public Boolean[] doInBackground(Boolean... boolArr) {
            return boolArr;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean[] boolArr) {
            final boolean booleanValue = boolArr[0].booleanValue();
            final boolean booleanValue2 = boolArr[1].booleanValue();
            ((Activity) TJAdUnitJSBridge.this.context).runOnUiThread(new Runnable() {
                public void run() {
                    if (booleanValue) {
                        ShowWebView.this.webView.setVisibility(0);
                        if (booleanValue2) {
                            if (ShowWebView.this.webView.getParent() instanceof RelativeLayout) {
                                ((RelativeLayout) ShowWebView.this.webView.getParent()).getBackground().setAlpha(0);
                                ((RelativeLayout) ShowWebView.this.webView.getParent()).setBackgroundColor(0);
                            }
                            if (Build.VERSION.SDK_INT >= 11) {
                                ShowWebView.this.webView.setLayerType(1, (Paint) null);
                                return;
                            }
                            return;
                        }
                        if (ShowWebView.this.webView.getParent() instanceof RelativeLayout) {
                            ((RelativeLayout) ShowWebView.this.webView.getParent()).getBackground().setAlpha(MotionEventCompat.ACTION_MASK);
                            ((RelativeLayout) ShowWebView.this.webView.getParent()).setBackgroundColor(-1);
                        }
                        if (Build.VERSION.SDK_INT >= 11) {
                            ShowWebView.this.webView.setLayerType(0, (Paint) null);
                            return;
                        }
                        return;
                    }
                    ShowWebView.this.webView.setVisibility(4);
                    if (ShowWebView.this.webView.getParent() instanceof RelativeLayout) {
                        ((RelativeLayout) ShowWebView.this.webView.getParent()).getBackground().setAlpha(0);
                        ((RelativeLayout) ShowWebView.this.webView.getParent()).setBackgroundColor(0);
                    }
                }
            });
        }
    }

    public TJAdUnitJSBridge(Context context2, WebView webView2, TJEventData tJEventData) {
        TapjoyLog.i(TAG, "creating AdUnit/JS Bridge");
        this.context = context2;
        this.eventData = tJEventData;
        this.self = this;
        this.webView = webView2;
        if (this.webView == null) {
            TapjoyLog.e(TAG, "Error: webView is NULL");
            return;
        }
        this.jsBridge = new TJWebViewJSInterface(this.webView, new TJWebViewJSInterfaceNotifier() {
            public void dispatchMethod(String str, JSONObject jSONObject) {
                if (TJAdUnitJSBridge.this.enabled) {
                    String str2 = null;
                    try {
                        str2 = jSONObject.getString(TJAdUnitConstants.String.CALLBACK_ID);
                    } catch (Exception e) {
                    }
                    try {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        TJAdUnitJSBridge.class.getMethod(str, new Class[]{JSONObject.class, String.class}).invoke(TJAdUnitJSBridge.this.self, new Object[]{jSONObject2, str2});
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        TJAdUnitJSBridge.this.invokeJSCallback(str2, Boolean.FALSE);
                    }
                }
            }
        });
        this.webView.addJavascriptInterface(this.jsBridge, TJAdUnitConstants.JAVASCRIPT_INTERFACE_ID);
        this.enabled = true;
    }

    @SuppressLint({"WorldReadableFiles"})
    private File downloadFileFromURL(String str) {
        String substring = str.substring(str.lastIndexOf(46));
        try {
            InputStream openStream = new URL(str).openStream();
            FileOutputStream openFileOutput = this.context.openFileOutput(TJAdUnitConstants.SHARE_FILENAME + substring, 1);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = openStream.read(bArr, 0, bArr.length);
                if (read >= 0) {
                    openFileOutput.write(bArr, 0, read);
                } else {
                    try {
                        break;
                    } catch (Exception e) {
                    }
                }
            }
            openFileOutput.close();
            openStream.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return new File(this.context.getFilesDir(), TJAdUnitConstants.SHARE_FILENAME + substring);
    }

    public void alert(JSONObject jSONObject, final String str) {
        int i;
        TapjoyLog.i(TAG, "alert_method: " + jSONObject);
        String str2 = BuildConfig.FLAVOR;
        String str3 = BuildConfig.FLAVOR;
        JSONArray jSONArray = null;
        try {
            str2 = jSONObject.getString("title");
            str3 = jSONObject.getString("message");
            jSONArray = jSONObject.getJSONArray(TJAdUnitConstants.String.BUTTONS);
        } catch (Exception e) {
            invokeJSCallback(str, Boolean.FALSE);
            e.printStackTrace();
        }
        AlertDialog create = new AlertDialog.Builder(this.context).setTitle(str2).setMessage(str3).create();
        if (jSONArray == null || jSONArray.length() == 0) {
            invokeJSCallback(str, Boolean.FALSE);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            switch (i2) {
                case 0:
                    i = -2;
                    break;
                case 1:
                    i = -3;
                    break;
                default:
                    i = -1;
                    break;
            }
            try {
                arrayList.add(jSONArray.getString(i2));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            create.setButton(i, (CharSequence) arrayList.get(i2), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    int i2 = 0;
                    switch (i) {
                        case -3:
                            i2 = 1;
                            break;
                        case -2:
                            i2 = 0;
                            break;
                        case -1:
                            i2 = 2;
                            break;
                    }
                    try {
                        TJAdUnitJSBridge.this.invokeJSCallback(str, Integer.valueOf(i2));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        create.show();
    }

    public void cacheAsset(JSONObject jSONObject, String str) {
        String str2 = BuildConfig.FLAVOR;
        Long l = 0L;
        try {
            String string = jSONObject.getString("url");
            try {
                str2 = jSONObject.getString(TapjoyConstants.TJC_EVENT_OFFER_ID);
            } catch (Exception e) {
            }
            try {
                l = Long.valueOf(jSONObject.getLong(TapjoyConstants.TJC_TIME_TO_LIVE));
            } catch (Exception e2) {
            }
            if (TapjoyCache.getInstance() != null) {
                invokeJSCallback(str, TapjoyCache.getInstance().cacheAssetFromURL(string, str2, l.longValue()));
                return;
            }
            invokeJSCallback(str, Boolean.FALSE);
        } catch (Exception e3) {
            TapjoyLog.w(TAG, "Unable to cache video. Invalid parameters.");
            invokeJSCallback(str, Boolean.FALSE);
        }
    }

    public void cachePathForURL(JSONObject jSONObject, String str) {
        try {
            String string = jSONObject.getString("url");
            if (TapjoyCache.getInstance() != null) {
                invokeJSCallback(str, TapjoyCache.getInstance().getPathOfCachedURL(string));
                return;
            }
            invokeJSCallback(str, BuildConfig.FLAVOR);
        } catch (Exception e) {
            invokeJSCallback(str, BuildConfig.FLAVOR);
        }
    }

    public void checkAppInstalled(JSONObject jSONObject, String str) {
        String str2 = BuildConfig.FLAVOR;
        try {
            str2 = jSONObject.getString(TJAdUnitConstants.String.BUNDLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (str2 != null && str2.length() > 0) {
            for (ApplicationInfo applicationInfo : this.context.getPackageManager().getInstalledApplications(0)) {
                if (applicationInfo.packageName.equals(str2)) {
                    invokeJSCallback(str, Boolean.TRUE);
                    return;
                }
            }
        }
        invokeJSCallback(str, Boolean.FALSE);
    }

    public void clearCache(JSONObject jSONObject, String str) {
        if (TapjoyCache.getInstance() != null) {
            TapjoyCache.getInstance().clearTapjoyCache();
            invokeJSCallback(str, Boolean.TRUE);
            return;
        }
        invokeJSCallback(str, Boolean.FALSE);
    }

    public void closeRequested() {
        this.shouldClose = true;
        invokeJSAdunitMethod(TJAdUnitConstants.String.CLOSE_REQUESTED, new Object[0]);
    }

    public void destroy() {
        if (this.locationListener != null && this.locationManager != null) {
            this.locationManager.removeUpdates(this.locationListener);
            this.locationManager = null;
            this.locationListener = null;
        }
    }

    public void disable() {
        this.enabled = false;
    }

    public void dismiss(JSONObject jSONObject, String str) {
        if (this.context instanceof TJAdUnitView) {
            invokeJSCallback(str, Boolean.TRUE);
            ((Activity) this.context).finish();
        }
    }

    public void display() {
        invokeJSAdunitMethod("display", new Object[0]);
    }

    public void displayOffers(JSONObject jSONObject, String str) {
        String str2 = null;
        try {
            str2 = jSONObject.getString(TJAdUnitConstants.String.CURRENCY_ID);
        } catch (Exception e) {
            TapjoyLog.w(TAG, "no currencyID for showOfferWall");
        }
        new TJCOffers(this.context).showOffersWithCurrencyID(str2, false, this.eventData, str, (TapjoyOffersNotifier) null);
    }

    public void displayRichMedia(final JSONObject jSONObject, String str) {
        boolean z = false;
        String str2 = null;
        try {
            z = jSONObject.getBoolean(TJAdUnitConstants.String.INLINE);
        } catch (Exception e) {
        }
        try {
            str2 = jSONObject.getString("html");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (str2 == null) {
            invokeJSCallback(str, Boolean.FALSE);
        } else if (z) {
            ((Activity) this.context).runOnUiThread(new Runnable() {
                public void run() {
                    String str = null;
                    try {
                        str = jSONObject.getString("html");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!(TJAdUnitJSBridge.this.bannerView == null || TJAdUnitJSBridge.this.bannerView.getParent() == null)) {
                        ((ViewGroup) TJAdUnitJSBridge.this.bannerView.getParent()).removeView(TJAdUnitJSBridge.this.bannerView);
                    }
                    MraidView mraidView = new MraidView(TJAdUnitJSBridge.this.context);
                    TJAdUnitJSBridge.this.webView.getSettings().setJavaScriptEnabled(true);
                    mraidView.setPlacementType(MraidView.PLACEMENT_TYPE.INLINE);
                    mraidView.setLayoutParams(new ViewGroup.LayoutParams(640, 100));
                    mraidView.setInitialScale(100);
                    mraidView.setBackgroundColor(0);
                    mraidView.loadDataWithBaseURL((String) null, str, "text/html", "utf-8", (String) null);
                    int width = ((WindowManager) ((Activity) TJAdUnitJSBridge.this.context).getSystemService("window")).getDefaultDisplay().getWidth();
                    View unused = TJAdUnitJSBridge.this.bannerView = TapjoyUtil.scaleDisplayAd(mraidView, width);
                    ((Activity) TJAdUnitJSBridge.this.context).addContentView(TJAdUnitJSBridge.this.bannerView, new ViewGroup.LayoutParams(width, (int) (100.0d * (((double) width) / 640.0d))));
                }
            });
        } else {
            Intent intent = new Intent(this.context, TJAdUnitView.class);
            intent.putExtra(TJAdUnitConstants.EXTRA_TJEVENT, this.eventData);
            intent.putExtra(TJAdUnitConstants.EXTRA_VIEW_TYPE, 3);
            intent.putExtra("html", str2);
            intent.putExtra(TJAdUnitConstants.EXTRA_BASE_URL, TapjoyConnectCore.getHostURL());
            intent.putExtra(TJAdUnitConstants.EXTRA_CALLBACK_ID, str);
            ((Activity) this.context).startActivityForResult(intent, 0);
        }
    }

    public void displayStoreURL(JSONObject jSONObject, String str) {
        displayURL(jSONObject, str);
    }

    public void displayURL(JSONObject jSONObject, String str) {
        try {
            String string = jSONObject.getString("url");
            this.didLaunchOtherActivity = true;
            this.otherActivityCallbackID = str;
            ((Activity) this.context).startActivity(new Intent("android.intent.action.VIEW", Uri.parse(string)));
        } catch (Exception e) {
            invokeJSCallback(str, Boolean.TRUE);
            e.printStackTrace();
        }
    }

    public void displayVideo(JSONObject jSONObject, String str) {
        TapjoyCachedAssetData cachedDataForURL;
        String str2 = BuildConfig.FLAVOR;
        Boolean valueOf = Boolean.valueOf(jSONObject.optBoolean(TJAdUnitConstants.String.ALLOW_BACK_BUTTON, true));
        HashMap hashMap = new HashMap();
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(TJAdUnitConstants.String.VIDEO_TRACKING_URLS);
            if (jSONObject2 != null) {
                Iterator<String> keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    try {
                        hashMap.put(next, jSONObject2.getString(next));
                    } catch (Exception e) {
                        TapjoyLog.i(TAG, "no tracking url for " + jSONObject2.getString(next));
                    }
                }
            }
        } catch (Exception e2) {
            TapjoyLog.i(TAG, "no video tracking urls");
        }
        try {
            str2 = jSONObject.getString(TJAdUnitConstants.String.CANCEL_MESSAGE);
        } catch (Exception e3) {
            TapjoyLog.w(TAG, "no cancelMessage");
        }
        String optString = jSONObject.optString(TJAdUnitConstants.String.VIDEO_MESSAGE, BuildConfig.FLAVOR);
        try {
            String string = jSONObject.getString("url");
            Intent intent = new Intent(this.context, TapjoyVideoView.class);
            if (!(TapjoyCache.getInstance() == null || (cachedDataForURL = TapjoyCache.getInstance().getCachedDataForURL(string)) == null)) {
                intent.putExtra(TapjoyConstants.EXTRA_CACHED_VIDEO_LOCATION, cachedDataForURL.getLocalFilePath());
            }
            intent.putExtra(TapjoyConstants.EXTRA_VIDEO_URL, string);
            intent.putExtra(TapjoyConstants.EXTRA_VIDEO_MESSAGE, optString);
            intent.putExtra(TapjoyConstants.EXTRA_VIDEO_CANCEL_MESSAGE, str2);
            intent.putExtra(TapjoyConstants.EXTRA_VIDEO_SHOULD_DISMISS, true);
            intent.putExtra(TJAdUnitConstants.EXTRA_CALLBACK_ID, str);
            intent.putExtra(TapjoyConstants.EXTRA_VIDEO_TRACKING_URLS, hashMap);
            intent.putExtra(TapjoyConstants.EXTRA_VIDEO_ALLOW_BACK_BUTTON, valueOf);
            ((Activity) this.context).startActivityForResult(intent, 0);
        } catch (Exception e4) {
            invokeJSCallback(str, Boolean.FALSE);
            e4.printStackTrace();
        }
    }

    public void eventOptimizationCallback(JSONObject jSONObject, String str) {
        try {
            TJEventOptimizer.getInstance().eventOptimizationCallback(jSONObject.getString(TJAdUnitConstants.String.EVENT_TOKEN), Boolean.valueOf(jSONObject.getBoolean("result")).booleanValue());
            invokeJSCallback(str, Boolean.TRUE);
        } catch (Exception e) {
            invokeJSCallback(str, Boolean.FALSE);
            e.printStackTrace();
        }
    }

    public void getCachedAssets(JSONObject jSONObject, String str) {
        if (TapjoyCache.getInstance() != null) {
            invokeJSCallback(str, TapjoyCache.getInstance().cachedAssetsToJSON());
            return;
        }
        invokeJSCallback(str, BuildConfig.FLAVOR);
    }

    public void getLocation(JSONObject jSONObject, String str) {
        float f = 100.0f;
        boolean z = false;
        try {
            f = Float.valueOf(jSONObject.getString(TJAdUnitConstants.String.GPS_ACCURACY)).floatValue();
        } catch (Exception e) {
        }
        try {
            z = Boolean.valueOf(jSONObject.getString(TJAdUnitConstants.String.ENABLED)).booleanValue();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.locationManager = (LocationManager) this.context.getSystemService("location");
        String bestProvider = this.locationManager.getBestProvider(new Criteria(), false);
        if (this.locationListener == null) {
            this.locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    if (TJAdUnitJSBridge.this.context == null || TJAdUnitJSBridge.this.webView == null) {
                        if (TJAdUnitJSBridge.this.locationManager != null && TJAdUnitJSBridge.this.locationListener != null) {
                            TapjoyLog.i(TJAdUnitJSBridge.TAG, "stopping updates");
                            TJAdUnitJSBridge.this.locationManager.removeUpdates(TJAdUnitJSBridge.this.locationListener);
                        }
                    } else if (location != null) {
                        HashMap hashMap = new HashMap();
                        hashMap.put(TJAdUnitConstants.String.LAT, Double.valueOf(location.getLatitude()));
                        hashMap.put(TJAdUnitConstants.String.LONG, Double.valueOf(location.getLongitude()));
                        hashMap.put(TJAdUnitConstants.String.ALTITUDE, Double.valueOf(location.getAltitude()));
                        hashMap.put("timestamp", Long.valueOf(location.getTime()));
                        hashMap.put(TJAdUnitConstants.String.SPEED, Float.valueOf(location.getSpeed()));
                        hashMap.put(TJAdUnitConstants.String.COURSE, Float.valueOf(location.getBearing()));
                        TJAdUnitJSBridge.this.invokeJSAdunitMethod(TJAdUnitConstants.String.LOCATION_UPDATED, (Map<String, Object>) hashMap);
                    }
                }

                public void onProviderDisabled(String str) {
                }

                public void onProviderEnabled(String str) {
                }

                public void onStatusChanged(String str, int i, Bundle bundle) {
                }
            };
        }
        if (!z) {
            if (!(this.locationManager == null || this.locationListener == null)) {
                this.locationManager.removeUpdates(this.locationListener);
            }
            invokeJSCallback(str, Boolean.TRUE);
        } else if (bestProvider != null) {
            this.locationManager.requestLocationUpdates(bestProvider, 0, f, this.locationListener);
            invokeJSCallback(str, Boolean.TRUE);
        } else {
            invokeJSCallback(str, Boolean.FALSE);
        }
    }

    public void invokeJSAdunitMethod(String str, Map<String, Object> map) {
        this.jsBridge.callback((Map<?, ?>) map, str, (String) null);
    }

    public void invokeJSAdunitMethod(String str, Object... objArr) {
        this.jsBridge.callback((ArrayList<?>) new ArrayList(Arrays.asList(objArr)), str, (String) null);
    }

    public void invokeJSCallback(String str, Map<String, Object> map) {
        this.jsBridge.callback((Map<?, ?>) map, BuildConfig.FLAVOR, str);
    }

    public void invokeJSCallback(String str, Object... objArr) {
        this.jsBridge.callback((ArrayList<?>) new ArrayList(Arrays.asList(objArr)), BuildConfig.FLAVOR, str);
    }

    public void log(JSONObject jSONObject, String str) {
        try {
            TapjoyLog.i(TAG, jSONObject.getString("message"));
            invokeJSCallback(str, Boolean.TRUE);
        } catch (Exception e) {
            invokeJSCallback(str, Boolean.FALSE);
            e.printStackTrace();
        }
    }

    @TargetApi(19)
    public void nativeEval(final JSONObject jSONObject, final String str) {
        ((Activity) this.context).runOnUiThread(new Runnable() {
            public void run() {
                try {
                    if (Build.VERSION.SDK_INT >= 19) {
                        TJAdUnitJSBridge.this.webView.evaluateJavascript(jSONObject.getString(TJAdUnitConstants.String.COMMAND), (ValueCallback) null);
                    } else {
                        TJAdUnitJSBridge.this.webView.loadUrl("javascript:" + jSONObject.getString(TJAdUnitConstants.String.COMMAND));
                    }
                    TJAdUnitJSBridge.this.invokeJSCallback(str, Boolean.TRUE);
                } catch (Exception e) {
                    TJAdUnitJSBridge.this.invokeJSCallback(str, Boolean.FALSE);
                }
            }
        });
    }

    public void openApp(JSONObject jSONObject, String str) {
        try {
            this.context.startActivity(this.context.getPackageManager().getLaunchIntentForPackage(jSONObject.getString(TJAdUnitConstants.String.BUNDLE)));
            invokeJSCallback(str, Boolean.TRUE);
        } catch (Exception e) {
            invokeJSCallback(str, Boolean.FALSE);
            e.printStackTrace();
        }
    }

    public void present(JSONObject jSONObject, String str) {
        try {
            boolean z = false;
            Boolean valueOf = Boolean.valueOf(jSONObject.getString(TJAdUnitConstants.String.VISIBLE));
            try {
                z = Boolean.valueOf(jSONObject.getString(TJAdUnitConstants.String.TRANSPARENT));
            } catch (Exception e) {
            }
            try {
                this.customClose = Boolean.valueOf(jSONObject.getString(TJAdUnitConstants.String.CUSTOM_CLOSE)).booleanValue();
            } catch (Exception e2) {
            }
            new ShowWebView(this.webView).execute(new Boolean[]{valueOf, z});
            invokeJSCallback(str, Boolean.TRUE);
        } catch (Exception e3) {
            invokeJSCallback(str, Boolean.FALSE);
            e3.printStackTrace();
        }
    }

    public void removeAssetFromCache(JSONObject jSONObject, String str) {
        try {
            String string = jSONObject.getString("url");
            if (TapjoyCache.getInstance() != null) {
                invokeJSCallback(str, Boolean.valueOf(TapjoyCache.getInstance().removeAssetFromCache(string)));
                return;
            }
            invokeJSCallback(str, Boolean.FALSE);
        } catch (Exception e) {
            TapjoyLog.w(TAG, "Unable to cache video. Invalid parameters.");
            invokeJSCallback(str, Boolean.FALSE);
        }
    }

    public void sendActionCallback(JSONObject jSONObject, final String str) {
        TJEventRequest tJEventRequest = new TJEventRequest();
        String str2 = null;
        try {
            str2 = jSONObject.getString("type");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tJEventRequest.quantity = Integer.valueOf(jSONObject.getString("quantity")).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            tJEventRequest.identifier = jSONObject.getString(TJAdUnitConstants.String.IDENTIFIER);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (str2 == null || tJEventRequest.identifier == null) {
            TapjoyLog.i(TAG, "sendActionCallback: null type or identifier");
            invokeJSCallback(str, Boolean.FALSE);
            return;
        }
        if (str2.equals(TJAdUnitConstants.String.CURRENCY)) {
            tJEventRequest.type = 3;
        } else if (str2.equals(TJAdUnitConstants.String.IN_APP_PURCHASE)) {
            tJEventRequest.type = 1;
        } else if (str2.equals(TJAdUnitConstants.String.NAVIGATION)) {
            tJEventRequest.type = 4;
        } else if (str2.equals(TJAdUnitConstants.String.VIRGUAL_GOOD)) {
            tJEventRequest.type = 2;
        }
        if (tJEventRequest.type == 0) {
            TapjoyLog.i(TAG, "unknown type: " + str2);
            invokeJSCallback(str, Boolean.FALSE);
            return;
        }
        tJEventRequest.callback = new TJEventRequestCallback() {
            public void cancelled() {
                TJAdUnitJSBridge.this.invokeJSCallback(str, Boolean.FALSE);
            }

            public void completed() {
                TJAdUnitJSBridge.this.invokeJSCallback(str, Boolean.TRUE);
            }
        };
        TJEvent tJEvent = TJEventManager.get(this.eventData.guid);
        if (tJEvent != null) {
            tJEvent.getCallback().didRequestAction(tJEvent, tJEventRequest);
        }
    }

    public void setAllowRedirect(JSONObject jSONObject, String str) {
        boolean z = true;
        try {
            z = jSONObject.getBoolean(TJAdUnitConstants.String.ENABLED);
        } catch (Exception e) {
        }
        this.allowRedirect = z;
        invokeJSCallback(str, Boolean.TRUE);
    }

    public void setCloseButtonVisible(JSONObject jSONObject, String str) {
        TapjoyLog.i(TAG, "setCloseButtonVisible_method: " + jSONObject);
        try {
            final boolean z = jSONObject.getBoolean(TJAdUnitConstants.String.VISIBLE);
            ((Activity) this.context).runOnUiThread(new Runnable() {
                public void run() {
                    ((TJAdUnitView) TJAdUnitJSBridge.this.context).setCloseButtonVisibility(z);
                }
            });
            invokeJSCallback(str, Boolean.TRUE);
        } catch (Exception e) {
            invokeJSCallback(str, Boolean.FALSE);
            e.printStackTrace();
        }
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public void setEventPreloadLimit(JSONObject jSONObject, String str) {
        if (TapjoyCache.getInstance() != null) {
            try {
                TapjoyCache.getInstance().setEventPreloadLimit(jSONObject.getInt(TJAdUnitConstants.String.TJC_CACHE_EVENT_PRELOAD_LIMIT));
                invokeJSCallback(str, Boolean.TRUE);
            } catch (Exception e) {
                TapjoyLog.w(TAG, "Unable to set Tapjoy cache's event preload limit. Invalid parameters.");
                invokeJSCallback(str, Boolean.FALSE);
            }
        } else {
            invokeJSCallback(str, Boolean.FALSE);
        }
    }

    public void setSpinnerVisible(JSONObject jSONObject, String str) {
        String str2 = TJAdUnitConstants.SPINNER_TITLE;
        String str3 = BuildConfig.FLAVOR;
        try {
            boolean z = jSONObject.getBoolean(TJAdUnitConstants.String.VISIBLE);
            try {
                str2 = jSONObject.getString("title");
                str3 = jSONObject.getString("message");
            } catch (Exception e) {
            }
            if (z) {
                this.progressDialog = ProgressDialog.show(this.context, str2, str3);
            } else if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
            invokeJSCallback(str, Boolean.TRUE);
        } catch (Exception e2) {
            invokeJSCallback(str, Boolean.FALSE);
            e2.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x009e A[Catch:{ Exception -> 0x00e9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00cc A[EDGE_INSN: B:50:0x00cc->B:30:0x00cc ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void share(org.json.JSONObject r19, java.lang.String r20) {
        /*
            r18 = this;
            java.lang.String r15 = "network"
            r0 = r19
            java.lang.String r12 = r0.getString(r15)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = "message"
            r0 = r19
            java.lang.String r11 = r0.getString(r15)     // Catch:{ Exception -> 0x00e9 }
            r6 = 0
            r8 = 0
            r10 = 0
            r3 = 0
            android.content.Intent r14 = new android.content.Intent     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = "android.intent.action.SEND"
            r14.<init>(r15)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = "imageURL"
            r0 = r19
            java.lang.String r6 = r0.getString(r15)     // Catch:{ Exception -> 0x00df }
        L_0x0023:
            java.lang.String r15 = "linkURL"
            r0 = r19
            java.lang.String r8 = r0.getString(r15)     // Catch:{ Exception -> 0x00fe }
        L_0x002b:
            if (r6 == 0) goto L_0x004c
            r0 = r18
            java.io.File r9 = r0.downloadFileFromURL(r6)     // Catch:{ Exception -> 0x00e9 }
            if (r9 == 0) goto L_0x004c
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e9 }
            r15.<init>()     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r16 = "file://"
            java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r16 = r9.getAbsolutePath()     // Catch:{ Exception -> 0x00e9 }
            java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r10 = r15.toString()     // Catch:{ Exception -> 0x00e9 }
        L_0x004c:
            if (r8 == 0) goto L_0x0065
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e9 }
            r15.<init>()     // Catch:{ Exception -> 0x00e9 }
            java.lang.StringBuilder r15 = r15.append(r11)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r16 = "\n"
            java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ Exception -> 0x00e9 }
            java.lang.StringBuilder r15 = r15.append(r8)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r11 = r15.toString()     // Catch:{ Exception -> 0x00e9 }
        L_0x0065:
            java.lang.String r15 = "android.intent.extra.TEXT"
            r14.putExtra(r15, r11)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = "facebook"
            boolean r15 = r12.equals(r15)     // Catch:{ Exception -> 0x00e9 }
            if (r15 == 0) goto L_0x010f
            if (r6 == 0) goto L_0x0108
            if (r10 == 0) goto L_0x0108
            java.lang.String r15 = "image/*"
            r14.setType(r15)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = "android.intent.extra.STREAM"
            android.net.Uri r16 = android.net.Uri.parse(r10)     // Catch:{ Exception -> 0x00e9 }
            r14.putExtra(r15, r16)     // Catch:{ Exception -> 0x00e9 }
        L_0x0084:
            r0 = r18
            android.content.Context r15 = r0.context     // Catch:{ Exception -> 0x00e9 }
            android.content.pm.PackageManager r15 = r15.getPackageManager()     // Catch:{ Exception -> 0x00e9 }
            r16 = 0
            r0 = r16
            java.util.List r13 = r15.queryIntentActivities(r14, r0)     // Catch:{ Exception -> 0x00e9 }
            java.util.Iterator r5 = r13.iterator()     // Catch:{ Exception -> 0x00e9 }
        L_0x0098:
            boolean r15 = r5.hasNext()     // Catch:{ Exception -> 0x00e9 }
            if (r15 == 0) goto L_0x00cc
            java.lang.Object r7 = r5.next()     // Catch:{ Exception -> 0x00e9 }
            android.content.pm.ResolveInfo r7 = (android.content.pm.ResolveInfo) r7     // Catch:{ Exception -> 0x00e9 }
            android.content.pm.ActivityInfo r15 = r7.activityInfo     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = r15.packageName     // Catch:{ Exception -> 0x00e9 }
            java.util.Locale r16 = java.util.Locale.ENGLISH     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = r15.toLowerCase(r16)     // Catch:{ Exception -> 0x00e9 }
            boolean r15 = r15.contains(r12)     // Catch:{ Exception -> 0x00e9 }
            if (r15 != 0) goto L_0x00c4
            android.content.pm.ActivityInfo r15 = r7.activityInfo     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = r15.name     // Catch:{ Exception -> 0x00e9 }
            java.util.Locale r16 = java.util.Locale.ENGLISH     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = r15.toLowerCase(r16)     // Catch:{ Exception -> 0x00e9 }
            boolean r15 = r15.contains(r12)     // Catch:{ Exception -> 0x00e9 }
            if (r15 == 0) goto L_0x0098
        L_0x00c4:
            android.content.pm.ActivityInfo r15 = r7.activityInfo     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = r15.packageName     // Catch:{ Exception -> 0x00e9 }
            r14.setPackage(r15)     // Catch:{ Exception -> 0x00e9 }
            r3 = 1
        L_0x00cc:
            if (r3 != 0) goto L_0x012b
            r15 = 1
            java.lang.Object[] r15 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x00e9 }
            r16 = 0
            java.lang.Boolean r17 = java.lang.Boolean.FALSE     // Catch:{ Exception -> 0x00e9 }
            r15[r16] = r17     // Catch:{ Exception -> 0x00e9 }
            r0 = r18
            r1 = r20
            r0.invokeJSCallback((java.lang.String) r1, (java.lang.Object[]) r15)     // Catch:{ Exception -> 0x00e9 }
        L_0x00de:
            return
        L_0x00df:
            r2 = move-exception
            java.lang.String r15 = "TJAdUnitJSBridge"
            java.lang.String r16 = "no imageURL"
            com.tapjoy.TapjoyLog.i(r15, r16)     // Catch:{ Exception -> 0x00e9 }
            goto L_0x0023
        L_0x00e9:
            r2 = move-exception
            r15 = 1
            java.lang.Object[] r15 = new java.lang.Object[r15]
            r16 = 0
            java.lang.Boolean r17 = java.lang.Boolean.FALSE
            r15[r16] = r17
            r0 = r18
            r1 = r20
            r0.invokeJSCallback((java.lang.String) r1, (java.lang.Object[]) r15)
            r2.printStackTrace()
            goto L_0x00de
        L_0x00fe:
            r2 = move-exception
            java.lang.String r15 = "TJAdUnitJSBridge"
            java.lang.String r16 = "no linkURL"
            com.tapjoy.TapjoyLog.i(r15, r16)     // Catch:{ Exception -> 0x00e9 }
            goto L_0x002b
        L_0x0108:
            java.lang.String r15 = "text/plain"
            r14.setType(r15)     // Catch:{ Exception -> 0x00e9 }
            goto L_0x0084
        L_0x010f:
            java.lang.String r15 = "twitter"
            boolean r15 = r12.equals(r15)     // Catch:{ Exception -> 0x00e9 }
            if (r15 == 0) goto L_0x0084
            java.lang.String r15 = "*/*"
            r14.setType(r15)     // Catch:{ Exception -> 0x00e9 }
            if (r6 == 0) goto L_0x0084
            if (r10 == 0) goto L_0x0084
            java.lang.String r15 = "android.intent.extra.STREAM"
            android.net.Uri r16 = android.net.Uri.parse(r10)     // Catch:{ Exception -> 0x00e9 }
            r14.putExtra(r15, r16)     // Catch:{ Exception -> 0x00e9 }
            goto L_0x0084
        L_0x012b:
            r15 = 1
            r0 = r18
            r0.didLaunchOtherActivity = r15     // Catch:{ Exception -> 0x00e9 }
            r0 = r20
            r1 = r18
            r1.otherActivityCallbackID = r0     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r15 = "Select"
            android.content.Intent r4 = android.content.Intent.createChooser(r14, r15)     // Catch:{ Exception -> 0x00e9 }
            r0 = r18
            android.content.Context r15 = r0.context     // Catch:{ Exception -> 0x00e9 }
            android.app.Activity r15 = (android.app.Activity) r15     // Catch:{ Exception -> 0x00e9 }
            r15.startActivity(r4)     // Catch:{ Exception -> 0x00e9 }
            goto L_0x00de
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tapjoy.TJAdUnitJSBridge.share(org.json.JSONObject, java.lang.String):void");
    }

    public void shouldClose(JSONObject jSONObject, String str) {
        try {
            if (Boolean.valueOf(jSONObject.getString(TJAdUnitConstants.String.CLOSE)).booleanValue() && (this.context instanceof TJAdUnitView)) {
                ((Activity) this.context).finish();
            }
            invokeJSCallback(str, Boolean.TRUE);
        } catch (Exception e) {
            invokeJSCallback(str, Boolean.FALSE);
            ((Activity) this.context).finish();
            e.printStackTrace();
        }
        this.shouldClose = false;
    }

    public void triggerEvent(JSONObject jSONObject, String str) {
        try {
            String string = jSONObject.getString(TJAdUnitConstants.String.TRIGGERED_EVENT_NAME);
            if (string.equals(TJAdUnitConstants.String.VIDEO_START)) {
                TapjoyVideo.videoNotifierStart();
            } else if (string.equals(TJAdUnitConstants.String.VIDEO_COMPLETE)) {
                TapjoyVideo.videoNotifierComplete();
            } else if (string.equals("error")) {
                TapjoyVideo.videoNotifierError(3);
            }
        } catch (Exception e) {
            TapjoyLog.w(TAG, "Unable to triggerEvent. No event name.");
        }
    }
}
