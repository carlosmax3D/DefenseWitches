package com.ansca.corona;

import com.naef.jnlua.LuaState;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class CoronaRuntimeProvider {
    private static ConcurrentHashMap<LuaState, CoronaRuntime> sRuntimesByLuaState = new ConcurrentHashMap<>();

    public static void addRuntime(CoronaRuntime coronaRuntime) {
        sRuntimesByLuaState.put(coronaRuntime.getLuaState(), coronaRuntime);
    }

    public static Collection<CoronaRuntime> getAllCoronaRuntimes() {
        return sRuntimesByLuaState.values();
    }

    public static long getLuaStateMemoryAddress(LuaState luaState) {
        try {
            Field declaredField = luaState.getClass().getDeclaredField("luaState");
            declaredField.setAccessible(true);
            return declaredField.getLong(luaState);
        } catch (Exception e) {
            return 0;
        }
    }

    public static CoronaRuntime getRuntimeByLuaState(LuaState luaState) {
        return sRuntimesByLuaState.get(luaState);
    }

    public static void removeRuntime(CoronaRuntime coronaRuntime) {
        LuaState luaState = coronaRuntime.getLuaState();
        if (luaState != null) {
            sRuntimesByLuaState.remove(luaState);
        }
    }
}
