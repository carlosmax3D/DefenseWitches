package com.flurry.sdk;

import java.util.Timer;
import java.util.TimerTask;

class dm {
    private Timer a;
    private a b;
    private b c;

    class a extends TimerTask {
        private b b;

        a(b bVar) {
            this.b = bVar;
        }

        public void run() {
            dm.this.a();
            if (this.b != null) {
                this.b.q();
            }
        }
    }

    public interface b {
        void q();
    }

    dm(b bVar) {
        this.c = bVar;
    }

    public synchronized void a() {
        if (this.a != null) {
            this.a.cancel();
            this.a = null;
        }
        this.b = null;
    }

    public synchronized void a(long j) {
        if (b()) {
            a();
        }
        this.a = new Timer("FlurrySessionTimer");
        this.b = new a(this.c);
        this.a.schedule(this.b, j);
    }

    public boolean b() {
        return this.a != null;
    }
}
