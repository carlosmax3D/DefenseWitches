package com.ansca.corona;

import android.content.Intent;
import com.ansca.corona.listeners.CoronaSystemApiListener;

public class CoronaSystemApiHandler implements CoronaSystemApiListener {
    /* access modifiers changed from: private */
    public CoronaActivity fActivity;

    public CoronaSystemApiHandler(CoronaActivity coronaActivity) {
        this.fActivity = coronaActivity;
    }

    public Intent getInitialIntent() {
        if (this.fActivity == null) {
            return null;
        }
        return this.fActivity.getInitialIntent();
    }

    public Intent getIntent() {
        if (this.fActivity == null) {
            return null;
        }
        return this.fActivity.getIntent();
    }

    public boolean requestSystem(String str) {
        if (this.fActivity == null || str == null || str.length() <= 0 || this.fActivity == null) {
            return false;
        }
        if (str.equals("exitApplication")) {
            this.fActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaSystemApiHandler.this.fActivity != null) {
                        CoronaSystemApiHandler.this.fActivity.finish();
                    }
                }
            });
        } else if (!str.equals("suspendApplication")) {
            return false;
        } else {
            this.fActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaSystemApiHandler.this.fActivity != null) {
                        CoronaSystemApiHandler.this.fActivity.moveTaskToBack(true);
                    }
                }
            });
        }
        return true;
    }
}
