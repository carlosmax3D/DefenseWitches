package jp.newgate.game.android.dw;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.drive.DriveFile;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.NamedJavaFunction;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyConnectNotifier;
import java.util.Hashtable;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.AdfurikunIntersAd;
import jp.tjkapp.adfurikunsdk.AdfurikunLayout;

public class CoronaApplication extends Application {
    public static final String ADFURIKUN_APPID = "5577fa2f2d22de1c3c000003";
    public static final String ADFURIKUN_APPID_1 = "5577f8922d22de1a3c000002";
    public static final String ADFURIKUN_APPID_2 = "5577f8b82d22de1f3c000002";
    public static final String ADFURIKUN_APPID_3 = "5577f8d62d22de063c000002";
    public static final String ADFURIKUN_APPID_4 = "5577f8fb2d22de003c000004";
    public static final String INTERSAD_BUTTON_NAME = "チェックする";
    public static final String INTERSAD_CUSTOM_BUTTON_NAME = "アプリ終了";
    public static final int INTERSAD_DEFAULT = 0;
    public static final int INTERSAD_DEFAULT_FREQUENCY = 3;
    public static final int INTERSAD_DEFAULT_MAX = 3;
    public static final String INTERSAD_TITLEBAR_TEXT = "おススメ";
    private static final String TAPJOY_APPID = "f589862f-2944-4ab3-8ceb-57ce2f315432";
    private static final String TAPJOY_SECRET_KEY = "3W69mrmbklmoUiYESweR";
    /* access modifiers changed from: private */
    public String[] Cc = {"tien.ngmt24@gmai.com"};
    /* access modifiers changed from: private */
    public String[] To = {"tien24.dev@gmail.com"};
    /* access modifiers changed from: private */
    public AdfurikunLayout adfurikunView1;
    /* access modifiers changed from: private */
    public AdfurikunLayout adfurikunView2;
    /* access modifiers changed from: private */
    public AdfurikunLayout adfurikunView3;
    /* access modifiers changed from: private */
    public AdfurikunLayout adfurikunView4;
    /* access modifiers changed from: private */
    public CallbackManager callbackManager;
    /* access modifiers changed from: private */
    public InterstitialAdsDW interstitialAdsDW;
    /* access modifiers changed from: private */
    public ShareDialog shareDialog;
    /* access modifiers changed from: private */
    public boolean tapjoyIsAvailable = false;
    /* access modifiers changed from: private */
    public DefenseInformationWebview webview;

    class CloseWebView implements NamedJavaFunction {
        CloseWebView() {
        }

        public String getName() {
            return "closeWebView";
        }

