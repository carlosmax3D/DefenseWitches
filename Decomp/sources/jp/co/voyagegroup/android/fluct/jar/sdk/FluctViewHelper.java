package jp.co.voyagegroup.android.fluct.jar.sdk;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import java.util.Timer;
import java.util.TimerTask;
import jp.co.voyagegroup.android.fluct.jar.FluctView;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctAd;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting;
import jp.co.voyagegroup.android.fluct.jar.task.FluctAdapterThread;
import jp.co.voyagegroup.android.fluct.jar.util.FluctUtils;
import jp.co.voyagegroup.android.fluct.jar.util.Log;

public class FluctViewHelper implements FluctAdapterThread.FluctAdapterThreadListener {
    private static final String TAG = "FluctViewHelper";
    private TranslateAnimation mAnimationIn;
    private int mAnimationIndex;
    private Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
            FluctViewHelper.this.mWebViewToLoad.setVisibility(8);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
            FluctViewHelper.this.flipWebView();
        }
    };
    private TranslateAnimation mAnimationOut;
    private Context mContext;
    private FluctView mFluctView;
    private FluctWebView mFluctWebViewSecond;
    private FluctWebView mFluctWebviewFirst;
    /* access modifiers changed from: private */
    public boolean mHasPreLoad;
    private Timer mLoadTimer;
    /* access modifiers changed from: private */
    public Handler mMainThreadHandler;
    /* access modifiers changed from: private */
    public String mMediaId;
    private Timer mRefreshTimer;
    /* access modifiers changed from: private */
    public FluctSetting mSetting;
    /* access modifiers changed from: private */
    public FluctWebView mWebViewToLoad;

    private static final class FluctMainThreadHandler extends Handler {
        private static final String TAG = "FluctMainThreadHandler";

        private FluctMainThreadHandler(Looper looper) {
            super(looper);
        }

        /* synthetic */ FluctMainThreadHandler(Looper looper, FluctMainThreadHandler fluctMainThreadHandler) {
            this(looper);
        }

        public void handleMessage(Message message) {
            Log.d(TAG, "handleMessage : msg is " + message);
            if (message.obj != null) {
                FluctViewHelper fluctViewHelper = (FluctViewHelper) message.obj;
                try {
                    switch (message.what) {
                        case 1:
                            fluctViewHelper.refreshFluctWebView();
                            return;
                        case 2:
                            fluctViewHelper.loadFluctWebView();
                            return;
                        case 3:
                            fluctViewHelper.initFluctViewHelper();
                            return;
                        default:
                            return;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "handleMessage : Exception is " + e.getLocalizedMessage());
                }
//                Log.e(TAG, "handleMessage : Exception is " + e.getLocalizedMessage());
            }
        }
    }

    public FluctViewHelper(Context context, FluctView fluctView, String str) {
        Log.d(TAG, "FluctViewHelper : ");
        this.mContext = context;
        this.mFluctView = fluctView;
        this.mMediaId = str;
        this.mMainThreadHandler = new FluctMainThreadHandler(Looper.getMainLooper(), (FluctMainThreadHandler) null);
        this.mAnimationIndex = 0;
    }

    /* access modifiers changed from: private */
    public void flipWebView() {
        Log.d(TAG, "flipWebView : ");
        synchronized (this.mWebViewToLoad) {
            if (this.mWebViewToLoad.equals(this.mFluctWebviewFirst)) {
                this.mWebViewToLoad = this.mFluctWebViewSecond;
                Log.v(TAG, "flipWebView : SwitchWebView First -> Second");
            } else {
                this.mWebViewToLoad = this.mFluctWebviewFirst;
                Log.v(TAG, "flipWebView : SwitchWebView Second -> First");
            }
        }
    }

    private long getLoadDelayTime() {
        Log.d(TAG, "getLoadDelayTime : ");
        long loadTime = this.mSetting.getLoadTime();
        long refreshTime = this.mSetting.getRefreshTime() * 1000;
        Log.v(TAG, "getLoadDelayTime : LoadTime is " + loadTime + " RefreshTime is " + refreshTime);
        if (refreshTime <= loadTime || loadTime <= 0) {
            return -1;
        }
        return refreshTime - loadTime;
    }

    /* access modifiers changed from: private */
    public void initFluctViewHelper() {
        Log.d(TAG, "initFluctViewHelper : ");
        this.mFluctWebviewFirst = new FluctWebView(this.mContext, this.mSetting);
        this.mFluctWebViewSecond = new FluctWebView(this.mContext, this.mSetting);
        this.mFluctView.addView(this.mFluctWebViewSecond);
        this.mFluctView.addView(this.mFluctWebviewFirst);
        this.mWebViewToLoad = this.mFluctWebviewFirst;
        FluctAd fluctAd = this.mSetting.getFluctAd();
        if (fluctAd != null && FluctUtils.isNetWorkAvailable(this.mContext)) {
            this.mFluctWebviewFirst.setAdHtml(fluctAd);
            showWebViewLoaded();
        }
    }

    private void prepareAnimation() {
        Log.d(TAG, "prepareAnimation : ");
        FluctSetting.Animation animation = this.mSetting.getAnimations().get(this.mAnimationIndex);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        switch (animation.getType()) {
            case 1:
                i2 = this.mWebViewToLoad.getHeight();
                i4 = 0 - this.mWebViewToLoad.getHeight();
                break;
            case 2:
                i2 = 0 - this.mWebViewToLoad.getHeight();
                i4 = this.mWebViewToLoad.getHeight();
                break;
            case 3:
                i = this.mWebViewToLoad.getWidth();
                i3 = 0 - this.mWebViewToLoad.getWidth();
                break;
            case 4:
                i = 0 - this.mWebViewToLoad.getWidth();
                i3 = this.mWebViewToLoad.getWidth();
                break;
        }
        if (i != 0 || i2 != 0 || i3 != 0 || i4 != 0) {
            this.mAnimationOut = new TranslateAnimation(0.0f, (float) i, 0.0f, (float) i2);
            this.mAnimationOut.setDuration((long) animation.getDuration());
            this.mAnimationIn = new TranslateAnimation((float) i3, 0.0f, (float) i4, 0.0f);
            this.mAnimationIn.setDuration((long) animation.getDuration());
            this.mAnimationIn.setAnimationListener(this.mAnimationListener);
        }
    }

    private void scheduleLoadTimer(long j) {
        Log.d(TAG, "scheduleLoadTimer : loadDelayTime is " + j);
        if (this.mLoadTimer != null) {
            this.mLoadTimer.schedule(new TimerTask() {
                public void run() {
                    FluctViewHelper.this.mSetting = FluctConfig.getInstance().getConfigFromMap(FluctViewHelper.this.mMediaId);
                    Message message = new Message();
                    message.what = 2;
                    message.obj = FluctViewHelper.this;
                    FluctViewHelper.this.mMainThreadHandler.sendMessage(message);
                }
            }, j);
        }
    }

    /* access modifiers changed from: private */
    public void scheduleNextRefresh() {
        Log.d(TAG, "scheduleNextRefresh : ");
        if (this.mSetting.getRefreshTime() * 1000 > 0) {
            long loadDelayTime = getLoadDelayTime();
            if (loadDelayTime != -1) {
                this.mHasPreLoad = true;
                scheduleLoadTimer(loadDelayTime);
            } else {
                this.mHasPreLoad = false;
            }
            scheduleRefreshTimer();
        }
    }

    private void scheduleRefreshTimer() {
        Log.d(TAG, "scheduleRefreshTimer : ");
        if (this.mRefreshTimer != null) {
            this.mRefreshTimer.schedule(new TimerTask() {
                public void run() {
                    if (!FluctViewHelper.this.mHasPreLoad) {
                        FluctViewHelper.this.mSetting = FluctConfig.getInstance().getConfigFromMap(FluctViewHelper.this.mMediaId);
                        Message message = new Message();
                        message.what = 2;
                        message.obj = FluctViewHelper.this;
                        FluctViewHelper.this.mMainThreadHandler.sendMessage(message);
                    }
                    Message message2 = new Message();
                    message2.what = 1;
                    message2.obj = FluctViewHelper.this;
                    FluctViewHelper.this.mMainThreadHandler.sendMessage(message2);
                    FluctViewHelper.this.scheduleNextRefresh();
                }
            }, this.mSetting.getRefreshTime() * 1000);
        }
    }

    private void showWebViewLoaded() {
        Log.d(TAG, "showWebViewLoaded : ");
        if (FluctUtils.isNetWorkAvailable(this.mContext)) {
            this.mWebViewToLoad.setVisibility(0);
            if (this.mSetting.getAnimations() == null || this.mSetting.getAnimations().size() <= 0) {
                flipWebView();
                this.mWebViewToLoad.setVisibility(8);
                return;
            }
            this.mAnimationIn = null;
            this.mAnimationOut = null;
            prepareAnimation();
            if (this.mAnimationIn == null && this.mAnimationOut == null) {
                flipWebView();
                this.mWebViewToLoad.setVisibility(8);
            } else {
                this.mWebViewToLoad.bringToFront();
                this.mWebViewToLoad.setAnimation(this.mAnimationIn);
                if (this.mWebViewToLoad == this.mFluctWebviewFirst) {
                    this.mFluctWebViewSecond.setAnimation(this.mAnimationOut);
                } else {
                    this.mFluctWebviewFirst.setAnimation(this.mAnimationOut);
                }
                this.mAnimationOut.start();
                this.mAnimationIn.start();
            }
            this.mAnimationIndex++;
            if (this.mAnimationIndex >= this.mSetting.getAnimations().size()) {
                this.mAnimationIndex = 0;
                return;
            }
            return;
        }
        this.mFluctWebviewFirst.setVisibility(8);
        this.mFluctWebViewSecond.setVisibility(8);
    }

    public void destroyFluctWebView(FluctView fluctView) {
        Log.d(TAG, "destroyFluctWebView : ");
        stopAnimation();
        if (this.mFluctWebviewFirst != null) {
            fluctView.removeView(this.mFluctWebviewFirst);
            this.mFluctWebviewFirst.destroy();
            this.mFluctWebviewFirst = null;
        }
        if (this.mFluctWebViewSecond != null) {
            fluctView.removeView(this.mFluctWebViewSecond);
            this.mFluctWebViewSecond.destroy();
            this.mFluctWebViewSecond = null;
        }
        this.mWebViewToLoad = null;
        this.mContext = null;
    }

    public void initWebView() {
        Log.d(TAG, "initWebView : ");
        this.mSetting = FluctConfig.getInstance().getConfigFromMap(this.mMediaId);
        Message message = new Message();
        message.what = 3;
        message.obj = this;
        this.mMainThreadHandler.sendMessage(message);
    }

    public void loadFluctWebView() {
        Log.d(TAG, "loadFluctWebView : ");
        if (this.mWebViewToLoad != null) {
            FluctAd fluctAd = this.mSetting.getFluctAd();
            if (fluctAd == null || !FluctUtils.isNetWorkAvailable(this.mContext)) {
                this.mWebViewToLoad.setVisibility(8);
                return;
            }
            this.mWebViewToLoad.setAdHtml(fluctAd);
            this.mWebViewToLoad.setVisibility(4);
        }
    }

    public void refreshFluctWebView() {
        Log.d(TAG, "refreshFluctWebView : ");
        if (this.mWebViewToLoad != null) {
            showWebViewLoaded();
        }
    }

    public void startTimer() {
        Log.d(TAG, "startTimer : ");
        if (this.mRefreshTimer == null) {
            this.mRefreshTimer = new Timer();
        }
        if (this.mLoadTimer == null) {
            this.mLoadTimer = new Timer();
        }
        scheduleNextRefresh();
    }

    public void stopAnimation() {
        Log.d(TAG, "stopAnimation : ");
        if (this.mAnimationIn != null) {
            this.mAnimationIn.setAnimationListener((Animation.AnimationListener) null);
            if (Build.VERSION.SDK_INT > 7) {
                this.mAnimationIn.cancel();
            }
            this.mAnimationIn.reset();
            this.mAnimationIn = null;
        }
        if (this.mAnimationOut != null) {
            this.mAnimationOut.setAnimationListener((Animation.AnimationListener) null);
            if (Build.VERSION.SDK_INT > 7) {
                this.mAnimationOut.cancel();
            }
            this.mAnimationOut.reset();
            this.mAnimationOut = null;
        }
        if (this.mFluctWebviewFirst != null) {
            this.mFluctWebviewFirst.clearAnimation();
        }
        if (this.mFluctWebViewSecond != null) {
            this.mFluctWebViewSecond.clearAnimation();
        }
    }

    public synchronized void stopTimer() {
        Log.d(TAG, "stopTimer : ");
        if (this.mLoadTimer != null) {
            this.mLoadTimer.cancel();
            this.mLoadTimer.purge();
            this.mLoadTimer = null;
        }
        if (this.mRefreshTimer != null) {
            this.mRefreshTimer.cancel();
            this.mRefreshTimer.purge();
            this.mRefreshTimer = null;
        }
        if (!(this.mFluctWebviewFirst == null || this.mFluctWebViewSecond == null || (this.mFluctWebviewFirst.getVisibility() != 0 && this.mFluctWebViewSecond.getVisibility() != 0))) {
            if (this.mFluctWebviewFirst.getVisibility() == 0) {
                this.mFluctWebviewFirst.setVisibility(4);
            } else {
                this.mFluctWebviewFirst.setVisibility(8);
            }
            if (this.mFluctWebViewSecond.getVisibility() == 0) {
                this.mFluctWebViewSecond.setVisibility(4);
            } else {
                this.mFluctWebViewSecond.setVisibility(8);
            }
        }
    }

    public synchronized void stopTimerAndDelWebView() {
        Log.d(TAG, "stopTimerAndDelWebView : ");
        stopTimer();
        destroyFluctWebView(this.mFluctView);
        Log.d(TAG, "dispose FluctPreferences : ");
        FluctPreferences.getInstance().dispose();
    }
}
