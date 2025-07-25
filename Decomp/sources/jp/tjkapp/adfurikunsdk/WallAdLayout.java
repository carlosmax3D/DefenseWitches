package jp.tjkapp.adfurikunsdk;

import android.R;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.util.ArrayList;
import jp.tjkapp.adfurikunsdk.AdWebView;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class WallAdLayout extends LayoutBase {
    private ProgressAdWebView[] mChildProgressAdWebView;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    private class ProgressAdWebView extends LinearLayout {
        private Handler handler;
        private final Runnable hideProgress;
        private ProgressBar mHorizontalProgressBar;
        private AdWebView.OnProgressListener mOnProgressListener;
        private ProgressBar mProgressBar;

        public ProgressAdWebView(Context context, AdWebView adWebView) {
            super(context);
            this.handler = new Handler();
            this.mOnProgressListener = new AdWebView.OnProgressListener() { // from class: jp.tjkapp.adfurikunsdk.WallAdLayout.ProgressAdWebView.1
                @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnProgressListener
                public void dismissProgress() {
                    ProgressAdWebView.this.handler.removeCallbacks(ProgressAdWebView.this.hideProgress);
                    ProgressAdWebView.this.hideProgressBar();
                }

                @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnProgressListener
                public void errorClose() {
                }

                @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnProgressListener
                public void setProgress(int i) {
                    if (ProgressAdWebView.this.mHorizontalProgressBar != null) {
                        ProgressAdWebView.this.mHorizontalProgressBar.setProgress(i);
                        if (i >= 100) {
                            ProgressAdWebView.this.handler.removeCallbacks(ProgressAdWebView.this.hideProgress);
                            ProgressAdWebView.this.handler.postDelayed(ProgressAdWebView.this.hideProgress, 100L);
                        }
                    }
                }

                @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnProgressListener
                public void startProgress() {
                    ProgressAdWebView.this.handler.removeCallbacks(ProgressAdWebView.this.hideProgress);
                    if (ProgressAdWebView.this.mHorizontalProgressBar != null) {
                        ProgressAdWebView.this.mHorizontalProgressBar.setMax(100);
                        ProgressAdWebView.this.mHorizontalProgressBar.setProgress(0);
                        ProgressAdWebView.this.mHorizontalProgressBar.setVisibility(0);
                    }
                    if (ProgressAdWebView.this.mProgressBar != null) {
                        ProgressAdWebView.this.mProgressBar.setVisibility(0);
                    }
                }

                @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnProgressListener
                public void stopProgress() {
                    if (ProgressAdWebView.this.mProgressBar != null) {
                        ProgressAdWebView.this.mProgressBar.setVisibility(8);
                    }
                }
            };
            this.hideProgress = new Runnable() { // from class: jp.tjkapp.adfurikunsdk.WallAdLayout.ProgressAdWebView.2
                @Override // java.lang.Runnable
                public void run() {
                    ProgressAdWebView.this.hideProgressBar();
                }
            };
            initialize(context, adWebView);
        }

        private GradientDrawable createGradient(int i, int i2) {
            return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{i, i2});
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void hideProgressBar() {
            if (this.mHorizontalProgressBar != null) {
                this.mHorizontalProgressBar.setVisibility(8);
            }
            if (this.mProgressBar != null) {
                this.mProgressBar.setVisibility(8);
            }
        }

        private void initialize(Context context, AdWebView adWebView) {
            setOrientation(1);
            int i = (int) ((8.0f * getResources().getDisplayMetrics().density) + 0.5f);
            this.mHorizontalProgressBar = new ProgressBar(context, null, R.attr.progressBarStyleHorizontal);
            this.mHorizontalProgressBar.setBackgroundDrawable(createGradient(-3684409, -5197648));
            this.mHorizontalProgressBar.setProgressDrawable(new ClipDrawable(createGradient(-10924, -1467136), 3, 1));
            addView(this.mHorizontalProgressBar, new LinearLayout.LayoutParams(-1, i));
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            FrameLayout frameLayout = new FrameLayout(context);
            addView(frameLayout, layoutParams);
            adWebView.setOnProgressListener(this.mOnProgressListener);
            frameLayout.addView(adWebView, new FrameLayout.LayoutParams(-1, -1));
            this.mProgressBar = new ProgressBar(context, null, R.attr.progressBarStyleLarge);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
            layoutParams2.gravity = 17;
            frameLayout.addView(this.mProgressBar, layoutParams2);
        }
    }

    public WallAdLayout(Context context) {
        super(context, true);
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
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
            } catch (Exception e2) {
            }
            this.mChildView.removeAll(this.mChildView);
        } else {
            this.mChildView = new ArrayList<>();
        }
        if (this.mChildProgressAdWebView != null) {
            int length = this.mChildProgressAdWebView.length;
            for (int i3 = 0; i3 < length; i3++) {
                ProgressAdWebView progressAdWebView = this.mChildProgressAdWebView[i3];
                ViewGroup viewGroup2 = (ViewGroup) progressAdWebView.getParent();
                if (viewGroup2 != null) {
                    viewGroup2.removeView(progressAdWebView);
                }
            }
        }
        int i4 = this.useSingleMode ? 1 : 2;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.mChildProgressAdWebView = new ProgressAdWebView[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            AdWebView adWebView = new AdWebView(getContext(), this.webviewAction);
            adWebView.setId(i5);
            this.mChildView.add(adWebView);
            this.mChildProgressAdWebView[i5] = new ProgressAdWebView(getContext(), adWebView);
            this.mChildProgressAdWebView[i5].setVisibility(4);
            addView(this.mChildProgressAdWebView[i5], layoutParams);
        }
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    protected View getChildPanel(int i) {
        if (i < this.mChildProgressAdWebView.length) {
            return this.mChildProgressAdWebView[i];
        }
        return null;
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    protected View getStandByChildPanel() {
        if (this.mChildProgressAdWebView == null) {
            return null;
        }
        int length = this.mChildProgressAdWebView.length;
        for (int i = 0; i < length; i++) {
            ProgressAdWebView progressAdWebView = this.mChildProgressAdWebView[i];
            if (progressAdWebView.getVisibility() != 0) {
                return progressAdWebView;
            }
        }
        return null;
    }

    protected boolean goBack() {
        View displayedView = getDisplayedView();
        if (displayedView == null || !(displayedView instanceof AdWebView)) {
            return false;
        }
        AdWebView adWebView = (AdWebView) displayedView;
        if (!adWebView.canGoBack()) {
            return false;
        }
        adWebView.goBack();
        return true;
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    protected void initialize(Context context, int i) {
        this.defaultAdType = 1;
        super.initialize(context, i);
    }
}
