package com.naef.jnlua.script;

import com.naef.jnlua.LuaException;
import com.naef.jnlua.LuaState;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import twitter4j.HttpResponseCode;

class LuaScriptEngine extends AbstractScriptEngine implements Compilable, Invocable {
    private static final String ERROR_WRITER = "errorWriter";
    private static final Pattern LUA_ERROR_MESSAGE = Pattern.compile("^(.+):(\\d+):");
    private static final String READER = "reader";
    private static final String WRITER = "writer";
    private LuaScriptEngineFactory factory;
    private LuaState luaState = new LuaState();

    private static class ReaderInputStream extends InputStream {
        private static final Charset UTF8 = Charset.forName("UTF-8");
        private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        private CharBuffer charBuffer = CharBuffer.allocate(1024);
        private CharsetEncoder encoder;
        private boolean flushed;
        private Reader reader;

        public ReaderInputStream(Reader reader2) {
            this.reader = reader2;
            this.encoder = UTF8.newEncoder();
            this.charBuffer.limit(0);
            this.byteBuffer.limit(0);
        }

        public int read() throws IOException {
            if (!this.byteBuffer.hasRemaining()) {
                if (!this.charBuffer.hasRemaining()) {
                    this.charBuffer.clear();
                    this.reader.read(this.charBuffer);
                    this.charBuffer.flip();
                }
                this.byteBuffer.clear();
                if (this.charBuffer.hasRemaining()) {
                    if (this.encoder.encode(this.charBuffer, this.byteBuffer, false).isError()) {
                        throw new IOException("Encoding error");
                    }
                } else if (!this.flushed) {
                    if (this.encoder.encode(this.charBuffer, this.byteBuffer, true).isError()) {
                        throw new IOException("Encoding error");
                    } else if (this.encoder.flush(this.byteBuffer).isError()) {
                        throw new IOException("Encoding error");
                    } else {
                        this.flushed = true;
                    }
                }
                this.byteBuffer.flip();
                if (!this.byteBuffer.hasRemaining()) {
                    return -1;
                }
            }
            return this.byteBuffer.get();
        }
    }

    LuaScriptEngine(LuaScriptEngineFactory luaScriptEngineFactory) {
        this.factory = luaScriptEngineFactory;
        this.context.setBindings(createBindings(), 100);
        this.luaState.openLibs();
        this.luaState.load("io.stdout:setvbuf(\"no\")", "setvbuf");
        this.luaState.call(0, 0);
        this.luaState.load("io.stderr:setvbuf(\"no\")", "setvbuf");
        this.luaState.call(0, 0);
    }

