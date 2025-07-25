package com.threatmetrix.TrustDefenderMobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import jp.stargarage.g2metrics.BuildConfig;

class JSExecutor extends WrapperHelper {
    private static final String TAG = JSExecutor.class.getName();
    private static final String jsInterfaceName = "androidJSInterface";
    private static final Method m_addJavascriptInterface;
    private static final Method m_evaluateJavascript;
    private static final Method m_getDefaultUserAgent;
    private static final Method m_removeJavascriptInterface;
    private static final Method m_setPluginState;
    private static final TreeMap<Integer, String> webkitVersions;
    private boolean javascriptInterfaceBroken;
    private boolean m_inited;
    private JavaScriptInterface m_jsInterface;
    private final boolean m_useWebView;
    private WebSettings m_webSettings;
    private WebView m_webView;

    static {
        Method method = getMethod(WebView.class, "evaluateJavascript", String.class, ValueCallback.class);
        m_evaluateJavascript = method;
        if (method == null && Build.VERSION.SDK_INT >= 19) {
            Log.e(TAG, "Failed to find expected function: evaluateJavascript");
        }
        Method method2 = getMethod(WebSettings.class, "getDefaultUserAgent", Context.class);
        m_getDefaultUserAgent = method2;
        if (method2 == null && Build.VERSION.SDK_INT >= 17) {
            Log.e(TAG, "Failed to find expected function: getDefaultUserAgent");
        }
        Method method3 = getMethod(WebSettings.class, "setPluginState", WebSettings.PluginState.class);
        m_setPluginState = method3;
        if (method3 == null && (Build.VERSION.SDK_INT >= 8 || Build.VERSION.SDK_INT <= 18)) {
            Log.e(TAG, "Failed to find expected function: setPluginState");
        }
        Method method4 = getMethod(WebView.class, "removeJavascriptInterface", String.class);
        m_removeJavascriptInterface = method4;
        if (method4 == null && Build.VERSION.SDK_INT >= 11) {
            Log.e(TAG, "Failed to find expected function: removeJavascriptInterface");
        }
        Method method5 = getMethod(WebView.class, "addJavascriptInterface", Object.class, String.class);
        m_addJavascriptInterface = method5;
        if (method5 == null && Build.VERSION.SDK_INT >= 17) {
            Log.e(TAG, "Failed to find expected function: addJavascriptInterface");
        }
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        webkitVersions = treeMap;
        treeMap.put(9, "533.1");
        webkitVersions.put(10, "533.1");
        webkitVersions.put(11, "533.1");
        webkitVersions.put(12, "533.1");
        webkitVersions.put(13, "534.13");
        webkitVersions.put(14, "534.30");
        webkitVersions.put(15, "534.30");
        webkitVersions.put(16, "534.30");
        webkitVersions.put(17, "534.30");
        webkitVersions.put(18, "534.30");
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public JSExecutor(Context context, JavaScriptInterface javaScriptInterface, boolean z) {
        this.m_webView = null;
        this.m_inited = false;
        this.m_jsInterface = null;
        this.javascriptInterfaceBroken = false;
        this.javascriptInterfaceBroken = isBrokenJSInterface();
        String str = TAG;
        new StringBuilder("JSExecutor() Build: ").append(Build.VERSION.RELEASE).append(this.javascriptInterfaceBroken ? " busted js interface " : " normal js interface ").append(hasAsyncInterface() ? " has async interface " : " has no async interface ");
        this.m_jsInterface = javaScriptInterface;
        this.m_useWebView = z;
        if (z) {
            boolean hasWebView = SingletonWebView.hasWebView();
            this.m_inited = false;
            this.m_webView = SingletonWebView.getInstance(context);
            if (this.m_webView != null) {
                if (hasWebView && !this.m_inited) {
                    Log.w(TAG, "WebView re-used from previous instance. Using a short-lived TrustDefenderMobile object is not recommended. Better profiling performance will be achieved by re-using a long-lived TrustDefenderMobile instance");
                }
                String str2 = TAG;
                new StringBuilder("Webview ").append(this.m_inited ? "init'd" : "un-init'd");
                if (this.m_jsInterface == null) {
                    this.m_jsInterface = new JavaScriptInterface((CountDownLatch) null);
                }
                WebViewClient webViewClient = new WebViewClient();
                this.m_webSettings = this.m_webView.getSettings();
                this.m_webSettings.setJavaScriptEnabled(true);
                invoke(this.m_webSettings, m_setPluginState, WebSettings.PluginState.ON);
                this.m_webView.setVisibility(4);
                if (!this.javascriptInterfaceBroken) {
                    invoke(this.m_webView, m_removeJavascriptInterface, jsInterfaceName);
                }
                this.m_webView.setWebViewClient(webViewClient);
                if (hasAsyncInterface()) {
                    if (this.m_jsInterface.latch == null) {
                        Log.e(TAG, "alternate JS interface but no global latch");
                    }
                    String str3 = TAG;
                } else if (!this.javascriptInterfaceBroken) {
                    invoke(this.m_webView, m_addJavascriptInterface, this.m_jsInterface, jsInterfaceName);
                } else {
                    if (this.m_jsInterface.latch == null) {
                        Log.e(TAG, "broken JS interface but no global latch");
                    }
                    String str4 = TAG;
                    this.m_webView.setWebChromeClient(new WebChromeWrapper(this.m_jsInterface));
                }
            }
        }
    }

    public static String getUserAgentString() {
        String str = TAG;
        String str2 = webkitVersions.containsKey(Integer.valueOf(Build.VERSION.SDK_INT)) ? webkitVersions.get(Integer.valueOf(Build.VERSION.SDK_INT)) : webkitVersions.lastEntry().getValue() + "+";
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (!country.isEmpty()) {
            language = language + AdNetworkKey.DEFAULT_AD + country;
        }
        return "Mozilla/5.0 (Linux; U; Android " + Build.VERSION.RELEASE + "; " + language.toLowerCase(Locale.US) + "; " + Build.MODEL + " Build/" + Build.DISPLAY + ") AppleWebKit/" + str2 + " (KHTML, like Gecko) Version/4.0 Mobile Safari/" + str2 + " " + TrustDefenderMobileCore.version;
    }

    public static boolean hasAsyncInterface() {
        return m_evaluateJavascript != null;
    }

    public static boolean isBrokenJSInterface() {
        try {
            return Build.VERSION.RELEASE.startsWith("2.3");
        } catch (Exception e) {
            return false;
        }
    }

    public final String getJSResult(String str) throws InterruptedException {
        if (!this.m_inited) {
            return null;
        }
        if (Thread.currentThread().isInterrupted()) {
            return BuildConfig.FLAVOR;
        }
        CountDownLatch countDownLatch = null;
        if (!this.javascriptInterfaceBroken && !hasAsyncInterface()) {
            countDownLatch = new CountDownLatch(1);
            this.m_jsInterface.setLatch(countDownLatch);
        }
        String str2 = hasAsyncInterface() ? "javascript:(function(){try{return " + str + " + \"\";}catch(js_eval_err){return '';}})();" : !this.javascriptInterfaceBroken ? "javascript:window.androidJSInterface.getString((function(){try{return " + str + " + \"\";}catch(js_eval_err){return '';}})());" : "javascript:alert((function(){try{return " + str + " + \"\";}catch(js_eval_err){return '';}})());";
        String str3 = TAG;
        new StringBuilder("getJSResult() attempting to execute: ").append(str2);
        this.m_jsInterface.returnedValue = null;
        if (hasAsyncInterface()) {
            boolean z = false;
            try {
                m_evaluateJavascript.invoke(this.m_webView, new Object[]{str2, this.m_jsInterface});
            } catch (IllegalAccessException e) {
                Log.e(TAG, "getJSResult() invoke failed: ", e);
                z = true;
            } catch (IllegalArgumentException e2) {
                Log.e(TAG, "getJSResult() invoke failed: ", e2);
                z = true;
            } catch (InvocationTargetException e3) {
                Log.e(TAG, "getJSResult() invoke failed: ", e3);
                z = true;
            }
            if (z && this.m_jsInterface.latch != null) {
                String str4 = TAG;
                new StringBuilder("getJSResult countdown for latch: ").append(this.m_jsInterface.latch.hashCode()).append(" with count: ").append(this.m_jsInterface.latch.getCount());
                this.m_jsInterface.latch.countDown();
            }
        } else {
            this.m_webView.loadUrl(str2);
        }
        if (this.javascriptInterfaceBroken || hasAsyncInterface()) {
            return null;
        }
        if (countDownLatch != null) {
            String str5 = TAG;
            new StringBuilder("getJSResult waiting for latch: ").append(countDownLatch.hashCode()).append(" with count: ").append(countDownLatch.getCount());
            if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
                String str6 = TAG;
                new StringBuilder("getJSResult timeout waiting for latch: ").append(countDownLatch.hashCode()).append(" with count: ").append(countDownLatch.getCount());
            }
        } else {
            Log.e(TAG, "latch == null");
        }
        if (this.m_jsInterface.returnedValue == null) {
            String str7 = TAG;
        } else {
            String str8 = TAG;
            new StringBuilder("getJSResult() After latch: got ").append(this.m_jsInterface.returnedValue);
        }
        return this.m_jsInterface.returnedValue;
    }

