package com.ansca.corona;

import android.view.Window;

public class CoronaApiHandler implements CoronaApiListener {
    /* access modifiers changed from: private */
    public CoronaActivity fActivity;
    private CoronaRuntime fCoronaRuntime;

    public CoronaApiHandler(CoronaActivity coronaActivity, CoronaRuntime coronaRuntime) {
        this.fActivity = coronaActivity;
        this.fCoronaRuntime = coronaRuntime;
    }

    public void addKeepScreenOnFlag() {
        this.fActivity.runOnUiThread(new Runnable() {
            public void run() {
                Window window = CoronaApiHandler.this.fActivity.getWindow();
                if (window != null) {
                    window.addFlags(128);
                }
            }
        });
    }

    public void onScreenLockStateChanged(boolean z) {
        if (this.fActivity != null) {
            this.fActivity.onScreenLockStateChanged(z);
        }
    }

    public void removeKeepScreenOnFlag() {
        this.fActivity.runOnUiThread(new Runnable() {
            public void run() {
                Window window = CoronaApiHandler.this.fActivity.getWindow();
                if (window != null) {
                    window.clearFlags(128);
                }
            }
        });
    }
}