    private void applyBindings(Bindings bindings) {
        for (Map.Entry entry : bindings.entrySet()) {
            this.luaState.pushJavaObject(entry.getValue());
            String str = (String) entry.getKey();
            int lastIndexOf = str.lastIndexOf(46);
            if (lastIndexOf >= 0) {
                str = str.substring(lastIndexOf + 1);
            }
            this.luaState.setGlobal(str);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0002, code lost:
        r0 = r2.getAttribute("javax.script.filename");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getChunkName(javax.script.ScriptContext r2) {
        /*
            r1 = this;
            if (r2 == 0) goto L_0x000f
            java.lang.String r0 = "javax.script.filename"
            java.lang.Object r0 = r2.getAttribute(r0)
            if (r0 == 0) goto L_0x000f
            java.lang.String r0 = r0.toString()
        L_0x000e:
            return r0
        L_0x000f:
            java.lang.String r0 = "null"
            goto L_0x000e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.naef.jnlua.script.LuaScriptEngine.getChunkName(javax.script.ScriptContext):java.lang.String");
    }

    private ScriptException getScriptException(LuaException luaException) {
        Matcher matcher = LUA_ERROR_MESSAGE.matcher(luaException.getMessage());
        if (!matcher.find()) {
            return new ScriptException(luaException);
        }
        return new ScriptException(luaException.getMessage(), matcher.group(1), Integer.parseInt(matcher.group(2)));
    }

    /* access modifiers changed from: package-private */
    public Object callChunk(ScriptContext scriptContext) throws ScriptException {
        Object[] objArr;
        if (scriptContext != null) {
            try {
                Bindings bindings = scriptContext.getBindings(HttpResponseCode.OK);
                if (bindings != null) {
                    applyBindings(bindings);
                }
                LuaBindings bindings2 = scriptContext.getBindings(100);
                if (bindings2 != null && (!(bindings2 instanceof LuaBindings) || bindings2.getScriptEngine() != this)) {
                    applyBindings(bindings2);
                }
                put(READER, scriptContext.getReader());
                put(WRITER, scriptContext.getWriter());
                put(ERROR_WRITER, scriptContext.getErrorWriter());
                objArr = (Object[]) scriptContext.getAttribute("javax.script.argv");
            } catch (LuaException e) {
                throw getScriptException(e);
            } catch (Throwable th) {
                this.luaState.pop(1);
                throw th;
            }
        } else {
            objArr = null;
        }
        int length = objArr != null ? objArr.length : 0;
        for (int i = 0; i < length; i++) {
            this.luaState.pushJavaObject(objArr[i]);
        }
        this.luaState.call(length, 1);
        Object javaObject = this.luaState.toJavaObject(1, Object.class);
        this.luaState.pop(1);
        return javaObject;
    }

    public CompiledScript compile(Reader reader) throws ScriptException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        synchronized (this.luaState) {
            loadChunk(reader, (ScriptContext) null);
            try {
                dumpChunk(byteArrayOutputStream);
            } finally {
                this.luaState.pop(1);
            }
        }
        return new CompiledLuaScript(this, byteArrayOutputStream.toByteArray());
    }

    public CompiledScript compile(String str) throws ScriptException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        synchronized (this.luaState) {
            loadChunk(str, (ScriptContext) null);
            try {
                dumpChunk(byteArrayOutputStream);
            } finally {
                this.luaState.pop(1);
            }
        }
        return new CompiledLuaScript(this, byteArrayOutputStream.toByteArray());
    }

    public Bindings createBindings() {
        return new LuaBindings(this);
    }

    /* access modifiers changed from: package-private */
    public void dumpChunk(OutputStream outputStream) throws ScriptException {
        try {
            this.luaState.dump(outputStream);
        } catch (LuaException e) {
            throw new ScriptException(e);
        } catch (IOException e2) {
            throw new ScriptException(e2);
        }
    }

    public Object eval(Reader reader, ScriptContext scriptContext) throws ScriptException {
        Object callChunk;
        synchronized (this.luaState) {
            loadChunk(reader, scriptContext);
            callChunk = callChunk(scriptContext);
        }
        return callChunk;
    }

    public Object eval(String str, ScriptContext scriptContext) throws ScriptException {
        Object callChunk;
        synchronized (this.luaState) {
            loadChunk(str, scriptContext);
            callChunk = callChunk(scriptContext);
        }
        return callChunk;
    }

    public ScriptEngineFactory getFactory() {
        return this.factory;
    }

    public <T> T getInterface(Class<T> cls) {
        T proxy;
        synchronized (this.luaState) {
            this.luaState.pushValue(LuaState.GLOBALSINDEX);
            try {
                proxy = this.luaState.getProxy(-1, cls);
            } finally {
                this.luaState.pop(1);
            }
        }
        return proxy;
    }

    public <T> T getInterface(Object obj, Class<T> cls) {
        T proxy;
        synchronized (this.luaState) {
            this.luaState.pushJavaObject(obj);
            try {
                if (!this.luaState.isTable(-1)) {
                    throw new IllegalArgumentException("object is not a table");
                }
                proxy = this.luaState.getProxy(-1, cls);
            } finally {
                this.luaState.pop(1);
            }
        }
        return proxy;
    }

    /* access modifiers changed from: package-private */
    public LuaState getLuaState() {
        return this.luaState;
    }

    public Object invokeFunction(String str, Object... objArr) throws ScriptException, NoSuchMethodException {
        Object javaObject;
        synchronized (this.luaState) {
            this.luaState.getGlobal(str);
            if (!this.luaState.isFunction(-1)) {
                this.luaState.pop(1);
                throw new NoSuchMethodException(String.format("function '%s' is undefined", new Object[]{str}));
            }
            for (Object pushJavaObject : objArr) {
                this.luaState.pushJavaObject(pushJavaObject);
            }
            this.luaState.call(objArr.length, 1);
            try {
                javaObject = this.luaState.toJavaObject(-1, Object.class);
            } finally {
                this.luaState.pop(1);
            }
        }
        return javaObject;
    }

    public Object invokeMethod(Object obj, String str, Object... objArr) throws ScriptException, NoSuchMethodException {
        Object javaObject;
        synchronized (this.luaState) {
            this.luaState.pushJavaObject(obj);
            try {
                if (!this.luaState.isTable(-1)) {
                    throw new IllegalArgumentException("object is not a table");
                }
                this.luaState.getField(-1, str);
                if (!this.luaState.isFunction(-1)) {
                    this.luaState.pop(1);
                    throw new NoSuchMethodException(String.format("method '%s' is undefined", new Object[]{str}));
                }
                this.luaState.pushValue(-2);
                for (Object pushJavaObject : objArr) {
                    this.luaState.pushJavaObject(pushJavaObject);
                }
                this.luaState.call(objArr.length + 1, 1);
                javaObject = this.luaState.toJavaObject(-1, Object.class);
                this.luaState.pop(1);
                this.luaState.pop(1);
            } catch (Throwable th) {
                this.luaState.pop(1);
                throw th;
            }
        }
        return javaObject;
    }

    /* access modifiers changed from: package-private */
    public void loadChunk(InputStream inputStream, ScriptContext scriptContext) throws ScriptException {
        try {
            this.luaState.load(inputStream, getChunkName(scriptContext));
        } catch (LuaException e) {
            throw getScriptException(e);
        } catch (IOException e2) {
            throw new ScriptException(e2);
        }
    }

    /* access modifiers changed from: package-private */
    public void loadChunk(Reader reader, ScriptContext scriptContext) throws ScriptException {
        loadChunk((InputStream) new ReaderInputStream(reader), scriptContext);
    }

    /* access modifiers changed from: package-private */
    public void loadChunk(String str, ScriptContext scriptContext) throws ScriptException {
        try {
            this.luaState.load(str, getChunkName(scriptContext));
        } catch (LuaException e) {
            throw getScriptException(e);
        }
    }
}
