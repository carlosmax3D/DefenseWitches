package com.naef.jnlua.script;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

class CompiledLuaScript extends CompiledScript {
    private LuaScriptEngine engine;
    private byte[] script;

    public CompiledLuaScript(LuaScriptEngine luaScriptEngine, byte[] bArr) {
        this.engine = luaScriptEngine;
        this.script = bArr;
    }

    public Object eval(ScriptContext scriptContext) throws ScriptException {
        Object callChunk;
        synchronized (this.engine.getLuaState()) {
            this.engine.loadChunk((InputStream) new ByteArrayInputStream(this.script), scriptContext);
            callChunk = this.engine.callChunk(scriptContext);
        }
        return callChunk;
    }

    public ScriptEngine getEngine() {
        return this.engine;
    }
}
