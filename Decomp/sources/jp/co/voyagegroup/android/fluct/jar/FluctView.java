package jp.co.voyagegroup.android.fluct.jar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctConversion;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctViewHelper;
import jp.co.voyagegroup.android.fluct.jar.task.FluctAdapterThread;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import jp.co.voyagegroup.android.fluct.jar.util.FluctUtils;
import jp.co.voyagegroup.android.fluct.jar.util.Log;
import jp.stargarage.g2metrics.BuildConfig;

public class FluctView extends RelativeLayout {
    private static final String TAG = "FluctView";
    /* access modifiers changed from: private */
    public FluctAdapterThread mAdapterThread;
    /* access modifiers changed from: private */
    public FluctViewHelper mFluctViewHelper;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private String mMediaId;

    public FluctView(Context context) {
        this(context, (String) null);
        Log.d(TAG, "FluctView : none MediaID ");
        Log.x("AD", "Create AdView : none media");
    }

    public FluctView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHandler = new Handler();
        Log.d(TAG, "FluctView : AttributeSet");
        Log.x("AD", "Create AdView:");
        createView(attributeSet.getAttributeValue((String) null, FluctConstants.META_DATA_MEDIA_ID));
    }

    public FluctView(Context context, String str) {
        super(context);
        this.mHandler = new Handler();
        Log.d(TAG, "FluctView : MediaID is " + str);
        Log.x("AD", "Create AdView : Media");
        createView(str);
    }

    @SuppressLint({"NewApi"})
    private void createView(String str) {
        Log.d(TAG, "createView :");
        Log.x("AD", "AD version : 3.2.0");
        if (str == null || BuildConfig.FLAVOR.equals(str)) {
            str = FluctUtils.getDefaultMediaId(getContext().getApplicationContext());
        }
        this.mMediaId = str;
        Log.v(TAG, "createView : mediaId is " + this.mMediaId);
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, (Paint) null);
        }
    }

    private void deleteView(final long j) {
        Log.d(TAG, "deleteView : SleepTime is " + j);
        new Thread(new Runnable() {
            public void run() {
                Log.d(FluctView.TAG, "deleteView : Thread run() ");
                try {
                    Thread.sleep(j);
                } catch (InterruptedException e) {
                    Log.e(FluctView.TAG, "deleteView : Sleep Exception");
                }
                int windowVisibility = FluctView.this.getWindowVisibility();
                Log.v(FluctView.TAG, "deleteView : Window visibility is " + windowVisibility);
                if (windowVisibility == 4 || windowVisibility == 8) {
                    FluctView.this.mHandler.post(new Runnable() {
                        public void run() {
                            if (FluctView.this.mAdapterThread != null) {
                                FluctView.this.mAdapterThread.halt();
                                FluctView.this.mAdapterThread = null;
                            }
                            if (FluctView.this.mFluctViewHelper != null) {
                                Log.x("AD", "Stop");
                                FluctView.this.mFluctViewHelper.stopTimerAndDelWebView();
                                FluctView.this.mFluctViewHelper = null;
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void makeSelfVisibility(int i) {
        Log.d(TAG, "makeSelfVisibility : visibility is " + i);
        if (i == 4 || i == 8) {
            if (this.mAdapterThread != null) {
                this.mAdapterThread.halt();
                this.mAdapterThread = null;
            }
            if (this.mFluctViewHelper != null) {
                this.mFluctViewHelper.stopAnimation();
                this.mFluctViewHelper.stopTimer();
            }
        } else if (i == 0) {
            if (this.mFluctViewHelper == null) {
                this.mFluctViewHelper = new FluctViewHelper(getContext().getApplicationContext(), this, this.mMediaId);
            }
            if (this.mAdapterThread == null) {
                this.mAdapterThread = new FluctAdapterThread(getContext().getApplicationContext(), this.mMediaId, 2, this.mFluctViewHelper);
                this.mAdapterThread.start();
            }
        }
    }

    private void makeVisibility(int i, long j) {
        Log.d(TAG, "makeVisibility : visibility is " + i);
        if (i == 4 || i == 8) {
            deleteView(j);
        } else if (i == 0 && getVisibility() == 0) {
            if (this.mFluctViewHelper == null) {
                Log.x("AD", "Start");
                this.mFluctViewHelper = new FluctViewHelper(getContext().getApplicationContext(), this, this.mMediaId);
            }
            if (this.mAdapterThread == null) {
                this.mAdapterThread = new FluctAdapterThread(getContext().getApplicationContext(), this.mMediaId, 2, this.mFluctViewHelper);
                this.mAdapterThread.start();
            }
        }
    }

    public static void prepareConfig(Context context) {
        Log.d(TAG, "prepareConfig : ");
        prepareConfig(context, (String) null);
    }

    public static void prepareConfig(Context context, String str) {
        Log.d(TAG, "prepareConfig : ");
        Log.x("AD", "AdView prepare : ");
        if (str == null || BuildConfig.FLAVOR.equals(str)) {
            str = FluctUtils.getDefaultMediaId(context);
        }
        Log.v(TAG, "prepareConfig : mediaId is " + str);
        new FluctAdapterThread(context, str, 1, (FluctAdapterThread.FluctAdapterThreadListener) null).start();
    }

    public static void setConversion(Context context) {
        Log.d(TAG, "setConversion : ");
        Log.x("AD", "AdView Conversion : ");
        FluctConversion.setConversion(context);
    }

    public void destroy() {
        Log.d(TAG, "destroy : ");
        Log.x("AD", "Destroy AdView : ");
        deleteView(0);
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i) {
        Log.d(TAG, "onWindowVisibilityChanged : visibility is " + i);
        makeVisibility(i, 900);
        super.onWindowVisibilityChanged(i);
    }

    public void requestChildFocus(View view, View view2) {
        Log.d(TAG, "requestChildFocus : ");
        if (!(view2 instanceof WebView)) {
            super.requestChildFocus(view, view2);
        }
    }

    public void setVisibility(int i) {
        Log.d(TAG, "setVisibility : visibility is " + i);
        Log.x("AD", "AdView set visibility : " + i);
        if (getVisibility() != i) {
            makeSelfVisibility(i);
            super.setVisibility(i);
        }
    }
}
