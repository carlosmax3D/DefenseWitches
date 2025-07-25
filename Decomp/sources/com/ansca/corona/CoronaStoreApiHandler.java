package com.ansca.corona;

import com.ansca.corona.listeners.CoronaStoreApiListener;
import com.ansca.corona.purchasing.StoreProxy;

class CoronaStoreApiHandler implements CoronaStoreApiListener {
    /* access modifiers changed from: private */
    public CoronaActivity fActivity;

    public CoronaStoreApiHandler(CoronaActivity coronaActivity) {
        this.fActivity = coronaActivity;
    }

    public void storeFinishTransaction(String str) {
        if (this.fActivity != null) {
            final String str2 = str;
            this.fActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaStoreApiHandler.this.fActivity != null) {
                        CoronaStoreApiHandler.this.fActivity.getStore().confirmTransaction(str2);
                    }
                }
            });
        }
    }

    public void storeInit(String str) {
        if (this.fActivity != null) {
            String str2 = str;
            this.fActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaStoreApiHandler.this.fActivity != null) {
                        StoreProxy store = CoronaStoreApiHandler.this.fActivity.getStore();
                        CoronaStoreApiHandler.this.fActivity.getRuntime().getController().showStoreDeprecatedAlert();
                        store.enable();
                    }
                }
            });
        }
    }

    public void storePurchase(String str) {
        if (this.fActivity != null) {
            final String str2 = str;
            this.fActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaStoreApiHandler.this.fActivity != null) {
                        CoronaStoreApiHandler.this.fActivity.getStore().purchase(str2);
                    }
                }
            });
        }
    }

    public void storeRestore() {
        if (this.fActivity != null) {
            this.fActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaStoreApiHandler.this.fActivity != null) {
                        CoronaStoreApiHandler.this.fActivity.getStore().restorePurchases();
                    }
                }
            });
        }
    }
}
