package jp.newgate.game.android.dw;

import android.widget.Toast;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import java.util.Random;
//import jp.co.voyagegroup.android.fluct.jar.FluctInterstitial;
import jp.stargarage.g2metrics.BuildConfig;
//import jp.tjkapp.adfurikunsdk.AdfurikunIntersAd;
//import jp.tjkapp.adfurikunsdk.OnAdfurikunIntersAdFinishListener;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class InterstitialAdsDW {
    public static final String ADFURIKUN_APPID = "5577fa2f2d22de1c3c000003";
    public static final String INTERSAD_BUTTON_NAME = "チェックする";
    public static final String INTERSAD_CUSTOM_BUTTON_NAME = "アプリ終了";
    public static final int INTERSAD_DEFAULT = 0;
    public static final int INTERSAD_DEFAULT_FREQUENCY = 3;
    public static final int INTERSAD_DEFAULT_MAX = 3;
    public static final String INTERSAD_TITLEBAR_TEXT = "おススメ";
    private CoronaActivity activity;

    public InterstitialAdsDW(CoronaActivity coronaActivity) {
        this.activity = coronaActivity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initInterstitailAdfrikun() {
/*        AdfurikunIntersAd.showIntersAd(this.activity, 0, new OnAdfurikunIntersAdFinishListener() { // from class: jp.newgate.game.android.dw.InterstitialAdsDW.3
            @Override // jp.tjkapp.adfurikunsdk.OnAdfurikunIntersAdFinishListener
            public void onAdfurikunIntersAdClose(int i) {
            }

            @Override // jp.tjkapp.adfurikunsdk.OnAdfurikunIntersAdFinishListener
            public void onAdfurikunIntersAdCustomClose(int i) {
            }

            @Override // jp.tjkapp.adfurikunsdk.OnAdfurikunIntersAdFinishListener
            public void onAdfurikunIntersAdError(int i, int i2) {
            }

            @Override // jp.tjkapp.adfurikunsdk.OnAdfurikunIntersAdFinishListener
            public void onAdfurikunIntersAdMaxEnd(int i) {
            }

            @Override // jp.tjkapp.adfurikunsdk.OnAdfurikunIntersAdFinishListener
            public void onAdfurikunIntersAdSkip(int i) {
                Toast.makeText(InterstitialAdsDW.this.activity, InterstitialAdsDW.this.activity.getString(C1242R.string.inters_ad_skip), 0).show();
            }
        });*/
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initZuckAdd() {
/*        FluctInterstitial fluctInterstitial = new FluctInterstitial(this.activity);
        fluctInterstitial.setFluctInterstitialCallback(new FluctInterstitial.FluctInterstitialCallback() { // from class: jp.newgate.game.android.dw.InterstitialAdsDW.2
            @Override // jp.co.voyagegroup.android.fluct.jar.FluctInterstitial.FluctInterstitialCallback
            public void onReceiveAdInfo(int i) {
                switch (i) {
                }
            }
        });
        fluctInterstitial.showIntersitialAd();*/
    }

    public void loadInterstitial() {
//        AdfurikunIntersAd.addIntersAdSetting(this.activity, "5577fa2f2d22de1c3c000003", "おススメ", 3, 3, "チェックする", BuildConfig.FLAVOR);
    }

    public void showInterstitial() {
        CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.InterstitialAdsDW.1
            @Override // java.lang.Runnable
            public void run() {
                if (new Random().nextInt(3) < 1) {
                    InterstitialAdsDW.this.initZuckAdd();
                } else {
                    InterstitialAdsDW.this.initInterstitailAdfrikun();
                }
            }
        });
    }
}
