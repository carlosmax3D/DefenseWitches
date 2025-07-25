package com.tapjoy;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.mraid.listener.MraidViewListener;
import com.tapjoy.mraid.view.MraidView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import jp.stargarage.g2metrics.BuildConfig;

@SuppressLint({"SetJavaScriptEnabled"})
public class TJAdUnitView extends Activity {
    private static final int CLOSE_BUTTON_OFFSET = 10;
    private static final long DELAY_BEFORE_CLOSE_FADE_IN = 2000;
    private static final long DURATION_OF_CLOSE_FADE_IN = 500;
    private static final String TAG = "TJAdUnitView";
    protected TJAdUnitJSBridge bridge;
    private String callbackID;
    /* access modifiers changed from: private */
    public ImageButton closeButton;
    private boolean closeButtonVisible = true;
    private String connectivityErrorMessage = "A connection error occurred loading this content.";
    private TJEvent event;
    private TJEventData eventData;
    protected int historyIndex = 0;
    /* access modifiers changed from: private */
    public boolean isLegacyView = false;
    protected RelativeLayout layout = null;
    protected String offersURL = null;
    protected boolean pauseCalled = false;
    /* access modifiers changed from: private */
    public ProgressBar progressBar;
    protected boolean redirectedActivity;
    protected boolean skipOfferWall = false;
    protected String url = null;
    /* access modifiers changed from: private */
    public int viewType = 0;
    protected MraidView webView = null;

    private class TJAdUnitViewListener implements MraidViewListener {
        private TJAdUnitViewListener() {
        }

        public boolean onClose() {
            TJAdUnitView.this.finish();
            return false;
        }

        @TargetApi(8)
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (TJAdUnitView.this.bridge.shouldClose) {
                TapjoyLog.i(TJAdUnitView.TAG, "shouldClose...");
                for (String contains : new String[]{"Uncaught", "uncaught", "Error", "error", "not defined"}) {
                    if (consoleMessage.message().contains(contains)) {
                        TJAdUnitView.this.handleClose();
                    }
                }
            }
            return true;
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
            TJAdUnitView.this.handleWebViewOnPageFinished(webView, str);
            if (TJAdUnitView.this.isLegacyView) {
                TJAdUnitView.this.progressBar.setVisibility(8);
            }
            TJAdUnitView.this.bridge.display();
            if (TJAdUnitView.this.webView != null && TJAdUnitView.this.webView.isMraid()) {
                TJAdUnitView.this.bridge.allowRedirect = false;
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            TapjoyLog.i(TJAdUnitView.TAG, "onPageStarted: " + str);
            if (TJAdUnitView.this.isLegacyView) {
                TJAdUnitView.this.progressBar.setVisibility(0);
                TJAdUnitView.this.progressBar.bringToFront();
            }
            if (TJAdUnitView.this.bridge != null) {
                TJAdUnitView.this.bridge.allowRedirect = true;
                TJAdUnitView.this.bridge.customClose = false;
                TJAdUnitView.this.bridge.shouldClose = false;
            }
        }

        public boolean onReady() {
            return false;
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            TJAdUnitView.this.handleWebViewOnReceivedError(webView, i, str, str2);
        }

        public boolean onResize() {
            return false;
        }

        public boolean onResizeClose() {
            return false;
        }

