package com.naef.jnlua;

public interface LuaValueProxy {
    LuaState getLuaState();

    void pushValue();
}
