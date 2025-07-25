package jp.newgate.game.android.dw;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
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
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.drive.DriveFile;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.NamedJavaFunction;
import com.tapjoy.TJAdUnitConstants;
//import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;
//import com.tapjoy.TapjoyConnectNotifier;
import java.util.Hashtable;
import jp.stargarage.g2metrics.BuildConfig;
//import jp.tjkapp.adfurikunsdk.AdfurikunIntersAd;
//import jp.tjkapp.adfurikunsdk.AdfurikunLayout;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
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
    private Object adfurikunView1 = null;//AdfurikunLayout
    private Object adfurikunView2 = null;//AdfurikunLayout
    private Object adfurikunView3 = null;//AdfurikunLayout
    private Object adfurikunView4 = null;//AdfurikunLayout
    private com.facebook.CallbackManager callbackManager;
    private InterstitialAdsDW interstitialAdsDW;
    private ShareDialog shareDialog;
    private DefenseInformationWebview webview;

    /* renamed from: To */
    private String[] f3198To = {"tien24.dev@gmail.com"};

    /* renamed from: Cc */
    private String[] f3197Cc = {"tien.ngmt24@gmai.com"};
    private boolean tapjoyIsAvailable = false;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class CloseWebView implements NamedJavaFunction {
        CloseWebView() {
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "closeWebView";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.CloseWebView.1
                @Override // java.lang.Runnable
                public void run() {
                    CoronaActivity coronaActivity;
                    if (CoronaApplication.this.webview == null || (coronaActivity = CoronaEnvironment.getCoronaActivity()) == null) {
                        return;
                    }
                    ((FrameLayout) coronaActivity.getWindow().getDecorView().findViewById(R.id.content)).removeView(CoronaApplication.this.webview);
                    CoronaApplication.this.webview.clearHistory();
                    CoronaApplication.this.webview.clearView();
                    CoronaApplication.this.webview.destroy();
                    CoronaApplication.this.webview = null;
                }
            });
            return 0;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    private class CoronaRuntimeEventListener implements CoronaRuntimeListener {
        private CoronaRuntimeEventListener() {
        }

        @Override // com.ansca.corona.CoronaRuntimeListener
        public void onExiting(CoronaRuntime coronaRuntime) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.CoronaRuntimeEventListener.5
                @Override // java.lang.Runnable
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
//                        CoronaApplication.this.adfurikunView1.destroy();
                    }
                    if (CoronaApplication.this.adfurikunView1 != null) {
//                        CoronaApplication.this.adfurikunView2.destroy();
                    }
                    if (CoronaApplication.this.adfurikunView1 != null) {
//                        CoronaApplication.this.adfurikunView3.destroy();
                    }
                    if (CoronaApplication.this.adfurikunView1 != null) {
//                        CoronaApplication.this.adfurikunView4.destroy();
                    }
                }
            });
        }

        @Override // com.ansca.corona.CoronaRuntimeListener
        public void onLoaded(CoronaRuntime coronaRuntime) {
            LuaState luaState = coronaRuntime.getLuaState();
            NamedJavaFunction[] namedJavaFunctionArr = {CoronaApplication.this.new ShareFB()};
            NamedJavaFunction[] namedJavaFunctionArr2 = {CoronaApplication.this.new ShareTW()};
            NamedJavaFunction[] namedJavaFunctionArr3 = {CoronaApplication.this.new ShareEmail()};
            NamedJavaFunction[] namedJavaFunctionArr4 = {CoronaApplication.this.new DefendseWebview(), CoronaApplication.this.new CloseWebView()};
            NamedJavaFunction[] namedJavaFunctionArr5 = {CoronaApplication.this.new InterstitialAds()};
            NamedJavaFunction[] namedJavaFunctionArr6 = {CoronaApplication.this.new ShowIconAds(), CoronaApplication.this.new HideIconAds()};
            NamedJavaFunction[] namedJavaFunctionArr7 = {CoronaApplication.this.new ShowWallAds()};
            NamedJavaFunction[] namedJavaFunctionArr8 = {CoronaApplication.this.new InAppPurchase()};
            luaState.register("shareFB", namedJavaFunctionArr);
            luaState.register("shareTW", namedJavaFunctionArr2);
            luaState.register("shareEmail", namedJavaFunctionArr3);
            luaState.register("inAppPurchase", namedJavaFunctionArr8);//TJAdUnitConstants.String.IN_APP_PURCHASE.toString()
            luaState.register("defendseWebview", namedJavaFunctionArr4);
            luaState.register("interstitialAds", namedJavaFunctionArr5);
            luaState.register("iconAds", namedJavaFunctionArr6);
            luaState.register("wallAds", namedJavaFunctionArr7);
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.CoronaRuntimeEventListener.1
                @Override // java.lang.Runnable
                public void run() {
                    CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
                    CoronaApplication.this.initializeIconAds(coronaActivity);
//                    AdfurikunIntersAd.addIntersAdSetting(coronaActivity, "5577fa2f2d22de1c3c000003", "おススメ", 3, 3, "チェックする", BuildConfig.FLAVOR);
                }
            });
        }

        @Override // com.ansca.corona.CoronaRuntimeListener
        public void onResumed(CoronaRuntime coronaRuntime) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.CoronaRuntimeEventListener.4
                @Override // java.lang.Runnable
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
//                        CoronaApplication.this.adfurikunView1.onResume();
                    }
                    if (CoronaApplication.this.adfurikunView2 != null) {
//                        CoronaApplication.this.adfurikunView2.onResume();
                    }
                    if (CoronaApplication.this.adfurikunView3 != null) {
//                        CoronaApplication.this.adfurikunView3.onResume();
                    }
                    if (CoronaApplication.this.adfurikunView4 != null) {
//                        CoronaApplication.this.adfurikunView4.onResume();
                    }
                    if (CoronaApplication.this.tapjoyIsAvailable) {
//                        TapjoyConnect.getTapjoyConnectInstance().appPause();
                    }
                }
            });
        }

        @Override // com.ansca.corona.CoronaRuntimeListener
        public void onStarted(CoronaRuntime coronaRuntime) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.CoronaRuntimeEventListener.2
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        }

        @Override // com.ansca.corona.CoronaRuntimeListener
        public void onSuspended(CoronaRuntime coronaRuntime) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.CoronaRuntimeEventListener.3
                @Override // java.lang.Runnable
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
//                        CoronaApplication.this.adfurikunView1.onPause();
                    }
                    if (CoronaApplication.this.adfurikunView2 != null) {
//                        CoronaApplication.this.adfurikunView2.onPause();
                    }
                    if (CoronaApplication.this.adfurikunView3 != null) {
//                        CoronaApplication.this.adfurikunView3.onPause();
                    }
                    if (CoronaApplication.this.adfurikunView4 != null) {
//                        CoronaApplication.this.adfurikunView4.onPause();
                    }
                    if (CoronaApplication.this.tapjoyIsAvailable) {
//                        TapjoyConnect.getTapjoyConnectInstance().appPause();
                    }
                }
            });
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class DefendseWebview implements NamedJavaFunction {
        DefendseWebview() {
        }

        private void loadCustomWebview(final double d, final double d2, final double d3, final double d4, final String str, final boolean z) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.DefendseWebview.1
                @Override // java.lang.Runnable
                public void run() {
                    CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
                    if (coronaActivity == null) {
                        return;
                    }
                    FrameLayout frameLayout = (FrameLayout) coronaActivity.getWindow().getDecorView().findViewById(R.id.content);
                    if (CoronaApplication.this.webview == null) {
                        CoronaApplication.this.webview = new DefenseInformationWebview(coronaActivity);
                    }
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) d3, (int) d4);
                    layoutParams.setMargins((int) d, (int) d2, 0, 0);
                    CoronaApplication.this.webview.setLayoutParams(layoutParams);
                    frameLayout.addView(CoronaApplication.this.webview);
                    CoronaApplication.this.webview.loadUrl(str);
                    CoronaApplication.this.webview.setInitialScale(1);
                    CoronaApplication.this.webview.getSettings().setUseWideViewPort(true);
                    CoronaApplication.this.webview.getSettings().setLoadWithOverviewMode(true);
                    CoronaApplication.this.webview.getSettings().setJavaScriptEnabled(z);
                    CoronaApplication.this.webview.setWebViewClient(new WebViewClient() { // from class: jp.newgate.game.android.dw.CoronaApplication.DefendseWebview.1.1
                        @Override // android.webkit.WebViewClient
                        public boolean shouldOverrideUrlLoading(WebView webView, String str2) {
                            if (!str2.startsWith("ntv:")) {
                                return false;
                            }
                            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str2.substring(4)));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            CoronaApplication.this.startActivity(intent);
                            return true;
                        }
                    });
                }
            });
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "webview";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            loadCustomWebview(luaState.checkNumber(1), luaState.checkNumber(2), luaState.checkNumber(3), luaState.checkNumber(4), luaState.checkString(5), luaState.checkBoolean(6));
            return 0;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class HideIconAds implements NamedJavaFunction {
        HideIconAds() {
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "hide";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.HideIconAds.1
                @Override // java.lang.Runnable
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
//                        CoronaApplication.this.adfurikunView1.stopRotateAd();
//                        CoronaApplication.this.adfurikunView1.setVisibility(View.INVISIBLE);
                    }
                    if (CoronaApplication.this.adfurikunView2 != null) {
//                        CoronaApplication.this.adfurikunView2.stopRotateAd();
//                        CoronaApplication.this.adfurikunView2.setVisibility(View.INVISIBLE);
                    }
                    if (CoronaApplication.this.adfurikunView3 != null) {
//                        CoronaApplication.this.adfurikunView3.stopRotateAd();
//                        CoronaApplication.this.adfurikunView3.setVisibility(View.INVISIBLE);
                    }
                    if (CoronaApplication.this.adfurikunView4 != null) {
//                        CoronaApplication.this.adfurikunView4.stopRotateAd();
//                        CoronaApplication.this.adfurikunView4.setVisibility(View.INVISIBLE);
                    }
                }
            });
            return 0;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class InAppPurchase implements NamedJavaFunction {
        InAppPurchase() {
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "buy";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            Intent intent = new Intent(coronaActivity, (Class<?>) InAppPurchaseActivity.class);
            InAppPurchaseActivity.isStartedPurchase = true;
            coronaActivity.startActivity(intent);
            return 0;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class InterstitialAds implements NamedJavaFunction {
        InterstitialAds() {
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "showInterstitialAds";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            CoronaApplication.this.interstitialAdsDW = new InterstitialAdsDW(coronaActivity);
            CoronaApplication.this.interstitialAdsDW.showInterstitial();
            return 0;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class ShareEmail implements NamedJavaFunction {
        ShareEmail() {
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "share";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setData(Uri.parse("mailto: "));
            intent.setType("text/plan");
            intent.putExtra("android.intent.extra.EMAIL", CoronaApplication.this.f3198To);
            intent.putExtra("android.intent.extra.CC", CoronaApplication.this.f3197Cc);
            intent.putExtra("android.intent.extra.SUBJECT", "Your Subject");
            intent.putExtra("android.intent.extra.TEXT", "Email Message goes here");
            try {
                coronaActivity.startActivity(Intent.createChooser(intent, "Send Mail..."));
                return 0;
            } catch (ActivityNotFoundException e) {
                Toast.makeText(coronaActivity, "There is no email client installed.", Toast.LENGTH_LONG).show();
                return 0;
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class ShareFB implements NamedJavaFunction {
        ShareFB() {
        }

        private void shareFB(String str, String str2, String str3, final LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            if (ShareDialog.canShow((Class<? extends ShareContent<?, ?>>) ShareLinkContent.class)) {
                ShareLinkContent shareLinkContentBuild = new ShareLinkContent.Builder().setContentUrl(Uri.parse(str3)).build();
                //.setContentTitle(str2).setContentDescription(str);
                if (CoronaApplication.this.shareDialog == null) {
                    CoronaApplication.this.shareDialog = new ShareDialog(coronaActivity);
                }
                CoronaApplication.this.shareDialog.registerCallback(CoronaApplication.this.callbackManager, new FacebookCallback<Sharer.Result>() { // from class: jp.newgate.game.android.dw.CoronaApplication.ShareFB.1
                    @Override // com.facebook.FacebookCallback
                    public void onCancel() {
                        luaState.call(0, 0);
                    }

                    @Override // com.facebook.FacebookCallback
                    public void onError(FacebookException facebookException) {
                        luaState.call(0, 0);
                    }

                    @Override // com.facebook.FacebookCallback
                    public void onSuccess(Sharer.Result result) {
                        luaState.call(0, 0);
                    }
                });
                CoronaApplication.this.shareDialog.show(shareLinkContentBuild);
            }
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "share";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            try {
                luaState.checkType(1, LuaType.STRING);
                luaState.checkType(2, LuaType.STRING);
                luaState.checkType(3, LuaType.STRING);
                luaState.checkType(4, LuaType.FUNCTION);
                String strCheckString = luaState.checkString(1);
                String strCheckString2 = luaState.checkString(2);
                String strCheckString3 = luaState.checkString(3);
                luaState.pushValue(4);
                shareFB(strCheckString, strCheckString2, strCheckString3, luaState);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class ShareTW implements NamedJavaFunction {
        ShareTW() {
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "share";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            luaState.checkType(1, LuaType.STRING);
            luaState.checkType(2, LuaType.STRING);
            luaState.checkType(3, LuaType.STRING);
            luaState.checkType(4, LuaType.STRING);
            String strCheckString = luaState.checkString(1);
            String strCheckString2 = luaState.checkString(2);
            String strCheckString3 = luaState.checkString(3);
            luaState.pushValue(4);
            shareTW(strCheckString, strCheckString2, strCheckString3, luaState);
            return 0;
        }

        public void shareTW(String str, String str2, String str3, LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            Intent intent = new Intent(coronaActivity, (Class<?>) CoronaNativeActivity.class);
            intent.putExtra("command", "shareTW"); //TJAdUnitConstants.String.COMMAND.toString()
            intent.putExtra("msg", str);
            intent.putExtra("caption", str2);
            intent.putExtra("url", str3);
            coronaActivity.startActivity(intent);
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class ShowIconAds implements NamedJavaFunction {
        ShowIconAds() {
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "show";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.ShowIconAds.1
                @Override // java.lang.Runnable
                public void run() {
                    if (CoronaApplication.this.adfurikunView1 != null) {
//                        CoronaApplication.this.adfurikunView1.setVisibility(android.view.View.VISIBLE);
//                        CoronaApplication.this.adfurikunView1.restartRotateAd();
                    }
                    if (CoronaApplication.this.adfurikunView2 != null) {
//                        CoronaApplication.this.adfurikunView2.setVisibility(View.VISIBLE);
//                        CoronaApplication.this.adfurikunView2.restartRotateAd();
                    }
                    if (CoronaApplication.this.adfurikunView3 != null) {
//                        CoronaApplication.this.adfurikunView3.setVisibility(View.VISIBLE);
//                        CoronaApplication.this.adfurikunView3.restartRotateAd();
                    }
                    if (CoronaApplication.this.adfurikunView4 != null) {
//                        CoronaApplication.this.adfurikunView4.setVisibility(View.VISIBLE);
//                        CoronaApplication.this.adfurikunView4.restartRotateAd();
                    }
                }
            });
            return 0;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class ShowWallAds implements NamedJavaFunction {
        ShowWallAds() {
        }

        @Override // com.naef.jnlua.NamedJavaFunction
        public String getName() {
            return "show";
        }

        @Override // com.naef.jnlua.JavaFunction
        public int invoke(LuaState luaState) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.CoronaApplication.ShowWallAds.1
                @Override // java.lang.Runnable
                public void run() {
                    if (CoronaApplication.this.tapjoyIsAvailable) {
//                        TapjoyConnect.getTapjoyConnectInstance().showOffers();
                    }
                }
            });
            return 0;
        }
    }

    private float getAdMarginWidth(float f, float f2, float f3) {
        return ((f - ((3.0f * f2) / 2.0f)) - (73.0f * f3)) / 2.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initializeIconAds(CoronaActivity coronaActivity) {
        float width = coronaActivity.getWindowManager().getDefaultDisplay().getWidth();
        float height = coronaActivity.getWindowManager().getDefaultDisplay().getHeight();
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
/*            this.adfurikunView1 = new AdfurikunLayout(coronaActivity.getApplicationContext());
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
            this.adfurikunView1.setVisibility(View.INVISIBLE);
            this.adfurikunView2.setVisibility(View.INVISIBLE);
            this.adfurikunView3.setVisibility(View.INVISIBLE);
            this.adfurikunView4.setVisibility(View.INVISIBLE);*/
        }
    }

    private boolean isVisibleAd(float f, float f2, float f3) {
        return f - ((3.0f * f2) / 2.0f) >= 73.0f * f3;
    }

    private void showInfoDialog(String str, String str2, Activity activity) {
        new AlertDialog.Builder(activity).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: jp.newgate.game.android.dw.CoronaApplication.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    private void showWarningDialog(String str, String str2, Activity activity) {
        new AlertDialog.Builder(activity).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: jp.newgate.game.android.dw.CoronaApplication.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setIcon(R.drawable.ic_dialog_alert).show();
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        CoronaEnvironment.addRuntimeListener(new CoronaRuntimeEventListener());
//        FacebookSdk.sdkInitialize(getApplicationContext());
        this.callbackManager = CallbackManager.Factory.create();
        Hashtable hashtable = new Hashtable();
        hashtable.put(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS, "true");
/*        TapjoyConnect.requestTapjoyConnect(getApplicationContext(), TAPJOY_APPID, TAPJOY_SECRET_KEY, hashtable, new TapjoyConnectNotifier() { // from class: jp.newgate.game.android.dw.CoronaApplication.1
            @Override // com.tapjoy.TapjoyConnectNotifier
            public void connectFail() {
                CoronaApplication.this.tapjoyIsAvailable = false;
            }

            @Override // com.tapjoy.TapjoyConnectNotifier
            public void connectSuccess() {
                CoronaApplication.this.tapjoyIsAvailable = true;
            }
        });*/
    }
}
