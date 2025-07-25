package com.flurry.sdk;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ds<K, V> {
    private final Map<K, List<V>> a = new HashMap();
    private int b;

    private List<V> a(K k, boolean z) {
        List<V> list = this.a.get(k);
        if (z && list == null) {
            list = this.b > 0 ? new ArrayList<>(this.b) : new ArrayList<>();
            this.a.put(k, list);
        }
        return list;
    }

    public List<V> a(K k) {
        if (k == null) {
            return Collections.emptyList();
        }
        List<V> a2 = a(k, false);
        return a2 == null ? Collections.emptyList() : a2;
    }

    public void a() {
        this.a.clear();
    }

    public void a(ds<K, V> dsVar) {
        if (dsVar != null) {
            for (Map.Entry next : dsVar.a.entrySet()) {
                this.a.put(next.getKey(), next.getValue());
            }
        }
    }

    public void a(K k, V v) {
        if (k != null) {
            a(k, true).add(v);
        }
    }

    public Collection<Map.Entry<K, V>> b() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry next : this.a.entrySet()) {
            for (Object simpleImmutableEntry : (List) next.getValue()) {
                arrayList.add(new AbstractMap.SimpleImmutableEntry(next.getKey(), simpleImmutableEntry));
            }
        }
        return arrayList;
    }

    public boolean b(K k) {
        if (k == null) {
            return false;
        }
        return this.a.remove(k) != null;
    }

    public boolean b(K k, V v) {
        List a2;
        boolean z = false;
        if (!(k == null || (a2 = a(k, false)) == null)) {
            z = a2.remove(v);
            if (a2.size() == 0) {
                this.a.remove(k);
            }
        }
        return z;
    }

    public Set<K> c() {
        return this.a.keySet();
    }
}
