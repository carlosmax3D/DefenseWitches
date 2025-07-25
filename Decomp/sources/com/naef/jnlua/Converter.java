package com.naef.jnlua;

public interface Converter {
    void convertJavaObject(LuaState luaState, Object obj);

    <T> T convertLuaValue(LuaState luaState, int i, Class<T> cls);

    int getTypeDistance(LuaState luaState, int i, Class<?> cls);
}
