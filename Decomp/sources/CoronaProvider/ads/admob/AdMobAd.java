package CoronaProvider.ads.admob;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.Crypto;
import com.facebook.appevents.AppEventsConstants;
import com.flurry.android.Constants;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.tapjoy.TapjoyConstants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import jp.stargarage.g2metrics.BuildConfig;

public class AdMobAd {
    public static final String BANNER = "banner";
    public static final String INTERSTITIAL = "interstitial";
    public static final String LOADED = "loaded";
    public static final String REFRESHED = "refreshed";
    public static final String SHOWN = "shown";
    private AbsoluteLayout fAbsoluteLayout;
    /* access modifiers changed from: private */
    public AdView fAdView;
    private String fAppId;
    private Handler fHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public InterstitialAd fInterstitialAd;
    volatile boolean fInterstitialIsLoaded = false;
    /* access modifiers changed from: private */
    public ViewGroup.LayoutParams fLayoutParams;
    private RelativeLayout fRelativeLayout;
    /* access modifiers changed from: private */
    public ViewGroup fViewGroup;

    private class AdmobBannerListener extends AdmobListener {
        private boolean fFirstTime = true;

        public AdmobBannerListener() {
            super();
        }

        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            LuaLoader.dispatchEvent(true, translateErrorCode(i), AdMobAd.BANNER, AdMobAd.LOADED);
        }

