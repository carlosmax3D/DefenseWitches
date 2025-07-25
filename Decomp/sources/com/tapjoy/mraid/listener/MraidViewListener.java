package com.tapjoy.mraid.listener;

import android.graphics.Bitmap;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;

public interface MraidViewListener {
    boolean onClose();

    boolean onConsoleMessage(ConsoleMessage consoleMessage);

    boolean onEventFired();

    boolean onExpand();

    boolean onExpandClose();

    void onPageFinished(WebView webView, String str);

    void onPageStarted(WebView webView, String str, Bitmap bitmap);

    boolean onReady();

    void onReceivedError(WebView webView, int i, String str, String str2);

    boolean onResize();

    boolean onResizeClose();

    boolean shouldOverrideUrlLoading(WebView webView, String str);
}
