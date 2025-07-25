package com.naef.jnlua;

public enum LuaType {
    NIL,
    BOOLEAN,
    LIGHTUSERDATA,
    NUMBER,
    STRING,
    TABLE,
    FUNCTION,
    USERDATA,
    THREAD;

    public String displayText() {
        return toString().toLowerCase();
    }
}
