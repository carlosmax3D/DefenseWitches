package com.naef.jnlua.script;

import com.naef.jnlua.LuaState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class LuaScriptEngineFactory implements ScriptEngineFactory {
    private static final String ENGINE_NAME = "JNLua";
    private static final List<String> EXTENSIONS;
    private static final String LANGUAGE_NAME = "Lua";
    private static final List<String> MIME_TYPES;
    private static final List<String> NAMES;

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("lua");
        EXTENSIONS = Collections.unmodifiableList(arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("application/x-lua");
        arrayList2.add("text/x-lua");
        MIME_TYPES = Collections.unmodifiableList(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("lua");
        arrayList3.add(LANGUAGE_NAME);
        arrayList3.add("jnlua");
        arrayList3.add(ENGINE_NAME);
        NAMES = Collections.unmodifiableList(arrayList3);
    }

    private void quoteString(StringBuffer stringBuffer, String str) {
        stringBuffer.append('\"');
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 7:
                    stringBuffer.append("\\a");
                    break;
                case 8:
                    stringBuffer.append("\\b");
                    break;
                case 9:
                    stringBuffer.append("\\t");
                    break;
                case 10:
                    stringBuffer.append("\\n");
                    break;
                case 11:
                    stringBuffer.append("\\v");
                    break;
                case 12:
                    stringBuffer.append("\\f");
                    break;
                case 13:
                    stringBuffer.append("\\r");
                    break;
                case '\"':
                    stringBuffer.append("\\\"");
                    break;
                case '\\':
                    stringBuffer.append("\\\\");
                    break;
                default:
                    stringBuffer.append(str.charAt(i));
                    break;
            }
        }
        stringBuffer.append('\"');
    }

    public String getEngineName() {
        return ENGINE_NAME;
    }

    public String getEngineVersion() {
        return LuaState.VERSION;
    }

    public List<String> getExtensions() {
        return EXTENSIONS;
    }

    public String getLanguageName() {
        return LANGUAGE_NAME;
    }

    public String getLanguageVersion() {
        return LuaState.LUA_VERSION;
    }

    public String getMethodCallSyntax(String str, String str2, String... strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(':');
        stringBuffer.append(str2);
        stringBuffer.append('(');
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(strArr[i]);
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public List<String> getMimeTypes() {
        return MIME_TYPES;
    }

    public List<String> getNames() {
        return NAMES;
    }

    public String getOutputStatement(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("print(");
        quoteString(stringBuffer, str);
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public Object getParameter(String str) {
        if (str.equals("javax.script.engine")) {
            return getEngineName();
        }
        if (str.equals("javax.script.engine_version")) {
            return getEngineVersion();
        }
        if (str.equals("javax.script.name")) {
            return getNames().get(0);
        }
        if (str.equals("javax.script.language")) {
            return getLanguageName();
        }
        if (str.equals("javax.script.language_version")) {
            return getLanguageVersion();
        }
        if (str.equals("THREADING")) {
            return "MULTITHREADED";
        }
        return null;
    }

    public String getProgram(String... strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String append : strArr) {
            stringBuffer.append(append);
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    public ScriptEngine getScriptEngine() {
        return new LuaScriptEngine(this);
    }
}
