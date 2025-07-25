package com.tapjoy;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.tapjoy.TJAdUnitConstants;
import java.util.ArrayList;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONArray;
import org.json.JSONObject;

public class TJWebViewJSInterface {
    private static final String TAG = "TJWebViewJSInterface";
    TJWebViewJSInterfaceNotifier notifier;
    WebView webView;

    @TargetApi(19)
    private class LoadJSTask extends AsyncTask<String, Void, String> {
        WebView webView;

        public LoadJSTask(WebView webView2) {
            this.webView = webView2;
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... strArr) {
            return strArr[0];
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            if (this.webView == null) {
                return;
            }
            if (!str.startsWith("javascript:") || Build.VERSION.SDK_INT < 19) {
                this.webView.loadUrl(str);
                return;
            }
            try {
                this.webView.evaluateJavascript(str.replaceFirst("javascript:", BuildConfig.FLAVOR), (ValueCallback) null);
            } catch (Exception e) {
                TapjoyLog.e(TJWebViewJSInterface.TAG, "Exception in evaluateJavascript. Device not supported. " + e.toString());
            }
        }
    }

    public TJWebViewJSInterface(WebView webView2, TJWebViewJSInterfaceNotifier tJWebViewJSInterfaceNotifier) {
        this.webView = webView2;
        this.notifier = tJWebViewJSInterfaceNotifier;
    }

    public void callback(ArrayList<?> arrayList, String str, String str2) {
        try {
            callbackToJavaScript(new JSONArray(arrayList), str, str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callback(Map<?, ?> map, String str, String str2) {
        try {
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(new JSONObject(map));
            callbackToJavaScript(jSONArray, str, str2);
        } catch (Exception e) {
            TapjoyLog.e(TAG, "Exception in callback to JS: " + e.toString());
            e.printStackTrace();
        }
    }

    public void callbackToJavaScript(Object obj, String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(TJAdUnitConstants.String.ARGUMENTS, obj);
            if (str != null && str.length() > 0) {
                jSONObject.put(TJAdUnitConstants.String.METHOD, str);
            }
            JSONObject jSONObject2 = new JSONObject();
            if (str2 != null && str2.length() > 0) {
                jSONObject2.put(TJAdUnitConstants.String.CALLBACK_ID, str2);
            }
            jSONObject2.put("data", jSONObject);
            String str3 = "javascript:if(window.AndroidWebViewJavascriptBridge) AndroidWebViewJavascriptBridge._handleMessageFromAndroid('" + jSONObject2 + "');";
            new LoadJSTask(this.webView).execute(new String[]{str3});
            TapjoyLog.i(TAG, "sendToJS: " + str3);
        } catch (Exception e) {
            TapjoyLog.e(TAG, "Exception in callback to JS: " + e.toString());
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void dispatchMethod(String str) {
        TapjoyLog.i(TAG, "dispatchMethod params: " + str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getJSONObject("data").getString(TJAdUnitConstants.String.METHOD);
            TapjoyLog.i(TAG, "method: " + string);
            if (this.notifier != null) {
                this.notifier.dispatchMethod(string, jSONObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
