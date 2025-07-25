package com.ansca.corona.purchasing;

import android.app.Activity;
import android.util.Log;
import com.ansca.corona.Controller;
import com.ansca.corona.CoronaRuntime;

public class StoreProxy extends Store {
    private static final String STORE_NOT_SUPPORTED_WARNING_MESSAGE = "This application does not support in-app purchases on this device.";
    private Store fStore = null;

    public StoreProxy(CoronaRuntime coronaRuntime, Controller controller) {
        super(coronaRuntime, controller);
    }

    private void setStoreTo(Store store) {
        if (store != this.fStore) {
            if (this.fStore != null) {
                this.fStore.disable();
            }
            this.fStore = store;
            if (this.fStore != null) {
                this.fStore.setActivity(getActivity());
                if (isEnabled()) {
                    this.fStore.enable();
                } else {
                    this.fStore.disable();
                }
            }
        }
    }

    public boolean canMakePurchases() {
        if (this.fStore == null) {
            return false;
        }
        return this.fStore.canMakePurchases();
    }

    public void confirmTransaction(String str) {
        if (this.fStore == null) {
            StoreTransactionResultSettings storeTransactionResultSettings = new StoreTransactionResultSettings();
            storeTransactionResultSettings.setState(StoreTransactionState.FAILED);
            storeTransactionResultSettings.setErrorType(StoreTransactionErrorType.CLIENT_INVALID);
            storeTransactionResultSettings.setErrorMessage(STORE_NOT_SUPPORTED_WARNING_MESSAGE);
            storeTransactionResultSettings.setTransactionStringId(str);
            raiseTransactionEventFor(storeTransactionResultSettings);
            logStoreNotSupportedWarning();
            return;
        }
        this.fStore.confirmTransaction(str);
    }

    public void disable() {
        super.disable();
        if (this.fStore != null) {
            this.fStore.disable();
        }
    }

    public void enable() {
        super.enable();
        if (this.fStore != null) {
            this.fStore.enable();
        }
    }

    public void logStoreNotSupportedWarning() {
        Log.v("Corona", STORE_NOT_SUPPORTED_WARNING_MESSAGE);
    }

    /* access modifiers changed from: protected */
    public void onEnabled() {
    }

    public void purchase(String str) {
        if (this.fStore == null) {
            StoreTransactionResultSettings storeTransactionResultSettings = new StoreTransactionResultSettings();
            storeTransactionResultSettings.setState(StoreTransactionState.FAILED);
            storeTransactionResultSettings.setErrorType(StoreTransactionErrorType.CLIENT_INVALID);
            storeTransactionResultSettings.setErrorMessage(STORE_NOT_SUPPORTED_WARNING_MESSAGE);
            storeTransactionResultSettings.setProductName(str);
            raiseTransactionEventFor(storeTransactionResultSettings);
            logStoreNotSupportedWarning();
            return;
        }
        this.fStore.purchase(str);
    }

    public void restorePurchases() {
        if (this.fStore == null) {
            StoreTransactionResultSettings storeTransactionResultSettings = new StoreTransactionResultSettings();
            storeTransactionResultSettings.setState(StoreTransactionState.FAILED);
            storeTransactionResultSettings.setErrorType(StoreTransactionErrorType.CLIENT_INVALID);
            storeTransactionResultSettings.setErrorMessage(STORE_NOT_SUPPORTED_WARNING_MESSAGE);
            raiseTransactionEventFor(storeTransactionResultSettings);
            logStoreNotSupportedWarning();
            return;
        }
        this.fStore.restorePurchases();
    }

    public void setActivity(Activity activity) {
        super.setActivity(activity);
        if (this.fStore != null) {
            this.fStore.setActivity(activity);
        }
    }

    public void useNoStore() {
        setStoreTo((Store) null);
    }

    public void useStore(String str) {
        useNoStore();
    }
}
