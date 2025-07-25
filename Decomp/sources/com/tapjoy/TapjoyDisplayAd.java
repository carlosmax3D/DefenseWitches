package com.tapjoy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.widget.LinearLayout;
import com.tapjoy.mraid.listener.MraidViewListener;
import com.tapjoy.mraid.view.MraidView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import twitter4j.HttpResponseCode;

public class TapjoyDisplayAd {
    private static final String TAG = "Banner Ad";
    /* access modifiers changed from: private */
    public static int bannerHeight;
    /* access modifiers changed from: private */
    public static int bannerWidth;
    /* access modifiers changed from: private */
    public static TapjoyDisplayAdNotifier displayAdNotifier;
    private static String displayAdSize;
    public static Map<String, String> displayAdURLParams;
    /* access modifiers changed from: private */
    public static String htmlData;
    /* access modifiers changed from: private */
    public static String lastCurrencyID;
    /* access modifiers changed from: private */
    public static TapjoyURLConnection tapjoyURLConnection = null;
    /* access modifiers changed from: private */
    public Activity activityContext;
    View adView;
    /* access modifiers changed from: private */
    public boolean autoRefresh;
    long elapsed_time;
    Bitmap lastAd;
    Timer resumeTimer;
    Timer timer;
    MraidView webView;

    private class CheckForResumeTimer extends TimerTask {
        private CheckForResumeTimer() {
        }

        public void run() {
            TapjoyDisplayAd.this.elapsed_time += 10000;
            TapjoyLog.i(TapjoyDisplayAd.TAG, "banner elapsed_time: " + TapjoyDisplayAd.this.elapsed_time + " (" + ((TapjoyDisplayAd.this.elapsed_time / 1000) / 60) + "m " + ((TapjoyDisplayAd.this.elapsed_time / 1000) % 60) + "s)");
            if (TapjoyDisplayAd.this.adView == null) {
                cancel();
                return;
            }
            TapjoyLog.i(TapjoyDisplayAd.TAG, "adView.isShown: " + TapjoyDisplayAd.this.adView.isShown());
            if (TapjoyDisplayAd.this.adView.isShown() && TapjoyConnectCore.getInstance() != null) {
                TapjoyLog.i(TapjoyDisplayAd.TAG, "call connect");
                TapjoyConnectCore.getInstance().callConnect();
                cancel();
            }
            if (TapjoyDisplayAd.this.elapsed_time >= TapjoyConstants.RESUME_TOTAL_TIME) {
                cancel();
            }
        }
    }

    private class GetBannerAdTask extends AsyncTask<Object, Void, TapjoyHttpURLResponse> {
        private GetBannerAdTask() {
        }

        /* access modifiers changed from: protected */
        public TapjoyHttpURLResponse doInBackground(Object... objArr) {
            return TapjoyDisplayAd.tapjoyURLConnection.getResponseFromURL(objArr[0], (Map<String, String>) objArr[1]);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(TapjoyHttpURLResponse tapjoyHttpURLResponse) {
            if (tapjoyHttpURLResponse != null) {
                switch (tapjoyHttpURLResponse.statusCode) {
                    case HttpResponseCode.OK /*200*/:
                        String unused = TapjoyDisplayAd.htmlData = tapjoyHttpURLResponse.response;
                        if (TapjoyDisplayAd.htmlData == null) {
                            TapjoyLog.d(TapjoyDisplayAd.TAG, "unexpected 200 response with no content");
                            TapjoyDisplayAd.displayAdNotifier.getDisplayAdResponseFailed("No ad to display.");
                            return;
                        }
                        TapjoyDisplayAd.this.webView.getSettings().setJavaScriptEnabled(true);
                        TapjoyDisplayAd.this.webView.setPlacementType(MraidView.PLACEMENT_TYPE.INLINE);
                        TapjoyLog.i(TapjoyDisplayAd.TAG, "response: " + tapjoyHttpURLResponse.response);
                        TapjoyDisplayAd.this.webView.setLayoutParams(new LinearLayout.LayoutParams(TapjoyDisplayAd.bannerWidth, TapjoyDisplayAd.bannerHeight));
                        TapjoyDisplayAd.this.webView.setInitialScale(100);
                        TapjoyDisplayAd.this.webView.setBackgroundColor(0);
                        TapjoyDisplayAd.this.webView.loadDataWithBaseURL((String) null, tapjoyHttpURLResponse.response, "text/html", "utf-8", (String) null);
                        TapjoyLog.i(TapjoyDisplayAd.TAG, "isMraid: " + TapjoyDisplayAd.this.webView.isMraid());
                        if (!TapjoyDisplayAd.this.webView.isMraid()) {
                            TapjoyDisplayAd.this.webView.setListener(new MraidViewListener() {
                                public boolean onClose() {
                                    return false;
                                }

                                @TargetApi(8)
                                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                                    return false;
                                }

                                public boolean onEventFired() {
                                    return false;
                                }

                                public boolean onExpand() {
                                    return false;
                                }

                                public boolean onExpandClose() {
                                    return false;
                                }

                                public void onPageFinished(WebView webView, String str) {
                                }

                                public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                                }

                                public boolean onReady() {
                                    return false;
                                }

                                public void onReceivedError(WebView webView, int i, String str, String str2) {
                                }

                                public boolean onResize() {
                                    return false;
                                }

                                public boolean onResizeClose() {
                                    return false;
                                }

                                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                                    String str2 = null;
                                    try {
                                        str2 = new URL(TapjoyConfig.TJC_SERVICE_URL).getHost();
                                    } catch (MalformedURLException e) {
                                    }
                                    TapjoyLog.i(TapjoyDisplayAd.TAG, "shouldOverrideUrlLoading: " + str + " with host " + str2);
                                    if ((str2 == null || !str.contains(str2)) && !str.contains(TapjoyConstants.TJC_YOUTUBE_AD_PARAM)) {
                                        TapjoyDisplayAd.this.activityContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                                    } else {
                                        TapjoyLog.i(TapjoyDisplayAd.TAG, "Open redirecting URL = [" + str + "]");
                                        ((MraidView) webView).loadUrlStandard(str);
                                    }
                                    if (TapjoyDisplayAd.this.resumeTimer != null) {
                                        TapjoyDisplayAd.this.resumeTimer.cancel();
                                    }
                                    TapjoyDisplayAd.this.elapsed_time = 0;
                                    TapjoyDisplayAd.this.resumeTimer = new Timer();
                                    TapjoyDisplayAd.this.resumeTimer.schedule(new CheckForResumeTimer(), 10000, 10000);
                                    return true;
                                }
                            });
                        }
                        if (TapjoyDisplayAd.this.adView != null) {
                            TapjoyDisplayAd.this.lastAd = TapjoyUtil.createBitmapFromView(TapjoyDisplayAd.this.adView);
                        }
                        TapjoyDisplayAd.this.adView = TapjoyDisplayAd.this.webView;
                        TapjoyDisplayAd.displayAdNotifier.getDisplayAdResponse(TapjoyDisplayAd.this.adView);
                        if (TapjoyDisplayAd.this.timer != null) {
                            TapjoyDisplayAd.this.timer.cancel();
                            TapjoyDisplayAd.this.timer = null;
                        }
                        if (TapjoyDisplayAd.this.autoRefresh && TapjoyDisplayAd.this.timer == null) {
                            TapjoyLog.i(TapjoyDisplayAd.TAG, "will refresh banner ad in 60000ms...");
                            TapjoyDisplayAd.this.timer = new Timer();
                            TapjoyDisplayAd.this.timer.schedule(new RefreshTimer(), 60000);
                            return;
                        }
                        return;
                    default:
                        TapjoyDisplayAd.displayAdNotifier.getDisplayAdResponseFailed("No ad to display.");
                        return;
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Void... voidArr) {
        }
    }

