package jp.tjkapp.adfurikunsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.AdInfo;
import jp.tjkapp.adfurikunsdk.AdWebView;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class LayoutBase extends FrameLayout {
    private static final int CHECK_INITIAL_TIME = 1000;
    private static final int DEBUG_FONT_SIZE = 16;
    protected static final int STANDBY_VIEW_CT = 2;
    private static long mAdLastTime;
    protected int defaultAdType;
    private int fqSequence;
    private int getInfoLimit;
    protected Handler handler1;
    private int initializePhase;
    private int intSequenceEmpty;
    protected final Runnable loadAdView;
    private int loadAdViewCounter;
    private int loadAdViewLimit;
    protected final Runnable loadErrorView;
    protected final Runnable loadRandom;
    private boolean loading;
    private AdInfo mAdInfo;
    private String mAdnetworkKey;
    protected String mAppID;
    private int mBgColor;
    protected ArrayList<View> mChildView;
    private float mClipScale;
    private Context mContext;
    protected long mCycleTime;
    private TextView mDebugText;
    private Animation mInAnimation;
    protected boolean mIsFirstLoaded;
    private LogUtil mLog;
    private int mOldBgColor;
    private AdWebView.OnActionListener mOnActionListener;
    private Animation mOutAnimation;
    protected boolean mTaOff;
    private Timer mTimer;
    private float mWallOffset;
    private int noGetInfoCounter;
    protected final Runnable switchAdView;
    private long timerStartTime;
    private TimerTaskManager ttm;
    protected boolean useFillerAsDefault;
    public boolean useSingleMode;
    protected final AdWebView.OnActionListener webviewAction;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public class AdType {
        public static final int Filler = 0;
        public static final int Loading = 1;

        public AdType() {
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    private class InitialAdTask extends TimerTask {
        private InitialAdTask() {
        }

        private void stop() {
            LayoutBase.this.initializePhase = -1;
            LayoutBase.this.cancelTimer();
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (LayoutBase.this.loading) {
            }
            if (60 < (System.currentTimeMillis() - LayoutBase.this.timerStartTime) / 1000) {
                LayoutBase.this.mLog.detail(Constants.TAG_NAME, "1分以上たったのでタイマーキャンセル");
                stop();
                return;
            }
            if (!FileUtil.isGetInfoCache(LayoutBase.this.mContext, LayoutBase.this.mAppID)) {
                if (FileUtil.getAdLastState(LayoutBase.this.mContext, LayoutBase.this.mAppID) == 400) {
                    stop();
                }
                if (LayoutBase.this.getInfoLimit < LayoutBase.this.noGetInfoCounter) {
                    stop();
                }
                LayoutBase.access$1508(LayoutBase.this);
                LayoutBase.this.mLog.detail(Constants.TAG_NAME, "getInfoないよ");
                return;
            }
            if (LayoutBase.this.fqSequence <= LayoutBase.this.intSequenceEmpty) {
                stop();
                LayoutBase.this.loading = true;
                LayoutBase.this.handler1.post(LayoutBase.this.loadErrorView);
                return;
            }
            if (LayoutBase.this.mAdInfo != null && !LayoutBase.this.mAdInfo.hasAd()) {
                stop();
                LayoutBase.this.loading = true;
                LayoutBase.this.handler1.post(LayoutBase.this.loadErrorView);
                return;
            }
            switch (LayoutBase.this.initializePhase) {
                case 0:
                    try {
                        LayoutBase.this.refreshAdInfoCache();
                        LayoutBase.access$108(LayoutBase.this);
                        if (LayoutBase.this.useSingleMode) {
                            stop();
                            break;
                        }
                    } catch (Exception e) {
                        return;
                    }
                case 1:
                    LayoutBase.this.loading = true;
                    LayoutBase.this.handler1.post(LayoutBase.this.loadRandom);
                case 2:
                    LayoutBase.this.loading = true;
                    LayoutBase.this.handler1.post(LayoutBase.this.switchAdView);
                    break;
            }
        }
    }

    public LayoutBase(Context context) {
        super(context);
        this.defaultAdType = 1;
        this.useSingleMode = false;
        this.intSequenceEmpty = 0;
        this.fqSequence = 10;
        this.loading = false;
        this.useFillerAsDefault = true;
        this.noGetInfoCounter = 0;
        this.getInfoLimit = 10;
        this.mTaOff = false;
        this.handler1 = new Handler();
        this.loadAdViewCounter = 0;
        this.loadAdViewLimit = 2;
        this.loadRandom = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.1
            @Override // java.lang.Runnable
            public void run() {
                if (LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView())) {
                    LayoutBase.access$108(LayoutBase.this);
                }
                LayoutBase.this.loading = false;
            }
        };
        this.switchAdView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (LayoutBase.this.mOldBgColor != LayoutBase.this.mBgColor) {
                        LayoutBase.this.setBackgroundColor(LayoutBase.this.mBgColor);
                        LayoutBase.this.mOldBgColor = LayoutBase.this.mBgColor;
                    }
                } catch (Exception e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "set background color: " + e.getMessage());
                }
                try {
                    if (LayoutBase.this.checkSubView()) {
                        LayoutBase.this.intSequenceEmpty = 0;
                        LayoutBase.this.pushSubView();
                        LayoutBase.this.mIsFirstLoaded = true;
                        LayoutBase.this.initializePhase = -1;
                        LayoutBase.this.cancelTimer();
                        LayoutBase.this.changeAdSuccess();
                        LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView());
                    } else {
                        LayoutBase.access$708(LayoutBase.this);
                        View standByView = LayoutBase.this.getStandByView();
                        LayoutBase.this.getDisplayedView();
                        if (LayoutBase.this.mIsFirstLoaded) {
                            LayoutBase.this.loadRandomAd(standByView);
                        }
                        if (standByView != null && (standByView instanceof AdWebView) && ((AdWebView) standByView).getAdInfoDetail() == null) {
                            LayoutBase.this.loadRandomAd(standByView);
                        }
                    }
                } catch (Error e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "checkSubView: " + e2.getMessage());
                } catch (Exception e3) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "checkSubView: " + e3.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.loadAdView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (LayoutBase.this.mOldBgColor != LayoutBase.this.mBgColor) {
                        LayoutBase.this.setBackgroundColor(LayoutBase.this.mBgColor);
                        LayoutBase.this.mOldBgColor = LayoutBase.this.mBgColor;
                    }
                } catch (Exception e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "set background color: " + e.getMessage());
                }
                try {
                    LayoutBase.this.addLayout(-1);
                    LayoutBase.this.showInitialView(false);
                    if (LayoutBase.this.loadRandomAd(LayoutBase.this.getDisplayedView())) {
                        LayoutBase.this.setDebugTxtOnDisplayedView();
                        LayoutBase.this.recImpression();
                        LayoutBase.this.mIsFirstLoaded = true;
                        LayoutBase.this.initializePhase = -1;
                        LayoutBase.this.cancelTimer();
                    }
                } catch (Error e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
                } catch (Exception e3) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e3.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.loadErrorView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LayoutBase.this.showInitialView(true, true);
                    if (!LayoutBase.this.useSingleMode) {
                        LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView());
                    }
                } catch (Error e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e.getMessage());
                } catch (Exception e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.webviewAction = new AdWebView.OnActionListener() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.5
            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void adClick() {
                AdWebView adWebView;
                AdInfo.AdInfoDetail adInfoDetail;
                View displayedView = LayoutBase.this.getDisplayedView();
                if (displayedView == null) {
                    return;
                }
                if ((displayedView instanceof AdWebView) && (adInfoDetail = (adWebView = (AdWebView) displayedView).getAdInfoDetail()) != null) {
                    new RecTask2(LayoutBase.this.getContext(), LayoutBase.this.mAppID, adInfoDetail.user_ad_id, 0, null, adWebView.getClickUrl(), adWebView.getRecClickParam());
                }
                if (LayoutBase.this.mOnActionListener != null) {
                    LayoutBase.this.mOnActionListener.adClick();
                }
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void errorLoad() {
                LayoutBase.this.setDebugTxtOnDisplayedView();
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void successLoad() {
                LayoutBase.this.setDebugTxtOnDisplayedView();
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void windowClose() {
                if (LayoutBase.this.mOnActionListener != null) {
                    LayoutBase.this.mOnActionListener.windowClose();
                }
            }
        };
        initialize(context, -2);
    }

    protected LayoutBase(Context context, int i) {
        super(context);
        this.defaultAdType = 1;
        this.useSingleMode = false;
        this.intSequenceEmpty = 0;
        this.fqSequence = 10;
        this.loading = false;
        this.useFillerAsDefault = true;
        this.noGetInfoCounter = 0;
        this.getInfoLimit = 10;
        this.mTaOff = false;
        this.handler1 = new Handler();
        this.loadAdViewCounter = 0;
        this.loadAdViewLimit = 2;
        this.loadRandom = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.1
            @Override // java.lang.Runnable
            public void run() {
                if (LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView())) {
                    LayoutBase.access$108(LayoutBase.this);
                }
                LayoutBase.this.loading = false;
            }
        };
        this.switchAdView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (LayoutBase.this.mOldBgColor != LayoutBase.this.mBgColor) {
                        LayoutBase.this.setBackgroundColor(LayoutBase.this.mBgColor);
                        LayoutBase.this.mOldBgColor = LayoutBase.this.mBgColor;
                    }
                } catch (Exception e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "set background color: " + e.getMessage());
                }
                try {
                    if (LayoutBase.this.checkSubView()) {
                        LayoutBase.this.intSequenceEmpty = 0;
                        LayoutBase.this.pushSubView();
                        LayoutBase.this.mIsFirstLoaded = true;
                        LayoutBase.this.initializePhase = -1;
                        LayoutBase.this.cancelTimer();
                        LayoutBase.this.changeAdSuccess();
                        LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView());
                    } else {
                        LayoutBase.access$708(LayoutBase.this);
                        View standByView = LayoutBase.this.getStandByView();
                        LayoutBase.this.getDisplayedView();
                        if (LayoutBase.this.mIsFirstLoaded) {
                            LayoutBase.this.loadRandomAd(standByView);
                        }
                        if (standByView != null && (standByView instanceof AdWebView) && ((AdWebView) standByView).getAdInfoDetail() == null) {
                            LayoutBase.this.loadRandomAd(standByView);
                        }
                    }
                } catch (Error e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "checkSubView: " + e2.getMessage());
                } catch (Exception e3) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "checkSubView: " + e3.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.loadAdView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (LayoutBase.this.mOldBgColor != LayoutBase.this.mBgColor) {
                        LayoutBase.this.setBackgroundColor(LayoutBase.this.mBgColor);
                        LayoutBase.this.mOldBgColor = LayoutBase.this.mBgColor;
                    }
                } catch (Exception e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "set background color: " + e.getMessage());
                }
                try {
                    LayoutBase.this.addLayout(-1);
                    LayoutBase.this.showInitialView(false);
                    if (LayoutBase.this.loadRandomAd(LayoutBase.this.getDisplayedView())) {
                        LayoutBase.this.setDebugTxtOnDisplayedView();
                        LayoutBase.this.recImpression();
                        LayoutBase.this.mIsFirstLoaded = true;
                        LayoutBase.this.initializePhase = -1;
                        LayoutBase.this.cancelTimer();
                    }
                } catch (Error e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
                } catch (Exception e3) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e3.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.loadErrorView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LayoutBase.this.showInitialView(true, true);
                    if (!LayoutBase.this.useSingleMode) {
                        LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView());
                    }
                } catch (Error e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e.getMessage());
                } catch (Exception e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.webviewAction = new AdWebView.OnActionListener() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.5
            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void adClick() {
                AdWebView adWebView;
                AdInfo.AdInfoDetail adInfoDetail;
                View displayedView = LayoutBase.this.getDisplayedView();
                if (displayedView == null) {
                    return;
                }
                if ((displayedView instanceof AdWebView) && (adInfoDetail = (adWebView = (AdWebView) displayedView).getAdInfoDetail()) != null) {
                    new RecTask2(LayoutBase.this.getContext(), LayoutBase.this.mAppID, adInfoDetail.user_ad_id, 0, null, adWebView.getClickUrl(), adWebView.getRecClickParam());
                }
                if (LayoutBase.this.mOnActionListener != null) {
                    LayoutBase.this.mOnActionListener.adClick();
                }
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void errorLoad() {
                LayoutBase.this.setDebugTxtOnDisplayedView();
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void successLoad() {
                LayoutBase.this.setDebugTxtOnDisplayedView();
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void windowClose() {
                if (LayoutBase.this.mOnActionListener != null) {
                    LayoutBase.this.mOnActionListener.windowClose();
                }
            }
        };
        initialize(context, i);
    }

    public LayoutBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.defaultAdType = 1;
        this.useSingleMode = false;
        this.intSequenceEmpty = 0;
        this.fqSequence = 10;
        this.loading = false;
        this.useFillerAsDefault = true;
        this.noGetInfoCounter = 0;
        this.getInfoLimit = 10;
        this.mTaOff = false;
        this.handler1 = new Handler();
        this.loadAdViewCounter = 0;
        this.loadAdViewLimit = 2;
        this.loadRandom = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.1
            @Override // java.lang.Runnable
            public void run() {
                if (LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView())) {
                    LayoutBase.access$108(LayoutBase.this);
                }
                LayoutBase.this.loading = false;
            }
        };
        this.switchAdView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (LayoutBase.this.mOldBgColor != LayoutBase.this.mBgColor) {
                        LayoutBase.this.setBackgroundColor(LayoutBase.this.mBgColor);
                        LayoutBase.this.mOldBgColor = LayoutBase.this.mBgColor;
                    }
                } catch (Exception e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "set background color: " + e.getMessage());
                }
                try {
                    if (LayoutBase.this.checkSubView()) {
                        LayoutBase.this.intSequenceEmpty = 0;
                        LayoutBase.this.pushSubView();
                        LayoutBase.this.mIsFirstLoaded = true;
                        LayoutBase.this.initializePhase = -1;
                        LayoutBase.this.cancelTimer();
                        LayoutBase.this.changeAdSuccess();
                        LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView());
                    } else {
                        LayoutBase.access$708(LayoutBase.this);
                        View standByView = LayoutBase.this.getStandByView();
                        LayoutBase.this.getDisplayedView();
                        if (LayoutBase.this.mIsFirstLoaded) {
                            LayoutBase.this.loadRandomAd(standByView);
                        }
                        if (standByView != null && (standByView instanceof AdWebView) && ((AdWebView) standByView).getAdInfoDetail() == null) {
                            LayoutBase.this.loadRandomAd(standByView);
                        }
                    }
                } catch (Error e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "checkSubView: " + e2.getMessage());
                } catch (Exception e3) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "checkSubView: " + e3.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.loadAdView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (LayoutBase.this.mOldBgColor != LayoutBase.this.mBgColor) {
                        LayoutBase.this.setBackgroundColor(LayoutBase.this.mBgColor);
                        LayoutBase.this.mOldBgColor = LayoutBase.this.mBgColor;
                    }
                } catch (Exception e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "set background color: " + e.getMessage());
                }
                try {
                    LayoutBase.this.addLayout(-1);
                    LayoutBase.this.showInitialView(false);
                    if (LayoutBase.this.loadRandomAd(LayoutBase.this.getDisplayedView())) {
                        LayoutBase.this.setDebugTxtOnDisplayedView();
                        LayoutBase.this.recImpression();
                        LayoutBase.this.mIsFirstLoaded = true;
                        LayoutBase.this.initializePhase = -1;
                        LayoutBase.this.cancelTimer();
                    }
                } catch (Error e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
                } catch (Exception e3) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e3.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.loadErrorView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LayoutBase.this.showInitialView(true, true);
                    if (!LayoutBase.this.useSingleMode) {
                        LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView());
                    }
                } catch (Error e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e.getMessage());
                } catch (Exception e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.webviewAction = new AdWebView.OnActionListener() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.5
            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void adClick() {
                AdWebView adWebView;
                AdInfo.AdInfoDetail adInfoDetail;
                View displayedView = LayoutBase.this.getDisplayedView();
                if (displayedView == null) {
                    return;
                }
                if ((displayedView instanceof AdWebView) && (adInfoDetail = (adWebView = (AdWebView) displayedView).getAdInfoDetail()) != null) {
                    new RecTask2(LayoutBase.this.getContext(), LayoutBase.this.mAppID, adInfoDetail.user_ad_id, 0, null, adWebView.getClickUrl(), adWebView.getRecClickParam());
                }
                if (LayoutBase.this.mOnActionListener != null) {
                    LayoutBase.this.mOnActionListener.adClick();
                }
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void errorLoad() {
                LayoutBase.this.setDebugTxtOnDisplayedView();
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void successLoad() {
                LayoutBase.this.setDebugTxtOnDisplayedView();
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void windowClose() {
                if (LayoutBase.this.mOnActionListener != null) {
                    LayoutBase.this.mOnActionListener.windowClose();
                }
            }
        };
        initialize(context, -2);
    }

    public LayoutBase(Context context, boolean z) {
        super(context);
        this.defaultAdType = 1;
        this.useSingleMode = false;
        this.intSequenceEmpty = 0;
        this.fqSequence = 10;
        this.loading = false;
        this.useFillerAsDefault = true;
        this.noGetInfoCounter = 0;
        this.getInfoLimit = 10;
        this.mTaOff = false;
        this.handler1 = new Handler();
        this.loadAdViewCounter = 0;
        this.loadAdViewLimit = 2;
        this.loadRandom = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.1
            @Override // java.lang.Runnable
            public void run() {
                if (LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView())) {
                    LayoutBase.access$108(LayoutBase.this);
                }
                LayoutBase.this.loading = false;
            }
        };
        this.switchAdView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (LayoutBase.this.mOldBgColor != LayoutBase.this.mBgColor) {
                        LayoutBase.this.setBackgroundColor(LayoutBase.this.mBgColor);
                        LayoutBase.this.mOldBgColor = LayoutBase.this.mBgColor;
                    }
                } catch (Exception e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "set background color: " + e.getMessage());
                }
                try {
                    if (LayoutBase.this.checkSubView()) {
                        LayoutBase.this.intSequenceEmpty = 0;
                        LayoutBase.this.pushSubView();
                        LayoutBase.this.mIsFirstLoaded = true;
                        LayoutBase.this.initializePhase = -1;
                        LayoutBase.this.cancelTimer();
                        LayoutBase.this.changeAdSuccess();
                        LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView());
                    } else {
                        LayoutBase.access$708(LayoutBase.this);
                        View standByView = LayoutBase.this.getStandByView();
                        LayoutBase.this.getDisplayedView();
                        if (LayoutBase.this.mIsFirstLoaded) {
                            LayoutBase.this.loadRandomAd(standByView);
                        }
                        if (standByView != null && (standByView instanceof AdWebView) && ((AdWebView) standByView).getAdInfoDetail() == null) {
                            LayoutBase.this.loadRandomAd(standByView);
                        }
                    }
                } catch (Error e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "checkSubView: " + e2.getMessage());
                } catch (Exception e3) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "checkSubView: " + e3.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.loadAdView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (LayoutBase.this.mOldBgColor != LayoutBase.this.mBgColor) {
                        LayoutBase.this.setBackgroundColor(LayoutBase.this.mBgColor);
                        LayoutBase.this.mOldBgColor = LayoutBase.this.mBgColor;
                    }
                } catch (Exception e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, "set background color: " + e.getMessage());
                }
                try {
                    LayoutBase.this.addLayout(-1);
                    LayoutBase.this.showInitialView(false);
                    if (LayoutBase.this.loadRandomAd(LayoutBase.this.getDisplayedView())) {
                        LayoutBase.this.setDebugTxtOnDisplayedView();
                        LayoutBase.this.recImpression();
                        LayoutBase.this.mIsFirstLoaded = true;
                        LayoutBase.this.initializePhase = -1;
                        LayoutBase.this.cancelTimer();
                    }
                } catch (Error e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
                } catch (Exception e3) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e3.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.loadErrorView = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LayoutBase.this.showInitialView(true, true);
                    if (!LayoutBase.this.useSingleMode) {
                        LayoutBase.this.loadRandomAd(LayoutBase.this.getStandByView());
                    }
                } catch (Error e) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e.getMessage());
                } catch (Exception e2) {
                    LayoutBase.this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
                }
                LayoutBase.this.loading = false;
            }
        };
        this.webviewAction = new AdWebView.OnActionListener() { // from class: jp.tjkapp.adfurikunsdk.LayoutBase.5
            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void adClick() {
                AdWebView adWebView;
                AdInfo.AdInfoDetail adInfoDetail;
                View displayedView = LayoutBase.this.getDisplayedView();
                if (displayedView == null) {
                    return;
                }
                if ((displayedView instanceof AdWebView) && (adInfoDetail = (adWebView = (AdWebView) displayedView).getAdInfoDetail()) != null) {
                    new RecTask2(LayoutBase.this.getContext(), LayoutBase.this.mAppID, adInfoDetail.user_ad_id, 0, null, adWebView.getClickUrl(), adWebView.getRecClickParam());
                }
                if (LayoutBase.this.mOnActionListener != null) {
                    LayoutBase.this.mOnActionListener.adClick();
                }
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void errorLoad() {
                LayoutBase.this.setDebugTxtOnDisplayedView();
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void successLoad() {
                LayoutBase.this.setDebugTxtOnDisplayedView();
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void windowClose() {
                if (LayoutBase.this.mOnActionListener != null) {
                    LayoutBase.this.mOnActionListener.windowClose();
                }
            }
        };
        this.useSingleMode = z;
        initialize(context, -2);
    }

    static /* synthetic */ int access$108(LayoutBase layoutBase) {
        int i = layoutBase.initializePhase;
        layoutBase.initializePhase = i + 1;
        return i;
    }

    static /* synthetic */ int access$1508(LayoutBase layoutBase) {
        int i = layoutBase.noGetInfoCounter;
        layoutBase.noGetInfoCounter = i + 1;
        return i;
    }

    static /* synthetic */ int access$708(LayoutBase layoutBase) {
        int i = layoutBase.intSequenceEmpty;
        layoutBase.intSequenceEmpty = i + 1;
        return i;
    }

    private void callRefReportConversion() {
        try {
            new Thread(new AdfurikunRefReportConversion(this.mContext)).start();
        } catch (Error e) {
        } catch (Exception e2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelTimer() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
        this.loading = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0167  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean checkSubView() {
        /*
            Method dump skipped, instructions count: 378
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.tjkapp.adfurikunsdk.LayoutBase.checkSubView():boolean");
    }

    private void clearSubView() {
        if (this.mChildView != null) {
            for (int size = this.mChildView.size() - 1; size >= 0; size--) {
                View view = this.mChildView.get(size);
                if (view instanceof AdWebView) {
                    AdWebView adWebView = (AdWebView) view;
                    adWebView.setAdInfoDetail(null, -1);
                    adWebView.setVisibility(View.GONE);
                    adWebView.stopLoading();
                    adWebView.setWebViewClient(null);
                    adWebView.clearCache(true);
                    adWebView.clearHistory();
                    ViewGroup viewGroup = (ViewGroup) adWebView.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(adWebView);
                    }
                    adWebView.destroy();
                    this.mChildView.remove(adWebView);
                } else {
                    ViewGroup viewGroup2 = (ViewGroup) view.getParent();
                    if (viewGroup2 != null) {
                        viewGroup2.removeView(view);
                    }
                    this.mChildView.remove(view);
                }
            }
            this.mChildView.clear();
        }
    }

    private boolean isEmptyBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            int[] iArr = new int[width];
            for (int i = 0; i < height; i++) {
                int i2 = 0;
                bitmap.getPixels(iArr, 0, width, 0, i, width, 1);
                for (int i3 = 0; i3 < width; i3++) {
                    int i4 = iArr[i3];
                    hashSet.add(Integer.valueOf(i4));
                    i2 += i4;
                }
                hashSet2.add(Integer.valueOf(i2));
            }
            if (hashSet.size() > 5 || hashSet2.size() > 5) {
                return false;
            }
            HashSet hashSet3 = new HashSet();
            int[] iArr2 = new int[height];
            for (int i5 = 0; i5 < width; i5++) {
                int i6 = 0;
                bitmap.getPixels(iArr2, 0, 1, i5, 0, 1, height);
                for (int i7 = 0; i7 < height; i7++) {
                    i6 += iArr2[i7];
                }
                hashSet3.add(Integer.valueOf(i6));
            }
            if (hashSet3.size() > 5) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean loadRandomAd(View view) {
        if (view != null && this.mAdInfo != null) {
            if (view instanceof AdWebView) {
                try {
                    ((AdWebView) view).setAdInfoDetail(this.mAdInfo.getNextAd(Constants.AD_RANDOM), this.mAdInfo.banner_kind);
                } catch (Exception e) {
                    this.mLog.detail(Constants.TAG_NAME, e.getMessage());
                }
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushSubView() {
        changePanel();
        recImpression();
        setDebugTxtOnDisplayedView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshAdInfoCache() throws Exception {
        int i;
        long adLastTime = FileUtil.getAdLastTime(this.mContext, this.mAppID);
        if (!FileUtil.isGetInfoCache(this.mContext, this.mAppID)) {
            throw new Exception("get info doesn't exists");
        }
        if (this.mAdInfo == null || mAdLastTime != adLastTime) {
            mAdLastTime = adLastTime;
            String strLoadStringFile = FileUtil.loadStringFile(FileUtil.getGetInfoFilePath(this.mContext, this.mAppID));
            if (strLoadStringFile.equals(BuildConfig.FLAVOR)) {
                throw new Exception("failed to create get_info");
            }
            this.mAdInfo = ApiAccessUtil.stringToAdInfo(this.mContext, this.mAppID, strLoadStringFile, true);
            if (this.mAdInfo == null) {
                throw new Exception("failed to create adinfo");
            }
            int i2 = this.mBgColor;
            if (this.mAdInfo.bg_color.length() > 0) {
                try {
                    i = Integer.parseInt(this.mAdInfo.bg_color, 16) | (-16777216);
                } catch (NumberFormatException e) {
                    i = -16777216;
                }
            } else {
                i = 0;
            }
            this.mBgColor = i;
            this.mTaOff = this.mAdInfo.ta_off;
            this.mCycleTime = this.mAdInfo.cycle_time * 1000;
        }
    }

    private void setDebugInfo(String str, int i, int i2) {
        if (this.mDebugText != null) {
            if (str == null) {
                str = BuildConfig.FLAVOR;
            }
            this.mDebugText.setText(str);
            this.mDebugText.setBackgroundColor(i);
            this.mDebugText.setTextColor(i2);
            if (str.length() > 0) {
                this.mDebugText.setVisibility(View.VISIBLE);
            } else {
                this.mDebugText.setVisibility(View.GONE);
            }
            try {
                this.mDebugText.bringToFront();
            } catch (Error e) {
                this.mLog.detail(Constants.TAG_NAME, e.getMessage());
            } catch (Exception e2) {
                this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
            }
        }
    }

    private void startTimer() {
        cancelTimer();
        if (this.mTimer == null) {
            if (this.ttm != null) {
                this.ttm.onResume();
            }
            this.initializePhase = 0;
            this.intSequenceEmpty = 0;
            this.timerStartTime = System.currentTimeMillis();
            this.mTimer = new Timer();
            this.mTimer.schedule(new InitialAdTask(), 0L, 1000L);
        }
    }

    protected void addLayout(int i) {
        if (this.mChildView != null) {
            try {
                int size = this.mChildView.size();
                for (int i2 = 0; i2 < size; i2++) {
                    View view = this.mChildView.get(i2);
                    ViewGroup viewGroup = (ViewGroup) view.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(view);
                    }
                }
            } catch (Error e) {
                this.mLog.detail(Constants.TAG_NAME, e.getMessage());
            } catch (Exception e2) {
                this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
            }
            this.mChildView.removeAll(this.mChildView);
        } else {
            this.mChildView = new ArrayList<>();
        }
        int i3 = this.useSingleMode ? 1 : 2;
        for (int i4 = 0; i4 < i3; i4++) {
            ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, Integer.valueOf(i).intValue());
            AdWebView adWebView = null;
            try {
                adWebView = new AdWebView(getContext(), this.webviewAction);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            adWebView.setId(i4);
            adWebView.setVisibility(View.INVISIBLE);
            addView(adWebView, layoutParams);
            this.mChildView.add(adWebView);
        }
    }

    protected void changeAdSuccess() {
    }

    protected void changePanel() {
        this.mLog.detail(Constants.TAG_NAME, "changePanel開始");
        if (this.mChildView != null) {
            View standByChildPanel = getStandByChildPanel();
            int size = this.mChildView.size();
            for (int i = 0; i < size; i++) {
                View childPanel = getChildPanel(i);
                if (childPanel != null) {
                    if (childPanel.equals(standByChildPanel)) {
                        if (this.mInAnimation != null) {
                            childPanel.startAnimation(this.mInAnimation);
                        }
                        childPanel.setVisibility(android.view.View.VISIBLE);
                    } else {
                        if (this.mOutAnimation != null && childPanel.getVisibility() == View.VISIBLE) {
                            childPanel.startAnimation(this.mOutAnimation);
                        } else if (childPanel.getAnimation() == this.mInAnimation) {
                            childPanel.clearAnimation();
                        }
                        childPanel.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    public void destroy() {
        if (this.ttm != null) {
            this.ttm.stopTimer();
        }
        cancelTimer();
        clearSubView();
    }

    protected View getChildPanel(int i) {
        return getViewAt(i);
    }

    protected View getDisplayedView() {
        if (this.mChildView == null) {
            return null;
        }
        int size = this.mChildView.size();
        for (int i = 0; i < size; i++) {
            View view = this.mChildView.get(i);
            if (view.getVisibility() == View.VISIBLE) {
                return view;
            }
        }
        return null;
    }

    protected View getStandByChildPanel() {
        return getStandByView();
    }

    protected View getStandByView() {
        if (this.mChildView == null) {
            return null;
        }
        int size = this.mChildView.size();
        for (int i = 0; i < size; i++) {
            View view = this.mChildView.get(i);
            if (view.getVisibility() != View.VISIBLE) {
                return view;
            }
        }
        return null;
    }

    protected View getViewAt(int i) {
        if (this.mChildView == null || i >= this.mChildView.size()) {
            return null;
        }
        return this.mChildView.get(i);
    }

    @SuppressLint({"NewApi"})
    protected void initialize(Context context, int i) {
        this.mContext = context;
        this.mLog = LogUtil.getInstance(this.mContext);
        float f = getResources().getDisplayMetrics().density;
        this.mClipScale = 0.75f / getResources().getDisplayMetrics().density;
        this.mWallOffset = (int) ((50.0f * f * this.mClipScale) + 0.5f);
        setClickable(true);
        this.mOnActionListener = null;
        this.mCycleTime = Constants.DEFAULT_CYCLE_TIME;
        this.mTimer = null;
        this.mAppID = BuildConfig.FLAVOR;
        this.mAdnetworkKey = BuildConfig.FLAVOR;
        mAdLastTime = -1L;
        this.initializePhase = 0;
        this.mIsFirstLoaded = false;
        this.loading = false;
        Display defaultDisplay = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        f = 16.0f * displayMetrics.density;
        this.mBgColor = -16777216;
        this.mOldBgColor = 0;
        if (!this.useSingleMode) {
            addLayout(i);
        }
        if (this.mLog.isDebugMode()) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            this.mDebugText = new TextView(context);
            this.mDebugText.setTextSize(16.0f);
            this.mDebugText.setPadding((int) f, 0, (int) f, 0);
            addView(this.mDebugText, layoutParams);
        } else {
            this.mDebugText = null;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    protected boolean isEmptyAd(View view) {
        int i;
        int i2;
        int width = getWidth();
        int height = getHeight();
        view.layout(0, 0, width, height);
        int i3 = (int) ((width * this.mClipScale) + 0.5f);
        int i4 = (int) ((height * this.mClipScale) + 0.5f);
        int i5 = i3 < 200 ? i3 : 200;
        int i6 = i4 < 160 ? i4 : 160;
        if (this.mAdInfo == null || AdInfo.getAdType(this.mAdInfo.banner_kind) != 2) {
            i = (i3 - i5) / 2;
            i2 = 0;
        } else {
            i = 0;
            i2 = (int) this.mWallOffset;
        }
        if (i6 == 0) {
            i6 = 100;
        }
        Bitmap bitmapCreateBitmap = null;
        Bitmap bitmapCreateScaledBitmap = null;
        try {
            bitmapCreateBitmap = Bitmap.createBitmap(i5, i6, Bitmap.Config.RGB_565);
        } catch (IllegalArgumentException e) {
        } catch (OutOfMemoryError e2) {
        }
        if (bitmapCreateBitmap != null) {
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            view.layout(0, 0, width, height);
            canvas.save();
            canvas.translate(-i, -i2);
            canvas.scale(this.mClipScale, this.mClipScale);
            view.draw(canvas);
            canvas.restore();
            try {
                bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, 32, 32, false);
            } catch (IllegalArgumentException e3) {
            } catch (OutOfMemoryError e4) {
            }
        }
        boolean zIsEmptyBitmap = bitmapCreateScaledBitmap != null ? isEmptyBitmap(bitmapCreateScaledBitmap) : bitmapCreateBitmap != null ? isEmptyBitmap(bitmapCreateBitmap) : true;
        if (bitmapCreateBitmap != null) {
            bitmapCreateBitmap.recycle();
        }
        if (bitmapCreateScaledBitmap != null) {
            bitmapCreateScaledBitmap.recycle();
        }
        return zIsEmptyBitmap;
    }

    public boolean isLoadFinished() {
        return FileUtil.isGetInfoCache(this.mContext, this.mAppID);
    }

    public void nextAd() {
        if (this.ttm != null) {
            this.ttm.onResume();
        }
        try {
            refreshAdInfoCache();
            if (this.useSingleMode) {
                this.handler1.post(this.loadAdView);
            } else {
                this.handler1.post(this.switchAdView);
            }
        } catch (Exception e) {
            startTimer();
        }
    }

    @SuppressLint({"NewApi"})
    public void onPause() {
        if (this.mChildView != null) {
            int size = this.mChildView.size();
            for (int i = 0; i < size; i++) {
                View viewAt = getViewAt(i);
                if (viewAt != null && (viewAt instanceof AdWebView)) {
                    ((AdWebView) viewAt).onPause();
                }
            }
        }
        cancelTimer();
        if (this.ttm != null) {
            this.ttm.onPause();
        }
    }

    @SuppressLint({"NewApi"})
    public void onResume() {
        if (this.ttm != null) {
            this.ttm.onResume();
        }
    }

    protected void recImpression() {
        AdWebView adWebView;
        AdInfo.AdInfoDetail adInfoDetail;
        View displayedView = getDisplayedView();
        if (displayedView == null) {
            return;
        }
        if (this.mOnActionListener != null) {
            this.mOnActionListener.successLoad();
        }
        if (!(displayedView instanceof AdWebView) || (adInfoDetail = (adWebView = (AdWebView) displayedView).getAdInfoDetail()) == null) {
            return;
        }
        new RecTask2(getContext(), this.mAppID, adInfoDetail.user_ad_id, 1, adWebView.getImpPrice(), adWebView.getImpUrl(), adWebView.getRecImpParam());
    }

    public void saveBitmap(Bitmap bitmap) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/AAAAAAAA/");
        try {
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (SecurityException e) {
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/AAAAAAAA//" + (UUID.randomUUID().toString() + ".png"));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e2) {
        }
    }

    public void setAdfurikunAppKey(String str) {
        if (this.mAppID == null || !this.mAppID.equals(str)) {
            this.mLog.detail(Constants.TAG_NAME, "appID: " + this.mAppID);
            this.mAppID = str;
            this.mIsFirstLoaded = false;
            if (this.mChildView != null) {
                int size = this.mChildView.size();
                for (int i = 0; i < size; i++) {
                    View view = this.mChildView.get(i);
                    if (view instanceof AdWebView) {
                        ((AdWebView) view).setAdfurikunAppKey(str);
                    }
                }
            }
            showInitialView(false);
            if (this.ttm == null) {
                this.ttm = new TimerTaskManager(this.mContext, this.mLog);
            }
            this.ttm.setAppID(this.mAppID);
            startTimer();
        }
    }

    public void setDebugTxtOnDisplayedView() {
        AdWebView adWebView;
        AdInfo.AdInfoDetail adInfoDetail;
        this.mAdnetworkKey = "xxxxxxxx";
        View displayedView = getDisplayedView();
        if (displayedView == null || !(displayedView instanceof AdWebView) || (adInfoDetail = (adWebView = (AdWebView) displayedView).getAdInfoDetail()) == null) {
            return;
        }
        if (adInfoDetail != null) {
            this.mAdnetworkKey = adInfoDetail.adnetwork_key;
        }
        if (adWebView.isError) {
            setDebugInfo(this.mAdnetworkKey, -256, -13312);
        } else {
            setDebugInfo(this.mAdnetworkKey, -256, -16777216);
        }
    }

    protected void setInAnimation(Animation animation) {
        this.mInAnimation = animation;
    }

    protected void setOnActionListener(AdWebView.OnActionListener onActionListener) {
        this.mOnActionListener = onActionListener;
    }

    protected void setOutAnimation(Animation animation) {
        this.mOutAnimation = animation;
    }

    protected void showInitialView(boolean z) {
        showInitialView(z, false);
    }

    protected void showInitialView(boolean z, boolean z2) {
        String fillerFilePath;
        if (this.mChildView == null) {
            return;
        }
        if (!z) {
            int size = this.mChildView.size();
            for (int i = 0; i < size; i++) {
                if (getChildPanel(i).getVisibility() == View.VISIBLE) {
                    return;
                }
            }
        }
        try {
            getChildPanel(0).setVisibility(View.VISIBLE);
            View view = this.mChildView.get(0);
            if (view instanceof AdWebView) {
                AdWebView adWebView = (AdWebView) view;
                adWebView.setAdfurikunAppKey(this.mAppID);
                if (z2 && (fillerFilePath = FileUtil.getFillerFilePath(this.mContext, this.mAppID)) != null && !fillerFilePath.equals(BuildConfig.FLAVOR) && new File(fillerFilePath).exists()) {
                    if (this.useFillerAsDefault) {
                        adWebView.loadUrl("file://" + fillerFilePath);
                    }
                    if (z) {
                        this.mIsFirstLoaded = true;
                    }
                }
            }
        } catch (Error e) {
            this.mLog.detail(Constants.TAG_NAME, e.getMessage());
        } catch (Exception e2) {
            this.mLog.detail(Constants.TAG_NAME, e2.getMessage());
        }
        setDebugInfo(AdNetworkKey.DEFAULT, -256, -13312);
    }
}
