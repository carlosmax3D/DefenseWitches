package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import jp.tjkapp.adfurikunsdk.AdWebView;
import jp.tjkapp.adfurikunsdk.WallAdUtil;
import jp.tjkapp.adfurikunsdk.WallView;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdfurikunWallAd extends Activity {
    public static final int ERROR_ALREADY_DISPLAYED = 3001;
    public static final int ERROR_NOT_NETWORK_CONNECTED = 3002;
    public static final int THEME_DARK = 1;
    public static final int THEME_LIGHT = 2;
    public static final int THEME_RANDOM = 0;
    private static boolean mIsShowWallAd = false;
    private static OnAdfurikunWallAdFinishListener mOnAdfurikunWallAdFinishListener = null;
    private static WallAdUtil mWallAdUtil = null;
    private int mOrientation;
    private WallAdUtil.WallAdInfo mWallAdInfo = null;

    public static void adfurikunWallAdFinalizeAll() {
        if (mIsShowWallAd || mWallAdUtil == null) {
            return;
        }
        mWallAdUtil.removeWallAdAll();
    }

    public static void initializeWallAdSetting(Activity activity, String str) {
        if (mWallAdUtil == null) {
            mWallAdUtil = new WallAdUtil();
        }
        mWallAdUtil.initializeWallAdSetting(activity.getApplicationContext(), str);
    }

    public static boolean isLoadFinished() {
        if (mWallAdUtil != null) {
            return mWallAdUtil.isLoadFinished();
        }
        return false;
    }

    public static void setWallAdTheme(Activity activity, int i) {
        if (mWallAdUtil != null) {
            mWallAdUtil.setWallAdTheme(i);
        }
    }

    private void showWallAd() {
        WallAdUtil.WallAdLayoutInfo wallAdLayoutInfo = null;
        if (this.mWallAdInfo != null && mWallAdUtil != null) {
            wallAdLayoutInfo = mWallAdUtil.getWallAdLayoutInfo(this.mWallAdInfo.app_id);
        }
        if (wallAdLayoutInfo == null) {
            cancelWallAd();
            return;
        }
        if (wallAdLayoutInfo.start_time) {
            wallAdLayoutInfo.start_time = false;
        }
        if (wallAdLayoutInfo.ad_layout == null) {
            wallAdLayoutInfo.ad_layout = new WallAdLayout(getApplicationContext());
            if (this.mWallAdInfo.app_id.length() > 0) {
                wallAdLayoutInfo.ad_layout.setAdfurikunAppKey(this.mWallAdInfo.app_id);
            }
        } else {
            ViewGroup viewGroup = (ViewGroup) wallAdLayoutInfo.ad_layout.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(wallAdLayoutInfo.ad_layout);
            }
        }
        wallAdLayoutInfo.ad_layout.nextAd();
        int i = this.mWallAdInfo != null ? this.mWallAdInfo.theme : 0;
        wallAdLayoutInfo.ad_layout.setOnActionListener(new AdWebView.OnActionListener() { // from class: jp.tjkapp.adfurikunsdk.AdfurikunWallAd.1
            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void adClick() {
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void errorLoad() {
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void successLoad() {
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void windowClose() {
                AdfurikunWallAd.this.cancelWallAd();
            }
        });
        WallView wallView = new WallView(this, wallAdLayoutInfo.ad_layout, i);
        wallView.setOnAdfurikunWallClickListener(new WallView.OnAdfurikunWallClickListener() { // from class: jp.tjkapp.adfurikunsdk.AdfurikunWallAd.2
            @Override // jp.tjkapp.adfurikunsdk.WallView.OnAdfurikunWallClickListener
            public void onClickClose() {
                AdfurikunWallAd.this.cancelWallAd();
            }
        });
        setContentView(wallView);
    }

    public static boolean showWallAd(Activity activity, OnAdfurikunWallAdFinishListener onAdfurikunWallAdFinishListener) {
        if (mWallAdUtil == null) {
            return false;
        }
        WallAdUtil.WallAdInfo wallAdInfo = mWallAdUtil.getWallAdInfo();
        if (mIsShowWallAd || wallAdInfo == null) {
            if (onAdfurikunWallAdFinishListener == null) {
                return false;
            }
            onAdfurikunWallAdFinishListener.onAdfurikunWallAdError(3001);
            return false;
        }
        if (mWallAdUtil.isLoadFinished()) {
            mOnAdfurikunWallAdFinishListener = onAdfurikunWallAdFinishListener;
            activity.startActivity(new Intent(activity, (Class<?>) AdfurikunWallAd.class));
            return true;
        }
        if (onAdfurikunWallAdFinishListener == null) {
            return false;
        }
        onAdfurikunWallAdFinishListener.onAdfurikunWallAdError(3002);
        return false;
    }

    public void cancelWallAd() {
        if (mOnAdfurikunWallAdFinishListener != null) {
            mOnAdfurikunWallAdFinishListener.onAdfurikunWallAdClose();
        }
        mIsShowWallAd = false;
        finish();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        WallAdUtil.WallAdLayoutInfo wallAdLayoutInfo;
        if (this.mWallAdInfo == null || mWallAdUtil == null || (wallAdLayoutInfo = mWallAdUtil.getWallAdLayoutInfo(this.mWallAdInfo.app_id)) == null || !wallAdLayoutInfo.ad_layout.goBack()) {
            cancelWallAd();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mOrientation != configuration.orientation) {
            this.mOrientation = configuration.orientation;
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        mIsShowWallAd = true;
        this.mOrientation = getResources().getConfiguration().orientation;
        if (mWallAdUtil != null) {
            this.mWallAdInfo = mWallAdUtil.getWallAdInfo();
        }
        showWallAd();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        mIsShowWallAd = false;
    }
}
