package jp.co.voyagegroup.android.fluct.jar.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.drive.DriveFile;
import jp.co.voyagegroup.android.fluct.jar.FluctInterstitial;
import jp.co.voyagegroup.android.fluct.jar.FluctInterstitialActivity;
import jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess;
import jp.co.voyagegroup.android.fluct.jar.db.FluctInterstitialTable;
import jp.co.voyagegroup.android.fluct.jar.task.FluctAdapterThread;
import jp.co.voyagegroup.android.fluct.jar.util.FluctUtils;
import jp.co.voyagegroup.android.fluct.jar.util.Log;
import jp.stargarage.g2metrics.BuildConfig;

public class FluctInterstitialManager {
    private static final String TAG = "FluctInterstitialManager";
    /* access modifiers changed from: private */
    public static boolean mAdStatus = false;
    private static FluctAdapterThread mAdapterThread;
    private static FluctInterstitial.FluctInterstitialCallback mCallback = null;
    private static Handler mCallbackHandler;
    private Context mContext;
    private int mFrameColor;
    private String mMediaId;

    private static final class CallbackHandler extends Handler {
        private static final String TAG = "CallbackHandler";

        private CallbackHandler(Looper looper) {
            super(looper);
        }

        /* synthetic */ CallbackHandler(Looper looper, CallbackHandler callbackHandler) {
            this(looper);
        }

        public void handleMessage(Message message) {
            Log.d(TAG, "handleMessage : msg is " + message);
            if (message.obj != null) {
                FluctInterstitial.FluctInterstitialCallback fluctInterstitialCallback = (FluctInterstitial.FluctInterstitialCallback) message.obj;
                switch (message.what) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 100:
                        FluctInterstitialManager.mAdStatus = false;
                        break;
                }
                if (fluctInterstitialCallback != null) {
                    fluctInterstitialCallback.onReceiveAdInfo(message.what);
                }
            }
        }
    }

    public FluctInterstitialManager(Context context, String str) {
        Log.d(TAG, "FluctInterstitialManager : MediaID is " + str);
        this.mContext = context;
        this.mMediaId = str;
        mCallbackHandler = new CallbackHandler(Looper.getMainLooper(), (CallbackHandler) null);
    }

    public static void callback(int i) {
        Log.d(TAG, "callback : status " + i);
        Message message = new Message();
        message.what = i;
        message.obj = mCallback;
        mCallbackHandler.sendMessage(message);
        mAdapterThread = null;
    }

    private void show(int i) {
        Log.d(TAG, "show : color is " + i);
        if (this.mMediaId.equals(BuildConfig.FLAVOR)) {
            callback(5);
        } else if (!FluctUtils.isNetWorkAvailable(this.mContext)) {
            callback(4);
        } else {
            this.mFrameColor = i;
            FluctInterstitialTable interstitial = FluctDbAccess.getInterstitial(this.mContext, this.mMediaId);
            if (interstitial == null) {
                Log.d(TAG, "show : mAdapterThread is " + mAdapterThread);
                if (mAdapterThread == null) {
                    mAdapterThread = new FluctAdapterThread(this.mContext, this.mMediaId, 3, this, true);
                    mAdapterThread.start();
                    return;
                }
                return;
            }
            showActivity(interstitial);
            if (mAdapterThread == null) {
                mAdapterThread = new FluctAdapterThread(this.mContext, this.mMediaId, 3, this, false);
                mAdapterThread.start();
            }
        }
    }

    private void showActivity(FluctInterstitialTable fluctInterstitialTable) {
        Log.d(TAG, "showActivity : ");
        int i = 0;
        if (-1 == fluctInterstitialTable.getAdHtml().indexOf("<html>")) {
            i = 6;
        } else if (Math.random() * 100.0d < ((double) fluctInterstitialTable.getRate())) {
            Intent intent = new Intent(this.mContext, FluctInterstitialActivity.class);
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            intent.putExtra("media_id", this.mMediaId);
            intent.putExtra("frame_color", this.mFrameColor);
            this.mContext.startActivity(intent);
        } else {
            i = 3;
        }
        if (i != 0) {
            callback(i);
        }
    }

    public void setFluctInterstitialCallback(FluctInterstitial.FluctInterstitialCallback fluctInterstitialCallback) {
        Log.d(TAG, "setFluctInterstitialCallback : ");
        mCallback = fluctInterstitialCallback;
    }

    public void showIntersitialAd(int i) {
        Log.d(TAG, "showIntersitialAd : frameColor is " + i);
        if (!mAdStatus) {
            mAdStatus = true;
            show(i);
            return;
        }
        callback(100);
    }

    public void startShowing(String str) {
        Log.d(TAG, "startShowing : message is " + str);
        FluctInterstitialTable interstitial = FluctDbAccess.getInterstitial(this.mContext, this.mMediaId);
        if (interstitial != null) {
            showActivity(interstitial);
            return;
        }
        int i = 6;
        if (str != null) {
            Log.d(TAG, "startShowing : error " + str);
            i = 8;
        }
        callback(i);
    }
}
