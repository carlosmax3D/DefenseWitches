package com.naef.jnlua;

public class LuaMessageHandlerException extends LuaException {
    private static final long serialVersionUID = 1;

    public LuaMessageHandlerException(String str) {
        super(str);
    }

    public LuaMessageHandlerException(String str, Throwable th) {
        super(str, th);
    }
}
