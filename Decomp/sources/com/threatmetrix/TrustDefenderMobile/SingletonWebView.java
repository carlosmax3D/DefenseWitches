package com.threatmetrix.TrustDefenderMobile;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: JSExecutor */
class SingletonWebView {
    private static final String TAG = SingletonWebView.class.getName();
    private static Context m_context = null;
    private static final Lock s_lock = new ReentrantLock();
    private static volatile WebView s_webView = null;

    private SingletonWebView() {
    }

    public static void clearWebView() {
        try {
            s_lock.lock();
            if (s_webView != null) {
                WebView webView = s_webView;
                new AsyncTask<WebView, Void, Void>() {
                    private WebView m_webview;

                    private Void doInBackground(WebView... webViewArr) {
                        this.m_webview = webViewArr[0];
                        return null;
                    }

                    private void onPostExecute$a83c79c() {
                        this.m_webview.removeAllViews();
                        this.m_webview.destroy();
                    }

                    /* access modifiers changed from: protected */
                    public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
                        this.m_webview = ((WebView[]) objArr)[0];
                        return null;
                    }

                    /* access modifiers changed from: protected */
                    public final /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
                        this.m_webview.removeAllViews();
                        this.m_webview.destroy();
                    }
                }.execute(new WebView[]{webView});
            }
            s_webView = null;
        } finally {
            s_lock.unlock();
        }
    }

    public static WebView getInstance(Context context) {
        if (m_context == null || m_context == context) {
            if (s_webView == null) {
                try {
                    s_lock.lock();
                    if (s_webView == null) {
                        s_webView = new WebView(context);
                        m_context = context;
                    }
                } finally {
                    s_lock.unlock();
                }
            } else {
                String str = TAG;
            }
            return s_webView;
        }
        Log.e(TAG, "Mismatched context: Only application context should be used, and it should always be consistent between calls");
        return null;
    }

    public static boolean hasWebView() {
        try {
            s_lock.lock();
            return s_webView != null;
        } finally {
            s_lock.unlock();
        }
    }
}
