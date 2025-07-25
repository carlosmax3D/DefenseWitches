package com.ansca.corona;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CoronaService extends Service {
    private Binder fBinder = null;

    private static class Binder extends android.os.Binder {
        private CoronaService fService;

        public Binder(CoronaService coronaService) {
            this.fService = coronaService;
        }

        /* access modifiers changed from: package-private */
        public CoronaService getService() {
            return this.fService;
        }
    }

    public IBinder onBind(Intent intent) {
        if (this.fBinder == null) {
            this.fBinder = new Binder(this);
        }
        return this.fBinder;
    }

    public void onCreate() {
    }

    public void onDestroy() {
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }
}