    public final String getUserAgentString(Context context) {
        String str = (String) invoke((Object) null, m_getDefaultUserAgent, context);
        if (str != null && !str.isEmpty()) {
            return str;
        }
        if (this.m_useWebView && this.m_webSettings != null) {
            str = this.m_webSettings.getUserAgentString();
        }
        return (str == null || str.isEmpty()) ? getUserAgentString() : str;
    }

    public final boolean hasBadOptions(boolean z) {
        return z != this.m_useWebView || !this.m_inited;
    }

    public final void init() throws InterruptedException {
        String str;
        String str2 = TAG;
        new StringBuilder("init() - init'd = ").append(this.m_inited);
        if (this.m_inited) {
            return;
        }
        if (this.m_webView == null) {
            String str3 = TAG;
            this.m_inited = true;
            return;
        }
        String str4 = TAG;
        CountDownLatch countDownLatch = null;
        if (this.javascriptInterfaceBroken || hasAsyncInterface()) {
            str = "<html><head></head><body></body></html>";
        } else {
            countDownLatch = new CountDownLatch(1);
            String str5 = TAG;
            new StringBuilder("Creating latch: ").append(countDownLatch.hashCode()).append(" with count: ").append(countDownLatch.getCount());
            str = "<html><head></head><body onLoad='javascript:window.androidJSInterface.getString(1)'></body></html>";
            this.m_jsInterface.setLatch(countDownLatch);
            this.m_jsInterface.returnedValue = null;
        }
        if (!Thread.currentThread().isInterrupted()) {
            this.m_webView.loadData(str, "text/html", (String) null);
            if (this.javascriptInterfaceBroken || countDownLatch == null || hasAsyncInterface()) {
                this.m_inited = true;
                return;
            }
            String str6 = TAG;
            new StringBuilder("waiting for latch: ").append(countDownLatch.hashCode()).append(" with count: ").append(countDownLatch.getCount());
            if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
                Log.e(TAG, "timed out waiting for javascript");
                return;
            }
            this.m_inited = true;
            String str7 = TAG;
            new StringBuilder("in init() count = ").append(countDownLatch.getCount());
            if (this.m_jsInterface.returnedValue == null) {
                String str8 = TAG;
                return;
            }
            String str9 = TAG;
            new StringBuilder("init() After latch: got ").append(this.m_jsInterface.returnedValue);
        }
    }
}