        @TargetApi(9)
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (!TJAdUnitView.this.isNetworkAvailable()) {
                TJAdUnitView.this.handleWebViewOnReceivedError(webView, 0, "Connection not properly established", str);
                return true;
            }
            TJAdUnitView.this.redirectedActivity = false;
            String str2 = null;
            try {
                str2 = new URL(TapjoyConfig.TJC_SERVICE_URL).getHost();
            } catch (MalformedURLException e) {
            }
            TapjoyLog.i(TJAdUnitView.TAG, "interceptURL: " + str + " with host " + str2);
            if (TJAdUnitView.this.webView != null && TJAdUnitView.this.webView.isMraid() && str.contains("mraid")) {
                return false;
            }
            if (TJAdUnitView.this.viewType == 4 && str.contains(TapjoyConstants.TJC_VIDEO_OFFER_WALL_URL)) {
                TJAdUnitView.this.finishWithResult(TapjoyConstants.TJC_VIDEO_OFFER_WALL_URL);
                return true;
            } else if (TJAdUnitView.this.viewType == 4 && str.contains(TapjoyConstants.TJC_VIDEO_TJVIDEO_URL)) {
                TJAdUnitView.this.finishWithResult(TapjoyConstants.TJC_VIDEO_TJVIDEO_URL);
                return true;
            } else if (str.startsWith(TapjoyConstants.TJC_VIDEO_AD_URL)) {
                TJAdUnitView.this.handleTJVideoURL(str);
                return true;
            } else if (str.contains(TapjoyConstants.TJC_FULLSCREEN_AD_SHOW_OFFERS_URL)) {
                TapjoyLog.i(TJAdUnitView.TAG, TapjoyConstants.TJC_FULLSCREEN_AD_SHOW_OFFERS_URL);
                new TJCOffers(TJAdUnitView.this).showOffers((TapjoyOffersNotifier) null);
                return true;
            } else if (str.contains(TapjoyConstants.TJC_FULLSCREEN_AD_DISMISS_URL)) {
                TapjoyLog.i(TJAdUnitView.TAG, TapjoyConstants.TJC_FULLSCREEN_AD_DISMISS_URL);
                TJAdUnitView.this.finish();
                return true;
            } else if ((str2 != null && str.contains(str2)) || str.contains(TapjoyConstants.TJC_YOUTUBE_AD_PARAM) || str.contains(TapjoyConnectCore.getRedirectDomain()) || str.contains(TapjoyUtil.getRedirectDomain(TapjoyConnectCore.getEventURL()))) {
                TapjoyLog.i(TJAdUnitView.TAG, "Open redirecting URL:" + str);
                ((MraidView) webView).loadUrlStandard(str);
                return true;
            } else if (TJAdUnitView.this.bridge.allowRedirect) {
                TJAdUnitView.this.redirectedActivity = true;
                return false;
            } else {
                webView.loadUrl(str);
                return true;
            }
        }
    }

    /* access modifiers changed from: private */
    public void finishActivity() {
        this.bridge.disable();
        if (this.viewType == 4) {
            finishWithResult(TapjoyConstants.TJC_VIDEO_OFFER_WALL_URL);
        } else if (this != null) {
            finish();
        }
    }

    /* access modifiers changed from: private */
    public void finishWithResult(String str) {
        Intent intent = new Intent();
        intent.putExtra("result", str);
        setResult(-1, intent);
        finish();
    }

    private Bitmap getCloseBitmap() {
        return !this.closeButtonVisible ? getTransparentCloseBitmap() : TapjoyUtil.loadBitmapFromJar("tj_close_button.png", this);
    }

    private Bitmap getTransparentCloseBitmap() {
        try {
            float deviceScreenDensityScale = TapjoyConnectCore.getDeviceScreenDensityScale();
            return Bitmap.createBitmap((int) (50.0f * deviceScreenDensityScale), (int) (50.0f * deviceScreenDensityScale), Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void handleTJVideoURL(String str) {
        Map<String, String> convertURLParams = TapjoyUtil.convertURLParams(str.substring(str.indexOf("://") + "://".length()), true);
        String str2 = convertURLParams.get(TapjoyConstants.TJC_VIDEO_ID);
        String str3 = convertURLParams.get(TapjoyConstants.TJC_AMOUNT);
        String str4 = convertURLParams.get(TapjoyConstants.TJC_CURRENCY_NAME);
        String str5 = convertURLParams.get(TapjoyConstants.TJC_CLICK_URL);
        String str6 = convertURLParams.get(TapjoyConstants.TJC_VIDEO_COMPLETE_URL);
        String str7 = convertURLParams.get(TapjoyConstants.TJC_VIDEO_URL);
        TapjoyLog.i(TAG, "video_id: " + str2);
        TapjoyLog.i(TAG, "amount: " + str3);
        TapjoyLog.i(TAG, "currency_name: " + str4);
        TapjoyLog.i(TAG, "click_url: " + str5);
        TapjoyLog.i(TAG, "video_complete_url: " + str6);
        TapjoyLog.i(TAG, "video_url: " + str7);
        if (TapjoyVideo.getInstance().startVideo(str2, str4, str3, str5, str6, str7)) {
            TapjoyLog.i(TAG, "Video started successfully");
            return;
        }
        TapjoyLog.e(TAG, "Unable to play video: " + str2);
        try {
            new AlertDialog.Builder(this).setTitle(BuildConfig.FLAVOR).setMessage("Unable to play video.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        } catch (Exception e) {
            TapjoyLog.e(TAG, "e: " + e.toString());
        }
    }

    public void finish() {
        if (!(this.viewType == 1 || this.viewType == 4)) {
            Intent intent = new Intent();
            intent.putExtra("result", Boolean.TRUE);
            intent.putExtra(TJAdUnitConstants.EXTRA_CALLBACK_ID, this.callbackID);
            setResult(-1, intent);
        }
        super.finish();
    }

    public void handleClose() {
        if (this.webView.videoPlaying()) {
            this.webView.videoViewCleanup();
        } else if (this.bridge.customClose) {
            TapjoyLog.i(TAG, TJAdUnitConstants.String.CUSTOM_CLOSE);
            if (this.bridge.shouldClose) {
                finishActivity();
                return;
            }
            TapjoyLog.i(TAG, "closeRequested...");
            this.bridge.closeRequested();
            new Timer().schedule(new TimerTask() {
                public void run() {
                    if (TJAdUnitView.this.bridge.shouldClose) {
                        TapjoyLog.i(TJAdUnitView.TAG, "customClose timeout");
                        TJAdUnitView.this.finishActivity();
                    }
                }
            }, 1000);
        } else {
            finishActivity();
        }
    }

    public void handleWebViewOnPageFinished(WebView webView2, String str) {
        TapjoyLog.i(TAG, "handleWebViewOnPageFinished");
    }

    public void handleWebViewOnReceivedError(WebView webView2, int i, String str, String str2) {
        TapjoyLog.i(TAG, "handleWebViewError");
        if (!isFinishing()) {
            new AlertDialog.Builder(this).setMessage(this.connectivityErrorMessage).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).create().show();
        }
    }

    /* access modifiers changed from: protected */
    @TargetApi(12)
    public void initUI() {
        TapjoyLog.i(TAG, "initUI");
        boolean z = false;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString(TapjoyConstants.EXTRA_DISPLAY_AD_URL) != null) {
                this.skipOfferWall = true;
                this.offersURL = extras.getString(TapjoyConstants.EXTRA_DISPLAY_AD_URL);
            } else if (extras.getSerializable(TapjoyConstants.EXTRA_URL_PARAMS) != null) {
                this.skipOfferWall = false;
                HashMap hashMap = (HashMap) extras.getSerializable(TapjoyConstants.EXTRA_URL_PARAMS);
                TapjoyLog.i(TAG, "urlParams: " + hashMap);
                this.offersURL = TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_SHOW_OFFERS_URL_PATH + TapjoyUtil.convertURLParams((Map<String, String>) hashMap, false);
            }
            this.eventData = (TJEventData) extras.getSerializable(TJAdUnitConstants.EXTRA_TJEVENT);
            if (this.eventData != null) {
                this.event = TJEventManager.get(this.eventData.guid);
            }
            this.viewType = extras.getInt(TJAdUnitConstants.EXTRA_VIEW_TYPE);
            String string = extras.getString("html");
            String string2 = extras.getString(TJAdUnitConstants.EXTRA_BASE_URL);
            this.url = extras.getString("url");
            this.callbackID = extras.getString(TJAdUnitConstants.EXTRA_CALLBACK_ID);
            this.isLegacyView = extras.getBoolean(TJAdUnitConstants.EXTRA_LEGACY_VIEW);
            if (this.webView == null) {
                this.webView = new MraidView(this);
                this.webView.getSettings().setJavaScriptEnabled(true);
                this.webView.setListener(new TJAdUnitViewListener());
                this.bridge = new TJAdUnitJSBridge(this, this.webView, this.eventData);
                if (this.viewType == 1) {
                    TapjoyLog.i(TAG, "Loading event data");
                    if (this.event != null) {
                        this.webView.loadDataWithBaseURL(this.eventData.baseURL, this.eventData.httpResponse, "text/html", "utf-8", (String) null);
                        this.webView.setVisibility(4);
                        TapjoyConnectCore.viewWillOpen(4);
                        if (this.event.getCallback() != null) {
                            this.event.getCallback().contentDidShow(this.event);
                        }
                    }
                } else {
                    if (string != null && string.length() > 0) {
                        TapjoyLog.i(TAG, "Loading HTML data");
                        if (this.isLegacyView) {
                            this.webView.loadDataWithBaseURL(string2, string, "text/html", "utf-8", (String) null);
                        } else {
                            this.webView.loadDataWithBaseURL((String) null, string, "text/html", "utf-8", (String) null);
                        }
                    } else if (this.url != null) {
                        TapjoyLog.i(TAG, "Load URL: " + this.url);
                        this.webView.loadUrl(this.url);
                    } else if (this.offersURL != null) {
                        TapjoyLog.i(TAG, "Load Offer Wall URL");
                        this.webView.loadUrl(this.offersURL);
                    }
                    z = true;
                }
                if (Build.VERSION.SDK_INT >= 11) {
                    getWindow().setFlags(ViewCompat.MEASURED_STATE_TOO_SMALL, ViewCompat.MEASURED_STATE_TOO_SMALL);
                }
                getWindow().setBackgroundDrawable(new ColorDrawable(1610612736));
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
                this.layout = new RelativeLayout(this);
                this.layout.setLayoutParams(layoutParams);
                if (this.viewType == 1) {
                    this.layout.setBackgroundColor(0);
                    this.layout.getBackground().setAlpha(0);
                } else {
                    this.layout.setBackgroundColor(-1);
                    this.layout.getBackground().setAlpha(MotionEventCompat.ACTION_MASK);
                }
                this.webView.setLayoutParams(layoutParams);
                if (this.webView.getParent() != null) {
                    ((ViewGroup) this.webView.getParent()).removeView(this.webView);
                }
                this.layout.addView(this.webView, -1, -1);
                setContentView(this.layout);
                if (this.isLegacyView && z) {
                    this.progressBar = new ProgressBar(this, (AttributeSet) null, 16842874);
                    this.progressBar.setVisibility(0);
                    RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                    layoutParams2.addRule(13);
                    this.progressBar.setLayoutParams(layoutParams2);
                    this.layout.addView(this.progressBar);
                }
                if (!this.webView.isMraid()) {
                    this.closeButton = new ImageButton(this);
                    Bitmap closeBitmap = getCloseBitmap();
                    if (closeBitmap != null) {
                        this.closeButton.setImageBitmap(closeBitmap);
                        this.closeButton.setBackgroundColor(ViewCompat.MEASURED_SIZE_MASK);
                        this.closeButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                TJAdUnitView.this.handleClose();
                            }
                        });
                        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
                        layoutParams3.addRule(10);
                        layoutParams3.addRule(11);
                        int deviceScreenDensityScale = (int) (-10.0f * TapjoyConnectCore.getDeviceScreenDensityScale());
                        layoutParams3.setMargins(0, deviceScreenDensityScale, deviceScreenDensityScale, 0);
                        this.layout.addView(this.closeButton, layoutParams3);
                        if (Build.VERSION.SDK_INT >= 12) {
                            this.closeButton.setAlpha(0.0f);
                            this.closeButton.setVisibility(0);
                            this.closeButton.setClickable(false);
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    TJAdUnitView.this.closeButton.animate().alpha(1.0f).setDuration(TJAdUnitView.DURATION_OF_CLOSE_FADE_IN).setListener(new Animator.AnimatorListener() {
                                        public void onAnimationCancel(Animator animator) {
                                        }

                                        public void onAnimationEnd(Animator animator) {
                                            TJAdUnitView.this.closeButton.setClickable(true);
                                        }

                                        public void onAnimationRepeat(Animator animator) {
                                        }

                                        public void onAnimationStart(Animator animator) {
                                        }
                                    });
                                }
                            }, DELAY_BEFORE_CLOSE_FADE_IN);
                            return;
                        }
                        return;
                    }
                    Log.e(TAG, "Error loading bitmap data for close button!");
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.i(TAG, "onActivityResult requestCode:" + i + ", resultCode: " + i2);
        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
        }
        if (bundle != null && bundle.getString(TJAdUnitConstants.EXTRA_CALLBACK_ID) != null) {
            TapjoyLog.i(TAG, "onActivityResult extras: " + bundle.keySet());
            this.bridge.invokeJSCallback(bundle.getString(TJAdUnitConstants.EXTRA_CALLBACK_ID), Boolean.valueOf(bundle.getBoolean("result")), bundle.getString(TJAdUnitConstants.EXTRA_RESULT_STRING1), bundle.getString(TJAdUnitConstants.EXTRA_RESULT_STRING2));
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        TapjoyLog.i(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(configuration);
        initUI();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        if (Build.VERSION.SDK_INT < 11) {
            setTheme(16973839);
        } else {
            requestWindowFeature(1);
            getWindow().setFlags(1024, 1024);
        }
        TapjoyLog.i(TAG, "TJAdUnitView onCreate: " + bundle);
        super.onCreate(bundle);
        initUI();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        TapjoyLog.i(TAG, "onDestroy isFinishing: " + isFinishing());
        if (isFinishing()) {
            if (this.viewType == 1) {
                this.bridge.destroy();
                TapjoyConnectCore.viewDidClose(4);
                if (!(this.event == null || this.event.getCallback() == null)) {
                    this.event.getCallback().contentDidDisappear(this.event);
                }
                if (!(this.event == null || !this.event.isPreloadEnabled() || TapjoyCache.getInstance() == null)) {
                    TapjoyCache.getInstance().decrementEventCacheCount();
                }
                TJEventManager.remove(this.eventData.guid);
            }
            if (this.webView != null) {
                try {
                    WebView.class.getMethod("onPause", new Class[0]).invoke(this.webView, new Object[0]);
                } catch (Exception e) {
                }
                try {
                    this.webView = null;
                } catch (Exception e2) {
                }
            }
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        handleClose();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.pauseCalled = true;
        try {
            WebView.class.getMethod("onPause", new Class[0]).invoke(this.webView, new Object[0]);
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (this.webView != null) {
            this.webView.restoreState(bundle);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        try {
            WebView.class.getMethod("onResume", new Class[0]).invoke(this.webView, new Object[0]);
        } catch (Exception e) {
        }
        if (this.viewType == 1 && this.bridge.didLaunchOtherActivity) {
            TapjoyLog.i(TAG, "onResume bridge.didLaunchOtherActivity callbackID: " + this.bridge.otherActivityCallbackID);
            this.bridge.invokeJSCallback(this.bridge.otherActivityCallbackID, Boolean.TRUE);
            this.bridge.didLaunchOtherActivity = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.webView != null) {
            this.webView.saveState(bundle);
        }
    }

    public void setCloseButtonVisibility(boolean z) {
        Bitmap closeBitmap;
        if (this.closeButtonVisible != z) {
            this.closeButtonVisible = z;
            if (this.closeButton != null && (closeBitmap = getCloseBitmap()) != null) {
                this.closeButton.setImageBitmap(closeBitmap);
            }
        }
    }
}