    private class RefreshTimer extends TimerTask {
        private RefreshTimer() {
        }

        public void run() {
            if (TapjoyDisplayAd.this.webView.getState().equals(MraidView.VIEW_STATE.DEFAULT.toString().toLowerCase(Locale.ENGLISH))) {
                TapjoyLog.i(TapjoyDisplayAd.TAG, "refreshing banner ad...");
                TapjoyDisplayAd.this.getDisplayAd(TapjoyDisplayAd.this.activityContext, TapjoyDisplayAd.lastCurrencyID, TapjoyDisplayAd.displayAdNotifier);
                TapjoyDisplayAd.this.timer.cancel();
                TapjoyDisplayAd.this.timer = null;
                return;
            }
            TapjoyLog.i(TapjoyDisplayAd.TAG, "ad is not in default state.  will try refreshing again in 60000s...");
            TapjoyDisplayAd.this.timer.cancel();
            TapjoyDisplayAd.this.timer = null;
            TapjoyDisplayAd.this.timer = new Timer();
            TapjoyDisplayAd.this.timer.schedule(new RefreshTimer(), 60000);
        }
    }

    public TapjoyDisplayAd(Context context) {
        setDisplayAdSize("640x100");
        tapjoyURLConnection = new TapjoyURLConnection();
    }

    public static String getHtmlString() {
        return htmlData;
    }

    public void enableAutoRefresh(boolean z) {
        this.autoRefresh = z;
    }

    public void getDisplayAd(Activity activity, TapjoyDisplayAdNotifier tapjoyDisplayAdNotifier) {
        TapjoyLog.i(TAG, "Get Banner Ad");
        getDisplayAd(activity, (String) null, tapjoyDisplayAdNotifier);
    }

    public void getDisplayAd(Activity activity, String str, TapjoyDisplayAdNotifier tapjoyDisplayAdNotifier) {
        TapjoyLog.i(TAG, "Get Banner Ad, currencyID: " + str);
        displayAdNotifier = tapjoyDisplayAdNotifier;
        if (activity == null) {
            Log.e(TAG, "getDisplayAd must take an Activity context");
            if (displayAdNotifier != null) {
                displayAdNotifier.getDisplayAdResponseFailed("getDisplayAd must take an Activity context");
                return;
            }
            return;
        }
        this.activityContext = activity;
        this.activityContext.runOnUiThread(new Runnable() {
            public void run() {
                TapjoyDisplayAd.this.webView = new MraidView(TapjoyDisplayAd.this.activityContext);
            }
        });
        lastCurrencyID = str;
        displayAdURLParams = TapjoyConnectCore.getURLParams();
        TapjoyUtil.safePut(displayAdURLParams, TapjoyConstants.TJC_DISPLAY_AD_SIZE, displayAdSize, true);
        TapjoyUtil.safePut(displayAdURLParams, TapjoyConstants.TJC_CURRENCY_ID, str, true);
        new GetBannerAdTask().execute(new Object[]{TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_DISPLAY_AD_URL_PATH, displayAdURLParams});
    }

    public String getDisplayAdSize() {
        return displayAdSize;
    }

    public void setDisplayAdSize(String str) {
        displayAdSize = str;
        if (str.equals("320x50")) {
            bannerWidth = 320;
            bannerHeight = 50;
        } else if (str.equals("640x100")) {
            bannerWidth = 640;
            bannerHeight = 100;
        } else if (str.equals("768x90")) {
            bannerWidth = 768;
            bannerHeight = 90;
        }
    }
}
