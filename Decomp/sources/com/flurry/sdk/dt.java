package com.flurry.sdk;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class dt<T> {
    private final List<WeakReference<T>> a = new LinkedList();

    public List<T> a() {
        ArrayList arrayList = new ArrayList(this.a.size());
        Iterator<WeakReference<T>> it = this.a.iterator();
        while (it.hasNext()) {
            Object obj = it.next().get();
            if (obj == null) {
                it.remove();
            } else {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public void a(T t) {
        if (t != null) {
            this.a.add(new WeakReference(t));
        }
    }

    public int b() {
        return this.a.size();
    }

    public boolean b(T t) {
        if (t == null) {
            return false;
        }
        Iterator<WeakReference<T>> it = this.a.iterator();
        while (it.hasNext()) {
            T t2 = it.next().get();
            if (t2 == null) {
                it.remove();
            } else if (t == t2 || t.equals(t2)) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
