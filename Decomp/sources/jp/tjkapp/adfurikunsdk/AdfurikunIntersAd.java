package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import jp.tjkapp.adfurikunsdk.FileUtil;
import jp.tjkapp.adfurikunsdk.IntersAdUtil;
import jp.tjkapp.adfurikunsdk.IntersView;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdfurikunIntersAd extends Activity {
    public static final int ERROR_ALREADY_DISPLAYED = 1001;
    public static final int ERROR_NOT_NETWORK_CONNECTED = 1002;
    private IntersAdUtil.IntersAdInfo mIntersAdInfo = null;
    private int mOrientation;
    private static boolean mIsShowIntersAd = false;
    private static OnAdfurikunIntersAdFinishListener mOnAdfurikunIntersAdFinishListener = null;
    private static IntersAdUtil mIntersAdUtil = null;

    public static void addIntersAdSetting(Activity activity, String str, String str2, int i, int i2, String str3, String str4) {
        if (mIntersAdUtil == null) {
            mIntersAdUtil = new IntersAdUtil();
        }
        mIntersAdUtil.addIntersAdSetting(activity.getApplicationContext(), str, str2, i, i2, str4);
    }

    public static void adfurikunIntersAdFinalizeAll() {
        if (mIsShowIntersAd || mIntersAdUtil == null) {
            return;
        }
        mIntersAdUtil.removeIntersAdAll();
    }

    public static boolean isLoadFinished(int i) {
        if (mIntersAdUtil != null) {
            return mIntersAdUtil.isLoadFinished(i);
        }
        return false;
    }

    private void showIntersAd(boolean z) {
        boolean z2;
        IntersAdUtil.IntersAdLayoutInfo intersAdLayoutInfo = null;
        if (this.mIntersAdInfo != null && mIntersAdUtil != null) {
            intersAdLayoutInfo = mIntersAdUtil.getIntersAdLayoutInfo(this.mIntersAdInfo.app_id);
        }
        if (intersAdLayoutInfo == null) {
            cancelIntersAd();
            return;
        }
        if (intersAdLayoutInfo.ad_layout == null) {
            intersAdLayoutInfo.ad_layout = new IntersAdLayout(getApplicationContext());
            if (this.mIntersAdInfo.app_id.length() > 0) {
                intersAdLayoutInfo.ad_layout.setAdfurikunAppKey(this.mIntersAdInfo.app_id);
            }
        } else {
            ViewGroup viewGroup = (ViewGroup) intersAdLayoutInfo.ad_layout.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(intersAdLayoutInfo.ad_layout);
            }
        }
        if (intersAdLayoutInfo.start_time) {
            intersAdLayoutInfo.start_time = false;
            z2 = intersAdLayoutInfo.ad_layout.useSingleMode;
        } else {
            z2 = true;
        }
        if (z && z2) {
            intersAdLayoutInfo.ad_layout.nextAd();
        }
        IntersView intersView = new IntersView(this, this.mIntersAdInfo.titlebar_text, intersAdLayoutInfo.ad_layout, this.mIntersAdInfo.custom_button_name);
        intersView.setOnAdfurikunIntersClickListener(new IntersView.OnAdfurikunIntersClickListener() { // from class: jp.tjkapp.adfurikunsdk.AdfurikunIntersAd.1
            @Override // jp.tjkapp.adfurikunsdk.IntersView.OnAdfurikunIntersClickListener
            public void onClickCancel() {
                AdfurikunIntersAd.this.cancelIntersAd();
            }

            @Override // jp.tjkapp.adfurikunsdk.IntersView.OnAdfurikunIntersClickListener
            public void onClickCustom() {
                if (AdfurikunIntersAd.mOnAdfurikunIntersAdFinishListener != null) {
                    if (AdfurikunIntersAd.this.mIntersAdInfo != null) {
                        AdfurikunIntersAd.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdCustomClose(AdfurikunIntersAd.this.mIntersAdInfo.index);
                    } else {
                        AdfurikunIntersAd.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdCustomClose(-1);
                    }
                }
                boolean unused = AdfurikunIntersAd.mIsShowIntersAd = false;
                AdfurikunIntersAd.this.finish();
            }
        });
        setContentView(intersView);
    }

    public static boolean showIntersAd(Activity activity, int i, OnAdfurikunIntersAdFinishListener onAdfurikunIntersAdFinishListener) {
        boolean z = false;
        if (mIntersAdUtil != null) {
            IntersAdUtil.IntersAdInfo intersAdInfo = mIntersAdUtil.getIntersAdInfo(i);
            if (mIsShowIntersAd || intersAdInfo == null) {
                if (onAdfurikunIntersAdFinishListener != null) {
                    onAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(i, 1001);
                }
            } else if (mIntersAdUtil.isLoadFinished(i)) {
                String str = intersAdInfo.index + "_" + intersAdInfo.app_id;
                Context applicationContext = activity.getApplicationContext();
                FileUtil.IntersAdPref intersAdPref = FileUtil.getIntersAdPref(applicationContext, str);
                if (intersAdInfo.max <= 0 || (intersAdInfo.max > 0 && intersAdPref.max_ct < intersAdInfo.max)) {
                    if (intersAdPref.frequency_ct == 0) {
                        mOnAdfurikunIntersAdFinishListener = onAdfurikunIntersAdFinishListener;
                        Intent intent = new Intent(activity, (Class<?>) AdfurikunIntersAd.class);
                        intent.putExtra(Constants.EXTRA_INTERS_AD_INDEX, i);
                        activity.startActivity(intent);
                        intersAdPref.max_ct++;
                        z = true;
                    } else if (onAdfurikunIntersAdFinishListener != null) {
                        onAdfurikunIntersAdFinishListener.onAdfurikunIntersAdSkip(i);
                    }
                    intersAdPref.frequency_ct++;
                    if (intersAdPref.frequency_ct >= intersAdInfo.frequency) {
                        intersAdPref.frequency_ct = 0;
                    }
                    FileUtil.setIntersAdPref(applicationContext, str, intersAdPref);
                } else if (onAdfurikunIntersAdFinishListener != null) {
                    onAdfurikunIntersAdFinishListener.onAdfurikunIntersAdMaxEnd(i);
                }
            } else if (onAdfurikunIntersAdFinishListener != null) {
                onAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(i, 1002);
            }
        }
        return z;
    }

    public void cancelIntersAd() {
        if (mOnAdfurikunIntersAdFinishListener != null) {
            if (this.mIntersAdInfo != null) {
                mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(this.mIntersAdInfo.index);
            } else {
                mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(-1);
            }
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
            showIntersAd(false);
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        mIsShowIntersAd = true;
        this.mOrientation = getResources().getConfiguration().orientation;
        Intent intent = getIntent();
        int intExtra = intent != null ? intent.getIntExtra(Constants.EXTRA_INTERS_AD_INDEX, -1) : -1;
        if (mIntersAdUtil != null) {
            this.mIntersAdInfo = mIntersAdUtil.getIntersAdInfo(intExtra);
        }
        showIntersAd(true);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        mIsShowIntersAd = false;
    }
}
