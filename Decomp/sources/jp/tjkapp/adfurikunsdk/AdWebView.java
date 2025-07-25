package jp.tjkapp.adfurikunsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.drive.DriveFile;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.AdInfo;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class AdWebView extends WebView {
    private static final String APP_URL = "adfurikun_appurl:";
    private static final String ASSETS_BASE_URL = "file:///android_asset/adfurikun/";
    private static final String BASE_URL = "about:blank";
    private static final int ERR_TYPE_API = 3;
    private static final int ERR_TYPE_CONNECTED = 1;
    private static final int ERR_TYPE_GETINFO = 2;
    private static final int ERR_TYPE_NOTFOUND = 4;
    private static final int ERR_TYPE_UNKNOWN = 5;
    public static final int LOADING_END = 2;
    public static final int LOADING_ERR = 3;
    public static final int LOADING_NONE = 0;
    public static final int LOADING_START = 1;
    private static final String NOTFOUND = "adfurikun_notfound:";
    private static final String NOTFOUND_HTML = "<!doctype html> <html lang=\"ja\"> <head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no\"> <title>NotFound</title> <style> html,body,div,span,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,abbr,address,cite,code,del,dfn,em,img,ins,kbd,q,samp,small,strong,sub,sup,var,b,i,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,figcaption,figure,footer,header,hgroup,menu,nav,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;outline:0;font-size:100%;vertical-align:baseline;background:transparent}body{line-height:1}article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{display:block}nav ul{list-style:none}blockquote,q{quotes:none}blockquote:before,blockquote:after,q:before,q:after{content:'';content:none}a{margin:0;padding:0;font-size:100%;vertical-align:baseline;background:transparent}mark{background-color:#ff9;color:#000;font-style:italic;font-weight:bold}del{text-decoration:line-through}abbr[title],dfn[title]{border-bottom:1px dotted;cursor:help}table{border-collapse:collapse;border-spacing:0}hr{display:block;height:1px;border:0;border-top:1px solid #cccccc;margin:1em 0;padding:0}input,select{vertical-align:middle}li{list-style:none}img{vertical-align:bottom}html{overflow-y:scroll}body{font:12px/1.2 \"ヒラギノ角ゴ Pro W3\", \"Hiragino Kaku Gothic Pro\", \"メイリオ\", Meiryo, \"MS UI Gotic\", \"MS PGothic\" ,Osaka, sans-serif}.clearfix:after{content:\".\";display:block;height:0;clear:both;visibility:hidden}.clearfix{display:inline-table;min-height:1%}* html .clearfix{height:1%}.clearfix{display:block}*{margin:0;padding:0}a{background:none;text-decoration:none}#notfound{text-align:center;padding:15px;font-size:14px;background:#f1f3ee}#notfound p{margin-top:5px;line-height:1.5}#notfound h1{background:URL(\"images/notfound.png\") no-repeat;background-size:100%;width:160px;height:140px;margin:0 auto;text-indent:-9999px} </style> </head> <body> <section id=\"notfound\"> <h1>NotFound</h1> [ERROR_CODE] <p>インターネットに接続できません。<br> 通信状況を確認してください。 </p> </section> </body> </html>";
    private static final String WEB_URL = "adfurikun_weburl:";
    public boolean isError;
    private API_Controller2 mAPI_Controller;
    private AdInfo.AdInfoDetail mAdInfoDetail;
    private String mAppID;
    private int mBannerKind;
    private String mClickUrl;
    private Context mContext;
    private String mImpPrice;
    private String mImpUrl;
    private boolean mIsEmptyAd;
    private boolean mIsWallAdLayout;
    private int mLoadingState;
    private LogUtil mLog;
    private OnActionListener mOnActionListener;
    private OnProgressListener mOnProgressListener;
    private String mRecClickParam;
    private String mRecImpParam;
    private WebViewClient mWebViewClient;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface OnActionListener {
        void adClick();

        void errorLoad();

        void successLoad();

        void windowClose();
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface OnProgressListener {
        void dismissProgress();

        void errorClose();

        void setProgress(int i);

        void startProgress();

        void stopProgress();
    }

    public AdWebView(Context context, OnActionListener onActionListener) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super(context);
        this.mBannerKind = 0;
        this.isError = false;
        this.mWebViewClient = new WebViewClient() { // from class: jp.tjkapp.adfurikunsdk.AdWebView.3
            Handler hand;
            private boolean timeout = true;
            private Runnable run = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.AdWebView.3.1
                @Override // java.lang.Runnable
                public void run() {
                    if (C12763.this.timeout) {
                        AdWebView.this.mLog.detail(Constants.TAG_NAME, "timeout: " + toString());
                        try {
                            AdWebView.this.stopLoading();
                            AdWebView.this.loadErrPage(1, -6);
                            AdWebView.this.reportClashLytics(0);
                        } catch (Error e) {
                        } catch (Exception e2) {
                        }
                    }
                }
            };

            @Override // android.webkit.WebViewClient
            public void onLoadResource(WebView webView, String str) {
                if (AdWebView.this.mAdInfoDetail != null && AdWebView.this.mAdInfoDetail.ext_act_url != null) {
                    int length = AdWebView.this.mAdInfoDetail.ext_act_url.length;
                    for (int i = 0; i < length; i++) {
                        if (str.startsWith(AdWebView.this.mAdInfoDetail.ext_act_url[i]) && AdWebView.this.checkLoadPage(webView, str, true)) {
                            return;
                        }
                    }
                }
                super.onLoadResource(webView, str);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (AdWebView.this.mOnActionListener != null) {
                    AdWebView.this.mOnActionListener.successLoad();
                }
                if (!AdWebView.BASE_URL.equals(str)) {
                    AdWebView.this.stopProgress();
                }
                this.timeout = false;
            }

            @Override // android.webkit.WebViewClient
            @SuppressLint({"HandlerLeak"})
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                this.timeout = true;
                if (!AdWebView.this.checkLoadPage(webView, str, true) && !AdWebView.BASE_URL.equals(str)) {
                    AdWebView.this.startProgress();
                }
                if (this.hand != null) {
                    this.hand.removeCallbacks(this.run);
                }
                this.hand = new Handler();
                this.hand.postDelayed(this.run, Constants.WEBVIEW_TIMEOUT);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                if (AdWebView.this.mOnProgressListener != null) {
                    AdWebView.this.mOnProgressListener.dismissProgress();
                }
                AdWebView.this.loadErrPage(1, i);
                AdWebView.this.reportClashLytics(1);
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (AdWebView.this.checkLoadPage(webView, str, false)) {
                    return true;
                }
                webView.loadUrl(str);
                return true;
            }
        };
        initialize(context, onActionListener);
        this.mContext = context;
    }

    private void cancelTask() {
        if (this.mAPI_Controller != null) {
            this.mAPI_Controller.cancelLoad();
            this.mAPI_Controller = null;
        }
        this.mLoadingState = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkLoadPage(WebView webView, String str, boolean z) {
        if (str != null) {
            int iIndexOf = str.indexOf(APP_URL);
            int iIndexOf2 = str.indexOf(WEB_URL);
            int iIndexOf3 = str.indexOf(NOTFOUND);
            if (iIndexOf != -1) {
                str = str.substring(APP_URL.length() + iIndexOf, str.length());
            } else if (iIndexOf2 != -1) {
                str = str.substring(WEB_URL.length() + iIndexOf2, str.length());
            } else if (iIndexOf3 != -1) {
                loadErrPage(4, -3);
                return true;
            }
            if (str.equals(BASE_URL)) {
                return false;
            }
            if (str.startsWith("file://")) {
                return false;
            }
        }
        if (this.mAdInfoDetail != null && this.mAdInfoDetail.wall_type == 1 && this.mAdInfoDetail.html.length() > 0 && this.mAdInfoDetail.html.equals(str)) {
            return false;
        }
        if (z) {
            webView.stopLoading();
        }
        if (this.mOnActionListener != null) {
            this.mOnActionListener.adClick();
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        try {
            getContext().startActivity(intent);
        } catch (Exception e) {
            this.mLog.debug_e(Constants.TAG_NAME, "failed url=" + str);
        }
        if (this.mOnProgressListener == null) {
            return true;
        }
        this.mOnProgressListener.dismissProgress();
        return true;
    }

    @SuppressLint({"NewApi"})
    private void initialize(Context context, OnActionListener onActionListener) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.mLog = LogUtil.getInstance(context);
        this.mIsWallAdLayout = false;
        setBackgroundColor(0);
        this.mOnProgressListener = null;
        this.mOnActionListener = onActionListener;
        this.mLoadingState = 0;
        this.mIsEmptyAd = false;
        this.mAPI_Controller = null;
        this.mImpPrice = BuildConfig.FLAVOR;
        this.mImpUrl = BuildConfig.FLAVOR;
        this.mClickUrl = BuildConfig.FLAVOR;
        this.mRecImpParam = BuildConfig.FLAVOR;
        this.mRecClickParam = BuildConfig.FLAVOR;
        this.mAppID = BuildConfig.FLAVOR;
        this.mAdInfoDetail = null;
        WebSettings settings = getSettings();
        if (settings != null) {
            settings.setJavaScriptEnabled(true);
            settings.setSaveFormData(false);
            settings.setSupportZoom(false);
            settings.setLoadWithOverviewMode(true);
            settings.setSupportMultipleWindows(false);
            settings.setAllowFileAccess(true);
            settings.setCacheMode(2);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            setVersionSetting(settings);
        }
        setScrollBarStyle(0);
        setVerticalScrollbarOverlay(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        onResume();
        setWebViewClient(this.mWebViewClient);
        setWebChromeClient(new WebChromeClient() { // from class: jp.tjkapp.adfurikunsdk.AdWebView.1
            @Override // android.webkit.WebChromeClient
            public void onCloseWindow(WebView webView) {
                super.onCloseWindow(webView);
                if (AdWebView.this.mOnActionListener != null) {
                    AdWebView.this.mOnActionListener.windowClose();
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onConsoleMessage(String str, int i, String str2) {
                AdWebView.this.mLog.debug(Constants.TAG_NAME, "[ConsoleMessage]");
                AdWebView.this.mLog.debug(Constants.TAG_NAME, " ---- " + str);
                AdWebView.this.mLog.debug(Constants.TAG_NAME, " ---- From line " + i + " of " + str2);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                onConsoleMessage(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId());
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
                WebView webView2 = new WebView(AdWebView.this.getContext());
                WebSettings settings2 = webView2.getSettings();
                if (settings2 != null) {
                    settings2.setJavaScriptEnabled(true);
                }
                webView2.setWebViewClient(AdWebView.this.mWebViewClient);
                ((WebView.WebViewTransport) message.obj).setWebView(webView2);
                message.sendToTarget();
                return false;
            }

            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (AdWebView.this.mOnProgressListener != null) {
                    AdWebView.this.mOnProgressListener.setProgress(i);
                }
            }
        });
    }

    private void loadApiAd() {
        if (this.mAdInfoDetail != null) {
            try {
                this.mAPI_Controller = new API_Controller2(new API_Controller2.APILoadListener() { // from class: jp.tjkapp.adfurikunsdk.AdWebView.2
                    @Override // jp.tjkapp.adfurikunsdk.API_Controller2.APILoadListener
                    public void onLoadFinish(API_Controller2.API_ResultParam aPI_ResultParam) {
                        if (aPI_ResultParam.err != 0 || aPI_ResultParam.html == null || aPI_ResultParam.html.length() <= 0) {
                            AdWebView.this.mLoadingState = 3;
                            return;
                        }
                        AdWebView.this.loadDataWithBaseURL(AdWebView.ASSETS_BASE_URL, aPI_ResultParam.html, "text/html", "UTF-8", null);
                        AdWebView.this.mImpPrice = aPI_ResultParam.imp_price;
                        AdWebView.this.mImpUrl = aPI_ResultParam.imp_url;
                        AdWebView.this.mClickUrl = aPI_ResultParam.click_url;
                        AdWebView.this.mRecImpParam = aPI_ResultParam.rec_imp_param;
                        AdWebView.this.mRecClickParam = aPI_ResultParam.rec_click_param;
                        AdWebView.this.mLoadingState = 2;
                    }
                }, getContext(), this.mAppID, this.mBannerKind, this.mAdInfoDetail);
                this.mAPI_Controller.forceLoad();
            } catch (Exception e) {
                this.mLog.debug_e(Constants.TAG_NAME, e);
            }
        }
    }

    private void loadHtml() {
        this.mAdInfoDetail.html = ApiAccessUtil.replaceIDFA(getContext(), this.mAdInfoDetail.html, this.mLog);
        if (!ApiAccessUtil.isConnected(getContext())) {
            loadErrPage(1, -6);
            return;
        }
        String adInfoDetailFilePath = FileUtil.getAdInfoDetailFilePath(getContext(), this.mAppID, this.mAdInfoDetail);
        File file = new File(adInfoDetailFilePath);
        if (!file.exists()) {
            FileUtil.saveStringFile(adInfoDetailFilePath, this.mAdInfoDetail.html);
            file = new File(adInfoDetailFilePath);
        }
        if (!file.exists()) {
            loadDataWithBaseURL(BASE_URL, this.mAdInfoDetail.html, "text/html", "UTF-8", null);
            return;
        }
        try {
            loadUrl(Uri.fromFile(file).toString());
        } catch (NullPointerException e) {
            loadDataWithBaseURL(BASE_URL, this.mAdInfoDetail.html, "text/html", "UTF-8", null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportClashLytics(int i) {
        ApiAccessUtil.reportClashLytics(this.mContext, (i == 0 ? "WEBVIEW_TIMEOUT " : "WEBVIEW_ERROR ") + this.mAdInfoDetail.adnetwork_key);
    }

    private void setVersionSetting(WebSettings webSettings) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (webSettings != null) {
            if (Build.VERSION.SDK_INT < 18) {
                webSettings.setSavePassword(false);
            }
            if (Build.VERSION.SDK_INT < 19) {
                webSettings.setDatabasePath(getContext().getDir("localstorage", 0).getPath());
            }
            if (Build.VERSION.SDK_INT >= 16) {
                try {
                    Method method = webSettings.getClass().getMethod("setAllowUniversalAccessFromFileURLs", Boolean.TYPE);
                    if (method != null) {
                        method.invoke(webSettings, true);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startProgress() {
        if (this.mOnProgressListener != null) {
            this.mOnProgressListener.startProgress();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopProgress() {
        if (this.mOnProgressListener != null) {
            this.mOnProgressListener.stopProgress();
        }
    }

    @Override // android.webkit.WebView
    public boolean canGoBack() {
        return false;
    }

    @Override // android.webkit.WebView
    public void destroy() {
        super.destroy();
        cancelTask();
    }

    protected AdInfo.AdInfoDetail getAdInfoDetail() {
        return this.mAdInfoDetail;
    }

    public String getAppID() {
        return this.mAppID;
    }

    protected String getClickUrl() {
        return this.mClickUrl;
    }

    protected String getImpPrice() {
        return this.mImpPrice;
    }

    protected String getImpUrl() {
        return this.mImpUrl;
    }

    protected int getLoadingState() {
        return this.mLoadingState;
    }

    protected String getRecClickParam() {
        return this.mRecClickParam;
    }

    protected String getRecImpParam() {
        return this.mRecImpParam;
    }

    protected boolean isEmptyAd() {
        return this.mIsEmptyAd;
    }

    protected void loadErrPage(int i, int i2) {
        this.mLoadingState = 3;
        this.isError = true;
        if (this.mOnActionListener != null) {
            this.mOnActionListener.errorLoad();
        }
        if (i == 2 || (i == 3 && i2 == -4 && AdInfo.getAdType(this.mBannerKind) != 2)) {
            this.mIsEmptyAd = true;
            loadDataWithBaseURL(BASE_URL, BuildConfig.FLAVOR, "text/html", "UTF-8", null);
            return;
        }
        String fillerFilePath = FileUtil.getFillerFilePath(getContext(), this.mAppID);
        try {
            if (new File(fillerFilePath).exists()) {
                loadUrl("file://" + fillerFilePath);
            } else {
                stopLoading();
            }
        } catch (Error e) {
        } catch (Exception e2) {
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void saveText(String str) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/AAAAAAAA/");
        try {
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (SecurityException e) {
            this.mLog.detail(Constants.TAG_NAME, e.getMessage());
        }
        try {
            FileUtil.saveStringFile(Environment.getExternalStorageDirectory().getPath() + "/AAAAAAAA//" + (UUID.randomUUID().toString() + ".html"), str);
        } catch (Exception e2) {
            this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
        }
    }

    protected void setAdInfoDetail(AdInfo.AdInfoDetail adInfoDetail, int i) {
        this.mAdInfoDetail = adInfoDetail;
        this.mBannerKind = i;
        stopProgress();
        cancelTask();
        this.mLoadingState = 1;
        this.mIsEmptyAd = false;
        this.mImpPrice = BuildConfig.FLAVOR;
        this.mImpUrl = BuildConfig.FLAVOR;
        this.mClickUrl = BuildConfig.FLAVOR;
        this.mRecImpParam = BuildConfig.FLAVOR;
        this.mRecClickParam = BuildConfig.FLAVOR;
        if (this.mAdInfoDetail != null) {
            startProgress();
            this.isError = false;
            if (ApiAccessUtil.isConnected(getContext())) {
                int i2 = this.mAdInfoDetail.wall_type;
                if (i2 == 3) {
                    loadHtml();
                } else if (i2 == 2) {
                    loadApiAd();
                    return;
                } else if (i2 == 1) {
                    try {
                        loadUrl(this.mAdInfoDetail.html);
                    } catch (Error e) {
                        loadErrPage(5, -5);
                    } catch (Exception e2) {
                        loadErrPage(5, -5);
                    }
                } else {
                    loadErrPage(3, -1);
                }
            } else {
                loadErrPage(1, -6);
            }
        } else {
            loadErrPage(2, -5);
        }
        if (this.mLoadingState != 3) {
            this.mLoadingState = 2;
        }
    }

    protected void setAdfurikunAppKey(String str) {
        this.mAppID = str;
    }

    protected void setIsWallAdLayout(boolean z) {
        this.mIsWallAdLayout = z;
    }

    protected void setOnProgressListener(OnProgressListener onProgressListener) {
        this.mOnProgressListener = onProgressListener;
    }
}
