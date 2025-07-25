package com.ansca.corona;

import com.ansca.corona.listeners.CoronaStatusBarApiListener;

class CoronaStatusBarApiHandler implements CoronaStatusBarApiListener {
    /* access modifiers changed from: private */
    public CoronaActivity fActivity;

    public CoronaStatusBarApiHandler(CoronaActivity coronaActivity) {
        this.fActivity = coronaActivity;
    }

    public int getStatusBarHeight() {
        if (this.fActivity == null) {
            return 0;
        }
        return this.fActivity.getStatusBarHeight();
    }

    public CoronaStatusBarSettings getStatusBarMode() {
        return this.fActivity == null ? CoronaStatusBarSettings.CORONA_STATUSBAR_MODE_HIDDEN : this.fActivity.getStatusBarMode();
    }

    public void setStatusBarMode(CoronaStatusBarSettings coronaStatusBarSettings) {
        if (this.fActivity != null) {
            final CoronaStatusBarSettings coronaStatusBarSettings2 = coronaStatusBarSettings;
            this.fActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaStatusBarApiHandler.this.fActivity != null) {
                        CoronaStatusBarApiHandler.this.fActivity.setStatusBarMode(coronaStatusBarSettings2);
                    }
                }
            });
        }
    }
}
