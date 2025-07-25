package com.ansca.corona.purchasing;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class StoreActivity extends Activity {
    public static final String EXTRA_FULL_SCREEN = "full_screen";
    public static final String EXTRA_NOOK_APP_EAN = "nook_app_ean";
    private boolean fHasShownStore;

    public void finish() {
        super.finish();
        if ((getIntent().getFlags() & 65536) != 0) {
            overridePendingTransition(0, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent().getStringExtra(EXTRA_NOOK_APP_EAN) == null) {
            finish();
            return;
        }
        int i = 1;
        if (Build.VERSION.SDK_INT >= 9) {
            i = 7;
        }
        setRequestedOrientation(i);
        if (getIntent().getBooleanExtra(EXTRA_FULL_SCREEN, false)) {
            getWindow().addFlags(1024);
            getWindow().clearFlags(2048);
        } else {
            getWindow().addFlags(2048);
            getWindow().clearFlags(1024);
        }
        this.fHasShownStore = false;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!this.fHasShownStore) {
            this.fHasShownStore = true;
            Intent intent = new Intent();
            intent.setAction("com.bn.sdk.shop.details");
            intent.putExtra("product_details_ean", getIntent().getStringExtra(EXTRA_NOOK_APP_EAN));
            startActivityForResult(intent, 1);
        }
    }
}
