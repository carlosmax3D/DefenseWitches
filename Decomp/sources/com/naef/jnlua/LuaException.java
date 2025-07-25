package com.naef.jnlua;

public abstract class LuaException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public LuaException(String str) {
        super(str);
    }

    public LuaException(String str, Throwable th) {
        super(str, th);
    }

    public LuaException(Throwable th) {
        super(th);
    }
}
