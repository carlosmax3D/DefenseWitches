package jp.co.voyagegroup.android.fluct.jar.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.drive.DriveFile;
import jp.co.voyagegroup.android.fluct.jar.FluctInterstitialActivity;
import jp.co.voyagegroup.android.fluct.jar.util.FluctUtils;
import jp.co.voyagegroup.android.fluct.jar.util.Log;

public class FluctWebViewClient extends WebViewClient {
    private static final String TAG = "FluctWebViewClient";
    private FluctInterstitialActivity mInterstitialActivity;

    public FluctWebViewClient() {
        Log.d(TAG, "FluctWebViewClient : ");
    }

    public FluctWebViewClient(FluctInterstitialActivity fluctInterstitialActivity) {
        Log.d(TAG, "FluctWebViewClient : activity ");
        this.mInterstitialActivity = fluctInterstitialActivity;
    }

    public void onPageFinished(WebView webView, String str) {
        Log.d(TAG, "onPageFinished : url is " + str + " webview is " + webView);
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        Log.d(TAG, "onPageStarted : url is " + str + " webview is " + webView);
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        Log.e(TAG, "onReceivedError : view is " + webView);
        Log.e(TAG, "onReceivedError : errorCode is " + i);
        Log.e(TAG, "onReceivedError : description is " + str);
        Log.e(TAG, "onReceivedError : failingUrl is " + str2);
        if (this.mInterstitialActivity != null) {
            this.mInterstitialActivity.callback(100);
        }
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        Log.d(TAG, "shouldOverrideUrlLoading : url is " + str);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(FluctUtils.replaceParams(webView.getContext(), str)));
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        webView.getContext().startActivity(intent);
        if (this.mInterstitialActivity != null) {
            this.mInterstitialActivity.callback(1);
        }
        return true;
    }
}
