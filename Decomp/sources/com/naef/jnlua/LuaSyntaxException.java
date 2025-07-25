package com.naef.jnlua;

public class LuaSyntaxException extends LuaException {
    private static final long serialVersionUID = 1;

    public LuaSyntaxException(String str) {
        super(str);
    }

    public LuaSyntaxException(String str, Throwable th) {
        super(str, th);
    }
}
