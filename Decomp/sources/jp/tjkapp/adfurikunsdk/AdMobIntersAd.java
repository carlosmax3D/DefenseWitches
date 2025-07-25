package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import com.google.ads.mediation.customevent.CustomEventInterstitialListener;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.AdWebView;
import jp.tjkapp.adfurikunsdk.IntersView;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdMobIntersAd extends Activity {
    private static CustomEventInterstitialListener mCustomEventInterstitialListener;
    private static IntersAdLayout mIntersAdLayout;
    private static boolean mIsShowIntersAd = false;
    private int mOrientation;

    protected static void adMobIntersFinalize() {
        if (mIsShowIntersAd || mIntersAdLayout == null) {
            return;
        }
        mIntersAdLayout.destroy();
        mIntersAdLayout = null;
    }

    protected static boolean isLoadFinished() {
        if (mIntersAdLayout != null) {
            return mIntersAdLayout.isLoadFinished();
        }
        return false;
    }

    protected static void setAdMobIntersInfo(Activity activity, String str) {
        if (mIntersAdLayout == null) {
            mIntersAdLayout = new IntersAdLayout(activity, true);
        }
        mIntersAdLayout.setAdfurikunAppKey(str);
        mIntersAdLayout.nextAd();
    }

    protected static boolean showAdMobIntersAd(Activity activity, CustomEventInterstitialListener customEventInterstitialListener) {
        if (mIsShowIntersAd) {
            return false;
        }
        mCustomEventInterstitialListener = customEventInterstitialListener;
        activity.startActivity(new Intent(activity, (Class<?>) AdMobIntersAd.class));
        return true;
    }

    private void showIntersAd() {
        if (mIntersAdLayout == null) {
            cancelIntersAd();
            return;
        }
        ViewGroup viewGroup = (ViewGroup) mIntersAdLayout.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(mIntersAdLayout);
        }
        mIntersAdLayout.setOnActionListener(new AdWebView.OnActionListener() { // from class: jp.tjkapp.adfurikunsdk.AdMobIntersAd.1
            @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
            public void adClick() {
                if (AdMobIntersAd.mCustomEventInterstitialListener != null) {
                    AdMobIntersAd.mCustomEventInterstitialListener.onLeaveApplication();
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
                AdMobIntersAd.this.cancelIntersAd();
            }
        });
        IntersView intersView = new IntersView(this, BuildConfig.FLAVOR, mIntersAdLayout, BuildConfig.FLAVOR);
        intersView.setOnAdfurikunIntersClickListener(new IntersView.OnAdfurikunIntersClickListener() { // from class: jp.tjkapp.adfurikunsdk.AdMobIntersAd.2
            @Override // jp.tjkapp.adfurikunsdk.IntersView.OnAdfurikunIntersClickListener
            public void onClickCancel() {
                AdMobIntersAd.this.cancelIntersAd();
            }

            @Override // jp.tjkapp.adfurikunsdk.IntersView.OnAdfurikunIntersClickListener
            public void onClickCustom() {
                AdMobIntersAd.this.cancelIntersAd();
            }
        });
        setContentView(intersView);
    }

    protected void cancelIntersAd() {
        if (mCustomEventInterstitialListener != null) {
            mCustomEventInterstitialListener.onDismissScreen();
        }
        mIsShowIntersAd = false;
        finish();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        cancelIntersAd();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mOrientation != configuration.orientation) {
            this.mOrientation = configuration.orientation;
            showIntersAd();
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        mIsShowIntersAd = true;
        this.mOrientation = getResources().getConfiguration().orientation;
        showIntersAd();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        mIsShowIntersAd = false;
        if (mIntersAdLayout != null) {
            mIntersAdLayout.destroy();
            mIntersAdLayout = null;
        }
    }
}
