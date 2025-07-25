package jp.newgate.game.android.dw;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.games.GamesActivityResultCodes;
import jp.newgate.game.android.dw.util.IabHelper;
import jp.newgate.game.android.dw.util.IabResult;
import jp.newgate.game.android.dw.util.Inventory;
import jp.newgate.game.android.dw.util.Purchase;
import jp.stargarage.g2metrics.BuildConfig;

public class InAppPurchaseActivity extends Activity {
    private static final String ITEM_SKU = "android.test.purchased";
    public static boolean isStartedPurchase = true;
    /* access modifiers changed from: private */
    public Button buttonBuyClick;
    private Context context;
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult iabResult) {
            if (iabResult.isSuccess()) {
                InAppPurchaseActivity.this.showInfoDialog(BuildConfig.FLAVOR, "Success!");
            } else {
                InAppPurchaseActivity.this.showWarningDialog(BuildConfig.FLAVOR, "Consume Failed!");
            }
        }
    };
    /* access modifiers changed from: private */
    public IabHelper mHelper;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult iabResult, Purchase purchase) {
            if (iabResult.isFailure()) {
                InAppPurchaseActivity.this.showWarningDialog(BuildConfig.FLAVOR, "Purchase Failed!");
            } else if (purchase.getSku().equals(InAppPurchaseActivity.ITEM_SKU)) {
                InAppPurchaseActivity.this.consumeItem();
                InAppPurchaseActivity.this.buttonBuyClick.setEnabled(false);
            }
        }
    };
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult iabResult, Inventory inventory) {
            if (iabResult.isFailure()) {
                InAppPurchaseActivity.this.showWarningDialog(BuildConfig.FLAVOR, "Query Inventory Failed!");
            } else {
                InAppPurchaseActivity.this.mHelper.consumeAsync(inventory.getPurchase(InAppPurchaseActivity.ITEM_SKU), InAppPurchaseActivity.this.mConsumeFinishedListener);
            }
        }
    };
    private SharedPreferences prefs;

    /* access modifiers changed from: private */
    public void consumeItem() {
        this.mHelper.queryInventoryAsync(this.mReceivedInventoryListener);
    }

    private void initViews() {
        this.buttonBuyClick = (Button) findViewById(R.id.button_buy_click);
        this.buttonBuyClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InAppPurchaseActivity.this.mHelper.launchPurchaseFlow(InAppPurchaseActivity.this, InAppPurchaseActivity.ITEM_SKU, GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED, InAppPurchaseActivity.this.mPurchaseListener, "mypurchasetoken");
            }
        });
    }

    /* access modifiers changed from: private */
    public void showInfoDialog(String str, String str2) {
        new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                InAppPurchaseActivity.this.onBackPressed();
            }
        }).show();
    }

    /* access modifiers changed from: private */
    public void showWarningDialog(String str, String str2) {
        new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                InAppPurchaseActivity.this.onBackPressed();
            }
        }).setIcon(17301543).show();
    }

    public void onActivityReenter(int i, Intent intent) {
        super.onActivityReenter(i, intent);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!this.mHelper.handleActivityResult(i, i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_in_app_purchase);
        this.mHelper = new IabHelper(this, getString(R.string.license_key));
        this.mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult iabResult) {
                if (!iabResult.isSuccess()) {
                    Toast.makeText(InAppPurchaseActivity.this, "InAppBilling setting up is failed", 1).show();
                } else {
                    Toast.makeText(InAppPurchaseActivity.this, "InAppBilling setting up is success", 1).show();
                }
            }
        });
        this.context = getApplicationContext();
        this.prefs = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.mHelper != null) {
            this.mHelper.dispose();
        }
        this.mHelper = null;
    }
}
