package com.ansca.corona;

import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import java.util.Hashtable;
import java.util.Map;

public class CoronaLua {
    public static final int NOREF = -2;
    public static final int REFNIL = -1;
    private static int REF_OWNER = LuaState.REGISTRYINDEX;

    public static void deleteRef(LuaState luaState, int i) {
        if (i > 0) {
            luaState.unref(REF_OWNER, i);
        }
    }

    public static void dispatchEvent(LuaState luaState, int i, int i2) throws Exception {
        int top = luaState.getTop();
        Exception exc = null;
        luaState.getField(top, "name");
        if (LuaType.STRING == luaState.type(-1)) {
            String luaState2 = luaState.toString(-1);
            luaState.rawGet(REF_OWNER, i);
            if (luaState.isFunction(-1)) {
                luaState.pushValue(top);
                luaState.call(1, i2);
            } else if (luaState.isTable(-1)) {
                luaState.getField(-1, luaState2);
                if (luaState.isFunction(-1)) {
                    luaState.insert(-2);
                    luaState.pushValue(top);
                    luaState.call(2, i2);
                } else {
                    exc = new Exception("[Lua::DispatchEvent()] ERROR: Table listener's property '" + luaState2 + "' is not a function.");
                }
            } else if (!luaState.isNoneOrNil(-1)) {
                exc = new Exception("[Lua::DispatchEvent()] ERROR: Listener must be a function or a table, not a '" + luaState.typeName(-1) + "'.");
            }
        } else {
            exc = new Exception("[Lua::DispatchEvent()] ERROR: Attempt to dispatch malformed event. The event must have a 'name' string property.");
        }
        luaState.pop(2);
        if (exc != null) {
            throw exc;
        }
    }

    public static void dispatchRuntimeEvent(LuaState luaState, int i) {
        luaState.getGlobal("Runtime");
        luaState.getField(-1, "dispatchEvent");
        luaState.insert(-3);
        luaState.insert(-2);
        luaState.call(2, i);
    }

    public static boolean isListener(LuaState luaState, int i, String str) {
        boolean isFunction = luaState.isFunction(i);
        if (isFunction || !luaState.isTable(i)) {
            return isFunction;
        }
        luaState.getField(i, str);
        boolean isFunction2 = luaState.isFunction(-1);
        luaState.pop(1);
        return isFunction2;
    }

    public static void newEvent(LuaState luaState, String str) {
        luaState.newTable();
        luaState.pushString(str);
        luaState.setField(-2, "name");
    }

    public static int newRef(LuaState luaState, int i) {
        luaState.pushValue(i);
        return luaState.ref(REF_OWNER);
    }

    public static int normalize(LuaState luaState, int i) {
        return i < 0 ? luaState.getTop() + i + 1 : i;
    }

    public static void pushHashtable(LuaState luaState, Hashtable<Object, Object> hashtable) {
        if (hashtable == null) {
            luaState.newTable();
            return;
        }
        luaState.newTable(0, hashtable.size());
        int top = luaState.getTop();
        for (Map.Entry next : hashtable.entrySet()) {
            pushValue(luaState, next.getKey());
            pushValue(luaState, next.getValue());
            luaState.setTable(top);
        }
    }

    public static void pushValue(LuaState luaState, Object obj) {
        if (obj instanceof String) {
            luaState.pushString((String) obj);
        } else if (obj instanceof Integer) {
            luaState.pushInteger(((Integer) obj).intValue());
        } else if (obj instanceof Double) {
            luaState.pushNumber(((Double) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            luaState.pushBoolean(((Boolean) obj).booleanValue());
        } else if (obj instanceof Hashtable) {
            pushHashtable(luaState, (Hashtable) obj);
        } else {
            luaState.pushNil();
        }
    }

    public static Hashtable<Object, Object> toHashtable(LuaState luaState, int i) {
        Object value;
        if (i < 0) {
            i = luaState.getTop() + i + 1;
        }
        Hashtable<Object, Object> hashtable = new Hashtable<>();
        luaState.checkType(i, LuaType.TABLE);
        luaState.pushNil();
        while (luaState.next(i)) {
            Object value2 = toValue(luaState, -2);
            if (!(value2 == null || (value = toValue(luaState, -1)) == null)) {
                hashtable.put(value2, value);
            }
            luaState.pop(1);
        }
        return hashtable;
    }

    public static Object toValue(LuaState luaState, int i) {
        if (i < 0) {
            i = luaState.getTop() + i + 1;
        }
        Object obj = null;
        switch (luaState.type(i)) {
            case STRING:
                return luaState.toString(i);
            case NUMBER:
                return Double.valueOf(luaState.toNumber(i));
            case BOOLEAN:
                return Boolean.valueOf(luaState.toBoolean(i));
            case TABLE:
                luaState.getGlobal("system");
                luaState.getField(-1, "pathForTable");
                luaState.remove(-2);
                luaState.pushValue(i);
                luaState.call(1, 1);
                switch (luaState.type(-1)) {
                    case STRING:
                        obj = luaState.toString(-1);
                        break;
                    case NIL:
                        obj = toHashtable(luaState, i);
                        break;
                }
                luaState.pop(1);
                return obj;
            default:
                return null;
        }
    }
}
