package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class dl {
    private static final String a = dl.class.getSimpleName();
    private static dl b;
    private final Map<String, dj> c = new HashMap();
    private final Map<Context, dj> d = new WeakHashMap();
    private final Object e = new Object();
    private dj f;

    private dl() {
    }

    public static synchronized dl a() {
        dl dlVar;
        synchronized (dl.class) {
            if (b == null) {
                b = new dl();
            }
            dlVar = b;
        }
        return dlVar;
    }

    public synchronized void a(Context context) {
        dj remove = this.d.remove(context);
        if (remove == null) {
            eo.d(a, "Session cannot be ended, session not found for context: " + context);
        } else {
            remove.c(context);
        }
    }

    public synchronized void a(Context context, String str) {
        dj djVar;
        Cdo.a(context);
        ev.a().b();
        dz.a().b();
        dj djVar2 = this.d.get(context);
        if (djVar2 != null) {
            eo.d(a, "Session already started with context: " + context + " count:" + djVar2.g());
        } else {
            if (this.c.containsKey(str)) {
                djVar = this.c.get(str);
            } else {
                djVar = new dj(str);
                this.c.put(str, djVar);
                djVar.a(context);
            }
            this.d.put(context, djVar);
            a(djVar);
            djVar.b(context);
        }
    }

    public void a(dj djVar) {
        synchronized (this.e) {
            this.f = djVar;
        }
    }

    public synchronized void a(String str) {
        if (!this.c.containsKey(str)) {
            eo.a(6, a, "Ended session is not in the session map! Maybe it was already destroyed.");
        } else {
            dj c2 = c();
            if (c2 != null && TextUtils.equals(c2.j(), str)) {
                a((dj) null);
            }
            this.c.remove(str);
        }
    }

    public synchronized int b() {
        return this.d.size();
    }

    public dj c() {
        dj djVar;
        synchronized (this.e) {
            djVar = this.f;
        }
        return djVar;
    }

    public synchronized void d() {
        for (Map.Entry next : this.d.entrySet()) {
            ((dj) next.getValue()).c((Context) next.getKey());
        }
        this.d.clear();
        for (dj c2 : new ArrayList(this.c.values())) {
            c2.c();
        }
        this.c.clear();
        a((dj) null);
    }
}
