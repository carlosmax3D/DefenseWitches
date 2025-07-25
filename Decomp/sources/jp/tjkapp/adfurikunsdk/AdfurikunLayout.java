package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdfurikunLayout extends LayoutBase {
    public static final int TRANSITION_FADEIN_FADEOUT = 4;
    private static final int TRANSITION_MAX = 5;
    public static final int TRANSITION_NOTHING = -1;
    public static final int TRANSITION_RANDOM = -2;
    public static final int TRANSITION_SLIDE_FROM_BOTTOM = 3;
    public static final int TRANSITION_SLIDE_FROM_LEFT = 1;
    public static final int TRANSITION_SLIDE_FROM_RIGHT = 0;
    public static final int TRANSITION_SLIDE_FROM_TOP = 2;
    private Handler handler;
    protected boolean mIsAutoRotate;
    private boolean mIsEnable;
    private Random mRandom;
    private int mTransitionType;
    private long mUpdateTime;
    private final Runnable updateThread;

    public AdfurikunLayout(Context context) {
        super(context);
        this.updateThread = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.AdfurikunLayout.1
            @Override // java.lang.Runnable
            public void run() {
                AdfurikunLayout.this.nextAd();
            }
        };
        this.mTransitionType = -1;
        this.handler = new Handler();
    }

    protected AdfurikunLayout(Context context, int i) {
        super(context, i);
        this.updateThread = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.AdfurikunLayout.1
            @Override // java.lang.Runnable
            public void run() {
                AdfurikunLayout.this.nextAd();
            }
        };
        this.mTransitionType = -1;
        this.handler = new Handler();
    }

    public AdfurikunLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.updateThread = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.AdfurikunLayout.1
            @Override // java.lang.Runnable
            public void run() {
                AdfurikunLayout.this.nextAd();
            }
        };
        this.mTransitionType = -1;
        this.handler = new Handler();
    }

    private Animation getFadeInAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(800L);
        return alphaAnimation;
    }

    private Animation getFadeOutAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(400L);
        return alphaAnimation;
    }

    private Animation getPushDownInAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, -1.0f, 2, 0.0f);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    private Animation getPushDownOutAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, 1.0f);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    private Animation getPushLeftInAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    private Animation getPushLeftOutAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    private Animation getPushRightInAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    private Animation getPushRightOutAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 1.0f, 2, 0.0f, 2, 0.0f);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    private Animation getPushUpInAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 1.0f, 2, 0.0f);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    private Animation getPushUpOutAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, -1.0f);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    private void setTransition() {
        if (this.mTaOff) {
            return;
        }
        int iNextInt = this.mTransitionType;
        if (iNextInt == -2) {
            iNextInt = this.mRandom.nextInt(5);
        }
        Animation fadeInAnimation = null;
        Animation fadeOutAnimation = null;
        switch (iNextInt) {
            case 0:
                fadeInAnimation = getPushLeftInAnimation();
                fadeOutAnimation = getPushLeftOutAnimation();
                break;
            case 1:
                fadeInAnimation = getPushRightInAnimation();
                fadeOutAnimation = getPushRightOutAnimation();
                break;
            case 2:
                fadeInAnimation = getPushDownInAnimation();
                fadeOutAnimation = getPushDownOutAnimation();
                break;
            case 3:
                fadeInAnimation = getPushUpInAnimation();
                fadeOutAnimation = getPushUpOutAnimation();
                break;
            case 4:
                fadeInAnimation = getFadeInAnimation();
                fadeOutAnimation = getFadeOutAnimation();
                break;
        }
        setInAnimation(fadeInAnimation);
        setOutAnimation(fadeOutAnimation);
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    protected void changeAdSuccess() {
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    protected void changePanel() {
        if (this.mTransitionType == -2) {
            setTransition();
        }
        super.changePanel();
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    public void destroy() {
        this.mIsEnable = false;
        this.handler.removeCallbacks(this.updateThread);
        super.destroy();
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    protected void initialize(Context context, int i) {
        super.initialize(context, i);
        this.mIsEnable = false;
        this.mIsAutoRotate = false;
        this.mRandom = new Random();
        this.mUpdateTime = new Date().getTime();
    }

    public boolean isFinishedFirstLoad() {
        return this.mIsFirstLoaded;
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    public /* bridge */ /* synthetic */ boolean isLoadFinished() {
        return super.isLoadFinished();
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    public void nextAd() {
        this.handler.removeCallbacks(this.updateThread);
        if (this.mIsEnable) {
            this.mUpdateTime = new Date().getTime();
            super.nextAd();
            if (this.mIsAutoRotate) {
                this.handler.postDelayed(this.updateThread, this.mCycleTime);
            }
        }
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    public void onPause() {
        this.mIsEnable = false;
        this.handler.removeCallbacks(this.updateThread);
        super.onPause();
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    public void onResume() {
        this.mIsEnable = true;
        super.onResume();
        long time = new Date().getTime();
        if (this.mIsAutoRotate) {
            if (time - this.mUpdateTime >= this.mCycleTime) {
                nextAd();
                return;
            }
            long j = this.mCycleTime - (time - this.mUpdateTime);
            if (j < Constants.WAIT_TIME) {
                j = Constants.WAIT_TIME;
            }
            this.handler.postDelayed(this.updateThread, j);
        }
    }

    public void restartRotateAd() {
        if (this.mIsAutoRotate) {
            return;
        }
        startRotateAd();
        this.handler.removeCallbacks(this.updateThread);
        if (this.mIsEnable) {
            this.mUpdateTime = new Date().getTime();
            if (this.mIsAutoRotate) {
                this.handler.postDelayed(this.updateThread, this.mCycleTime);
            }
        }
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    public /* bridge */ /* synthetic */ void saveBitmap(Bitmap bitmap) throws IOException {
        super.saveBitmap(bitmap);
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    public /* bridge */ /* synthetic */ void setAdfurikunAppKey(String str) {
        super.setAdfurikunAppKey(str);
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    public /* bridge */ /* synthetic */ void setDebugTxtOnDisplayedView() {
        super.setDebugTxtOnDisplayedView();
    }

    public void setTransitionType(int i) {
        this.mTransitionType = i;
        if (this.mTransitionType >= 5) {
            this.mTransitionType = -1;
        }
        setTransition();
    }

    public void startRotateAd() {
        this.mIsAutoRotate = true;
    }

    public void stopRotateAd() {
        this.handler.removeCallbacks(this.updateThread);
        if (this.mIsAutoRotate) {
            this.mIsAutoRotate = false;
        }
    }
}
