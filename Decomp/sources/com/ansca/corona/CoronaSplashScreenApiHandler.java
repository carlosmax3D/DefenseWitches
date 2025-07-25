package com.ansca.corona;

import com.ansca.corona.listeners.CoronaSplashScreenApiListener;

class CoronaSplashScreenApiHandler implements CoronaSplashScreenApiListener {
    /* access modifiers changed from: private */
    public CoronaActivity fActivity;

    public CoronaSplashScreenApiHandler(CoronaActivity coronaActivity) {
        this.fActivity = coronaActivity;
    }

    public void hideSplashScreen() {
        if (this.fActivity != null) {
            this.fActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaSplashScreenApiHandler.this.fActivity != null) {
                        CoronaSplashScreenApiHandler.this.fActivity.hideSplashScreen();
                    }
                }
            });
        }
    }

    public void showSplashScreen() {
        if (this.fActivity != null) {
            this.fActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaSplashScreenApiHandler.this.fActivity != null) {
                        CoronaSplashScreenApiHandler.this.fActivity.showSplashScreen();
                    }
                }
            });
        }
    }
}
