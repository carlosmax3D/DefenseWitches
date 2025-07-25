package com.naef.jnlua;

import com.naef.jnlua.JavaReflector;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LuaState {
    private static final int APIVERSION = 2;
    public static final int ENVIRONINDEX = -10001;
    public static final int GLOBALSINDEX = -10002;
    public static final String LUA_VERSION = lua_version();
    public static final int MULTRET = -1;
    public static final int REGISTRYINDEX = -10000;
    public static final String VERSION = "0.9";
    public static final int YIELD = 1;
    private ClassLoader classLoader;
    private Converter converter;
    private Object finalizeGuardian;
    private JavaReflector javaReflector;
    private long luaState;
    private long luaThread;
    private boolean ownState;
    /* access modifiers changed from: private */
    public ReferenceQueue<LuaValueProxyImpl> proxyQueue;
    /* access modifiers changed from: private */
    public Set<LuaValueProxyRef> proxySet;

    public enum GcAction {
        STOP,
        RESTART,
        COLLECT,
        COUNT,
        COUNTB,
        STEP,
        SETPAUSE,
        SETSTEPMUL
    }

    public enum Library {
        BASE,
        TABLE,
        IO,
        OS,
        STRING,
        MATH,
        DEBUG,
        PACKAGE,
        JAVA {
            /* access modifiers changed from: package-private */
            public void open(LuaState luaState) {
                JavaModule.getInstance().open(luaState);
            }
        };

        /* access modifiers changed from: package-private */
        public void open(LuaState luaState) {
            luaState.lua_openlib(ordinal());
        }
    }

    private class LuaInvocationHandler extends LuaValueProxyImpl implements InvocationHandler {
        public LuaInvocationHandler(int i) {
            super(i);
        }

        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            Object obj2;
            int i = 0;
            if (method.getDeclaringClass() == LuaValueProxy.class) {
                return method.invoke(this, objArr);
            }
            synchronized (LuaState.this) {
                pushValue();
                LuaState.this.getField(-1, method.getName());
                if (!LuaState.this.isFunction(-1)) {
                    LuaState.this.pop(2);
                    throw new UnsupportedOperationException(method.getName());
                }
                LuaState.this.insert(-2);
                int length = objArr != null ? objArr.length : 0;
                for (int i2 = 0; i2 < length; i2++) {
                    LuaState.this.pushJavaObject(objArr[i2]);
                }
                if (method.getReturnType() != Void.TYPE) {
                    i = 1;
                }
                LuaState.this.call(length + 1, i);
                if (i == 1) {
                    try {
                        obj2 = LuaState.this.toJavaObject(-1, method.getReturnType());
                    } catch (Throwable th) {
                        if (i == 1) {
                            LuaState.this.pop(1);
                        }
                        throw th;
                    }
                } else {
                    obj2 = null;
                }
                if (i == 1) {
                    LuaState.this.pop(1);
                }
            }
            return obj2;
        }
    }

    private class LuaValueProxyImpl implements LuaValueProxy {
        private int reference;

        public LuaValueProxyImpl(int i) {
            this.reference = i;
            LuaState.this.proxySet.add(new LuaValueProxyRef(this, i));
        }

        public LuaState getLuaState() {
            return LuaState.this;
        }

        public void pushValue() {
            synchronized (LuaState.this) {
                LuaState.this.rawGet(LuaState.REGISTRYINDEX, this.reference);
            }
        }
    }

    private static class LuaValueProxyRef extends PhantomReference<LuaValueProxyImpl> {
        private int reference;

        public LuaValueProxyRef(LuaValueProxyImpl luaValueProxyImpl, int i) {
            super(luaValueProxyImpl, luaValueProxyImpl.getLuaState().proxyQueue);
            this.reference = i;
        }

        public int getReference() {
            return this.reference;
        }
    }

    static {
        NativeSupport.getInstance().getLoader().load();
    }

    public LuaState() {
        this(0);
    }

    public LuaState(long j) {
        this.proxySet = new HashSet();
        this.proxyQueue = new ReferenceQueue<>();
        this.ownState = j == 0;
        lua_newstate(2, j);
        check();
        if (this.ownState) {
            this.finalizeGuardian = new Object() {
                public void finalize() {
                    synchronized (LuaState.this) {
                        LuaState.this.closeInternal();
                    }
                }
            };
        } else {
            this.finalizeGuardian = null;
        }
        for (final JavaReflector.Metamethod metamethod : JavaReflector.Metamethod.values()) {
            lua_pushjavafunction(new JavaFunction() {
                public int invoke(LuaState luaState) {
                    JavaFunction metamethod = LuaState.this.getMetamethod(luaState.toJavaObjectRaw(1), metamethod);
                    if (metamethod != null) {
                        return metamethod.invoke(LuaState.this);
                    }
                    throw new UnsupportedOperationException(metamethod.getMetamethodName());
                }
            });
            lua_setfield(-2, metamethod.getMetamethodName());
        }
        lua_pop(1);
        this.classLoader = Thread.currentThread().getContextClassLoader();
        this.javaReflector = DefaultJavaReflector.getInstance();
        this.converter = DefaultConverter.getInstance();
    }

    private void check() {
        if (!isOpenInternal()) {
            throw new IllegalStateException("Lua state is closed");
        }
        while (true) {
            LuaValueProxyRef luaValueProxyRef = (LuaValueProxyRef) this.proxyQueue.poll();
            if (luaValueProxyRef != null) {
                this.proxySet.remove(luaValueProxyRef);
                lua_unref(REGISTRYINDEX, luaValueProxyRef.getReference());
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void closeInternal() {
        if (isOpenInternal()) {
            lua_close(this.ownState);
            if (isOpenInternal()) {
                throw new IllegalStateException("cannot close");
            }
        }
    }

    private LuaRuntimeException getArgException(int i, String str) {
        String str2;
        String format;
        check();
        String lua_funcname = lua_funcname();
        int lua_narg = lua_narg(i);
        if (lua_narg > 0) {
            str2 = String.format("argument #%d", new Object[]{Integer.valueOf(lua_narg)});
        } else {
            str2 = "self argument";
        }
        if (lua_funcname != null) {
            format = String.format("bad %s to '%s' (%s)", new Object[]{str2, lua_funcname, str});
        } else {
            format = String.format("bad %s (%s)", new Object[]{str2, str});
        }
        return new LuaRuntimeException(format);
    }

    private LuaRuntimeException getArgTypeException(int i, LuaType luaType) {
        return getArgException(i, String.format("expected %s, got %s", new Object[]{luaType.toString().toLowerCase(), type(i).toString().toLowerCase()}));
    }

    private boolean isOpenInternal() {
        return this.luaState != 0;
    }

    private native void lua_close(boolean z);

    private native void lua_concat(int i);

    private native void lua_createtable(int i, int i2);

    private native void lua_dump(OutputStream outputStream) throws IOException;

    private native int lua_equal(int i, int i2);

    private native String lua_findtable(int i, String str, int i2);

    private native String lua_funcname();

    private native int lua_gc(int i, int i2);

    private native void lua_getfenv(int i);

    private native void lua_getfield(int i, String str);

    private native void lua_getglobal(String str);

    private native int lua_getmetafield(int i, String str);

    private native int lua_getmetatable(int i);

    private native void lua_gettable(int i);

    private native int lua_gettop();

    private native void lua_insert(int i);

    private native int lua_isboolean(int i);

    private native int lua_iscfunction(int i);

    private native int lua_isfunction(int i);

    private native int lua_isjavafunction(int i);

    private native int lua_isjavaobject(int i);

    private native int lua_isnil(int i);

    private native int lua_isnone(int i);

    private native int lua_isnoneornil(int i);

    private native int lua_isnumber(int i);

    private native int lua_isstring(int i);

    private native int lua_istable(int i);

    private native int lua_isthread(int i);

    private native int lua_lessthan(int i, int i2);

    private native void lua_load(InputStream inputStream, String str) throws IOException;

    private native int lua_narg(int i);

    private native void lua_newstate(int i, long j);

    private native void lua_newtable();

    private native void lua_newthread();

    private native int lua_next(int i);

    private native int lua_objlen(int i);

    /* access modifiers changed from: private */
    public native void lua_openlib(int i);

    private native void lua_pcall(int i, int i2);

    private native void lua_pop(int i);

    private native void lua_pushboolean(int i);

    private native void lua_pushbytearray(byte[] bArr, int i);

    private native void lua_pushinteger(int i);

    private native void lua_pushjavafunction(JavaFunction javaFunction);

    private native void lua_pushjavaobject(Object obj);

    private native void lua_pushnil();

    private native void lua_pushnumber(double d);

    private native void lua_pushstring(String str);

    private native void lua_pushvalue(int i);

    private native int lua_rawequal(int i, int i2);

    private native void lua_rawget(int i);

    private native void lua_rawgeti(int i, int i2);

    private native void lua_rawset(int i);

    private native void lua_rawseti(int i, int i2);

    private native int lua_ref(int i);

    private native void lua_remove(int i);

    private native void lua_replace(int i);

    private native int lua_resume(int i, int i2);

    private native int lua_setfenv(int i);

    private native void lua_setfield(int i, String str);

    private native void lua_setglobal(String str);

    private native int lua_setmetatable(int i);

    private native void lua_settable(int i);

    private native void lua_settop(int i);

    private native int lua_status(int i);

    private native void lua_tablemove(int i, int i2, int i3, int i4);

    private native int lua_tablesize(int i);

    private native int lua_toboolean(int i);

    private native byte[] lua_tobytearray(int i);

    private native int lua_tointeger(int i);

    private native JavaFunction lua_tojavafunction(int i);

    private native Object lua_tojavaobject(int i);

    private native double lua_tonumber(int i);

    private native long lua_topointer(int i);

    private native String lua_tostring(int i);

    private native int lua_type(int i);

    private native void lua_unref(int i, int i2);

    private static native String lua_version();

    private native int lua_yield(int i);

    public synchronized void call(int i, int i2) {
        check();
        lua_pcall(i, i2);
    }

    public synchronized void checkArg(int i, boolean z, String str) {
        check();
        if (!z) {
            throw getArgException(i, str);
        }
    }

    public synchronized boolean checkBoolean(int i) {
        check();
        if (!isBoolean(i)) {
            throw getArgTypeException(i, LuaType.BOOLEAN);
        }
        return toBoolean(i);
    }

    public synchronized boolean checkBoolean(int i, boolean z) {
        check();
        if (!isNoneOrNil(i)) {
            z = checkBoolean(i);
        }
        return z;
    }

    public synchronized byte[] checkByteArray(int i) {
        check();
        if (!isString(i)) {
            throw getArgTypeException(i, LuaType.STRING);
        }
        return toByteArray(i);
    }

    public synchronized byte[] checkByteArray(int i, byte[] bArr) {
        check();
        if (!isNoneOrNil(i)) {
            bArr = checkByteArray(i);
        }
        return bArr;
    }

    public synchronized int checkInteger(int i) {
        check();
        if (!isNumber(i)) {
            throw getArgTypeException(i, LuaType.NUMBER);
        }
        return toInteger(i);
    }

    public synchronized int checkInteger(int i, int i2) {
        check();
        if (!isNoneOrNil(i)) {
            i2 = checkInteger(i);
        }
        return i2;
    }

    public synchronized <T> T checkJavaObject(int i, Class<T> cls) {
        check();
        if (!isJavaObject(i, cls)) {
            throw getArgException(i, String.format("exptected %s, got %s", new Object[]{cls.getCanonicalName(), typeName(i)}));
        }
        return toJavaObject(i, cls);
    }

    public synchronized <T> T checkJavaObject(int i, Class<T> cls, T t) {
        check();
        if (!isNoneOrNil(i)) {
            t = checkJavaObject(i, cls);
        }
        return t;
    }

    public synchronized double checkNumber(int i) {
        check();
        if (!isNumber(i)) {
            throw getArgTypeException(i, LuaType.NUMBER);
        }
        return toNumber(i);
    }

    public synchronized double checkNumber(int i, double d) {
        check();
        if (!isNoneOrNil(i)) {
            d = checkNumber(i);
        }
        return d;
    }

    public synchronized String checkOption(int i, String[] strArr) {
        String checkString;
        int i2 = 0;
        synchronized (this) {
            check();
            checkString = checkString(i);
            while (i2 < strArr.length) {
                if (!checkString.equals(strArr[i2])) {
                    i2++;
                }
            }
            throw getArgException(i, String.format("expected one of %s, got %s", new Object[]{Arrays.asList(strArr), checkString}));
        }
        return checkString;
    }

    public synchronized String checkOption(int i, String[] strArr, String str) {
        check();
        if (!isNoneOrNil(i)) {
            str = checkOption(i, strArr);
        }
        return str;
    }

    public synchronized String checkString(int i) {
        check();
        if (!isString(i)) {
            throw getArgTypeException(i, LuaType.STRING);
        }
        return toString(i);
    }

    public synchronized String checkString(int i, String str) {
        check();
        if (!isNoneOrNil(i)) {
            str = checkString(i);
        }
        return str;
    }

    public synchronized void checkType(int i, LuaType luaType) {
        check();
        if (type(i) != luaType) {
            throw getArgTypeException(i, luaType);
        }
    }

    public synchronized void close() {
        closeInternal();
    }

    public synchronized void concat(int i) {
        check();
        lua_concat(i);
    }

    public synchronized void dump(OutputStream outputStream) throws IOException {
        check();
        lua_dump(outputStream);
    }

    public synchronized boolean equal(int i, int i2) {
        check();
        return lua_equal(i, i2) != 0;
    }

    public synchronized int gc(GcAction gcAction, int i) {
        check();
        return lua_gc(gcAction.ordinal(), i);
    }

    public synchronized ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public synchronized Converter getConverter() {
        return this.converter;
    }

    public synchronized void getFEnv(int i) {
        check();
        lua_getfenv(i);
    }

    public synchronized void getField(int i, String str) {
        check();
        lua_getfield(i, str);
    }

    public synchronized void getGlobal(String str) {
        check();
        lua_getglobal(str);
    }

    public synchronized JavaReflector getJavaReflector() {
        return this.javaReflector;
    }

    public synchronized boolean getMetafield(int i, String str) {
        check();
        return lua_getmetafield(i, str) != 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000d, code lost:
        if (r0 != null) goto L_0x000f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.naef.jnlua.JavaFunction getMetamethod(java.lang.Object r2, com.naef.jnlua.JavaReflector.Metamethod r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 == 0) goto L_0x0011
            boolean r0 = r2 instanceof com.naef.jnlua.JavaReflector     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x0011
            com.naef.jnlua.JavaReflector r2 = (com.naef.jnlua.JavaReflector) r2     // Catch:{ all -> 0x0018 }
            com.naef.jnlua.JavaFunction r0 = r2.getMetamethod(r3)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x0011
        L_0x000f:
            monitor-exit(r1)
            return r0
        L_0x0011:
            com.naef.jnlua.JavaReflector r0 = r1.javaReflector     // Catch:{ all -> 0x0018 }
            com.naef.jnlua.JavaFunction r0 = r0.getMetamethod(r3)     // Catch:{ all -> 0x0018 }
            goto L_0x000f
        L_0x0018:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.naef.jnlua.LuaState.getMetamethod(java.lang.Object, com.naef.jnlua.JavaReflector$Metamethod):com.naef.jnlua.JavaFunction");
    }

    public synchronized boolean getMetatable(int i) {
        check();
        return lua_getmetatable(i) != 0;
    }

    public synchronized LuaValueProxy getProxy(int i) {
        pushValue(i);
        return new LuaValueProxyImpl(ref(REGISTRYINDEX));
    }

    public synchronized LuaValueProxy getProxy(int i, Class<?>[] clsArr) {
        Class[] clsArr2;
        pushValue(i);
        if (!isTable(i)) {
            throw new IllegalArgumentException(String.format("index %d is not a table", new Object[]{Integer.valueOf(i)}));
        }
        clsArr2 = new Class[(clsArr.length + 1)];
        System.arraycopy(clsArr, 0, clsArr2, 0, clsArr.length);
        clsArr2[clsArr2.length - 1] = LuaValueProxy.class;
        try {
        } catch (Throwable th) {
            if (-1 >= 0) {
                unref(REGISTRYINDEX, -1);
            }
            throw th;
        }
        return (LuaValueProxy) Proxy.newProxyInstance(this.classLoader, clsArr2, new LuaInvocationHandler(ref(REGISTRYINDEX)));
    }

    public synchronized <T> T getProxy(int i, Class<T> cls) {
        return getProxy(i, (Class<?>[]) new Class[]{cls});
    }

    public synchronized void getTable(int i) {
        check();
        lua_gettable(i);
    }

    public synchronized int getTop() {
        check();
        return lua_gettop();
    }

    public synchronized void insert(int i) {
        check();
        lua_insert(i);
    }

    public synchronized boolean isBoolean(int i) {
        check();
        return lua_isboolean(i) != 0;
    }

    public synchronized boolean isCFunction(int i) {
        check();
        return lua_iscfunction(i) != 0;
    }

    public synchronized boolean isFunction(int i) {
        check();
        return lua_isfunction(i) != 0;
    }

    public synchronized boolean isJavaFunction(int i) {
        check();
        return lua_isjavafunction(i) != 0;
    }

    public synchronized boolean isJavaObject(int i, Class<?> cls) {
        check();
        return this.converter.getTypeDistance(this, i, cls) != Integer.MAX_VALUE;
    }

    public synchronized boolean isJavaObjectRaw(int i) {
        check();
        return lua_isjavaobject(i) != 0;
    }

    public synchronized boolean isNil(int i) {
        check();
        return lua_isnil(i) != 0;
    }

    public synchronized boolean isNone(int i) {
        check();
        return lua_isnone(i) != 0;
    }

    public synchronized boolean isNoneOrNil(int i) {
        check();
        return lua_isnoneornil(i) != 0;
    }

    public synchronized boolean isNumber(int i) {
        check();
        return lua_isnumber(i) != 0;
    }

    public final synchronized boolean isOpen() {
        return isOpenInternal();
    }

    public synchronized boolean isString(int i) {
        check();
        return lua_isstring(i) != 0;
    }

    public synchronized boolean isTable(int i) {
        check();
        return lua_istable(i) != 0;
    }

    public synchronized boolean isThread(int i) {
        check();
        return lua_isthread(i) != 0;
    }

    public synchronized int length(int i) {
        check();
        return lua_objlen(i);
    }

    public synchronized boolean lessThan(int i, int i2) throws LuaMemoryAllocationException, LuaRuntimeException {
        check();
        return lua_lessthan(i, i2) != 0;
    }

    public synchronized void load(InputStream inputStream, String str) throws IOException {
        if (str == null) {
            throw new NullPointerException();
        }
        check();
        lua_load(inputStream, "=" + str);
    }

    public synchronized void load(String str, String str2) {
        try {
            load((InputStream) new ByteArrayInputStream(str.getBytes("UTF-8")), str2);
        } catch (IOException e) {
            throw new LuaMemoryAllocationException(e.getMessage());
        }
    }

    public synchronized void newTable() {
        check();
        lua_newtable();
    }

    public synchronized void newTable(int i, int i2) {
        check();
        lua_createtable(i, i2);
    }

    public synchronized void newThread() {
        check();
        lua_newthread();
    }

    public synchronized boolean next(int i) {
        check();
        return lua_next(i) != 0;
    }

    public synchronized void openLib(Library library) {
        check();
        library.open(this);
    }

    public synchronized void openLibs() {
        check();
        for (Library open : Library.values()) {
            open.open(this);
        }
    }

    public synchronized void pop(int i) {
        check();
        lua_pop(i);
    }

    public synchronized void pushBoolean(boolean z) {
        check();
        lua_pushboolean(z ? 1 : 0);
    }

    public synchronized void pushInteger(int i) {
        check();
        lua_pushinteger(i);
    }

    public synchronized void pushJavaFunction(JavaFunction javaFunction) {
        check();
        lua_pushjavafunction(javaFunction);
    }

    public synchronized void pushJavaObject(Object obj) {
        check();
        getConverter().convertJavaObject(this, obj);
    }

    public synchronized void pushJavaObjectRaw(Object obj) {
        check();
        lua_pushjavaobject(obj);
    }

    public synchronized void pushNil() {
        check();
        lua_pushnil();
    }

    public synchronized void pushNumber(double d) {
        check();
        lua_pushnumber(d);
    }

    public synchronized void pushString(String str) {
        check();
        lua_pushstring(str);
    }

    public synchronized void pushString(byte[] bArr) {
        check();
        lua_pushbytearray(bArr, bArr.length);
    }

    public synchronized void pushString(byte[] bArr, int i) {
        check();
        lua_pushbytearray(bArr, i);
    }

    public synchronized void pushValue(int i) {
        check();
        lua_pushvalue(i);
    }

    public synchronized boolean rawEqual(int i, int i2) {
        check();
        return lua_rawequal(i, i2) != 0;
    }

    public synchronized void rawGet(int i) {
        check();
        lua_rawget(i);
    }

    public synchronized void rawGet(int i, int i2) {
        check();
        lua_rawgeti(i, i2);
    }

    public synchronized void rawSet(int i) {
        check();
        lua_rawset(i);
    }

    public synchronized void rawSet(int i, int i2) {
        check();
        lua_rawseti(i, i2);
    }

    public synchronized int ref(int i) {
        check();
        return lua_ref(i);
    }

    public synchronized void register(NamedJavaFunction namedJavaFunction) {
        check();
        String name = namedJavaFunction.getName();
        if (name == null) {
            throw new IllegalArgumentException("Anonymous function");
        }
        pushJavaFunction(namedJavaFunction);
        setGlobal(name);
    }

    public synchronized void register(String str, NamedJavaFunction[] namedJavaFunctionArr) {
        synchronized (this) {
            check();
            lua_findtable(REGISTRYINDEX, "_LOADED", 1);
            getField(-1, str);
            if (!isTable(-1)) {
                pop(1);
                String lua_findtable = lua_findtable(GLOBALSINDEX, str, namedJavaFunctionArr.length);
                if (lua_findtable != null) {
                    throw new IllegalArgumentException(String.format("naming conflict for module name '%s' at '%s'", new Object[]{str, lua_findtable}));
                } else {
                    pushValue(-1);
                    setField(-3, str);
                }
            }
            remove(-2);
            for (int i = 0; i < namedJavaFunctionArr.length; i++) {
                String name = namedJavaFunctionArr[i].getName();
                if (name == null) {
                    throw new IllegalArgumentException(String.format("anonymous function at index %d", new Object[]{Integer.valueOf(i)}));
                }
                pushJavaFunction(namedJavaFunctionArr[i]);
                setField(-2, name);
            }
        }
    }

    public synchronized void remove(int i) {
        check();
        lua_remove(i);
    }

    public synchronized void replace(int i) {
        check();
        lua_replace(i);
    }

    public synchronized int resume(int i, int i2) {
        check();
        return lua_resume(i, i2);
    }

    public synchronized void setClassLoader(ClassLoader classLoader2) {
        if (classLoader2 == null) {
            throw new NullPointerException();
        }
        this.classLoader = classLoader2;
    }

    public synchronized void setConverter(Converter converter2) {
        if (converter2 == null) {
            throw new NullPointerException();
        }
        this.converter = converter2;
    }

    public synchronized boolean setFEnv(int i) {
        check();
        return lua_setfenv(i) != 0;
    }

    public synchronized void setField(int i, String str) {
        check();
        lua_setfield(i, str);
    }

    public synchronized void setGlobal(String str) throws LuaMemoryAllocationException, LuaRuntimeException {
        check();
        lua_setglobal(str);
    }

    public synchronized void setJavaReflector(JavaReflector javaReflector2) {
        if (javaReflector2 == null) {
            throw new NullPointerException();
        }
        this.javaReflector = javaReflector2;
    }

    public synchronized boolean setMetatable(int i) {
        check();
        return lua_setmetatable(i) != 0;
    }

    public synchronized void setTable(int i) {
        check();
        lua_settable(i);
    }

    public synchronized void setTop(int i) {
        check();
        lua_settop(i);
    }

    public synchronized int status(int i) {
        check();
        return lua_status(i);
    }

    public synchronized void tableMove(int i, int i2, int i3, int i4) {
        check();
        lua_tablemove(i, i2, i3, i4);
    }

    public synchronized int tableSize(int i) {
        check();
        return lua_tablesize(i);
    }

    public synchronized boolean toBoolean(int i) {
        check();
        return lua_toboolean(i) != 0;
    }

    public synchronized byte[] toByteArray(int i) {
        check();
        return lua_tobytearray(i);
    }

    public synchronized int toInteger(int i) {
        check();
        return lua_tointeger(i);
    }

    public synchronized JavaFunction toJavaFunction(int i) {
        check();
        return lua_tojavafunction(i);
    }

    public synchronized <T> T toJavaObject(int i, Class<T> cls) {
        check();
        return this.converter.convertLuaValue(this, i, cls);
    }

    public synchronized Object toJavaObjectRaw(int i) {
        check();
        return lua_tojavaobject(i);
    }

    public synchronized double toNumber(int i) {
        check();
        return lua_tonumber(i);
    }

    public synchronized long toPointer(int i) {
        check();
        return lua_topointer(i);
    }

    public synchronized String toString(int i) {
        check();
        return lua_tostring(i);
    }

    public synchronized LuaType type(int i) {
        int lua_type;
        check();
        lua_type = lua_type(i);
        return lua_type >= 0 ? LuaType.values()[lua_type] : null;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String typeName(int r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            r3.check()     // Catch:{ all -> 0x0038 }
            com.naef.jnlua.LuaType r0 = r3.type(r4)     // Catch:{ all -> 0x0038 }
            if (r0 != 0) goto L_0x000e
            java.lang.String r0 = "no value"
        L_0x000c:
            monitor-exit(r3)
            return r0
        L_0x000e:
            int[] r1 = com.naef.jnlua.LuaState.AnonymousClass3.$SwitchMap$com$naef$jnlua$LuaType     // Catch:{ all -> 0x0038 }
            int r2 = r0.ordinal()     // Catch:{ all -> 0x0038 }
            r1 = r1[r2]     // Catch:{ all -> 0x0038 }
            switch(r1) {
                case 1: goto L_0x001e;
                default: goto L_0x0019;
            }     // Catch:{ all -> 0x0038 }
        L_0x0019:
            java.lang.String r0 = r0.displayText()     // Catch:{ all -> 0x0038 }
            goto L_0x000c
        L_0x001e:
            boolean r1 = r3.isJavaObjectRaw(r4)     // Catch:{ all -> 0x0038 }
            if (r1 == 0) goto L_0x0019
            java.lang.Object r0 = r3.toJavaObjectRaw(r4)     // Catch:{ all -> 0x0038 }
            boolean r1 = r0 instanceof java.lang.Class     // Catch:{ all -> 0x0038 }
            if (r1 == 0) goto L_0x0033
            java.lang.Class r0 = (java.lang.Class) r0     // Catch:{ all -> 0x0038 }
        L_0x002e:
            java.lang.String r0 = r0.getCanonicalName()     // Catch:{ all -> 0x0038 }
            goto L_0x000c
        L_0x0033:
            java.lang.Class r0 = r0.getClass()     // Catch:{ all -> 0x0038 }
            goto L_0x002e
        L_0x0038:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.naef.jnlua.LuaState.typeName(int):java.lang.String");
    }

    public synchronized void unref(int i, int i2) {
        check();
        lua_unref(i, i2);
    }

    public synchronized int yield(int i) {
        check();
        return lua_yield(i);
    }
}