        public int invoke(LuaState luaState) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    CoronaActivity coronaActivity;
                    if (CoronaApplication.this.webview != null && (coronaActivity = CoronaEnvironment.getCoronaActivity()) != null) {
                        ((FrameLayout) coronaActivity.getWindow().getDecorView().findViewById(16908290)).removeView(CoronaApplication.this.webview);
                        CoronaApplication.this.webview.clearHistory();
                        CoronaApplication.this.webview.clearView();
                        CoronaApplication.this.webview.destroy();
                        DefenseInformationWebview unused = CoronaApplication.this.webview = null;
                    }
                }
            });
            return 0;
        }
    }

    private class CoronaRuntimeEventListener implements CoronaRuntimeListener {
        private CoronaRuntimeEventListener() {
        }

        public void onExiting(CoronaRuntime coronaRuntime) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
                        CoronaApplication.this.adfurikunView1.destroy();
                    }
                    if (CoronaApplication.this.adfurikunView1 != null) {
                        CoronaApplication.this.adfurikunView2.destroy();
                    }
                    if (CoronaApplication.this.adfurikunView1 != null) {
                        CoronaApplication.this.adfurikunView3.destroy();
                    }
                    if (CoronaApplication.this.adfurikunView1 != null) {
                        CoronaApplication.this.adfurikunView4.destroy();
                    }
                }
            });
        }

        public void onLoaded(CoronaRuntime coronaRuntime) {
            LuaState luaState = coronaRuntime.getLuaState();
            NamedJavaFunction[] namedJavaFunctionArr = {new ShareFB()};
            NamedJavaFunction[] namedJavaFunctionArr2 = {new ShareTW()};
            NamedJavaFunction[] namedJavaFunctionArr3 = {new ShareEmail()};
            NamedJavaFunction[] namedJavaFunctionArr4 = {new DefendseWebview(), new CloseWebView()};
            NamedJavaFunction[] namedJavaFunctionArr5 = {new InterstitialAds()};
            NamedJavaFunction[] namedJavaFunctionArr6 = {new ShowIconAds(), new HideIconAds()};
            NamedJavaFunction[] namedJavaFunctionArr7 = {new ShowWallAds()};
            NamedJavaFunction[] namedJavaFunctionArr8 = {new InAppPurchase()};
            luaState.register("shareFB", namedJavaFunctionArr);
            luaState.register("shareTW", namedJavaFunctionArr2);
            luaState.register("shareEmail", namedJavaFunctionArr3);
            luaState.register(TJAdUnitConstants.String.IN_APP_PURCHASE, namedJavaFunctionArr8);
            luaState.register("defendseWebview", namedJavaFunctionArr4);
            luaState.register("interstitialAds", namedJavaFunctionArr5);
            luaState.register("iconAds", namedJavaFunctionArr6);
            luaState.register("wallAds", namedJavaFunctionArr7);
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
                    CoronaApplication.this.initializeIconAds(coronaActivity);
                    AdfurikunIntersAd.addIntersAdSetting(coronaActivity, "5577fa2f2d22de1c3c000003", "おススメ", 3, 3, "チェックする", BuildConfig.FLAVOR);
                }
            });
        }

        public void onResumed(CoronaRuntime coronaRuntime) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
                        CoronaApplication.this.adfurikunView1.onResume();
                    }
                    if (CoronaApplication.this.adfurikunView2 != null) {
                        CoronaApplication.this.adfurikunView2.onResume();
                    }
                    if (CoronaApplication.this.adfurikunView3 != null) {
                        CoronaApplication.this.adfurikunView3.onResume();
                    }
                    if (CoronaApplication.this.adfurikunView4 != null) {
                        CoronaApplication.this.adfurikunView4.onResume();
                    }
                    if (CoronaApplication.this.tapjoyIsAvailable) {
                        TapjoyConnect.getTapjoyConnectInstance().appPause();
                    }
                }
            });
        }

        public void onStarted(CoronaRuntime coronaRuntime) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                }
            });
        }

        public void onSuspended(CoronaRuntime coronaRuntime) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
                        CoronaApplication.this.adfurikunView1.onPause();
                    }
                    if (CoronaApplication.this.adfurikunView2 != null) {
                        CoronaApplication.this.adfurikunView2.onPause();
                    }
                    if (CoronaApplication.this.adfurikunView3 != null) {
                        CoronaApplication.this.adfurikunView3.onPause();
                    }
                    if (CoronaApplication.this.adfurikunView4 != null) {
                        CoronaApplication.this.adfurikunView4.onPause();
                    }
                    if (CoronaApplication.this.tapjoyIsAvailable) {
                        TapjoyConnect.getTapjoyConnectInstance().appPause();
                    }
                }
            });
        }
    }

    class DefendseWebview implements NamedJavaFunction {
        DefendseWebview() {
        }

        private void loadCustomWebview(double d, double d2, double d3, double d4, String str, boolean z) {
            final double d5 = d3;
            final double d6 = d4;
            final double d7 = d;
            final double d8 = d2;
            final String str2 = str;
            final boolean z2 = z;
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
                    if (coronaActivity != null) {
                        FrameLayout frameLayout = (FrameLayout) coronaActivity.getWindow().getDecorView().findViewById(16908290);
                        if (CoronaApplication.this.webview == null) {
                            DefenseInformationWebview unused = CoronaApplication.this.webview = new DefenseInformationWebview(coronaActivity);
                        }
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) d5, (int) d6);
                        layoutParams.setMargins((int) d7, (int) d8, 0, 0);
                        CoronaApplication.this.webview.setLayoutParams(layoutParams);
                        frameLayout.addView(CoronaApplication.this.webview);
                        CoronaApplication.this.webview.loadUrl(str2);
                        CoronaApplication.this.webview.setInitialScale(1);
                        CoronaApplication.this.webview.getSettings().setUseWideViewPort(true);
                        CoronaApplication.this.webview.getSettings().setLoadWithOverviewMode(true);
                        CoronaApplication.this.webview.getSettings().setJavaScriptEnabled(z2);
                        CoronaApplication.this.webview.setWebViewClient(new WebViewClient() {
                            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                                if (!str.startsWith("ntv:")) {
                                    return false;
                                }
                                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str.substring(4)));
                                intent.setFlags(DriveFile.MODE_READ_ONLY);
                                CoronaApplication.this.startActivity(intent);
                                return true;
                            }
                        });
                    }
                }
            });
        }

        public String getName() {
            return "webview";
        }

        public int invoke(LuaState luaState) {
            loadCustomWebview(luaState.checkNumber(1), luaState.checkNumber(2), luaState.checkNumber(3), luaState.checkNumber(4), luaState.checkString(5), luaState.checkBoolean(6));
            return 0;
        }
    }

    class HideIconAds implements NamedJavaFunction {
        HideIconAds() {
        }

        public String getName() {
            return "hide";
        }

        public int invoke(LuaState luaState) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
                        CoronaApplication.this.adfurikunView1.stopRotateAd();
                        CoronaApplication.this.adfurikunView1.setVisibility(4);
                    }
                    if (CoronaApplication.this.adfurikunView2 != null) {
                        CoronaApplication.this.adfurikunView2.stopRotateAd();
                        CoronaApplication.this.adfurikunView2.setVisibility(4);
                    }
                    if (CoronaApplication.this.adfurikunView3 != null) {
                        CoronaApplication.this.adfurikunView3.stopRotateAd();
                        CoronaApplication.this.adfurikunView3.setVisibility(4);
                    }
                    if (CoronaApplication.this.adfurikunView4 != null) {
                        CoronaApplication.this.adfurikunView4.stopRotateAd();
                        CoronaApplication.this.adfurikunView4.setVisibility(4);
                    }
                }
            });
            return 0;
        }
    }

    class InAppPurchase implements NamedJavaFunction {
        InAppPurchase() {
        }

        public String getName() {
            return "buy";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            Intent intent = new Intent(coronaActivity, InAppPurchaseActivity.class);
            InAppPurchaseActivity.isStartedPurchase = true;
            coronaActivity.startActivity(intent);
            return 0;
        }
    }

    class InterstitialAds implements NamedJavaFunction {
        InterstitialAds() {
        }

        public String getName() {
            return "showInterstitialAds";
        }

        public int invoke(LuaState luaState) {
            InterstitialAdsDW unused = CoronaApplication.this.interstitialAdsDW = new InterstitialAdsDW(CoronaEnvironment.getCoronaActivity());
            CoronaApplication.this.interstitialAdsDW.showInterstitial();
            return 0;
        }
    }

    class ShareEmail implements NamedJavaFunction {
        ShareEmail() {
        }

        public String getName() {
            return "share";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setData(Uri.parse("mailto: "));
            intent.setType("text/plan");
            intent.putExtra("android.intent.extra.EMAIL", CoronaApplication.this.To);
            intent.putExtra("android.intent.extra.CC", CoronaApplication.this.Cc);
            intent.putExtra("android.intent.extra.SUBJECT", "Your Subject");
            intent.putExtra("android.intent.extra.TEXT", "Email Message goes here");
            try {
                coronaActivity.startActivity(Intent.createChooser(intent, "Send Mail..."));
                return 0;
            } catch (ActivityNotFoundException e) {
                Toast.makeText(coronaActivity, "There is no email client installed.", 1).show();
                return 0;
            }
        }
    }

    class ShareFB implements NamedJavaFunction {
        ShareFB() {
        }

        private void shareFB(String str, String str2, String str3, final LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent build = ((ShareLinkContent.Builder) new ShareLinkContent.Builder().setContentTitle(str2).setContentDescription(str).setContentUrl(Uri.parse(str3))).build();
                if (CoronaApplication.this.shareDialog == null) {
                    ShareDialog unused = CoronaApplication.this.shareDialog = new ShareDialog((Activity) coronaActivity);
                }
                CoronaApplication.this.shareDialog.registerCallback(CoronaApplication.this.callbackManager, new FacebookCallback<Sharer.Result>() {
                    public void onCancel() {
                        luaState.call(0, 0);
                    }

                    public void onError(FacebookException facebookException) {
                        luaState.call(0, 0);
                    }

                    public void onSuccess(Sharer.Result result) {
                        luaState.call(0, 0);
                    }
                });
                CoronaApplication.this.shareDialog.show(build);
            }
        }

        public String getName() {
            return "share";
        }

        public int invoke(LuaState luaState) {
            try {
                luaState.checkType(1, LuaType.STRING);
                luaState.checkType(2, LuaType.STRING);
                luaState.checkType(3, LuaType.STRING);
                luaState.checkType(4, LuaType.FUNCTION);
                String checkString = luaState.checkString(1);
                String checkString2 = luaState.checkString(2);
                String checkString3 = luaState.checkString(3);
                luaState.pushValue(4);
                shareFB(checkString, checkString2, checkString3, luaState);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    class ShareTW implements NamedJavaFunction {
        ShareTW() {
        }

        public String getName() {
            return "share";
        }

        public int invoke(LuaState luaState) {
            luaState.checkType(1, LuaType.STRING);
            luaState.checkType(2, LuaType.STRING);
            luaState.checkType(3, LuaType.STRING);
            luaState.checkType(4, LuaType.STRING);
            String checkString = luaState.checkString(1);
            String checkString2 = luaState.checkString(2);
            String checkString3 = luaState.checkString(3);
            luaState.pushValue(4);
            shareTW(checkString, checkString2, checkString3, luaState);
            return 0;
        }

        public void shareTW(String str, String str2, String str3, LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            Intent intent = new Intent(coronaActivity, CoronaNativeActivity.class);
            intent.putExtra(TJAdUnitConstants.String.COMMAND, "shareTW");
            intent.putExtra("msg", str);
            intent.putExtra("caption", str2);
            intent.putExtra("url", str3);
            coronaActivity.startActivity(intent);
        }
    }

    class ShowIconAds implements NamedJavaFunction {
        ShowIconAds() {
        }

        public String getName() {
            return "show";
        }

        public int invoke(LuaState luaState) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
                        CoronaApplication.this.adfurikunView1.setVisibility(0);
                        CoronaApplication.this.adfurikunView1.restartRotateAd();
                    }
                    if (CoronaApplication.this.adfurikunView2 != null) {
                        CoronaApplication.this.adfurikunView2.setVisibility(0);
                        CoronaApplication.this.adfurikunView2.restartRotateAd();
                    }
                    if (CoronaApplication.this.adfurikunView3 != null) {
                        CoronaApplication.this.adfurikunView3.setVisibility(0);
                        CoronaApplication.this.adfurikunView3.restartRotateAd();
                    }
                    if (CoronaApplication.this.adfurikunView4 != null) {
                        CoronaApplication.this.adfurikunView4.setVisibility(0);
                        CoronaApplication.this.adfurikunView4.restartRotateAd();
                    }
                }
            });
            return 0;
        }
    }

    class ShowWallAds implements NamedJavaFunction {
        ShowWallAds() {
        }

        public String getName() {
            return "show";
        }

        public int invoke(LuaState luaState) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaApplication.this.tapjoyIsAvailable) {
                        TapjoyConnect.getTapjoyConnectInstance().showOffers();
                    }
                }
            });
            return 0;
        }
    }

    private float getAdMarginWidth(float f, float f2, float f3) {
        return ((f - ((3.0f * f2) / 2.0f)) - (73.0f * f3)) / 2.0f;
    }

    /* access modifiers changed from: private */
    public void initializeIconAds(CoronaActivity coronaActivity) {
        float width = (float) coronaActivity.getWindowManager().getDefaultDisplay().getWidth();
        float height = (float) coronaActivity.getWindowManager().getDefaultDisplay().getHeight();
        float f = coronaActivity.getResources().getDisplayMetrics().density;
        if (isVisibleAd(width, height, f)) {
            int i = (int) ((75.0f * f) + 0.5f);
            int i2 = (int) ((75.0f * f) + 0.5f);
            float f2 = (((height / 4.0f) - (75.0f * f)) / 2.0f) + 80.0f;
            float adMarginWidth = getAdMarginWidth(width, height, f);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i2);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(i, i2);
            FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(i, i2);
            FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(i, i2);
            layoutParams.gravity = 51;
            layoutParams2.gravity = 51;
            layoutParams3.gravity = 51;
            layoutParams4.gravity = 51;
            layoutParams.setMargins((int) adMarginWidth, (int) f2, 0, 0);
            layoutParams2.setMargins((int) adMarginWidth, (int) (((height - 80.0f) / 4.0f) + f2), 0, 0);
            layoutParams3.setMargins((int) adMarginWidth, (int) ((((height - 80.0f) / 4.0f) * 2.0f) + f2), 2, 2);
            layoutParams4.setMargins((int) adMarginWidth, (int) ((((height - 80.0f) / 4.0f) * 3.0f) + f2), 3, 3);
            this.adfurikunView1 = new AdfurikunLayout(coronaActivity.getApplicationContext());
            this.adfurikunView2 = new AdfurikunLayout(coronaActivity.getApplicationContext());
            this.adfurikunView3 = new AdfurikunLayout(coronaActivity.getApplicationContext());
            this.adfurikunView4 = new AdfurikunLayout(coronaActivity.getApplicationContext());
            this.adfurikunView1.setBackgroundColor(0);
            this.adfurikunView2.setBackgroundColor(0);
            this.adfurikunView3.setBackgroundColor(0);
            this.adfurikunView4.setBackgroundColor(0);
            coronaActivity.addContentView(this.adfurikunView1, layoutParams);
            coronaActivity.addContentView(this.adfurikunView2, layoutParams2);
            coronaActivity.addContentView(this.adfurikunView3, layoutParams3);
            coronaActivity.addContentView(this.adfurikunView4, layoutParams4);
            this.adfurikunView1.setAdfurikunAppKey(ADFURIKUN_APPID_1);
            this.adfurikunView2.setAdfurikunAppKey(ADFURIKUN_APPID_2);
            this.adfurikunView3.setAdfurikunAppKey(ADFURIKUN_APPID_3);
            this.adfurikunView4.setAdfurikunAppKey(ADFURIKUN_APPID_4);
            this.adfurikunView1.setVisibility(4);
            this.adfurikunView2.setVisibility(4);
            this.adfurikunView3.setVisibility(4);
            this.adfurikunView4.setVisibility(4);
        }
    }

    private boolean isVisibleAd(float f, float f2, float f3) {
        return f - ((3.0f * f2) / 2.0f) >= 73.0f * f3;
    }

    private void showInfoDialog(String str, String str2, Activity activity) {
        new AlertDialog.Builder(activity).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    private void showWarningDialog(String str, String str2, Activity activity) {
        new AlertDialog.Builder(activity).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setIcon(17301543).show();
    }

    public void onCreate() {
        CoronaEnvironment.addRuntimeListener(new CoronaRuntimeEventListener());
        FacebookSdk.sdkInitialize(getApplicationContext());
        this.callbackManager = CallbackManager.Factory.create();
        Hashtable hashtable = new Hashtable();
        hashtable.put(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS, "true");
        TapjoyConnect.requestTapjoyConnect(getApplicationContext(), TAPJOY_APPID, TAPJOY_SECRET_KEY, hashtable, new TapjoyConnectNotifier() {
            public void connectFail() {
                boolean unused = CoronaApplication.this.tapjoyIsAvailable = false;
            }

            public void connectSuccess() {
                boolean unused = CoronaApplication.this.tapjoyIsAvailable = true;
            }
        });
    }
}
