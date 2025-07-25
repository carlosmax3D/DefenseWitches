package com.naef.jnlua.script;

import com.naef.jnlua.LuaState;
import com.naef.jnlua.util.AbstractTableMap;
import javax.script.Bindings;

class LuaBindings extends AbstractTableMap<String> implements Bindings {
    private LuaScriptEngine scriptEngine;

    public LuaBindings(LuaScriptEngine luaScriptEngine) {
        this.scriptEngine = luaScriptEngine;
    }

    /* access modifiers changed from: protected */
    public boolean acceptKey(int i) {
        return getLuaState().isString(i) && getLuaState().toString(i).length() > 0;
    }

    /* access modifiers changed from: protected */
    public void checkKey(Object obj) {
        super.checkKey(obj);
        if (!(obj instanceof String)) {
            throw new IllegalArgumentException("key must be a string");
        } else if (((String) obj).length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    /* access modifiers changed from: protected */
    public String convertKey(int i) {
        return getLuaState().toString(i);
    }

    /* access modifiers changed from: protected */
    public boolean filterKeys() {
        return true;
    }

    public LuaState getLuaState() {
        return this.scriptEngine.getLuaState();
    }

    /* access modifiers changed from: package-private */
    public LuaScriptEngine getScriptEngine() {
        return this.scriptEngine;
    }

    public void pushValue() {
        getLuaState().pushValue(LuaState.GLOBALSINDEX);
    }

    public /* bridge */ /* synthetic */ Object put(String str, Object obj) {
        return super.put(str, obj);
    }
}
