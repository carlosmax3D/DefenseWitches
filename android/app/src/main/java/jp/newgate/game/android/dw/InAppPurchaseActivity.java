package jp.newgate.game.android.dw;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.google.android.gms.games.GamesActivityResultCodes;

import jp.newgate.game.android.dw.util.IabHelper;
import jp.newgate.game.android.dw.util.IabResult;
import jp.newgate.game.android.dw.util.Inventory;
import jp.newgate.game.android.dw.util.Purchase;
import jp.stargarage.g2metrics.BuildConfig;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class InAppPurchaseActivity extends Activity {
    private static final String ITEM_SKU = "android.test.purchased";
    public static boolean isStartedPurchase = true;
    private Button buttonBuyClick;
    private Context context;
    private IabHelper mHelper;
    private SharedPreferences prefs;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseListener = new IabHelper.OnIabPurchaseFinishedListener() { // from class: jp.newgate.game.android.dw.InAppPurchaseActivity.3
        @Override // jp.newgate.game.android.dw.util.IabHelper.OnIabPurchaseFinishedListener
        public void onIabPurchaseFinished(IabResult iabResult, Purchase purchase) {
            if (iabResult.isFailure()) {
                InAppPurchaseActivity.this.showWarningDialog(BuildConfig.FLAVOR, "Purchase Failed!");
            } else if (purchase.getSku().equals(InAppPurchaseActivity.ITEM_SKU)) {
                InAppPurchaseActivity.this.consumeItem();
                InAppPurchaseActivity.this.buttonBuyClick.setEnabled(false);
            }
        }
    };
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() { // from class: jp.newgate.game.android.dw.InAppPurchaseActivity.4
        @Override // jp.newgate.game.android.dw.util.IabHelper.QueryInventoryFinishedListener
        public void onQueryInventoryFinished(IabResult iabResult, Inventory inventory) {
            if (iabResult.isFailure()) {
                InAppPurchaseActivity.this.showWarningDialog(BuildConfig.FLAVOR, "Query Inventory Failed!");
            } else {
                InAppPurchaseActivity.this.mHelper.consumeAsync(inventory.getPurchase(InAppPurchaseActivity.ITEM_SKU), InAppPurchaseActivity.this.mConsumeFinishedListener);
            }
        }
    };
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() { // from class: jp.newgate.game.android.dw.InAppPurchaseActivity.5
        @Override // jp.newgate.game.android.dw.util.IabHelper.OnConsumeFinishedListener
        public void onConsumeFinished(Purchase purchase, IabResult iabResult) {
            if (iabResult.isSuccess()) {
                InAppPurchaseActivity.this.showInfoDialog(BuildConfig.FLAVOR, "Success!");
            } else {
                InAppPurchaseActivity.this.showWarningDialog(BuildConfig.FLAVOR, "Consume Failed!");
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void consumeItem() {
        this.mHelper.queryInventoryAsync(this.mReceivedInventoryListener);
    }

    private void initViews() {
        this.buttonBuyClick = (Button) findViewById(C1242R.id.button_buy_click);
        this.buttonBuyClick.setOnClickListener(new View.OnClickListener() { // from class: jp.newgate.game.android.dw.InAppPurchaseActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
//                    InAppPurchaseActivity.this.mHelper.launchPurchaseFlow(InAppPurchaseActivity.this, InAppPurchaseActivity.ITEM_SKU, GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED, InAppPurchaseActivity.this.mPurchaseListener, "mypurchasetoken");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInfoDialog(String str, String str2) {
        new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: jp.newgate.game.android.dw.InAppPurchaseActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                InAppPurchaseActivity.this.onBackPressed();
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWarningDialog(String str, String str2) {
        new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: jp.newgate.game.android.dw.InAppPurchaseActivity.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                InAppPurchaseActivity.this.onBackPressed();
            }
        }).setIcon(R.drawable.ic_dialog_alert).show();
    }

    @Override // android.app.Activity
    public void onActivityReenter(int i, Intent intent) {
        super.onActivityReenter(i, intent);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (this.mHelper.handleActivityResult(i, i2, intent)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1242R.layout.activity_in_app_purchase);
        this.mHelper = new IabHelper(this, getString(C1242R.string.license_key));
        this.mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() { // from class: jp.newgate.game.android.dw.InAppPurchaseActivity.1
            @Override // jp.newgate.game.android.dw.util.IabHelper.OnIabSetupFinishedListener
            public void onIabSetupFinished(IabResult iabResult) {
                if (iabResult.isSuccess()) {
                    Toast.makeText(InAppPurchaseActivity.this, "InAppBilling setting up is success", android.widget.Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(InAppPurchaseActivity.this, "InAppBilling setting up is failed", Toast.LENGTH_LONG).show();
                }
            }
        });
        this.context = getApplicationContext();
        this.prefs = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.mHelper != null) {
            this.mHelper.dispose();
        }
        this.mHelper = null;
    }
}
