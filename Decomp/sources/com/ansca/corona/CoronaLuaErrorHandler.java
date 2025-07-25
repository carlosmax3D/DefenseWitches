package com.ansca.corona;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaError;
import com.naef.jnlua.LuaStackTraceElement;
import com.naef.jnlua.LuaState;

public class CoronaLuaErrorHandler implements JavaFunction {
    /* access modifiers changed from: private */
    public Controller fController;
    private boolean fIsShowingError = false;

    private String getStackTraceFrom(Throwable th) {
        if (th == null || th.getStackTrace() == null || th.getStackTrace().length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(LuaError.JAVA_STACK_TRACE_HEADER_MESSAGE);
        for (StackTraceElement stackTraceElement : th.getStackTrace()) {
            sb.append("\n\t");
            sb.append(stackTraceElement.toString());
        }
        return sb.toString();
    }

    private String getStackTraceFrom(LuaStackTraceElement[] luaStackTraceElementArr) {
        if (luaStackTraceElementArr == null || luaStackTraceElementArr.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Lua Stack Trace:");
        for (LuaStackTraceElement luaStackTraceElement : luaStackTraceElementArr) {
            sb.append("\n\t");
            sb.append(luaStackTraceElement.toString());
        }
        return sb.toString();
    }

    private void reportError(final String str, final RuntimeException runtimeException) {
        if (runtimeException == null) {
            throw new NullPointerException();
        } else if (str == null || str.length() <= 0) {
            throw runtimeException;
        } else if (!this.fIsShowingError) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            if (coronaActivity == null) {
                throw runtimeException;
            }
            this.fIsShowingError = true;
            coronaActivity.runOnUiThread(new Runnable() {
                public void run() {
                    CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
                    if (coronaActivity == null) {
                        throw runtimeException;
                    }
                    AnonymousClass1 r2 = new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialogInterface) {
                            throw runtimeException;
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(coronaActivity);
                    builder.setTitle("Runtime Error");
                    builder.setMessage(str);
                    builder.setOnCancelListener(r2);
                    AlertDialog create = builder.create();
                    create.setCanceledOnTouchOutside(false);
                    create.show();
                }
            });
        }
    }

    public int invoke(LuaState luaState) {
        Handler handler;
        String str = null;
        String str2 = null;
        String str3 = null;
        if (luaState.isString(1)) {
            str = luaState.toString(1);
        } else if (luaState.isJavaObjectRaw(1)) {
            Object javaObjectRaw = luaState.toJavaObjectRaw(1);
            if (javaObjectRaw instanceof LuaError) {
                LuaError luaError = (LuaError) javaObjectRaw;
                str = luaError.toString();
                str3 = getStackTraceFrom(luaError.getLuaStackTrace());
                str2 = getStackTraceFrom(luaError.getCause());
            }
        }
        if (str == null || str.length() <= 0) {
            str = "Lua runtime error occurred.";
        }
        int indexOf = str.indexOf(LuaError.JAVA_STACK_TRACE_HEADER_MESSAGE);
        if (indexOf > 0) {
            if (str2 == null) {
                str2 = str.substring(indexOf + 1);
            }
            str = str.substring(0, indexOf);
        }
        if (str3 == null) {
            int top = luaState.getTop();
            luaState.getField(LuaState.GLOBALSINDEX, "debug");
            if (luaState.isTable(-1)) {
                luaState.getField(-1, "traceback");
                if (luaState.isFunction(-1)) {
                    luaState.call(0, 1);
                    if (luaState.isString(-1)) {
                        str3 = luaState.toString(-1);
                    }
                }
            }
            luaState.setTop(top);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        if (str2 != null && str2.length() > 0) {
            sb.append("\n");
            sb.append(str2);
        }
        if (str3 != null && str3.length() > 0) {
            sb.append("\n");
            sb.append(str3);
        }
        RuntimeException runtimeException = new RuntimeException(sb.toString());
        runtimeException.setStackTrace(new StackTraceElement[0]);
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (!(coronaActivity == null || (handler = coronaActivity.getHandler()) == null)) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (CoronaLuaErrorHandler.this.fController != null) {
                        CoronaLuaErrorHandler.this.fController.stop();
                    } else {
                        Log.d("Corona", "CoronaLuaErrorHandler.invoke(): Can't suspend the CoronaRuntime because our Controller died!");
                    }
                }
            }, 10);
        }
        reportError(str, runtimeException);
        return 1;
    }

    /* access modifiers changed from: package-private */
    public void setController(Controller controller) {
        this.fController = controller;
    }
}
