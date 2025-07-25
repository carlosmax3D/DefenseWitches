package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.res.Configuration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Iterator;
import jp.tjkapp.adfurikunsdk.FileUtil;
import jp.tjkapp.adfurikunsdk.IntersAdUtil;
import jp.tjkapp.adfurikunsdk.IntersView;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class UnityLayout extends RelativeLayout {
    private static final int AD_HEIGHT = 50;
    public static final int ERROR_ALREADY_DISPLAYED = 2001;
    public static final int ERROR_NOT_NETWORK_CONNECTED = 2002;
    public static final int TRANSITION_TYPE_FADEIN_FADEOUT = 6;
    public static final int TRANSITION_TYPE_NOTHING = 0;
    public static final int TRANSITION_TYPE_RANDOM = 1;
    public static final int TRANSITION_TYPE_SLIDE_FROM_BOTTOM = 5;
    public static final int TRANSITION_TYPE_SLIDE_FROM_LEFT = 3;
    public static final int TRANSITION_TYPE_SLIDE_FROM_RIGHT = 2;
    public static final int TRANSITION_TYPE_SLIDE_FROM_TOP = 4;
    private int mAdHeight;
    private RelativeLayout mBannerAdRoot;
    private ArrayList<AdfurikunLayoutInfo> mBannerLayoutList;
    private RelativeLayout mCustomSizeAdRoot;
    private ArrayList<AdfurikunLayoutInfo> mCustomSizeLayoutList;
    private IntersAdUtil.IntersAdInfo mIntersAdInfo;
    private RelativeLayout mIntersAdRoot;
    private IntersAdUtil mIntersAdUtil;
    private boolean mIsShowIntersAd;
    private OnAdfurikunIntersAdFinishListener mOnAdfurikunIntersAdFinishListener;
    private int mOrientation;
    private float mScale;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class AdfurikunLayoutInfo {
        public boolean start_time = true;
        public AdfurikunLayout ad_layout = null;

        AdfurikunLayoutInfo() {
        }
    }

    public UnityLayout(Context context) {
        super(context);
        this.mBannerLayoutList = null;
        this.mCustomSizeLayoutList = null;
        this.mScale = 1.0f;
        this.mAdHeight = 50;
        this.mIsShowIntersAd = false;
        this.mOnAdfurikunIntersAdFinishListener = null;
        this.mIntersAdUtil = null;
        this.mIntersAdInfo = null;
        initialize(context);
    }

    private AdfurikunLayout createAdfurikunLayout(String str, int i, boolean z) {
        AdfurikunLayout adfurikunLayout = new AdfurikunLayout(getContext());
        adfurikunLayout.setAdfurikunAppKey(str);
        switch (i) {
            case 1:
                adfurikunLayout.setTransitionType(-2);
                return adfurikunLayout;
            case 2:
                adfurikunLayout.setTransitionType(0);
                return adfurikunLayout;
            case 3:
                adfurikunLayout.setTransitionType(1);
                return adfurikunLayout;
            case 4:
                adfurikunLayout.setTransitionType(2);
                return adfurikunLayout;
            case 5:
                adfurikunLayout.setTransitionType(3);
                return adfurikunLayout;
            case 6:
                adfurikunLayout.setTransitionType(4);
                return adfurikunLayout;
            default:
                adfurikunLayout.setTransitionType(-1);
                return adfurikunLayout;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hideIntersAd() {
        if (!this.mIsShowIntersAd) {
            return false;
        }
        this.mIsShowIntersAd = false;
        this.mIntersAdRoot.setVisibility(4);
        return true;
    }

    private void initialize(Context context) {
        this.mBannerLayoutList = new ArrayList<>();
        this.mCustomSizeLayoutList = new ArrayList<>();
        this.mScale = getResources().getDisplayMetrics().density;
        this.mAdHeight = (int) ((50.0f * this.mScale) + 0.5f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.mBannerAdRoot = new RelativeLayout(context);
        this.mCustomSizeAdRoot = new RelativeLayout(context);
        this.mIntersAdRoot = new RelativeLayout(context);
        addView(this.mBannerAdRoot, layoutParams);
        addView(this.mCustomSizeAdRoot, layoutParams);
        addView(this.mIntersAdRoot, layoutParams);
        this.mIntersAdRoot.setVisibility(4);
        this.mOrientation = getResources().getConfiguration().orientation;
        this.mIntersAdUtil = new IntersAdUtil();
    }

    private void showIntersAd(IntersAdUtil.IntersAdInfo intersAdInfo) {
        boolean z;
        this.mIntersAdRoot.removeAllViews();
        if (intersAdInfo.app_id.length() <= 0) {
            if (this.mOnAdfurikunIntersAdFinishListener != null) {
                this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(-1);
            }
            hideIntersAd();
            return;
        }
        this.mIntersAdInfo = intersAdInfo;
        IntersAdUtil.IntersAdLayoutInfo intersAdLayoutInfo = this.mIntersAdUtil.getIntersAdLayoutInfo(this.mIntersAdInfo.app_id);
        if (intersAdLayoutInfo == null) {
            if (this.mOnAdfurikunIntersAdFinishListener != null) {
                this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(-1);
            }
            hideIntersAd();
            return;
        }
        if (intersAdLayoutInfo.start_time) {
            intersAdLayoutInfo.start_time = false;
            z = false;
        } else {
            z = true;
        }
        Context context = getContext();
        if (intersAdLayoutInfo.ad_layout == null) {
            intersAdLayoutInfo.ad_layout = new IntersAdLayout(context);
            if (this.mIntersAdInfo.app_id.length() > 0) {
                intersAdLayoutInfo.ad_layout.setAdfurikunAppKey(this.mIntersAdInfo.app_id);
            }
        } else {
            ViewGroup viewGroup = (ViewGroup) intersAdLayoutInfo.ad_layout.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(intersAdLayoutInfo.ad_layout);
            }
        }
        if (intersAdLayoutInfo.ad_layout.useSingleMode) {
            z = true;
        }
        if (z) {
            intersAdLayoutInfo.ad_layout.nextAd();
        }
        IntersView intersView = new IntersView(context, this.mIntersAdInfo.titlebar_text, intersAdLayoutInfo.ad_layout, this.mIntersAdInfo.custom_button_name);
        intersView.setOnAdfurikunIntersClickListener(new IntersView.OnAdfurikunIntersClickListener() { // from class: jp.tjkapp.adfurikunsdk.UnityLayout.1
            @Override // jp.tjkapp.adfurikunsdk.IntersView.OnAdfurikunIntersClickListener
            public void onClickCancel() {
                UnityLayout.this.cancelIntersAd();
            }

            @Override // jp.tjkapp.adfurikunsdk.IntersView.OnAdfurikunIntersClickListener
            public void onClickCustom() {
                if (UnityLayout.this.mOnAdfurikunIntersAdFinishListener != null) {
                    if (UnityLayout.this.mIntersAdInfo != null) {
                        UnityLayout.this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdCustomClose(UnityLayout.this.mIntersAdInfo.index);
                    } else {
                        UnityLayout.this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdCustomClose(-1);
                    }
                }
                UnityLayout.this.hideIntersAd();
            }
        });
        this.mIntersAdRoot.addView(intersView);
        this.mIsShowIntersAd = true;
        this.mIntersAdRoot.setVisibility(0);
    }

    public void addBannerAd(String str, int i, int i2, boolean z) {
        AdfurikunLayoutInfo adfurikunLayoutInfo = new AdfurikunLayoutInfo();
        adfurikunLayoutInfo.ad_layout = createAdfurikunLayout(str, i2, z);
        adfurikunLayoutInfo.ad_layout.startRotateAd();
        adfurikunLayoutInfo.ad_layout.onResume();
        if (z) {
            adfurikunLayoutInfo.start_time = false;
            adfurikunLayoutInfo.ad_layout.setVisibility(0);
        } else {
            adfurikunLayoutInfo.ad_layout.stopRotateAd();
            adfurikunLayoutInfo.ad_layout.setVisibility(4);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, this.mAdHeight);
        if (i == 0) {
            layoutParams.addRule(10, -1);
        } else {
            layoutParams.addRule(12, -1);
        }
        this.mBannerAdRoot.addView(adfurikunLayoutInfo.ad_layout, layoutParams);
        this.mBannerLayoutList.add(adfurikunLayoutInfo);
    }

    public void addCustomSizeAd(String str, float f, float f2, float f3, float f4, int i, boolean z) {
        AdfurikunLayoutInfo adfurikunLayoutInfo = new AdfurikunLayoutInfo();
        adfurikunLayoutInfo.ad_layout = createAdfurikunLayout(str, i, z);
        adfurikunLayoutInfo.ad_layout.startRotateAd();
        adfurikunLayoutInfo.ad_layout.onResume();
        if (z) {
            adfurikunLayoutInfo.start_time = false;
            adfurikunLayoutInfo.ad_layout.setVisibility(0);
        } else {
            adfurikunLayoutInfo.ad_layout.stopRotateAd();
            adfurikunLayoutInfo.ad_layout.setVisibility(4);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.leftMargin = (int) f;
        layoutParams.topMargin = (int) f2;
        layoutParams.width = (int) (f3 + 0.5f);
        layoutParams.height = (int) (f4 + 0.5f);
        this.mCustomSizeAdRoot.addView(adfurikunLayoutInfo.ad_layout, layoutParams);
        this.mCustomSizeLayoutList.add(adfurikunLayoutInfo);
    }

    public void addIntersAdSetting(String str, String str2, int i, int i2, String str3, String str4) {
        this.mIntersAdUtil.addIntersAdSetting(getContext(), str, str2, i, i2, str4);
    }

    public void cancelIntersAd() {
        if (!hideIntersAd() || this.mOnAdfurikunIntersAdFinishListener == null) {
            return;
        }
        if (this.mIntersAdInfo != null) {
            this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(this.mIntersAdInfo.index);
        } else {
            this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(-1);
        }
    }

    public void hideBannerAd(int i) {
        if (i < this.mBannerLayoutList.size()) {
            AdfurikunLayoutInfo adfurikunLayoutInfo = this.mBannerLayoutList.get(i);
            adfurikunLayoutInfo.ad_layout.stopRotateAd();
            adfurikunLayoutInfo.ad_layout.setVisibility(4);
        }
    }

    public void hideCustomSizeAd(int i) {
        if (i < this.mCustomSizeLayoutList.size()) {
            AdfurikunLayoutInfo adfurikunLayoutInfo = this.mCustomSizeLayoutList.get(i);
            adfurikunLayoutInfo.ad_layout.stopRotateAd();
            adfurikunLayoutInfo.ad_layout.setVisibility(4);
        }
    }

    public void nextBannerAd(int i) {
        if (i < this.mBannerLayoutList.size()) {
            this.mBannerLayoutList.get(i).ad_layout.nextAd();
        }
    }

    public void nextCustomSizeAd(int i) {
        if (i < this.mCustomSizeLayoutList.size()) {
            this.mCustomSizeLayoutList.get(i).ad_layout.nextAd();
        }
    }

    public void onDestroy() {
        Iterator<AdfurikunLayoutInfo> it = this.mBannerLayoutList.iterator();
        while (it.hasNext()) {
            it.next().ad_layout.destroy();
        }
        Iterator<AdfurikunLayoutInfo> it2 = this.mCustomSizeLayoutList.iterator();
        while (it2.hasNext()) {
            it2.next().ad_layout.destroy();
        }
        this.mBannerLayoutList.removeAll(this.mBannerLayoutList);
        this.mCustomSizeLayoutList.removeAll(this.mCustomSizeLayoutList);
        this.mIntersAdUtil.removeIntersAdAll();
    }

    public void onPause() {
        Iterator<AdfurikunLayoutInfo> it = this.mBannerLayoutList.iterator();
        while (it.hasNext()) {
            it.next().ad_layout.onPause();
        }
        Iterator<AdfurikunLayoutInfo> it2 = this.mCustomSizeLayoutList.iterator();
        while (it2.hasNext()) {
            it2.next().ad_layout.onPause();
        }
    }

    public void onResume() {
        Iterator<AdfurikunLayoutInfo> it = this.mBannerLayoutList.iterator();
        while (it.hasNext()) {
            it.next().ad_layout.onResume();
        }
        Iterator<AdfurikunLayoutInfo> it2 = this.mCustomSizeLayoutList.iterator();
        while (it2.hasNext()) {
            it2.next().ad_layout.onResume();
        }
    }

    public void restartBannerAd(int i) {
        if (i < this.mBannerLayoutList.size()) {
            this.mBannerLayoutList.get(i).ad_layout.restartRotateAd();
        }
    }

    public void restartCustomSizeAd(int i) {
        if (i < this.mCustomSizeLayoutList.size()) {
            this.mCustomSizeLayoutList.get(i).ad_layout.restartRotateAd();
        }
    }

    public void setAdfurikunTestMode(int i) {
        FileUtil.setTestMode(getContext(), i);
    }

    public void showBannerAd(int i) {
        if (i < this.mBannerLayoutList.size()) {
            AdfurikunLayoutInfo adfurikunLayoutInfo = this.mBannerLayoutList.get(i);
            adfurikunLayoutInfo.ad_layout.setVisibility(0);
            if (adfurikunLayoutInfo.start_time) {
                adfurikunLayoutInfo.start_time = false;
                adfurikunLayoutInfo.ad_layout.restartRotateAd();
            } else {
                adfurikunLayoutInfo.ad_layout.startRotateAd();
                adfurikunLayoutInfo.ad_layout.onResume();
            }
        }
    }

    public void showCustomSizeAd(int i) {
        if (i < this.mCustomSizeLayoutList.size()) {
            AdfurikunLayoutInfo adfurikunLayoutInfo = this.mCustomSizeLayoutList.get(i);
            adfurikunLayoutInfo.ad_layout.setVisibility(0);
            if (adfurikunLayoutInfo.start_time) {
                adfurikunLayoutInfo.start_time = false;
                adfurikunLayoutInfo.ad_layout.restartRotateAd();
            } else {
                adfurikunLayoutInfo.ad_layout.startRotateAd();
                adfurikunLayoutInfo.ad_layout.onResume();
            }
        }
    }

    public boolean showIntersAd(int i, OnAdfurikunIntersAdFinishListener onAdfurikunIntersAdFinishListener) {
        boolean z = false;
        Configuration configuration = getResources().getConfiguration();
        IntersAdUtil.IntersAdInfo intersAdInfo = this.mIntersAdUtil.getIntersAdInfo(i);
        if ((this.mIsShowIntersAd && this.mOrientation == configuration.orientation) || intersAdInfo == null) {
            if (onAdfurikunIntersAdFinishListener != null) {
                onAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(i, 2001);
            }
        } else if (this.mIntersAdUtil.isLoadFinished(i)) {
            Context context = getContext();
            this.mOrientation = configuration.orientation;
            String str = intersAdInfo.index + "_" + intersAdInfo.app_id;
            FileUtil.IntersAdPref intersAdPref = FileUtil.getIntersAdPref(context, str);
            if (intersAdInfo.max <= 0 || (intersAdInfo.max > 0 && intersAdPref.max_ct < intersAdInfo.max)) {
                if (intersAdPref.frequency_ct == 0) {
                    this.mOnAdfurikunIntersAdFinishListener = onAdfurikunIntersAdFinishListener;
                    showIntersAd(intersAdInfo);
                    intersAdPref.max_ct++;
                    z = true;
                } else if (onAdfurikunIntersAdFinishListener != null) {
                    onAdfurikunIntersAdFinishListener.onAdfurikunIntersAdSkip(i);
                }
                intersAdPref.frequency_ct++;
                if (intersAdPref.frequency_ct >= intersAdInfo.frequency) {
                    intersAdPref.frequency_ct = 0;
                }
                FileUtil.setIntersAdPref(context, str, intersAdPref);
            } else if (onAdfurikunIntersAdFinishListener != null) {
                onAdfurikunIntersAdFinishListener.onAdfurikunIntersAdMaxEnd(i);
            }
        } else if (onAdfurikunIntersAdFinishListener != null) {
            onAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(i, 2002);
        }
        return z;
    }

    public void stopBannerAd(int i) {
        if (i < this.mBannerLayoutList.size()) {
            this.mBannerLayoutList.get(i).ad_layout.stopRotateAd();
        }
    }

    public void stopCustomSizeAd(int i) {
        if (i < this.mCustomSizeLayoutList.size()) {
            this.mCustomSizeLayoutList.get(i).ad_layout.stopRotateAd();
        }
    }
}
