package com.naef.jnlua;

import java.io.PrintStream;
import java.io.PrintWriter;

public class LuaRuntimeException extends LuaException {
    private static final LuaStackTraceElement[] EMPTY_LUA_STACK_TRACE = new LuaStackTraceElement[0];
    private static final long serialVersionUID = 1;
    private LuaStackTraceElement[] luaStackTrace = EMPTY_LUA_STACK_TRACE;

    public LuaRuntimeException(String str) {
        super(str);
    }

    public LuaRuntimeException(String str, Throwable th) {
        super(str, th);
    }

    public LuaRuntimeException(Throwable th) {
        super(th);
    }

    public LuaStackTraceElement[] getLuaStackTrace() {
        return (LuaStackTraceElement[]) this.luaStackTrace.clone();
    }

    public void printLuaStackTrace() {
        printLuaStackTrace(System.err);
    }

    public void printLuaStackTrace(PrintStream printStream) {
        synchronized (printStream) {
            printStream.println(this);
            for (int i = 0; i < this.luaStackTrace.length; i++) {
                printStream.println("\tat " + this.luaStackTrace[i]);
            }
        }
    }

    public void printLuaStackTrace(PrintWriter printWriter) {
        synchronized (printWriter) {
            printWriter.println(this);
            for (int i = 0; i < this.luaStackTrace.length; i++) {
                printWriter.println("\tat " + this.luaStackTrace[i]);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setLuaError(LuaError luaError) {
        initCause(luaError.getCause());
        this.luaStackTrace = luaError.getLuaStackTrace();
    }
}
