package com.flurry.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.List;

public class ev extends BroadcastReceiver {
    private static ev e;
    boolean a;
    Boolean b;
    private final dt<eu> c = new dt<>();
    private boolean d = false;

    public enum a {
        NONE_OR_UNKNOWN(0),
        WIFI(1),
        CELL(2);
        
        private int d;

        private a(int i) {
            this.d = i;
        }

        public int a() {
            return this.d;
        }
    }

    public static synchronized ev a() {
        ev evVar;
        synchronized (ev.class) {
            if (e == null) {
                e = new ev();
            }
            evVar = e;
        }
        return evVar;
    }

    private boolean a(Context context) {
        if (!this.d || context == null) {
            return true;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private synchronized List<eu> f() {
        return this.c.a();
    }

    public synchronized void a(eu euVar) {
        this.c.a(euVar);
    }

    public synchronized void b() {
        Context b2 = Cdo.a().b();
        this.d = b2.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0;
        this.a = a(b2);
        if (this.d) {
            d();
        }
    }

    public boolean c() {
        return this.b != null ? this.b.booleanValue() : this.a;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        Context b2 = Cdo.a().b();
        this.a = a(b2);
        b2.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public a e() {
        if (!this.d) {
            return a.NONE_OR_UNKNOWN;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) Cdo.a().b().getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return a.NONE_OR_UNKNOWN;
        }
        switch (activeNetworkInfo.getType()) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return a.CELL;
            case 1:
            case 9:
                return a.WIFI;
            case 8:
                return a.NONE_OR_UNKNOWN;
            default:
                return a.CELL;
        }
    }

    public void onReceive(Context context, Intent intent) {
        boolean a2 = a(context);
        if (this.a != a2) {
            this.a = a2;
            for (eu a3 : f()) {
                a3.a(this.a);
            }
        }
    }
}
