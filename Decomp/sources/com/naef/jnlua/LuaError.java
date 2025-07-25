package com.naef.jnlua;

public class LuaError {
    public static final String JAVA_STACK_TRACE_HEADER_MESSAGE = "\nJava Stack Trace:";
    private Throwable cause;
    private LuaStackTraceElement[] luaStackTrace;
    private String message;

    public LuaError(String str, Throwable th) {
        this.message = str;
        this.cause = th;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public LuaStackTraceElement[] getLuaStackTrace() {
        return this.luaStackTrace;
    }

    public String getMessage() {
        return this.message;
    }

    /* access modifiers changed from: package-private */
    public void setLuaStackTrace(LuaStackTraceElement[] luaStackTraceElementArr) {
        this.luaStackTrace = luaStackTraceElementArr;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.message != null) {
            sb.append(this.message);
        }
        if (this.cause != null) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(this.cause.toString());
            if (this.cause.getStackTrace().length > 0) {
                sb.append(JAVA_STACK_TRACE_HEADER_MESSAGE);
                for (StackTraceElement stackTraceElement : this.cause.getStackTrace()) {
                    sb.append("\n\t");
                    sb.append(stackTraceElement.toString());
                }
            }
        }
        return sb.toString();
    }
}
