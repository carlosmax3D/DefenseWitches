package com.tapjoy;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tapjoy.TJAdUnitConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

@SuppressLint({"SetJavaScriptEnabled"})
public class TJEventOptimizer extends WebView {
    /* access modifiers changed from: private */
    public static String TAG = "TJEventOptimizer";
    private static int eventCount = 0;
    /* access modifiers changed from: private */
    public static TJEventOptimizer eventOptimizer;
    /* access modifiers changed from: private */
    public static CountDownLatch initLatch;
    private TJAdUnitJSBridge bridge;
    private Context ctx;
    /* access modifiers changed from: private */
    public HashMap<String, TJEvent> events;
    private Map<String, String> urlParams;

    private class TJEventTimerTask extends TimerTask {
        String token;

        public TJEventTimerTask(String str) {
            this.token = str;
        }

        public void run() {
            TJEvent tJEvent = (TJEvent) TJEventOptimizer.this.events.get(this.token);
            if (tJEvent != null) {
                TapjoyLog.d(TJEventOptimizer.TAG, "Event optimization call timed out for event " + tJEvent.getName() + ", fall through to full event call");
                TJEventOptimizer.this.eventOptimizationCallback(this.token, true);
            }
        }
    }

    private class TJEventWebChromeClient extends WebChromeClient {
        private TJEventWebChromeClient() {
        }

        @TargetApi(8)
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            TapjoyLog.d(TJEventOptimizer.TAG, "JS CONSOLE: " + consoleMessage.message() + " -- From line " + consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
            return true;
        }
    }

    private class TJEventWebViewClient extends WebViewClient {
        private TJEventWebViewClient() {
        }

        public void onPageFinished(WebView webView, String str) {
            TapjoyLog.d(TJEventOptimizer.TAG, "boostrap html loaded successfully");
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            TapjoyLog.e(TJEventOptimizer.TAG, "error:" + str);
        }
    }

    private TJEventOptimizer(Context context) {
        super(context);
        this.ctx = context;
        this.events = new HashMap<>();
        this.bridge = new TJAdUnitJSBridge(this.ctx, this, (TJEventData) null);
        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(new TJEventWebViewClient());
        setWebChromeClient(new TJEventWebChromeClient());
        loadUrl(TapjoyConnectCore.getHostURL() + TJAdUnitConstants.EVENTS_PROXY_PATH + TapjoyUtil.convertURLParams(TapjoyConnectCore.getGenericURLParams(), true));
    }

    public static TJEventOptimizer getInstance() {
        return eventOptimizer;
    }

    public static void init(final Context context) throws InterruptedException {
        TapjoyLog.d(TAG, "Initializing event optimizer");
        initLatch = new CountDownLatch(1);
        AnonymousClass1 r1 = new Runnable() {
            public void run() {
                TJEventOptimizer unused = TJEventOptimizer.eventOptimizer = new TJEventOptimizer(context);
                TJEventOptimizer.initLatch.countDown();
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r1.run();
        } else {
            new Handler(context.getMainLooper()).post(r1);
        }
        initLatch.await();
    }

    @TargetApi(19)
    public void checkEvent(TJEvent tJEvent) {
        StringBuilder append = new StringBuilder().append(TJAdUnitConstants.String.EVENT_TOKEN);
        int i = eventCount;
        eventCount = i + 1;
        final String sb = append.append(i).toString();
        this.urlParams = TapjoyConnectCore.getGenericURLParams();
        this.urlParams.putAll(tJEvent.getParameters());
        this.urlParams.putAll(TapjoyConnectCore.getTimeStampAndVerifierParams());
        final String jSONObject = new JSONObject(this.urlParams).toString();
        this.events.put(sb, tJEvent);
        AnonymousClass2 r1 = new Runnable() {
            public void run() {
                String str = "window.eventsProxy.processEvent('" + sb + "', " + jSONObject + ");";
                new Timer().schedule(new TJEventTimerTask(sb), 2000);
                if (Build.VERSION.SDK_INT >= 19) {
                    try {
                        TJEventOptimizer.this.evaluateJavascript(str, (ValueCallback) null);
                    } catch (Exception e) {
                        TapjoyLog.e(TJEventOptimizer.TAG, "Exception in evaluateJavascript. Device not supported. " + e.toString());
                    }
                } else {
                    TJEventOptimizer.this.loadUrl("javascript:" + str);
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r1.run();
        } else {
            new Handler(this.ctx.getMainLooper()).post(r1);
        }
    }

    public void eventOptimizationCallback(String str, boolean z) {
        TJEvent tJEvent = this.events.get(str);
        if (tJEvent != null) {
            tJEvent.processSendCallback(z);
            TapjoyLog.d(TAG, "Event optimization result " + z + " for event " + tJEvent.getName());
            this.events.remove(str);
        }
    }
}
