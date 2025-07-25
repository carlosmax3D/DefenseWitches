package com.tapjoy.mraid.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import com.google.android.gms.drive.DriveFile;
import com.tapjoy.TapjoyCache;
import com.tapjoy.TapjoyCacheMap;
import com.tapjoy.TapjoyCachedAssetData;
import com.tapjoy.TapjoyHttpURLResponse;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyURLConnection;
import com.tapjoy.TapjoyUtil;
import com.tapjoy.mraid.controller.Abstract;
import com.tapjoy.mraid.controller.Utility;
import com.tapjoy.mraid.listener.MraidViewListener;
import com.tapjoy.mraid.listener.Player;
import com.tapjoy.mraid.util.MraidPlayer;
import com.tapjoy.mraid.util.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.regex.Pattern;
import jp.stargarage.g2metrics.BuildConfig;

public class MraidView extends WebView implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final String ACTION_KEY = "action";
    private static final String AD_PATH = "AD_PATH";
    protected static final int BACKGROUND_ID = 101;
    private static final String CURRENT_FILE = "_current_file";
    public static final String DIMENSIONS = "expand_dimensions";
    private static final String ERROR_ACTION = "action";
    private static final String ERROR_MESSAGE = "message";
    private static final String EXPAND_PROPERTIES = "expand_properties";
    public static final String EXPAND_URL = "expand_url";
    private static final int MESSAGE_CLOSE = 1001;
    private static final int MESSAGE_EXPAND = 1004;
    private static final int MESSAGE_HIDE = 1002;
    private static final int MESSAGE_OPEN = 1006;
    private static final int MESSAGE_PLAY_AUDIO = 1008;
    private static final int MESSAGE_PLAY_VIDEO = 1007;
    private static final int MESSAGE_RAISE_ERROR = 1009;
    private static final int MESSAGE_RESIZE = 1000;
    private static final int MESSAGE_RESIZE_ORIENTATION = 1010;
    private static final int MESSAGE_SEND_EXPAND_CLOSE = 1005;
    private static final int MESSAGE_SHOW = 1003;
    public static final int MRAID_ID = 102;
    private static final String NO_CONNECTION_HTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\"><title>Connection not Established</title></head><h2>Connection Not Properly Established</h2><body></body></html>";
    protected static final int PLACEHOLDER_ID = 100;
    public static final String PLAYER_PROPERTIES = "player_properties";
    private static final String RESIZE_ALLOWOFFSCREEN = "resize_allowOffScreen";
    private static final String RESIZE_CUSTOMCLOSEPOSITION = "resize_customClosePostition";
    private static final String RESIZE_HEIGHT = "resize_height";
    private static final String RESIZE_WIDTH = "resize_width";
    private static final String RESIZE_X = "resize_x";
    private static final String RESIZE_Y = "resize_y";
    private static final String TAG = "MRAIDView";
    private static int[] attrs = {16843039, 16843040};
    private static String mScriptPath;
    private static MraidPlayer player;
    private static final String[] videoFormats = {".mp4", ".3gp", ".mpg"};
    private boolean bGotLayoutParams;
    private boolean bKeyboardOut;
    private boolean bPageFinished;
    /* access modifiers changed from: private */
    public customCloseState closeButtonState = customCloseState.UNKNOWN;
    /* access modifiers changed from: private */
    public Context ctx;
    private String initialExpandUrl = null;
    private boolean initialLoadUrl = true;
    private boolean isMraid = false;
    private int lastScreenHeight = 0;
    private int lastScreenWidth = 0;
    private int mContentViewHeight;
    /* access modifiers changed from: private */
    public int mDefaultHeight;
    /* access modifiers changed from: private */
    public int mDefaultWidth;
    private int mDefaultX;
    private int mDefaultY;
    /* access modifiers changed from: private */
    public float mDensity;
    private GestureDetector mGestureDetector;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            switch (message.what) {
                case 1000:
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) MraidView.this.getLayoutParams();
                    if (marginLayoutParams != null) {
                        MraidView.this.removeCloseImageButton();
                        VIEW_STATE unused = MraidView.this.mViewState = VIEW_STATE.RESIZED;
                        marginLayoutParams.height = data.getInt(MraidView.RESIZE_HEIGHT, marginLayoutParams.height);
                        marginLayoutParams.width = data.getInt(MraidView.RESIZE_WIDTH, marginLayoutParams.width);
                        marginLayoutParams.leftMargin = data.getInt(MraidView.RESIZE_X, marginLayoutParams.leftMargin);
                        marginLayoutParams.topMargin = data.getInt(MraidView.RESIZE_Y, marginLayoutParams.topMargin);
                        MraidView.this.injectMraidJavaScript("window.mraidview.fireChangeEvent({ state: 'resized', size: { width: " + marginLayoutParams.width + ", " + "height: " + marginLayoutParams.height + ", " + "x: " + marginLayoutParams.leftMargin + ", " + "y: " + marginLayoutParams.topMargin + "}});");
                        MraidView.this.requestLayout();
                        MraidView.this.repositionCloseButton(data.getString(MraidView.RESIZE_CUSTOMCLOSEPOSITION));
                        MraidView.this.showCloseImageButton();
                    }
                    if (MraidView.this.mListener != null) {
                        MraidView.this.mListener.onResize();
                        break;
                    }
                    break;
                case 1001:
                    switch (AnonymousClass7.$SwitchMap$com$tapjoy$mraid$view$MraidView$VIEW_STATE[MraidView.this.mViewState.ordinal()]) {
                        case 1:
                            MraidView.this.closeResized();
                            break;
                        case 2:
                            MraidView.this.closeExpanded();
                            break;
                        case 3:
                            if (MraidView.this.placement != PLACEMENT_TYPE.INLINE) {
                                MraidView.this.closeWindow();
                                break;
                            }
                            break;
                    }
                case 1002:
                    MraidView.this.setVisibility(4);
                    MraidView.this.injectMraidJavaScript("window.mraidview.fireChangeEvent({ state: 'hidden' });");
                    break;
                case MraidView.MESSAGE_SHOW /*1003*/:
                    MraidView.this.injectMraidJavaScript("window.mraidview.fireChangeEvent({ state: 'default' });");
                    MraidView.this.setVisibility(0);
                    break;
                case MraidView.MESSAGE_EXPAND /*1004*/:
                    MraidView.this.doExpand(data);
                    break;
                case MraidView.MESSAGE_SEND_EXPAND_CLOSE /*1005*/:
                    if (MraidView.this.mListener != null) {
                        MraidView.this.mListener.onExpandClose();
                        break;
                    }
                    break;
                case MraidView.MESSAGE_OPEN /*1006*/:
                    VIEW_STATE unused2 = MraidView.this.mViewState = VIEW_STATE.LEFT_BEHIND;
                    break;
                case MraidView.MESSAGE_PLAY_VIDEO /*1007*/:
                    MraidView.this.playVideoImpl(data);
                    break;
                case MraidView.MESSAGE_PLAY_AUDIO /*1008*/:
                    MraidView.this.playAudioImpl(data);
                    break;
                case MraidView.MESSAGE_RAISE_ERROR /*1009*/:
                    MraidView.this.injectMraidJavaScript("window.mraidview.fireErrorEvent(\"" + data.getString("message") + "\", \"" + data.getString("action") + "\")");
                    break;
                case MraidView.MESSAGE_RESIZE_ORIENTATION /*1010*/:
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) MraidView.this.getLayoutParams();
                    if (marginLayoutParams2 != null) {
                        MraidView.this.removeCloseImageButton();
                        marginLayoutParams2.height = data.getInt(MraidView.RESIZE_HEIGHT, marginLayoutParams2.height);
                        marginLayoutParams2.width = data.getInt(MraidView.RESIZE_WIDTH, marginLayoutParams2.width);
                        String str = "window.mraidview.fireChangeEvent({ state: '" + MraidView.this.getState() + "'," + " size: { width: " + ((int) (((float) marginLayoutParams2.width) / MraidView.this.mDensity)) + ", " + "height: " + ((int) (((float) marginLayoutParams2.height) / MraidView.this.mDensity)) + "}" + "});";
                        TapjoyLog.i(MraidView.TAG, "resize: injection: " + str);
                        MraidView.this.injectMraidJavaScript(str);
                        MraidView.this.requestLayout();
                        MraidView.this.repositionCloseButton(data.getString(MraidView.RESIZE_CUSTOMCLOSEPOSITION));
                        if (MraidView.this.placement != PLACEMENT_TYPE.INLINE && MraidView.this.closeButtonState == customCloseState.OPEN) {
                            MraidView.this.showCloseImageButton();
                        }
                    }
                    if (MraidView.this.mListener != null) {
                        MraidView.this.mListener.onResize();
                        break;
                    }
                    break;
            }
            super.handleMessage(message);
        }
    };
    private int mIndex;
    private int mInitLayoutHeight;
    private int mInitLayoutWidth;
    /* access modifiers changed from: private */
    public MraidViewListener mListener;
    private String mLocalFilePath;
    private TimeOut mTimeOut;
    /* access modifiers changed from: private */
    public Utility mUtilityController;
    /* access modifiers changed from: private */
    public VIEW_STATE mViewState = VIEW_STATE.DEFAULT;
    WebChromeClient mWebChromeClient = new WebChromeClient() {
        public void onCloseWindow(WebView webView) {
            super.onCloseWindow(webView);
            MraidView.this.closeWindow();
        }

        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return MraidView.this.mListener != null ? MraidView.this.mListener.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
        }

        public void onHideCustomView() {
            super.onHideCustomView();
        }

        public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
            TapjoyLog.d(MraidView.TAG, str2);
            return false;
        }

        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
            TapjoyLog.i(MraidView.TAG, "-- onShowCustomView --");
            super.onShowCustomView(view, customViewCallback);
            WebChromeClient.CustomViewCallback unused = MraidView.this.videoViewCallback = customViewCallback;
            if (view instanceof FrameLayout) {
                FrameLayout frameLayout = (FrameLayout) view;
                if ((frameLayout.getFocusedChild() instanceof VideoView) && (MraidView.this.ctx instanceof Activity)) {
                    Activity activity = (Activity) MraidView.this.ctx;
                    VideoView unused2 = MraidView.this.videoView = (VideoView) frameLayout.getFocusedChild();
                    frameLayout.removeView(MraidView.this.videoView);
                    if (MraidView.this.videoRelativeLayout == null) {
                        RelativeLayout unused3 = MraidView.this.videoRelativeLayout = new RelativeLayout(MraidView.this.ctx);
                        MraidView.this.videoRelativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                        MraidView.this.videoRelativeLayout.setBackgroundColor(-16777216);
                    }
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
                    layoutParams.addRule(13);
                    MraidView.this.videoView.setLayoutParams(layoutParams);
                    ProgressBar unused4 = MraidView.this.progressBar = new ProgressBar(MraidView.this.ctx, (AttributeSet) null, 16842874);
                    MraidView.this.progressBar.setVisibility(0);
                    RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                    layoutParams2.addRule(13);
                    MraidView.this.progressBar.setLayoutParams(layoutParams2);
                    MraidView.this.videoRelativeLayout.addView(MraidView.this.videoView);
                    MraidView.this.videoRelativeLayout.addView(MraidView.this.progressBar);
                    activity.getWindow().addContentView(MraidView.this.videoRelativeLayout, new ViewGroup.LayoutParams(-1, -1));
                    new Thread(new VideoLoadingThread()).start();
                    MraidView.this.setVisibility(8);
                    MraidView.this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            TapjoyLog.i(MraidView.TAG, "** ON PREPARED **");
                            TapjoyLog.i(MraidView.TAG, "isPlaying: " + mediaPlayer.isPlaying());
                            if (!mediaPlayer.isPlaying()) {
                                mediaPlayer.start();
                            }
                        }
                    });
                    MraidView.this.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            TapjoyLog.i(MraidView.TAG, "** ON COMPLETION **");
                            MraidView.this.videoViewCleanup();
                        }
                    });
                    MraidView.this.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                            TapjoyLog.i(MraidView.TAG, "** ON ERROR **");
                            MraidView.this.videoViewCleanup();
                            return false;
                        }
                    });
                    MraidView.this.videoView.start();
                }
            }
        }
    };
    WebViewClient mWebViewClient = new WebViewClient() {
        public void onLoadResource(WebView webView, String str) {
        }

        public void onPageFinished(WebView webView, String str) {
            if (MraidView.this.mListener != null) {
                MraidView.this.mListener.onPageFinished(webView, str);
            }
            int unused = MraidView.this.mDefaultHeight = (int) (((float) MraidView.this.getHeight()) / MraidView.this.mDensity);
            int unused2 = MraidView.this.mDefaultWidth = (int) (((float) MraidView.this.getWidth()) / MraidView.this.mDensity);
            MraidView.this.mUtilityController.init(MraidView.this.mDensity);
            MraidView.this.createCloseImageButton();
            if (MraidView.this.placement == PLACEMENT_TYPE.INLINE) {
                MraidView.this.removeCloseImageButton();
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (MraidView.this.mListener != null) {
                MraidView.this.mListener.onPageStarted(webView, str, bitmap);
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            if (MraidView.this.mListener != null) {
                MraidView.this.mListener.onReceivedError(webView, i, str, str2);
            }
            TapjoyLog.d(MraidView.TAG, "error:" + str);
            super.onReceivedError(webView, i, str, str2);
        }

        public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
            if (TapjoyCache.getInstance() != null) {
                TapjoyCacheMap cachedData = TapjoyCache.getInstance().getCachedData();
                if (cachedData.containsKey(str)) {
                    if (new File(((TapjoyCachedAssetData) cachedData.get(str)).getLocalFilePath()).exists()) {
                        WebResourceResponse access$1500 = MraidView.this.getWebResourceResponseFromCache(str);
                        if (access$1500 != null) {
                            return access$1500;
                        }
                    } else {
                        TapjoyCache.getInstance().removeAssetFromCache(str);
                    }
                }
            }
            return super.shouldInterceptRequest(webView, str);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            TapjoyLog.i(MraidView.TAG, "shouldOverrideUrlLoading: " + str);
            if (MraidView.this.mListener != null && MraidView.this.mListener.shouldOverrideUrlLoading(webView, str)) {
                return true;
            }
            Uri parse = Uri.parse(str);
            try {
                if (str.startsWith("mraid")) {
                    return super.shouldOverrideUrlLoading(webView, str);
                }
                if (str.startsWith("tel:")) {
                    Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(str));
                    intent.addFlags(DriveFile.MODE_READ_ONLY);
                    MraidView.this.getContext().startActivity(intent);
                    return true;
                } else if (str.startsWith("mailto:")) {
                    Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str));
                    intent2.addFlags(DriveFile.MODE_READ_ONLY);
                    MraidView.this.getContext().startActivity(intent2);
                    return true;
                } else {
                    Intent intent3 = new Intent();
                    intent3.setAction("android.intent.action.VIEW");
                    intent3.setData(parse);
                    intent3.addFlags(DriveFile.MODE_READ_ONLY);
                    MraidView.this.getContext().startActivity(intent3);
                    return true;
                }
            } catch (Exception e) {
                try {
                    Intent intent4 = new Intent();
                    intent4.setAction("android.intent.action.VIEW");
                    intent4.setData(parse);
                    intent4.addFlags(DriveFile.MODE_READ_ONLY);
                    MraidView.this.getContext().startActivity(intent4);
                    return true;
                } catch (Exception e2) {
                    return false;
                }
            }
        }
    };
    private Thread orientationThread = null;
    private int originalRequestedOrientation;
    /* access modifiers changed from: private */
    public PLACEMENT_TYPE placement;
    /* access modifiers changed from: private */
    public ProgressBar progressBar;
    private final HashSet<String> registeredProtocols = new HashSet<>();
    /* access modifiers changed from: private */
    public RelativeLayout videoRelativeLayout;
    /* access modifiers changed from: private */
    public VideoView videoView;
    /* access modifiers changed from: private */
    public WebChromeClient.CustomViewCallback videoViewCallback;
    /* access modifiers changed from: private */
    public boolean viewDetached = false;

    /* renamed from: com.tapjoy.mraid.view.MraidView$7  reason: invalid class name */
    static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] $SwitchMap$com$tapjoy$mraid$view$MraidView$VIEW_STATE = new int[VIEW_STATE.values().length];

        static {
            try {
                $SwitchMap$com$tapjoy$mraid$view$MraidView$VIEW_STATE[VIEW_STATE.RESIZED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$tapjoy$mraid$view$MraidView$VIEW_STATE[VIEW_STATE.EXPANDED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$tapjoy$mraid$view$MraidView$VIEW_STATE[VIEW_STATE.DEFAULT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public enum Action {
        PLAY_AUDIO,
        PLAY_VIDEO
    }

    private class MraidHTTPTask extends AsyncTask<String, Void, Void> {
        TapjoyHttpURLResponse httpResult;
        TapjoyURLConnection tapjoyConnection;
        String url;

        private MraidHTTPTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... strArr) {
            this.url = strArr[0];
            try {
                this.tapjoyConnection = new TapjoyURLConnection();
                this.httpResult = this.tapjoyConnection.getResponseFromURL(this.url);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            try {
                if (this.httpResult.statusCode == 0 || this.httpResult.response == null) {
                    TapjoyLog.e(MraidView.TAG, "Connection not properly established");
                    if (MraidView.this.mListener != null) {
                        MraidView.this.mListener.onReceivedError(MraidView.this, 0, "Connection not properly established", this.url);
                    }
                } else if (this.httpResult.statusCode != 302 || this.httpResult.redirectURL == null || this.httpResult.redirectURL.length() <= 0) {
                    MraidView.this.loadDataWithBaseURL(this.url, this.httpResult.response, "text/html", "utf-8", this.url);
                } else {
                    TapjoyLog.i(MraidView.TAG, "302 redirectURL detected: " + this.httpResult.redirectURL);
                    MraidView.this.loadUrlStandard(this.httpResult.redirectURL);
                }
            } catch (Exception e) {
                TapjoyLog.w(MraidView.TAG, "error in loadURL " + e);
                e.printStackTrace();
            }
        }
    }

    public static abstract class NewLocationReciever {
        public abstract void OnNewLocation(VIEW_STATE view_state);
    }

    private class OrientationThread implements Runnable {
        public OrientationThread() {
        }

        public void run() {
            while (!MraidView.this.viewDetached) {
                try {
                    Thread.sleep(250);
                    MraidView.this.checkForOrientationChange();
                } catch (Exception e) {
                }
            }
        }
    }

    public enum PLACEMENT_TYPE {
        INLINE,
        INTERSTITIAL
    }

    class ScrollEater extends GestureDetector.SimpleOnGestureListener {
        ScrollEater() {
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return true;
        }
    }

    class TimeOut extends TimerTask {
        int mCount = 0;
        int mProgress = 0;

        TimeOut() {
        }

        public void run() {
            int progress = MraidView.this.getProgress();
            if (progress == 100) {
                cancel();
            } else if (this.mProgress == progress) {
                this.mCount++;
                if (this.mCount == 3) {
                    try {
                        MraidView.this.stopLoading();
                    } catch (Exception e) {
                        TapjoyLog.w(MraidView.TAG, "error in stopLoading");
                        e.printStackTrace();
                    }
                    cancel();
                }
            }
            this.mProgress = progress;
        }
    }

    public enum VIEW_STATE {
        DEFAULT,
        RESIZED,
        EXPANDED,
        HIDDEN,
        LEFT_BEHIND,
        OPENED
    }

    private class VideoLoadingThread implements Runnable {

        private class VideoRunningThread implements Runnable {
            private boolean playing = false;

            public VideoRunningThread() {
            }

            public void run() {
                while (MraidView.this.videoView != null) {
                    try {
                        Thread.sleep(100);
                        if (this.playing != MraidView.this.videoView.isPlaying()) {
                            this.playing = MraidView.this.videoView.isPlaying();
                            MraidView.this.loadUrl("javascript:try{Tapjoy.AdUnit.dispatchEvent('" + (this.playing ? "videoplay" : "videopause") + "')}catch(e){}");
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        public VideoLoadingThread() {
        }

        public void run() {
            int i = 0;
            while (MraidView.this.videoView != null && !MraidView.this.videoView.isPlaying()) {
                try {
                    Thread.sleep(50);
                    i += 50;
                    if (i >= 10000) {
                        break;
                    }
                } catch (Exception e) {
                }
            }
            ((Activity) MraidView.this.ctx).runOnUiThread(new Runnable() {
                public void run() {
                    if (MraidView.this.progressBar != null) {
                        MraidView.this.progressBar.setVisibility(8);
                    }
                    new Thread(new VideoRunningThread()).start();
                }
            });
        }
    }

    public enum customCloseState {
        HIDDEN,
        OPEN,
        UNKNOWN
    }

    public MraidView(Context context) {
        super(context);
        this.ctx = context;
        initialize();
    }

    public MraidView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize();
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, attrs);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(1, -1);
        if (dimensionPixelSize > 0 && dimensionPixelSize2 > 0) {
            this.mUtilityController.setMaxSize(dimensionPixelSize, dimensionPixelSize2);
        }
        obtainStyledAttributes.recycle();
    }

    public MraidView(Context context, MraidViewListener mraidViewListener) {
        super(context);
        setListener(mraidViewListener);
        this.ctx = context;
        initialize();
    }

    private FrameLayout changeContentArea(Abstract.Dimensions dimensions) {
        FrameLayout frameLayout = (FrameLayout) getRootView().findViewById(16908290);
        ViewGroup viewGroup = (ViewGroup) getParent();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dimensions.width, dimensions.height);
        layoutParams.topMargin = dimensions.x;
        layoutParams.leftMargin = dimensions.y;
        int childCount = viewGroup.getChildCount();
        int i = 0;
        while (i < childCount && viewGroup.getChildAt(i) != this) {
            i++;
        }
        this.mIndex = i;
        FrameLayout frameLayout2 = new FrameLayout(getContext());
        frameLayout2.setId(100);
        viewGroup.addView(frameLayout2, i, new ViewGroup.LayoutParams(getWidth(), getHeight()));
        viewGroup.removeView(this);
        FrameLayout frameLayout3 = new FrameLayout(getContext());
        frameLayout3.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                TapjoyLog.i(MraidView.TAG, "background touch called");
                return true;
            }
        });
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        frameLayout3.setId(101);
        frameLayout3.setPadding(dimensions.x, dimensions.y, 0, 0);
        frameLayout3.addView(this, layoutParams);
        frameLayout.addView(frameLayout3, layoutParams2);
        return frameLayout3;
    }

    /* access modifiers changed from: private */
    public void checkForOrientationChange() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        if (width != this.lastScreenWidth || height != this.lastScreenHeight) {
            if ((getPlacementType() == PLACEMENT_TYPE.INLINE && getViewState() == VIEW_STATE.EXPANDED) || getPlacementType() == PLACEMENT_TYPE.INTERSTITIAL) {
                resizeOrientation(width, height, "top-right", true);
            }
        }
    }

    private static boolean checkForVideo(String str) {
        for (String endsWith : videoFormats) {
            if (str.endsWith(endsWith)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void closeResized() {
        this.mViewState = VIEW_STATE.DEFAULT;
        if (this.mListener != null) {
            this.mListener.onResizeClose();
        }
        String str = "window.mraidview.fireChangeEvent({ state: 'default', size: { width: " + this.mDefaultWidth + ", " + "height: " + this.mDefaultHeight + ", " + "x:0" + "," + "y:0" + "}" + "});";
        TapjoyLog.d(TAG, "closeResized: injection: " + str);
        injectMraidJavaScript(str);
        repositionCloseButton("top-right");
        resetLayout();
    }

    /* access modifiers changed from: private */
    public void closeWindow() {
        if (this.mListener != null) {
            this.mListener.onClose();
        }
        ((ViewGroup) getParent()).removeView(this);
    }

    /* access modifiers changed from: private */
    public void doExpand(Bundle bundle) {
        if (this.mViewState != VIEW_STATE.EXPANDED) {
            Abstract.Dimensions dimensions = (Abstract.Dimensions) bundle.getParcelable(DIMENSIONS);
            String string = bundle.getString(EXPAND_URL);
            Abstract.Properties properties = (Abstract.Properties) bundle.getParcelable(EXPAND_PROPERTIES);
            if (URLUtil.isValidUrl(string)) {
                loadUrl(string);
            }
            FrameLayout changeContentArea = changeContentArea(dimensions);
            if (properties.useBackground) {
                changeContentArea.setBackgroundColor(properties.backgroundColor | (((int) (properties.backgroundOpacity * 255.0f)) * DriveFile.MODE_READ_ONLY));
            }
            if (!properties.useCustomClose) {
                showCloseImageButton();
            }
            String str = "window.mraidview.fireChangeEvent({ state: 'expanded', size: { width: " + ((int) (((float) dimensions.width) / this.mDensity)) + ", " + "height: " + ((int) (((float) dimensions.height) / this.mDensity)) + "," + "x:0," + "y:0" + "}" + " });";
            TapjoyLog.d(TAG, "doExpand: injection: " + str);
            injectMraidJavaScript(str);
            this.mViewState = VIEW_STATE.EXPANDED;
            checkForOrientationChange();
            if (this.mListener != null) {
                this.mListener.onExpand();
            }
        }
    }

    private int getContentViewHeight() {
        View findViewById = getRootView().findViewById(16908290);
        if (findViewById != null) {
            return findViewById.getHeight();
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public WebResourceResponse getWebResourceResponseFromCache(String str) {
        TapjoyCachedAssetData cachedDataForURL = TapjoyCache.getInstance().getCachedDataForURL(str);
        if (cachedDataForURL == null) {
            return null;
        }
        try {
            return new WebResourceResponse(cachedDataForURL.getMimeType(), "UTF-8", new FileInputStream(cachedDataForURL.getLocalFilePath()));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private void initAndPlayVideo(String str) {
        Abstract.Dimensions dimensions = new Abstract.Dimensions();
        dimensions.x = 0;
        dimensions.y = 0;
        dimensions.width = getWidth();
        dimensions.height = getHeight();
        playVideo(str, false, true, true, false, dimensions, Abstract.FULL_SCREEN, Abstract.EXIT);
    }

    private boolean isRegisteredProtocol(Uri uri) {
        String scheme = uri.getScheme();
        if (scheme == null) {
            return false;
        }
        Iterator<String> it = this.registeredProtocols.iterator();
        while (it.hasNext()) {
            if (it.next().equalsIgnoreCase(scheme)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void repositionCloseButton(String str) {
        if (str != null) {
            String str2 = null;
            if (str.equals("top-right")) {
                str2 = "document.getElementById(\"closeButton\").style.right = 1;document.getElementById(\"closeButton\").style.top = 1;document.getElementById(\"closeButton\").style.bottom = mraid.getSize().height -36;document.getElementById(\"closeButton\").style.left = mraid.getSize().width -36";
            } else if (str.equals("top-center")) {
                str2 = "document.getElementById(\"closeButton\").style.right = mraid.getSize().width/2 - 18;document.getElementById(\"closeButton\").style.top = 1;document.getElementById(\"closeButton\").style.bottom = mraid.getSize().height -36;document.getElementById(\"closeButton\").style.left = mraid.getSize().width/2 -18";
            } else if (str.equals("top-left")) {
                str2 = "document.getElementById(\"closeButton\").style.right = mraid.getSize().width -36;document.getElementById(\"closeButton\").style.top = 1;document.getElementById(\"closeButton\").style.bottom = mraid.getSize().height -36;document.getElementById(\"closeButton\").style.left = 1";
            } else if (str.equals("center")) {
                str2 = "document.getElementById(\"closeButton\").style.right = mraid.getSize().width/2 - 18;document.getElementById(\"closeButton\").style.top = mraid.getSize().height/2 -18;document.getElementById(\"closeButton\").style.bottom = mraid.getSize().height/2 -18;document.getElementById(\"closeButton\").style.left = mraid.getSize().width/2 -18";
            } else if (str.equals("bottom-right")) {
                str2 = "document.getElementById(\"closeButton\").style.right = 1;document.getElementById(\"closeButton\").style.top = mraid.getSize().height -36;document.getElementById(\"closeButton\").style.bottom = 1;document.getElementById(\"closeButton\").style.left = mraid.getSize().width -36";
            } else if (str.equals("bottom-left")) {
                str2 = "document.getElementById(\"closeButton\").style.left = 1;document.getElementById(\"closeButton\").style.bottom = 1;document.getElementById(\"closeButton\").style.right = mraid.getSize().width -36;document.getElementById(\"closeButton\").style.top = mraid.getSize().height-36;";
            } else if (str.equals("bottom-center")) {
                str2 = "document.getElementById(\"closeButton\").style.bottom = 1;document.getElementById(\"closeButton\").style.right = mraid.getSize().width -36document.getElementById(\"closeButton\").style.right = mraid.getSize().width/2 -18;document.getElementById(\"closeButton\").style.top = mraid.getSize().height-36;";
            }
            if (str2 != null) {
                injectMraidJavaScript(str2);
            } else {
                TapjoyLog.d(TAG, "Reposition of close button failed.");
            }
        }
    }

    private void resetLayout() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (this.bGotLayoutParams) {
            layoutParams.height = this.mInitLayoutHeight;
            layoutParams.width = this.mInitLayoutWidth;
        }
        setVisibility(0);
        requestLayout();
    }

    private synchronized void setScriptPath() {
        TapjoyLog.d(TAG, " paths" + mScriptPath);
        if (mScriptPath == null && TapjoyUtil.getResource("mraid.js") == null) {
            mScriptPath = this.mUtilityController.copyTextFromJarIntoAssetDir("/js/mraid.js", "js/mraid.js");
        }
    }

    public void addJavascriptObject(Object obj, String str) {
        addJavascriptInterface(obj, str);
    }

    public void clearView() {
        reset();
        super.clearView();
    }

    public void close() {
        this.mHandler.sendEmptyMessage(1001);
    }

    /* access modifiers changed from: protected */
    public synchronized void closeExpanded() {
        resetContents();
        String str = "window.mraidview.fireChangeEvent({ state: 'default', size: { width: " + this.mDefaultWidth + ", " + "height: " + this.mDefaultHeight + "}" + "});";
        TapjoyLog.d(TAG, "closeExpanded: injection: " + str);
        injectMraidJavaScript(str);
        this.mViewState = VIEW_STATE.DEFAULT;
        this.mHandler.sendEmptyMessage(MESSAGE_SEND_EXPAND_CLOSE);
        setVisibility(0);
        removeCloseImageButton();
        ((Activity) getContext()).setRequestedOrientation(this.originalRequestedOrientation);
    }

    /* access modifiers changed from: protected */
    public void closeOpened(View view) {
        ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).removeView(view);
        requestLayout();
    }

    public void createCloseImageButton() {
        injectMraidJavaScript("window.mraidview.createCss();");
        TapjoyLog.d(TAG, "Creating close button.");
    }

    public void deregisterProtocol(String str) {
        if (str != null) {
            this.registeredProtocols.remove(str.toLowerCase());
        }
    }

    public void expand(Abstract.Dimensions dimensions, String str, Abstract.Properties properties) {
        Message obtainMessage = this.mHandler.obtainMessage(MESSAGE_EXPAND);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DIMENSIONS, dimensions);
        bundle.putString(EXPAND_URL, str);
        bundle.putParcelable(EXPAND_PROPERTIES, properties);
        obtainMessage.setData(bundle);
        this.mHandler.sendMessage(obtainMessage);
    }

    public customCloseState getCloseButtonState() {
        return this.closeButtonState;
    }

    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getContext().getSystemService("connectivity");
    }

    public PLACEMENT_TYPE getPlacementType() {
        return this.placement;
    }

    /* access modifiers changed from: package-private */
    public MraidPlayer getPlayer() {
        if (player != null) {
            player.releasePlayer();
        }
        player = new MraidPlayer(getContext());
        return player;
    }

    public String getSize() {
        return "{ width: " + ((int) Math.ceil((double) (((float) getWidth()) / this.mDensity))) + ", " + "height: " + ((int) Math.ceil((double) (((float) getHeight()) / this.mDensity))) + "}";
    }

    public String getState() {
        return this.mViewState.toString().toLowerCase();
    }

    public VIEW_STATE getViewState() {
        return this.mViewState;
    }

    public boolean hasMraidTag(String str) {
        return Pattern.compile("<\\s*script[^>]+mraid\\.js").matcher(str).find() || Pattern.compile("<\\s*script[^>]+ormma\\.js").matcher(str).find();
    }

    public void hide() {
        this.mHandler.sendEmptyMessage(1002);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public void initialize() {
        setPlacementType(PLACEMENT_TYPE.INTERSTITIAL);
        setScrollContainer(false);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        this.mGestureDetector = new GestureDetector(new ScrollEater());
        setBackgroundColor(0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.mDensity = displayMetrics.density;
        this.bPageFinished = false;
        if (getSettings() != null) {
            getSettings().setJavaScriptEnabled(true);
        }
        this.mUtilityController = new Utility(this, getContext());
        addJavascriptInterface(this.mUtilityController, "MRAIDUtilityControllerBridge");
        setWebViewClient(this.mWebViewClient);
        setWebChromeClient(this.mWebChromeClient);
        setScriptPath();
        this.mContentViewHeight = getContentViewHeight();
        if (getViewTreeObserver() != null) {
            getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
        this.lastScreenWidth = windowManager.getDefaultDisplay().getWidth();
        this.lastScreenHeight = windowManager.getDefaultDisplay().getHeight();
        this.originalRequestedOrientation = ((Activity) getContext()).getRequestedOrientation();
    }

    public void injectMraidJavaScript(String str) {
        if (str != null && this.isMraid) {
            loadUrl("javascript:" + str);
        }
    }

    public boolean isExpanded() {
        return this.mViewState == VIEW_STATE.EXPANDED;
    }

    public boolean isMraid() {
        return this.isMraid;
    }

    public boolean isPageFinished() {
        return this.bPageFinished;
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        if (str2 != null) {
            StringBuffer stringBuffer = new StringBuffer();
            int indexOf = str2.indexOf("<html>");
            this.isMraid = false;
            int indexOf2 = str2.indexOf("mraid.js");
            if (indexOf2 < 0) {
                indexOf2 = str2.indexOf("ormma.js");
            }
            int i = indexOf2;
            int i2 = indexOf2;
            if (indexOf2 <= 0 || !hasMraidTag(str2)) {
                stringBuffer.append(str2);
            } else {
                this.isMraid = true;
                int i3 = indexOf2;
                while (true) {
                    if (i3 < 0) {
                        break;
                    }
                    if (str2.substring(i3, i3 + 7).equals("<script")) {
                        i2 = i3;
                        break;
                    }
                    i3--;
                }
                int i4 = 0;
                while (true) {
                    if (i4 >= str2.length()) {
                        break;
                    }
                    if (str2.substring(indexOf2 + i4, indexOf2 + i4 + 2).equalsIgnoreCase("/>")) {
                        i = i4 + indexOf2 + 2;
                        break;
                    }
                    if (str2.substring(indexOf2 + i4, indexOf2 + i4 + 9).equalsIgnoreCase("</script>")) {
                        i = i4 + indexOf2 + 9;
                        break;
                    }
                    i4++;
                }
                if (indexOf < 0) {
                    TapjoyLog.d(TAG, "wrapping fragment");
                    stringBuffer.append("<html>");
                    stringBuffer.append("<head>");
                    stringBuffer.append("<meta name='viewport' content='user-scalable=no initial-scale=1.0' />");
                    stringBuffer.append("<title>Advertisement</title>");
                    stringBuffer.append("</head>");
                    stringBuffer.append("<body style=\"margin:0; padding:0; overflow:hidden; background-color:transparent;\">");
                    stringBuffer.append("<div align=\"center\"> ");
                    stringBuffer.append(str2.substring(0, i2));
                    stringBuffer.append("<script type=text/javascript>");
                    String str6 = (String) TapjoyUtil.getResource("mraid.js");
                    if (str6 == null) {
                        str6 = TapjoyUtil.copyTextFromJarIntoString("js/mraid.js", getContext());
                    }
                    stringBuffer.append(str6);
                    stringBuffer.append("</script>");
                    stringBuffer.append(str2.substring(i));
                } else {
                    int indexOf3 = str2.indexOf("<head>");
                    if (indexOf3 != -1) {
                        String str7 = (String) TapjoyUtil.getResource("mraid.js");
                        if (str7 == null) {
                            str7 = TapjoyUtil.copyTextFromJarIntoString("js/mraid.js", getContext());
                        }
                        stringBuffer.append(str2.substring(0, indexOf3 + 6));
                        stringBuffer.append("<script type='text/javascript'>");
                        stringBuffer.append(str7);
                        stringBuffer.append("</script>");
                        stringBuffer.append(str2.substring(indexOf3 + 6));
                    }
                }
                TapjoyLog.d(TAG, "injected js/mraid.js");
            }
            super.loadDataWithBaseURL(str, stringBuffer.toString(), str3, str4, str5);
        }
    }

    public void loadUrl(final String str) {
        ((Activity) this.ctx).runOnUiThread(new Runnable() {
            public void run() {
                if (!URLUtil.isValidUrl(str)) {
                    MraidView.this.loadDataWithBaseURL((String) null, MraidView.NO_CONNECTION_HTML, "text/html", "utf-8", (String) null);
                } else if (!str.startsWith("javascript:")) {
                    new MraidHTTPTask().execute(new String[]{str});
                } else if (Build.VERSION.SDK_INT >= 19) {
                    try {
                        MraidView.super.evaluateJavascript(str.replaceFirst("javascript:", BuildConfig.FLAVOR), (ValueCallback) null);
                    } catch (Exception e) {
                        TapjoyLog.e(MraidView.TAG, "Exception in evaluateJavascript. Device not supported. " + e.toString());
                    }
                } else {
                    MraidView.super.loadUrl(str);
                }
            }
        });
    }

    public void loadUrlStandard(String str) {
        super.loadUrl(str);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        if (!this.bGotLayoutParams) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            this.mInitLayoutHeight = layoutParams.height;
            this.mInitLayoutWidth = layoutParams.width;
            this.bGotLayoutParams = true;
        }
        this.viewDetached = false;
        if (this.orientationThread == null || !this.orientationThread.isAlive()) {
            this.orientationThread = new Thread(new OrientationThread());
            this.orientationThread.start();
        }
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.viewDetached = true;
        this.mUtilityController.stopAllListeners();
        try {
            if (this.videoView != null) {
                this.videoView.stopPlayback();
            }
            if (this.videoViewCallback != null) {
                this.videoViewCallback.onCustomViewHidden();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDetachedFromWindow();
    }

    public void onGlobalLayout() {
        boolean z = this.bKeyboardOut;
        if (!this.bKeyboardOut && this.mContentViewHeight >= 0 && getContentViewHeight() >= 0 && this.mContentViewHeight != getContentViewHeight()) {
            z = true;
            injectMraidJavaScript("window.mraidview.fireChangeEvent({ keyboardState: true});");
        }
        if (this.bKeyboardOut && this.mContentViewHeight >= 0 && getContentViewHeight() >= 0 && this.mContentViewHeight == getContentViewHeight()) {
            z = false;
            injectMraidJavaScript("window.mraidview.fireChangeEvent({ keyboardState: false});");
        }
        if (this.mContentViewHeight < 0) {
            this.mContentViewHeight = getContentViewHeight();
        }
        this.bKeyboardOut = z;
    }

    public void open(String str, boolean z, boolean z2, boolean z3) {
        boolean z4 = false;
        String str2 = null;
        if (checkForVideo(str)) {
            z4 = true;
            str2 = str;
        } else {
            TapjoyHttpURLResponse redirectFromURL = new TapjoyURLConnection().getRedirectFromURL(str);
            TapjoyLog.i(TAG, "redirect: " + redirectFromURL.redirectURL + ", " + redirectFromURL.statusCode);
            if (redirectFromURL != null && redirectFromURL.redirectURL != null && redirectFromURL.redirectURL.length() > 0 && checkForVideo(redirectFromURL.redirectURL)) {
                z4 = true;
                str2 = redirectFromURL.redirectURL;
            }
        }
        if (z4) {
            initAndPlayVideo(str2);
            return;
        }
        TapjoyLog.d(TAG, "Mraid Browser open:" + str);
        Intent intent = new Intent(getContext(), Browser.class);
        intent.putExtra(Browser.URL_EXTRA, str);
        intent.putExtra(Browser.SHOW_BACK_EXTRA, z);
        intent.putExtra(Browser.SHOW_FORWARD_EXTRA, z2);
        intent.putExtra(Browser.SHOW_REFRESH_EXTRA, z3);
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        getContext().startActivity(intent);
    }

    public void openMap(String str, boolean z) {
        TapjoyLog.d(TAG, "Opening Map Url " + str);
        String convert = Utils.convert(str.trim());
        if (z) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(convert));
                intent.setFlags(DriveFile.MODE_READ_ONLY);
                getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void playAudio(String str, boolean z, boolean z2, boolean z3, boolean z4, String str2, String str3) {
        Abstract.PlayerProperties playerProperties = new Abstract.PlayerProperties();
        playerProperties.setProperties(false, z, z2, z4, z3, str2, str3);
        Bundle bundle = new Bundle();
        bundle.putString("action", Action.PLAY_AUDIO.toString());
        bundle.putString(EXPAND_URL, str);
        bundle.putParcelable(PLAYER_PROPERTIES, playerProperties);
        if (playerProperties.isFullScreen()) {
            try {
                Intent intent = new Intent(getContext(), ActionHandler.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Message obtainMessage = this.mHandler.obtainMessage(MESSAGE_PLAY_AUDIO);
            obtainMessage.setData(bundle);
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void playAudioImpl(Bundle bundle) {
        String string = bundle.getString(EXPAND_URL);
        MraidPlayer player2 = getPlayer();
        player2.setPlayData((Abstract.PlayerProperties) bundle.getParcelable(PLAYER_PROPERTIES), string);
        player2.setLayoutParams(new ViewGroup.LayoutParams(1, 1));
        ((ViewGroup) getParent()).addView(player2);
        player2.playAudio();
    }

    public void playVideo(String str, boolean z, boolean z2, boolean z3, boolean z4, Abstract.Dimensions dimensions, String str2, String str3) {
        Message obtainMessage = this.mHandler.obtainMessage(MESSAGE_PLAY_VIDEO);
        Abstract.PlayerProperties playerProperties = new Abstract.PlayerProperties();
        playerProperties.setProperties(z, z2, z3, false, z4, str2, str3);
        Bundle bundle = new Bundle();
        bundle.putString(EXPAND_URL, str);
        bundle.putString("action", Action.PLAY_VIDEO.toString());
        bundle.putParcelable(PLAYER_PROPERTIES, playerProperties);
        if (dimensions != null) {
            bundle.putParcelable(DIMENSIONS, dimensions);
        }
        if (playerProperties.isFullScreen()) {
            try {
                Intent intent = new Intent(getContext(), ActionHandler.class);
                intent.putExtras(bundle);
                intent.setFlags(DriveFile.MODE_READ_ONLY);
                getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        } else if (dimensions != null) {
            obtainMessage.setData(bundle);
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void playVideoImpl(Bundle bundle) {
        Abstract.Dimensions dimensions = (Abstract.Dimensions) bundle.getParcelable(DIMENSIONS);
        String string = bundle.getString(EXPAND_URL);
        MraidPlayer player2 = getPlayer();
        player2.setPlayData((Abstract.PlayerProperties) bundle.getParcelable(PLAYER_PROPERTIES), string);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dimensions.width, dimensions.height);
        layoutParams.topMargin = dimensions.x;
        layoutParams.leftMargin = dimensions.y;
        player2.setLayoutParams(layoutParams);
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setId(101);
        frameLayout.setPadding(dimensions.x, dimensions.y, 0, 0);
        ((FrameLayout) getRootView().findViewById(16908290)).addView(frameLayout, new FrameLayout.LayoutParams(-1, -1));
        frameLayout.addView(player2);
        setVisibility(4);
        player2.setListener(new Player() {
            public void onComplete() {
                FrameLayout frameLayout = (FrameLayout) MraidView.this.getRootView().findViewById(101);
                ((ViewGroup) frameLayout.getParent()).removeView(frameLayout);
                MraidView.this.setVisibility(0);
            }

            public void onError() {
                onComplete();
            }

            public void onPrepared() {
            }
        });
        player2.playVideo();
    }

    public void raiseError(String str, String str2) {
        Message obtainMessage = this.mHandler.obtainMessage(MESSAGE_RAISE_ERROR);
        Bundle bundle = new Bundle();
        bundle.putString("message", str);
        bundle.putString("action", str2);
        obtainMessage.setData(bundle);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void registerProtocol(String str) {
        if (str != null) {
            this.registeredProtocols.add(str.toLowerCase());
        }
    }

    public void removeCloseImageButton() {
        injectMraidJavaScript("document.getElementById(\"closeButton\").style.visibility=\"hidden\";");
        TapjoyLog.d(TAG, "Removing close button.");
        this.closeButtonState = customCloseState.HIDDEN;
    }

    public void removeListener() {
        this.mListener = null;
    }

    public void reset() {
        if (this.mViewState == VIEW_STATE.EXPANDED) {
            closeExpanded();
        } else if (this.mViewState == VIEW_STATE.RESIZED) {
            closeResized();
        }
        invalidate();
        this.mUtilityController.deleteOldAds();
        this.mUtilityController.stopAllListeners();
        resetLayout();
    }

    public void resetContents() {
        ViewGroup viewGroup;
        FrameLayout frameLayout = (FrameLayout) getRootView().findViewById(100);
        FrameLayout frameLayout2 = (FrameLayout) getRootView().findViewById(101);
        frameLayout2.removeView(this);
        ((FrameLayout) getRootView().findViewById(16908290)).removeView(frameLayout2);
        resetLayout();
        if (frameLayout != null && (viewGroup = (ViewGroup) frameLayout.getParent()) != null) {
            viewGroup.addView(this, this.mIndex);
            viewGroup.removeView(frameLayout);
            viewGroup.invalidate();
        }
    }

    public void resize(int i, int i2, int i3, int i4, String str, boolean z) {
        Message obtainMessage = this.mHandler.obtainMessage(1000);
        Bundle bundle = new Bundle();
        bundle.putInt(RESIZE_WIDTH, i);
        bundle.putInt(RESIZE_HEIGHT, i2);
        bundle.putInt(RESIZE_X, i3);
        bundle.putInt(RESIZE_Y, i4);
        bundle.putBoolean(RESIZE_ALLOWOFFSCREEN, z);
        bundle.putString(RESIZE_CUSTOMCLOSEPOSITION, str);
        obtainMessage.setData(bundle);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void resizeOrientation(int i, int i2, String str, boolean z) {
        this.lastScreenWidth = i;
        this.lastScreenHeight = i2;
        TapjoyLog.i(TAG, "resizeOrientation to dimensions: " + i + "x" + i2);
        Message obtainMessage = this.mHandler.obtainMessage(MESSAGE_RESIZE_ORIENTATION);
        Bundle bundle = new Bundle();
        bundle.putInt(RESIZE_WIDTH, i);
        bundle.putInt(RESIZE_HEIGHT, i2);
        bundle.putBoolean(RESIZE_ALLOWOFFSCREEN, z);
        bundle.putString(RESIZE_CUSTOMCLOSEPOSITION, str);
        obtainMessage.setData(bundle);
        this.mHandler.sendMessage(obtainMessage);
    }

    public WebBackForwardList restoreState(Bundle bundle) {
        return super.restoreState(bundle);
    }

    public WebBackForwardList saveState(Bundle bundle) {
        return super.saveState(bundle);
    }

    public void setListener(MraidViewListener mraidViewListener) {
        this.mListener = mraidViewListener;
    }

    public void setMaxSize(int i, int i2) {
        this.mUtilityController.setMaxSize(i, i2);
    }

    public void setOrientationProperties(boolean z, String str) {
        int i = -1;
        if (!z) {
            i = str.equals("landscape") ? 0 : 1;
        }
        ((Activity) getContext()).setRequestedOrientation(i);
    }

    public void setPlacementType(PLACEMENT_TYPE placement_type) {
        if (placement_type.equals(PLACEMENT_TYPE.INLINE) || placement_type.equals(PLACEMENT_TYPE.INTERSTITIAL)) {
            this.placement = placement_type;
        } else {
            TapjoyLog.d(TAG, "Incorrect placement type.");
        }
        if (!placement_type.equals(PLACEMENT_TYPE.INLINE)) {
            return;
        }
        if (this.orientationThread == null || !this.orientationThread.isAlive()) {
            this.orientationThread = new Thread(new OrientationThread());
            this.orientationThread.start();
        }
    }

    public void show() {
        this.mHandler.sendEmptyMessage(MESSAGE_SHOW);
    }

    public void showCloseImageButton() {
        injectMraidJavaScript("document.getElementById(\"closeButton\").style.visibility=\"visible\";");
        TapjoyLog.d(TAG, "Showing close button.");
        this.closeButtonState = customCloseState.OPEN;
    }

    public boolean videoPlaying() {
        return this.videoView != null;
    }

    public void videoViewCleanup() {
        if (this.videoRelativeLayout != null) {
            ((ViewGroup) this.videoRelativeLayout.getParent()).removeView(this.videoRelativeLayout);
            this.videoRelativeLayout.setVisibility(8);
            this.videoRelativeLayout = null;
        }
        try {
            if (this.videoView != null) {
                this.videoView.stopPlayback();
            }
            if (this.videoViewCallback != null) {
                this.videoViewCallback.onCustomViewHidden();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.videoView = null;
        this.videoViewCallback = null;
        if (this != null) {
            setVisibility(0);
        }
        loadUrl("javascript:try{Tapjoy.AdUnit.dispatchEvent('videoend')}catch(e){}");
    }
}
