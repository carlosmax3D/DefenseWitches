package jp.co.voyagegroup.android.fluct.jar.task;

import android.content.Context;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctConfig;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctInterstitialManager;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctPreferences;
import jp.co.voyagegroup.android.fluct.jar.util.Log;

public class FluctAdapterThread extends Thread {
    private static final String TAG = "FluctAdapterThread";
    private Context mContext;
    private boolean mHalt = false;
    private FluctInterstitialManager mInterstitial;
    private boolean mInterstitialCallback;
    private String mMediaId;
    private FluctAdapterThreadListener mThreadListener;
    private int mType;

    public interface FluctAdapterThreadListener {
        void initWebView();

        void startTimer();
    }

    public FluctAdapterThread(Context context, String str, int i, FluctInterstitialManager fluctInterstitialManager, boolean z) {
        Log.d(TAG, "FluctAdapterThread : ");
        this.mContext = context;
        this.mMediaId = str;
        this.mType = i;
        this.mInterstitial = fluctInterstitialManager;
        this.mInterstitialCallback = z;
    }

    public FluctAdapterThread(Context context, String str, int i, FluctAdapterThreadListener fluctAdapterThreadListener) {
        Log.d(TAG, "FluctAdapterThread : ");
        this.mContext = context;
        this.mMediaId = str;
        this.mType = i;
        this.mThreadListener = fluctAdapterThreadListener;
    }

    private boolean getConfigFromDBorNet() {
        Log.d(TAG, "getConfigFromDBorNet : ");
        FluctConfig instance = FluctConfig.getInstance();
        if (instance.getFromDB(this.mContext, this.mMediaId) != null) {
            return true;
        }
        Log.v(TAG, "getConfigFromDBorNet : DB has no config");
        if (instance.getFromNet(this.mContext, this.mMediaId) == null) {
            Log.v(TAG, "getConfigFromDBorNet : got writelock failed");
            if (instance.getFromDBWithResultLock(this.mContext, this.mMediaId) == null) {
                return true;
            }
            Log.v(TAG, "getConfigFromDBorNet : got config by waiting for DB");
            return false;
        }
        Log.v(TAG, "getConfigFromDBorNet : got config from network");
        return false;
    }

    private void startListener() {
        Log.d(TAG, "startListener : ");
        if (FluctConfig.getInstance().getConfigFromMap(this.mMediaId) != null) {
            Log.v(TAG, "startListener : start the timer of FluctViewHelper");
            this.mThreadListener.initWebView();
            this.mThreadListener.startTimer();
        }
    }

    public void halt() {
        Log.d(TAG, "halt : ");
        this.mHalt = true;
        interrupt();
    }

    public void run() {
        Log.d(TAG, "FluctAdapterThread run : start run in : " + this.mType);
        String str = null;
        try {
            if (!this.mHalt) {
                FluctConfig instance = FluctConfig.getInstance();
                FluctPreferences.getInstance().makeFluctPreferences(this.mContext);
                switch (this.mType) {
                    case 1:
                        Log.v(TAG, "FluctAdapterThread run : prepare get config start");
                        instance.getFromNet(this.mContext, this.mMediaId);
                        break;
                    case 2:
                        Log.v(TAG, "FluctAdapterThread run : normal get config start");
                        boolean configFromDBorNet = getConfigFromDBorNet();
                        startListener();
                        if (configFromDBorNet) {
                            Log.v(TAG, "FluctAdapterThread run : need update config");
                            Thread.currentThread().setPriority(Thread.currentThread().getPriority() - 2);
                            instance.getFromNet(this.mContext, this.mMediaId);
                            break;
                        }
                        break;
                    case 3:
                        Log.v(TAG, "FluctAdapterThread run : interstitial get config start");
                        str = instance.getFromNetErrorMsg(this.mContext, this.mMediaId);
                        break;
                }
            }
            this.mContext = null;
            this.mMediaId = null;
            this.mThreadListener = null;
            if (this.mInterstitial != null && this.mInterstitialCallback) {
                this.mInterstitial.startShowing(str);
            }
            this.mInterstitial = null;
            this.mInterstitialCallback = false;
        } catch (Exception e) {
            Log.e(TAG, "FluctAdapterThread run : Exception is " + e.getLocalizedMessage());
            this.mContext = null;
            this.mMediaId = null;
            this.mThreadListener = null;
            if (this.mInterstitial != null && this.mInterstitialCallback) {
                this.mInterstitial.startShowing((String) null);
            }
            this.mInterstitial = null;
            this.mInterstitialCallback = false;
        } catch (Throwable th) {
            this.mContext = null;
            this.mMediaId = null;
            this.mThreadListener = null;
            if (this.mInterstitial != null && this.mInterstitialCallback) {
                this.mInterstitial.startShowing((String) null);
            }
            this.mInterstitial = null;
            this.mInterstitialCallback = false;
            throw th;
        }
    }
}
