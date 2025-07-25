package com.naef.jnlua;

public class LuaMemoryAllocationException extends LuaException {
    private static final long serialVersionUID = 1;

    public LuaMemoryAllocationException(String str) {
        super(str);
    }

    public LuaMemoryAllocationException(String str, Throwable th) {
        super(str, th);
    }
}
