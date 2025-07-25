package com.naef.jnlua;

public class LuaStackTraceElement {
    private String functionName;
    private int lineNumber;
    private String sourceName;

    public LuaStackTraceElement(String str, String str2, int i) {
        this.functionName = str;
        this.sourceName = str2;
        this.lineNumber = i;
    }

    private boolean safeEquals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LuaStackTraceElement)) {
            return false;
        }
        LuaStackTraceElement luaStackTraceElement = (LuaStackTraceElement) obj;
        return safeEquals(this.functionName, luaStackTraceElement.functionName) && safeEquals(this.sourceName, luaStackTraceElement.sourceName) && this.lineNumber == luaStackTraceElement.lineNumber;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public int hashCode() {
        int i = 0;
        if ((((this.functionName != null ? this.functionName.hashCode() : 0) * 65599) + this.sourceName) != null) {
            i = this.sourceName.hashCode();
        }
        return (i * 65599) + this.lineNumber;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.functionName != null) {
            stringBuffer.append(this.functionName);
        } else {
            stringBuffer.append("(Unknown Function)");
        }
        stringBuffer.append(" (");
        if (this.sourceName != null) {
            stringBuffer.append(this.sourceName);
            if (this.lineNumber >= 0) {
                stringBuffer.append(':');
                stringBuffer.append(this.lineNumber);
            }
        } else {
            stringBuffer.append("External Function");
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }
}
