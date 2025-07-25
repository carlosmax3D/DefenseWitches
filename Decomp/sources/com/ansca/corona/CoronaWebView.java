package com.ansca.corona;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.events.DidFailLoadUrlTask;
import com.ansca.corona.events.EventManager;
import com.ansca.corona.events.FinishedLoadUrlTask;
import com.ansca.corona.events.RunnableEvent;
import com.ansca.corona.events.ShouldLoadUrlTask;
import com.ansca.corona.events.WebHistoryUpdatedTask;
import java.lang.reflect.Field;
import java.util.HashMap;
import jp.stargarage.g2metrics.BuildConfig;

public class CoronaWebView extends WebView {
    private boolean fAutoCloseEnabled = true;
    private boolean fBackKeySupported = true;
    /* access modifiers changed from: private */
    public CoronaRuntime fCoronaRuntime;
    /* access modifiers changed from: private */
    public boolean fIsLoading;
    /* access modifiers changed from: private */
    public HashMap<String, DidFailLoadUrlTask> fReceivedErrorEvents = new HashMap<>();
    /* access modifiers changed from: private */
    public int fUrlRequestSourceType = 5;

    private static class ApiLevel21 {

        private static class FileUploadActivityResultHandler implements CoronaActivity.OnActivityResultHandler {
            private ValueCallback<Uri[]> fFilePathCallback;

            public FileUploadActivityResultHandler(ValueCallback<Uri[]> valueCallback) {
                this.fFilePathCallback = valueCallback;
            }

            public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
                coronaActivity.unregisterActivityResultHandler(this);
                Uri[] uriArr = null;
                if (i2 == -1 && intent != null) {
                    try {
                        uriArr = new Uri[]{Uri.parse(intent.getDataString())};
                    } catch (Exception e) {
                    }
                }
                if (this.fFilePathCallback != null) {
                    this.fFilePathCallback.onReceiveValue(uriArr);
                    this.fFilePathCallback = null;
                }
            }
        }

        private ApiLevel21() {
        }

