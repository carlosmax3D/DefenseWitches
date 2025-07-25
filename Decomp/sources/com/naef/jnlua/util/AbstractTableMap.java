package com.naef.jnlua.util;

import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaValueProxy;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractTableMap<K> extends AbstractMap<K, Object> implements LuaValueProxy {
    private Set<Map.Entry<K, Object>> entrySet;

    private class Entry implements Map.Entry<K, Object> {
        /* access modifiers changed from: private */
        public K key;

        public Entry(K k) {
            this.key = k;
        }

        /* access modifiers changed from: private */
        public LuaState getLuaState() {
            return AbstractTableMap.this.getLuaState();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return getLuaState() == entry.getLuaState() && this.key.equals(entry.key);
        }

        public K getKey() {
            return this.key;
        }

        public Object getValue() {
            return AbstractTableMap.this.get(this.key);
        }

        public int hashCode() {
            return (getLuaState().hashCode() * 65599) + this.key.hashCode();
        }

        public Object setValue(Object obj) {
            return AbstractTableMap.this.put(this.key, obj);
        }

        public String toString() {
            return this.key.toString();
        }
    }

    private class EntryIterator implements Iterator<Map.Entry<K, Object>> {
        private K key;

        private EntryIterator() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x0030 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:6:0x0019  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean hasNext() {
            /*
                r4 = this;
                r0 = 1
                com.naef.jnlua.util.AbstractTableMap r1 = com.naef.jnlua.util.AbstractTableMap.this
                com.naef.jnlua.LuaState r1 = r1.getLuaState()
                monitor-enter(r1)
                com.naef.jnlua.util.AbstractTableMap r2 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x0037 }
                r2.pushValue()     // Catch:{ all -> 0x0037 }
                K r2 = r4.key     // Catch:{ all -> 0x0037 }
                r1.pushJavaObject(r2)     // Catch:{ all -> 0x0037 }
            L_0x0012:
                r2 = -2
                boolean r2 = r1.next(r2)     // Catch:{ all -> 0x0037 }
                if (r2 == 0) goto L_0x0030
                com.naef.jnlua.util.AbstractTableMap r2 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x0037 }
                boolean r2 = r2.filterKeys()     // Catch:{ all -> 0x0037 }
                if (r2 == 0) goto L_0x002a
                com.naef.jnlua.util.AbstractTableMap r2 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x0037 }
                r3 = -2
                boolean r2 = r2.acceptKey(r3)     // Catch:{ all -> 0x0037 }
                if (r2 == 0) goto L_0x0012
            L_0x002a:
                r2 = 3
                r1.pop(r2)     // Catch:{ all -> 0x0037 }
                monitor-exit(r1)     // Catch:{ all -> 0x0037 }
            L_0x002f:
                return r0
            L_0x0030:
                r0 = 1
                r1.pop(r0)     // Catch:{ all -> 0x0037 }
                r0 = 0
                monitor-exit(r1)     // Catch:{ all -> 0x0037 }
                goto L_0x002f
            L_0x0037:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0037 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.naef.jnlua.util.AbstractTableMap.EntryIterator.hasNext():boolean");
        }

        /* JADX WARNING: Removed duplicated region for block: B:6:0x0018  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.Map.Entry<K, java.lang.Object> next() {
            /*
                r4 = this;
                com.naef.jnlua.util.AbstractTableMap r0 = com.naef.jnlua.util.AbstractTableMap.this
                com.naef.jnlua.LuaState r1 = r0.getLuaState()
                monitor-enter(r1)
                com.naef.jnlua.util.AbstractTableMap r0 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x004b }
                r0.pushValue()     // Catch:{ all -> 0x004b }
                K r0 = r4.key     // Catch:{ all -> 0x004b }
                r1.pushJavaObject(r0)     // Catch:{ all -> 0x004b }
            L_0x0011:
                r0 = -2
                boolean r0 = r1.next(r0)     // Catch:{ all -> 0x004b }
                if (r0 == 0) goto L_0x0041
                com.naef.jnlua.util.AbstractTableMap r0 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x004b }
                boolean r0 = r0.filterKeys()     // Catch:{ all -> 0x004b }
                if (r0 == 0) goto L_0x0029
                com.naef.jnlua.util.AbstractTableMap r0 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x004b }
                r2 = -2
                boolean r0 = r0.acceptKey(r2)     // Catch:{ all -> 0x004b }
                if (r0 == 0) goto L_0x0011
            L_0x0029:
                com.naef.jnlua.util.AbstractTableMap r0 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x004b }
                r2 = -2
                java.lang.Object r0 = r0.convertKey(r2)     // Catch:{ all -> 0x004b }
                r4.key = r0     // Catch:{ all -> 0x004b }
                r0 = 3
                r1.pop(r0)     // Catch:{ all -> 0x004b }
                com.naef.jnlua.util.AbstractTableMap$Entry r0 = new com.naef.jnlua.util.AbstractTableMap$Entry     // Catch:{ all -> 0x004b }
                com.naef.jnlua.util.AbstractTableMap r2 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x004b }
                K r3 = r4.key     // Catch:{ all -> 0x004b }
                r0.<init>(r3)     // Catch:{ all -> 0x004b }
                monitor-exit(r1)     // Catch:{ all -> 0x004b }
                return r0
            L_0x0041:
                r0 = 1
                r1.pop(r0)     // Catch:{ all -> 0x004b }
                java.util.NoSuchElementException r0 = new java.util.NoSuchElementException     // Catch:{ all -> 0x004b }
                r0.<init>()     // Catch:{ all -> 0x004b }
                throw r0     // Catch:{ all -> 0x004b }
            L_0x004b:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x004b }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.naef.jnlua.util.AbstractTableMap.EntryIterator.next():java.util.Map$Entry");
        }

        public void remove() {
            LuaState luaState = AbstractTableMap.this.getLuaState();
            synchronized (luaState) {
                AbstractTableMap.this.pushValue();
                luaState.pushJavaObject(this.key);
                luaState.pushNil();
                luaState.setTable(-3);
                luaState.pop(1);
            }
        }
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, Object>> {
        private EntrySet() {
        }

        public boolean contains(Object obj) {
            AbstractTableMap.this.checkKey(obj);
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (entry.getLuaState() == AbstractTableMap.this.getLuaState()) {
                return AbstractTableMap.this.containsKey(entry.key);
            }
            return false;
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x002f A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:6:0x0017  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean isEmpty() {
            /*
                r4 = this;
                r0 = 1
                com.naef.jnlua.util.AbstractTableMap r1 = com.naef.jnlua.util.AbstractTableMap.this
                com.naef.jnlua.LuaState r1 = r1.getLuaState()
                monitor-enter(r1)
                com.naef.jnlua.util.AbstractTableMap r2 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x0035 }
                r2.pushValue()     // Catch:{ all -> 0x0035 }
                r1.pushNil()     // Catch:{ all -> 0x0035 }
            L_0x0010:
                r2 = -2
                boolean r2 = r1.next(r2)     // Catch:{ all -> 0x0035 }
                if (r2 == 0) goto L_0x002f
                com.naef.jnlua.util.AbstractTableMap r2 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x0035 }
                boolean r2 = r2.filterKeys()     // Catch:{ all -> 0x0035 }
                if (r2 == 0) goto L_0x0028
                com.naef.jnlua.util.AbstractTableMap r2 = com.naef.jnlua.util.AbstractTableMap.this     // Catch:{ all -> 0x0035 }
                r3 = -2
                boolean r2 = r2.acceptKey(r3)     // Catch:{ all -> 0x0035 }
                if (r2 == 0) goto L_0x0010
            L_0x0028:
                r0 = 3
                r1.pop(r0)     // Catch:{ all -> 0x0035 }
                r0 = 0
                monitor-exit(r1)     // Catch:{ all -> 0x0035 }
            L_0x002e:
                return r0
            L_0x002f:
                r2 = 1
                r1.pop(r2)     // Catch:{ all -> 0x0035 }
                monitor-exit(r1)     // Catch:{ all -> 0x0035 }
                goto L_0x002e
            L_0x0035:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0035 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.naef.jnlua.util.AbstractTableMap.EntrySet.isEmpty():boolean");
        }

        public Iterator<Map.Entry<K, Object>> iterator() {
            return new EntryIterator();
        }

        public boolean remove(Object obj) {
            boolean z;
            if (!(obj instanceof Entry)) {
                return false;
            }
            if (((Entry) obj).getLuaState() != AbstractTableMap.this.getLuaState()) {
                return false;
            }
            LuaState luaState = AbstractTableMap.this.getLuaState();
            synchronized (luaState) {
                AbstractTableMap.this.pushValue();
                luaState.pushJavaObject(obj);
                luaState.getTable(-2);
                z = !luaState.isNil(-1);
                luaState.pop(1);
                if (z) {
                    luaState.pushJavaObject(obj);
                    luaState.pushNil();
                    luaState.setTable(-3);
                }
                luaState.pop(1);
            }
            return z;
        }

        public int size() {
            int i;
            LuaState luaState = AbstractTableMap.this.getLuaState();
            synchronized (luaState) {
                i = 0;
                AbstractTableMap.this.pushValue();
                if (AbstractTableMap.this.filterKeys()) {
                    luaState.pushNil();
                    while (luaState.next(-2)) {
                        if (AbstractTableMap.this.acceptKey(-2)) {
                            i++;
                        }
                        luaState.pop(1);
                    }
                } else {
                    i = luaState.tableSize(-1);
                }
                luaState.pop(1);
            }
            return i;
        }
    }

    /* access modifiers changed from: protected */
    public boolean acceptKey(int i) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void checkKey(Object obj) {
        if (obj == null) {
            throw new NullPointerException("key must not be null");
        }
    }

    public boolean containsKey(Object obj) {
        boolean z;
        checkKey(obj);
        LuaState luaState = getLuaState();
        synchronized (luaState) {
            pushValue();
            luaState.pushJavaObject(obj);
            luaState.getTable(-2);
            try {
                z = !luaState.isNil(-1);
                luaState.pop(2);
            } catch (Throwable th) {
                luaState.pop(2);
                throw th;
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public abstract K convertKey(int i);

    public Set<Map.Entry<K, Object>> entrySet() {
        if (this.entrySet == null) {
            this.entrySet = new EntrySet();
        }
        return this.entrySet;
    }

    /* access modifiers changed from: protected */
    public boolean filterKeys() {
        return false;
    }

    public Object get(Object obj) {
        Object javaObject;
        checkKey(obj);
        LuaState luaState = getLuaState();
        synchronized (luaState) {
            pushValue();
            luaState.pushJavaObject(obj);
            luaState.getTable(-2);
            try {
                javaObject = luaState.toJavaObject(-1, Object.class);
                luaState.pop(2);
            } catch (Throwable th) {
                luaState.pop(2);
                throw th;
            }
        }
        return javaObject;
    }

    public boolean isEmpty() {
        return entrySet().isEmpty();
    }

    public Object put(K k, Object obj) {
        Object obj2;
        checkKey(k);
        LuaState luaState = getLuaState();
        synchronized (luaState) {
            obj2 = get(k);
            pushValue();
            luaState.pushJavaObject(k);
            luaState.pushJavaObject(obj);
            luaState.setTable(-3);
            luaState.pop(1);
        }
        return obj2;
    }

    public Object remove(Object obj) {
        Object obj2;
        checkKey(obj);
        LuaState luaState = getLuaState();
        synchronized (luaState) {
            obj2 = get(obj);
            pushValue();
            luaState.pushJavaObject(obj);
            luaState.pushNil();
            luaState.setTable(-3);
            luaState.pop(1);
        }
        return obj2;
    }
}
