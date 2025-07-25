package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import com.google.ads.mediation.customevent.CustomEventInterstitialListener;
import jp.tjkapp.adfurikunsdk.AdWebView;
import jp.tjkapp.adfurikunsdk.WallView;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdMobWallAd extends Activity {
    private static WallAdLayout mWallAdLayout;
    private int mOrientation;
    private static boolean mIsShowWallAd = false;
    private static CustomEventInterstitialListener mCustomEventInterstitialListener = null;

    protected static void adMobWallFinalize() {
        if (mIsShowWallAd || mWallAdLayout == null) {
            return;
        }
        mWallAdLayout.destroy();
        mWallAdLayout = null;
    }

    protected static boolean isLoadFinished() {
        if (mWallAdLayout != null) {
            return mWallAdLayout.isLoadFinished();
        }
        return false;
    }

    protected static void setAdMobWallInfo(Activity activity, String str) {
        if (mWallAdLayout == null) {
            mWallAdLayout = new WallAdLayout(activity);
        }
        mWallAdLayout.setAdfurikunAppKey(str);
    }

    protected static boolean showAdMobWallAd(Activity activity, CustomEventInterstitialListener customEventInterstitialListener) {
        if (mIsShowWallAd) {
            return false;
        }
        mCustomEventInterstitialListener = customEventInterstitialListener;
        activity.startActivity(new Intent(activity, (Class<?>) AdMobWallAd.class));
        return true;
    }

    private void showWallAd() {
        if (mWallAdLayout == null) {
            cancelWallAd();
            return;
        }
        ViewGroup viewGroup = (ViewGroup) mWallAdLayout.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(mWallAdLayout);
        }
        mWallAdLayout.nextAd();
        mWallAdLayout.setOnActionListener(new AdWebView.OnActionListener() { // from class: jp.tjkapp.adfurikunsdk.AdMobWallAd.1
            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void adClick() {
                if (AdMobWallAd.mCustomEventInterstitialListener != null) {
                    AdMobWallAd.mCustomEventInterstitialListener.onLeaveApplication();
                }
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void errorLoad() {
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void successLoad() {
            }

            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void windowClose() {
                AdMobWallAd.this.cancelWallAd();
            }
        });
        WallView wallView = new WallView(this, mWallAdLayout, 0);
        wallView.setOnAdfurikunWallClickListener(new WallView.OnAdfurikunWallClickListener() { // from class: jp.tjkapp.adfurikunsdk.AdMobWallAd.2
            @Override // jp.tjkapp.adfurikunsdk.WallView.OnAdfurikunWallClickListener
            public void onClickClose() {
                AdMobWallAd.this.cancelWallAd();
            }
        });
        setContentView(wallView);
    }

    protected void cancelWallAd() {
        if (mCustomEventInterstitialListener != null) {
            mCustomEventInterstitialListener.onDismissScreen();
        }
        mIsShowWallAd = false;
        finish();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (mWallAdLayout == null) {
            cancelWallAd();
        } else {
            if (mWallAdLayout.goBack()) {
                return;
            }
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
        showWallAd();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        mIsShowWallAd = false;
        if (mWallAdLayout != null) {
            mWallAdLayout.destroy();
            mWallAdLayout = null;
        }
    }
}