        public static void openFileUpload(ValueCallback<Uri[]> valueCallback) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            if (coronaActivity != null) {
                int registerActivityResultHandler = coronaActivity.registerActivityResultHandler(new FileUploadActivityResultHandler(valueCallback));
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("*/*");
                coronaActivity.startActivityForResult(Intent.createChooser(intent, BuildConfig.FLAVOR), registerActivityResultHandler);
            }
        }
    }

    private class CoronaWebViewClient extends WebViewClient {
        private CoronaWebViewClient() {
        }

        public void onFormResubmission(WebView webView, Message message, Message message2) {
            int unused = CoronaWebView.this.fUrlRequestSourceType = 4;
            if (message2 != null) {
                message2.sendToTarget();
            }
        }

        public void onPageFinished(WebView webView, String str) {
            boolean unused = CoronaWebView.this.fIsLoading = false;
            super.onPageFinished(webView, str);
            if (CoronaWebView.this.fCoronaRuntime != null && CoronaWebView.this.fCoronaRuntime.isRunning()) {
                DidFailLoadUrlTask didFailLoadUrlTask = (DidFailLoadUrlTask) CoronaWebView.this.fReceivedErrorEvents.get(str);
                CoronaWebView.this.fReceivedErrorEvents.clear();
                CoronaWebView.this.fCoronaRuntime.getTaskDispatcher().send(new WebHistoryUpdatedTask(CoronaWebView.this.getId(), CoronaWebView.this.canGoBack(), CoronaWebView.this.canGoForward()));
                if (didFailLoadUrlTask != null) {
                    CoronaWebView.this.fCoronaRuntime.getTaskDispatcher().send(didFailLoadUrlTask);
                } else {
                    CoronaWebView.this.fCoronaRuntime.getTaskDispatcher().send(new FinishedLoadUrlTask(CoronaWebView.this.getId(), str));
                }
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            int access$200 = CoronaWebView.this.fUrlRequestSourceType;
            int unused = CoronaWebView.this.fUrlRequestSourceType = 5;
            if (!CoronaWebView.this.fReceivedErrorEvents.containsKey(str) && CoronaWebView.this.fCoronaRuntime != null && CoronaWebView.this.fCoronaRuntime.isRunning()) {
                CoronaWebView.this.fCoronaRuntime.getTaskDispatcher().send(new ShouldLoadUrlTask(CoronaWebView.this.getId(), str, access$200));
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            int unused = CoronaWebView.this.fUrlRequestSourceType = 5;
            CoronaWebView.this.fReceivedErrorEvents.put(str2, new DidFailLoadUrlTask(CoronaWebView.this.getId(), str2, str, i));
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            int unused = CoronaWebView.this.fUrlRequestSourceType = 0;
            return false;
        }
    }

    private static class UrlRequestSourceType {
        public static final int FORM = 1;
        public static final int HISTORY = 2;
        public static final int LINK = 0;
        public static final int OTHER = 5;
        public static final int RELOAD = 3;
        public static final int RESUBMITTED = 4;

        private UrlRequestSourceType() {
        }
    }

    public CoronaWebView(Context context, CoronaRuntime coronaRuntime) {
        super(context);
        this.fCoronaRuntime = coronaRuntime;
        setWebViewClient(new CoronaWebViewClient());
        setWebChromeClient(new WebChromeClient() {
            View mCustomView;

            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
                if (callback != null) {
                    callback.invoke(str, true, false);
                }
            }

            public void onHideCustomView() {
                CoronaActivity coronaActivity;
                if (this.mCustomView != null && (coronaActivity = CoronaEnvironment.getCoronaActivity()) != null) {
                    ViewManager viewManager = CoronaWebView.this.fCoronaRuntime.getViewManager();
                    coronaActivity.getOverlayView().removeView(this.mCustomView);
                }
            }

            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                if (Build.VERSION.SDK_INT >= 19) {
                    CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
                    if (coronaActivity != null) {
                        coronaActivity.getOverlayView().addView(view, new ViewGroup.LayoutParams(-1, -1));
                        this.mCustomView = view;
                        return;
                    }
                    return;
                }
                onHideCustomView();
                customViewCallback.onCustomViewHidden();
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                ApiLevel21.openFileUpload(valueCallback);
                return true;
            }
        });
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= 11) {
            Class<WebSettings> cls = WebSettings.class;
            try {
                cls.getMethod("setEnableSmoothTransition", new Class[]{Boolean.TYPE}).invoke(settings, new Object[]{true});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                    case 1:
                        if (view.hasFocus()) {
                            return false;
                        }
                        view.requestFocus();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    public static int getBackgroundColorFrom(WebView webView) {
        if (webView == null) {
            throw new NullPointerException();
        }
        try {
            Field declaredField = WebView.class.getDeclaredField("mBackgroundColor");
            declaredField.setAccessible(true);
            return declaredField.getInt(webView);
        } catch (Exception e) {
            return -1;
        }
    }

    public void destroy() {
        EventManager eventManager;
        if (this.fCoronaRuntime.wasNotDisposed() && (eventManager = this.fCoronaRuntime.getController().getEventManager()) != null) {
            final int id = getId();
            eventManager.addEvent(new RunnableEvent(new Runnable() {
                public void run() {
                    JavaToNativeShim.webViewClosed(CoronaWebView.this.fCoronaRuntime, id);
                }
            }));
        }
        super.destroy();
    }

    public int getBackgroundColor() {
        return getBackgroundColorFrom(this);
    }

    public void goBack() {
        if (canGoBack()) {
            stopLoading();
            this.fUrlRequestSourceType = 2;
            super.goBack();
        } else if (this.fAutoCloseEnabled) {
            ViewManager viewManager = this.fCoronaRuntime.getViewManager();
            if (viewManager != null) {
                viewManager.destroyDisplayObject(getId());
            }
        } else if (this.fCoronaRuntime != null && this.fCoronaRuntime.isRunning()) {
            this.fCoronaRuntime.getTaskDispatcher().send(new ShouldLoadUrlTask(getId(), BuildConfig.FLAVOR, 2));
        }
    }

    public void goForward() {
        if (canGoForward()) {
            stopLoading();
            this.fUrlRequestSourceType = 2;
            super.goForward();
        }
    }

    public boolean isAutoCloseEnabled() {
        return this.fAutoCloseEnabled;
    }

    public boolean isBackKeySupported() {
        return this.fBackKeySupported;
    }

    public void loadUrl(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        stopLoading();
        this.fIsLoading = true;
        this.fUrlRequestSourceType = 5;
        super.loadUrl(str);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        if (!this.fBackKeySupported) {
            return false;
        }
        goBack();
        if (canGoBack()) {
            return true;
        }
        return this.fAutoCloseEnabled;
    }

    public void postUrl(String str, byte[] bArr) {
        stopLoading();
        this.fUrlRequestSourceType = 1;
        super.postUrl(str, bArr);
    }

    public void reload() {
        stopLoading();
        this.fUrlRequestSourceType = 3;
        super.reload();
    }

    public void setAutoCloseEnabled(boolean z) {
        this.fAutoCloseEnabled = z;
    }

    public void setBackKeySupported(boolean z) {
        this.fBackKeySupported = z;
    }

    public void stopLoading() {
        if (this.fIsLoading) {
            super.stopLoading();
            this.fIsLoading = false;
        }
    }
}
