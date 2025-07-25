package jp.co.voyagegroup.android.fluct.jar.sdk;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Picture;
import android.os.Build;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import jp.co.voyagegroup.android.fluct.jar.FluctInterstitialActivity;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctAd;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting;
import jp.co.voyagegroup.android.fluct.jar.util.FluctUtils;
import jp.co.voyagegroup.android.fluct.jar.util.Log;
import jp.co.voyagegroup.android.fluct.jar.web.FluctWebViewClient;
import jp.stargarage.g2metrics.BuildConfig;

public class FluctWebView extends WebView implements WebView.PictureListener {
    private static final String TAG = "FluctWebView";
    private boolean mSettingFlag = false;

    public FluctWebView(Context context, FluctSetting fluctSetting) {
        super(context);
        Log.d(TAG, "new FluctWebView : ");
        initFluctWebView(context, fluctSetting);
        setWebViewClient(new FluctWebViewClient());
    }

    public FluctWebView(Context context, FluctSetting fluctSetting, FluctInterstitialActivity fluctInterstitialActivity) {
        super(context);
        Log.d(TAG, "new FluctWebView : Interstitial");
        initFluctWebView(context, fluctSetting);
        setWebViewClient(new FluctWebViewClient(fluctInterstitialActivity));
    }

    private void initFluctWebView(Context context, FluctSetting fluctSetting) {
        getSettings().setJavaScriptEnabled(true);
        setVerticalScrollbarOverlay(true);
        if (Build.VERSION.SDK_INT == 7) {
            setPictureListener(this);
        }
        setDatabase(context, getSettings());
        setHorizontalScrollBarEnabled(false);
        setBackgroundColor(0);
        setVisibility(8);
        setWebViewClient(new FluctWebViewClient());
        String userAgent = fluctSetting.getUserAgent();
        if (userAgent != null) {
            getSettings().setUserAgentString(String.valueOf(getSettings().getUserAgentString()) + userAgent);
        }
    }

    private String processAdHtml(String str, String str2) {
        Log.d(TAG, "processAdHtml : color is " + str2);
        String str3 = str;
        if (str2 != null && !BuildConfig.FLAVOR.equals(str2)) {
            String[] split = str3.split("</head>");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(split[0]);
            stringBuffer.append("<style type=\"text/css\">body{background-color:#" + str2 + ";}</style>");
            stringBuffer.append("</head>");
            stringBuffer.append(split[1]);
            str3 = stringBuffer.toString();
            try {
                setBackgroundColor(Color.parseColor(str2));
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "processAdHtml : IllegalArgumentException is " + e.getLocalizedMessage());
            }
        }
        String replaceParams = FluctUtils.replaceParams(getContext(), str3);
        Log.v(TAG, "processAdHtml : adHtml is " + replaceParams);
        return replaceParams;
    }

    private void setDatabase(Context context, WebSettings webSettings) {
        Log.d(TAG, "setDatabase : ");
        webSettings.setDatabasePath("data/data/" + context.getApplicationContext().getPackageName() + "/database");
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
    }

    public void destroy() {
        Log.d(TAG, "destroy : ");
        loadDataWithBaseURL((String) null, BuildConfig.FLAVOR, "text/html", "UTF-8", (String) null);
        stopLoading();
        setWebChromeClient((WebChromeClient) null);
        setWebViewClient((WebViewClient) null);
        clearFocus();
        setVisibility(8);
        super.destroy();
    }

    public void onNewPicture(WebView webView, Picture picture) {
        Log.d(TAG, "onNewPicture : ");
        if (!this.mSettingFlag) {
            Log.v(TAG, "onNewPicture : picture  height is " + picture.getHeight() + ":" + webView.getLayoutParams().height);
            if (picture.getHeight() != 0) {
                ViewGroup.LayoutParams layoutParams = webView.getLayoutParams();
                layoutParams.height = (int) (((float) picture.getHeight()) * webView.getScale());
                webView.setLayoutParams(layoutParams);
                this.mSettingFlag = true;
            }
        }
    }

    public void setAdHtml(String str) {
        Log.d(TAG, "setAdHtml : String ");
        String replaceParams = FluctUtils.replaceParams(getContext(), str);
        Log.v(TAG, "setAdHtml : adHtml is " + str);
        loadDataWithBaseURL("http://fluct.jp", replaceParams, "text/html", "UTF-8", (String) null);
    }

    public void setAdHtml(FluctAd fluctAd) {
        Log.d(TAG, "setAdHtml : ");
        String processAdHtml = processAdHtml(fluctAd.getAdHtml(), fluctAd.getBackColor());
        Log.v(TAG, "setAdHtml : adHtml is " + processAdHtml);
        loadDataWithBaseURL("http://fluct.jp", processAdHtml, "text/html", "UTF-8", (String) null);
    }
}
