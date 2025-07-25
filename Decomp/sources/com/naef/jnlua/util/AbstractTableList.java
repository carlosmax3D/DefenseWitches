package com.naef.jnlua.util;

import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaValueProxy;
import java.util.AbstractList;
import java.util.RandomAccess;

public abstract class AbstractTableList extends AbstractList<Object> implements RandomAccess, LuaValueProxy {
    public void add(int i, Object obj) {
        LuaState luaState = getLuaState();
        synchronized (luaState) {
            int size = size();
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException("index: " + i + ", size: " + size);
            }
            pushValue();
            luaState.tableMove(-1, i + 1, i + 2, size - i);
            luaState.pushJavaObject(obj);
            luaState.rawSet(-2, i + 1);
            luaState.pop(1);
        }
    }

    public Object get(int i) {
        Object javaObject;
        LuaState luaState = getLuaState();
        synchronized (luaState) {
            int size = size();
            if (i < 0 || i >= size) {
                throw new IndexOutOfBoundsException("index: " + i + ", size: " + size);
            }
            pushValue();
            luaState.rawGet(-1, i + 1);
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

    public Object remove(int i) {
        Object obj;
        LuaState luaState = getLuaState();
        synchronized (luaState) {
            int size = size();
            if (i < 0 || i >= size) {
                throw new IndexOutOfBoundsException("index: " + i + ", size: " + size);
            }
            obj = get(i);
            pushValue();
            luaState.tableMove(-1, i + 2, i + 1, (size - i) - 1);
            luaState.pushNil();
            luaState.rawSet(-2, size);
            luaState.pop(1);
        }
        return obj;
    }

    public Object set(int i, Object obj) {
        Object obj2;
        LuaState luaState = getLuaState();
        synchronized (luaState) {
            int size = size();
            if (i < 0 || i >= size) {
                throw new IndexOutOfBoundsException("index: " + i + ", size: " + size);
            }
            obj2 = get(i);
            pushValue();
            luaState.pushJavaObject(obj);
            luaState.rawSet(-2, i + 1);
            luaState.pop(1);
        }
        return obj2;
    }

    public int size() {
        int length;
        LuaState luaState = getLuaState();
        synchronized (luaState) {
            pushValue();
            try {
                length = luaState.length(-1);
                luaState.pop(1);
            } catch (Throwable th) {
                luaState.pop(1);
                throw th;
            }
        }
        return length;
    }
}
