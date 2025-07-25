package com.ansca.corona.purchasing;

import android.app.Activity;
import com.ansca.corona.Controller;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.JavaToNativeShim;
import com.ansca.corona.events.EventManager;
import com.ansca.corona.events.RunnableEvent;

public abstract class Store {
    private Activity fActivity = null;
    protected Controller fController;
    protected CoronaRuntime fCoronaRuntime;
    private boolean fIsEnabled = false;

    public Store(CoronaRuntime coronaRuntime, Controller controller) {
        this.fController = controller;
        this.fCoronaRuntime = coronaRuntime;
    }

    public abstract boolean canMakePurchases();

    public abstract void confirmTransaction(String str);

    public void disable() {
        if (this.fIsEnabled) {
            this.fIsEnabled = false;
            onDisabled();
        }
    }

    public void enable() {
        if (!this.fIsEnabled) {
            if (this.fActivity == null) {
                throw new IllegalStateException();
            }
            this.fIsEnabled = true;
            onEnabled();
        }
    }

    public Activity getActivity() {
        return this.fActivity;
    }

    public boolean isDisabled() {
        return !this.fIsEnabled;
    }

    public boolean isEnabled() {
        return this.fIsEnabled;
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
    }

    /* access modifiers changed from: protected */
    public abstract void onEnabled();

    public abstract void purchase(String str);

    /* access modifiers changed from: protected */
    public void raiseTransactionEventFor(final StoreTransactionResultSettings storeTransactionResultSettings) {
        EventManager eventManager;
        if (storeTransactionResultSettings != null && (eventManager = this.fController.getEventManager()) != null) {
            eventManager.addEvent(new RunnableEvent(new Runnable() {
                public void run() {
                    if (Store.this.fController != null) {
                        JavaToNativeShim.storeTransactionEvent(Store.this.fCoronaRuntime, storeTransactionResultSettings);
                    }
                }
            }));
        }
    }

    public abstract void restorePurchases();

    public void setActivity(Activity activity) {
        this.fActivity = activity;
    }
}