        public void onAdLoaded() {
            if (this.fFirstTime) {
                AdMobAd.this.fViewGroup.addView(AdMobAd.this.fAdView, AdMobAd.this.fLayoutParams);
                this.fFirstTime = false;
                LuaLoader.dispatchEvent(false, BuildConfig.FLAVOR, AdMobAd.BANNER, AdMobAd.SHOWN);
            } else {
                LuaLoader.dispatchEvent(false, BuildConfig.FLAVOR, AdMobAd.BANNER, AdMobAd.REFRESHED);
            }
            super.onAdLoaded();
        }
    }

    private class AdmobInterstitialListener extends AdmobListener {
        boolean mShouldShow;

        public AdmobInterstitialListener(boolean z) {
            super();
            this.mShouldShow = z;
            AdMobAd.this.fInterstitialIsLoaded = false;
        }

        public void onAdClosed() {
            super.onAdClosed();
            LuaLoader.dispatchEvent(false, (String) null, "interstitial", AdMobAd.SHOWN);
        }

        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            LuaLoader.dispatchEvent(true, translateErrorCode(i), "interstitial", AdMobAd.LOADED);
            AdMobAd.this.fInterstitialIsLoaded = false;
        }

        public void onAdLoaded() {
            AdMobAd.this.fInterstitialIsLoaded = true;
            if (this.mShouldShow) {
                AdMobAd.this.fInterstitialAd.show();
            } else {
                LuaLoader.dispatchEvent(false, BuildConfig.FLAVOR, "interstitial", AdMobAd.LOADED);
            }
            super.onAdLoaded();
        }
    }

    private abstract class AdmobListener extends AdListener {
        private AdmobListener() {
        }

        /* access modifiers changed from: protected */
        public String translateErrorCode(int i) {
            switch (i) {
                case 0:
                    return "Something happened internally in the AdmobSDK; for instance, an invalid response was received from the ad server.";
                case 1:
                    return "The ad request was invalid; for instance, the ad unit ID was incorrect.";
                case 2:
                    return "The ad request was unsuccessful due to network connectivity.";
                case 3:
                    return "The ad request was successful, but no ad was returned due to lack of ad inventory.";
                default:
                    return BuildConfig.FLAVOR;
            }
        }
    }

    AdMobAd(String str) {
        this.fAppId = str;
    }

    public static String getDeviceId() {
        String str = BuildConfig.FLAVOR;
        try {
            byte[] digest = MessageDigest.getInstance(Crypto.ALGORITHM_MD5).digest(Settings.Secure.getString(CoronaEnvironment.getApplicationContext().getContentResolver(), TapjoyConstants.TJC_ANDROID_ID).getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & Constants.UNKNOWN);
                while (hexString.length() < 2) {
                    hexString = AppEventsConstants.EVENT_PARAM_VALUE_NO + hexString;
                }
                stringBuffer.append(hexString);
            }
            str = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str.toUpperCase();
    }

    public void destroy() {
        this.fHandler.post(new Runnable() {
            public void run() {
                if (AdMobAd.this.fAdView != null) {
                    AdMobAd.this.fAdView.destroy();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public AbsoluteLayout getParent() {
        if (this.fAbsoluteLayout == null) {
            this.fAbsoluteLayout = new AbsoluteLayout(CoronaEnvironment.getCoronaActivity());
            CoronaEnvironment.getCoronaActivity().getOverlayView().addView(this.fAbsoluteLayout);
        }
        return this.fAbsoluteLayout;
    }

    /* access modifiers changed from: protected */
    public int getParentHeight() {
        return CoronaEnvironment.getCoronaActivity().getOverlayView().getHeight();
    }

    /* access modifiers changed from: protected */
    public RelativeLayout getRelativeParent() {
        if (this.fRelativeLayout == null) {
            this.fRelativeLayout = new RelativeLayout(CoronaEnvironment.getCoronaActivity());
            CoronaEnvironment.getCoronaActivity().getOverlayView().addView(this.fRelativeLayout);
        }
        return this.fRelativeLayout;
    }

    /* access modifiers changed from: package-private */
    public void hide() {
        if (CoronaEnvironment.getCoronaActivity() != null) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (AdMobAd.this.getParent() != null) {
                        AdMobAd.this.getParent().removeView(AdMobAd.this.fAdView);
                    }
                    if (AdMobAd.this.getRelativeParent() != null) {
                        AdMobAd.this.getRelativeParent().removeView(AdMobAd.this.fAdView);
                    }
                    if (AdMobAd.this.fAdView != null) {
                        AdMobAd.this.fAdView.destroy();
                        AdView unused = AdMobAd.this.fAdView = null;
                    }
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isLoaded(String str) {
        if (str.equals("interstitial")) {
            return this.fInterstitialIsLoaded;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void load(String str, boolean z) {
        if (str.equals("interstitial")) {
            loadInterstital(z);
        }
    }

    /* access modifiers changed from: package-private */
    public void loadInterstital(final boolean z) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            LuaLoader.dispatchEvent(true, "No corona activity", (String) null, (String) null);
            return;
        }
        final String str = this.fAppId;
        if (str == null || str.length() <= 0) {
            LuaLoader.dispatchEvent(true, "No app id", (String) null, (String) null);
        } else {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    InterstitialAd unused = AdMobAd.this.fInterstitialAd = new InterstitialAd(CoronaEnvironment.getCoronaActivity());
                    AdMobAd.this.fInterstitialAd.setAdUnitId(str);
                    AdMobAd.this.fInterstitialAd.setAdListener(new AdmobInterstitialListener(false));
                    AdRequest.Builder builder = new AdRequest.Builder();
                    builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
                    if (z) {
                        builder.addTestDevice(AdMobAd.getDeviceId());
                    }
                    AdMobAd.this.fInterstitialAd.loadAd(builder.build());
                }
            });
        }
    }

    public void pause() {
        this.fHandler.post(new Runnable() {
            public void run() {
                if (AdMobAd.this.fAdView != null) {
                    AdMobAd.this.fAdView.pause();
                }
            }
        });
    }

    public void resume() {
        this.fHandler.post(new Runnable() {
            public void run() {
                if (AdMobAd.this.fAdView != null) {
                    AdMobAd.this.fAdView.resume();
                }
            }
        });
    }

    public void setAppId(String str) {
        this.fAppId = str;
    }

    /* access modifiers changed from: package-private */
    public double show(int i, int i2, boolean z) {
        hide();
        if (CoronaEnvironment.getCoronaActivity() == null) {
            LuaLoader.dispatchEvent(true, "No corona activity", (String) null, (String) null);
            return 0.0d;
        } else if (this.fAppId == null || this.fAppId.length() <= 0) {
            LuaLoader.dispatchEvent(true, "No app id", (String) null, (String) null);
            return 0.0d;
        } else {
            final String str = this.fAppId;
            final CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            int i3 = 0;
            int i4 = i;
            int contentWidthInPixels = coronaActivity.getContentWidthInPixels();
            int horizontalMarginInPixels = coronaActivity.getHorizontalMarginInPixels();
            if (i >= horizontalMarginInPixels && i < contentWidthInPixels + horizontalMarginInPixels) {
                i3 = contentWidthInPixels - (i - horizontalMarginInPixels);
            } else if (i < horizontalMarginInPixels) {
                i3 = contentWidthInPixels + ((horizontalMarginInPixels - i) * 2);
            }
            final AdSize adSize = new AdSize((int) (((float) i3) / coronaActivity.getResources().getDisplayMetrics().density), -2);
            final int i5 = i3;
            final int i6 = i2;
            final int i7 = i;
            final boolean z2 = z;
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    AdView unused = AdMobAd.this.fAdView = new AdView(coronaActivity);
                    AdMobAd.this.fAdView.setAdSize(adSize);
                    AdMobAd.this.fAdView.setAdUnitId(str);
                    AdMobAd.this.fAdView.setAdListener(new AdmobBannerListener());
                    if (i6 >= AdMobAd.this.getParentHeight()) {
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i5, -2);
                        layoutParams.addRule(12);
                        ViewGroup.LayoutParams unused2 = AdMobAd.this.fLayoutParams = layoutParams;
                        ViewGroup unused3 = AdMobAd.this.fViewGroup = AdMobAd.this.getRelativeParent();
                    } else {
                        ViewGroup.LayoutParams unused4 = AdMobAd.this.fLayoutParams = new AbsoluteLayout.LayoutParams(i5, -2, i7, i6);
                        ViewGroup unused5 = AdMobAd.this.fViewGroup = AdMobAd.this.getParent();
                    }
                    AdRequest.Builder builder = new AdRequest.Builder();
                    builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
                    if (z2) {
                        builder.addTestDevice(AdMobAd.getDeviceId());
                    }
                    AdMobAd.this.fAdView.loadAd(builder.build());
                }
            });
            return ((double) adSize.getHeightInPixels(CoronaEnvironment.getCoronaActivity())) / ((double) coronaActivity.getContentHeightInPixels());
        }
    }

    /* access modifiers changed from: package-private */
    public void showInterstitialAd(final boolean z) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            LuaLoader.dispatchEvent(true, "No corona activity", (String) null, (String) null);
            return;
        }
        final String str = this.fAppId;
        if (str == null || str.length() <= 0) {
            LuaLoader.dispatchEvent(true, "No app id", (String) null, (String) null);
        } else {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (AdMobAd.this.fInterstitialAd == null || !AdMobAd.this.fInterstitialAd.isLoaded()) {
                        InterstitialAd unused = AdMobAd.this.fInterstitialAd = new InterstitialAd(CoronaEnvironment.getCoronaActivity());
                        AdMobAd.this.fInterstitialAd.setAdUnitId(str);
                        AdMobAd.this.fInterstitialAd.setAdListener(new AdmobInterstitialListener(true));
                        AdRequest.Builder builder = new AdRequest.Builder();
                        builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
                        if (z) {
                            builder.addTestDevice(AdMobAd.getDeviceId());
                        }
                        AdMobAd.this.fInterstitialAd.loadAd(builder.build());
                        return;
                    }
                    AdMobAd.this.fInterstitialAd.show();
                }
            });
        }
    }
}
