package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.mediation.MediationAdRequest;

import java.io.File;
import java.lang.Thread;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.AdWebView;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdfurikunAdMob implements Thread.UncaughtExceptionHandler {
    private static AdfurikunAdMobLayout adfurikunView;
    private static AdfurikunAdMob myself;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class AdfurikunAdMobLayout extends AdfurikunLayout {
        public AdfurikunAdMobLayout(Context context, int i) {
            super(context, i);
        }

        public String getAdfurikunAppKey() {
            return this.mAppID;
        }

        @Override // jp.tjkapp.adfurikunsdk.AdfurikunLayout, jp.tjkapp.adfurikunsdk.LayoutBase
        protected void initialize(Context context, int i) {
            this.useFillerAsDefault = false;
            super.initialize(context, i);
        }

        public void setCustomEventBannerListener(final Object customEventBannerListener) {
            setOnActionListener(new AdWebView.OnActionListener() { // from class: jp.tjkapp.adfurikunsdk.AdfurikunAdMob.AdfurikunAdMobLayout.1
                @Override // jp.tjkapp.adfurikunsdk.AdWebView.OnActionListener
                public void adClick() {
                    if (customEventBannerListener != null) {
//                        customEventBannerListener.onClick();
//                        customEventBannerListener.onLeaveApplication();
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
                }
            });
        }
    }

    private void removeAdfurikunLayout() {
        if (adfurikunView != null) {
            adfurikunView.onPause();
            adfurikunView.destroy();
            adfurikunView = null;
        }
    }

    private void removeParent() {
        ViewGroup viewGroup;
        if (adfurikunView == null || (viewGroup = (ViewGroup) adfurikunView.getParent()) == null) {
            return;
        }
        viewGroup.removeView(adfurikunView);
    }

//    @Override // com.google.ads.mediation.customevent.CustomEvent
    public void destroy() {
        if (myself == null || !equals(myself)) {
            return;
        }
        removeAdfurikunLayout();
    }

//    @Override // com.google.ads.mediation.customevent.CustomEventBanner
    public void requestBannerAd(Object customEventBannerListener, Activity activity, String str, String str2, AdSize adSize, MediationAdRequest mediationAdRequest, Object obj) {
        myself = this;
        String str3 = BuildConfig.FLAVOR;
        if (str2 != null && str2.length() > 0) {
            str3 = str2;
        }
        if (str3 == null || str3.length() <= 0) {
//            customEventBannerListener.onFailedToReceiveAd();
            return;
        }
        int height = adSize != null ? (int) ((adSize.getHeight() * activity.getResources().getDisplayMetrics().density) + 0.5f) : -2;
        boolean z = false;
        try {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, height);
            if (adfurikunView == null) {
                adfurikunView = new AdfurikunAdMobLayout(activity, height);
                adfurikunView.setAdfurikunAppKey(str3);
                adfurikunView.setLayoutParams(layoutParams);
            } else if (str3.equals(adfurikunView.getAdfurikunAppKey())) {
                removeParent();
                z = true;
            } else {
                removeAdfurikunLayout();
                adfurikunView = new AdfurikunAdMobLayout(activity, height);
                adfurikunView.setAdfurikunAppKey(str3);
                adfurikunView.setLayoutParams(layoutParams);
            }
            if (!new File(FileUtil.getGetInfoFilePath(activity.getApplicationContext(), str3)).exists()) {
//                customEventBannerListener.onFailedToReceiveAd();
                return;
            }
            adfurikunView.setCustomEventBannerListener(customEventBannerListener);
            adfurikunView.onResume();
            if (z) {
                adfurikunView.nextAd();
            }
//            customEventBannerListener.onReceivedAd(adfurikunView);
        } catch (Error e) {
        } catch (Exception e2) {
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
    }
}
